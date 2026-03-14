我来深入解析 Spring Bean 的完整生命周期，从源码层面理解各个阶段的扩展点。

---

## 1. 生命周期全景图

```
┌─────────────────────────────────────────────────────────────────────┐
│                         Spring Bean 生命周期                         │
├─────────────────────────────────────────────────────────────────────┤
                                                                     │
实例化阶段                                                            │
    │                                                                │
    ▼                                                                │
┌─────────────┐    ┌─────────────┐    ┌─────────────┐               │
│  加载配置    │ → │  解析Bean定义 │ → │  实例化Bean  │               │
│  (XML/注解)  │    │ (BeanDefinition)│   │ (构造器/工厂) │               │
└─────────────┘    └─────────────┘    └──────┬──────┘               │
                                             │                       │
初始化阶段                                    │                       │
    │                                        ▼                       │
    │                              ┌─────────────────┐               │
    │                              │  填充属性        │               │
    │                              │ (populateBean)  │               │
    │                              │  @Autowired等   │               │
    │                              └────────┬────────┘               │
    │                                       │                        │
    │    ┌──────────────────────────────────┼──────────────────┐     │
    │    ▼                                  ▼                  ▼     │
    │ ┌─────────────┐              ┌─────────────┐      ┌──────────┐ │
    │ │ Aware接口    │              │  @PostConstruct │   │ InitializingBean│
    │ │ 注入容器资源  │              │  (JSR-250)    │      │ afterPropertiesSet()│
    │ │ BeanNameAware│              └──────┬──────┘      └────┬─────┘ │
    │ │ ApplicationContextAware│            │                   │      │
    │ └──────┬──────┘                     └─────────┬─────────┘      │
    │        │                                      │                 │
    │        ▼                                      ▼                 │
    │   【BeanPostProcessor前置处理】                【自定义init-method】 │
    │   postProcessBeforeInitialization()           (XML/@Bean指定)    │
    │                                                                  │
    │                              ┌─────────────────┐                │
    └─────────────────────────────→│   Bean就绪可用   │◄────────────────┘
                                   │  (in use)       │
                                   └────────┬────────┘
                                            │
销毁阶段                                    │
    │                                       │
    ◄───────────────────────────────────────┘
    │
    ▼
┌─────────────┐              ┌─────────────┐      ┌──────────┐
│ @PreDestroy  │              │ DisposableBean │   │ 自定义destroy │
│  (JSR-250)   │              │ destroy()      │   │ -method      │
└──────┬──────┘              └──────┬──────┘      └────┬─────┘
       │                            │                  │
       └─────────────┬──────────────┴─────────┬────────┘
                     ▼                        ▼
              【BeanPostProcessor后置处理？】   （实际无此标准扩展）
                     
                     ▼
              ┌─────────────┐
              │   GC回收     │
              │  (finalize) │
              └─────────────┘
```

---

## 2. 核心源码解析

### 2.1 入口：AbstractApplicationContext.refresh()

```java
public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        // 1. 准备上下文环境
        prepareRefresh();
        
        // 2. 获取/刷新 BeanFactory（加载 BeanDefinition）
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        
        // 3. 准备 BeanFactory（添加后置处理器、注册环境Bean等）
        prepareBeanFactory(beanFactory);
        
        try {
            // 4. 子类扩展：允许在Bean创建前修改BeanFactory
            postProcessBeanFactory(beanFactory);
            
            // 5. 【关键】执行所有 BeanFactoryPostProcessor
            invokeBeanFactoryPostProcessors(beanFactory);
            
            // 6. 【关键】注册所有 BeanPostProcessor（实例化并注册）
            registerBeanPostProcessors(beanFactory);
            
            // 7. 初始化消息源、事件广播器等...
            initMessageSource();
            initApplicationEventMulticaster();
            onRefresh();  // 子类扩展（如Spring Boot的web服务器启动）
            registerListeners();
            
            // 8. 【核心】实例化所有非延迟加载的单例Bean
            finishBeanFactoryInitialization(beanFactory);
            
            // 9. 完成刷新，发布上下文刷新事件
            finishRefresh();
        }
        // ...
    }
}
```

### 2.2 核心：finishBeanFactoryInitialization

```java
protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
    // 转换服务、嵌入值解析器等初始化...
    
    // 【关键】预实例化所有非懒加载的单例
    beanFactory.preInstantiateSingletons();
}
```

### 2.3 DefaultListableBeanFactory.preInstantiateSingletons()

```java
public void preInstantiateSingletons() throws BeansException {
    List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
    
    for (String beanName : beanNames) {
        RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
        
        // 条件：非抽象、单例、非懒加载
        if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
            
            // FactoryBean 特殊处理
            if (isFactoryBean(beanName)) {
                // ...
            }
            else {
                // 【核心】获取Bean，触发完整生命周期
                getBean(beanName);  // → doGetBean → createBean
            }
        }
    }
    
    // 所有单例创建完成后，触发 SmartInitializingSingleton
    for (String beanName : beanNames) {
        Object singletonInstance = getSingleton(beanName);
        if (singletonInstance instanceof SmartInitializingSingleton) {
            ((SmartInitializingSingleton) singletonInstance).afterSingletonsInstantiated();
        }
    }
}
```

---

## 3. Bean 创建的完整流程

### 3.1 AbstractAutowireCapableBeanFactory.doCreateBean()

```java
protected Object doCreateBean(String beanName, RootBeanDefinition mbd, 
        @Nullable Object[] args) throws BeanCreationException {
    
    // ========== 第1阶段：实例化 ==========
    BeanWrapper instanceWrapper = null;
    if (mbd.isSingleton()) {
        instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
    }
    if (instanceWrapper == null) {
        // 【1.1】创建实例：构造器反射 / 工厂方法 / Supplier
        instanceWrapper = createBeanInstance(beanName, mbd, args);
    }
    Object bean = instanceWrapper.getWrappedInstance();
    Class<?> beanType = instanceWrapper.getWrappedClass();
    
    // 解决循环依赖：暴露早期引用到三级缓存
    boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
            isSingletonCurrentlyInCreation(beanName));
    if (earlySingletonExposure) {
        addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
    }
    
    // ========== 第2阶段：属性填充 ==========
    Object exposedObject = bean;
    try {
        // 【2.1】填充属性：@Autowired, @Value, @Resource 等
        populateBean(beanName, mbd, instanceWrapper);
        
        // ========== 第3阶段：初始化 ==========
        // 【3.1】执行完整的初始化回调
        exposedObject = initializeBean(beanName, exposedObject, mbd);
    }
    catch (Throwable ex) {
        // 异常处理...
    }
    
    // 注册销毁回调
    registerDisposableBeanIfNecessary(beanName, bean, mbd);
    
    return exposedObject;
}
```

### 3.2 initializeBean：初始化核心

```java
protected Object initializeBean(String beanName, Object bean, 
        @Nullable RootBeanDefinition mbd) {
    
    // ========== Step 1: Aware 接口回调 ==========
    if (System.getSecurityManager() != null) {
        AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
            invokeAwareMethods(beanName, bean);
            return null;
        }, getAccessControlContext());
    }
    else {
        invokeAwareMethods(beanName, bean);
    }
    
    // ========== Step 2: BeanPostProcessor.before ==========
    Object wrappedBean = bean;
    if (mbd == null || !mbd.isSynthetic()) {
        // 执行所有 BPP 的 before 方法
        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
    }
    
    // ========== Step 3: 初始化方法 ==========
    try {
        invokeInitMethods(beanName, wrappedBean, mbd);
    }
    catch (Throwable ex) {
        throw new BeanCreationException(...);
    }
    
    // ========== Step 4: BeanPostProcessor.after ==========
    if (mbd == null || !mbd.isSynthetic()) {
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }
    
    return wrappedBean;
}
```

### 3.3 Aware 接口详细回调

```java
private void invokeAwareMethods(String beanName, Object bean) {
    if (bean instanceof Aware) {
        // 按层次注入不同资源
        
        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        
        if (bean instanceof BeanClassLoaderAware) {
            ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
        }
        
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
        }
        
        // 注意：以下是在 ApplicationContextAwareProcessor 中处理的
        // EnvironmentAware, EmbeddedValueResolverAware, 
        // ResourceLoaderAware, ApplicationEventPublisherAware,
        // MessageSourceAware, ApplicationContextAware
    }
}
```

### 3.4 初始化方法调用顺序

```java
protected void invokeInitMethods(String beanName, Object bean, 
        RootBeanDefinition mbd) throws Throwable {
    
    // 第1种：实现 InitializingBean 接口
    boolean isInitializingBean = (bean instanceof InitializingBean);
    if (isInitializingBean && 
            (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
        
        ((InitializingBean) bean).afterPropertiesSet();
    }
    
    // 第2种：自定义 init-method（XML 或 @Bean(initMethod="xxx")）
    String initMethodName = (mbd != null ? mbd.getInitMethodName() : null);
    if (StringUtils.hasLength(initMethodName) &&
            !(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
            !mbd.isExternallyManagedInitMethod(initMethodName)) {
        
        // 反射调用自定义初始化方法
        invokeCustomInitMethod(beanName, bean, mbd);
    }
}

// 注意：@PostConstruct 的处理在 InitDestroyAnnotationBeanPostProcessor 中
// 它实际上是在 BeanPostProcessor.before 阶段执行的！
```

---

## 4. 关键扩展点详解

### 4.1 BeanPostProcessor（最常用）

```java
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) 
            throws BeansException {
        // 在 @PostConstruct / afterPropertiesSet 之前执行
        // 可用于：属性检查、代理包装前的准备
        System.out.println("Before init: " + beanName);
        return bean;  // 可以返回包装后的代理对象！
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) 
            throws BeansException {
        // 在所有初始化之后执行
        // 常用于：AOP 代理创建、性能监控代理
        System.out.println("After init: " + beanName);
        return bean;
    }
}
```

**执行顺序控制**：

```java
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)  // 数值越小优先级越高
public class PriorityProcessor implements BeanPostProcessor, Ordered {
    // ...
}
```

### 4.2 三种初始化方式的实际顺序

```java
@Component
public class LifecycleDemoBean implements InitializingBean {
    
    @PostConstruct  // ← 第1个执行（通过BPP）
    public void postConstruct() {
        System.out.println("1. @PostConstruct");
    }
    
    @Override  // ← 第2个执行
    public void afterPropertiesSet() throws Exception {
        System.out.println("2. InitializingBean.afterPropertiesSet()");
    }
    
    // ← 第3个执行（如果配置了）
    public void customInit() {
        System.out.println("3. custom init-method");
    }
}
```

**真实原因**：`@PostConstruct` 由 `InitDestroyAnnotationBeanPostProcessor` 在 `postProcessBeforeInitialization` 中处理。

---

## 5. 销毁阶段详解

### 5.1 触发时机

```java
// AbstractApplicationContext.close()
public void close() {
    synchronized (this.startupShutdownMonitor) {
        doClose();
    }
}

protected void doClose() {
    // 发布关闭事件
    publishEvent(new ContextClosedEvent(this));
    
    // 销毁所有单例Bean
    destroyBeans();
    
    // 关闭BeanFactory
    closeBeanFactory();
    
    onClose();  // 子类扩展
}

protected void destroyBeans() {
    getBeanFactory().destroySingletons();  // → DefaultSingletonBeanRegistry
}
```

### 5.2 销毁回调执行

```java
// DisposableBeanAdapter.destroy()
public void destroy() {
    // 第1种：@PreDestroy（JSR-250）
    if (!CollectionUtils.isEmpty(this.beanPostProcessors)) {
        for (DestructionAwareBeanPostProcessor processor : this.beanPostProcessors) {
            processor.postProcessBeforeDestruction(this.bean, this.beanName);
        }
    }
    
    // 第2种：DisposableBean.destroy()
    if (this.invokeDisposableBean) {
        ((DisposableBean) this.bean).destroy();
    }
    
    // 第3种：自定义 destroy-method
    if (this.destroyMethod != null) {
        invokeCustomDestroyMethod(this.destroyMethod);
    }
}
```

---

## 6. 完整生命周期时间线

```
时间 ─────────────────────────────────────────────────────────────►

配置加载        Bean定义         实例化        属性填充        初始化           使用           销毁
   │              │              │            │             │              │            │
   ▼              ▼              ▼            ▼             ▼              ▼            ▼
┌─────┐      ┌─────┐       ┌─────────┐   ┌─────────┐   ┌─────────┐    ┌─────────┐   ┌─────────┐
│读取 │  →   │解析 │   →   │构造器    │ → │@Autowired│ → │Aware    │ →  │业务逻辑   │ → │@PreDestroy│
│XML │      │@Component│   │反射调用  │   │@Value   │   │接口注入  │    │         │   │          │
│扫描 │      │@Bean    │   │@Bean方法 │   │@Resource│   │         │    │         │   │Disposable│
│    │      │       │   │Supplier  │   │setter   │   │BeanPost │    │         │   │.destroy()│
│    │      │       │   │         │   │注入     │   │.before()│    │         │   │          │
│    │      │       │   │         │   │         │   │         │    │         │   │destroy- │
│    │      │       │   │         │   │         │   │@PostConstruct│  │         │   │method   │
│    │      │       │   │         │   │         │   │         │    │         │   │          │
│    │      │       │   │         │   │         │   │Initializing│   │         │   │          │
│    │      │       │   │         │   │         │   │.afterPropertiesSet()│    │   │          │
│    │      │       │   │         │   │         │   │         │    │         │   │          │
│    │      │       │   │         │   │         │   │init-method│    │         │   │          │
│    │      │       │   │         │   │         │   │         │    │         │   │          │
│    │      │       │   │         │   │         │   │BeanPost │    │         │   │          │
│    │      │       │   │         │   │         │   │.after() │    │         │   │          │
│    │      │       │   │         │   │         │   │【AOP代理 │    │         │   │          │
│    │      │       │   │         │   │         │   │ 在此处创建】│   │         │   │          │
└─────┘      └─────┘       └─────────┘   └─────────┘   └─────────┘    └─────────┘   └─────────┘
                                              ▲
                                              │
                                    SmartInitializingSingleton
                                    .afterSingletonsInstantiated()
                                    （所有单例创建完成后）
```

---

## 7. 实战：自定义生命周期管理

### 场景：优雅关闭资源

```java
@Component
public class ConnectionPoolManager implements 
        InitializingBean,           // 初始化
        DisposableBean,             // 销毁
        SmartLifecycle,             // 控制启动顺序
        ApplicationListener<ContextRefreshedEvent> {  // 事件监听
    
    private volatile boolean running = false;
    private int phase = Ordered.LOWEST_PRECEDENCE - 100;  // 早启动晚停止
    
    // ========== 初始化阶段 ==========
    @Override
    public void afterPropertiesSet() throws Exception {
        // 属性设置后的初始化
        validateConfig();
    }
    
    @PostConstruct
    public void init() {
        // 更早的初始化
        warmUpConnections();
    }
    
    // ========== 启动阶段（SmartLifecycle）==========
    @Override
    public void start() {
        this.running = true;
        // 开始接受请求
    }
    
    @Override
    public void stop() {
        this.running = false;
        // 停止接受新请求
    }
    
    @Override
    public boolean isRunning() {
        return this.running;
    }
    
    @Override
    public int getPhase() {
        return this.phase;  // 控制多个Lifecycle的顺序
    }
    
    // ========== 销毁阶段 ==========
    @PreDestroy
    public void preDestroy() {
        // 开始优雅关闭：等待现有请求完成
        gracefulShutdown();
    }
    
    @Override
    public void destroy() throws Exception {
        // 最终强制清理
        forceCloseAllConnections();
    }
    
    // ========== 事件监听 ==========
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 上下文完全刷新后，可做额外检查
        healthCheck();
    }
}
```

---

如需深入了解 **Spring Boot 的自动配置如何影响生命周期**、**@Configuration 类的特殊处理（Full vs Lite 模式）**，或 **BeanFactoryPostProcessor 与 BeanPostProcessor 的区别**，可以继续探讨！
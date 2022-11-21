# Srping
## bean
> e.g. AnnotationConfigApplicationContext

> 写了个简单的spring 加载bean的过程,没有涉及依赖其他bean,循环依赖等
1. org.springframework.context.support.AbstractApplicationContext#refresh
   * org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization  
   加载非懒加载的bean
      * org.springframework.beans.factory.support.DefaultListableBeanFactory#preInstantiateSingletons
      * org.springframework.beans.factory.support.AbstractBeanFactory#getBean(java.lang.String)
      * org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory<?>)  
        //创建bean并且加入缓存
        * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])  
          //创建bean,这里有bean属性复制,postProcess等

> spring 循环依赖处理
1. org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#beforeSingletonCreation
   放入singletonsCurrentlyInCreation Map ①
   * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
     * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#doCreateBean
     * org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#addSingletonFactory ②
       这里放入bean对应的Beanfactory映射关系singletonFactories Map
       * org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#populateBean
         * org/springframework/beans/factory/support/AbstractAutowireCapableBeanFactory.java:1377
           * AutowiredAnnotationBeanPostProcessor  处理通过autowired依赖的类(就先看autowired 这个例子) 
             * org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency ③
             * org.springframework.beans.factory.config.DependencyDescriptor#resolveCandidate
               又到了beanFactory.getBean(beanName).

2. A依赖了B,同时B依赖了A, 创建A的实例的时候 把A放入singletonsCurrentlyInCreation (对应步骤①) ,经过(步骤②)创建bean对应的factory(只是针对这个实例的factory),  
   通过AutowiredAnnotationBeanPostProcessor 处理依赖的实例, 经过(步骤③)处理依赖,回到getBean, getInstance的时候通过singletonsCurrentlyInCreation  
   判断A实例已经再创建过程中,通过 singletonFactories Map 查到A对应的factory.

## aop


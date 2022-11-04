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

## aop


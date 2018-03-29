package cn.hycun.spring.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

public class ElasticConfigurationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        String scan = env.getProperty("spring.extend.elastic.scan");
        if (scan != null) {
//            applicationContext.addBeanFactoryPostProcessor(scanner);
//            applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
//            applicationContext.getBeanFactory().registerSingleton("annotationBean", scanner);
        }
    }
}

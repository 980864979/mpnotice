package com.tangdi.production.mpnotice.context;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext
  implements ApplicationContextAware
{
  private static final Logger log = LoggerFactory.getLogger(SpringContext.class);
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext)
    throws BeansException
  {
    applicationContext = applicationContext;
    log.info("ApplicationContext registed");
  }

  public static ApplicationContext getApplicationContext()
  {
    return applicationContext;
  }

  public static <T> T getBean(String name, Class<T> clazz)
  {
    checkApplicationContext();
    return applicationContext.getBean(name, clazz);
  }

  public static <T> T getBean(String name)
  {
    checkApplicationContext();
    return (T) applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> clazz)
  {
    checkApplicationContext();
    Map beanMaps = applicationContext.getBeansOfType(clazz);
    if ((beanMaps != null) && (!beanMaps.isEmpty())) {
      return (T) beanMaps.values().iterator().next();
    }
    return null;
  }

  private static void checkApplicationContext()
  {
    if (applicationContext == null) {
    	applicationContext = SpringContext.getApplicationContext();
      /*String eString = "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil";
      IllegalStateException exception = new IllegalStateException(eString);
      log.error(eString, exception);
      throw exception;*/
    }
  }
}
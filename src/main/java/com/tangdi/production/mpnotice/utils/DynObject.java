package com.tangdi.production.mpnotice.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tangdi.production.tdcomm.util.HasPrototype;

public class DynObject
  implements Cloneable
{
  protected Map<Class, Object> properties;
  protected Map<Class, Map> mapProperties;
  protected Map<Class, List> listProperties;
  protected DynObject prototype;

  public DynObject(Map<Class, Object> properties, Map<Class, Map> mapProperties, Map<Class, List> listProperties)
  {
    this.properties = properties;
    this.mapProperties = mapProperties;
    this.listProperties = listProperties;
  }

  public DynObject() {
    this.properties = Maps.newHashMap();
    this.mapProperties = Maps.newHashMap();
    this.listProperties = Maps.newHashMap();
  }

  public DynObject(DynObject parent) {
    this();
    setPrototype(parent);
  }

  public <T> T setInstance(Class<T> c, T obj)
  {
    return (T) this.properties.put(c, obj);
  }

  public <T> void setHasPrototypeInstance(Class<T> c, T obj) {
    if ((this.prototype != null) && (this.prototype.getInstance(c) != null))
      ((HasPrototype)obj).setPrototype((HasPrototype)this.prototype.getInstance(c));
    this.properties.put(c, obj);
  }

  public <T> T getInstance(Class<T> c) {
    Object ret = this.properties.get(c);
    if ((ret == null) && (this.prototype != null)) {
      ret = this.prototype.getInstance(c);
    }
    return (T) ret;
  }

  public <T> T getInstance(Class<T> c, Object key) {
    Map map = (Map)this.mapProperties.get(c);

    if ((map != null) && (map.containsKey(key))) {
      return (T) map.get(key);
    }
    if (this.prototype != null) {
      return this.prototype.getInstance(c, key);
    }
    return null;
  }

  public Object getInstance(String key) {
    return getInstance(Object.class, key);
  }

  public <T> void addInstance(Class<T> c, T obj) {
    List list = (List)this.listProperties.get(c);
    if (list == null) {
      list = Lists.newArrayList();
      this.listProperties.put(c, list);
    }
    list.add(obj);
  }

  public <T> List<T> getListInstance(Class<T> c) {
    List list = (List)this.listProperties.get(c);
    if ((list == null) && (this.prototype != null))
      list = this.prototype.getListInstance(c);
    return list;
  }

  public <T> void setListInstance(Class<T> c, List<T> list) {
    this.listProperties.put(c, list);
  }

  protected <T> void removeListInstance(Class<T> c) {
    this.listProperties.remove(c);
  }

  public <T> void putInstance(Class<T> c, Object key, T obj) {
    Map map = (Map)this.mapProperties.get(c);
    if (map == null) {
      map = Maps.newHashMap();
      this.mapProperties.put(c, map);
    }
    map.put(key, obj);
  }

  public void putInstance(String key, Object obj) {
    putInstance(Object.class, key, obj);
  }

  public <T> Map<Object, T> getMapInstance(Class<T> c) {
    return (Map)this.mapProperties.get(c);
  }

  public <T> void setMapInstance(Class<T> c, Map<?, T> map) {
    this.mapProperties.put(c, map);
  }

  public <T> T removeInstance(Class<T> c) {
    return (T) this.properties.remove(c);
  }

  public Map<Class, Object> getProperties()
  {
    return this.properties;
  }

  public Map<Class, Map> getMapProperties() {
    return this.mapProperties;
  }

  public DynObject getPrototype() {
    return this.prototype;
  }

  public void setPrototype(DynObject prototype) {
    this.prototype = prototype;
  }

  public DynObject clone() throws CloneNotSupportedException
  {
    DynObject ret = (DynObject)super.clone();
    ret.properties = ((Map)((HashMap)this.properties).clone());
    ret.mapProperties = Maps.newHashMap();
    for (Map.Entry entry : this.mapProperties.entrySet())
      ret.mapProperties.put((Class)entry.getKey(), (Map)((HashMap)entry.getValue()).clone());
    ret.listProperties = Maps.newHashMap();
    for (Map.Entry entry : this.listProperties.entrySet())
      ret.listProperties.put((Class)entry.getKey(), (List)((ArrayList)entry.getValue()).clone());
    return ret;
  }
}
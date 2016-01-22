package com.tangdi.production.mpnotice.utils;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.collect.Maps;

public class StackedDynObject extends DynObject
{
  private Map<Class, Stack> stacks = Maps.newHashMap();

  public StackedDynObject()
  {
  }

  public StackedDynObject(DynObject parent)
  {
    setPrototype(parent);
  }

  public StackedDynObject(Map<Class, Object> properties, Map<Class, Map> mapProperties, Map<Class, List> listProperties)
  {
    super(properties, mapProperties, listProperties);
  }

  public <T> void pushInstance(Class<T> type, T value)
  {
    Stack v = (Stack)this.stacks.get(type);
    if (v == null) {
      v = new Stack();
      this.stacks.put(type, v);
    }

    v.push(value);
  }

  public <T> T popInstance(Class<T> type) {
    Stack v = (Stack)this.stacks.get(type);
    return (T) v.pop();
  }

  public <T> T peekInstance(Class<T> type) {
    Stack v = (Stack)this.stacks.get(type);
    return (T) ((v == null) || (v.empty()) ? null : v.peek());
  }

  public <T> Stack<T> getStack(Class<T> type) {
    return (Stack)this.stacks.get(type);
  }

  public <T> void removeStack(Class<T> type) {
    this.stacks.remove(type);
  }

  public StackedDynObject clone() throws CloneNotSupportedException
  {
    StackedDynObject ret = (StackedDynObject)super.clone();
    ret.stacks = Maps.newHashMap();
    for (Map.Entry entry : this.stacks.entrySet()) {
      ret.stacks.put((Class)entry.getKey(), (Stack)((Stack)entry.getValue()).clone());
    }
    return ret;
  }
}
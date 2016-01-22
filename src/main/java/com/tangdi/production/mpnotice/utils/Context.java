package com.tangdi.production.mpnotice.utils;

import java.io.Closeable;
import java.io.Flushable;
import java.util.List;
import java.util.Stack;

import com.google.common.base.Preconditions;

public class Context
{
  private static final ThreadLocal<DynObject> instances = new ThreadLocal();

  public static void enter(DynObject ctx) {
    Preconditions.checkState(instances.get() == null, "不允许重复进入Context!");
    instances.set(new StackedDynObject(ctx));
  }

  public static void enter() {
    Preconditions.checkState(instances.get() == null, "不允许重复进入Context!");
    instances.set(new StackedDynObject());
  }

  public static void exit() {
    Preconditions.checkState(instances.get() != null, "没有Context!");
    try {
      flush();
      close();
    } finally {
      instances.remove();
    }
  }

  public static void flush() {
    Stack<Flushable> flushables = getStack(Flushable.class);
    if (flushables == null)
      return;
    for (Flushable f : flushables)
      try {
        f.flush();
      }
      catch (Throwable e) {
        e.printStackTrace();
      }
  }

  private static void close() {
    Stack<Closeable> flushables = getStack(Closeable.class);
    if (flushables == null)
      return;
    for (Closeable f : flushables)
      try {
        f.close();
      }
      catch (Throwable e) {
        e.printStackTrace();
      }
  }

  public static <T> T getInstance(Class<T> type) {
    return ((DynObject)instances.get()).getInstance(type);
  }

  public static <T> T getInstance(Class<T> c, Object key) {
    return ((DynObject)instances.get()).getInstance(c, key);
  }

  public static Object getInstance(String key) {
    return ((DynObject)instances.get()).getInstance(key);
  }

  public static <T> T setInstance(Class<T> type, T value) {
    return ((DynObject)instances.get()).setInstance(type, value);
  }

  public static <T> void setHasPrototypeInstance(Class<T> c, T obj) {
    ((DynObject)instances.get()).setHasPrototypeInstance(c, obj);
  }

  public static <T> void putInstance(Class<T> c, Object key, T obj) {
    ((DynObject)instances.get()).putInstance(c, key, obj);
  }

  public static <T> void seed(Class<T> type, T value) {
    setInstance(type, value);
  }

  public static <T> void pushInstance(Class<T> type, T value)
  {
    DynObject obj = (DynObject)instances.get();
    if ((obj instanceof StackedDynObject)) {
      ((StackedDynObject)obj).pushInstance(type, value);
    }
    else
      Preconditions.checkState(instances.get() != null, "Context不支持Stack!");
  }

  public static <T> T popInstance(Class<T> type)
  {
    DynObject obj = (DynObject)instances.get();
    if ((obj instanceof StackedDynObject)) {
      return ((StackedDynObject)obj).popInstance(type);
    }

    Preconditions.checkState(instances.get() != null, "Context不支持Stack!");
    return null;
  }

  public static <T> T peekInstance(Class<T> type)
  {
    DynObject obj = (DynObject)instances.get();
    if ((obj instanceof StackedDynObject)) {
      return ((StackedDynObject)obj).peekInstance(type);
    }

    Preconditions.checkState(instances.get() != null, "Context不支持Stack!");
    return null;
  }

  public static <T> Stack<T> getStack(Class<T> type)
  {
    DynObject obj = (DynObject)instances.get();
    if ((obj instanceof StackedDynObject)) {
      return ((StackedDynObject)obj).getStack(type);
    }

    Preconditions.checkState(instances.get() != null, "Context不支持Stack!");
    return null;
  }

  public static <T> void addInstance(Class<T> c, T obj)
  {
    ((DynObject)instances.get()).addInstance(c, obj);
  }

  public static <T> List<T> getListInstance(Class<T> c) {
    return ((DynObject)instances.get()).getListInstance(c);
  }

  public static <T> T removeInstance(Class<T> c) {
    return ((DynObject)instances.get()).removeInstance(c);
  }

  public static DynObject getPrototype() {
    return ((DynObject)instances.get()).getPrototype();
  }

  public static void setPrototype(DynObject p) {
    ((DynObject)instances.get()).setPrototype(p);
  }

  public static DynObject createCopy() {
    DynObject old = (DynObject)instances.get();
    try {
      return old.clone(); } catch (CloneNotSupportedException e) {
    }
    return null;
  }

}
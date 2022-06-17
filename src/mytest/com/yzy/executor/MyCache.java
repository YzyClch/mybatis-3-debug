package com.yzy.executor;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.type.Alias;


@Alias("MY")
public class MyCache implements Cache {
  String id;
  Cache delegate;


  public MyCache(Cache cache){
    this.delegate = cache;
    this.id=cache.getId();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void putObject(Object key, Object value) {

  }

  @Override
  public Object getObject(Object key) {
    return null;
  }

  @Override
  public Object removeObject(Object key) {
    return null;
  }

  @Override
  public void clear() {

  }

  @Override
  public int getSize() {
    return 0;
  }
}

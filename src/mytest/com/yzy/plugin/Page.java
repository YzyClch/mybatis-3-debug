package com.yzy.plugin;


import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Page <T>{

  protected List<T> records = Collections.emptyList();

  /**
   * 总数
   */
  protected long total = 0;
  /**
   * 每页显示条数，默认 10
   */
  protected long size = 10;

  /**
   * 当前页
   */
  protected long current = 1;



  Page(int current,int size){
    this.current=current;
    this.size=size;
  }




}

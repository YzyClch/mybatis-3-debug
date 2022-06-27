package com.yzy.domain;

import java.io.Serializable;

public class User implements Serializable {

  Integer id;
  String name;

  public void doSomeThing(){
    System.out.println("xx");
  }

  public boolean getB(){
    return true;
  }


  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}

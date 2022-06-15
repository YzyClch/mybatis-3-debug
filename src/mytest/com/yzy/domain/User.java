package com.yzy.domain;

import java.io.Serializable;

public class User implements Serializable {

  Integer id;
  String name;

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}

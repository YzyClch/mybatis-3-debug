package com.yzy.ognl;

import com.yzy.domain.User;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.junit.Test;

public class OgnlTest {






  @Test
  public void ognlTest(){

    User u =new User();
    u.setId(100);
    ExpressionEvaluator expressionEvaluator=new ExpressionEvaluator();
    boolean b = expressionEvaluator.evaluateBoolean("id==100", u);
    System.out.println(b);
    boolean b1 = expressionEvaluator.evaluateBoolean("getB==true", u);
    System.out.println(b1);


  }



}

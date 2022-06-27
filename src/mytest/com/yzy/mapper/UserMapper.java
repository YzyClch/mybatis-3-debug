package com.yzy.mapper;

import com.yzy.domain.User;
import com.yzy.param.QueryParam;
import com.yzy.plugin.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {



  List<User> selectUserList(@Param("ParamAnnotationValue") QueryParam param);

  Boolean updateUser(@Param("ParamAnnotationValue") QueryParam param);


  @Select("select * from user where id =#{id} and name =#{name } ")
  List<User> selectUser(List<Integer>listParam,@Param("param1")Integer id,@Param("param2") String name);

  @Select("select * from user")
  List<User> selectUser2(List<Integer>listParam);


  List<User> findUserListByParam(@Param("id")Integer id,@Param("name") String name);


  @Select("select * from user where id =#{id} and name =#{name } ")
  Page<User> pageTest(Page page,@Param("id")Integer id,@Param("name") String name);
}

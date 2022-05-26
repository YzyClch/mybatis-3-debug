package com.yzy.mapper;

import com.yzy.domain.User;
import com.yzy.param.QueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {


  List<User> selectUserList(@Param("ParamAnnotationValue") QueryParam param);

}

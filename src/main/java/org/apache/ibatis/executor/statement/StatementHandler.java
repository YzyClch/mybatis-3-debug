/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

/**
 *
 * JDBC处理器
 * 使用数据库的Statement（PreparedStatement）进行操作
 *
 * @author Clinton Begin
 */
public interface StatementHandler {

  // 创建操作数据库用的 Statement 对象，可能是 PreparedStatement
  Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException;

  /**
   * 预处理参数，对sql参数进行设置
   * @param statement
   * @throws SQLException
   */
  void parameterize(Statement statement)throws SQLException;

  /**
   * 调用 statement的addBatch()
   * @param statement
   * @throws SQLException
   */
  void batch(Statement statement)throws SQLException;
  int update(Statement statement)throws SQLException;
  <E> List<E> query(Statement statement, ResultHandler resultHandler)throws SQLException;
  <E> Cursor<E> queryCursor(Statement statement)throws SQLException;

  /**
   * 获取动态sql
   * @return
   */
  BoundSql getBoundSql();

  /**
   * 获取参数处理器
   * @return
   */
  ParameterHandler getParameterHandler();

}

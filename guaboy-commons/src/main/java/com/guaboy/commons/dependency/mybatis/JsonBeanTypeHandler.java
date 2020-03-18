package com.guaboy.commons.dependency.mybatis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.guaboy.commons.utils.StringUtil;


/**
 * Mybatis自定义TypeHandler类, 处理JSON字段与JAVA对象互相转换<br>
 * 使用时直接派生一个空的子类指定泛型类型即可<br>
 * 依赖mybatis-3.4.6以上、FastJson
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class JsonBeanTypeHandler<T> extends BaseTypeHandler<T> {
	private Class<T> clazz;
	
	public JsonBeanTypeHandler() {
		clazz = getTClass();
	}
	
	@SuppressWarnings("unchecked")
	public Class<T>  getTClass() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		Class<T> cls = (Class<T>) params[0];
		return cls;
	}
	

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		String json = JSON.toJSONString(parameter, SerializerFeature.WriteDateUseDateFormat);
		ps.setString(i, json);
	}

	
	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String json = rs.getString(columnName);
		return parseJson2Bean(json);
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String json = rs.getString(columnIndex);
		return parseJson2Bean(json);
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String json = cs.getString(columnIndex);
		return parseJson2Bean(json);
	}
	
	private T parseJson2Bean(String json) {
		if(StringUtil.isEmpty(json)) {
			return null;
		}
		try {
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}

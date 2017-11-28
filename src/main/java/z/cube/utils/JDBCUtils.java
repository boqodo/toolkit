/* ==================================================================   
 * Created Mar 19, 2015 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;


public class JDBCUtils {
	private static final Map<String,String> sqlMapper=new ConcurrentHashMap<String,String>();
	
	public static final String MAPKEY_SQL="MAPKEY_SQL";
	public static final String MAPVALUE_VALUES="MAPVALUE_VALUES";
	
	
	@SuppressWarnings({  "rawtypes" })
	public static List<Object> loadInsertValues(Object obj){
		Class clazz=obj.getClass();
   		Field[] fs=clazz.getDeclaredFields();
   		List<Object> columnValues=new ArrayList<Object>(24);
   		for(Field f : fs){
   			Transient tr=f.getAnnotation(Transient.class);
   			if(tr!=null) {continue;}
   			Method getter=null;
   			
   			getter=ReflectionUtils.findMethod(clazz, "get"+StringUtils.capitalize(f.getName()), new Class[]{});
   			if(getter==null){
   				getter=ReflectionUtils.findMethod(clazz, "get"+f.getName(), new Class[]{});
   			}
   			
   			tr=getter.getAnnotation(Transient.class);
   			if(tr!=null) {continue;}
   			
   			Object value=ReflectionUtils.invokeMethod(getter, obj, new Class[]{});
   			columnValues.add(value);
   		}
   		return columnValues;
	}
	
	@SuppressWarnings("rawtypes")
	public static String buildSelectSql(Class clazz,String userPrefix,String dblinkSuffix){
		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
   		String select="select * from %s a where 1=1 ";
   		select+=" order by a.%s desc";
   		return String.format(select,tableName,findIdColumn(clazz));
	}
	
	@SuppressWarnings("rawtypes")
	public static String buildGetSql(Class clazz,String userPrefix,String dblinkSuffix){
		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
		String select="select a.* from %s a where a.%s=? ";
		return String.format(select,tableName,findIdColumn(clazz));
	}
	@SuppressWarnings("rawtypes")
	public static String buildExistsSql(Class clazz,String userPrefix,String dblinkSuffix){
		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
   		String select="select count(*) from %s a where a.%s=? ";
   		return String.format(select,tableName,findIdColumn(clazz));
	}

	@SuppressWarnings("rawtypes")
	public static String tableNameWrapper(Class clazz, String userPrefix, String dblinkSuffix) {
		String tableName=AnnotationUtils.findAnnotation(clazz, Table.class).name();
   		return tableNameWrapper(tableName,userPrefix,dblinkSuffix);
	}
	public static String tableNameWrapper(String tableName, String userPrefix, String dblinkSuffix) {
		if(StringUtils.isNotBlank(userPrefix)){
   			tableName=userPrefix+"."+tableName;
   		}
   		if(StringUtils.isNotBlank(dblinkSuffix)){
   			tableName=tableName+"@"+dblinkSuffix;
   		}
		return tableName;
	}
	
	@SuppressWarnings("rawtypes")
	public static String findIdField(Class clazz){
		//查询@ID的字段
		//TODO:需要处理@Id在get方法上的情况
		String key="IDFIELD_"+clazz.getSimpleName();
		String idField="";
		if(sqlMapper.containsKey(key)){
			idField=sqlMapper.get(key);
		}else{
			Field[] fs=clazz.getDeclaredFields();
			for(Field f : fs){
				Id id=f.getAnnotation(Id.class);
				if(id!=null){
					idField=f.getName();
					sqlMapper.put(key, idField);
					break;
				}
			}
		}
		return idField;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String findIdColumn(Class clazz){
		//TODO:需要处理@Id在get方法上的情况
		String key="IDCOLUMN_"+clazz.getSimpleName();
		String idColumn="";
		if(sqlMapper.containsKey(key)){
			idColumn=sqlMapper.get(key);
		}else{
			Field[] fs=clazz.getDeclaredFields();
			for(Field f : fs){
				Id id=f.getAnnotation(Id.class);
				if(id!=null){
					Column column=f.getAnnotation(Column.class);
		   			if(column!=null){
		   				idColumn=column.name();
		   			}else{
		   				idColumn=f.getName();
		   			}
					sqlMapper.put(key, idColumn);
					break;
				}
			}
		}
		return idColumn;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String buildDeleteSql(Class clazz,String userPrefix,String dblinkSuffix){
		//查询@ID的字段
		String idColumn=findIdColumn(clazz);
		String delete="delete from  %s a where a.%s=? ";
   		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
   		delete=String.format(delete,tableName,idColumn);
   		return delete;
	}
	@SuppressWarnings("rawtypes")
	@Deprecated
	public static String buildUpdateSql(Class clazz,String userPrefix,String dblinkSuffix){
		String sql="";
		String key="UPDATESET_"+clazz.getSimpleName();
		if(sqlMapper.containsKey(key)){
			sql=sqlMapper.get(key);
		}else{
			//UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
			Field[] fs=clazz.getDeclaredFields();
	   		List<String> columnNames=new ArrayList<String>(fs.length);
	   		for(Field f : fs){
	   			Transient tr=f.getAnnotation(Transient.class);
	   			if(tr!=null) {continue;}
	   			Method getter=null;
	   			
	   			getter=ReflectionUtils.findMethod(clazz, "get"+StringUtils.capitalize(f.getName()), new Class[]{});
	   			if(getter==null){
	   				getter=ReflectionUtils.findMethod(clazz, "get"+f.getName(), new Class[]{});
	   			}
	   			tr=getter.getAnnotation(Transient.class);
	   			if(tr!=null) {continue;}
	   			
	   			Column column=f.getAnnotation(Column.class);
	   			if(column!=null){
	   				columnNames.add(column.name());
	   			}else{
	   				columnNames.add(f.getName());
	   			}
	   		}
	   		String update="update %s set %s=?  where %s=?";
	   		String columns=StringUtils.join(columnNames,"=? , ");
	   		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
	   		sql= String.format(update,tableName,columns,findIdColumn(clazz));
	   		sqlMapper.put(key, sql);
	   		
		}
		
		return sql;
	}
	@SuppressWarnings("rawtypes")
	public static Map<String,Object> buildUpdateSqlWithValues(Object entity,String userPrefix,String dblinkSuffix){
		//UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
		Class clazz=entity.getClass();
		Field[] fs=clazz.getDeclaredFields();
   		List<String> columnNames=new ArrayList<String>(fs.length);
   		List<Object> columnValues=new ArrayList<Object>(fs.length);
   		for(Field f : fs){
   			Transient tr=f.getAnnotation(Transient.class);
   			if(tr!=null) {continue;}
   			Method getter=null;
   			
   			getter=ReflectionUtils.findMethod(clazz, "get"+StringUtils.capitalize(f.getName()), new Class[]{});
   			if(getter==null){
   				getter=ReflectionUtils.findMethod(clazz, "get"+f.getName(), new Class[]{});
   			}
   			tr=getter.getAnnotation(Transient.class);
   			if(tr!=null) {continue;}
   			
   			String columnName="";
   			Column column=f.getAnnotation(Column.class);
   			if(column!=null){
   				columnName=column.name();
   			}else{
   				columnName=f.getName();
   			}
   			Object value=ReflectionUtils.invokeMethod(getter, entity, new Class[]{});
   			
   			Class r=getter.getReturnType();
   			//如果该方法的返回值为Date类型，则需要进行null处理
   			if(Date.class.equals(r)&&value==null){
   				columnNames.add(columnName+"= null");
   			}else{
   				columnNames.add(columnName+" =? ");
   	   			columnValues.add(value);
   			}
   		}
   		String update="update %s set %s  where %s=?";
   		String columns=StringUtils.join(columnNames," , ");
   		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
   		String sql= String.format(update,tableName,columns,findIdColumn(clazz));
   		Map<String,Object> map=new HashMap<String, Object>(2);
   		map.put(MAPKEY_SQL,sql);
   		map.put(MAPVALUE_VALUES, columnValues);
   		return map;
	}
	@SuppressWarnings("rawtypes")
	public static Map<String,Object> buildInsertSqlWithValues(Object entity,String userPrefix,String dblinkSuffix){
		Class clazz=entity.getClass();
		Field[] fs=clazz.getDeclaredFields();
   		List<String> columnNames=new ArrayList<String>(fs.length);
   		List<String> columnChars=new ArrayList<String>(fs.length);
   		List<Object> columnValues=new ArrayList<Object>(fs.length);
   		for(Field f : fs){
   			Transient tr=f.getAnnotation(Transient.class);
   			if(tr!=null) {continue;}
   			Method getter=null;
   			
   			getter=ReflectionUtils.findMethod(clazz, "get"+StringUtils.capitalize(f.getName()), new Class[]{});
   			if(getter==null){
   				getter=ReflectionUtils.findMethod(clazz, "get"+f.getName(), new Class[]{});
   			}
   			tr=getter.getAnnotation(Transient.class);
   			if(tr!=null) {continue;}
   			
   			String columnName="";
   			Column column=f.getAnnotation(Column.class);
   			if(column!=null){
   				columnName=column.name();
   			}else{
   				columnName=f.getName();
   			}
   			
   			Object value=ReflectionUtils.invokeMethod(getter, entity, new Class[]{});
   			
   			Class r=getter.getReturnType();
   			//如果该方法的返回值为Date类型，则需要进行null处理
   			if(Date.class.equals(r)&&value==null){
   				columnChars.add("null");
   			}else{
   				columnChars.add("?");
   				columnValues.add(value);
   			}
   			columnNames.add(columnName);
   		}
   		String insert="insert into %s (%s) values (%s)";
   		String columns=StringUtils.join(columnNames,",");
   		String chars=StringUtils.join(columnChars,",");
   		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
   		String sql= String.format(insert,tableName,columns,chars);
   		Map<String,Object> map=new HashMap<String, Object>(2);
   		map.put(MAPKEY_SQL,sql);
   		map.put(MAPVALUE_VALUES, columnValues);
   		return map;
	}
	
	@SuppressWarnings({"rawtypes" })
	@Deprecated
	public static String buildInsertSql(Class clazz,String userPrefix,String dblinkSuffix){
		String sql="";
		String key="INSERTINTO_"+clazz.getSimpleName();
		if(sqlMapper.containsKey(key)){
			sql=sqlMapper.get(key);
		}else{
			Field[] fs=clazz.getDeclaredFields();
	   		List<String> columnNames=new ArrayList<String>(fs.length);
	   		List<String> columnChars=new ArrayList<String>(fs.length);
	   		for(Field f : fs){
	   			Transient tr=f.getAnnotation(Transient.class);
	   			if(tr!=null) {continue;}
	   			Method getter=null;
	   			
	   			getter=ReflectionUtils.findMethod(clazz, "get"+StringUtils.capitalize(f.getName()), new Class[]{});
	   			if(getter==null){
	   				getter=ReflectionUtils.findMethod(clazz, "get"+f.getName(), new Class[]{});
	   			}
	   			tr=getter.getAnnotation(Transient.class);
	   			if(tr!=null) {continue;}
	   			
	   			Column column=f.getAnnotation(Column.class);
	   			if(column!=null){
	   				columnNames.add(column.name());
	   			}else{
	   				columnNames.add(f.getName());
	   			}
	   			columnChars.add("?");
	   		}
	   		String insert="insert into %s (%s) values (%s)";
	   		String columns=StringUtils.join(columnNames,",");
	   		String chars=StringUtils.join(columnChars,",");
	   		String tableName = tableNameWrapper(clazz, userPrefix, dblinkSuffix);
	   		sql= String.format(insert,tableName,columns,chars);
	   		sqlMapper.put(key, sql);
		}
		return sql;
   	}
}

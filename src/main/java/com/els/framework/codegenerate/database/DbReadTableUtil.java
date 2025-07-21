package com.els.framework.codegenerate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.els.framework.codegenerate.config.ResourceConfig;
import com.els.framework.codegenerate.database.util.ParameterUtil;
import com.els.framework.codegenerate.generate.pojo.ColumnVo;
import com.els.framework.codegenerate.generate.util.SqlStringConverter;

public class DbReadTableUtil {
	private static final Logger logger = LoggerFactory.getLogger(DbReadTableUtil.class);
	private static Connection connection;
	private static Statement statement;

	public static void main(String[] args) throws SQLException {
		try {

			List<ColumnVo> localList = queryTableColumns("demo");

			for (ColumnVo localColumnVo : localList) {

				System.out.println(localColumnVo.getFieldName());
			}

		} catch (Exception localException1) {

			localException1.printStackTrace();
		}

		new DbReadTableUtil();
		System.out.println(ArrayUtils.toString(queryOnlineGenerateTable()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> queryOnlineGenerateTable() throws SQLException {

		String str1 = null;

		ArrayList localArrayList = new ArrayList(0);
		try {

			Class.forName(ResourceConfig.dbDriver);

			connection = DriverManager.getConnection(ResourceConfig.dbUrl, ResourceConfig.dbUserName,
					ResourceConfig.dbPwd);

			statement = connection.createStatement(1005, 1007);

			if ("mysql".equals(ResourceConfig.dbType)) {

				str1 = String.format(
						"select distinct table_name from information_schema.columns where table_schema = '%s'",
						ResourceConfig.dbDatabase);
			}

			if ("oracle".equals(ResourceConfig.dbType)) {

				str1 = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
			}

			if ("postgresql".equals(ResourceConfig.dbType)) {

				str1 = "select tablename from pg_tables where schemaname='public'";
			}

			if ("sqlserver".equals(ResourceConfig.dbType)) {

				str1 = "select distinct c.name as  table_name from sys.objects c where c.type = 'U' ";
			}

			ResultSet localResultSet = statement.executeQuery(str1);

			while (localResultSet.next()) {

				String str2 = localResultSet.getString(1);

				localArrayList.add(str2);
			}
		} catch (Exception localException) {

			localException.printStackTrace();
		} finally {
			try {

				if (statement != null) {

					statement.close();

					statement = null;

					System.gc();
				}

				if (connection != null) {

					connection.close();

					connection = null;

					System.gc();
				}
			} catch (SQLException localSQLException3) {

				throw localSQLException3;
			}
		}

		return localArrayList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ColumnVo> queryTableColumns(String tableName) throws Exception {

		String str = null;

		ArrayList localArrayList1 = new ArrayList();
		ColumnVo localColumnVo1;
		try {

			Class.forName(ResourceConfig.dbDriver);

			connection = DriverManager.getConnection(ResourceConfig.dbUrl, ResourceConfig.dbUserName,
					ResourceConfig.dbPwd);

			statement = connection.createStatement(1005, 1007);

			if ("mysql".equals(ResourceConfig.dbType)) {

				str = MessageFormat.format(
						"select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order By ORDINAL_POSITION",
						new Object[] { SqlStringConverter.appendQuotes(tableName),
								SqlStringConverter.appendQuotes(ResourceConfig.dbDatabase) });
			}

			if ("oracle".equals(ResourceConfig.dbType)) {

				str = MessageFormat.format(
						" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
						new Object[] { SqlStringConverter.appendQuotes(tableName.toUpperCase()) });
			}

			if ("postgresql".equals(ResourceConfig.dbType)) {

				str = MessageFormat.format(
						"select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name",
						new Object[] { SqlStringConverter.appendQuotes(tableName),
								SqlStringConverter.appendQuotes(tableName) });
			}

			if ("sqlserver".equals(ResourceConfig.dbType)) {

				str = MessageFormat.format(
						"select distinct cast(getParamsInsertValueSql.name as varchar(50)) column_name,  cast(connection.name as varchar(50)) data_type,  cast(tableColumnName2CodeFieldName.value as NVARCHAR(200)) comment,  cast(ColumnProperty(getParamsInsertValueSql.object_id,getParamsInsertValueSql.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(getParamsInsertValueSql.object_id,getParamsInsertValueSql.Name,'''Scale''') as int) num_scale,  getParamsInsertValueSql.max_length,  (case when getParamsInsertValueSql.is_nullable=1 then '''y''' else '''FieldRequiredNum''' end) nullable,column_id   from sys.columns getParamsInsertValueSql left join sys.types connection on getParamsInsertValueSql.user_type_id=connection.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) statement on getParamsInsertValueSql.object_id=statement.object_id left join sys.extended_properties tableColumnName2CodeFieldName on tableColumnName2CodeFieldName.major_id=statement.object_id and tableColumnName2CodeFieldName.minor_id=getParamsInsertValueSql.column_id and tableColumnName2CodeFieldName.class=1 where statement.name={0} order by getParamsInsertValueSql.column_id",
						new Object[] { SqlStringConverter.appendQuotes(tableName) });
			}

			ResultSet localResultSet = statement.executeQuery(str);

			localResultSet.last();

			int i = localResultSet.getRow();

			int j = i;

			if (j > 0) {

				localColumnVo1 = new ColumnVo();

				if (ResourceConfig.bFieldConvert)
					localColumnVo1
							.setFieldName(tableColumnName2CodeFieldName(localResultSet.getString(1).toLowerCase()));
				else {

					localColumnVo1.setFieldName(localResultSet.getString(1).toLowerCase());
				}

				localColumnVo1.setFieldDbName(localResultSet.getString(1).toLowerCase());

				localColumnVo1.setFieldType(getFiledCodeType(localResultSet.getString(2).toLowerCase(),
						localColumnVo1.getPrecision(), localColumnVo1.getScale()));

				localColumnVo1.setFieldDbType(
						tableColumnName2CodeFieldName(localResultSet.getString(2).toLowerCase()).toUpperCase());

				localColumnVo1.setPrecision(localResultSet.getString(4));

				localColumnVo1.setScale(localResultSet.getString(5));

				localColumnVo1.setCharmaxLength(localResultSet.getString(6));

				localColumnVo1.setNullable((localResultSet.getString(7).substring(0, 1)));

				initTableField2CodeField(localColumnVo1);

				localColumnVo1.setFiledComment(
						StringUtils.isBlank(localResultSet.getString(3)) ? localColumnVo1.getFieldName()
								: localResultSet.getString(3));

				logger.debug("columnt.getFieldName() -------------" + localColumnVo1.getFieldName());

				String[] arrayOfString = new String[0];

				if (ResourceConfig.pageFilterFields != null) {

					arrayOfString = ResourceConfig.pageFilterFields.toLowerCase().split(",");
				}

				if ((!ResourceConfig.dbTableId.equals(localColumnVo1.getFieldName())) && (!ParameterUtil
						.checkParamExists(localColumnVo1.getFieldDbName().toLowerCase(), arrayOfString))) {

					localArrayList1.add(localColumnVo1);
				}

				while (localResultSet.previous()) {

					ColumnVo localColumnVo2 = new ColumnVo();

					if (ResourceConfig.bFieldConvert)
						localColumnVo2
								.setFieldName(tableColumnName2CodeFieldName(localResultSet.getString(1).toLowerCase()));
					else {

						localColumnVo2.setFieldName(localResultSet.getString(1).toLowerCase());
					}

					localColumnVo2.setFieldDbName(localResultSet.getString(1).toLowerCase());

					logger.debug("columnt.getFieldName() -------------" + localColumnVo2.getFieldName());

					if ((!ResourceConfig.dbTableId.equals(localColumnVo2.getFieldName())) && (!ParameterUtil
							.checkParamExists(localColumnVo2.getFieldDbName().toLowerCase(), arrayOfString))) {

						localColumnVo2
								.setFieldType(getFiledCodeType(localResultSet.getString(2).toLowerCase(),
										localColumnVo2.getPrecision(), localColumnVo2.getScale()));

						localColumnVo2.setFieldDbType(
								tableColumnName2CodeFieldName(localResultSet.getString(2).toLowerCase()).toUpperCase());

						logger.debug("-----po.setFieldType------------" + localColumnVo2.getFieldType());

						localColumnVo2.setPrecision(localResultSet.getString(4));

						localColumnVo2.setScale(localResultSet.getString(5));

						localColumnVo2.setCharmaxLength(localResultSet.getString(6));

						localColumnVo2.setNullable((localResultSet.getString(7).substring(0, 1)));

						initTableField2CodeField(localColumnVo2);

						localColumnVo2.setFiledComment(
								StringUtils.isBlank(localResultSet.getString(3)) ? localColumnVo2.getFieldName()
										: localResultSet.getString(3));

						localArrayList1.add(localColumnVo2);
					}
				}
			} else {
				throw new Exception("该表不存在或者表中没有字段");
			}

			logger.debug("读取表成功");
		} catch (ClassNotFoundException localClassNotFoundException) {

			throw localClassNotFoundException;
		} catch (SQLException localSQLException2) {

			throw localSQLException2;
		} finally {
			try {

				if (statement != null) {

					statement.close();

					statement = null;

					System.gc();
				}

				if (connection != null) {

					connection.close();

					connection = null;

					System.gc();
				}
			} catch (SQLException localSQLException3) {

				throw localSQLException3;
			}

		}

		ArrayList localArrayList2 = new ArrayList();

		for (int j = localArrayList1.size() - 1; j >= 0; j--) {

			localColumnVo1 = (ColumnVo) localArrayList1.get(j);

			localArrayList2.add(localColumnVo1);
		}

		return localArrayList2;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ColumnVo> getTableFields(String tableName) throws Exception {

		ResultSet localResultSet = null;

		String str = null;

		ArrayList localArrayList1 = new ArrayList();
		ColumnVo localColumnVo1;
		try {

			Class.forName(ResourceConfig.dbDriver);

			connection = DriverManager.getConnection(ResourceConfig.dbUrl, ResourceConfig.dbUserName,
					ResourceConfig.dbPwd);

			statement = connection.createStatement(1005, 1007);

			if (ResourceConfig.dbType.equals("mysql")) {

				str = MessageFormat.format(
						"select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1}  order By ORDINAL_POSITION",
						new Object[] { SqlStringConverter.appendQuotes(tableName),
								SqlStringConverter.appendQuotes(ResourceConfig.dbDatabase) });
			}

			if (ResourceConfig.dbType.equals("oracle")) {

				str = MessageFormat.format(
						" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
						new Object[] { SqlStringConverter.appendQuotes(tableName.toUpperCase()) });
			}

			if (ResourceConfig.dbType.equals("postgresql")) {

				str = MessageFormat.format(
						"select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name",
						new Object[] { SqlStringConverter.appendQuotes(tableName),
								SqlStringConverter.appendQuotes(tableName) });
			}

			if (ResourceConfig.dbType.equals("sqlserver")) {

				str = MessageFormat.format(
						"select distinct cast(getParamsInsertValueSql.name as varchar(50)) column_name,  cast(connection.name as varchar(50)) data_type,  cast(tableColumnName2CodeFieldName.value as NVARCHAR(200)) comment,  cast(ColumnProperty(getParamsInsertValueSql.object_id,getParamsInsertValueSql.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(getParamsInsertValueSql.object_id,getParamsInsertValueSql.Name,'''Scale''') as int) num_scale,  getParamsInsertValueSql.max_length,  (case when getParamsInsertValueSql.is_nullable=1 then '''y''' else '''FieldRequiredNum''' end) nullable,column_id   from sys.columns getParamsInsertValueSql left join sys.types connection on getParamsInsertValueSql.user_type_id=connection.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) statement on getParamsInsertValueSql.object_id=statement.object_id left join sys.extended_properties tableColumnName2CodeFieldName on tableColumnName2CodeFieldName.major_id=statement.object_id and tableColumnName2CodeFieldName.minor_id=getParamsInsertValueSql.column_id and tableColumnName2CodeFieldName.class=1 where statement.name={0} order by getParamsInsertValueSql.column_id",
						new Object[] { SqlStringConverter.appendQuotes(tableName) });
			}

			localResultSet = statement.executeQuery(str);

			localResultSet.last();

			int i = localResultSet.getRow();

			int j = i;

			if (j > 0) {

				localColumnVo1 = new ColumnVo();

				if (ResourceConfig.bFieldConvert)
					localColumnVo1
							.setFieldName(tableColumnName2CodeFieldName(localResultSet.getString(1).toLowerCase()));
				else {

					localColumnVo1.setFieldName(localResultSet.getString(1).toLowerCase());
				}

				localColumnVo1.setFieldDbName(localResultSet.getString(1).toLowerCase());

				localColumnVo1.setPrecision((localResultSet.getString(4)));

				localColumnVo1.setScale((localResultSet.getString(5)));

				localColumnVo1.setCharmaxLength((localResultSet.getString(6)));

				localColumnVo1.setNullable((localResultSet.getString(7).substring(0, 1)));

				localColumnVo1.setFieldType(getFiledCodeType(localResultSet.getString(2).toLowerCase(),
						localColumnVo1.getPrecision(), localColumnVo1.getScale()));

				localColumnVo1.setFieldDbType(
						tableColumnName2CodeFieldName(localResultSet.getString(2).toLowerCase()).toUpperCase());

				initTableField2CodeField(localColumnVo1);

				localColumnVo1.setFiledComment(
						StringUtils.isBlank(localResultSet.getString(3)) ? localColumnVo1.getFieldName()
								: localResultSet.getString(3));

				logger.debug("columnt.getFieldName() -------------" + localColumnVo1.getFieldName());

				localArrayList1.add(localColumnVo1);

				while (localResultSet.previous()) {

					ColumnVo localColumnVo2 = new ColumnVo();

					if (ResourceConfig.bFieldConvert)
						localColumnVo2
								.setFieldName(tableColumnName2CodeFieldName(localResultSet.getString(1).toLowerCase()));
					else {

						localColumnVo2.setFieldName(localResultSet.getString(1).toLowerCase());
					}

					localColumnVo2.setFieldDbName(localResultSet.getString(1).toLowerCase());

					localColumnVo2.setPrecision((localResultSet.getString(4)));

					localColumnVo2.setScale((localResultSet.getString(5)));

					localColumnVo2.setCharmaxLength((localResultSet.getString(6)));

					localColumnVo2.setNullable((localResultSet.getString(7).substring(0, 1)));

					localColumnVo2.setFieldType(getFiledCodeType(localResultSet.getString(2).toLowerCase(),
							localColumnVo2.getPrecision(), localColumnVo2.getScale()));

					localColumnVo2.setFieldDbType(
							tableColumnName2CodeFieldName(localResultSet.getString(2).toLowerCase()).toUpperCase());

					initTableField2CodeField(localColumnVo2);

					localColumnVo2.setFiledComment(
							StringUtils.isBlank(localResultSet.getString(3)) ? localColumnVo2.getFieldName()
									: localResultSet.getString(3));

					localArrayList1.add(localColumnVo2);
				}
			} else {

				throw new Exception("该表不存在或者表中没有字段");
			}

			logger.debug("读取表成功");
		} catch (ClassNotFoundException localClassNotFoundException) {

			throw localClassNotFoundException;
		} catch (SQLException localSQLException2) {

			throw localSQLException2;
		} finally {
			try {

				if (statement != null) {

					statement.close();

					statement = null;

					System.gc();
				}

				if (connection != null) {

					connection.close();

					connection = null;

					System.gc();
				}
			} catch (SQLException localSQLException3) {

				throw localSQLException3;
			}

		}

		ArrayList localArrayList2 = new ArrayList();

		for (int j = localArrayList1.size() - 1; j >= 0; j--) {

			localColumnVo1 = (ColumnVo) localArrayList1.get(j);

			localArrayList2.add(localColumnVo1);
		}

		return localArrayList2;
	}

	public static boolean checkTableExists(String tableName) {

		String str = null;
		try {

			logger.debug("数据库驱动: " + ResourceConfig.dbDriver);

			Class.forName(ResourceConfig.dbDriver);

			connection = DriverManager.getConnection(ResourceConfig.url(), ResourceConfig.username(),
					ResourceConfig.password());

			statement = connection.createStatement(1005, 1007);

			if (ResourceConfig.dbType.equals("mysql")) {

				str = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '"
						+ tableName + "' and table_schema = '" + ResourceConfig.dbDatabase
						+ "'  order By ORDINAL_POSITION";
			}

			if (ResourceConfig.dbType.equals("oracle")) {

				str = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '"
						+ tableName.toUpperCase() + "'";
			}

			if (ResourceConfig.dbType.equals("postgresql")) {

				str = MessageFormat.format(
						"select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name",
						new Object[] { SqlStringConverter.appendQuotes(tableName),
								SqlStringConverter.appendQuotes(tableName) });
			}

			if (ResourceConfig.dbType.equals("sqlserver")) {

				str = MessageFormat.format(
						"select distinct cast(getParamsInsertValueSql.name as varchar(50)) column_name,  cast(connection.name as varchar(50)) data_type,  cast(tableColumnName2CodeFieldName.value as NVARCHAR(200)) comment,  cast(ColumnProperty(getParamsInsertValueSql.object_id,getParamsInsertValueSql.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(getParamsInsertValueSql.object_id,getParamsInsertValueSql.Name,'''Scale''') as int) num_scale,  getParamsInsertValueSql.max_length,  (case when getParamsInsertValueSql.is_nullable=1 then '''y''' else '''FieldRequiredNum''' end) nullable,column_id   from sys.columns getParamsInsertValueSql left join sys.types connection on getParamsInsertValueSql.user_type_id=connection.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) statement on getParamsInsertValueSql.object_id=statement.object_id left join sys.extended_properties tableColumnName2CodeFieldName on tableColumnName2CodeFieldName.major_id=statement.object_id and tableColumnName2CodeFieldName.minor_id=getParamsInsertValueSql.column_id and tableColumnName2CodeFieldName.class=1 where statement.name={0} order by getParamsInsertValueSql.column_id",
						new Object[] { SqlStringConverter.appendQuotes(tableName) });
			}

			ResultSet localResultSet = statement.executeQuery(str);

			localResultSet.last();

			int i = localResultSet.getRow();

			if (i > 0) {

				return true;
			}
		} catch (Exception localException) {

			localException.printStackTrace();

			return false;
		}

		return false;
	}

	public static String tableColumnName2CodeFieldName(String paramString) {

		String[] arrayOfString = paramString.split("_");

		if ("is".equals(arrayOfString[0])) {
			arrayOfString = paramString.substring(3).split("_");
		}

		paramString = "";

		int i = 0;
		for (int j = arrayOfString.length; i < j; i++) {

			if (i > 0) {

				String str = arrayOfString[i].toLowerCase();

				str = str.substring(0, 1).toUpperCase() + str/* 448 */.substring(1, str/* 448 */.length());

				paramString = paramString + str;
			} else {

				paramString = paramString + arrayOfString[i].toLowerCase();
			}
		}

		return paramString;
	}

	public static String tableColumnName2CodeField(String paramString) {

		String[] arrayOfString = paramString.split("_");

		paramString = "";

		int i = 0;
		for (int j = arrayOfString.length; i < j; i++) {

			if (i > 0) {

				String str = arrayOfString[i].toLowerCase();

				str = str.substring(0, 1).toUpperCase() + str/* 470 */.substring(1, str/* 470 */.length());

				paramString = paramString + str;
			} else {

				paramString = paramString + arrayOfString[i].toLowerCase();
			}
		}

		paramString = paramString.substring(0, 1).toUpperCase() + paramString.substring(1);

		return paramString;
	}

	private static void initTableField2CodeField(ColumnVo paramColumnVo) {

		String fieldType = paramColumnVo.getFieldType();

		String scale = paramColumnVo.getScale();

		paramColumnVo.setClassType("inputxt");

		if ("N".equals(paramColumnVo.getNullable())) {

			paramColumnVo.setOptionType("*");
		}

		if (("datetime".equals(fieldType)) || (fieldType.contains("time"))) {

			paramColumnVo.setClassType("easyui-datetimebox");
		}

		else if ("date".equals(fieldType)) {

			paramColumnVo.setClassType("easyui-datebox");
		}

		else if (fieldType.contains("int")) {

			paramColumnVo.setOptionType("FieldRequiredNum");
		}

		else if ("number".equals(fieldType)) {

			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {

				paramColumnVo.setOptionType("tableColumnName2CodeField");
			}
		}

		else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {

			paramColumnVo.setOptionType("tableColumnName2CodeField");
		}

		else if ("numeric".equals(fieldType)) {

			paramColumnVo.setOptionType("tableColumnName2CodeField");
		}
	}

	public static String getFiledCodeType(String fieldType, String precision, String scale) {

		if (fieldType.contains("char")) {

			fieldType = "java.lang.String";
		}

		else if (fieldType.contains("int")) {

			fieldType = "java.lang.Integer";
		}

		else if (fieldType.contains("float")) {

			fieldType = "java.lang.Float";
		} else if (fieldType.contains("double")) {

			fieldType = "java.lang.Double";
		} else if (fieldType.contains("number")) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {

				fieldType = "java.math.BigDecimal";
			} else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 10)) {

				fieldType = "java.lang.Long";
			} else {

				fieldType = "java.lang.Integer";
			}
		} else if (fieldType.contains("decimal")) {

			fieldType = "java.math.BigDecimal";
		} else if (fieldType.contains("date")) {

			fieldType = "java.util.Date";
		} else if (fieldType.contains("time")) {
			fieldType = "java.util.Date";
		} else if (fieldType.contains("blob")) {

			fieldType = "byte[]";
		} else if (fieldType.contains("clob")) {

			fieldType = "java.sql.Clob";
		} else if (fieldType.contains("numeric")) {

			fieldType = "java.math.BigDecimal";
		} else {
			fieldType = "java.lang.Object";
		}
		return fieldType;
	}
}

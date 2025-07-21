package com.els.framework.codegenerate.config;

import java.util.ResourceBundle;

/**
 * 资源文件
 */
public class ResourceConfig {
	private static final ResourceBundle GENERATE_DB_CONFIG = ResourceBundle.getBundle("els/els_database");
	private static final ResourceBundle GENERATE_CODE_CONFIG = ResourceBundle.getBundle("els/els_config");

	public static String dbType = "mysql";

	public static String dbDriver = "com.mysql.jdbc.Driver";

	public static String dbUrl = "jdbc:mysql://localhost:3306/els?useUnicode=true&characterEncoding=UTF-8";

	public static String dbUserName;

	public static String dbPwd;

	public static String dbDatabase = "els";

	public static String projectPath = "checkTableExists:/workspace/els";

	public static String basePackage = "com.els";

	public static String sourceRootPackage = "";
	// getDbTableId.replace(".", "/");
	public static String webRootPackage = "";// getPageFilterFields.replace(".", "/");

	public static String templatePath = "/els/code-template/";

	public static boolean bFieldConvert = true;
	public static String dbTableId;
	public static String FieldRequiredNum = "4";

	public static String pageSearchFieldNum = "3";
	public static String pageFilterFields;
	public static String FieldRowNum = "1";

	public static final String getDriveName() {

		return GENERATE_DB_CONFIG.getString("diver_name");
	}

	public static final String url() {

		return GENERATE_DB_CONFIG.getString("url");
	}

	public static final String username() {

		return GENERATE_DB_CONFIG.getString("username");
	}

	public static final String password() {

		return GENERATE_DB_CONFIG.getString("password");
	}

	public static final String getDatabaseName() {

		return GENERATE_DB_CONFIG.getString("database_name");
	}

	public static final boolean isFieldConvert() {

		String str = GENERATE_CODE_CONFIG.getString("db_filed_convert");

		if ("false".equals(str.toString())) {
			return false;
		}

		return true;
	}

	private static String getBussiPackage() {

		return GENERATE_CODE_CONFIG.getString("bussi_package");
	}

	private static String getTemplatePath() {

		return GENERATE_CODE_CONFIG.getString("templatepath");
	}

	public static final String getSourceRootPackage() {

		return GENERATE_CODE_CONFIG.getString("source_root_package");
	}

	public static final String getWebRootPackage() {

		return GENERATE_CODE_CONFIG.getString("webroot_package");
	}

	public static final String getDbTableId() {

		return GENERATE_CODE_CONFIG.getString("db_table_id");
	}

	public static final String getPageFilterFields() {

		return GENERATE_CODE_CONFIG.getString("page_filter_fields");
	}

	public static final String getPageSearchFieldNum() {

		return GENERATE_CODE_CONFIG.getString("page_search_filed_num");
	}

	public static final String getPageFiledRequiredNum() {

		return GENERATE_CODE_CONFIG.getString("page_field_required_num");
	}

	public static String getProjectPath() {

		String str = GENERATE_CODE_CONFIG.getString("project_path");

		if ((str != null) && (!"".equals(str))) {

			projectPath = str;
		}

		return projectPath;
	}

	public static void a(String paramString) {

		projectPath = paramString;
	}

	public static void b(String paramString) {

		templatePath = paramString;
	}

	static {

		dbDriver = getDriveName();

		dbUrl = url();

		dbUserName = username();

		dbPwd = password();

		dbDatabase = getDatabaseName();

		sourceRootPackage = getSourceRootPackage();

		webRootPackage = getWebRootPackage();

		basePackage = getBussiPackage();

		templatePath = getTemplatePath();

		projectPath = getProjectPath();

		dbTableId = getDbTableId();

		bFieldConvert = isFieldConvert();

		pageFilterFields = getPageFilterFields();

		pageSearchFieldNum = getPageSearchFieldNum();

		if ((dbUrl.indexOf("mysql") >= 0) || (dbUrl.indexOf("MYSQL") >= 0)) {

			dbType = "mysql";
		} else if ((dbUrl.indexOf("oracle") >= 0) || (dbUrl.indexOf("ORACLE") >= 0)) {

			dbType = "oracle";
		} else if ((dbUrl.indexOf("postgresql") >= 0) || (dbUrl.indexOf("POSTGRESQL") >= 0)) {

			dbType = "postgresql";
		} else if ((dbUrl.indexOf("sqlserver") >= 0) || (dbUrl.indexOf("sqlserver") >= 0)) {

			dbType = "sqlserver";
		}
	}
}

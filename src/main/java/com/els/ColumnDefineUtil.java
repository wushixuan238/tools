package com.els;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.els.framework.codegenerate.database.DbReadTableUtil;
import com.els.framework.codegenerate.generate.pojo.ColumnVo;

/**
 * 生成自定义列脚本工具类
 * 
 * @author wyssss
 * @date 2020/03/13
 */
public class ColumnDefineUtil {

	public static final String HIDDEN_FIELDS = "id,elsAccount,deleted";

	/**
	 * 生成自定义列脚本
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String tableCode = "costAccessList";
		String dbTableName = "purchase_cost_access_head";
		generateSql(tableCode, dbTableName);
	}

	/**
	 * 根据参数生成脚本
	 * 
	 * @param tableCode
	 * @param dbTableName
	 * @throws Exception
	 */
	private static void generateSql(String tableCode, String dbTableName) throws Exception {
		// 获取表格的列
		List<ColumnVo> columnList = DbReadTableUtil.getTableFields(dbTableName);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String curr = format.format(new Date());
		int index = 1;
		for (ColumnVo column : columnList) {
			// 过滤不需要显示的信息
			if (!HIDDEN_FIELDS.contains(column.getFieldName())) {
				StringBuilder sb = new StringBuilder(
						"insert into els_column_define(els_account, id, table_code, column_code, column_name, is_hidden, align_type, sort_order, create_by, create_time, update_by, update_time,column_width) value(");
				sb.append("'100000'").append(",'");
				sb.append(IdWorker.getIdStr()).append("','");
				sb.append(tableCode).append("','");
				sb.append(column.getFieldName()).append("','");
				sb.append(column.getFiledComment()).append("',");
				sb.append("0,");
				sb.append("'center',");
				sb.append(index).append(",");
				sb.append("'admin','");
				sb.append(curr).append("',");
				sb.append("'admin','");
				sb.append(curr).append("','100');");
				System.out.println(sb.toString());
				index ++;
			}
		}
	}

}

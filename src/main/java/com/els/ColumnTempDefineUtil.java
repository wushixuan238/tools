package com.els;

import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.els.framework.codegenerate.database.DbReadTableUtil;
import com.els.framework.codegenerate.generate.pojo.ColumnVo;
import com.els.tools.StringUtil;

/**
 * 生成自定义列脚本工具类
 * 
 * @author wyssss
 * @date 2020/03/13
 */
public class ColumnTempDefineUtil {

	public static final String HIDDEN_FIELDS = "id,elsAccount,deleted";

	/**
	 * 生成模版头行脚本
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String headID = "1925808930059649025";//

		String dbTableName = "purchase_bidding_head";
		String dbTableItemName = "purchase_bidding_item";
//		generateSqlHead(headID, dbTableName);
		generateSqlItem(headID, dbTableItemName,"elsOaFlowConfigItemList");
	}

	/**
	 * 根据参数生成脚本(TempHead)
	 * 
	 * @param tableCode
	 * @param dbTableName
	 * @throws Exception
	 */
	private static void generateSqlHead(String tableCode, String dbTableName) throws Exception {
		// 获取表格的列
		List<ColumnVo> columnList = DbReadTableUtil.getTableFields(dbTableName);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curr = format.format(new Date());
		int sort = 1;
		for (int i = 0; i < columnList.size(); i++) {
			ColumnVo column = columnList.get(i);
			// 过滤不需要显示的信息
			if (!HIDDEN_FIELDS.contains(column.getFieldName())) {
				StringBuilder sb = new StringBuilder(
						"INSERT INTO els_template_config_head(id, els_account, head_id, group_code, field_name, field_type, field_label, field_label_i18n_key, placeholder, placeholder_i18n_key, default_value, sort_order, data_format, dict_code, is_purchase_show, is_purchase_edit, is_sale_show, is_sale_edit, is_sys, is_required, regex, alert_msg, alert_msg_i18n_key, bind_function, extend, create_by, create_time, update_by, update_time, is_deleted, fbk1, fbk2, fbk3, fbk4, fbk5, fbk6, fbk7, fbk8, fbk9, fbk10, fbk11, fbk12, fbk13, fbk14, fbk15, fbk16, fbk17, fbk18, fbk19, fbk20, extend_field) VALUES (");
				sb.append("'"+ IdWorker.getIdStr() +"'").append(",'");
				sb.append("100000'").append(",'");
				sb.append(tableCode).append("','baseForm','");
				sb.append(column.getFieldName()).append("','");
				sb.append("input'").append(",'");
				sb.append(column.getFiledComment()).append("',");
				sb.append("NULL, NULL, NULL, NULL, ");
				sb.append(sort+i);
				sb.append(",");
				sb.append(" NULL, NULL, '1', '1', '0', '0', '1', '0', NULL, NULL, NULL, NULL, NULL, 'admin', '");
				sb.append(curr).append("',");
				sb.append("'admin','");
				sb.append(curr).append("',");
				sb.append("'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);");
				System.out.println(sb.toString());
			}
		}
	}
	/**
	 * 根据参数生成脚本(TempItem)
	 *
	 * @param tableCode
	 * @param dbTableName
	 * @throws Exception
	 */
	private static void generateSqlItem(String tableCode, String dbTableName,String group) throws Exception {
		// 获取表格的列
		List<ColumnVo> columnList = DbReadTableUtil.getTableFields(dbTableName);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String curr = format.format(new Date());
		int index = 1;
		for (ColumnVo column : columnList) {
			// 过滤不需要显示的信息
			if (!HIDDEN_FIELDS.contains(column.getFieldName())) {
				StringBuilder sb = new StringBuilder(
						"INSERT INTO els_template_config_item(id, els_account, head_id,group_code, field_name, field_type, field_label, field_label_i18n_key, column_width, column_align, default_value, sort_order, data_format, dict_code, is_purchase_show, is_purchase_edit, is_sale_show, is_sale_edit, is_sys, is_required, regex, alert_msg, alert_msg_i18n_key, bind_function, extend, create_by, create_time, update_by, update_time, is_deleted, fbk1, fbk2, fbk3, fbk4, fbk5, fbk6, fbk7, fbk8, fbk9, fbk10, fbk11, fbk12, fbk13, fbk14, fbk15, fbk16, fbk17, fbk18, fbk19, fbk20, extend_field) VALUES (");
				sb.append("'"+ IdWorker.getIdStr() +"'").append(",'");
				sb.append("100000'").append(",'");
				sb.append(tableCode).append("','")
						.append(group).append("','");

				sb.append(column.getFieldName()).append("','");
				sb.append("input'").append(",'");
				sb.append(column.getFiledComment()).append("',");
				sb.append("NULL, NULL, NULL, NULL, ");
				sb.append(index);
				sb.append(",");
				sb.append(" NULL, NULL, '0', '0', '0', '0', '1', '0', NULL, NULL, NULL, NULL, NULL, 'admin', '");
				sb.append(curr).append("',");
				sb.append("'admin','");
				sb.append(curr).append("',");
				sb.append("'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);");
				System.out.println(sb.toString());
				index ++;
			}
		}
	}

}

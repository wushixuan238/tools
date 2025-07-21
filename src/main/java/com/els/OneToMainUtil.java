package com.els;

import com.els.framework.codegenerate.generate.impl.CodeGenerateOneToMany;
import com.els.framework.codegenerate.generate.pojo.onetomany.MainTableVo;
import com.els.framework.codegenerate.generate.pojo.onetomany.SubTableVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器入口【一对多】
 * 
 * @Author wyssss
 * 
 */
public class OneToMainUtil {

    /**
     * 一对多(父子表)数据模型，生成方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        // 第一步：设置主表配置
        MainTableVo mainTable = new MainTableVo();
        mainTable.setTableName("purchase_test_head");// 表名
        mainTable.setEntityName("PurchaseTestHead"); // 实体名
        mainTable.setEntityPackage("test"); // 包名
        mainTable.setFtlDescription("采购测试头"); // 描述

        // 第二步：设置子表集合配置
        List<SubTableVo> subTables = new ArrayList<SubTableVo>();
        // [1].子表一
        SubTableVo po = new SubTableVo();
        po.setTableName("purchase_test_item");// 表
        po.setEntityName("PurchaseTestItem"); // 实体名
        po.setEntityPackage("order1"); // 包名
        po.setFtlDescription("采购测试行"); // 描述
        po.setForeignKeys(new String[] {"head_id"});
        subTables.add(po);



        // [2].子表二
//        SubTableVo po2 = new SubTableVo();
//        po2.setTableName("sale_order_delivery_plan"); // 表名
//       po2.setEntityName("SaleOrderDeliveryPlan"); // 实体名
//        po2.setEntityPackage("order"); // 包名
//        po2.setFtlDescription("采购订单交货计划"); // 描述
//        po2.setForeignKeys(new String[] {"head_id"});
//        subTables.add(po2);
////        
//       SubTableVo po3 = new SubTableVo();
//        po3.setTableName("sale_order_item_bom"); // 表名
//        po3.setEntityName("SaleOrderItemBom"); // 实体名
//        po3.setEntityPackage("order"); // 包名
//        po3.setFtlDescription("采购订单行BOM"); // 描述
//        po3.setForeignKeys(new String[] {"head_id"});
//        subTables.add(po3);
////        
//        SubTableVo po4 = new SubTableVo();
//        po4.setTableName("sale_attachment"); // 表名
//        po4.setEntityName("SaleAttachment"); // 实体名
//        po4.setEntityPackage("order"); // 包名
//        po4.setFtlDescription("采购订单附件"); // 描述
//        po4.setForeignKeys(new String[] {"head_id"});
//        subTables.add(po4);
        
        mainTable.setSubTables(subTables);
        // 第三步：一对多(父子表)数据模型,代码生成
        try {
            new CodeGenerateOneToMany(mainTable, subTables).generateCodeFile(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

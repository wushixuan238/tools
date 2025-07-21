package com.els.framework.codegenerate.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.els.framework.codegenerate.database.DbReadTableUtil;
import com.els.framework.codegenerate.generate.impl.CodeGenerateOne;
import com.els.framework.codegenerate.generate.pojo.TableVo;

public class YSSWindow extends JFrame {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static String packageName = "test";
    private static String entityName = "TestEntity";
    private static String tableName = "t00_company";
    private static String discribe = "分公司";
    private static Integer rowCount = Integer.valueOf(1);
    private static String idType = "uuid";
    private static String sequenceCode = "";

    String[] a = {"uuid", "identity", "sequence"};

    public YSSWindow() {
        JPanel localJPanel = new JPanel();
        setContentPane(localJPanel);
        localJPanel.setLayout(new GridLayout(14, 2));

        JLabel localJLabel1 = new JLabel("提示:");
        final JLabel localJLabel2 = new JLabel();
        JLabel localJLabel3 = new JLabel("包名（小写）：");
        final JTextField packageNameField = new JTextField();
        JLabel localJLabel4 = new JLabel("实体类名（首字母大写）：");
        final JTextField entityNameField = new JTextField();
        JLabel localJLabel5 = new JLabel("表名：");
        final JTextField tableNameField = new JTextField(20);

        JLabel localJLabel8 = new JLabel("功能描述：");
        final JTextField localJTextField5 = new JTextField();

        JCheckBox localJCheckBox1 = new JCheckBox("Control");
        localJCheckBox1.setSelected(true);

        JCheckBox localJCheckBox3 = new JCheckBox("Service");
        localJCheckBox3.setSelected(true);
        JCheckBox localJCheckBox4 = new JCheckBox("Mapper.xml");
        localJCheckBox4.setSelected(true);
        JCheckBox localJCheckBox5 = new JCheckBox("Dao");
        localJCheckBox5.setSelected(true);
        JCheckBox localJCheckBox6 = new JCheckBox("Entity");
        localJCheckBox6.setSelected(true);

        JCheckBox localJCheckBoxVO = new JCheckBox("VO");
        localJCheckBoxVO.setSelected(true);

        localJPanel.add(localJLabel1);
        localJPanel.add(localJLabel2);
        localJPanel.add(localJLabel3);
        localJPanel.add(packageNameField);
        localJPanel.add(localJLabel4);
        localJPanel.add(entityNameField);
        localJPanel.add(localJLabel5);
        localJPanel.add(tableNameField);

        localJPanel.add(localJLabel8);
        localJPanel.add(localJTextField5);

        localJPanel.add(localJCheckBox1);

        localJPanel.add(localJCheckBox3);
        localJPanel.add(localJCheckBox4);
        localJPanel.add(localJCheckBox5);
        localJPanel.add(localJCheckBox6);
        localJPanel.add(localJCheckBoxVO);

        JButton localJButton1 = new JButton("生成");
        localJButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(packageNameField.getText())) {
                    YSSWindow.packageName = packageNameField.getText();
                } else {
                    localJLabel2.setForeground(Color.red);
                    localJLabel2.setText("包名不能为空！");
                    return;
                }
                if (!"".equals(entityNameField.getText())) {
                    YSSWindow.entityName = (entityNameField.getText());
                } else {
                    localJLabel2.setForeground(Color.red);
                    localJLabel2.setText("实体类名不能为空！");
                    return;
                }
                if (!"".equals(localJTextField5.getText())) {
                    YSSWindow.discribe = (localJTextField5.getText());
                } else {
                    localJLabel2.setForeground(Color.red);
                    localJLabel2.setText("描述不能为空！");
                    return;
                }
                if (!"".equals(tableNameField.getText())) {
                    YSSWindow.tableName = (tableNameField.getText());
                } else {
                    localJLabel2.setForeground(Color.red);
                    localJLabel2.setText("表名不能为空！");
                    return;
                }

                try {
                    boolean bool = DbReadTableUtil.checkTableExists(YSSWindow.tableName);
                    if (bool) {
                        TableVo localTableVo = new TableVo();
                        localTableVo.setTableName(YSSWindow.tableName);
                        localTableVo.setPrimaryKeyPolicy(YSSWindow.idType);
                        localTableVo.setEntityPackage(YSSWindow.packageName);
                        localTableVo.setEntityName(YSSWindow.entityName);
                        localTableVo.setFieldRowNum(YSSWindow.rowCount);
                        localTableVo.setSequenceCode(YSSWindow.sequenceCode);
                        localTableVo.setFtlDescription(YSSWindow.discribe);
                        new CodeGenerateOne(localTableVo).generateCodeFile(false);
                        localJLabel2.setForeground(Color.red);
                        localJLabel2.setText("成功生成增删改查->功能：" + YSSWindow.entityName);
                    } else {
                        localJLabel2.setForeground(Color.red);
                        localJLabel2.setText("表[" + YSSWindow.tableName + "] 在数据库中，不存在");
                        System.err.println(
                            " ERROR ：   表 [ " + YSSWindow.tableName + " ] 在数据库中，不存在 ！请确认数据源配置是否配置正确、表名是否填写正确~ ");
                    }
                } catch (Exception localException1) {
                    localJLabel2.setForeground(Color.red);
                    localJLabel2.setText(localException1.getMessage());
                }
            }
        });
        JButton localJButton2 = new JButton("退出");
        localJButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YSSWindow.this.dispose();
                System.exit(0);
            }
        });
        localJPanel.add(localJButton1);
        localJPanel.add(localJButton2);

        setTitle("代码生成器[单表模型]");
        setVisible(true);
        setDefaultCloseOperation(3);
        setSize(new Dimension(600, 400));
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    public static void main(String[] args) {
        try {
            new YSSWindow().pack();
        } catch (Exception localException) {
            System.out.println(localException.getMessage());
        }
    }
}

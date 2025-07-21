package com.els;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取文件夹下所有java文件, 拿到java文件中的i18nKey 和 defaultValue
 * 并生成对应SQL
 */
public class I18nUtil {

    private static String path = "D:\\Work\\QQT\\V5_SRM\\srm-parent";

    public static void main(String[] args) {
        File file = new File(path);
        List<String> pathList = new ArrayList<>();
        getJavaFileName(file, pathList);
        for (String path : pathList){
            readFile(path);
        }
    }

    private static void getJavaFileName(File file, List<String> pathList){
        if (file.exists()){
            File files[] = file.listFiles();
            if (files != null){
                for (File f : files){
                    String path = f.getPath();
                    if (f.isDirectory()){
                        File directory = new File(path);
                        getJavaFileName(directory, pathList);
                    }else {
                        if (path.endsWith(".java")){
                            pathList.add(path);
                        }
                    }
                }
            }
        }
    }

    private static void readFile(String path){
        File file = new File(path);
        if (file.exists()){
            try(FileInputStream inputStream = new FileInputStream(path);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)){
                String line;
                String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                while ((line = reader.readLine()) != null){
                    if (line.contains("I18nUtil.translate(")){
                        String result = line.replaceAll("\"", "");
                        String[] array = result.split("I18nUtil.translate");
                        for (int i = 1; i < array.length; i++){
                            String str = array[i];
                            int leftCount = appearNumber(str);
                            int rightIndex = getCharacterPosition(str, leftCount);
                            str = str.substring(1, rightIndex);
                            int index = str.indexOf(",");
                            String i18nKey = str.substring(0, index);
                            String defaultValue = str.substring(index + 1);
                            StringBuilder sb = new StringBuilder("insert into els_i18n(id, els_account, i18n_key, language, i18n_value, create_by, create_time, update_by, update_time) value(");
                            sb.append("'").append(IdWorker.getIdStr()).append("',");
                            sb.append("'100000'").append(",'");
                            sb.append(i18nKey.trim()).append("','");
                            sb.append("zh_CN").append("','");
                            sb.append(defaultValue.trim()).append("',");
                            sb.append("'admin','");
                            sb.append(now).append("',");
                            sb.append("'admin','");
                            sb.append(now).append("');");
                            System.out.println(sb.toString());
                        }
                    }
                }
            }catch (Exception e){

            }
        }
    }

    private static int appearNumber(String str){
        int count = 0;
        Matcher matcher = Pattern.compile("\\(").matcher(str);
        while (matcher.find()){
            count++;
        }
        return count;
    }

    private static int getCharacterPosition(String str, int count){
        Matcher matcher = Pattern.compile("\\)").matcher(str);
        int index = 0;
        while (matcher.find()){
            index++;
            if (index == count){
                break;
            }
        }
        return matcher.start();
    }
}

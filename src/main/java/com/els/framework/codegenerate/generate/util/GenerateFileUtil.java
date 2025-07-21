package com.els.framework.codegenerate.generate.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(GenerateFileUtil.class);

    public static List<String> IGNORE_FILE_SUFFIX = new ArrayList();
    public static List<String> TEMPLATE_FILE_SUFFIX = new ArrayList();
    
	public static List<File> sortFile(File paramFile) throws IOException {

        ArrayList localArrayList = new ArrayList();

        getChildFiles(paramFile, localArrayList);

        Collections.sort(localArrayList, new Comparator<File>() {
            @Override
            public int compare(File paramAnonymousFile1, File paramAnonymousFile2) {
                return paramAnonymousFile1.getAbsolutePath().compareTo(paramAnonymousFile2.getAbsolutePath());
            }
        });

        return localArrayList;
    }

    public static void getChildFiles(File paramFile, List<File> paramList) throws IOException {

        logger.debug("---------dir------------path: " + paramFile.getPath() + " -- isHidden --: " + paramFile.isHidden()
            + " -- isFile --: " + paramFile.isDirectory());

        if ((!paramFile.isHidden()) && (paramFile.isDirectory()) && (!isIgnoreFile(paramFile))) {

            File[] arrayOfFile = paramFile.listFiles();

            for (int i = 0; i < arrayOfFile.length; i++) {

                getChildFiles(arrayOfFile[i], paramList);
            }
        }

        else if ((!isTemplateFile(paramFile)) && (!isIgnoreFile(paramFile))) {

            paramList.add(paramFile);
        }
    }

    public static String getFileAbsolutePath(File paramFile1, File paramFile2) {

        if (paramFile1.equals(paramFile2)) {

            return "";
        }

        if (paramFile1.getParentFile() == null) {

            return paramFile2.getAbsolutePath().substring(paramFile1.getAbsolutePath().length());
        }
        return paramFile2.getAbsolutePath().substring(paramFile1.getAbsolutePath().length() + 1);
    }

    public static boolean isFile(File paramFile) {
        if (paramFile.isDirectory()) {
            return false;
        }
        return isFile(paramFile.getName());
    }

    public static boolean isFile(String fileName) {
        if (StringUtils.isBlank(getFileName(fileName))) {
            return false;
        }
        return true;
    }

    public static String getFileName(String fileFullName) {
        if (fileFullName == null) {
            return null;
        }
        int i = fileFullName.indexOf(".");
        if (i == -1) {
            return "";
        }
        return fileFullName.substring(i + 1);
    }

    public static File getFile(String fileAbsolutePath) {
        if (fileAbsolutePath == null) {
            throw new IllegalArgumentException("file must be not null");
        }
        File localFile = new File(fileAbsolutePath);
        initParentPath(localFile);
        return localFile;
    }

    public static void initParentPath(File paramFile) {
        if (paramFile.getParentFile() != null) {

            paramFile.getParentFile().mkdirs();
        }
    }

    private static boolean isIgnoreFile(File paramFile) {
        for (int i = 0; i < IGNORE_FILE_SUFFIX.size(); i++) {
            if (paramFile.getName().equals(IGNORE_FILE_SUFFIX.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTemplateFile(File paramFile) {
        for (int i = 0; i < TEMPLATE_FILE_SUFFIX.size(); i++) {
            if (paramFile.getName().endsWith((String) TEMPLATE_FILE_SUFFIX.get(i))) {
                return true;
            }
        }
        return false;
    }

    static {
        IGNORE_FILE_SUFFIX.add(".svn");
        IGNORE_FILE_SUFFIX.add("CVS");
        IGNORE_FILE_SUFFIX.add(".cvsignore");
        IGNORE_FILE_SUFFIX.add(".copyarea.db");
        IGNORE_FILE_SUFFIX.add("SCCS");
        IGNORE_FILE_SUFFIX.add("vssver.scc");
        IGNORE_FILE_SUFFIX.add(".DS_Store");
        IGNORE_FILE_SUFFIX.add(".git");
        IGNORE_FILE_SUFFIX.add(".gitignore");
        TEMPLATE_FILE_SUFFIX.add(".ftl");
    }
}

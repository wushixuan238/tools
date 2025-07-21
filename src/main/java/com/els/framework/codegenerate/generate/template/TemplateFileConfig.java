package com.els.framework.codegenerate.generate.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class TemplateFileConfig {
    private static final Logger logger = LoggerFactory.getLogger(TemplateFileConfig.class);
    private String templatePath;
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private List<File> fileList = new ArrayList();


    public TemplateFileConfig(String templatePath) {
        logger.info("----templatePath-----------------" + templatePath);
        this.templatePath = templatePath;
    }

    private void initFileList(File paramFile) {
        array2List(new File[] {paramFile});
    }

    private void array2List(File[] paramArrayOfFile) {
        this.fileList = Arrays.asList(paramArrayOfFile);
    }

    public List<File> getTemplateFileList() {
        String str = getClass().getResource(this.templatePath).getFile();
        str = str.replaceAll("%20", " ");
        logger.debug("-------classpath-------" + str);
        initFileList(new File(str));
        return this.fileList;
    }

    public void setTemplateFiles(List<File> fileList) {
        this.fileList = fileList;
    }

}

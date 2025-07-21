package com.els.framework.codegenerate.generate.impl.framework;

import com.els.framework.codegenerate.config.ResourceConfig;
import com.els.framework.codegenerate.generate.template.TemplateFileConfig;
import com.els.framework.codegenerate.generate.util.FrameworkUtil;
import com.els.framework.codegenerate.generate.util.GenerateFileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class FrameworkFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FrameworkFileUtil.class);

    protected static String ENCODING = "UTF-8";

    protected void generateFile(TemplateFileConfig parama, String paramString, Map<String, Object> paramMap) throws Exception {

        logger.info("--------generate----projectPath--------" + paramString);

        for (int i = 0; i < parama.getTemplateFileList().size(); i++) {

            File localFile = parama.getTemplateFileList().get(i);

            generate(paramString, localFile, paramMap, parama);
        }
    }

    protected void generate(String paramString, File paramFile, Map<String, Object> paramMap, TemplateFileConfig parama)
            throws Exception {

        if (paramFile == null) {
            throw new IllegalStateException("'templateRootDir' must be not null");
        }

        logger.debug("-------------------load template from templateRootDir = '" + paramFile.getAbsolutePath()
                + "' outJavaRootDir:"
                + new File(ResourceConfig.sourceRootPackage.replace(".", File.separator)).getAbsolutePath()
                + "' outWebappRootDir:"
                + new File(ResourceConfig.webRootPackage.replace(".", File.separator)).getAbsolutePath());

        List<?> localList = GenerateFileUtil.sortFile(paramFile);

        logger.debug("----srcFiles----size-----------" + localList.size());

        logger.debug("----srcFiles----list------------" + localList.toString());

        for (int i = 0; i < localList.size(); i++) {

            File localFile = (File) localList.get(i);

            generateToLocal(paramString, paramFile, paramMap, localFile, parama);
        }
    }

    protected void generateToLocal(String paramString, File paramFile1, Map<String, Object> paramMap, File paramFile2,
                                   TemplateFileConfig parama) throws Exception {

        logger.debug("-------templateRootDir--" + paramFile1.getPath());

        logger.debug("-------srcFile--" + paramFile2.getPath());

        String templateFileName = GenerateFileUtil.getFileAbsolutePath(paramFile1, paramFile2);
        try {

            logger.debug("-------templateFile--" + templateFileName);

            String outputFilepath = getOutputFilepath(paramMap, templateFileName, parama);

            logger.debug("-------outputFilepath--" + outputFilepath);
            String str3;
            String str4;

            if (outputFilepath.startsWith("java")) {

                str3 = paramString + File.separator + ResourceConfig.sourceRootPackage.replace(".", File.separator);

                str4 = str3;

                outputFilepath = outputFilepath.substring("java".length());

                outputFilepath = str4 + outputFilepath;

                logger.debug("-------java----outputFilepath--" + outputFilepath);

                generateTpl2Local(templateFileName, outputFilepath, paramMap, parama);

            } else if (outputFilepath.startsWith("webapp")) {

                str3 = paramString + File.separator + ResourceConfig.webRootPackage.replace(".", File.separator);

                str4 = str3;

                outputFilepath = outputFilepath.substring("webapp".length());

                outputFilepath = str4 + outputFilepath;

                logger.debug("-------webapp---outputFilepath---" + outputFilepath);

                generateTpl2Local(templateFileName, outputFilepath, paramMap, parama);
            }
        } catch (Exception localException) {

            localException.printStackTrace();

            logger.error(localException.toString());
        }
    }

    protected void generateTpl2Local(String templateFileName, String outputFilepath,
                                     Map<String, Object> paramMap, TemplateFileConfig parama)
            throws Exception {

        if (outputFilepath.endsWith("i")) {

            outputFilepath = outputFilepath.substring(0, outputFilepath.length() - 1);
        }

        Template localTemplate = getTemplate(templateFileName, parama);

        localTemplate.setOutputEncoding(ENCODING);

        File localFile = GenerateFileUtil.getFile(outputFilepath);

        logger.debug("[generate]\t template:" + templateFileName + " ==> " + outputFilepath);

        FrameworkUtil.write2TargetFile(localTemplate, paramMap, localFile, ENCODING);

        if (isSubTableFile(localFile)) {

            write2Local(localFile, "#segment#");
        }
    }

    protected Template getTemplate(String paramString, TemplateFileConfig parama) throws IOException {

        return FrameworkUtil.getConfiguration(parama.getTemplateFileList(), ENCODING, paramString).getTemplate(paramString);
    }

    protected boolean isSubTableFile(File paramFile) {

        if (paramFile.getName().startsWith("[1-n]")) {

            return true;
        }

        return false;
    }

    @SuppressWarnings("deprecation")
	protected static void write2Local(File paramFile, String subTableFileContentFlag) {

        InputStreamReader localInputStreamReader = null;

        BufferedReader localBufferedReader = null;


        try {

            localInputStreamReader = new InputStreamReader(new FileInputStream(paramFile), "UTF-8");

            localBufferedReader = new BufferedReader(localInputStreamReader);

            int m = 0;

            String readerTmp;
            File templateFile = null;
            while ((readerTmp = localBufferedReader.readLine()) != null) {

                if ((readerTmp.trim().length() > 0) && (readerTmp.startsWith(subTableFileContentFlag))) {

                    String str2 = readerTmp.substring(subTableFileContentFlag.length());

                    String str3 = paramFile.getParentFile().getAbsolutePath();

                    str2 = str3 + File.separator + str2;

                    logger.debug("[generate]\t split file:" + paramFile.getAbsolutePath() + " ==> " + str2);
                    templateFile = new File(str2);

                    //localArrayList.add(localOutputStreamWriter);

                    m = 1;

                } else if (m != 0) {

                    logger.debug("row : " + readerTmp);
                    FileUtils.writeStringToFile(templateFile, readerTmp + "\r\n", true);

                }
            }


            localBufferedReader.close();

            localInputStreamReader.close();

            logger.debug("[generate]\t delete file:" + paramFile.getAbsolutePath());

            deleteFile(paramFile);
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
            logger.error("没找到模板文件：", localFileNotFoundException);
        } catch (IOException localIOException3) {

            localIOException3.printStackTrace();
            logger.error("文件流出来出错：", localIOException3);
        } finally {
            try {

                if (localBufferedReader != null) {

                    localBufferedReader.close();
                }

                if (localInputStreamReader != null) {

                    localInputStreamReader.close();
                }


            } catch (IOException localIOException5) {

                localIOException5.printStackTrace();
                logger.error("文件流出来出错：", localIOException5);
            }
        }
    }

    protected static String getOutputFilepath(Map<String, Object> paramMap,
                                              String templateFileName, TemplateFileConfig parama)
            throws Exception {

        String str1 = templateFileName;

        int i = -1;

        if ((i = templateFileName.indexOf('@')) != -1) {

            str1 = templateFileName.substring(0, i);

            String localObject1 = templateFileName.substring(i + 1);

            Object localObject2 = paramMap.get(localObject1);

            if (localObject2 == null) {

                System.err.println("[not-generate] WARN: test expression is null by key:[" + localObject1
                        + "] on template:[" + templateFileName + "]");

                return null;
            }

            if (!"true".equals(String.valueOf(localObject2))) {

                logger.error("[not-generate]\t test expression '@" + localObject1 + "' is false,template:"
                        + templateFileName);

                return null;
            }
        }

        Object localObject1 = FrameworkUtil.getConfiguration(parama.getTemplateFileList(), ENCODING, "/");
        //用户的账户名称
//        String author = System.getProperty("user.name");
        String author = "lanYing";
        if (StringUtils.isNotBlank(author)) {
        	paramMap.put("author", author);
        } else {
        	paramMap.put("author", "");
        }
        
        str1 = FrameworkUtil.templateFillWithParams(str1, paramMap, (Configuration) localObject1);

        Object localObject2 = str1.substring(str1.lastIndexOf("."));

        String str2 = str1.substring(0, str1.lastIndexOf(".")).replace(".", File.separator);

        str1 = str2 + (String) localObject2;

        return str1;
    }

    protected static boolean deleteFile(File paramFile) {

        boolean bool = false;

        int i = 0;

        while ((!bool) && (i++ < 10)) {

            System.gc();

            bool = paramFile.delete();
        }

        return bool;
    }

    protected static String getPathSuffix(String templatePath, String suffix) {

        int i = 1;

        int j = 1;
        do {

            int k = templatePath.indexOf(suffix) == 0 ? 1 : 0;

            int m = templatePath.lastIndexOf(suffix) + 1 == templatePath.length()
                    ? templatePath.lastIndexOf(suffix) : templatePath.length();

            templatePath = templatePath.substring(k, m);

            i = templatePath.indexOf(suffix) == 0 ? 1 : 0;

            j = templatePath.lastIndexOf(suffix) + 1 == templatePath.length() ? 1 : 0;

        } while ((i != 0) || (j != 0));

        return templatePath;
    }
}
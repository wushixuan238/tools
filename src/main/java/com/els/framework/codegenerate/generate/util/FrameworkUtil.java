package com.els.framework.codegenerate.generate.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FrameworkUtil {
    @SuppressWarnings("deprecation")
	public static Configuration getConfiguration(List<File> paramList, String paramString1, String paramString2) throws IOException {
        Configuration localConfiguration = new Configuration();

        FileTemplateLoader[] arrayOfFileTemplateLoader = new FileTemplateLoader[paramList.size()];
        for (int i = 0; i < paramList.size(); i++) {
            arrayOfFileTemplateLoader[i] = new FileTemplateLoader((File)paramList.get(i));
        }
        MultiTemplateLoader localMultiTemplateLoader = new MultiTemplateLoader(arrayOfFileTemplateLoader);

        localConfiguration.setTemplateLoader(localMultiTemplateLoader);
        localConfiguration.setNumberFormat("###############");
        localConfiguration.setBooleanFormat("true,false");
        localConfiguration.setDefaultEncoding(paramString1);
        return localConfiguration;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getFileName(String paramString1, String paramString2) {
        String[] arrayOfString = getTokenizer(paramString1, "\\/");
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramString2);
        localArrayList.add(File.separator + paramString2);
        String str = "";
        for (int i = 0; i < arrayOfString.length; i++) {
            str = str + File.separator + arrayOfString[i];
            localArrayList.add(str + File.separator + paramString2);
        }
        return localArrayList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] getTokenizer(String paramString1, String paramString2) {
        if (paramString1 == null) {
            return new String[0];
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
        ArrayList localArrayList = new ArrayList();

        while (localStringTokenizer.hasMoreElements()) {
            Object localObject = localStringTokenizer.nextElement();
            localArrayList.add(localObject.toString());
        }
        return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }

    public static String templateFillWithParams(String paramString, Map<String, Object> paramMap, Configuration paramConfiguration) {
        StringWriter localStringWriter = new StringWriter();
        try {
            Template localTemplate =
                new Template("templateString...", new StringReader(paramString), paramConfiguration);
            localTemplate.process(paramMap, localStringWriter);
            return localStringWriter.toString();
        } catch (Exception localException) {
            throw new IllegalStateException("cannot process templateString:" + paramString + " cause:" + localException,
                localException);
        }
    }

    public static void write2TargetFile(Template paramTemplate, Map<String, Object> paramMap, File targetFile, String charsetName)
        throws IOException, TemplateException {
        BufferedWriter localBufferedWriter =
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), charsetName));

        paramMap.put("Format", new SimpleFormat());
        paramTemplate.process(paramMap, localBufferedWriter);
        localBufferedWriter.close();
    }
}

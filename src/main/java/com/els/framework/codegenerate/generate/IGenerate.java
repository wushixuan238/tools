package com.els.framework.codegenerate.generate;

import java.util.Map;

public abstract interface IGenerate {
    public abstract Map<String, Object> configMap()
            throws Exception;

    public abstract void generateCodeFile(boolean custom) throws Exception;

    public abstract void generateCodeFile(String paramString1, String paramString2)
            throws Exception;
}

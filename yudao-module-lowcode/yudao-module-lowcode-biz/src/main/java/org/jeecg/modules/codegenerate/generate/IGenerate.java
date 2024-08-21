package org.jeecg.modules.codegenerate.generate;

import java.util.List;
import java.util.Map;

public interface IGenerate {
    Map<String, Object> a() throws Exception;

    List<String> generateCodeFile(String var1) throws Exception;

    List<String> generateCodeFile(String var1, String var2, String var3) throws Exception;
}

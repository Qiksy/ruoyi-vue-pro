package org.jeecg.modules.online.cgform.enhance.impl.http.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/http/base/CgformEnhanceHttpInter.class */
public interface CgformEnhanceHttpInter {
    public static final Logger logger = LoggerFactory.getLogger(CgformEnhanceHttpInter.class);

    default void execute(String tableName, JSONObject json, OnlCgformEnhanceJava enhance) {
    }

    default void execute(String tableName, List<Map<String, Object>> dataList, OnlCgformEnhanceJava enhance) {
    }

    default Object sendPost(JSONObject params, OnlCgformEnhanceJava enhance) {
        if (enhance == null) {
            return null;
        }
        String cgJavaValue = enhance.getCgJavaValue();
        if (oConvertUtils.isEmpty(cgJavaValue)) {
            return null;
        }
        if (!cgJavaValue.startsWith("http") && !cgJavaValue.startsWith("https")) {
            String baseUrl = RestUtil.getBaseUrl();
            cgJavaValue = cgJavaValue.startsWith("/") ? baseUrl + cgJavaValue : baseUrl + "/" + cgJavaValue;
        }
        ResponseEntity<String> request = RestUtil.request(cgJavaValue, HttpMethod.POST, getHeaders(SpringContextUtils.getHttpServletRequest()), (JSONObject) null, params, String.class);
        if (request.getStatusCode() == HttpStatus.OK) {
            String str = (String) request.getBody();
            if (oConvertUtils.isNotEmpty(str)) {
                try {
                    JSONObject parseObject = JSON.parseObject(str);
                    if (parseObject.getBoolean("success")) {
                        return parseObject.get("result");
                    }
                    throw new JeecgBootException(parseObject.getString("message"));
                } catch (JeecgBootException e) {
                    throw e;
                } catch (Exception e2) {
                    logger.warn("请求Online表单Java增强http接口时转换数据出错：" + e2.getMessage() + "\n body: " + str);
                    throw new JeecgBootException("Online表单Java增强http接口JSON转换失败！");
                }
            }
            return null;
        }
        return null;
    }

    default HttpHeaders getHeaders(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String str = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(str);
            while (headers.hasMoreElements()) {
                httpHeaders.set(str, headers.nextElement());
            }
        }
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.remove("Accept-Encoding");
        httpHeaders.remove("accept-encoding");
        httpHeaders.remove("Accept");
        httpHeaders.add("Accept", "application/json");
        return httpHeaders;
    }
}

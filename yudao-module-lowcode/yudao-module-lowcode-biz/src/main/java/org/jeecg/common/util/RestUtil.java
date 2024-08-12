package org.jeecg.common.util;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.online.ConvertUtils;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.spring6.context.SpringContextUtils;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * 调用 Restful 接口 Util
 *
 * @author sunjianlei
 */
@Slf4j
public class RestUtil {

    /**
     * RestAPI 调用器
     */
    private final static RestTemplate RT;
    public static String X_GATEWAY_BASE_PATH = "X_GATEWAY_BASE_PATH";
    private static String domain = null;
    private static String path = null;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        RT = new RestTemplate(requestFactory);
        // 解决乱码问题
        RT.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    private static String getDomain() {
        if (domain == null) {
            domain = getDomain1();
            // issues/2959
            // 微服务版集成企业微信单点登录
            // 因为微服务版没有端口号，导致 SpringContextUtils.getDomain() 方法获取的域名的端口号变成了:-1所以出问题了，只需要把这个-1给去掉就可以了。
            String port = ":-1";
            if (domain.endsWith(port)) {
                domain = domain.substring(0, domain.length() - 3);
            }
        }
        return domain;
    }

    /**
     * 获取项目根路径 basePath
     */
    public static String getDomain1() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        //1.微服务情况下，获取gateway的basePath
        String basePath = request.getHeader(X_GATEWAY_BASE_PATH);
        if (ConvertUtils.isNotEmpty(basePath)) {
            return basePath;
        } else {
            String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
            //2.【兼容】SSL认证之后，request.getScheme()获取不到https的问题
            // https://blog.csdn.net/weixin_34376986/article/details/89767950
            String scheme = request.getHeader(CommonConstant.X_FORWARDED_SCHEME);
            if (scheme != null && !request.getScheme().equals(scheme)) {
                domain = domain.replace(request.getScheme(), scheme);
            }
            return domain;
        }
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static String getPath() {
        if (path == null) {
            path = SpringUtil.getEnvironment().getProperty("server.servlet.context-path");
        }
        return ConvertUtils.getString(path);
    }

    public static String getBaseUrl() {
        String basepath = getDomain() + getPath();
        log.info(" RestUtil.getBaseUrl: " + basepath);
        return basepath;
    }

    public static RestTemplate getRestTemplate() {
        return RT;
    }

    /**
     * 发送 get 请求
     */
    public static JSONObject get(String url) {
        return getNative(url, null, null).getBody();
    }

    /**
     * 发送 get 请求
     */
    public static JSONObject get(String url, JSONObject variables) {
        return getNative(url, variables, null).getBody();
    }

    /**
     * 发送 get 请求
     */
    public static JSONObject get(String url, JSONObject variables, JSONObject params) {
        return getNative(url, variables, params).getBody();
    }

    /**
     * 发送 get 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> getNative(String url, JSONObject variables, JSONObject params) {
        return request(url, HttpMethod.GET, variables, params);
    }

    /**
     * 发送 Post 请求
     */
    public static JSONObject post(String url) {
        return postNative(url, null, null).getBody();
    }

    /**
     * 发送 Post 请求
     */
    public static JSONObject post(String url, JSONObject params) {
        return postNative(url, null, params).getBody();
    }

    /**
     * 发送 Post 请求
     */
    public static JSONObject post(String url, JSONObject variables, JSONObject params) {
        return postNative(url, variables, params).getBody();
    }

    /**
     * 发送 POST 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> postNative(String url, JSONObject variables, JSONObject params) {
        return request(url, HttpMethod.POST, variables, params);
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url) {
        return putNative(url, null, null).getBody();
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url, JSONObject params) {
        return putNative(url, null, params).getBody();
    }

    /**
     * 发送 put 请求
     */
    public static JSONObject put(String url, JSONObject variables, JSONObject params) {
        return putNative(url, variables, params).getBody();
    }

    /**
     * 发送 put 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> putNative(String url, JSONObject variables, JSONObject params) {
        return request(url, HttpMethod.PUT, variables, params);
    }

    /**
     * 发送 delete 请求
     */
    public static JSONObject delete(String url) {
        return deleteNative(url, null, null).getBody();
    }

    /**
     * 发送 delete 请求
     */
    public static JSONObject delete(String url, JSONObject variables, JSONObject params) {
        return deleteNative(url, variables, params).getBody();
    }

    /**
     * 发送 delete 请求，返回原生 ResponseEntity 对象
     */
    public static ResponseEntity<JSONObject> deleteNative(String url, JSONObject variables, JSONObject params) {
        return request(url, HttpMethod.DELETE, null, variables, params, JSONObject.class);
    }

    /**
     * 发送请求
     */
    public static ResponseEntity<JSONObject> request(String url, HttpMethod method, JSONObject variables, JSONObject params) {
        return request(url, method, getHeaderApplicationJson(), variables, params, JSONObject.class);
    }

    /**
     * 发送请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param headers      请求头  可空
     * @param variables    请求url参数 可空
     * @param params       请求body参数 可空
     * @param responseType 返回类型
     * @return ResponseEntity<responseType>
     */
    public static <T> ResponseEntity<T> request(String url, HttpMethod method, HttpHeaders headers, JSONObject variables, Object params, Class<T> responseType) {
        log.info(" RestUtil  --- request ---  url = " + url);
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url 不能为空");
        }
        if (method == null) {
            throw new RuntimeException("method 不能为空");
        }
        if (headers == null) {
            headers = new HttpHeaders();
        }
        // 请求体
        String body = "";
        if (params != null) {
            if (params instanceof JSONObject) {
                body = ((JSONObject) params).toJSONString();

            } else {
                body = params.toString();
            }
        }
        // 拼接 url 参数
        if (variables != null && !variables.isEmpty()) {
            url += ("?" + asUrlVariables(variables));
        }
        // 发送请求
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return RT.exchange(url, method, request, responseType);
    }

    /**
     * 获取JSON请求头
     */
    public static HttpHeaders getHeaderApplicationJson() {
        return getHeader(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * 获取请求头
     */
    public static HttpHeaders getHeader(String mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mediaType));
        headers.add("Accept", mediaType);
        return headers;
    }

    /**
     * 将 JSONObject 转为 a=1&b=2&c=3...&n=n 的形式
     */
    public static String asUrlVariables(JSONObject variables) {
        Map<String, Object> source = variables.getInnerMap();
        Iterator<String> it = source.keySet().iterator();
        StringBuilder urlVariables = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            String value = "";
            Object object = source.get(key);
            if (object != null) {
                if (!StringUtils.isEmpty(object.toString())) {
                    value = object.toString();
                }
            }
            urlVariables.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&
        return urlVariables.substring(1);
    }

}

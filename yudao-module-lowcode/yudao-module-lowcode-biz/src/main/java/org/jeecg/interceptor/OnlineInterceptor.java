package org.jeecg.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.dto.OnlineAuthDTO;
import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.modules.online.annotation.OnlineAuth;
import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;

/**
 * online 模块的拦截器
 *
 * @author opv2
 * @since 2024/8/21 下午12:43
 */
/* compiled from: OnlineInterceptor.java */
/* renamed from: org.jeecg.interceptor.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/interceptor/a.class */
@Slf4j
public class OnlineInterceptor implements HandlerInterceptor {

    /* renamed from: d */
    private static final String PATH = "/online/cgform";
    /* renamed from: e */
    private static final String[] PATH_ARRAY = {"/online/cgformInnerTableList", "/online/cgformErpList", "/online/cgformList", "/online/cgformTreeList", "/online/cgformTabList"};
    /* renamed from: b */
    private IOnlineBaseAPI onlineBaseAPI;
    /* renamed from: c */
//    底层共通业务API，提供其他独立模块调用
    private ISysBaseAPI sysBaseAPI;

    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) throws Exception {
        OnlineAuth methodAnnotation;
        if (handler.getClass().isAssignableFrom(HandlerMethod.class) && (methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(OnlineAuth.class)) != null) {
            String m4a = removeSlashes(request.getRequestURI().substring(request.getContextPath().length()));
            String value = methodAnnotation.value();
            String substring = m4a.substring(m4a.lastIndexOf(value) + value.length());
            if (CgformUtil.FORM.equals(value) && "DELETE".equals(request.getMethod())) {
                substring = substring.substring(0, substring.lastIndexOf("/"));
            }
            String parameter = request.getParameter("tabletype");
            if (this.onlineBaseAPI == null) {
                this.onlineBaseAPI = (IOnlineBaseAPI) SpringUtil.getBean(IOnlineBaseAPI.class);
            }
            String onlineErpCode = this.onlineBaseAPI.getOnlineErpCode(substring, parameter);


            //判断是否有操作权限
            ArrayList<String> arrayList = new ArrayList<>();
            for (String path : PATH_ARRAY) {
                arrayList.add(path + onlineErpCode);
            }
            if (this.sysBaseAPI == null) {
                //如果为空，就获取
                this.sysBaseAPI = SpringUtil.getBean(ISysBaseAPI.class);
            }
            if (!this.sysBaseAPI.hasOnlineAuth(new OnlineAuthDTO(SecurityFrameworkUtils.getUserName(), arrayList, PATH))) {
                //没有操作权限
                noPermission(response, value);
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * 递归处理路径中的双斜杠
     * 如果存在\\或者//，则替换为/
     *
     * @param str 路径
     * @return 处理后的路径
     */
    /* renamed from: a */
    private String removeSlashes(String str) {
        String str2 = "";
        if (StringUtils.isNotEmpty(str)) {
            str2 = str.replace("\\", "/").replace("//", "/");
            if (str2.contains("//")) {
                str2 = removeSlashes(str2);
            }
        }
        return str2;
    }

    /**
     * 判断为没有权限之后，直接回写到前端
     *
     * @param httpServletResponse 响应
     * @param operation           操作
     */
    /* renamed from: a */
    private void noPermission(HttpServletResponse httpServletResponse, String operation) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setHeader("auth", "fail");
        try {
            PrintWriter writer = httpServletResponse.getWriter();
            if ("exportXls".equals(operation)) {
                writer.print("");
            } else {
                writer.print(JSON.toJSON(error(401, "无权限访问(操作)")));
            }
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

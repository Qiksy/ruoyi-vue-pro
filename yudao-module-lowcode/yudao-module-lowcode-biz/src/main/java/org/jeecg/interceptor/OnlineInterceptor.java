package org.jeecg.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.online.annotation.OnlineAuth;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;

/* compiled from: OnlineInterceptor.java */
/* renamed from: org.jeecg.interceptor.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/interceptor/a.class */
public class OnlineInterceptor implements HandlerInterceptor {

    /* renamed from: b */
    private IOnlineBaseAPI onlineBaseAPI;

    /* renamed from: c */
//    底层共通业务API，提供其他独立模块调用
//    private ISysBaseAPI sysBaseAPI;

    /* renamed from: d */
    private static final String PATH = "/online/cgform";

    /* renamed from: a */
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineInterceptor.class);

    /* renamed from: e */
    private static final String[] PATH_ARRAY = {"/online/cgformInnerTableList", "/online/cgformErpList", "/online/cgformList", "/online/cgformTreeList", "/online/cgformTabList"};

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        OnlineAuth methodAnnotation;
        if (handler.getClass().isAssignableFrom(HandlerMethod.class) && (methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(OnlineAuth.class)) != null) {
            String m4a = m4a(request.getRequestURI().substring(request.getContextPath().length()));
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
            ArrayList arrayList = new ArrayList();
            for (String str : PATH_ARRAY) {
                arrayList.add(str + onlineErpCode);
            }
            //todo 这里要恢复检查权限
//            if (this.sysBaseAPI == null) {
//                //如果为空，就获取
//                this.sysBaseAPI = (ISysBaseAPI) SpringUtil.getBean(ISysBaseAPI.class);
//            }
//            if (!this.sysBaseAPI.hasOnlineAuth(new OnlineAuthDTO(JwtUtil.getUserNameByToken(request), arrayList, PATH))) {
//                //没有操作权限
//                noPermission(response, value);
//                return false;
//            }
            return true;
        }
        return true;
    }

    /* renamed from: a */
    private String m4a(String str) {
        String str2 = "";
        if (StringUtils.isNotEmpty(str)) {
            str2 = str.replace("\\", "/").replace("//", "/");
            if (str2.indexOf("//") >= 0) {
                str2 = m4a(str2);
            }
        }
        return str2;
    }

    /* renamed from: a */
    private void noPermission(HttpServletResponse httpServletResponse, String str) {
        PrintWriter printWriter = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setHeader("auth", "fail");
        try {
            try {
                PrintWriter writer = httpServletResponse.getWriter();
                if ("exportXls".equals(str)) {
                    writer.print("");
                } else {
                    writer.print(JSON.toJSON(error(401,"无权限访问(操作)")));
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                if (0 != 0) {
                    printWriter.close();
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                printWriter.close();
            }
            throw th;
        }
    }
}

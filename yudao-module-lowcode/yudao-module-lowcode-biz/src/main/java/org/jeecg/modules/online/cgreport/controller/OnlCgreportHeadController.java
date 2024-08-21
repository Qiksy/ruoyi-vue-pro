package org.jeecg.modules.online.cgreport.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.common.util.online.SqlInjectionUtil;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.config.blackList.OnlReportQueryBlackListHandler;
import org.jeecg.common.query.QueryGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/* compiled from: OnlCgreportHeadController.java */
@RequestMapping({"/online/cgreport/head"})
@RestController("onlCgreportHeadController")
@Tag(name = "online报表Head")
/* renamed from: org.jeecg.modules.online.cgreport.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/a/b.class */
public class OnlCgreportHeadController {

    /* renamed from: a */
    private static final Logger log = LoggerFactory.getLogger(OnlCgreportHeadController.class);

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlCgreportHeadService onlCgreportHeadService;

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

//    @Autowired
//    private BaseCommonService baseCommonService;

    @Autowired
    private OnlReportQueryBlackListHandler onlReportQueryBlackListHandler;

    @PreAuthorize("@ss.hasPermission('online:report:parseSql')")
    @GetMapping({"/parseSql"})
    @Operation(summary = "解析SQL")
    /* renamed from: a */
    public CommonResult<?> m403a(@RequestParam(name = "sql") String str, @RequestParam(name = "dbKey", required = false) String str2) {
        String str3;
        if (ConvertUtils.isNotBlank(str2) && this.sysBaseAPI.getDynamicDbSourceByCode(str2) == null) {
            return error("数据源不存在");
        }
        HashMap<String,Object> hashMap = new HashMap<>(5);
        ArrayList<OnlCgreportItem> arrayList = new ArrayList<>();
        ArrayList<OnlCgreportParam> arrayList2 = new ArrayList<>();
        try {
//            this.baseCommonService.addLog("Online报表，sql解析：" + str, 2, 2);
            if (!this.onlReportQueryBlackListHandler.isPass(str)) {
                return error(this.onlReportQueryBlackListHandler.getError());
            }
            SqlInjectionUtil.specialFilterContentForOnlineReport(str);
            List<String> sqlFields = this.onlCgreportHeadService.getSqlFields(str, str2);
            List<String> sqlParams = this.onlCgreportHeadService.getSqlParams(str);
            int i = 1;
            for (String str4 : sqlFields) {
                OnlCgreportItem onlCgreportItem = new OnlCgreportItem();
                onlCgreportItem.setFieldName(str4.toLowerCase());
                onlCgreportItem.setFieldTxt(str4);
                onlCgreportItem.setIsShow(1);
                onlCgreportItem.setOrderNum(Integer.valueOf(i));
                onlCgreportItem.setId(CgformUtil.nextId());
                onlCgreportItem.setFieldType(CgReportConstant.STRING);
                arrayList.add(onlCgreportItem);
                i++;
            }
            for (String str5 : sqlParams) {
                OnlCgreportParam onlCgreportParam = new OnlCgreportParam();
                onlCgreportParam.setParamName(str5);
                onlCgreportParam.setParamTxt(str5);
                arrayList2.add(onlCgreportParam);
            }
            hashMap.put("fields", arrayList);
            hashMap.put(CgReportConstant.PARAMS, arrayList2);
            return success(hashMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e.getMessage().contains("Connection refused: connect")) {
                str3 = "解析失败，" + "数据源连接失败.";
            } else if (e.getMessage().contains("值可能存在SQL注入风险")) {
                str3 = "解析失败，" + "SQL可能存在SQL注入风险.";
            } else if (e.getMessage().contains("该报表sql没有数据")) {
                str3 = "解析失败，" + "报表sql查询数据为空，无法解析字段.";
            } else if (e.getMessage().contains("SqlServer不支持SQL内排序")) {
                str3 = "解析失败，" + "SqlServer不支持SQL内排序.";
            } else if (e.getMessage().contains("Unknown column")) {
                str3 = "解析失败，" + "未知的字段名.";
            } else if (e instanceof Exception) {
                str3 = "解析失败，" + e.getMessage();
            } else {
                str3 = "解析失败，" + "SQL语法错误.";
            }
            return error(str3);
        }
    }

    @GetMapping({"/list"})
    @Operation(summary = "online报表Head-通过参数查询")
    /* renamed from: a */
    public CommonResult<IPage<OnlCgreportHead>> m404a(OnlCgreportHead onlCgreportHead, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        CommonResult<IPage<OnlCgreportHead>> result = new CommonResult<>();
        IPage<OnlCgreportHead> page = this.onlCgreportHeadService.page(new Page<>(num, num2), QueryGenerator.initQueryWrapper(onlCgreportHead, httpServletRequest.getParameterMap()));
        return success(page);
    }

    @PostMapping({"/add"})
    @Operation(summary = "online报表Head-增加")
    /* renamed from: a */
    public CommonResult<?> m405a(@RequestBody OnlCgreportModel onlCgreportModel) {
        CommonResult<?> result = new CommonResult<>();
        try {
            String m240a = CgformUtil.nextId();
            OnlCgreportHead head = onlCgreportModel.getHead();
            List<OnlCgreportParam> params = onlCgreportModel.getParams();
            List<OnlCgreportItem> items = onlCgreportModel.getItems();
            head.setId(m240a);
            for (OnlCgreportParam onlCgreportParam : params) {
                onlCgreportParam.setId(null);
                onlCgreportParam.setCgrheadId(m240a);
            }
            for (OnlCgreportItem onlCgreportItem : items) {
                onlCgreportItem.setId(null);
                onlCgreportItem.setFieldName(onlCgreportItem.getFieldName().trim().toLowerCase());
                onlCgreportItem.setCgrheadId(m240a);
            }
            this.onlCgreportHeadService.save(head);
            this.onlCgreportParamService.saveBatch(params);
            this.onlCgreportItemService.saveBatch(items);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return error("操作失败");
        }
        return result;
    }

    @PutMapping({"/editAll"})
    @CacheEvict(value = {"sys:cache:online:rp"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "online报表Head-修改")
    /* renamed from: b */
    public CommonResult<?> m406b(@RequestBody OnlCgreportModel onlCgreportModel) {
        try {
            return this.onlCgreportHeadService.editAll(onlCgreportModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return error("操作失败");
        }
    }

    @DeleteMapping({"/delete"})
    @Operation(summary = "online报表Head-删除")
    /* renamed from: a */
    public CommonResult<?> m407a(@RequestParam(name = "id", required = true) String str) {
        return this.onlCgreportHeadService.delete(str);
    }

    @DeleteMapping({"/deleteBatch"})
    @Operation(summary = "online报表Head-批量删除")
    /* renamed from: b */
    public CommonResult<?> m408b(@RequestParam(name = "ids", required = true) String str) {
        return this.onlCgreportHeadService.bathDelete(str.split(CgformUtil.COMMA_SEPARATOR));
    }

    @GetMapping({"/queryById"})
    @Operation(summary = "online报表Head-通过id查询")
    /* renamed from: c */
    public CommonResult<OnlCgreportHead> m409c(@RequestParam(name = "id", required = true) String str) {

        return success(this.onlCgreportHeadService.getById(str));
    }
}

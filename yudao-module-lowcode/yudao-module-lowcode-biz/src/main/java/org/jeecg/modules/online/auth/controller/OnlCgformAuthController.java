package org.jeecg.modules.online.auth.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 认证控制器
 *
 * @author linr
 * @since 2024/5/13 9:34
 */
/* compiled from: OnlCgformAuthController.java */
@RequestMapping({"/online/cgform/api"})
@RestController("onlCgformAuthController")
/* renamed from: org.jeecg.modules.online.auth.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/a/a.class */
@Tag(name = "online表单权限")
public class OnlCgformAuthController {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformAuthController.class);

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Autowired
    private IOnlCgformButtonService onlCgformButtonService;

    @Autowired
    private IOnlAuthRelationService onlAuthRelationService;

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    /* renamed from: a */
    public OnlCgformAuthController() {

    }

    @GetMapping({"/authData/{cgformId}"})
    /* renamed from: a */
    @Operation(summary = "获取表单的权限")
    public CommonResult<List<OnlAuthData>> getAuthData(@PathVariable("cgformId") String str) {
        LambdaQueryWrapper<OnlAuthData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlAuthData::getCgformId, str);
        return success(this.onlAuthDataService.list(lambdaQueryWrapper));
    }

    @PostMapping({"/authData"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: a */
    @Operation(summary = "创建表单权限")
    public CommonResult<OnlAuthData> createAuthData(@RequestBody OnlAuthData onlAuthData) {
        CommonResult<OnlAuthData> result = new CommonResult<>();
        try {
            this.onlAuthDataService.save(onlAuthData);
            result.success("添加成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error(500, "操作失败");
        }
        return result;
    }

    @PutMapping({"/authData"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: b */
    @Operation(summary = "更新表单权限")
    public CommonResult<OnlAuthData> updateAuthData(@RequestBody OnlAuthData onlAuthData) {
        CommonResult<OnlAuthData> result = new CommonResult<>();
        this.onlAuthDataService.updateById(onlAuthData);
        result.success("编辑成功！");
        return result;
    }

    @DeleteMapping({"/authData/{id}"})
    @Operation(summary = "删除表单权限")
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: b */
    public CommonResult<?> deleteAuthData(@PathVariable("id") String str) {
        this.onlAuthDataService.deleteOne(str);
        return success("删除成功！");
    }

    @PostMapping({"/createAiTestAuthData"})
    @Operation(summary = "创建AI测试数据")
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: a */
    public CommonResult<?> createAiTestAuthData(@RequestBody JSONObject jSONObject) {
        CommonResult<?> result = new CommonResult<>();
        try {
            this.onlAuthDataService.createAiTestAuthData(jSONObject);
            result.success("添加成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error(500, "操作失败");
        }
        return result;
    }

    @GetMapping({"/authButton/{cgformId}"})
    /* renamed from: c */
    @Operation(summary = "获取表单按钮权限")
    public CommonResult<Map<String, Object>> getAuthButton(@PathVariable("cgformId") String str) {
        LambdaQueryWrapper<OnlCgformButton> selectWrapper = new LambdaQueryWrapper<OnlCgformButton>()
                .eq(OnlCgformButton::getCgformHeadId, str)
                .eq(OnlCgformButton::getButtonStatus, "1")  //todo 替换为常量
                .select(OnlCgformButton::getButtonCode, OnlCgformButton::getButtonName, OnlCgformButton::getButtonStyle);

        List<OnlCgformButton> list = this.onlCgformButtonService.list(selectWrapper);

        LambdaQueryWrapper<OnlAuthPage> wrapper = new LambdaQueryWrapper<OnlAuthPage>()
                .eq(OnlAuthPage::getCgformId, str)
                .eq(OnlAuthPage::getType, 2);

        List<OnlAuthPage> list2 = this.onlAuthPageService.list(wrapper);
        HashMap<String, Object> hashMap = new HashMap<>(5);
        hashMap.put("buttonList", list);
        hashMap.put("authList", list2);
        return success(hashMap);
    }

    @PostMapping({"/authButton"})
    @Operation(summary = "创建按钮权限")
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: a */
    public CommonResult<OnlAuthPage> createAuthButton(@RequestBody OnlAuthPage onlAuthPage) {
        OnlAuthPage onlAuthPage2;
        try {
            String id = onlAuthPage.getId();
            boolean z = false;
            if (StringUtils.isNotEmpty(id) && (onlAuthPage2 = this.onlAuthPageService.getById(id)) != null) {
                z = true;
                onlAuthPage2.setStatus(1);
                this.onlAuthPageService.updateById(onlAuthPage2);
            }
            if (!z) {
                onlAuthPage.setStatus(1); //todo 改为常量
                this.onlAuthPageService.save(onlAuthPage);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error(500, "操作失败");
        }
        return success(onlAuthPage);
    }

    @PutMapping({"/authButton/{id}"})
    @Operation(summary = "更新按钮权限")
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: d */
    public CommonResult<?> updateAuthButton(@PathVariable("id") String str) {
        LambdaUpdateWrapper<OnlAuthPage> updateWrapper = new UpdateWrapper<OnlAuthPage>().lambda().eq(OnlAuthPage::getId, str).set(OnlAuthPage::getStatus, 0);
        this.onlAuthPageService.update(updateWrapper);
        return success("操作成功");
    }

    @GetMapping({"/authColumn/{cgformId}"})
    @Operation(summary = "获取所有字段的权限信息")
    /* renamed from: e */
    public CommonResult<List<AuthColumnVO>> getAuthColumn(@PathVariable("cgformId") String str) {
        CommonResult<List<AuthColumnVO>> result = new CommonResult<>();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
        if (list == null || list.isEmpty()) {
            error(500, "未找到对应字段信息！");
        }
        List<OnlAuthPage> list2 = this.onlAuthPageService.list(new LambdaQueryWrapper<OnlAuthPage>().eq(OnlAuthPage::getCgformId, str).eq(OnlAuthPage::getType, 1));
        ArrayList<AuthColumnVO> arrayList = new ArrayList<>();
        if (list != null) {
            for (OnlCgformField onlCgformField : list) {
                AuthColumnVO authColumnVO = new AuthColumnVO(onlCgformField);
                Integer num = 0;
                boolean isListShow = false;
                boolean isFormShow = false;
                boolean isFormEditable = false;
                for (OnlAuthPage authPage : list2) {
                    // todo 这里需要改成常量
                    if (onlCgformField.getDbFieldName().equals(authPage.getCode())) {
                        num = authPage.getStatus();
                        if (authPage.getPage() == 3 && authPage.getControl() == 5) {
                            isListShow = true;
                        }
                        if (authPage.getPage() == 5) {
                            if (authPage.getControl() == 5) {
                                isFormShow = true;
                            } else if (authPage.getControl() == 3) {
                                isFormEditable = true;
                            }
                        }
                    }
                }
                authColumnVO.setStatus(num);
                authColumnVO.setListShow(isListShow);
                authColumnVO.setFormShow(isFormShow);
                authColumnVO.setFormEditable(isFormEditable);
                arrayList.add(authColumnVO);
            }
        }
        //加载字段权限数据完成
        return success(arrayList);
    }

    @PutMapping({"/authColumn"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "更新字段权限信息")
    /* renamed from: a */
    public CommonResult<?> updateAuthColumn(@RequestBody AuthColumnVO authColumnVO) {
        CommonResult<?> result = new CommonResult<>();
        try {
            if (authColumnVO.getStatus() == 1) {
                this.onlAuthPageService.enableAuthColumn(authColumnVO);
            } else {
                this.onlAuthPageService.disableAuthColumn(authColumnVO);
            }
            result.success("操作成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error(500, "操作失败");
        }
        return result;
    }

    @PostMapping({"/authColumn"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "创建字段权限信息")
    /* renamed from: b */
    public CommonResult<?> createAuthColumn(@RequestBody AuthColumnVO authColumnVO) {
        try {
            this.onlAuthPageService.switchAuthColumn(authColumnVO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error(500, "操作失败");
        }
        return success("操作成功");
    }

    @GetMapping({"/authPage/{cgformId}/{type}"})
    @Operation(summary = "获取表单页面权限")
    /* renamed from: a */
    public CommonResult<List<AuthPageVO>> getAuthPage(@PathVariable("cgformId") String str, @PathVariable("type") Integer num) {
        return success(this.onlAuthPageService.queryAuthByFormId(str, num));
    }

    @GetMapping({"/validAuthData/{cgformId}"})
    @Operation(summary = "获取有效的权限数据")
    /* renamed from: f */
    public CommonResult<List<OnlAuthData>> validAuthData(@PathVariable("cgformId") String str) {
        return success(this.onlAuthDataService.list((
                new LambdaQueryWrapper<OnlAuthData>().eq(OnlAuthData::getCgformId, str)).eq(OnlAuthData::getStatus, 1)
                .select(OnlAuthData::getId, OnlAuthData::getRuleName)));
    }

    @GetMapping({"/roleAuth"})
    @Operation(summary = "获取角色权限")
    /* renamed from: a */
    public CommonResult<List<OnlAuthRelation>> getRoleAuth(@RequestParam("roleId") String str, @RequestParam("cgformId") String str2, @RequestParam("type") Integer num, @RequestParam("authMode") String str3) {

        return success(this.onlAuthRelationService.list(
                new LambdaQueryWrapper<OnlAuthRelation>().eq(OnlAuthRelation::getRoleId, str).eq(OnlAuthRelation::getCgformId, str2).eq(OnlAuthRelation::getType, num)
                        .eq(OnlAuthRelation::getAuthMode, str3).select(OnlAuthRelation::getAuthId)));
    }

    @PostMapping({"/roleColumnAuth/{roleId}/{cgformId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "创建字段权限")
    /* renamed from: a */
    public CommonResult<?> createColumnAuth(@PathVariable("roleId") String str, @PathVariable("cgformId") String str2, @RequestBody JSONObject jSONObject) {
        CommonResult<?> result = new CommonResult<>();
        JSONArray jSONArray = jSONObject.getJSONArray("authId");
        this.onlAuthRelationService.saveRoleAuth(str, str2, 1, jSONObject.getString("authMode"), jSONArray.toJavaList(String.class));

        return success("操作成功");
    }

    @PostMapping({"/roleButtonAuth/{roleId}/{cgformId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "创建按钮权限")
    /* renamed from: b */
    public CommonResult<?> createRoleButtonAuth(@PathVariable("roleId") String str, @PathVariable("cgformId") String str2, @RequestBody JSONObject jSONObject) {
        CommonResult<?> result = new CommonResult<>();
        JSONArray jSONArray = jSONObject.getJSONArray("authId");
        this.onlAuthRelationService.saveRoleAuth(str, str2, 2, jSONObject.getString("authMode"), jSONArray.toJavaList(String.class));

        return success("操作成功");
    }

    @PostMapping({"/roleDataAuth/{roleId}/{cgformId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "创建数据权限")
    /* renamed from: c */
    public CommonResult<?> createRoleDataAuth(@PathVariable("roleId") String str, @PathVariable("cgformId") String str2, @RequestBody JSONObject jSONObject) {
        CommonResult<?> result = new CommonResult<>();
        JSONArray jSONArray = jSONObject.getJSONArray("authId");
        this.onlAuthRelationService.saveRoleAuth(str, str2, 3, jSONObject.getString("authMode"), jSONArray.toJavaList(String.class));

        return success("操作成功");
    }

    @GetMapping({"/getAuthColumn/{desformCode}"})
    @Operation(summary = "获取字段权限")
    /* renamed from: g */
    public CommonResult<List<AuthColumnVO>> getAuthColumnByDesformCode(@PathVariable("desformCode") String str) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
        if (onlCgformHead == null) {
            return error(500,"未找到对应表单信息!");
        }
        CommonResult<List<AuthColumnVO>> result = new CommonResult<>();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
        if (list == null || list.isEmpty()) {
            return error(500,"未找到对应字段信息!");
        }
        ArrayList<AuthColumnVO> arrayList = new ArrayList<>();
        for (OnlCgformField onlCgformField : list) {
            if (!CgformUtil.m259i(onlCgformField.getDbFieldName())) {
                AuthColumnVO authColumnVO = new AuthColumnVO(onlCgformField);
                authColumnVO.setTableName(onlCgformHead.getTableName());
                authColumnVO.setTableNameTxt(onlCgformHead.getTableTxt());
                authColumnVO.setIsMain(true);
                arrayList.add(authColumnVO);
            }
        }
        if (StringUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
            for (String str2 : onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(
                        new LambdaQueryWrapper<OnlCgformHead>()
                                .eq(OnlCgformHead::getTableName, str2));
                if (onlCgformHead2 != null) {
                    List<OnlCgformField> list2 = this.onlCgformFieldService.list(
                            new LambdaQueryWrapper<OnlCgformField>()
                                    .eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId()));
                    if (list2 != null) {
                        for (OnlCgformField onlCgformField2 : list2) {
                            if (!CgformUtil.m259i(onlCgformField2.getDbFieldName())) {
                                AuthColumnVO authColumnVO2 = new AuthColumnVO(onlCgformField2);
                                authColumnVO2.setTableName(onlCgformHead2.getTableName());
                                authColumnVO2.setTableNameTxt(onlCgformHead2.getTableTxt());
                                authColumnVO2.setIsMain(false);
                                arrayList.add(authColumnVO2);
                            }
                        }
                    }
                }
            }
        }
        return success(arrayList);
    }
}

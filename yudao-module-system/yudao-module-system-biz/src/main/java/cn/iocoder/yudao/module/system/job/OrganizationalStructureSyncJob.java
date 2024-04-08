package cn.iocoder.yudao.module.system.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 同步组织架构的任务
 * 1. 同步用户
 * 2. 同步部门
 * 3. 同步部门负责人
 * @author linr
 * @since 2023/12/19 9:43
 */
@Component
@Slf4j
public class OrganizationalStructureSyncJob implements JobHandler {


    @Resource
    private AdminUserService adminUserService;

    @Resource
    private DeptService deptService;

    /**
     * 执行任务
     * @param param 参数
     * @return 执行结果
     */
    @Override
    @TenantJob
    public String execute(String param) {
        log.info("[execute][开始同步部门]");
        // 同步部门
        deptService.syncDept();
        log.info("[execute][同步部门成功]");


        log.info("[execute][开始执行同步组织架构的任务]");
        // 首先，同步用户
        adminUserService.syncUser();
        log.info("[execute][同步用户成功]");



        return null;
    }
}

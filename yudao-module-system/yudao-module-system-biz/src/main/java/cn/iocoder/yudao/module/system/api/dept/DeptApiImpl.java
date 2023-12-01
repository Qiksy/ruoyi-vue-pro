package cn.iocoder.yudao.module.system.api.dept;

import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.convert.dept.DeptConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 部门 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DeptApiImpl implements DeptApi {

    @Resource
    private DeptService deptService;

    @Override
    public DeptRespDTO getDept(Long id) {
        DeptDO dept = deptService.getDept(id);
        return DeptConvert.INSTANCE.convert03(dept);
    }

    @Override
    public List<DeptRespDTO> getDeptList(Collection<Long> ids) {
        List<DeptDO> depts = deptService.getDeptList(ids);
        return DeptConvert.INSTANCE.convertList03(depts);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        deptService.validateDeptList(ids);
    }

    /**
     * 获取没有负责人的部门信息
     *
     * @param ids 部门id
     * @return
     */
    @Override
    public List<DeptRespDTO> getNotExistsLeaderDepts(Collection<Long> ids) {
        List<DeptDO> depts = deptService.getNotExistsLeaderDepts(ids);

        return DeptConvert.INSTANCE.convertList03(depts);
    }
}

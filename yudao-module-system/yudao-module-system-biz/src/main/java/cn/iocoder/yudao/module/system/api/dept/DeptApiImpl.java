package cn.iocoder.yudao.module.system.api.dept;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
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
        return BeanUtils.toBean(dept, DeptRespDTO.class);
    }

    @Override
    public List<DeptRespDTO> getDeptList(Collection<Long> ids) {
        List<DeptDO> depts = deptService.getDeptList(ids);
        return BeanUtils.toBean(depts, DeptRespDTO.class);
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

        return  BeanUtils.toBean(depts, DeptRespDTO.class);
    }

    /**
     * 根据NC主键的值，获取部门信息
     *
     * @param areaPk
     * @return
     */
    @Override
    public DeptRespDTO getDeptByPk(String areaPk) {
        DeptDO dept = deptService.getDeptByPk(areaPk);

        return BeanUtils.toBean(dept, DeptRespDTO.class);
    }
}

package cn.iocoder.yudao.module.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptNcDTO;
import cn.iocoder.yudao.module.system.api.openapi.BoenOpenApi;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.dept.DeptMapper;
import cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.openapi.BoenApiUrlEnums.DEPT_DETAIL_URL;

/**
 * 部门 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

//    @Autowired
//    @Lazy
//    DeptService deptService; //引入自己，使得数据源切换生效

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private BoenOpenApi boenOpenApi;


    @Resource
    private RestTemplate restTemplate;


    @Resource
    @Lazy
    private AdminUserService adminUserService;

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public Long createDept(DeptSaveReqVO createReqVO) {
        if (createReqVO.getParentId() == null) {
            createReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // 校验父部门的有效性
        validateParentDept(null, createReqVO.getParentId());
        // 校验部门名的唯一性
        validateDeptNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入部门
        DeptDO dept = BeanUtils.toBean(createReqVO, DeptDO.class);
        deptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public void updateDept(DeptSaveReqVO updateReqVO) {
        if (updateReqVO.getParentId() == null) {
            updateReqVO.setParentId(DeptDO.PARENT_ID_ROOT);
        }
        // 校验自己存在
        validateDeptExists(updateReqVO.getId());
        // 校验父部门的有效性
        validateParentDept(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验部门名的唯一性
        validateDeptNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新部门
        DeptDO updateObj = BeanUtils.toBean(updateReqVO, DeptDO.class);
        deptMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,
            allEntries = true) // allEntries 清空所有缓存，因为操作一个部门，涉及到多个缓存
    public void deleteDept(Long id) {
        // 校验是否存在
        validateDeptExists(id);
        // 校验是否有子部门
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除部门
        deptMapper.deleteById(id);
    }

    @VisibleForTesting
    void validateDeptExists(Long id) {
        if (id == null) {
            return;
        }
        DeptDO dept = deptMapper.selectById(id);
        if (dept == null) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    @VisibleForTesting
    void validateParentDept(Long id, Long parentId) {
        if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父部门
        if (Objects.equals(id, parentId)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 2. 父部门不存在
        DeptDO parentDept = deptMapper.selectById(parentId);
        if (parentDept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父部门，如果父部门是自己的子部门，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentDept.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(DEPT_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父部门
            if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentDept = deptMapper.selectById(parentId);
            if (parentDept == null) {
                break;
            }
        }
    }

    @VisibleForTesting
    void validateDeptNameUnique(Long id, Long parentId, String name) {
        DeptDO dept = deptMapper.selectByParentIdAndName(parentId, name);
        if (dept == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的部门
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dept.getId(), id)) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<DeptDO> getDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public List<DeptRespVO> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> list = deptMapper.selectList(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));

        //获取部门负责人ID
        List<Long> leaderUserIdList = list.stream().map(DeptDO::getLeaderUserId).toList();
        List<AdminUserDO> userList = adminUserService.getUserList(leaderUserIdList);

        List<DeptRespVO> result = new ArrayList<>();

        for (DeptDO deptDO : list) {
            DeptRespVO deptRespVO = BeanUtils.toBean(deptDO, DeptRespVO.class);
            //设置部门负责人名称
            if (deptDO.getLeaderUserId() != null) {
                AdminUserDO user = userList.stream().filter(v -> v.getId().equals(deptDO.getLeaderUserId())).findFirst().orElse(null);
                if (user != null) {
                    deptRespVO.setLeaderName(user.getNickname());
                }
            }
            result.add(deptRespVO);
        }


        return result;
    }


    @Override
    public List<DeptDO> getDeptList2(DeptListReqVO reqVO) {
        List<DeptDO> list = deptMapper.selectList(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));
        return list;
    }

    @Override
    public List<DeptSimpleRespVO> getDeptList3(DeptListReqVO reqVO) {
        List<DeptSimpleRespVO> list = deptMapper.selectList3(reqVO);
        list.sort(Comparator.comparing(DeptSimpleRespVO::getId));
        return list;
    }

    @Override
    public List<DeptDO> getChildDeptList(Long id) {
        List<DeptDO> children = new LinkedList<>();
        // 遍历每一层
        Collection<Long> parentIds = Collections.singleton(id);
        for (int i = 0; i < Short.MAX_VALUE; i++) { // 使用 Short.MAX_VALUE 避免 bug 场景下，存在死循环
            // 查询当前层，所有的子部门
            List<DeptDO> depts = deptMapper.selectListByParentId(parentIds);
            // 1. 如果没有子部门，则结束遍历
            if (CollUtil.isEmpty(depts)) {
                break;
            }
            // 2. 如果有子部门，继续遍历
            children.addAll(depts);
            parentIds = convertSet(depts, DeptDO::getId);
        }
        return children;
    }

    @Override
    @DataPermission(enable = false) // 禁用数据权限，避免建立不正确的缓存
    @Cacheable(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST, key = "#id")
    public Set<Long> getChildDeptIdListFromCache(Long id) {
        List<DeptDO> children = getChildDeptList(id);
        return convertSet(children, DeptDO::getId);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        Map<Long, DeptDO> deptMap = getDeptMap(ids);
        // 校验
        ids.forEach(id -> {
            DeptDO dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDept() {

        //重写逻辑 by 2024.04.08
//        1. 从NC65中获取部门数据
        List<DeptNcDTO> ncDeptList = this.getDeptFromNC();

        //2. 删除目前没有的部门
        List<String> pkDept = ncDeptList.stream().map(DeptNcDTO::getPkDept).distinct().toList();
        deptMapper.delete(new LambdaQueryWrapper<DeptDO>().notIn(DeptDO::getPkDept, pkDept));

        //3. 更新所有部门的领导人都为空
        deptMapper.update(new LambdaUpdateWrapper<>(DeptDO.class).set(DeptDO::getLeaderUserId, null));

        //3. 设置用户的leaderUserId
        for (DeptNcDTO deptNcDTO : ncDeptList) {
            setLeaderUserId(deptNcDTO, ncDeptList);
        }


        //4. 插入或者更新
        // 首先，查询出来系统的所有的部门
        List<DeptDO> deptDOS = deptMapper.selectList();
        Map<String, DeptDO> systemDepMap = deptDOS.stream().collect(Collectors.toMap(DeptDO::getPkDept, v -> v));
        //将系统部门的id值赋值给ncDeptList中具有相同的pkDept的部门

        //转成树状结构
        List<DeptNcDTO> treeRoot = convertListToTree(ncDeptList);
        //队列
        Deque<DeptNcDTO> deque = new ArrayDeque<>();
        for (DeptNcDTO root : treeRoot) {

            deque.add(root);

            while (!deque.isEmpty() ) {

                int size = deque.size();
                while (size>0){
                    DeptNcDTO poll = deque.poll();
                    DeptDO deptDO = systemDepMap.get(poll.getPkDept());
                    //设置了id
                    if(deptDO==null){
                        poll.setId(DefaultIdentifierGenerator.getInstance().nextId(null));
                    }else {
                        poll.setId(deptDO.getId());
                    }

                    //转成DO
                    DeptDO deptDO1 = BeanUtils.toBean(poll, DeptDO.class);
                    deptDO1.setStatus(CommonStatusEnum.ENABLE.getStatus());
                    //插入或者更新
                    if (deptDO == null) {
                        deptMapper.insert(deptDO1);
                    } else {
                        deptMapper.updateById(deptDO1);
                    }


                    List<DeptNcDTO> children = Optional.ofNullable(poll.getChildren()).orElse(Collections.emptyList());
                    for (DeptNcDTO child : children) {
                        DeptDO deptDO2 = systemDepMap.get(child.getPkDept());
                        //设置了id
                        if(deptDO2==null){
                            child.setId(DefaultIdentifierGenerator.getInstance().nextId(null));
                        }else {
                            child.setId(deptDO2.getId());
                        }
                        //设置父级id
                        child.setParentId(poll.getId());
                        //加入队列
                        deque.add(child);
                    }
                    size--;
                }
            }
        }
    }


    /**
     * 将部门列表转换为树结构
     * @param list
     * @return
     */
    public List<DeptNcDTO> convertListToTree(List<DeptNcDTO> list) {
        // 将列表转换为Map，键为pkDept，值为DeptNcDTO对象
        Map<String, DeptNcDTO> map = list.stream()
                .collect(Collectors.toMap(DeptNcDTO::getPkDept, dept -> dept));

        // 遍历列表，为每个DeptNcDTO设置其子节点
        list.forEach(dept -> {
            DeptNcDTO parent = map.get(dept.getParentPkDept());
            if (parent != null) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(dept);
            }
        });

        // 返回根节点列表
        return list.stream()
                .filter(dept -> dept.getParentPkDept() == null)
                .collect(Collectors.toList());
    }

    /**
     * 一个递归方法
     *
     * @param dto
     * @param deptDOList
     */
    private void setLeaderUserId(DeptNcDTO dto, List<DeptNcDTO> deptDOList) {
        if (dto == null) {
            return;
        }
        String leaderUserPhone = dto.getLeaderUserPhone();

        // 不再利用上级领导兼任下级部门的领导人

//        if (leaderUserPhone == null) {
//            // 如果不为空，则设置为leaderUserId
//            //如果是空的 那就继续找上级的phone，如果找不到就继续找，所以是一个递归的问题
//            leaderUserPhone = findParentLeaderUserPhone(dto.getParentPkDept(), deptDOList);
//        }
        if (leaderUserPhone!=null && !leaderUserPhone.isEmpty()) {
            AdminUserDO user = adminUserService.getUser(leaderUserPhone);//根据用户名查询用户
            if (user != null) {
                dto.setLeaderUserId(user.getId());
            }
        }
    }

    private String findParentLeaderUserPhone(String parentPkDept, List<DeptNcDTO> deptDOList) {
        if (parentPkDept == null) {
            return null;
        }
        DeptNcDTO parentDept = deptDOList.stream().filter(v -> v.getPkDept().equals(parentPkDept)).findFirst().orElse(null);
        if (parentDept == null) {
            return null;
        }
        if (parentDept.getLeaderUserPhone() != null) {
            return parentDept.getLeaderUserPhone();
        } else {
            return findParentLeaderUserPhone(parentDept.getParentPkDept(), deptDOList);
        }
    }

    @Override
//    @DS("nc65")
    public List<DeptNcDTO> getDeptFromNC() {
        ParameterizedTypeReference<List<DeptNcDTO>> typeRef = new ParameterizedTypeReference<>() {
        };

        return boenOpenApi.sendRequest(typeRef, DEPT_DETAIL_URL.getUrl(), "GET", null);
    }


    @Override
    public List<DeptDO> getNotExistsLeaderDepts(Collection<Long> ids) {
        LambdaQueryWrapper<DeptDO> queryWrapper = new LambdaQueryWrapper<DeptDO>()
                .in(DeptDO::getId, ids)
                .isNull(DeptDO::getLeaderUserId);

        return deptMapper.selectList(queryWrapper);
    }

    /**
     * 根据ncpk值获取部门信息
     *
     * @param areaPk
     * @return
     */
    @Override
    public DeptDO getDeptByPk(String areaPk) {
        LambdaQueryWrapper<DeptDO> queryWrapper = new LambdaQueryWrapper<DeptDO>()
                .eq(DeptDO::getPkDept, areaPk);
        return deptMapper.selectOne(queryWrapper);
    }
}

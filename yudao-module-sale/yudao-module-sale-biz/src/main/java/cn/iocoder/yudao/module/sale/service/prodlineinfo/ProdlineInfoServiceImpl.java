package cn.iocoder.yudao.module.sale.service.prodlineinfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.prodlineinfo.ProdlineInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.prodlineinfo.ProdlineInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 产品线 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ProdlineInfoServiceImpl implements ProdlineInfoService {

    @Resource
    private ProdlineInfoMapper prodlineInfoMapper;

    @Override
    public Long createProdlineInfo(ProdlineInfoSaveReqVO createReqVO) {
        // 校验父级ID的有效性
        validateParentProdlineInfo(null, createReqVO.getParentId());
        // 校验名称的唯一性
        validateProdlineInfoNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        ProdlineInfoDO prodlineInfo = BeanUtils.toBean(createReqVO, ProdlineInfoDO.class);
        prodlineInfoMapper.insert(prodlineInfo);
        // 返回
        return prodlineInfo.getId();
    }

    @Override
    public void updateProdlineInfo(ProdlineInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateProdlineInfoExists(updateReqVO.getId());
        // 校验父级ID的有效性
        validateParentProdlineInfo(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验名称的唯一性
        validateProdlineInfoNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        ProdlineInfoDO updateObj = BeanUtils.toBean(updateReqVO, ProdlineInfoDO.class);
        prodlineInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteProdlineInfo(Long id) {
        // 校验存在
        validateProdlineInfoExists(id);
        // 校验是否有子产品线
        if (prodlineInfoMapper.selectCountByParentId(id) > 0) {
            throw exception(PRODLINE_INFO_EXITS_CHILDREN);
        }
        // 删除
        prodlineInfoMapper.deleteById(id);
    }

    private void validateProdlineInfoExists(Long id) {
        if (prodlineInfoMapper.selectById(id) == null) {
            throw exception(PRODLINE_INFO_NOT_EXISTS);
        }
    }

    private void validateParentProdlineInfo(Long id, Long parentId) {
        if (parentId == null || ProdlineInfoDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父产品线
        if (Objects.equals(id, parentId)) {
            throw exception(PRODLINE_INFO_PARENT_ERROR);
        }
        // 2. 父产品线不存在
        ProdlineInfoDO parentProdlineInfo = prodlineInfoMapper.selectById(parentId);
        if (parentProdlineInfo == null) {
            throw exception(PRODLINE_INFO_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父产品线，如果父产品线是自己的子产品线，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentProdlineInfo.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(PRODLINE_INFO_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父产品线
            if (parentId == null || ProdlineInfoDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentProdlineInfo = prodlineInfoMapper.selectById(parentId);
            if (parentProdlineInfo == null) {
                break;
            }
        }
    }

    private void validateProdlineInfoNameUnique(Long id, Long parentId, String name) {
        ProdlineInfoDO prodlineInfo = prodlineInfoMapper.selectByParentIdAndName(parentId, name);
        if (prodlineInfo == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的产品线
        if (id == null) {
            throw exception(PRODLINE_INFO_NAME_DUPLICATE);
        }
        if (!Objects.equals(prodlineInfo.getId(), id)) {
            throw exception(PRODLINE_INFO_NAME_DUPLICATE);
        }
    }

    @Override
    public ProdlineInfoDO getProdlineInfo(Long id) {
        return prodlineInfoMapper.selectById(id);
    }

    @Override
    public List<ProdlineInfoDO> getProdlineInfoList(ProdlineInfoListReqVO listReqVO) {
        return prodlineInfoMapper.selectList(listReqVO);
    }

}
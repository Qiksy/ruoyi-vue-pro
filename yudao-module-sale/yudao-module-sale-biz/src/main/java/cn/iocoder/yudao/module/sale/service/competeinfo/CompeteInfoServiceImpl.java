package cn.iocoder.yudao.module.sale.service.competeinfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfo.CompeteInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.competeinfo.CompeteInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 竞品信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CompeteInfoServiceImpl implements CompeteInfoService {

    @Resource
    private CompeteInfoMapper competeInfoMapper;

    @Override
    public Long createCompeteInfo(CompeteInfoSaveReqVO createReqVO) {
        // 插入
        CompeteInfoDO competeInfo = BeanUtils.toBean(createReqVO, CompeteInfoDO.class);
        competeInfoMapper.insert(competeInfo);
        // 返回
        return competeInfo.getId();
    }

    @Override
    public void updateCompeteInfo(CompeteInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateCompeteInfoExists(updateReqVO.getId());
        // 更新
        CompeteInfoDO updateObj = BeanUtils.toBean(updateReqVO, CompeteInfoDO.class);
        competeInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteCompeteInfo(Long id) {
        // 校验存在
        validateCompeteInfoExists(id);
        // 删除
        competeInfoMapper.deleteById(id);
    }

    private void validateCompeteInfoExists(Long id) {
        if (competeInfoMapper.selectById(id) == null) {
            throw exception(COMPETE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public CompeteInfoDO getCompeteInfo(Long id) {
        return competeInfoMapper.selectById(id);
    }

    @Override
    public PageResult<CompeteInfoDO> getCompeteInfoPage(CompeteInfoPageReqVO pageReqVO) {
        return competeInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<CompeteInfoRespVO> getCompeteInfoPage2(CompeteInfoPageReqVO pageReqVO) {
        // 进行分页查询
        IPage<CompeteInfoRespVO> iPage = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        IPage<CompeteInfoRespVO> page = competeInfoMapper.selectPage2(iPage,pageReqVO); // 转为PageResult
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public CompeteInfoRespVO getCompeteInfo2(Long id) {
        return competeInfoMapper.selectInfoById(id);
    }
}
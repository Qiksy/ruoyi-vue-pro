package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;
import cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻管库存预录入 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingTubeStockPreEntryServiceImpl implements FreezingTubeStockPreEntryService {

    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;

    @Resource
    private MicrobeBasicInfoMapper microbeBasicInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createFreezingTubeStockPreEntry(FreezingTubeStockPreEntrySaveReqVO createReqVO) {


        Long microbeId = createReqVO.getMicrobeId();
        MicrobeBasicInfoDO microbeBasicInfoDO = microbeBasicInfoMapper.selectById(microbeId);
        String microbeType = microbeBasicInfoDO.getMicrobeType();
        String microbeCode = microbeBasicInfoDO.getCode();

        // 获取数量，然后构造多个对象，并且批量插入
        List<FreezingTubeStockPreEntryDO> doList = new ArrayList<>();

        for (int i = 0; i < createReqVO.getNum(); i++) {
            FreezingTubeStockPreEntryDO entryDO = new FreezingTubeStockPreEntryDO();
            entryDO.setTubeId(createReqVO.getTubeId()); // 冻藏管
            entryDO.setRemark(createReqVO.getRemark()); // 备注
            entryDO.setMicrobeId(createReqVO.getMicrobeId()); // 菌种
            entryDO.setSaveBy(SecurityFrameworkUtils.getLoginUserId()); // 保存人
            entryDO.setSaveDate(createReqVO.getSaveDate()); // 保存日期
            entryDO.setCode(generateCode(microbeCode, i));

            //设置融冻次数
            entryDO.setThawFreezeCycleCount(1);

            //过期时间=保存时间+有效期天数
            entryDO.setExpirationDate(createReqVO.getSaveDate().plusDays(microbeBasicInfoDO.getValidityPeriodDays()));

            // 默认没有入库
            entryDO.setStatus(InventoryStatisEnum.NOT_IN_STOCK.getValue());

            doList.add(entryDO);
        }

        freezingTubeStockPreEntryMapper.insertBatch(doList);

        return doList.stream().map(FreezingTubeStockPreEntryDO::getId).toList();//返回id
    }

    /**
     * 生成冷冻管编号
     * 菌种编号-加上流水号
     *
     * @param microbeCode 菌种编号
     * @param i           第几个
     * @return 冷冻管编号
     */
    private String generateCode(String microbeCode, int i) {
        //菌种类型
//        microbeCode = String.format("%02d", Integer.parseInt(microbeCode));

        FreezingTubeStockPreEntryDO entryDO = freezingTubeStockPreEntryMapper.selectOne(new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>()
                .select(FreezingTubeStockPreEntryDO::getCode)
                .likeRight(FreezingTubeStockPreEntryDO::getCode,(microbeCode+"-") )
                .last("limit 1")
                .orderByDesc(FreezingTubeStockPreEntryDO::getCode));


        int serialNumber;
        if (entryDO!=null){
            String oldCode = entryDO.getCode();
            serialNumber = Integer.parseInt(oldCode.substring((microbeCode+"-").length()));
        }else {
            serialNumber = 0;
        }

        serialNumber = serialNumber + i + 1;

        int length = Math.max(2,String.valueOf(serialNumber).length()) ;

        return (microbeCode+"-") + String.format("%0"+length+"d", serialNumber);
    }

    @Override
    public void updateFreezingTubeStockPreEntry(FreezingTubeStockPreEntrySaveReqVO updateReqVO) {
        // 校验存在
        validateFreezingTubeStockPreEntryExists(updateReqVO.getId());
        // 更新
        FreezingTubeStockPreEntryDO updateObj = BeanUtils.toBean(updateReqVO, FreezingTubeStockPreEntryDO.class);
        freezingTubeStockPreEntryMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingTubeStockPreEntry(Long id) {
        // 校验存在
        validateFreezingTubeStockPreEntryExists(id);
        // 删除
        freezingTubeStockPreEntryMapper.deleteById(id);
    }

    private void validateFreezingTubeStockPreEntryExists(Long id) {
        if (freezingTubeStockPreEntryMapper.selectById(id) == null) {
            throw exception(FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
        }
    }

    @Override
    public FreezingTubeStockPreEntryDO getFreezingTubeStockPreEntry(Long id) {
        return freezingTubeStockPreEntryMapper.selectById(id);
    }

    @Override
    public PageResult<FreezingTubeStockPreEntryDO> getFreezingTubeStockPreEntryPage(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        return freezingTubeStockPreEntryMapper.selectPage(pageReqVO);
    }

    /**
     * 连表查询分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    @Override
    public PageResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntryPage2(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        IPage<FreezingTubeStockPreEntryRespVO> iPage = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
         freezingTubeStockPreEntryMapper.selectPage2(iPage,pageReqVO);
        return new PageResult<>(iPage.getRecords(),iPage.getTotal());
    }
}
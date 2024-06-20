package cn.iocoder.yudao.module.strain.service.resave;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveRespVO;
import cn.iocoder.yudao.module.strain.dal.mysql.resave.ResaveInfoMapper;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.SpecimenInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResaveInfoServiceImpl implements ResaveInfoService{

    @Resource
    ResaveInfoMapper resaveInfoMapper;

    @Resource
    SpecimenInfoService stockPreEntryService;




    @Override
    public PageResult<ResaveRespVO> list(ResaveReqVO param) {


        IPage<ResaveRespVO> page = resaveInfoMapper.list(new Page<>(param.getPageNo(), param.getPageSize()), param);
        // 3. 拼接结果
        List<ResaveRespVO> records = page.getRecords();

        List<Long> stockIds = records.stream().map(ResaveRespVO::getStockId).toList();

        Map<Long, String> positionMap = stockPreEntryService.getStockPositionStrMap(stockIds);

        if (!positionMap.isEmpty()) {
            records.forEach(
                    item -> item.setPositionStr(positionMap.get(item.getStockId()))
            );
        }


        return new PageResult<>(page.getRecords(), page.getTotal());

    }
}

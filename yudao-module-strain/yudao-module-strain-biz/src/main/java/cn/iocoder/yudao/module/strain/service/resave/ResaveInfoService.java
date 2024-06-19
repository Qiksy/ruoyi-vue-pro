package cn.iocoder.yudao.module.strain.service.resave;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveRespVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ResaveInfoService {

    PageResult<ResaveRespVO> list(ResaveReqVO param);
}

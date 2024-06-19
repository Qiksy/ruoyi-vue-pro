package cn.iocoder.yudao.module.strain.dal.mysql.resave;


import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResaveInfoMapper {


    IPage<ResaveRespVO> list(IPage<ResaveRespVO> page, @Param("param") ResaveReqVO param);
}

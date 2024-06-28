package cn.iocoder.yudao.module.oa.service.signininfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInInfoDO;
import cn.iocoder.yudao.module.oa.dal.mysql.signininfo.SignInInfoMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static cn.iocoder.yudao.module.oa.enums.ErrorCodeConstants.SIGN_IN_INFO_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link SignInInfoServiceImpl} 的单元测试类
 *
 * @author 超级管理员
 */
@Import(SignInInfoServiceImpl.class)
public class SignInInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SignInInfoServiceImpl signInInfoService;

    @Resource
    private SignInInfoMapper signInInfoMapper;

    @Test
    public void testCreateSignInInfo_success() {
        // 准备参数
        SignInInfoSaveReqVO createReqVO = randomPojo(SignInInfoSaveReqVO.class).setId(null);

        // 调用
        Long signInInfoId = signInInfoService.createSignInInfo(createReqVO);
        // 断言
        assertNotNull(signInInfoId);
        // 校验记录的属性是否正确
        SignInInfoDO signInInfo = signInInfoMapper.selectById(signInInfoId);
        assertPojoEquals(createReqVO, signInInfo, "id");
    }

    @Test
    public void testUpdateSignInInfo_success() {
        // mock 数据
        SignInInfoDO dbSignInInfo = randomPojo(SignInInfoDO.class);
        signInInfoMapper.insert(dbSignInInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SignInInfoSaveReqVO updateReqVO = randomPojo(SignInInfoSaveReqVO.class, o -> {
            o.setId(dbSignInInfo.getId()); // 设置更新的 ID
        });

        // 调用
        signInInfoService.updateSignInInfo(updateReqVO);
        // 校验是否更新正确
        SignInInfoDO signInInfo = signInInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, signInInfo);
    }

    @Test
    public void testUpdateSignInInfo_notExists() {
        // 准备参数
        SignInInfoSaveReqVO updateReqVO = randomPojo(SignInInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> signInInfoService.updateSignInInfo(updateReqVO), SIGN_IN_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteSignInInfo_success() {
        // mock 数据
        SignInInfoDO dbSignInInfo = randomPojo(SignInInfoDO.class);
        signInInfoMapper.insert(dbSignInInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbSignInInfo.getId();

        // 调用
        signInInfoService.deleteSignInInfo(id);
       // 校验数据不存在了
       assertNull(signInInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteSignInInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> signInInfoService.deleteSignInInfo(id), SIGN_IN_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSignInInfoPage() {
       // mock 数据
       SignInInfoDO dbSignInInfo = randomPojo(SignInInfoDO.class, o -> { // 等会查询到
           o.setCoverPicId(null);
           o.setCoverPicUrl(null);
           o.setTitle(null);
           o.setDescription(null);
           o.setStartDate(null);
           o.setEndDate(null);
           o.setSignInTimeType(null);
           o.setPersonInfoNeed(null);
           o.setPositionNeed(null);
           o.setPositionInfo(null);
           o.setScannerNeed(null);
           o.setBannerId(null);
           o.setBannerUrl(null);
           o.setLogoId(null);
           o.setLogoUrl(null);
           o.setTitlePicUrl(null);
           o.setTitlePicId(null);
           o.setSignTaskCount(null);
           o.setCreateTime(null);
       });
       signInInfoMapper.insert(dbSignInInfo);
       // 测试 coverPicId 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setCoverPicId(null)));
       // 测试 coverPicUrl 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setCoverPicUrl(null)));
       // 测试 title 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setTitle(null)));
       // 测试 description 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setDescription(null)));
       // 测试 startDate 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setStartDate(null)));
       // 测试 endDate 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setEndDate(null)));
       // 测试 signInTimeType 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setSignInTimeType(null)));
       // 测试 personInfoNeed 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setPersonInfoNeed(null)));
       // 测试 positionNeed 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setPositionNeed(null)));
       // 测试 positionInfo 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setPositionInfo(null)));
       // 测试 scannerNeed 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setScannerNeed(null)));
       // 测试 bannerId 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setBannerId(null)));
       // 测试 bannerUrl 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setBannerUrl(null)));
       // 测试 logoId 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setLogoId(null)));
       // 测试 logoUrl 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setLogoUrl(null)));
       // 测试 titlePicUrl 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setTitlePicUrl(null)));
       // 测试 titlePicId 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setTitlePicId(null)));
       // 测试 signTaskCount 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setSignTaskCount(null)));
       // 测试 createTime 不匹配
       signInInfoMapper.insert(cloneIgnoreId(dbSignInInfo, o -> o.setCreateTime(null)));
       // 准备参数
       SignInInfoPageReqVO reqVO = new SignInInfoPageReqVO();
       reqVO.setCoverPicId(null);
       reqVO.setCoverPicUrl(null);
       reqVO.setTitle(null);
       reqVO.setDescription(null);
       reqVO.setStartDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setSignInTimeType(null);
       reqVO.setPersonInfoNeed(null);
       reqVO.setPositionNeed(null);
       reqVO.setPositionInfo(null);
       reqVO.setScannerNeed(null);
       reqVO.setBannerId(null);
       reqVO.setBannerUrl(null);
       reqVO.setLogoId(null);
       reqVO.setLogoUrl(null);
       reqVO.setTitlePicUrl(null);
       reqVO.setTitlePicId(null);
       reqVO.setSignTaskCount(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<SignInInfoDO> pageResult = signInInfoService.getSignInInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSignInInfo, pageResult.getList().get(0));
    }

}
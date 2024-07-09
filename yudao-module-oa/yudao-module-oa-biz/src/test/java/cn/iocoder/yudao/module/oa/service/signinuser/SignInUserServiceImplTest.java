package cn.iocoder.yudao.module.oa.service.signinuser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.oa.controller.admin.signinuser.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinuser.SignInUserDO;
import cn.iocoder.yudao.module.oa.dal.mysql.signinuser.SignInUserMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.oa.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link SignInUserServiceImpl} 的单元测试类
 *
 * @author 超级管理员
 */
@Import(SignInUserServiceImpl.class)
public class SignInUserServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SignInUserServiceImpl signInUserService;

    @Resource
    private SignInUserMapper signInUserMapper;

    @Test
    public void testCreateSignInUser_success() {
        // 准备参数
        SignInUserSaveReqVO createReqVO = randomPojo(SignInUserSaveReqVO.class).setId(null);

        // 调用
        Long signInUserId = signInUserService.createSignInUser(createReqVO);
        // 断言
        assertNotNull(signInUserId);
        // 校验记录的属性是否正确
        SignInUserDO signInUser = signInUserMapper.selectById(signInUserId);
        assertPojoEquals(createReqVO, signInUser, "id");
    }

    @Test
    public void testUpdateSignInUser_success() {
        // mock 数据
        SignInUserDO dbSignInUser = randomPojo(SignInUserDO.class);
        signInUserMapper.insert(dbSignInUser);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SignInUserSaveReqVO updateReqVO = randomPojo(SignInUserSaveReqVO.class, o -> {
            o.setId(dbSignInUser.getId()); // 设置更新的 ID
        });

        // 调用
        signInUserService.updateSignInUser(updateReqVO);
        // 校验是否更新正确
        SignInUserDO signInUser = signInUserMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, signInUser);
    }

    @Test
    public void testUpdateSignInUser_notExists() {
        // 准备参数
        SignInUserSaveReqVO updateReqVO = randomPojo(SignInUserSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> signInUserService.updateSignInUser(updateReqVO), SIGN_IN_USER_NOT_EXISTS);
    }

    @Test
    public void testDeleteSignInUser_success() {
        // mock 数据
        SignInUserDO dbSignInUser = randomPojo(SignInUserDO.class);
        signInUserMapper.insert(dbSignInUser);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbSignInUser.getId();

        // 调用
        signInUserService.deleteSignInUser(id);
       // 校验数据不存在了
       assertNull(signInUserMapper.selectById(id));
    }

    @Test
    public void testDeleteSignInUser_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> signInUserService.deleteSignInUser(id), SIGN_IN_USER_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSignInUserPage() {
       // mock 数据
       SignInUserDO dbSignInUser = randomPojo(SignInUserDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setMeetingId(null);
           o.setCreateTime(null);
       });
       signInUserMapper.insert(dbSignInUser);
       // 测试 userId 不匹配
       signInUserMapper.insert(cloneIgnoreId(dbSignInUser, o -> o.setUserId(null)));
       // 测试 meetingId 不匹配
       signInUserMapper.insert(cloneIgnoreId(dbSignInUser, o -> o.setMeetingId(null)));
       // 测试 createTime 不匹配
       signInUserMapper.insert(cloneIgnoreId(dbSignInUser, o -> o.setCreateTime(null)));
       // 准备参数
       SignInUserPageReqVO reqVO = new SignInUserPageReqVO();
       reqVO.setUserId(null);
       reqVO.setMeetingId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<SignInUserDO> pageResult = signInUserService.getSignInUserPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSignInUser, pageResult.getList().get(0));
    }

}
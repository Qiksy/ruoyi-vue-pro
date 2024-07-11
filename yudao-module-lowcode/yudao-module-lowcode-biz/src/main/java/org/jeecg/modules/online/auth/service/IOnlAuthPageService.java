package org.jeecg.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/service/IOnlAuthPageService.class */
public interface IOnlAuthPageService extends IService<OnlAuthPage> {
    void disableAuthColumn(AuthColumnVO authColumnVO);

    void enableAuthColumn(AuthColumnVO authColumnVO);

    void switchAuthColumn(AuthColumnVO authColumnVO);

    void switchFormShow(String str, String str2, boolean z);

    void switchFormEditable(String str, String str2, boolean z);

    void switchListShow(String str, String str2, boolean z);

    List<AuthPageVO> queryRoleAuthByFormId(String str, String str2, int i);

    List<AuthPageVO> queryRoleDataAuth(String str, String str2);

    List<AuthPageVO> queryAuthByFormId(String str, int i);

    List<String> queryRoleNoAuthCode(String str, Integer num, Integer num2);

    List<String> queryFormDisabledCode(String str);

    List<String> queryHideCode(String str, String str2, boolean z);

    List<String> queryListHideColumn(String str, String str2);

    List<String> queryFormHideColumn(String str, String str2);

    List<String> queryFormHideButton(String str, String str2);

    List<String> queryHideCode(String str, boolean z);

    List<String> queryListHideButton(String str, String str2);
}

package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.CgformEnum;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.MyClassLoader;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaImportInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.cgform.enhance.impl.http.CgformEnhanceHttpFormImpl;
import org.jeecg.modules.online.cgform.enhance.impl.http.CgformEnhanceHttpListImpl;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.jeecg.modules.online.cgform.mapper.OnlCgformButtonMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJsMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.model.OnlGenerateModel;
import org.jeecg.modules.online.cgform.model.TreeSelectColumn;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.database.CgformConfigModel;
import org.jeecg.modules.online.config.database.DataBaseConfig;
import org.jeecg.modules.online.config.database.dmDataBaseConfig;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.template.DbTableProcess;
import org.jeecg.modules.online.config.template.DbTableUtil;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOne;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOneToMany;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.jeecgframework.codegenerate.generate.pojo.TableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.MainTableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.SubTableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* compiled from: OnlCgformHeadServiceImpl.java */
@Service("onlCgformHeadServiceImpl")
/* renamed from: org.jeecg.modules.online.cgform.service.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/d.class */
public class OnlCgformHeadServiceImpl extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformHeadServiceImpl.class);

    @Autowired
    private IOnlCgformFieldService fieldService;

    @Autowired
    private IOnlCgformIndexService indexService;

    @Autowired
    private OnlCgformEnhanceJsMapper onlCgformEnhanceJsMapper;

    @Autowired
    private OnlCgformButtonMapper onlCgformButtonMapper;

    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;

    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private DataBaseConfig dataBaseConfig;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    private IOnlAuthRelationService onlAuthRelationService;

    @Autowired
    private CgformEnhanceHttpFormImpl cgformEnhanceJavaHttp;

    @Autowired
    private CgformEnhanceHttpListImpl cgformEnhanceJavaListHttp;

    @Value("${jeecg.online.datasource:}")
    private String onlineDatasource;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseApi;

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> addAll(OnlCgformModel model) {
        String replace = UUID.randomUUID().toString().replace("-", "");
        OnlCgformHead head = model.getHead();
        List<OnlCgformField> fields = model.getFields();
        List<OnlCgformIndex> indexs = model.getIndexs();
        head.setId(replace);
        boolean z = false;
        for (int i = 0; i < fields.size(); i++) {
            OnlCgformField onlCgformField = fields.get(i);
            onlCgformField.setId(null);
            onlCgformField.setCgformHeadId(replace);
            if (onlCgformField.getOrderNum() == null) {
                onlCgformField.setOrderNum(i);
            }
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable()) && oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                z = true;
            }
            setDefaultLength(onlCgformField);
            if (onlCgformField.getDbIsPersist() == null) {
                onlCgformField.setDbIsPersist(OnlineConst.isPersist);
            }
        }
        for (OnlCgformIndex onlCgformIndex : indexs) {
            onlCgformIndex.setId(null);
            onlCgformIndex.setCgformHeadId(replace);
            onlCgformIndex.setIsDbSynch("N");
            onlCgformIndex.setDelFlag(CommonConstant.DEL_FLAG_0);
        }
        head.setIsDbSynch("N");
        head.setQueryMode("single");
        head.setTableVersion(1);
        head.setCopyType(0);
        if (head.getTableType() == 3 && head.getTabOrderNum() == null) {
            head.setTabOrderNum(1);
        }
        super.save(head);
        this.fieldService.saveBatch(fields);
        this.indexService.saveBatch(indexs);
        m335a(head, fields);
        if (head.getTableType() == 3 && z) {
            this.onlCgformFieldService.clearCacheOnlineConfig();
        }
        return Result.ok("添加成功");
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public Result<?> editAll(OnlCgformModel model) {
        OnlCgformHead head = model.getHead();
        OnlCgformHead onlCgformHead = super.getById(head.getId());
        if (onlCgformHead == null) {
            return Result.error("未找到对应实体");
        }
        String isDbSynch = onlCgformHead.getIsDbSynch();
        if (CgformUtil.m204a(onlCgformHead, head)) {
            isDbSynch = "N";
        }
        Integer tableVersion = onlCgformHead.getTableVersion();
        if (tableVersion == null) {
            tableVersion = 1;
        }
        head.setTableVersion(tableVersion + 1);
        List<OnlCgformField> fields = model.getFields();
        List<OnlCgformIndex> indexs = model.getIndexs();
        ArrayList<OnlCgformField> arrayList = new ArrayList<>();
        ArrayList<OnlCgformField> arrayList2 = new ArrayList<>();
        for (OnlCgformField onlCgformField : fields) {
            String valueOf = String.valueOf(onlCgformField.getId());
            setDefaultLength(onlCgformField);
            if (valueOf.length() == 32) {
                arrayList2.add(onlCgformField);
            } else if (!"_pk".equals(valueOf)) {
                onlCgformField.setId(null);
                onlCgformField.setCgformHeadId(head.getId());
                arrayList.add(onlCgformField);
            }
            if (onlCgformField.getDbIsPersist() == null) {
                onlCgformField.setDbIsPersist(OnlineConst.isPersist);
            }
        }
        if (!arrayList.isEmpty() && m330a(arrayList)) {
            isDbSynch = "N";
        }
        int i = 0;
        for (OnlCgformField onlCgformField2 : arrayList2) {
            OnlCgformField onlCgformField3 = this.fieldService.getById(onlCgformField2.getId());
            removeSubTableRelation(onlCgformField3.getMainTable(), head.getTableName());
            if (CgformUtil.m202a(onlCgformField3, onlCgformField2)) {
                isDbSynch = "N";
            }
            if ((onlCgformField3.getOrderNum() == null ? 0 : onlCgformField3.getOrderNum()) > i) {
                i = onlCgformField3.getOrderNum();
            }
            if ("Y".equals(onlCgformHead.getIsDbSynch()) && !onlCgformField2.getDbFieldName().equals(onlCgformField3.getDbFieldName())) {
                onlCgformField2.setDbFieldNameOld(onlCgformField3.getDbFieldName());
            }
            UpdateWrapper<OnlCgformField> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(OnlCgformField::getId, onlCgformField2.getId());
            if (onlCgformField2.getFieldValidType() == null) {
                updateWrapper.lambda().set(OnlCgformField::getFieldValidType, "");
            }
            this.fieldService.update(onlCgformField2, updateWrapper);
        }
        for (OnlCgformField onlCgformField4 : arrayList) {
            if (onlCgformField4.getOrderNum() == null) {
                i++;
                onlCgformField4.setOrderNum(i);
            }
            this.fieldService.save(onlCgformField4);
        }
        List<OnlCgformIndex> cgformIndexsByCgformId = this.indexService.getCgformIndexsByCgformId(head.getId());
        ArrayList<OnlCgformIndex> arrayList3 = new ArrayList<>();
        ArrayList<OnlCgformIndex> arrayList4 = new ArrayList<>();
        for (OnlCgformIndex onlCgformIndex : indexs) {
            if (String.valueOf(onlCgformIndex.getId()).length() == 32) {
                arrayList4.add(onlCgformIndex);
            } else {
                onlCgformIndex.setId(null);
                onlCgformIndex.setIsDbSynch("N");
                onlCgformIndex.setDelFlag(CommonConstant.DEL_FLAG_0);
                onlCgformIndex.setCgformHeadId(head.getId());
                arrayList3.add(onlCgformIndex);
            }
        }
        for (OnlCgformIndex onlCgformIndex2 : cgformIndexsByCgformId) {
            if (indexs.stream().noneMatch(onlCgformIndex3 -> onlCgformIndex2.getId().equals(onlCgformIndex3.getId()))) {
                onlCgformIndex2.setDelFlag(CommonConstant.DEL_FLAG_1);
                arrayList4.add(onlCgformIndex2);
                isDbSynch = "N";
            }
        }
        if (!arrayList3.isEmpty()) {
            isDbSynch = "N";
            this.indexService.saveBatch(arrayList3);
        }
        for (OnlCgformIndex onlCgformIndex4 : arrayList4) {
            if (CgformUtil.m203a(this.indexService.getById(onlCgformIndex4.getId()), onlCgformIndex4)) {
                isDbSynch = "N";
                onlCgformIndex4.setIsDbSynch("N");
            }
            this.indexService.updateById(onlCgformIndex4);
        }
        if (!model.getDeleteFieldIds().isEmpty()) {
            for (String str : model.getDeleteFieldIds()) {
                OnlCgformField onlCgformField5 = (OnlCgformField) this.fieldService.getById(str);
                if (onlCgformField5 != null) {
                    if (OnlineConst.isPersist.equals(onlCgformField5.getDbIsPersist())) {
                        isDbSynch = "N";
                    }
                    removeSubTableRelation(onlCgformField5.getMainTable(), head.getTableName());
                    this.fieldService.removeById(str);
                }
            }
        }
        head.setIsDbSynch(isDbSynch);
        super.updateById(head);
        m335a(head, fields);
        m347b(head, fields);
        return Result.ok("全部修改成功");
    }

    /* renamed from: a */
    private boolean m330a(List<OnlCgformField> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        boolean z = false;
        Iterator<OnlCgformField> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (OnlineConst.isPersist.equals(it.next().getDbIsPersist())) {
                z = true;
                break;
            }
        }
        return z;
    }

    /**
     * 这也是取消和主表的关系的
     * @param tableName 主表名
     * @param tableName 子表的表名
     */
    /* renamed from: a */
    private void removeSubTableRelation(String tableName, String subTable) {
        if (oConvertUtils.isNotEmpty(tableName)) {
            OnlCgformHead onlCgformHead = this.baseMapper.selectOne( new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tableName));
            if (onlCgformHead != null && oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
                String[] split = onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR);
                ArrayList<String> arrayList = new ArrayList<>();
                for (String str3 : split) {
                    if (!str3.equals(subTable)) {
                        arrayList.add(str3);
                    }
                }
                onlCgformHead.setSubTableStr(String.join(CgformUtil.COMMA_SEPARATOR, arrayList));
                this.baseMapper.updateById(onlCgformHead);
            }
        }
    }

    /**
     * 同步在线表单到数据库
     * @param code
     * @param syncMethod
     * @throws HibernateException
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     * @throws DBException
     */
    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void doDbSynch(String code, String syncMethod) throws HibernateException, IOException, TemplateException, SQLException, DBException {
        OnlCgformHead onlCgformHead = getById(code);
        if (onlCgformHead == null) {
            throw new DBException("实体配置不存在");
        }
        String tableName = onlCgformHead.getTableName();
        //查询需要持久化到数据库的字段
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, OnlineConst.isPersist);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> fieldList = this.fieldService.list(lambdaQueryWrapper);

        //建立一个表配置对象
        CgformConfigModel cgformConfigModel = new CgformConfigModel();
        cgformConfigModel.setTableName(tableName); //表名
        cgformConfigModel.setJformPkType(onlCgformHead.getIdType()); // 主键类型
        cgformConfigModel.setJformPkSequence(onlCgformHead.getIdSequence());  //主键生成序列
        cgformConfigModel.setContent(onlCgformHead.getTableTxt()); //表注释
        cgformConfigModel.setColumns(fieldList); //字段列表

        //获取当前数据库的配置
        DataBaseConfig onlineDataBaseConfig = getOnlineDataBaseConfig();
        cgformConfigModel.setDbConfig(onlineDataBaseConfig); //设置进去
        //获取数据库类型
        DbType type = DbTableUtil.getDbTypeByonfig(onlineDataBaseConfig);

        //如果是正常同步，并且数据库类型不是sqlite
        if (CgformUtil.SYNC_TYPE_NORMAL.equals(syncMethod) && !type.equals(DbType.SQLITE)) {

            System.currentTimeMillis();
            if (DbTableUtil.isTableExistsInDatabase(tableName, onlineDataBaseConfig)) {
                // 如果表已经存在

                //1. 执行表结构的变更
                DbTableProcess dbTableProcess = new DbTableProcess(onlineDataBaseConfig);
                for (String str : dbTableProcess.getUpdateSQL(cgformConfigModel)) {
                    if (!oConvertUtils.isEmpty(str) && !oConvertUtils.isEmpty(str.trim())) {
                        String[] split = str.split(";");
                        for (String str2 : split) {
                            if (!oConvertUtils.isEmpty(str2) && !oConvertUtils.isEmpty(str2.trim())) {
                                // 执行sql
                                this.baseMapper.executeDDL(str2);
                            }
                        }
                    }
                }

                //2.执行表的索引的变更
                List<OnlCgformIndex> indexList = this.indexService.list(new LambdaQueryWrapper<OnlCgformIndex>().eq(OnlCgformIndex::getCgformHeadId, code));
                for (OnlCgformIndex onlCgformIndex : indexList ) {
                    if ("N".equals(onlCgformIndex.getIsDbSynch()) || CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                        if (this.indexService.isExistIndex(dbTableProcess.m486b(onlCgformIndex.getIndexName(), tableName))) {
                            try {
                                this.baseMapper.executeDDL(dbTableProcess.m485a(onlCgformIndex.getIndexName(), tableName));
                                if (CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                                    this.indexService.removeById(onlCgformIndex.getId());
                                }
                            } catch (Exception e) {
                                logger.error("删除表【" + tableName + "】索引(" + onlCgformIndex.getIndexName() + ")失败!", e);
                            }
                        } else if (CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                            this.indexService.removeById(onlCgformIndex.getId());
                        }
                    }
                }
            } else {
                // 表不存在，直接更新
                DbTableProcess.handleCreateNewTableByFormModel(cgformConfigModel);
            }


        } else if (CgformUtil.SYNC_TYPE_FORCE.equals(syncMethod) || type.equals(DbType.SQLITE)) {
            // 这里是强制更新（也就是先删除表数据，再进行同步）
            this.baseMapper.executeDDL(DbTableUtil.getTableHandle().dropTableSQL(tableName));
            DbTableProcess.handleCreateNewTableByFormModel(cgformConfigModel);
        }

        // 创建表的索引数据
        this.indexService.createIndex(code, DbTableUtil.getDatabaseType(), tableName);
        //更新同步完成表示到数据库
        onlCgformHead.setIsDbSynch("Y");
        if (onlCgformHead.getTableVersion() == 1) {
            onlCgformHead.setTableVersion(2);
        }
        updateById(onlCgformHead);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void deleteRecordAndTable(String id) throws DBException, SQLException {
        OnlCgformHead onlCgformHead = getById(id);
        if (onlCgformHead == null) {
            throw new DBException("实体配置不存在");
        }
        System.currentTimeMillis();
        if (DbTableUtil.isTableExistsInDatabase(onlCgformHead.getTableName())) {
            this.baseMapper.executeDDL(DbTableUtil.getTableHandle().dropTableSQL(onlCgformHead.getTableName()));
        }
        deleteRecord(id);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void deleteRecord(String id) throws DBException, SQLException {
        OnlCgformHead onlCgformHead = getById(id);
        if (onlCgformHead == null) {
            throw new DBException("实体配置不存在");
        }
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformHead::getPhysicId, id);  //原始表id
        List<OnlCgformHead> selectList = this.baseMapper.selectList(lambdaQueryWrapper);
        if (selectList != null && !selectList.isEmpty()) {
            for (OnlCgformHead o : selectList) {
                deleteAll(o.getId());
            }
        }
        removeSubTableRelation(onlCgformHead);  //取消和主表的依赖
        deleteAll(id); //删除这个表
        if (onlCgformHead.getTableType() == 3) {
            //如果是子表，还需要清除掉所有的缓存
            this.onlCgformFieldService.clearCacheOnlineConfig();
        }
    }

    /**
     * 删除某个在线表单的所有数据
     * @param id 在线表单的某个数据
     */
    /* renamed from: a */
    private void deleteAll(String id) {
        //删除表
        this.baseMapper.deleteById(id);

        //删除字段
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, id);
        this.fieldService.remove(lambdaQueryWrapper);
        //删除索引
        LambdaQueryWrapper<OnlCgformIndex> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(OnlCgformIndex::getCgformHeadId, id);
        this.indexService.remove(lambdaQueryWrapper2);
        //删除授权权限
        LambdaQueryWrapper<OnlAuthRelation> lambdaQueryWrapper3 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper3.eq(OnlAuthRelation::getCgformId, id);
        this.onlAuthRelationService.remove(lambdaQueryWrapper3);
        //删除权限规则数据
        LambdaQueryWrapper<OnlAuthData> lambdaQueryWrapper4 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper4.eq(OnlAuthData::getCgformId, id);
        this.onlAuthDataService.remove(lambdaQueryWrapper4);
        //删除按钮/字段权限数据
        LambdaQueryWrapper<OnlAuthPage> lambdaQueryWrapper5 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper5.eq(OnlAuthPage::getCgformId, id);
        this.onlAuthPageService.remove(lambdaQueryWrapper5);
    }

    /**
     * 这个方法的作用，就是在删除一个表的时候，如果它是某个表的子表，则跟主表取消关联
     * @param onlCgformHead
     */
    /* renamed from: a */
    private void removeSubTableRelation(OnlCgformHead onlCgformHead) {
        OnlCgformHead mainTableHead;  //主表
        if (onlCgformHead.getTableType() == 3) {  //这个可能是子表的吧
            String mainTable = null;
            for (OnlCgformField onlCgformField : this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()))) {
                mainTable = onlCgformField.getMainTable();
                if (oConvertUtils.isNotEmpty(mainTable)) {
                    //如果有外键，旧删除
                    break;
                }
            }
            if (oConvertUtils.isNotEmpty(mainTable) && (mainTableHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, mainTable))) != null) {
                String subTableStr = mainTableHead.getSubTableStr();
                if (oConvertUtils.isNotEmpty(subTableStr)) {
                    //子表不为空
                    List<String> list = new ArrayList<>(Arrays.asList(subTableStr.split(CgformUtil.COMMA_SEPARATOR)));
                    // 删除目前的这个表
                    list.remove(onlCgformHead.getTableName());
                    mainTableHead.setSubTableStr(String.join(CgformUtil.COMMA_SEPARATOR, list));
                    this.baseMapper.updateById(mainTableHead);
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<Map<String, Object>> queryListData(String sql) {
        return this.baseMapper.queryList(sql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceJs queryEnhance(String code, String type) {
        return this.onlCgformEnhanceJsMapper.selectOne(new LambdaQueryWrapper<OnlCgformEnhanceJs>().eq(OnlCgformEnhanceJs::getCgJsType, type).eq(OnlCgformEnhanceJs::getCgformHeadId, code));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceSql queryEnhanceSql(String formId, String buttonCode) {
        return this.onlCgformEnhanceSqlMapper.selectOne(new LambdaQueryWrapper<OnlCgformEnhanceSql>().eq(OnlCgformEnhanceSql::getCgformHeadId, formId).eq(OnlCgformEnhanceSql::getButtonCode, buttonCode));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        return this.onlCgformEnhanceJavaMapper.selectOne(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<OnlCgformButton> queryButtonList(String code, boolean isListButton) {
        LambdaQueryWrapper<OnlCgformButton> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, code);
        if (isListButton) {
            lambdaQueryWrapper.in(OnlCgformButton::getButtonStyle, "link", "button");
        } else {
            lambdaQueryWrapper.eq(OnlCgformButton::getButtonStyle, CgformUtil.FORM);
        }
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<OnlCgformButton> queryButtonList(String code) {
        LambdaQueryWrapper<OnlCgformButton> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, code);
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<String> queryOnlinetables() {
        return this.baseMapper.queryOnlinetables();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void saveDbTable2Online(String tbname) {
        OnlCgformHead onlCgformHead = new OnlCgformHead();
        onlCgformHead.setTableType(1);
        onlCgformHead.setIsCheckbox("Y");
        onlCgformHead.setIsDbSynch("Y");
        onlCgformHead.setIsTree("N");
        onlCgformHead.setIsPage("Y");
        onlCgformHead.setQueryMode(CgReportConstant.f467K);
        onlCgformHead.setTableName(tbname.toLowerCase());
        onlCgformHead.setTableTxt(tbname);
        onlCgformHead.setTableVersion(1);
        onlCgformHead.setFormTemplate("1");
        onlCgformHead.setCopyType(0);
        onlCgformHead.setIsDesForm("N");
        onlCgformHead.setScroll(1);
        onlCgformHead.setThemeTemplate(CgformUtil.SYNC_TYPE_NORMAL);
        String generate = UUIDGenerator.generate();
        onlCgformHead.setId(generate);
        ArrayList<OnlCgformField> arrayList = new ArrayList<>();
        try {
            List<ColumnVo> readOriginalTableColumn = DbReadTableUtil.readOriginalTableColumn(tbname);
            for (int i = 0; i < readOriginalTableColumn.size(); i++) {
                ColumnVo columnVo = readOriginalTableColumn.get(i);
                String fieldDbName = columnVo.getFieldDbName();
                OnlCgformField onlCgformField = new OnlCgformField();
                onlCgformField.setCgformHeadId(generate);
                onlCgformField.setDbFieldNameOld(columnVo.getFieldDbName().toLowerCase());
                onlCgformField.setDbFieldName(columnVo.getFieldDbName().toLowerCase());
                if (oConvertUtils.isNotEmpty(columnVo.getFiledComment())) {
                    onlCgformField.setDbFieldTxt(columnVo.getFiledComment());
                } else {
                    onlCgformField.setDbFieldTxt(columnVo.getFieldName());
                }
                onlCgformField.setDbIsKey(0);
                onlCgformField.setIsShowForm(1);
                onlCgformField.setIsQuery(0);
                onlCgformField.setFieldMustInput("0");
                onlCgformField.setIsShowList(1);
                onlCgformField.setOrderNum(i + 1);
                onlCgformField.setQueryMode("single");
                onlCgformField.setDbLength(oConvertUtils.getInt(columnVo.getPrecision()));
                onlCgformField.setFieldLength(120);
                onlCgformField.setDbPointLength(oConvertUtils.getInt(columnVo.getScale()));
                onlCgformField.setFieldShowType(ExtendJsonKey.TEXT);
                onlCgformField.setDbIsNull("Y".equals(columnVo.getNullable()) ? 1 : 0);
                onlCgformField.setIsReadOnly(0);
                if ("id".equalsIgnoreCase(fieldDbName)) {
                    if (Arrays.asList("java.lang.Integer", "java.lang.Long").contains(columnVo.getFieldType())) {
                        onlCgformHead.setIdType("NATIVE");
                    } else {
                        onlCgformHead.setIdType("UUID");
                    }
                    onlCgformField.setDbIsKey(1);
                    onlCgformField.setIsShowForm(0);
                    onlCgformField.setIsShowList(0);
                    onlCgformField.setIsReadOnly(1);
                }
                if ("create_by".equalsIgnoreCase(fieldDbName) || "create_time".equalsIgnoreCase(fieldDbName) || "update_by".equalsIgnoreCase(fieldDbName) || "update_time".equalsIgnoreCase(fieldDbName) || "sys_org_code".equalsIgnoreCase(fieldDbName)) {
                    onlCgformField.setIsShowForm(0);
                    onlCgformField.setIsShowList(0);
                }
                if ("java.lang.Integer".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("int");
                } else if ("java.lang.Long".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("int");
                } else if ("java.util.Date".equalsIgnoreCase(columnVo.getFieldType())) {
                    if ("datetime".equals(columnVo.getFieldDbType())) {
                        onlCgformField.setDbType("Datetime");
                        onlCgformField.setFieldShowType("datetime");
                    } else {
                        onlCgformField.setDbType("Date");
                        onlCgformField.setFieldShowType(OnlFormShowType.DATE);
                    }
                } else if ("java.lang.Double".equalsIgnoreCase(columnVo.getFieldType()) || "java.lang.Float".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("double");
                } else if ("java.math.BigDecimal".equalsIgnoreCase(columnVo.getFieldType()) || "BigDecimal".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("BigDecimal");
                } else if ("byte[]".equalsIgnoreCase(columnVo.getFieldType()) || columnVo.getFieldType().contains("blob")) {
                    onlCgformField.setDbType("Blob");
                    columnVo.setCharmaxLength((String) null);
                } else if ("java.lang.Object".equals(columnVo.getFieldType()) && (ExtendJsonKey.TEXT.equalsIgnoreCase(columnVo.getFieldDbType()) || "ntext".equalsIgnoreCase(columnVo.getFieldDbType()))) {
                    onlCgformField.setDbType(DataBaseConst.TEXT);
                    onlCgformField.setFieldShowType(DataBaseConst.TEXTAREA);
                } else if ("java.lang.Object".equals(columnVo.getFieldType()) && "image".equalsIgnoreCase(columnVo.getFieldDbType())) {
                    onlCgformField.setDbType("Blob");
                } else {
                    onlCgformField.setDbType(DataBaseConst.STRING);
                }
                if (!oConvertUtils.isEmpty(columnVo.getPrecision()) || !oConvertUtils.isNotEmpty(columnVo.getCharmaxLength())) {
                    if (oConvertUtils.isNotEmpty(columnVo.getPrecision())) {
                        onlCgformField.setDbLength(Integer.valueOf(columnVo.getPrecision()));
                    } else if (onlCgformField.getDbType().equals("int")) {
                        onlCgformField.setDbLength(10);
                    }
                    if (oConvertUtils.isNotEmpty(columnVo.getScale())) {
                        onlCgformField.setDbPointLength(Integer.valueOf(columnVo.getScale()));
                    }
                } else if (Long.valueOf(columnVo.getCharmaxLength()) >= 3000) {
                    onlCgformField.setDbType(DataBaseConst.TEXT);
                    onlCgformField.setFieldShowType(DataBaseConst.TEXTAREA);
                    try {
                        onlCgformField.setDbLength(Integer.valueOf(columnVo.getCharmaxLength()));
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                } else {
                    onlCgformField.setDbLength(Integer.valueOf(columnVo.getCharmaxLength()));
                }
                if (oConvertUtils.getInt(columnVo.getPrecision()) == -1 && oConvertUtils.getInt(columnVo.getScale()) == 0) {
                    onlCgformField.setDbType(DataBaseConst.TEXT);
                }
                if ("Blob".equals(onlCgformField.getDbType()) || DataBaseConst.TEXT.equals(onlCgformField.getDbType()) || "Date".equals(onlCgformField.getDbType())) {
                    onlCgformField.setDbLength(0);
                    onlCgformField.setDbPointLength(0);
                }
                onlCgformField.setDbIsPersist(OnlineConst.isPersist);
                arrayList.add(onlCgformField);
            }
        } catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        if (oConvertUtils.isEmpty(onlCgformHead.getFormCategory())) {
            onlCgformHead.setFormCategory("bdfl_include");
        }
        save(onlCgformHead);
        this.fieldService.saveBatch(arrayList);
    }

    /* renamed from: b */
    private boolean m334b(String str, String str2) {
        if (oConvertUtils.isEmpty(str2)) {
            return false;
        }
        for (String str3 : str2.split(CgformUtil.COMMA_SEPARATOR)) {
            if (str3.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private void m335a(OnlCgformHead onlCgformHead, List<OnlCgformField> list) {
        OnlCgformHead onlCgformHead2;
        if (onlCgformHead.getTableType() == 3) {
            OnlCgformHead onlCgformHead3 = this.baseMapper.selectById(onlCgformHead.getId());
            for (int i = 0; i < list.size(); i++) {
                String mainTable = list.get(i).getMainTable();
                if (!oConvertUtils.isEmpty(mainTable) && (onlCgformHead2 = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, mainTable))) != null) {
                    String subTableStr = onlCgformHead2.getSubTableStr();
                    if (oConvertUtils.isEmpty(subTableStr)) {
                        subTableStr = onlCgformHead3.getTableName();
                    } else if (!m334b(onlCgformHead3.getTableName(), subTableStr)) {
                        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subTableStr.split(CgformUtil.COMMA_SEPARATOR)));
                        int i2 = 0;
                        while (true) {
                            if (i2 >= arrayList.size()) {
                                break;
                            }
                            OnlCgformHead onlCgformHead4 = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, (String) arrayList.get(i2)));
                            if (onlCgformHead4 == null || onlCgformHead3.getTabOrderNum() >= oConvertUtils.getInt(onlCgformHead4.getTabOrderNum(), 0)) {
                                i2++;
                            } else {
                                arrayList.add(i2, onlCgformHead3.getTableName());
                                break;
                            }
                        }
                        if (!arrayList.contains(onlCgformHead3.getTableName())) {
                            arrayList.add(onlCgformHead3.getTableName());
                        }
                        subTableStr = String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
                    }
                    onlCgformHead2.setSubTableStr(subTableStr);
                    this.baseMapper.updateById(onlCgformHead2);
                    return;
                }
            }
            return;
        }
        List<OnlCgformHead> selectList = this.baseMapper.selectList(new LambdaQueryWrapper<OnlCgformHead>().like(OnlCgformHead::getSubTableStr, onlCgformHead.getTableName()));
        if (selectList != null && !selectList.isEmpty()) {
            for (OnlCgformHead onlCgformHead5 : selectList) {
                String subTableStr2 = onlCgformHead5.getSubTableStr();
                if (onlCgformHead5.getSubTableStr().equals(onlCgformHead.getTableName())) {
                    subTableStr2 = "";
                } else if (onlCgformHead5.getSubTableStr().startsWith(onlCgformHead.getTableName() + ",")) {
                    subTableStr2 = subTableStr2.replace(onlCgformHead.getTableName() + ",", "");
                } else if (onlCgformHead5.getSubTableStr().endsWith("," + onlCgformHead.getTableName())) {
                    subTableStr2 = subTableStr2.replace("," + onlCgformHead.getTableName(), "");
                } else if (onlCgformHead5.getSubTableStr().contains("," + onlCgformHead.getTableName() + ",")) {
                    subTableStr2 = subTableStr2.replace("," + onlCgformHead.getTableName() + ",", CgformUtil.COMMA_SEPARATOR);
                }
                onlCgformHead5.setSubTableStr(subTableStr2);
                this.baseMapper.updateById(onlCgformHead5);
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public String saveManyFormData(String code, JSONObject json, String token) throws DBException, BusinessException {
        OnlCgformHead onlCgformHead;
        OnlCgformHead table = getTable(code);
        executeEnhanceJava(CgformConstant.ADD, CgformUtil.f248aq, table, json);
        String m235f = CgformUtil.m235f(table.getTableName());
        if (table.getTableType() == 2) {
            String subTableStr = table.getSubTableStr();
            if (StringUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    JSONArray jSONArray = json.getJSONArray(str);
                    if (jSONArray != null && !jSONArray.isEmpty() && (onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str))) != null) {
                        List<OnlCgformField> list = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : list) {
                            if (!StringUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                String mainField = onlCgformField.getMainField();
                                if (json.get(mainField.toLowerCase()) != null) {
                                    str3 = json.getString(mainField.toLowerCase());
                                }
                                if (json.get(mainField.toUpperCase()) != null) {
                                    str3 = json.getString(mainField.toUpperCase());
                                }
                            }
                        }
                        for (int i = 0; i < jSONArray.size(); i++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);
                            if (str3 != null) {
                                jSONObject.put(str2, str3);
                            }
                            this.fieldService.saveFormData(list, str, jSONObject);
                        }
                    }
                }
            }
        }
        if ("Y".equals(table.getIsTree())) {
            this.fieldService.saveTreeFormData(table.getId(), m235f, json, table.getTreeIdField(), table.getTreeParentIdField());
        } else {
            this.fieldService.saveFormData(table.getId(), m235f, json, false);
        }
        executeEnhanceSql(CgformConstant.ADD, table.getId(), json);
        executeEnhanceJava(CgformConstant.ADD, "end", table, json);
        return table.getTableName();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public Map<String, Object> querySubFormData(String table, String mainId) throws DBException {

        OnlCgformHead onlCgformHead = getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, table));
        if (onlCgformHead == null) {
            throw new DBException("数据库子表[" + table + "]不存在");
        }
        List<OnlCgformField> queryFormFields = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
        String str = null;
        Iterator<OnlCgformField> it = queryFormFields.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OnlCgformField next = it.next();
            if (oConvertUtils.isNotEmpty(next.getMainField())) {
                str = next.getDbFieldName();
                break;
            }
        }
        List<Map<String, Object>> querySubFormData = this.fieldService.querySubFormData(queryFormFields, table, str, mainId);
        if (querySubFormData==null){
            throw new DBException("数据库子表[" + table + "]未找到相关信息, 主表ID为" + mainId);
        }

        if (querySubFormData.isEmpty()) {
            throw new DBException("数据库子表[" + table + "]未找到相关信息, 主表ID为" + mainId);
        }
        if (querySubFormData.size() > 1) {
            throw new DBException("数据库子表[" + table + "]存在多条记录, 主表ID为" + mainId);
        }
        return querySubFormData.get(0);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<Map<String, Object>> queryManySubFormData(String table, String mainId) throws DBException {
        OnlCgformHead onlCgformHead = getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, table));
        if (onlCgformHead == null) {
            throw new DBException("数据库子表[" + table + "]不存在");
        }
        List<OnlCgformField> queryFormFields = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
        if (queryFormFields == null || queryFormFields.isEmpty()) {
            throw new DBException("找不到子表字段，请确认配置是否正确!");
        }
        String str = null;
        String str2 = null;
        String str3 = null;
        Iterator<OnlCgformField> it = queryFormFields.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OnlCgformField next = it.next();
            if (oConvertUtils.isNotEmpty(next.getMainField())) {
                str = next.getDbFieldName();
                str2 = next.getMainTable();
                str3 = next.getMainField();
                break;
            }
        }
        ArrayList<OnlCgformField> arrayList = new ArrayList<>();
        OnlCgformField onlCgformField = new OnlCgformField();
        onlCgformField.setDbFieldName(str3);
        arrayList.add(onlCgformField);
        Map<String, Object> queryFormData = this.fieldService.queryFormData(arrayList, str2, mainId);
        List<Map<String, Object>> querySubFormData = null;
        if (str3 != null) {
            querySubFormData = this.fieldService.querySubFormData(queryFormFields, table, str, oConvertUtils.getString(oConvertUtils.getString(queryFormData.get(str3)), oConvertUtils.getString(queryFormData.get(str3.toUpperCase()))));
        }
        if (querySubFormData != null && querySubFormData.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Map<String, Object>> arrayList2 = new ArrayList<>(querySubFormData.size());
        for (Map<String, Object> querySubFormDatum : querySubFormData) {
            arrayList2.add(CgformUtil.m224a(querySubFormDatum));
        }
        return arrayList2;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public Map<String, Object> queryManyFormData(String code, String id) throws DBException {
        OnlCgformHead table = getTable(code);
        List<OnlCgformField> queryFormFields = this.fieldService.queryFormFields(table.getId(), true);
        if (queryFormFields == null || queryFormFields.isEmpty()) {
            throw new DBException("找不到字段，请确认配置是否正确!");
        }
        Map<String, Object> queryFormData = this.fieldService.queryFormData(queryFormFields, table.getTableName(), id);
        if (table.getTableType().intValue() == 2) {
            String subTableStr = table.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null) {
                        List<OnlCgformField> queryFormFields2 = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : queryFormFields2) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                str3 = CgformUtil.m253a(queryFormData, onlCgformField.getMainField());
                            }
                        }
                        List<Map<String, Object>> querySubFormData = this.fieldService.querySubFormData(queryFormFields2, str, str2, str3);
                        if (querySubFormData == null || querySubFormData.isEmpty()) {
                            queryFormData.put(str, new String[0]);
                        } else {
                            queryFormData.put(str, CgformUtil.m227d(querySubFormData));
                        }
                    }
                }
            }
        }
        return queryFormData;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public String editManyFormData(String code, JSONObject json) throws DBException, BusinessException {
        OnlCgformHead table = getTable(code);
        executeEnhanceJava(CgformConstant.EDIT, CgformUtil.f248aq, table, json);
        String tableName = table.getTableName();
        if ("Y".equals(table.getIsTree())) {
            this.fieldService.editTreeFormData(table.getId(), tableName, json, table.getTreeIdField(), table.getTreeParentIdField());
        } else {
            this.fieldService.editFormData(table.getId(), tableName, json, false);
        }
        if (table.getTableType() == 2) {
            String subTableStr = table.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null) {
                        List<OnlCgformField> list = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : list) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                String mainField = onlCgformField.getMainField();
                                if (json.get(mainField.toLowerCase()) != null) {
                                    str3 = json.getString(mainField.toLowerCase());
                                }
                                if (json.get(mainField.toUpperCase()) != null) {
                                    str3 = json.getString(mainField.toUpperCase());
                                }
                            }
                        }
                        if (!oConvertUtils.isEmpty(str3)) {
                            this.fieldService.deleteAutoList(str, str2, str3);
                            JSONArray jSONArray = json.getJSONArray(str);
                            if (jSONArray != null && !jSONArray.isEmpty()) {
                                for (int i = 0; i < jSONArray.size(); i++) {
                                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                                    jSONObject.put(str2, str3);
                                    this.fieldService.saveFormData(list, str, jSONObject);
                                }
                            }
                        }
                    }
                }
            }
        }
        executeEnhanceJava(CgformConstant.EDIT, "end", table, json);
        executeEnhanceSql(CgformConstant.EDIT, table.getId(), json);
        return tableName;
    }

    /* renamed from: a */
    private OnlCgformEnhanceJava m336a(String str, String str2, String str3) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, str);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, str2);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, str3);
        return this.onlCgformEnhanceJavaMapper.selectOne(lambdaQueryWrapper);
    }

    /* renamed from: b */
    private Object m337b(String str, String str2, String str3) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, str);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, str2);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, str3);
        return m339a(this.onlCgformEnhanceJavaMapper.selectOne(lambdaQueryWrapper));
    }

    /* renamed from: a */
    private void m338a(JSONObject jSONObject, Object obj, String str, OnlCgformEnhanceJava onlCgformEnhanceJava) throws BusinessException {
        if ((obj instanceof CgformEnhanceJavaInter)) {
            ((CgformEnhanceJavaInter) obj).execute(str, jSONObject);
        } else if ((obj instanceof CgformEnhanceHttpFormImpl)) {
            ((CgformEnhanceHttpFormImpl) obj).execute(str, jSONObject, onlCgformEnhanceJava);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceJava(String buttonCode, String eventType, OnlCgformHead head, JSONObject json) throws BusinessException {
        OnlCgformEnhanceJava m336a = m336a(buttonCode, eventType, head.getId());
        m338a(json, m339a(m336a), head.getTableName(), m336a);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceExport(OnlCgformHead head, List<Map<String, Object>> dataList) throws BusinessException {
        executeEnhanceList(head, "export", dataList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public EnhanceDataEnum executeEnhanceImport(OnlCgformHead head, JSONObject json) throws BusinessException {
        Object m339a = m339a(m336a("import", CgformUtil.f248aq, head.getId()));
        if ((m339a instanceof CgformEnhanceJavaImportInter)) {
            return ((CgformEnhanceJavaImportInter) m339a).execute(head.getTableName(), json);
        }
        return EnhanceDataEnum.INSERT;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceList(OnlCgformHead head, String buttonCode, List<Map<String, Object>> dataList) throws BusinessException {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformEnhanceJava>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, buttonCode);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, head.getId());
        List selectList = this.onlCgformEnhanceJavaMapper.selectList(lambdaQueryWrapper);
        if (selectList != null && !selectList.isEmpty()) {
            Object m339a = m339a((OnlCgformEnhanceJava) selectList.get(0));
            if ((m339a instanceof CgformEnhanceJavaListInter)) {
                ((CgformEnhanceJavaListInter) m339a).execute(head.getTableName(), dataList);
            } else if ((m339a instanceof CgformEnhanceHttpListImpl)) {
                ((CgformEnhanceHttpListImpl) m339a).execute(head.getTableName(), dataList, (OnlCgformEnhanceJava) selectList.get(0));
            }
        }
    }

    /* renamed from: a */
    private Object m339a(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        if (onlCgformEnhanceJava != null) {
            String cgJavaType = onlCgformEnhanceJava.getCgJavaType();
            String cgJavaValue = onlCgformEnhanceJava.getCgJavaValue();
            if (oConvertUtils.isNotEmpty(cgJavaValue)) {
                Object obj = null;
                if ("class".equals(cgJavaType)) {
                    try {
                        obj = MyClassLoader.getClassByScn(cgJavaValue).getDeclaredConstructor().newInstance();
                    } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else if ("spring".equals(cgJavaType)) {
                    obj = SpringContextUtils.getBean(cgJavaValue);
                } else if ("http".equals(cgJavaType)) {
                    obj = m340b(onlCgformEnhanceJava);
                }
                return obj;
            }
            return null;
        }
        return null;
    }

    /* renamed from: b */
    private Object m340b(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        String buttonCode = onlCgformEnhanceJava.getButtonCode();
        switch (buttonCode) {
            case "add":
            case "edit":
            case "delete":
            case "import":
                return this.cgformEnhanceJavaHttp;
            case "export":
            case "query":
                return this.cgformEnhanceJavaListHttp;
            default:
                return this.cgformEnhanceJavaHttp;
        }
    }

    /* renamed from: c */
    private OnlCgformEnhanceSql m341c(String str, String str2) {
        LambdaQueryWrapper<OnlCgformEnhanceSql> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getButtonCode, str);
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getCgformHeadId, str2);
        return  this.onlCgformEnhanceSqlMapper.selectOne(lambdaQueryWrapper);
    }

    /* renamed from: a */
    private void m342a(JSONObject jSONObject, OnlCgformEnhanceSql onlCgformEnhanceSql) {
        if (onlCgformEnhanceSql != null && oConvertUtils.isNotEmpty(onlCgformEnhanceSql.getCgbSql())) {
            for (String str : CgformUtil.m215a(onlCgformEnhanceSql.getCgbSql(), jSONObject).split(";")) {
                if (str != null && !str.toLowerCase().trim().isEmpty()) {
                    ( this.baseMapper).executeDDL(str);
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeEnhanceSql(String buttonCode, String formId, JSONObject json) {
        m342a(json, m341c(buttonCode, formId));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void executeCustomerButton(String buttonCode, String formId, String dataId) throws BusinessException {
        OnlCgformHead onlCgformHead = getById(formId);
        if (onlCgformHead == null) {
            throw new BusinessException("未找到表配置信息");
        }
        OnlCgformEnhanceJava m336a = m336a(buttonCode, CgformUtil.f248aq, formId);
        OnlCgformEnhanceJava m336a2 = m336a(buttonCode, "end", formId);
        Object m339a = m339a(m336a);
        Object m339a2 = m339a(m336a2);
        OnlCgformEnhanceSql m341c = m341c(buttonCode, formId);
        String tableName = onlCgformHead.getTableName();
        String[] split = dataId.split(CgformUtil.COMMA_SEPARATOR);
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, formId);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
        for (String str : split) {
            JSONObject parseObject = JSONObject.parseObject(JSON.toJSONString(m344a(list, m343d(CgformUtil.m235f(onlCgformHead.getTableName()), CgformUtil.m261k(str)))));
            m338a(parseObject, m339a, tableName, m336a);
            m342a(parseObject, m341c);
            m338a(parseObject, m339a2, tableName, m336a2);
        }
    }

    /* renamed from: d */
    private Map<String, Object> m343d(String str, String str2) {
        return this.baseMapper.queryOneByTableNameAndId(SqlInjectionUtil.getSqlInjectTableName(str), str2);
    }

    /* renamed from: a */
    private Map<String, Object> m344a(List<OnlCgformField> list, Map<String, Object> map) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        for (OnlCgformField onlCgformField : list) {
            String dbType = onlCgformField.getDbType();
            if (!"blob".equalsIgnoreCase(dbType) && !ExtendJsonKey.TEXT.equalsIgnoreCase(dbType)) {
                String dbFieldName = onlCgformField.getDbFieldName();
                hashMap.put(dbFieldName, CgformUtil.m254b(map, dbFieldName));
            }
        }
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<OnlCgformButton> queryValidButtonList(String headId) {
        LambdaQueryWrapper<OnlCgformButton> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, headId);
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformEnhanceJs queryEnhanceJs(String formId, String cgJsType) {
        LambdaQueryWrapper<OnlCgformEnhanceJs> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJs::getCgformHeadId, formId);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJs::getCgJsType, cgJsType);
        return this.onlCgformEnhanceJsMapper.selectOne(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public void deleteOneTableInfo(String formId, String dataId) throws BusinessException {
        OnlCgformHead onlCgformHead = getById(formId);
        if (onlCgformHead == null) {
            throw new BusinessException("未找到表配置信息");
        }
        String m235f = CgformUtil.m235f(onlCgformHead.getTableName());
        Map<String, Object> m343d = m343d(m235f, dataId);
        if (m343d == null) {
            return;
        }
        JSONObject parseObject = JSONObject.parseObject(JSON.toJSONString(CgformUtil.m224a(m343d)));
        executeEnhanceJava(CgformConstant.DELETE, CgformUtil.f248aq, onlCgformHead, parseObject);
        updateParentNode(onlCgformHead, dataId);
        if (onlCgformHead.getTableType() == 2) {
            this.fieldService.deleteAutoListMainAndSub(onlCgformHead, dataId);
        } else {
            ((OnlCgformHeadMapper) this.baseMapper).deleteOne(SqlInjectionUtil.getSqlInjectTableName(m235f), dataId);
        }
        executeEnhanceSql(CgformConstant.DELETE, formId, parseObject);
        executeEnhanceJava(CgformConstant.DELETE, "end", onlCgformHead, parseObject);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Deprecated
    public JSONObject queryFormItem(OnlCgformHead head, String username) {
        List<String> queryFormDisabledCode;
        List<OnlCgformField> queryAvailableFields = this.fieldService.queryAvailableFields(head.getId(), head.getTableName(), head.getTaskId(), false);
        List<String> arrayList = new ArrayList<>();
        if (oConvertUtils.isEmpty(head.getTaskId())) {
            List<String> queryFormDisabledCode2 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
            if (queryFormDisabledCode2 != null && !queryFormDisabledCode2.isEmpty() && queryFormDisabledCode2.get(0) != null) {
                arrayList.addAll(queryFormDisabledCode2);
            }
        } else {
            List<String> queryDisabledFields = this.fieldService.queryDisabledFields(head.getTableName(), head.getTaskId());
            if (queryDisabledFields != null && !queryDisabledFields.isEmpty() && queryDisabledFields.get(0) != null) {
                arrayList.addAll(queryDisabledFields);
            }
        }
        JSONObject m192a = CgformUtil.m192a(queryAvailableFields, arrayList, (TreeSelectColumn) null);
        if (head.getTableType() == 2) {
            String subTableStr = head.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null) {
                        List<OnlCgformField> queryAvailableFields2 = this.fieldService.queryAvailableFields(onlCgformHead.getId(), onlCgformHead.getTableName(), head.getTaskId(), false);

                        if (oConvertUtils.isNotEmpty(head.getTaskId())) {
                            queryFormDisabledCode = this.fieldService.queryDisabledFields(onlCgformHead.getTableName(), head.getTaskId());
                        } else {
                            queryFormDisabledCode = this.onlAuthPageService.queryFormDisabledCode(onlCgformHead.getId());
                        }
                        JSONObject jSONObject = new JSONObject();
                        if (1 == onlCgformHead.getRelationType()) {
                            jSONObject = CgformUtil.m192a(queryAvailableFields2, queryFormDisabledCode, null);
                        } else {
                            jSONObject.put("columns", CgformUtil.m222a(queryAvailableFields2, queryFormDisabledCode));
                        }
                        jSONObject.put("relationType", onlCgformHead.getRelationType());
                        jSONObject.put(CgformUtil.VIEW, "tab");
                        jSONObject.put("order", onlCgformHead.getTabOrderNum());
                        jSONObject.put("formTemplate", onlCgformHead.getFormTemplate());
                        jSONObject.put("describe", onlCgformHead.getTableTxt());
                        jSONObject.put("key", onlCgformHead.getTableName());
                        m192a.getJSONObject(CgformUtil.PROPERTIES).put(onlCgformHead.getTableName(), jSONObject);
                    }
                }
            }
        }
        return m192a;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<String> generateCode(OnlGenerateModel model) throws Exception {
        TableVo tableVo = new TableVo();
        tableVo.setEntityName(model.getEntityName());
        tableVo.setEntityPackage(model.getEntityPackage());
        tableVo.setFtlDescription(model.getFtlDescription());
        tableVo.setTableName(model.getTableName());
        tableVo.setSearchFieldNum(-1);
        List<ColumnVo> arrayList = new ArrayList<>();
        List<ColumnVo> arrayList2 = new ArrayList<>();
        m345a(model.getCode(), arrayList, arrayList2);
        OnlCgformHead onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getId, model.getCode()));
        HashMap<String,String> hashMap = new HashMap<>(5);
        hashMap.put("scroll", onlCgformHead.getScroll() == null ? "0" : onlCgformHead.getScroll().toString());
        String formTemplate = onlCgformHead.getFormTemplate();
        if (oConvertUtils.isEmpty(formTemplate)) {
            tableVo.setFieldRowNum(1);
        } else {
            tableVo.setFieldRowNum(Integer.parseInt(formTemplate));
        }
        if ("Y".equals(onlCgformHead.getIsTree())) {
            hashMap.put("pidField", onlCgformHead.getTreeParentIdField());
            hashMap.put("hasChildren", onlCgformHead.getTreeIdField());
            hashMap.put(ExtendJsonKey.TEXT_FIELD, onlCgformHead.getTreeFieldname());
        }
        if (oConvertUtils.isNotEmpty(model.getVueStyle())) {
            hashMap.put("vueStyle", model.getVueStyle());
        }
        tableVo.setExtendParams(hashMap);
        CgformEnum cgformEnumByConfig = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        List<String> generateCodeFile = null;
        if (cgformEnumByConfig != null) {
            generateCodeFile = new CodeGenerateOne(tableVo, arrayList, arrayList2).generateCodeFile(model.getProjectPath(), cgformEnumByConfig.getTemplatePath(), cgformEnumByConfig.getStylePath());
        }
        if (generateCodeFile == null || generateCodeFile.isEmpty()) {
            generateCodeFile = new ArrayList<>();
            generateCodeFile.add(" :::::: 生成失败ERROR提示 :::::: ");
            generateCodeFile.add("1.JeecgBoot项目所在路径是否含有中文或空格，去掉就好了！参考 http://doc.jeecg.com/2088984");
            generateCodeFile.add("2.采用JAR包上线发布，需要做额外配置！参考 http://doc.jeecg.com/2043922");
        }
        return generateCodeFile;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public List<String> generateOneToMany(OnlGenerateModel model) throws Exception {
        MainTableVo mainTableVo = new MainTableVo();
        mainTableVo.setEntityName(model.getEntityName());
        mainTableVo.setEntityPackage(model.getEntityPackage());
        mainTableVo.setFtlDescription(model.getFtlDescription());
        mainTableVo.setTableName(model.getTableName());
        String formTemplate = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getId, model.getCode())).getFormTemplate();
        if (oConvertUtils.isEmpty(formTemplate)) {
            mainTableVo.setFieldRowNum(1);
        } else {
            mainTableVo.setFieldRowNum(Integer.parseInt(formTemplate));
        }
        List<ColumnVo> arrayList = new ArrayList<>();
        List<ColumnVo> arrayList2 = new ArrayList<>();
        m345a(model.getCode(), arrayList, arrayList2);
        List<OnlGenerateModel> subList = model.getSubList();
        List<SubTableVo> arrayList3 = new ArrayList<>();
        for (OnlGenerateModel onlGenerateModel : subList) {
            OnlCgformHead onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, onlGenerateModel.getTableName()));
            if (onlCgformHead != null) {
                SubTableVo subTableVo = new SubTableVo();
                subTableVo.setEntityName(onlGenerateModel.getEntityName());
                subTableVo.setEntityPackage(model.getEntityPackage());
                subTableVo.setTableName(onlGenerateModel.getTableName());
                subTableVo.setFtlDescription(onlGenerateModel.getFtlDescription());
                subTableVo.setForeignRelationType(onlCgformHead.getRelationType() == 1 ? "1" : "0");
                List<ColumnVo> arrayList4 = new ArrayList<>();
                List<ColumnVo> arrayList5 = new ArrayList<>();
                OnlCgformField m345a = m345a(onlCgformHead.getId(), arrayList4, arrayList5);
                if (m345a != null) {
                    subTableVo.setOriginalForeignKeys(new String[]{m345a.getDbFieldName()});
                    subTableVo.setForeignKeys(new String[]{m345a.getDbFieldName()});
                    subTableVo.setForeignMainKeys(new String[]{m345a.getMainField()});
                    subTableVo.setColums(arrayList4);
                    subTableVo.setOriginalColumns(arrayList5);
                    arrayList3.add(subTableVo);
                }
            }
        }
        CgformEnum cgformEnumByConfig = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        if (oConvertUtils.isNotEmpty(model.getVueStyle())) {
            List<String> asList = null;
            if (cgformEnumByConfig != null) {
                asList = Arrays.asList(cgformEnumByConfig.getVueStyle());
            }
            HashMap<String,String> hashMap = new HashMap<>(5);
            if (asList != null && asList.contains(model.getVueStyle())) {
                hashMap.put("vueStyle", model.getVueStyle());
            }
            mainTableVo.setExtendParams(hashMap);
        }
        if (arrayList3.isEmpty()) {
            logger.error("你选择的表类型是【主表】，但是没有关联子表，导致生成代码报错！");
            throw new JeecgBootException("你选择的表类型是【主表】，但是没有关联子表，生成代码失败！");
        }
        if (cgformEnumByConfig==null){
            throw new JeecgBootException("未找到对应的生成模板，请检查生成模式是否正确！");
        }
        return new CodeGenerateOneToMany(mainTableVo, arrayList, arrayList2, arrayList3).generateCodeFile(model.getProjectPath(), cgformEnumByConfig.getTemplatePath(), cgformEnumByConfig.getStylePath());
    }

    /* renamed from: a */
    private OnlCgformField m345a(String str, List<ColumnVo> list, List<ColumnVo> list2) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, OnlineConst.isPersist);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        OnlCgformField onlCgformField = null;
        for (OnlCgformField onlCgformField2 : this.fieldService.list(lambdaQueryWrapper)) {
            if (oConvertUtils.isNotEmpty(onlCgformField2.getMainTable())) {
                onlCgformField = onlCgformField2;
            }
            ColumnVo columnVo = new ColumnVo();
            columnVo.setFieldLength(onlCgformField2.getFieldLength());
            columnVo.setFieldHref(onlCgformField2.getFieldHref());
            columnVo.setFieldValidType(onlCgformField2.getFieldValidType());
            columnVo.setFieldDefault(onlCgformField2.getDbDefaultVal());
            columnVo.setFieldShowType(onlCgformField2.getFieldShowType());
            columnVo.setFieldOrderNum(onlCgformField2.getOrderNum());
            columnVo.setIsKey(onlCgformField2.getDbIsKey() == 1 ? "Y" : "N");
            columnVo.setIsShow(onlCgformField2.getIsShowForm() == 1 ? "Y" : "N");
            columnVo.setIsShowList(onlCgformField2.getIsShowList() == 1 ? "Y" : "N");
            columnVo.setIsQuery(onlCgformField2.getIsQuery() == 1 ? "Y" : "N");
            columnVo.setQueryMode(onlCgformField2.getQueryMode());
            columnVo.setDictField(onlCgformField2.getDictField());
            columnVo.setDictTable(onlCgformField2.getDictTable());
            columnVo.setDictText(onlCgformField2.getDictText());
            columnVo.setFieldDbName(onlCgformField2.getDbFieldName());
            columnVo.setFieldName(oConvertUtils.camelName(onlCgformField2.getDbFieldName()));
            columnVo.setFiledComment(onlCgformField2.getDbFieldTxt());
            columnVo.setFieldDbType(onlCgformField2.getDbType());
            columnVo.setFieldType(m346b(onlCgformField2.getDbType()));
            columnVo.setClassType(onlCgformField2.getFieldShowType());
            columnVo.setClassType_row(onlCgformField2.getFieldShowType());
            if (onlCgformField2.getDbIsNull() == 0 || "*".equals(onlCgformField2.getFieldValidType()) || "1".equals(onlCgformField2.getFieldMustInput())) {
                columnVo.setNullable("N");
            } else {
                columnVo.setNullable("Y");
            }
            if (CgformUtil.f217L.equals(onlCgformField2.getFieldShowType())) {
                if (oConvertUtils.isNotEmpty(onlCgformField2.getFieldExtendJson())) {
                    columnVo.setDictField(onlCgformField2.getFieldExtendJson());
                } else {
                    columnVo.setDictField("is_open");
                }
            }
            Map<String, Object> hashMap = new HashMap<>(5);
            if (StringUtils.isNotBlank(onlCgformField2.getFieldExtendJson())) {
                try {
                    JSONObject parseObject = JSONObject.parseObject(onlCgformField2.getFieldExtendJson());
                    if (parseObject != null) {
                        hashMap.putAll(parseObject.getInnerMap());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            columnVo.setExtendParams(hashMap);
            if (CgformUtil.f218M.equals(onlCgformField2.getFieldShowType())) {
                boolean z = true;
                Object obj = hashMap.get(ExtendJsonKey.POPUP_MULTI);
                if (obj != null) {
                    z = (Boolean) obj;
                }
                hashMap.put(ExtendJsonKey.POPUP_MULTI, Boolean.valueOf(z));
            }
            columnVo.setSort("1".equals(onlCgformField2.getSortFlag()) ? "Y" : "N");
            Integer num = 1;
            columnVo.setReadonly(num.equals(onlCgformField2.getIsReadOnly()) ? "Y" : "N");
            if (oConvertUtils.isNotEmpty(onlCgformField2.getFieldDefaultValue()) && !onlCgformField2.getFieldDefaultValue().trim().startsWith("${") && !onlCgformField2.getFieldDefaultValue().trim().startsWith("#{") && !onlCgformField2.getFieldDefaultValue().trim().startsWith("{{")) {
                columnVo.setDefaultVal(onlCgformField2.getFieldDefaultValue());
            }
            if (("file".equals(onlCgformField2.getFieldShowType()) || "image".equals(onlCgformField2.getFieldShowType())) && oConvertUtils.isNotEmpty(onlCgformField2.getFieldExtendJson())) {
                JSONObject parseObject2 = JSONObject.parseObject(onlCgformField2.getFieldExtendJson());
                if (oConvertUtils.isNotEmpty(parseObject2.getString(ExtendJsonKey.UPLOADNUM))) {
                    columnVo.setUploadnum(parseObject2.getString(ExtendJsonKey.UPLOADNUM));
                }
            }
            list2.add(columnVo);
            if (onlCgformField2.getIsShowForm() == 1 || onlCgformField2.getIsShowList() == 1 || onlCgformField2.getIsQuery() == 1) {
                list.add(columnVo);
            }
        }
        return onlCgformField;
    }

    /* renamed from: b */
    private String m346b(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.indexOf("int") >= 0) {
            return "java.lang.Integer";
        }
        if (lowerCase.indexOf("double") >= 0) {
            return "java.lang.Double";
        }
        if (lowerCase.indexOf(DataBaseConst.DECIMAL) >= 0) {
            return "java.math.BigDecimal";
        }
        if (lowerCase.indexOf(OnlFormShowType.DATE) >= 0) {
            return "java.util.Date";
        }
        return "java.lang.String";
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void addCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
        String subTableStr;
        int i = 0;  // todo 不知道这个初始化对不对
        OnlCgformHead onlCgformHead;
        OnlCgformHead onlCgformHead2 = getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tbname));
        if (onlCgformHead2 == null) {
            throw new DBException("数据库主表[" + tbname + "]不存在");
        }
        if (onlCgformHead2.getTableType() == 2 && (subTableStr = onlCgformHead2.getSubTableStr()) != null) {
            for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                String string = json.getString("sub-table-design_" + str);
                if (oConvertUtils.isEmpty(string)) {
                    string = json.getString("sub-table-one2one_" + str);
                    i = oConvertUtils.isEmpty(string) ? i + 1 : 0;
                }
                JSONArray parseArray = JSONArray.parseArray(string);
                if (parseArray != null && !parseArray.isEmpty() && (onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str))) != null) {
                    List<OnlCgformField> list = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
                    String str2 = "";
                    String str3 = null;
                    for (OnlCgformField onlCgformField : list) {
                        if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                            str2 = onlCgformField.getDbFieldName();
                            str3 = json.getString(onlCgformField.getMainField());
                        }
                    }
                    for (int i2 = 0; i2 < parseArray.size(); i2++) {
                        JSONObject jSONObject = parseArray.getJSONObject(i2);
                        if (str3 != null) {
                            jSONObject.put(str2, str3);
                        }
                        this.fieldService.executeInsertSQL(CgformUtil.m231c(str, list, jSONObject));
                    }
                }
            }
        }
        this.fieldService.saveFormData(onlCgformHead2.getId(), tbname, json, true);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void editCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
        JSONArray parseArray;
        OnlCgformHead onlCgformHead = getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tbname));
        if (onlCgformHead == null) {
            throw new DBException("数据库主表[" + tbname + "]不存在");
        }
        if (onlCgformHead.getTableType().intValue() == 2) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead2 = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead2 != null) {
                        List<OnlCgformField> list = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId()));
                        String str2 = "";
                        String str3 = null;
                        for (OnlCgformField onlCgformField : list) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getMainField())) {
                                str2 = onlCgformField.getDbFieldName();
                                str3 = json.getString(onlCgformField.getMainField());
                            }
                        }
                        if (!oConvertUtils.isEmpty(str3)) {
                            this.fieldService.deleteAutoList(str, str2, str3);
                            String string = json.getString("sub-table-design_" + str);
                            if (oConvertUtils.isEmpty(string)) {
                                string = json.getString("sub-table-one2one_" + str);
                                if (oConvertUtils.isEmpty(string)) {
                                }
                            }
                            if (!oConvertUtils.isEmpty(string) && (parseArray = JSONArray.parseArray(string)) != null && !parseArray.isEmpty()) {
                                for (int i = 0; i < parseArray.size(); i++) {
                                    JSONObject jSONObject = parseArray.getJSONObject(i);
                                    jSONObject.put(str2, str3);
                                    this.fieldService.executeInsertSQL(CgformUtil.m231c(str, list, jSONObject));
                                }
                            }
                        }
                    }
                }
            }
        }
        this.fieldService.editFormData(onlCgformHead.getId(), tbname, json, true);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public Integer getMaxCopyVersion(String physicId) {
        Integer maxCopyVersion = ((OnlCgformHeadMapper) this.baseMapper).getMaxCopyVersion(physicId);
        return Integer.valueOf(maxCopyVersion == null ? 0 : maxCopyVersion);
    }

    /**
     *
     * @param physicTable 真实表单，也就是要复制的对象
     * @throws Exception
     */
    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void copyOnlineTableConfig(OnlCgformHead physicTable) throws Exception {
        String id = physicTable.getId();
        OnlCgformHead onlCgformHead = new OnlCgformHead();
        String generate = UUIDGenerator.generate();
        onlCgformHead.setId(generate);
        onlCgformHead.setPhysicId(id);
        onlCgformHead.setCopyType(1);
        onlCgformHead.setCopyVersion(physicTable.getTableVersion());
        onlCgformHead.setTableVersion(1);
        onlCgformHead.setTableName(getNewTableName(id, physicTable.getTableName()));
        onlCgformHead.setTableTxt(physicTable.getTableTxt());
        onlCgformHead.setFormCategory(physicTable.getFormCategory());
        onlCgformHead.setFormTemplate(physicTable.getFormTemplate());
        onlCgformHead.setFormTemplateMobile(physicTable.getFormTemplateMobile());
        onlCgformHead.setIdSequence(physicTable.getIdSequence());
        onlCgformHead.setIdType(physicTable.getIdType());
        onlCgformHead.setIsCheckbox(physicTable.getIsCheckbox());
        onlCgformHead.setIsPage(physicTable.getIsPage());
        onlCgformHead.setIsTree(physicTable.getIsTree());
        onlCgformHead.setQueryMode(physicTable.getQueryMode());
        onlCgformHead.setTableType(1);
        onlCgformHead.setIsDbSynch("N");
        onlCgformHead.setIsDesForm(physicTable.getIsDesForm());
        onlCgformHead.setDesFormCode(physicTable.getDesFormCode());
        onlCgformHead.setTreeParentIdField(physicTable.getTreeParentIdField());
        onlCgformHead.setTreeFieldname(physicTable.getTreeFieldname());
        onlCgformHead.setTreeIdField(physicTable.getTreeIdField());
        onlCgformHead.setRelationType(null);
        onlCgformHead.setTabOrderNum(null);
        onlCgformHead.setSubTableStr(null);
        onlCgformHead.setThemeTemplate(physicTable.getThemeTemplate());
        onlCgformHead.setScroll(physicTable.getScroll());
        onlCgformHead.setExtConfigJson(physicTable.getExtConfigJson());
        for (OnlCgformField onlCgformField : this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, id))) {
            OnlCgformField onlCgformField2 = new OnlCgformField();
            onlCgformField2.setCgformHeadId(generate);
            copyAttribute(onlCgformField, onlCgformField2);
            this.fieldService.save(onlCgformField2);
        }
        this.baseMapper.insert(onlCgformHead);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void initCopyState(List<OnlCgformHead> headList) {
        List<String> queryCopyPhysicId = ((OnlCgformHeadMapper) this.baseMapper).queryCopyPhysicId();
        for (OnlCgformHead onlCgformHead : headList) {
            if (queryCopyPhysicId.contains(onlCgformHead.getId())) {
                onlCgformHead.setHascopy(1);
            } else {
                onlCgformHead.setHascopy(0);
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void deleteBatch(String ids, String flag) {
        String[] split = ids.split(CgformUtil.COMMA_SEPARATOR);
        if ("1".equals(flag)) {
            for (String str : split) {
                try {
                    deleteRecordAndTable(str);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (DBException e2) {
                    e2.printStackTrace();
                }
            }
            return;
        }
        removeByIds(Arrays.asList(split));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public void updateParentNode(OnlCgformHead head, String dataId) {
        if ("Y".equals(head.getIsTree())) {
            String m235f = CgformUtil.m235f(head.getTableName());
            String treeParentIdField = head.getTreeParentIdField();
            Map<String, Object> m343d = m343d(m235f, dataId);
            String str = null;
            if (m343d.get(treeParentIdField) != null && !"0".equals(m343d.get(treeParentIdField))) {
                str = m343d.get(treeParentIdField).toString();
            } else if (m343d.get(treeParentIdField.toUpperCase()) != null && !"0".equals(m343d.get(treeParentIdField.toUpperCase()))) {
                str = m343d.get(treeParentIdField.toUpperCase()).toString();
            }
            if (str != null && this.baseMapper.queryChildNode(m235f, treeParentIdField, str) == 1) {
                this.fieldService.updateTreeNodeNoChild(m235f, head.getTreeIdField(), str);
            }
        }
    }

    /* renamed from: b */
    private void m347b(OnlCgformHead onlCgformHead, List<OnlCgformField> list) {
        List<OnlCgformHead> list2 = list(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getPhysicId, onlCgformHead.getId()));
        if (list2 != null && !list2.isEmpty()) {
            for (OnlCgformHead onlCgformHead2 : list2) {
                List<OnlCgformField> list3 = this.fieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId()));
                if (list3 == null || list3.isEmpty()) {
                    for (OnlCgformField onlCgformField : list) {
                        OnlCgformField onlCgformField2 = new OnlCgformField();
                        onlCgformField2.setCgformHeadId(onlCgformHead2.getId());
                        copyAttribute(onlCgformField, onlCgformField2);
                        this.fieldService.save(onlCgformField2);
                    }
                } else {
                    HashMap<String,Object> hashMap = new HashMap<>(5);
                    for (OnlCgformField onlCgformField : list3) {
                        hashMap.put(onlCgformField.getDbFieldName(), 1);
                    }
                    HashMap<String,Object> hashMap2 = new HashMap<>(5);
                    for (OnlCgformField onlCgformField : list) {
                        hashMap2.put(onlCgformField.getDbFieldName(), 1);
                    }
                    ArrayList<String> arrayList = new ArrayList<>();
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    for (String str : hashMap2.keySet()) {
                        if (hashMap.get(str) == null) {
                            arrayList2.add(str);
                        } else {
                            arrayList.add(str);
                        }
                    }
                    ArrayList<String> arrayList3 = new ArrayList<>();
                    for (String str2 : hashMap.keySet()) {
                        if (hashMap2.get(str2) == null) {
                            arrayList3.add(str2);
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        for (OnlCgformField onlCgformField3 : list3) {
                            if (arrayList3.contains(onlCgformField3.getDbFieldName())) {
                                this.fieldService.removeById(onlCgformField3.getId());
                            }
                        }
                    }
                    if (!arrayList2.isEmpty()) {
                        for (OnlCgformField onlCgformField4 : list) {
                            if (arrayList2.contains(onlCgformField4.getDbFieldName())) {
                                OnlCgformField onlCgformField5 = new OnlCgformField();
                                onlCgformField5.setCgformHeadId(onlCgformHead2.getId());
                                copyAttribute(onlCgformField4, onlCgformField5);
                                this.fieldService.save(onlCgformField5);
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        for (String s : arrayList) {
                            m348b(s, list, list3);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: b */
    private void m348b(String str, List<OnlCgformField> list, List<OnlCgformField> list2) {
        OnlCgformField onlCgformField = null;
        for (OnlCgformField onlCgformField2 : list) {
            if (str.equals(onlCgformField2.getDbFieldName())) {
                onlCgformField = onlCgformField2;
            }
        }
        OnlCgformField onlCgformField3 = null;
        for (OnlCgformField onlCgformField4 : list2) {
            if (str.equals(onlCgformField4.getDbFieldName())) {
                onlCgformField3 = onlCgformField4;
            }
        }
        if (onlCgformField != null && onlCgformField3 != null) {
            boolean z = false;
            if (!onlCgformField.getDbType().equals(onlCgformField3.getDbType())) {
                onlCgformField3.setDbType(onlCgformField.getDbType());
                z = true;
            }
            if (onlCgformField.getDbDefaultVal() != null && !onlCgformField.getDbDefaultVal().equals(onlCgformField3.getDbDefaultVal())) {
                onlCgformField3.setDbDefaultVal(onlCgformField.getDbDefaultVal());
                z = true;
            }
            if (!onlCgformField.getDbLength().equals(onlCgformField3.getDbLength())) {
                onlCgformField3.setDbLength(onlCgformField.getDbLength());
                z = true;
            }
            if (onlCgformField.getDbIsNull() != onlCgformField3.getDbIsNull()) {
                onlCgformField3.setDbIsNull(onlCgformField.getDbIsNull());
                z = true;
            }
            if (z) {
                this.fieldService.updateById(onlCgformField3);
            }
        }
    }

    /**
     * 复制两个对象的属性
     * @param source 源对象
     * @param target 目标对象
     */
    /* renamed from: a */
    private void copyAttribute(OnlCgformField source, OnlCgformField target) {
        target.setDbDefaultVal(source.getDbDefaultVal());
        target.setDbFieldName(source.getDbFieldName());
        target.setDbFieldNameOld(source.getDbFieldNameOld());
        target.setDbFieldTxt(source.getDbFieldTxt());
        target.setDbIsKey(source.getDbIsKey());
        target.setDbIsNull(source.getDbIsNull());
        target.setDbLength(source.getDbLength());
        target.setDbPointLength(source.getDbPointLength());
        target.setDbType(source.getDbType());
        target.setDictField(source.getDictField());
        target.setDictTable(source.getDictTable());
        target.setDictText(source.getDictText());
        target.setFieldExtendJson(source.getFieldExtendJson());
        target.setFieldHref(source.getFieldHref());
        target.setFieldLength(source.getFieldLength());
        target.setFieldMustInput(source.getFieldMustInput());
        target.setFieldShowType(source.getFieldShowType());
        target.setFieldValidType(source.getFieldValidType());
        target.setFieldDefaultValue(source.getFieldDefaultValue());
        target.setIsQuery(source.getIsQuery());
        target.setIsShowForm(source.getIsShowForm());
        target.setIsShowList(source.getIsShowList());
        target.setMainField(source.getMainField());
        target.setMainTable(source.getMainTable());
        target.setOrderNum(source.getOrderNum());
        target.setQueryMode(source.getQueryMode());
        target.setIsReadOnly(source.getIsReadOnly());
        target.setSortFlag(source.getSortFlag());
        target.setQueryDefVal(source.getQueryDefVal());
        target.setQueryConfigFlag(source.getQueryConfigFlag());
        target.setQueryDictField(source.getQueryDictField());
        target.setQueryDictTable(source.getQueryDictTable());
        target.setQueryDictText(source.getQueryDictText());
        target.setQueryMustInput(source.getQueryMustInput());
        target.setQueryShowType(source.getQueryShowType());
        target.setQueryValidType(source.getQueryValidType());
        target.setConverter(source.getConverter());
        target.setDbIsPersist(source.getDbIsPersist());
    }

    /**
     * 设置默认的长度
     * @param onlCgformField 表单字段
     */
    /* renamed from: a */
    private void setDefaultLength(OnlCgformField onlCgformField) {
        if (DataBaseConst.TEXT.equals(onlCgformField.getDbType()) || "Blob".equals(onlCgformField.getDbType())) {
            onlCgformField.setDbLength(0);
            onlCgformField.setDbPointLength(0);
        }
    }

    /**
     *
     * 查询副本的数量，也就是流水号
     * 跟表名拼接，形成新的表单号
     * @param id 表id
     * @param tableName 表名
     * @return 新表名
     */
    /* renamed from: e */
    private String getNewTableName(String id, String tableName) {
        //获取所有这个表单的副本
        List<String> queryAllCopyTableName = this.baseMapper.queryAllCopyTableName(id);
        int i = 0;
        if (queryAllCopyTableName != null || !queryAllCopyTableName.isEmpty()) {
            for (int i2 = 0; i2 < queryAllCopyTableName.size(); i2++) {
                int parseInt = Integer.parseInt(queryAllCopyTableName.get(i2).split("\\$")[1]);
                if (parseInt > i) {
                    i = parseInt;
                }
            }
        }
        return tableName + "$" + (i + 1);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    @Transactional(rollbackFor = {Exception.class})
    public String deleteDataByCode(String cgformCode, String dataIds) {
        OnlCgformHead onlCgformHead = super.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, cgformCode));
        if (onlCgformHead == null) {
            throw new JeecgBootException("实体不存在");
        }
        String tableName = onlCgformHead.getTableName();
        try {
            if (dataIds.indexOf(CgformUtil.COMMA_SEPARATOR) > 0) {
                this.onlCgformFieldService.deleteAutoListById(tableName, dataIds);
            } else {
                deleteOneTableInfo(onlCgformHead.getId(), dataIds);
            }
            return tableName;
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEdit()发生异常：" + e.getMessage(), e);
            throw new JeecgBootException("删除失败：" + e.getMessage());
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public JSONObject queryAllDataByTableNameForDesform(String tableName, String dataIds) throws DBException {
        JSONObject jSONObject = new JSONObject();
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, tableName);
        OnlCgformHead onlCgformHead = super.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            throw new JeecgBootException("表单数据不存在！");
        }
        Map<String, Object> queryManyFormData = queryManyFormData(onlCgformHead.getId(), dataIds);
        if (queryManyFormData == null) {
            throw new JeecgBootException("表单数据查询失败！");
        }
        JSONObject parseObject = JSON.parseObject(JSON.toJSONString(queryManyFormData));
        String subTableStr = onlCgformHead.getSubTableStr();
        if (oConvertUtils.isNotEmpty(subTableStr)) {
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(subTableStr.split(CgformUtil.COMMA_SEPARATOR)));
            LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper2.in(OnlCgformHead::getTableName, arrayList);
            List<OnlCgformHead> list = super.list(lambdaQueryWrapper2);
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            for (OnlCgformHead onlCgformHead2 : list) {
                JSONArray jSONArray = parseObject.getJSONArray(onlCgformHead2.getTableName());
                if (jSONArray != null && !jSONArray.isEmpty()) {
                    if (0 == onlCgformHead2.getRelationType()) {
                        jSONObject2.put(onlCgformHead2.getTableName(), jSONArray);
                    } else {
                        jSONObject3.put(onlCgformHead2.getTableName(), jSONArray.getJSONObject(0));
                    }
                }
                parseObject.remove(onlCgformHead2.getTableName());
            }
            jSONObject.put("one2one", jSONObject3);
            jSONObject.put("one2many", jSONObject2);
        }
        jSONObject.put(CgReportConstant.MAIN, parseObject);
        return jSONObject;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformHead copyOnlineTable(String id, String tableName) {
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, tableName);
        Long selectCount = this.baseMapper.selectCount(lambdaQueryWrapper);
        if (selectCount != null && selectCount >= 1) {
            throw new JeecgBootException("表名已经存在!");
        }
        OnlCgformHead onlCgformHead = this.baseMapper.selectById(id);
        if (onlCgformHead == null) {
            throw new JeecgBootException("表不存在!");
        }
        OnlCgformHead onlCgformHead2 = new OnlCgformHead();
        BeanUtils.copyProperties(onlCgformHead, onlCgformHead2);
        String m240a = CgformUtil.nextId();
        onlCgformHead2.setId(m240a);
        onlCgformHead2.setSubTableStr(null);
        onlCgformHead2.setTableName(tableName);
        onlCgformHead2.setTableVersion(1);
        onlCgformHead2.setIsDbSynch("N");
        onlCgformHead2.setCreateBy(null);
        onlCgformHead2.setCreateTime(null);
        onlCgformHead2.setUpdateBy(null);
        onlCgformHead2.setUpdateTime(null);
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(OnlCgformField::getCgformHeadId, id);
        List<OnlCgformField> list = this.fieldService.list(lambdaQueryWrapper2);
        ArrayList<OnlCgformField> arrayList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (OnlCgformField onlCgformField : list) {
                OnlCgformField onlCgformField2 = new OnlCgformField();
                BeanUtils.copyProperties(onlCgformField, onlCgformField2);
                onlCgformField2.setCgformHeadId(m240a);
                onlCgformField2.setMainField(null);
                onlCgformField2.setMainTable(null);
                onlCgformField2.setId(null);
                onlCgformField2.setCreateBy(null);
                onlCgformField2.setCreateTime(null);
                onlCgformField2.setUpdateBy(null);
                onlCgformField2.setUpdateTime(null);
                arrayList.add(onlCgformField2);
            }
        }
        LambdaQueryWrapper<OnlCgformIndex> lambdaQueryWrapper3 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper3.eq(OnlCgformIndex::getCgformHeadId, id);
        List<OnlCgformIndex> list2 = this.indexService.list(lambdaQueryWrapper3);
        ArrayList<OnlCgformIndex> arrayList2 = new ArrayList<>();
        if (list2 != null && list2.size() > 0) {
            for (OnlCgformIndex onlCgformIndex : list2) {
                OnlCgformIndex onlCgformIndex2 = new OnlCgformIndex();
                BeanUtils.copyProperties(onlCgformIndex, onlCgformIndex2);
                onlCgformIndex2.setCgformHeadId(m240a);
                onlCgformIndex2.setId(null);
                onlCgformIndex2.setCreateBy(null);
                onlCgformIndex2.setCreateTime(null);
                onlCgformIndex2.setUpdateBy(null);
                onlCgformIndex2.setUpdateTime(null);
                arrayList2.add(onlCgformIndex2);
            }
        }
        save(onlCgformHead2);
        this.fieldService.saveBatch(arrayList);
        this.indexService.saveBatch(arrayList2);
        return onlCgformHead2;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformHeadService
    public OnlCgformHead getTable(String code) throws DBException {
        OnlCgformHead onlCgformHead = getById(code);
        if (onlCgformHead == null) {
            onlCgformHead = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, code));
        }
        if (onlCgformHead == null) {
            throw new DBException("online表[" + code + "]不存在");
        }
        return onlCgformHead;
    }

    private DataBaseConfig getOnlineDataBaseConfig() {
        if (oConvertUtils.isEmpty(this.onlineDatasource)) {
            return this.dataBaseConfig;
        }
        DataSourceProperty dataSourceProperty = CommonUtils.getDataSourceProperty(this.onlineDatasource);
        if (dataSourceProperty == null) {
            logger.error("jeecg.online.datasource配置错误,获取不到数据源返回master");
            return this.dataBaseConfig;
        }
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataBaseConfig.setPassword(dataSourceProperty.getPassword());
        dataBaseConfig.setUsername(dataSourceProperty.getUsername());
        dataBaseConfig.setUrl(dataSourceProperty.getUrl());
        dataBaseConfig.setDmDataBaseConfig(new dmDataBaseConfig());
        return dataBaseConfig;
    }
}

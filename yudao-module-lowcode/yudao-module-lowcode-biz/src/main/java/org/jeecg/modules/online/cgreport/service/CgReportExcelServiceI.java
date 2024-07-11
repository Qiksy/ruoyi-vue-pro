package org.jeecg.modules.online.cgreport.service;

import java.util.Collection;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/CgReportExcelServiceI.class */
public interface CgReportExcelServiceI {
    HSSFWorkbook exportExcel(String str, Collection<?> collection, Collection<?> collection2) throws Exception;
}

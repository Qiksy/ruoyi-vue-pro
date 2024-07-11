package org.jeecg.modules.online.cgreport.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.cgreport.service.CgReportExcelServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/* compiled from: CgReportExcelServiceImpl.java */
@Service("cgReportExcelService")
/* renamed from: org.jeecg.modules.online.cgreport.service.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/a/a.class */
public class CgReportExcelServiceImpl implements CgReportExcelServiceI {

    /* renamed from: a */
    private static final Logger f475a = LoggerFactory.getLogger(CgReportExcelServiceImpl.class);

    @Override // org.jeecg.modules.online.cgreport.service.CgReportExcelServiceI
    public HSSFWorkbook exportExcel(String title, Collection<?> titleSet, Collection<?> dataSet) throws Exception {
        HSSFWorkbook hSSFWorkbook = null;
        if (titleSet != null) {
            try {
            } catch (Exception e) {
                f475a.error(e.getMessage(), e);
            }
            if (titleSet.size() != 0) {
                if (title == null) {
                    title = "";
                }
                hSSFWorkbook = new HSSFWorkbook();
                HSSFSheet createSheet = hSSFWorkbook.createSheet(title);
                int i = 0;
                int i2 = 0;
                Row createRow = createSheet.createRow(0);
                createRow.setHeight((short) 450);
                HSSFCellStyle m435a = m435a(hSSFWorkbook);
                List list = (List) titleSet;
                Iterator<?> it = dataSet.iterator();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String str = (String) ((Map) it2.next()).get("field_txt");
                    Cell createCell = createRow.createCell(i2);
                    createCell.setCellValue(new HSSFRichTextString(str));
                    createCell.setCellStyle(m435a);
                    i2++;
                }
                HSSFCellStyle m438c = m438c(hSSFWorkbook);
                while (it.hasNext()) {
                    int i3 = 0;
                    i++;
                    Row createRow2 = createSheet.createRow(i);
                    Map map = (Map) it.next();
                    Iterator it3 = list.iterator();
                    while (it3.hasNext()) {
                        String str2 = (String) ((Map) it3.next()).get(CgReportConstant.FIELD_NAME);
                        String obj = map.get(str2) == null ? "" : map.get(str2).toString();
                        Cell createCell2 = createRow2.createCell(i3);
                        HSSFRichTextString hSSFRichTextString = new HSSFRichTextString(obj);
                        createCell2.setCellStyle(m438c);
                        createCell2.setCellValue(hSSFRichTextString);
                        i3++;
                    }
                }
                for (int i4 = 0; i4 < list.size(); i4++) {
                    createSheet.autoSizeColumn(i4);
                }
                return hSSFWorkbook;
            }
        }
        throw new Exception("读取表头失败！");
    }

    /* renamed from: a */
    private HSSFCellStyle m435a(HSSFWorkbook hSSFWorkbook) {
        HSSFCellStyle createCellStyle = hSSFWorkbook.createCellStyle();
        createCellStyle.setBorderLeft(BorderStyle.THIN);
        createCellStyle.setBorderRight(BorderStyle.THIN);
        createCellStyle.setBorderBottom(BorderStyle.THIN);
        createCellStyle.setBorderTop(BorderStyle.THIN);
        createCellStyle.setAlignment(HorizontalAlignment.CENTER);
        createCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        createCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return createCellStyle;
    }

    /* renamed from: a */
    private void m436a(int i, int i2, HSSFWorkbook hSSFWorkbook) {
        HSSFSheet sheetAt = hSSFWorkbook.getSheetAt(0);
        HSSFCellStyle m438c = m438c(hSSFWorkbook);
        for (int i3 = 1; i3 <= i; i3++) {
            Row createRow = sheetAt.createRow(i3);
            for (int i4 = 0; i4 < i2; i4++) {
                createRow.createCell(i4).setCellStyle(m438c);
            }
        }
    }

    /* renamed from: b */
    private HSSFCellStyle m437b(HSSFWorkbook hSSFWorkbook) {
        HSSFCellStyle createCellStyle = hSSFWorkbook.createCellStyle();
        createCellStyle.setBorderLeft(BorderStyle.THIN);
        createCellStyle.setBorderRight(BorderStyle.THIN);
        createCellStyle.setBorderBottom(BorderStyle.THIN);
        createCellStyle.setBorderTop(BorderStyle.THIN);
        createCellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
        createCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return createCellStyle;
    }

    /* renamed from: c */
    private HSSFCellStyle m438c(HSSFWorkbook hSSFWorkbook) {
        HSSFCellStyle createCellStyle = hSSFWorkbook.createCellStyle();
        createCellStyle.setBorderLeft(BorderStyle.THIN);
        createCellStyle.setBorderRight(BorderStyle.THIN);
        createCellStyle.setBorderBottom(BorderStyle.THIN);
        createCellStyle.setBorderTop(BorderStyle.THIN);
        return createCellStyle;
    }
}

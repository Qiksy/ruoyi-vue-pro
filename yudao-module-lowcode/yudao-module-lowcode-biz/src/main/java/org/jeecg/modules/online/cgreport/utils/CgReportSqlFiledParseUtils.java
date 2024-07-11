package org.jeecg.modules.online.cgreport.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: CgReportSqlFiledParseUtils.java */
/* renamed from: org.jeecg.modules.online.cgreport.c.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/c/a.class */
public class CgReportSqlFiledParseUtils {
    /* renamed from: a */
    public static Map<String, Object> m426a(String str) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        Select select = null;
        try {
            select = (Select) new CCJSqlParserManager().parse(new StringReader(str));
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        SelectBody selectBody = null;
        if (select != null) {
            selectBody = select.getSelectBody();
        }
        if (selectBody instanceof SetOperationList setOperationList) {
            List<SelectBody> selects = setOperationList.getSelects();
            for (SelectBody body : selects) {
                m427a(hashMap, m428a((PlainSelect) body));
            }
        }
        if (selectBody instanceof PlainSelect) {
            m427a(hashMap, m428a((PlainSelect) selectBody));
        }
        return hashMap;
    }

    /* renamed from: a */
    private static void m427a(Map<String, Object> map, List<String> list) {
        for (String str : list) {
            if (!"*".equals(str)) {
                map.put(str, str);
            }
        }
    }

    /* renamed from: a */
    public static List<String> m428a(PlainSelect plainSelect) {
        String name;
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        ArrayList<String> arrayList = new ArrayList();
        if (selectItems != null) {
            for (SelectItem allTableColumns : selectItems) {
                if (allTableColumns instanceof SelectExpressionItem selectExpressionItem) {
                    Alias alias = selectExpressionItem.getAlias();
                    Expression expression = selectExpressionItem.getExpression();
                    if (expression instanceof CaseExpression) {
                        name = alias.getName();
                    } else if ((expression instanceof LongValue) || (expression instanceof StringValue) || (expression instanceof DateValue) || (expression instanceof DoubleValue)) {
                        name = Objects.nonNull(alias.getName()) ? alias.getName() : expression.getASTNode().jjtGetValue().toString();
                    } else if (expression instanceof TimeKeyExpression) {
                        name = alias.getName();
                    } else if (alias != null) {
                        name = alias.getName();
                    } else {
                        Object jjtGetValue = expression.getASTNode().jjtGetValue();
                        if (jjtGetValue instanceof Column) {
                            name = ((Column) jjtGetValue).getColumnName();
                        } else if (jjtGetValue instanceof Function) {
                            name = jjtGetValue.toString();
                        } else {
                            name = String.valueOf(jjtGetValue).replace(CgformUtil.SINGLE_QUOTE, "").replace("\"", "").replace("`", "");
                        }
                    }
                    arrayList.add(name.replace(CgformUtil.SINGLE_QUOTE, "").replace("\"", "").replace("`", ""));
                } else if (allTableColumns instanceof AllTableColumns) {
                    arrayList.add(allTableColumns.toString());
                } else {
                    arrayList.add(allTableColumns.toString());
                }
            }
        }
        return arrayList;
    }
}

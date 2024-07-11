package org.jeecg.modules.online.config.blackList;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.jeecg.common.util.security.AbstractQueryBlackListHandler;
import org.springframework.stereotype.Component;

/* compiled from: OnlReportQueryBlackListHandler.java */
@Component("onlReportQueryBlackListHandler")
/* renamed from: org.jeecg.modules.online.config.c.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/c/a.class */
public class OnlReportQueryBlackListHandler extends AbstractQueryBlackListHandler {

    /* renamed from: a */
    private static ThreadLocal<Map<String, AbstractQueryBlackListHandler.QueryTable>> mapThreadLocal = new ThreadLocal<>();

    /* renamed from: b */
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /* renamed from: a */
    private void m454a() {
        mapThreadLocal.set(new HashMap<>(5));
        threadLocal.set("");
    }

    /* renamed from: b */
    private void m455b() {
        mapThreadLocal.remove();
        threadLocal.remove();
    }

    /* renamed from: a */
    private void m456a(String str, AbstractQueryBlackListHandler.QueryTable queryTable) {
        mapThreadLocal.get().put(str, queryTable);
    }

    /* renamed from: a */
    private AbstractQueryBlackListHandler.QueryTable m457a(String str) {
        return mapThreadLocal.get().get(str);
    }

    /* renamed from: a */
    private void m458a(String str, String str2) {
        mapThreadLocal.get().get(str).addField(str2);
    }

    private List<AbstractQueryBlackListHandler.QueryTable> getResult() {
        ArrayList<QueryTable> arrayList = new ArrayList<>(mapThreadLocal.get().values());
        m455b();
        return arrayList;
    }

    protected List<AbstractQueryBlackListHandler.QueryTable> getQueryTableInfo(String sql) {
        m454a();
        try {
            Select parse = (Select)new CCJSqlParserManager().parse(new StringReader(sql));
            if (parse != null) {
                SelectBody selectBody = (SelectBody)parse.getSelectBody();
                if (selectBody instanceof PlainSelect) {
                    PlainSelect plainSelect = (PlainSelect) selectBody;
                    m459a(plainSelect);
                    m461b(plainSelect);
                }
                if (selectBody instanceof SetOperationList setOperationList) {
                    List<SelectBody> selects = setOperationList.getSelects();
                    for (SelectBody select : selects) {
                        if (select instanceof PlainSelect plainSelect2) {
                            m459a(plainSelect2);
                            m461b(plainSelect2);
                        }
                    }
                }
            }
            return getResult();
        } catch (JSQLParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    private void m459a(PlainSelect plainSelect) {
        m460a((Table) plainSelect.getFromItem());
        List joins = plainSelect.getJoins();
        if (joins != null) {
            Iterator it = joins.iterator();
            while (it.hasNext()) {
                m460a((Table) ((Join) it.next()).getRightItem());
            }
        }
    }

    /* renamed from: a */
    private void m460a(Table table) {
        String name;
        if (table.getAlias() != null) {
            name = table.getAlias().getName();
        } else {
            name = table.getName();
        }
        if (threadLocal.get().isEmpty()) {
            threadLocal.set(name);
        }
        m456a(name, new AbstractQueryBlackListHandler.QueryTable( table.getName(), name));
    }

    /* renamed from: b */
    private void m461b(PlainSelect plainSelect) {
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        String str = threadLocal.get();
        for (SelectItem selectItem : selectItems) {
            selectItem.accept(new SelectItemVisitorAdapter() { // from class: org.jeecg.modules.online.config.c.a.1
                public void visit(SelectExpressionItem item) {
                    QueryTable m457a;
                    Column expression = (Column) item.getExpression();
                    if (expression instanceof Column) {
                        Column column = expression;
                        if (column.getTable() == null) {
                            OnlReportQueryBlackListHandler.this.m458a(str, column.getColumnName());
                            return;
                        }
                        String name = column.getTable().getName();
                        if (name == null || "".equals(name)) {
                            m457a = OnlReportQueryBlackListHandler.this.m457a(str);
                        } else {
                            m457a = OnlReportQueryBlackListHandler.this.m457a(name);
                        }
                        if (m457a != null) {
                            m457a.addField(column.getColumnName());
                            return;
                        }
                        return;
                    }
                    if (!OnlReportQueryBlackListHandler.this.m463b(expression)) {
                        String obj = expression.toString();
                        boolean z = false;
                        for (String str2 : OnlReportQueryBlackListHandler.mapThreadLocal.get().keySet()) {
                            if (obj.contains(str2 + ".")) {
                                z = true;
                                OnlReportQueryBlackListHandler.this.m458a(str2, obj);
                            }
                        }
                        if (!z) {
                            OnlReportQueryBlackListHandler.this.m458a(str, obj);
                        }
                    }
                }

                public void visit(AllTableColumns columns) {
                    String str2 = null;
                    try {
                        str2 = columns.getTable().getName();
                    } catch (Exception e) {
                    }
                    if (str2 == null) {
                        str2 = str;
                    }
                    OnlReportQueryBlackListHandler.this.m457a(str2).setAll(true);
                }

                public void visit(AllColumns columns) {
                    if ("*".equals(columns.toString())) {
                        OnlReportQueryBlackListHandler.this.m457a(str).setAll(true);
                    }
                }
            });
        }
    }



    /* renamed from: a */
    private boolean m462a(Expression expression) {
        if (expression != null) {
            return (expression instanceof Function) || (expression instanceof BinaryExpression) || (expression instanceof CastExpression) || (expression instanceof CaseExpression);
        }
        return false;
    }

    /* renamed from: b */
    private boolean m463b(Expression expression) {
        return (expression instanceof StringValue) || (expression instanceof DoubleValue) || (expression instanceof LongValue);
    }
}

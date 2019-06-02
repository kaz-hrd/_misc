package com.view.conv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.view.conv.util.TextBuilder;

public class RowContainer extends AbstractComponentContainer {
    private static final Logger logger = LoggerFactory.getLogger(RowContainer.class);

    protected List<ViewComponent> cells = new ArrayList<>();

    public RowContainer(ViewComponent parent) {
        super(parent);
    }
    public void addViewComponent(ViewComponent vc) {
        this.cells.add(vc);
    }
    @Override
    public List<ViewComponent> row(int index) {
        if(index > 0) {
            throw new IndexOutOfBoundsException(index);
        }
        return this.cells;
    }
    @Override
    public int numRows() {
        if(this.cells.size() == 0) {
            return 0;
        }else {
            return 1;
        }
    }
    @Override
    public void adjust() {
        this.sort();
        this.adjustWidth();
        ViewComponent prev = null;
        for(ViewComponent c : this.cells) {
            if(prev != null) {
                if(c.x < prev.x + prev.width) {
                    this.checkStates(prev, c);
                }
            }
            prev = c;
        }
    }

    protected void adjustWidth() {
        ViewComponent prev = null;
        for(ViewComponent c : this.cells) {
            if(prev != null) {
                if(prev.width == 0) {
                    prev.width = c.x - prev.x - 2;
                    logger.info("幅サイズの自動設定を実施 id=" + prev.id + ", width=" + prev.width);
                }
            }
            prev = c;
        }
        if(prev != null && prev.width == 0) {
            prev.width = this.parent.width - prev.x - 2;
            logger.info("幅サイズの自動設定を実施 id=" + prev.id + ", width=" + prev.width);
        }
    }

    protected void checkStates(ViewComponent o1, ViewComponent o2) {
        if(o1.states != null && o2.states != null) {
            o1.states.forEach(s1 -> {
                if(o2.states.contains(s1)) {
                    logger.warn("表示領域（列）が重なっている可能性があります state=" + s1 + ", id1=" + o1.id + "(x=" + o1.x + ", width=" + o1.width + ")" + ", id2=" + o2.id + "(x=" + o2.x + ")");
                }
            });
        }
    }

    @Override
    public void sort() {
        Collections.sort(this.cells, (o1, o2) -> o1.x - o2.x);
    }

    @Override
    public String getHtmlText() {

        List<RowData> cols = new ArrayList<RowData>();
        for(ViewComponent c : this.cells) {
            RowData data = new RowData();
            data.colClass = this.getColClass(c);
            data.text = c.getHtmlText();
            cols.add(data);
        }

        TextBuilder builder = TextBuilder.getInstance();
        Map<String, Object> parms = new HashMap<>();
        parms.put("cols", cols);
        return builder.create("row.txt", this.getIndent(), parms);

        /*
        StringBuilder builder = new StringBuilder();
        builder.append(this.getIndent()).append("<div class=\"row\">").append("\n");
        for(ViewComponent c : this.cells) {
            builder.append(this.getIndent()).append("  ").append("<div class=\"").append(getColClass(c)).append("\">")
            .append("\n");
            builder.append(c.getHtmlText()).append("\n");
            builder.append(this.getIndent()).append("  ").append("</div>").append("\n");
        }
        builder.append(this.getIndent()).append("</div>").append("\n");
        return builder.toString();
        */
    }

    protected String getColClass(ViewComponent c) {
        int col = c.width / Config.getCellWidth();
        if(col == 0 || c.width % Config.getCellWidth() > Config.getAllowableLimitX()) {
            col++;
        }
        return "col-xl-" + col;
    }
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.getIndent() + "RowContainer [x=" + x + ", y=" + y + ", width=" + width + ", id=" + id + "]");
        this.cells.forEach(el -> buf.append("\n").append(el));
        return buf.toString();
    }
}

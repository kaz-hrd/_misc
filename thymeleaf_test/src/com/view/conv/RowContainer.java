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

    private static final ViewComponent DUMMY_COMPONENT = new ViewComponent(null, "", 0, 0, null, 0);

    protected List<ViewComponent> cells = new ArrayList<>();
    protected int cellWidth = 0;

    protected int minLimitWidth = 0;
    protected int validEmptyWidth = 0;

    public RowContainer(ViewComponent parent) {
        super(parent);
        calcCellWidth();
    }
    public void addViewComponent(ViewComponent vc) {
        this.cells.add(vc);
    }
    protected void calcCellWidth() {
        this.cellWidth = this.width / Config.getCellNum();
        this.minLimitWidth = (int)(this.cellWidth * 0.9);
        this.validEmptyWidth = (int)(this.cellWidth * 0.5);

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
                    this.printCheckWarn(prev, c);
                }
            }
            prev = c;
        }
    }

    protected void adjustWidth() {
        ViewComponent prev = null;
        int totalWidth = 0;
        for(ViewComponent c : this.cells) {
            if(prev != null) {
                if(prev.width == 0) {
                    prev.width = c.x - prev.x - 2;
                    logger.info("幅サイズの自動設定を実施 id=" + prev.id + ", width=" + prev.width);
                }
                totalWidth += prev.width;
            }
            prev = c;
        }
        if(prev != null && prev.width == 0) {
            prev.width = this.parent.width - totalWidth - 2;
            logger.info("幅サイズの自動設定を実施 id=" + prev.id + ", width=" + prev.width);
        }
    }

    protected void printCheckWarn(ViewComponent o1, ViewComponent o2) {
        logger.warn("表示領域（列）が重なっている可能性があります id1=" + o1.id + "(x=" + o1.x + ", width=" + o1.width + ")" + ", id2=" + o2.id + "(x=" + o2.x + ")");
    }

    @Override
    public void sort() {
        Collections.sort(this.cells, (o1, o2) -> o1.x - o2.x);
    }

    protected int calcWidth(ViewComponent c) {
        int w = (int)(c.width  / this.cellWidth);
        return w;
    }

    protected int calcCellCount(ViewComponent c) {
        int w = this.calcWidth(c);
        if(w <= 0) {
            return 1;
        }
        int rem = c.width % this.cellWidth;
        if(rem >= this.minLimitWidth) {
            w++;
        }
        return w;
    }

    protected int calcEmptySpaceWidth(ViewComponent prev, ViewComponent current) {
        int s = current.x - (prev.x + prev.width);
        return s;
    }

    protected int calcEmptyCells(ViewComponent prev, ViewComponent current) {
        int s = this.calcEmptySpaceWidth(prev, current);
        int w = s / this.cellWidth;
        int rem = s % this.cellWidth;
        if(rem >= this.validEmptyWidth) {
            w++;
        }
        return w;
    }

    protected boolean shrinkEmptyCells(List<RowData> cols){
        List<RowData> target = new ArrayList<RowData>();
        cols.forEach(c -> {
            if(c.viewComponent == null) {
                target.add(c);
            }
        });
        if(target.size() == 0) {
            return false;
        }
        Collections.sort(target, (o1, o2) -> o1.width - o2.width);
        RowData rd = target.get(target.size() - 1);
        if(rd.width <= 0) {
            return false;
        }
        rd.cells--;
        rd.width = rd.width - this.cellWidth;
        if(rd.width < 0) {
            rd.width = 0;
        }
        if(rd.cells == 0) {
            cols.remove(rd);
            return true;
        }else {
            return true;
        }
    }

    protected List<RowData> adjustCells(int totalCellCount, List<RowData> cols) {
        int count = totalCellCount - Config.getCellNum();
        for(int n = 0; n < count; n++) {
            if(!this.shrinkEmptyCells(cols)) {
                logger.warn("調整可能な空列がありません");
                logger.warn(this.cells.toString());
                break;
            }
        }
        return cols;
    }

    protected void setupColClass(List<RowData> cols) {
        for(RowData rd : cols) {
            rd.colClass = "col-xl-" + rd.cells;
        }
    }

    @Override
    public String getHtmlText() {
        List<RowData> cols = new ArrayList<RowData>();
        ViewComponent prev = DUMMY_COMPONENT;
        int totalCells = 0;
        for(ViewComponent c : this.cells) {
            int emptyCells = this.calcEmptyCells(prev, c);
            if(emptyCells > 0) {
                RowData emptyData = new RowData();
                emptyData.cells = emptyCells;
                emptyData.width = this.calcEmptySpaceWidth(prev, c);
                cols.add(emptyData);
                totalCells += emptyData.cells;
                logger.info("空列を追加： " + emptyData);
            }
            RowData data = new RowData();
            data.cells = this.calcCellCount(c);
            data.width = c.width;
            data.text = c.getHtmlText();
            data.viewComponent = c;
            cols.add(data);
            totalCells += data.cells;
            prev = c;
        }

        if(totalCells > Config.getCellNum()) {
            logger.info("総列数が" + Config.getCellNum() + "を超過したため、空列の調整を実施");
            cols = this.adjustCells(totalCells, cols);
        }

        this.setupColClass(cols);

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

    @Override
    protected int getChildIndent() {
        return this.indentNum + 2;
    }
    /*
    protected String getColClass(ViewComponent c) {
        int col = c.width / Config.getCellWidth();
        if(col == 0 || c.width % Config.getCellWidth() > Config.getAllowableLimitX()) {
            col++;
        }
        return "col-xl-" + col;
    }
    */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.getIndent() + "RowContainer [x=" + x + ", y=" + y + ", width=" + width + ", id=" + id + "]");
        this.cells.forEach(el -> buf.append("\n").append(el));
        return buf.toString();
    }
}

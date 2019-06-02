package com.view.conv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeCointainer extends AbstractComponentContainer {
    private static final Logger logger = LoggerFactory.getLogger(CompositeCointainer.class);

    protected List<AbstractComponentContainer> rows = new ArrayList<>();
    protected int numRows = -1;


    public CompositeCointainer(ViewComponent parent) {
        super(parent);
    }

    @Override
    public void adjust() {
        this.sort();
        List<RowContainer> adjustTarget = new ArrayList<>();
        List<AbstractComponentContainer> checkTarget = new ArrayList<>();
        rows.forEach(e -> {
            if(e instanceof RowContainer) {
                adjustTarget.add((RowContainer)e);
            }else {
                checkTarget.add(e);
            }
        });

        List<AbstractComponentContainer> newRows = new ArrayList<>();

        // Y軸方向の調整
        RowContainer prev1 = null;
        for(RowContainer rc : adjustTarget) {
            if(prev1 == null) {
                prev1 = rc;
                continue;
            }
            if(rc.getY() < prev1.getY() + Config.getAllowableLimitY()) {
                for(ViewComponent vc : rc.cells) {
                    vc.updateParent(prev1);
                    prev1.addViewComponent(vc);
                }
            }else {
                newRows.add(prev1);
                prev1 = rc;
            }
        }
        if(prev1 != null) {
            newRows.add(prev1);
        }

        // 表示領域の重なりチェック
        AbstractComponentContainer prev2  = null;
        for(AbstractComponentContainer c : checkTarget) {
            newRows.add(c);
            if(prev2 != null) {
                if(c.getY() < prev2.getY() + Config.getAllowableLimitY()) {
                    this.checkStates(prev2, c);
                }
            }
            prev2 = c;
        }

        this.rows = newRows;
        this.sort();
        this.rows.forEach( e -> e.adjust() );
    }

    protected void checkStates(AbstractComponentContainer o1, AbstractComponentContainer o2) {
        if(o1.states != null && o2.states != null) {
            o1.states.forEach(s1 -> {
                if(o2.states.contains(s1)) {
                    logger.warn("表示領域（行）が重なっている可能性があります (許容誤差 +" + Config.getAllowableLimitY() + ") state=" + s1 + ", id1=" + o1.id + "(y=" + o1.y + ")" + ", id2=" + o2.id + "(y=" + o2.y + ")");
                }
            });
        }
    }

    @Override
    public void addViewComponent(ViewComponent vc) {
        AbstractComponentContainer ac = null;
        if(!(vc instanceof AbstractComponentContainer)) {
            ac = new RowContainer(this);
            ac.setY(vc.getY());
            vc.updateParent(ac);
            ac.addViewComponent(vc);
        }else {
            ac = (AbstractComponentContainer)vc;
        }
        this.rows.add(ac);
        this.numRows = -1;
    }

    @Override
    public List<ViewComponent> row(int index) {
        if(index >= this.numRows() || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        int count = 0;
        for(AbstractComponentContainer cc : this.rows) {
            for(int i = 0; i < cc.numRows(); i++) {
                if(index == count) {
                    return cc.row(i);
                }else {
                    count++;
                }
            }
        }
        throw new IndexOutOfBoundsException(index);
    }

    @Override
    public int numRows() {
        if(this.numRows < 0) {
            this.numRows = 0;
            rows.forEach((r) -> {
                this.numRows += r.numRows();
            });
        }
        return this.numRows;
    }

    @Override
    public void sort() {
        Collections.sort(this.rows, (o1,o2) -> o1.y - o2.y);
        this.rows.forEach(e -> e.sort());
    }

    @Override
    public String getHtmlText() {
        StringBuilder buf = new StringBuilder();
        for(AbstractComponentContainer c : this.rows) {
            buf.append(c.getHtmlText()).append("\n");
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.getIndent() + "CompositeCointainer [x=" + x + ", y=" + y + ", width=" + width + ", id=" + id + ", states=" + states + "]");
        this.rows.forEach(el -> buf.append("\n").append(el));
        return buf.toString();
    }

}

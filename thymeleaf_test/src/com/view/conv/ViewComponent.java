package com.view.conv;

import java.util.Set;

import com.view.conv.util.TextBuilder;

public class ViewComponent {
    protected int x = 0;
    protected int y= 0;
    protected int margin = 0;
    protected int width = Config.getBaseWidth();
    protected int indentNum = 0;
    protected String id;
    protected ViewComponent parent;
    protected Set<String> states;
    public ViewComponent(ViewComponent parent) {
        this.parent = parent;
        if(this.parent != null) {
            this.indentNum = this.parent.getChildIndent();
            this.width = this.parent.width - this.parent.margin;
        }
    }
    public ViewComponent(ViewComponent parent, String id, int x, int y, Set<String> states) {
        this(parent);
        this.id = id;
        this.x = x;
        this.y = y;
        this.states = states;
    }
    public ViewComponent(ViewComponent parent, String id, int x, int y, Set<String> states, int width) {
        this(parent, id, x, y, states);
        this.width = width;
    }
    protected int getChildIndent() {
        return this.indentNum + 1;
    }
    public void updateParent(ViewComponent parent) {
        this.parent = parent;
        if(this.parent != null) {
            this.indentNum = this.parent.getChildIndent();
        }
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Set<String> getStates() {
        return states;
    }
    public void setStates(Set<String> states) {
        this.states = states;
    }
    public int getIndentNum() {
        return indentNum;
    }
    public String getIndent() {
        String s = TextBuilder.getInstance().getIndentString(this.indentNum);
        return s;
    }
    public String getHtmlText() {
        return "";
    }
    @Override
    public String toString() {
        return this.getIndent() + "ViewComponent [x=" + x + ", y=" + y + ", width=" + width + ", id=" + id + ", states=" + states + "]";
    }
}

package com.view.conv;

public class RowData {
    public int width;
    public int cells;
    public String colClass;
    public String text;
    public ViewComponent viewComponent;
    @Override
    public String toString() {
        return "RowData [width=" + width + ", cells=" + cells + ", colClass=" + colClass + ", text=" + text + "]";
    }
}

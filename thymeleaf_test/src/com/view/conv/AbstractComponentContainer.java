package com.view.conv;

import java.util.List;

public abstract class AbstractComponentContainer  extends ViewComponent {
    public AbstractComponentContainer(ViewComponent parent) {
        super(parent);
    }
    public abstract void addViewComponent(ViewComponent vc);
    public abstract List<ViewComponent> row(int index);
    public abstract int numRows();
    public abstract void adjust();
    public abstract void sort();
}

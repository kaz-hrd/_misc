package com.view.conv.form;

import java.util.Set;

import com.view.conv.ViewComponent;

public abstract class AbstractFormComponent extends ViewComponent {

    protected HeadingLabel groupLabel;

    public AbstractFormComponent(ViewComponent parent, String id, int x, int y, Set<String> states, int width) {
        super(parent, id, x, y, states, width);
    }

    public AbstractFormComponent(ViewComponent parent) {
        super(parent);
    }

    public HeadingLabel getGroupLabel() {
        return groupLabel;
    }

    public void setGroupLabel(HeadingLabel groupLabel) {
        this.groupLabel = groupLabel;
    }

}

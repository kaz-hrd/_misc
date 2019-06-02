package com.view.conv.form;

import java.util.Set;

import com.view.conv.ViewComponent;

public abstract class AbstractFormComponent extends ViewComponent {

    protected HeadingLabel groupLabel;

    public AbstractFormComponent(ViewComponent parent, String id, int x, int y, int width, Set<String> states) {
        super(parent, id, x, y, width, states);
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

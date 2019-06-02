package com.view.conv.form;

import java.util.Set;

import com.view.conv.ViewComponent;

public class HeadingLabel extends ViewComponent {
    public HeadingLabel(ViewComponent parent) {
        super(parent);
    }
    public HeadingLabel(ViewComponent parent, String id, int x, int y, Set<String> states, int width) {
        super(parent, id, x, y, states, width);
    }
}

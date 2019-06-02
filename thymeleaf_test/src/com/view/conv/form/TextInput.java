package com.view.conv.form;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.view.conv.ViewComponent;
import com.view.conv.util.TextBuilder;

public class TextInput extends AbstractFormComponent {
    public TextInput(ViewComponent parent) {
        super(parent);
    }
    public TextInput(ViewComponent parent, String id, int x, int y, int width, Set<String> states) {
        super(parent, id, x, y, width, states);
    }
    @Override
    public String getHtmlText() {
        TextBuilder builder = TextBuilder.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put("id", this.id);
        return builder.create("input.txt", this.getIndent(), params);
    }
}

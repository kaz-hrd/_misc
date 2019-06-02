package com.view.conv.util;

import java.util.Locale;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TextBuilder {
    private TemplateEngine engine = null;
    private static TextBuilder INSTANCE = null;
    public static TextBuilder getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TextBuilder();
            INSTANCE.initialize();
        }
        return INSTANCE;
    }
    private TextBuilder() {
    }
    protected void initialize() {
        final TemplateEngine engine = new TemplateEngine();
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
        templateResolver.setPrefix("/template_file/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setCacheable(false);
        engine.addTemplateResolver(templateResolver);

        this.engine = engine;
    }
    public String create(String templateName, String indent, Map<String, String> params) {
        final Context context = new Context(Locale.getDefault());
        context.setVariable("indent", indent);
        params.forEach((k, v) -> {
            context.setVariable(k, v);
        });
        String str = engine.process(templateName, context);
        return str;
    }
}
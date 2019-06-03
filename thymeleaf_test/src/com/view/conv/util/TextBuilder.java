package com.view.conv.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TextBuilder {
    private String[] indentStringList;
    private Map<Integer, String> indentStringCache = new HashMap<>();
    private TemplateEngine engine = null;
    private static TextBuilder INSTANCE = null;
    public static synchronized TextBuilder getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TextBuilder();
            INSTANCE.init();
        }
        return INSTANCE;
    }
    public static void initialize() {
        getInstance();
    }
    private TextBuilder() {
    }
    protected void init() {
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

        this.indentStringList = new String[10];
        this.indentStringList[0] = "";
        for(int n=1; n < this.indentStringList.length; n++) {
            StringBuilder buf = new StringBuilder(this.indentStringList[ n - 1]);
            buf.append("    ");
            this.indentStringList[n] = buf.toString();
        }
    }
    public String getIndentString(int indentLev) {
        if(indentLev >= this.indentStringList.length) {
            synchronized (this) {
                Integer key = Integer.valueOf(indentLev);
                String s = this.indentStringCache.get(key);
                if(s == null) {
                    StringBuilder buf = new StringBuilder();
                    for(int n=1; n < indentLev; n++) {
                        buf.append("    ");
                    }
                    s = buf.toString();
                    this.indentStringCache.put(key, s);
                }
                return s;
            }
        }else {
            return this.indentStringList[indentLev];
        }
    }
    public String create(String templateName, String indent, Map<String, Object> params) {
        final Context context = new Context(Locale.getDefault());
        context.setVariable("indent", indent);
        params.forEach((k, v) -> {
            context.setVariable(k, v);
        });
        String str = engine.process(templateName, context);
        return str;
    }
}

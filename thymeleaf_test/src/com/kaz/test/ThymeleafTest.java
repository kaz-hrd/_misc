package com.kaz.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafTest {

	public static void main(String[] args) {
        final TemplateEngine engine = new TemplateEngine();
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
        templateResolver.setPrefix("/template_file/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setCacheable(false);
        engine.addTemplateResolver(templateResolver);

        DTOInfo dinfo = new DTOInfo();
        dinfo.setName("SampleDTO");
        List<PropertyInfo> list = new ArrayList<PropertyInfo>();
        PropertyInfo p1 = new PropertyInfo();
        p1.setName("keyName");
        p1.setType("string");
        p1.setInitialValue("\"\"");
        list.add(p1);
        PropertyInfo p2 = new PropertyInfo();
        p2.setName("keyName");
        p2.setType("string");
        p2.setInitialValue("\"\"");
        list.add(p2);
        dinfo.setPropertiesInfo(list);

        final Context context = new Context(Locale.getDefault());
        context.setVariable("param", dinfo);
        String str = engine.process("dto.txt", context);

        System.out.println(str);
	}

}

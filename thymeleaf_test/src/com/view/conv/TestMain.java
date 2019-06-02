package com.view.conv;

import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.view.conv.form.TextInput;

public class TestMain {

    public static void main(String[] args) {

        Properties props = new Properties();
        Config.configure(props);

        //Config.setMaxWidth(900);

        Config.print();

        Logger logger = LoggerFactory.getLogger(TestMain.class);

        AbstractComponentContainer root = new CompositeCointainer(null);
        root.setId("root");

        AbstractComponentContainer layout1 = new CompositeCointainer(root);
        layout1.setId("layout1");
        layout1.setX(0);
        layout1.setY(0);
        layout1.setStates(new TreeSet<>(Arrays.asList("st1", "st2")));
        root.addViewComponent(layout1);

        RowContainer row1 = new RowContainer(layout1);
        row1.setX(0);
        row1.setY(40);
        layout1.addViewComponent(row1);

        row1.addViewComponent(new TextInput(row1, "ui01", 0, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        row1.addViewComponent(new TextInput(row1, "ui02", 120, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        row1.addViewComponent(new TextInput(row1, "ui03", 250, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        row1.addViewComponent(new TextInput(row1, "ui04", 349, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));

        layout1.addViewComponent(new TextInput(layout1, "u12", 50, 0, 0, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        layout1.addViewComponent(new TextInput(layout1, "u11", 10, 1, 0, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        layout1.addViewComponent(new TextInput(layout1, "u13", 10, 6, 0, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));

        AbstractComponentContainer card1 = new CompositeCointainer(layout1);
        card1.setId("card1");
        card1.setWidth(1100);
        card1.setX(0);
        card1.setY(120);
        card1.setStates(new TreeSet<>(Arrays.asList("st3", "st4")));
        layout1.addViewComponent(card1);

        RowContainer row2 = new RowContainer(card1);
        row2.setX(0);
        row2.setY(0);
        card1.addViewComponent(row2);

        row2.addViewComponent(new ViewComponent(row2, "ui21", 0, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        row2.addViewComponent(new ViewComponent(row2, "ui22", 120, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));
        row2.addViewComponent(new ViewComponent(row2, "ui23", 250, 0, 100, new TreeSet<>(Arrays.asList("st1", "st2", "st3", "st4"))));

        AbstractComponentContainer card2 = new CompositeCointainer(layout1);
        card2.setId("card2");
        card2.setWidth(1100);
        card2.setX(0);
        card2.setY(122);
        card2.setStates(new TreeSet<>(Arrays.asList("st3")));
        layout1.addViewComponent(card2);

        AbstractComponentContainer card3 = new CompositeCointainer(layout1);
        card3.setId("card3");
        card3.setWidth(1100);
        card3.setX(0);
        card3.setY(122);
        card3.setStates(new TreeSet<>(Arrays.asList("st1")));
        layout1.addViewComponent(card3);


        root.sort();
        System.out.println(root);

        root.adjust();
        System.out.println("---調整後---");
        System.out.println(root);
        logger.info("\n" + root.toString());

        System.out.println(root.getHtmlText());
        /*
        for(int n=0; n < root.numRows(); n++) {
            System.out.println(root.row(n));
        }
        */
    }
}

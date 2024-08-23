/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.bson.Document;

import java.util.Date;

public class ChronicleDetailsComponent extends HorizontalLayout {

    Document historyDocument;

    Icon historyIcon;

    /**
     * Constructor
     */
    public ChronicleDetailsComponent(Document document){
        addClassName("tasklayout");
        getStyle().set("margin","10px");
        this.historyDocument = document;
        setAlignItems(Alignment.CENTER);
        setVerticalComponentAlignment(Alignment.CENTER);
        prepareLayout();
    }

    /**
     * Function for preparing layout
     */
    void prepareComponents(){
        switch(historyDocument.getString("source")){
            case "task":
                historyIcon = VaadinIcon.TICKET.create();
                break;
            case "memory":
                historyIcon = new Icon("vaadin", "book");
                break;
            case "timebox":
                historyIcon = new Icon("vaadin", "history");
                break;
            case "note":
                historyIcon = new Icon("vaadin", "file-text");
                break;
            default:
                historyIcon = new Icon("vaadin", "user");
                break;
        }
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");

        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        center_layout.setAlignItems(Alignment.CENTER);
        center_layout.setWidth("80%");

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(Alignment.END);
        right_layout.setWidth("80%");

        left_layout.add(historyIcon);

        center_layout.add(new H6(historyDocument.getString("message")));

        Date date = new Date(historyDocument.getLong("timestamp"));
        right_layout.add(new H6(date.toString()));

        add(left_layout,center_layout,right_layout);
    }
}

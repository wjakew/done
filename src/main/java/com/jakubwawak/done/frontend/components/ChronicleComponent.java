/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.backend.database.DatabaseHistory;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Component for displaying chronicle data (history of user actions)
 */
public class ChronicleComponent extends VerticalLayout {

    VirtualList<Document> historyList;
    ArrayList<Document> content;

    /**
     * Constructor
     */
    public ChronicleComponent(){
        addClassName("taskview");
        prepareLayout();
        setHeight("80%"); setWidth("80%");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        historyList = new VirtualList<>();
        historyList.addClassName("listtask");

        DatabaseHistory dh = new DatabaseHistory();
        content = dh.getLoggedUserHistory();
        historyList.setItems(content);
        historyList.setRenderer(documentRenderer);

    }

    /**
     * Component renderer for document
     */
    private ComponentRenderer<Component, Document> documentRenderer = new ComponentRenderer<>(document ->{
        ChronicleDetailsComponent tdc = new ChronicleDetailsComponent(document);
        return tdc;
    });

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();
        add(new H6("Your Look Into the Past!"));
        if (content.isEmpty()){
            add(new H6("No history found."));
        }
        else{
            add(historyList);
        }
    }
}

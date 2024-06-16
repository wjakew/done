/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseMemory;
import com.jakubwawak.done.backend.entity.DoneMemory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.ArrayList;

/**
 * Object for creating custom memory list
 */
public class MemoryListComponent extends VirtualList {

    HeaderComponent header;

    ArrayList<DoneMemory> content;

    DatabaseMemory databaseMemory;

    /**
     * Constructor
     */
    public MemoryListComponent(){
        this.content = new ArrayList<>();
        databaseMemory = new DatabaseMemory();
        addClassName("listtask-border");
        loadMemories();
        setItems(content);
        setRenderer(doneMemoryRenderer);
    }

    private ComponentRenderer<Component, DoneMemory> doneMemoryRenderer = new ComponentRenderer<>(memory ->{

        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);
        cardLayout.addClassName("memorylayout");
        cardLayout.setAlignItems(FlexComponent.Alignment.START);
        cardLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START);
        VerticalLayout infoLayout = new VerticalLayout();

        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);

        infoLayout.add(new H6(memory.memory_date.toString()));

        cardLayout.add(infoLayout);

        return cardLayout;
    });

    /**
     * Function for loading memories from database
     */
    public void loadMemories(){
        content.clear();
        content.addAll(databaseMemory.getCollectionOfMemoriesLoggedUser());
        getDataProvider().refreshAll();
    }
}

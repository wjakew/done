/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.jakubwawak.done.frontend.components.MemoryListComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("memories")
@Route("memories")
public class MemoryView extends VerticalLayout {

    MemoryListComponent mlc;

    HeaderComponent header;

    /**
     * Constructor
     */
    public MemoryView(){
        addClassName("taskview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        mlc = new MemoryListComponent();
        header = new HeaderComponent();
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setVerticalComponentAlignment(Alignment.CENTER);

        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();

        leftLayout.setWidth("30%");leftLayout.setHeight("100%");
        leftLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        leftLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        leftLayout.getStyle().set("text-align", "center");

        rightLayout.setSizeFull();
        rightLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        rightLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        rightLayout.getStyle().set("text-align", "center");

        leftLayout.add(mlc);

        mainLayout.add(leftLayout,rightLayout);

        add(header);
        add(mainLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
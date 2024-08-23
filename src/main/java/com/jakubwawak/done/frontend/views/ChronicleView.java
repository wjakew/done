/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.frontend.components.ChronicleComponent;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("a look into the past.")
@Route("chronicle")
public class ChronicleView extends VerticalLayout {

    HeaderComponent header;

    ChronicleComponent cc;

    /**
     * Constructor
     */
    public ChronicleView(){
        addClassName("taskview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        cc = new ChronicleComponent();
        header = new HeaderComponent();
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();
        add(header);
        add(cc);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
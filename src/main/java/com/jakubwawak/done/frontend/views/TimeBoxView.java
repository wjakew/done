/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("your timeboxes")
@Route("timebox")
public class TimeBoxView extends VerticalLayout {

    HeaderComponent header;

    /**
     * Constructor
     */
    public TimeBoxView(){
        addClassName("taskview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new HeaderComponent();
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();

        if (DoneApplication.loggedUser != null){
            prepareComponents();
            add(header);
        }

        // user is not logged
        else{
            add(new H1("no one is logged! forbidden access"));
        }

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
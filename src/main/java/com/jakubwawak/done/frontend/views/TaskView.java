/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.jakubwawak.done.frontend.components.InsertTaskComponent;
import com.jakubwawak.done.frontend.components.ListTaskComponent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("task to be done.")
@Route("tasks")
public class TaskView extends VerticalLayout {

    HeaderComponent header;
    InsertTaskComponent insertTaskComponent;

    /**
     * Constructor
     */
    public TaskView(){
        addClassName("taskview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new HeaderComponent();
        insertTaskComponent = new InsertTaskComponent();
        DoneApplication.ltc = new ListTaskComponent();
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){

        if (DoneApplication.loggedUser != null){
            prepareComponents();
            add(header);
            add(insertTaskComponent);
            add(DoneApplication.ltc);
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
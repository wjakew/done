/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.backend.database.DatabaseTimeBox;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.jakubwawak.done.frontend.components.InsertTaskComponent;
import com.jakubwawak.done.frontend.components.ListTaskComponent;
import com.jakubwawak.done.frontend.components.TimeBoxTaskListComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Window for logging user to the app
 */
public class SelectTaskWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    Button select_button;

    ListTaskComponent ltc;
    InsertTaskComponent insertTaskComponent;


    DoneTimeBox timeBoxObject;
    TimeBoxTaskListComponent tlbtc;

    /**
     * Constructor
     */
    public SelectTaskWindow(DoneTimeBox timeBoxObject, TimeBoxTaskListComponent tlbtc){
        main_dialog = new Dialog();
        this.timeBoxObject = timeBoxObject;
        this.tlbtc = tlbtc;
        main_layout = new VerticalLayout();
        main_dialog.setClassName("done-task-details-window-white");
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        ltc = new ListTaskComponent(1);
        insertTaskComponent = new InsertTaskComponent(ltc);

        select_button = new Button("Select Task",this::setSelect_button);
        select_button.addClassName("buttonprimary");
        select_button.setWidthFull();
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout
        main_layout.add(new H6("Select task to work on"));
        main_layout.add(insertTaskComponent);
        main_layout.add(ltc);
        main_layout.add(select_button);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color",backgroundStyle);
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        main_dialog.add(main_layout);
        main_dialog.setWidth(width);main_dialog.setHeight(height);
    }

    /**
     * Function for action UI selecting button
     * @param ex
     */
    private void setSelect_button(ClickEvent ex){
        if ( ltc.selected != null ){
            timeBoxObject.addTask(ltc.selected.task_id);
            ltc.reload();
            DatabaseTimeBox dtb = new DatabaseTimeBox();
            dtb.updateTimeBox(timeBoxObject);
            tlbtc.reload();
            main_dialog.close();
        }
    }
}
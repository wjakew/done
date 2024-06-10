/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseTimeBox;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.jakubwawak.done.frontend.components.TimeBoxComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Window for logging user to the app
 */
public class TimeBoxCreateWindow {

    // variables for setting x and y of window
    public String width = "50%";
    public String height = "30%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField timebox_name_field;
    Button addtimebox_button;

    TimeBoxComponent parent;

    /**
     * Constructor
     */
    public TimeBoxCreateWindow(TimeBoxComponent parent){
        main_dialog = new Dialog();
        this.parent = parent;
        main_dialog.setClassName("done-task-details-window");
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        timebox_name_field = new TextField("");
        timebox_name_field.setPlaceholder("TimeBox Name");
        timebox_name_field.setMaxLength(250);
        timebox_name_field.addClassName("textfield-white");
        timebox_name_field.setWidth("100%");

        addtimebox_button = new Button("Create", VaadinIcon.PLUS.create(),this::setAddtimebox_button_button);
        addtimebox_button.addClassName("buttonprimary-white");


    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

        main_layout.add(new H3("Create New Timebox"));
        main_layout.add(timebox_name_field);
        main_layout.add(addtimebox_button);

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
     * UI Action for button addtimebox_button click
     */
    private void setAddtimebox_button_button(ClickEvent ex){
        String timeboxName = timebox_name_field.getValue();
        if ( !timeboxName.isEmpty() ){
             DoneTimeBox dtb = new DoneTimeBox();
             dtb.timebox_name = timeboxName;
             dtb.user_id = DoneApplication.loggedUser.user_id;
             DatabaseTimeBox databaseTimeBox = new DatabaseTimeBox();

             if ( databaseTimeBox.createTimeBox(dtb) == 1 ){
                 // operation done
                 DoneApplication.notificationService("Timebox ("+timeboxName+") created",1);
                 // refresh the view
                 parent.refeshTimeBoxList();
                 main_dialog.close();
             }
             else{
                 // operation failed
                 DoneApplication.notificationService("Failed to add timebox, check application logs",1);
             }
        }
        else{
            DoneApplication.notificationService("Name cannot be empty!",1);
        }
    }
}
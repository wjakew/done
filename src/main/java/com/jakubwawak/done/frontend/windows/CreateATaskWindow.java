/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.datamanager.TaskDataManager;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;

/**
 * Window for logging user to the app
 */
public class CreateATaskWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField taskname_field;
    DateTimePicker day_picker;
    Button addtask_button;

    /**
     * Constructor
     */
    public CreateATaskWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        taskname_field = new TextField("");
        taskname_field.setPlaceholder("name");
        taskname_field.setMaxLength(250);
        taskname_field.addClassName("textfield-insertwindow");
        taskname_field.setWidthFull();

        day_picker = new DateTimePicker();
        day_picker.setValue(LocalDateTime.now());
        day_picker.addClassName("textfield");
        day_picker.setWidthFull();

        addtask_button = new Button("Add Task", VaadinIcon.PLUS.create(),this::setInsert_button);
        addtask_button.addClassName("buttonprimary");
        addtask_button.setWidthFull();
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

        main_layout.add(taskname_field,day_picker,addtask_button);

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

    private void setInsert_button(ClickEvent e){
        insertTask();
    }
    /**
     * Function for inserting task used in UI
     */
    void insertTask(){
        String task_name = taskname_field.getValue();
        if ( !task_name.isBlank() ){
            DoneTask task = new DoneTask(task_name,day_picker.getValue());
            TaskDataManager tdm = new TaskDataManager();
            int ans = tdm.insertTask(task);
            if ( ans == 1 ){
                taskname_field.clear();
                DoneApplication.ltc.reload(); // reloading UI component on task view
            }
        }
    }
}
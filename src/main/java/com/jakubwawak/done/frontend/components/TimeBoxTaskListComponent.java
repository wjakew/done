/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.jakubwawak.done.backend.maintanance.GridElement;
import com.jakubwawak.done.datamanager.TaskDataManager;
import com.jakubwawak.done.frontend.windows.DoneTaskDetailsWindow;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.ArrayList;

/**
 * Object for creating custom timebox list
 */
public class TimeBoxTaskListComponent extends VirtualList {

    ArrayList<DoneTask> content;

    ComboBox statusCombobox;

    Button detailsButton, trashButton;

    DatabaseTask databaseTask;

    DoneTimeBox timeBoxObject;


    /**
     * Constructor
     */
    public TimeBoxTaskListComponent(DoneTimeBox timeBoxObject){
        this.content = timeBoxObject.currentTask;
        this.timeBoxObject = timeBoxObject;
        addClassName("listtask-border");
        databaseTask = new DatabaseTask();
        this.content = content;
        setItems(content);
        setRenderer(donetaskRenderer);
    }

    private ComponentRenderer<Component, DoneTask> donetaskRenderer = new ComponentRenderer<>(task ->{
        TaskDetailsComponent tdc = new TaskDetailsComponent(task,this,1);
        return tdc;
    });

    /**
     * Function for adding new task to UI
     * @param doneTask
     */
    public void addNewTask(DoneTask doneTask){
        content.add(doneTask);
        getDataProvider().refreshAll();
    }

    /**
     * Function for removing new task
     * @param doneTask
     */
    public void removeTask(DoneTask doneTask){
        content.remove(doneTask);
        getDataProvider().refreshAll();
    }

    /**
     * Function for reloading tasks from object
     */
    public void reload(){
        content.clear();
        timeBoxObject.loadTasks();
        content.addAll(timeBoxObject.currentTask);
        getDataProvider().refreshAll();
    }
}

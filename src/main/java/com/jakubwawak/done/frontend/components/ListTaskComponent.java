/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.entity.DoneTask;
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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * UI object for creating task list
 */
public class ListTaskComponent extends VirtualList {

    public List<DoneTask> content;
    DatabaseTask databaseTask;

    public DoneTask selected;

    ComboBox statusCombobox;

    Button detailsButton, trashButton;

    public int reloadMode;

    public ListTaskComponent(){
        super();
        reloadMode = 0;
        addClassName("listtask");
        databaseTask = new DatabaseTask();
        content = databaseTask.loadAllTasks();
        selected = null;
        setItems(content);
        setRenderer(donetaskRenderer);
    }

    private ComponentRenderer<Component, DoneTask> donetaskRenderer = new ComponentRenderer<>(task ->{
        TaskDetailsComponent tdc = new TaskDetailsComponent(task,this,0);
        return tdc;
    });

    /**
     * Function for reloading data
     * reloadMode:
     * 0 - reloads all data
     * 1 - only NEW
     * 2 - only IN PROGRESS
     * 3 - only DONE
     * 4 - only CURRENT ( without DONE )
     */
    public void reload(){
        content.clear();
        content.addAll(databaseTask.loadSpecificTasks(reloadMode));
        getDataProvider().refreshAll();
    }
}

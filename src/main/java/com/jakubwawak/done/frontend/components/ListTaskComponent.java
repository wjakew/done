/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.maintanance.GridElement;
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
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UI object for creating task list
 */
public class ListTaskComponent extends VirtualList {

    public List<DoneTask> content;
    DatabaseTask databaseTask;

    ComboBox statusCombobox;

    Button detailsButton, trashButton;

    public ListTaskComponent(){
        super();
        addClassName("listtask");
        databaseTask = new DatabaseTask();
        content = databaseTask.loadTasks();
        setItems(content);
        setRenderer(donetaskRenderer);
    }

    private ComponentRenderer<Component, DoneTask> donetaskRenderer = new ComponentRenderer<>(task ->{

        detailsButton = new Button("", VaadinIcon.INFO.create());
        detailsButton.addClassName("buttonprimary");

        trashButton = new Button("", VaadinIcon.TRASH.create());
        trashButton.addClassName("buttonprimary");

        ArrayList<GridElement> statusContent = new ArrayList<>();
        statusContent.add(new GridElement("NEW"));
        statusContent.add(new GridElement("IN PROGRESS"));
        statusContent.add(new GridElement("DONE"));

        statusCombobox = new ComboBox();
        statusCombobox.addClassName("textfield");
        statusCombobox.setItems(statusContent);
        statusCombobox.setItemLabelGenerator(gridElement -> {
            if (gridElement instanceof GridElement) {
                return ((GridElement) gridElement).getGridelement_text();
            } else {
                return "";
            }
        });
        statusCombobox.setValue(new GridElement(task.getStatus()));

        statusCombobox.addValueChangeListener(e->{
            GridElement ge = (GridElement) e.getValue();
            task.task_status = ge.getGridelement_text();
            databaseTask.updateTask(task);
            reload();
        });

        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);
        cardLayout.addClassName("tasklayout");
        cardLayout.setAlignItems(FlexComponent.Alignment.START);
        cardLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START);
        VerticalLayout infoLayout = new VerticalLayout();

        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);

        infoLayout.add(new H4(task.getName()));
        infoLayout.add(new H6(task.getTime()));

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        left_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add(infoLayout);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.END);
        right_layout.add(detailsButton,trashButton,statusCombobox);
        right_layout.setWidth("80%");

        switch(task.getStatus()){
            case "NEW":
            {
                cardLayout.getStyle().set("border-color","red");
                break;
            }
            case "IN PROGRESS":
            {
                cardLayout.getStyle().set("border-color","blue");
                break;
            }
            case "DONE":
            {
                cardLayout.getStyle().set("border-color","green");
                break;
            }
        }

        cardLayout.add(left_layout,right_layout);
        return cardLayout;
    });

    /**
     * Function for reloading data
     */
    public void reload(){
        content.clear();
        content.addAll(databaseTask.loadTasks());
        getDataProvider().refreshAll();
    }
}

/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.maintanance.GridElement;
import com.jakubwawak.done.datamanager.TaskDataManager;
import com.jakubwawak.done.frontend.windows.DoneTaskDetailsWindow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

/**
 * Object for showing task details in UI
 */
public class TaskDetailsComponent extends HorizontalLayout {

    DoneTask doneTask;

    ComboBox statusCombobox;

    Button detailsButton, trashButton;

    ListTaskComponent parent;

    TimeBoxTaskListComponent parent2;

    int mode;

    /**
     * Constructor
     * @param mode (int) 0 - extended view, simple view
     */
    public TaskDetailsComponent(DoneTask doneTask,ListTaskComponent parent,int mode){
        super();
        this.doneTask = doneTask;
        this.parent = parent;
        this.mode = mode;
        prepareLayout();
    }

    /**
     * Constructor
     * @param mode (int) 0 - extended view, simple view
     */
    public TaskDetailsComponent(DoneTask doneTask,TimeBoxTaskListComponent parent,int mode){
        super();
        this.doneTask = doneTask;
        this.parent2 = parent;
        this.mode = mode;
        prepareLayout();
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        if ( mode == 0 ){
            prepareExtendedLayout();
        }
        else{
            prepareSimpleLayout();
        }

    }

    /**
     * Function for preparing simple layout of task UI
     */
    void prepareSimpleLayout(){
        setMargin(true);
        addClassName("tasklayout");
        setAlignItems(FlexComponent.Alignment.START);
        setVerticalComponentAlignment(FlexComponent.Alignment.START);

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        left_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        left_layout.setWidth("80%");

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.END);
        right_layout.setWidth("80%");

        left_layout.add(new H6(doneTask.task_name));

        //TODO --- add more details to simple view

        add(left_layout,right_layout);
    }

    /**
     * Function for preparing extended layout of task UI
     */
    void prepareExtendedLayout(){
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
        statusCombobox.setValue(new GridElement(doneTask.getStatus()));
        statusCombobox.setAllowCustomValue(false);

        statusCombobox.addValueChangeListener(e->{
            GridElement ge = (GridElement) e.getValue();
            System.out.println(ge.getGridelement_text());
            doneTask.task_status = ge.getGridelement_text();
            TaskDataManager tdm = new TaskDataManager();
            tdm.upgradeTask(doneTask);
            if ( parent != null )
                parent.reload();
            if ( parent2 != null )
                parent2.reload();
        });

        trashButton.addClickListener(e->{
            TaskDataManager tdm = new TaskDataManager();
            tdm.deleteTask(doneTask);
        });

        detailsButton.addClickListener(e->{
            DoneTaskDetailsWindow dtdw = new DoneTaskDetailsWindow(doneTask);
            dtdw.main_dialog.open();
        });

        setMargin(true);
        addClassName("tasklayout");
        setAlignItems(FlexComponent.Alignment.START);
        setVerticalComponentAlignment(FlexComponent.Alignment.START);
        VerticalLayout infoLayout = new VerticalLayout();

        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);

        infoLayout.add(new H4(doneTask.getName()));
        infoLayout.add(new H6(doneTask.getTime()));

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

        switch(doneTask.getStatus()){
            case "NEW":
            {
                getStyle().set("border-color","red");
                break;
            }
            case "IN PROGRESS":
            {
                getStyle().set("border-color","blue");
                break;
            }
            case "DONE":
            {
                getStyle().set("border-color","green");
                break;
            }
        }
        add(left_layout,right_layout);

        addClickListener(e->{
            if ( parent != null )
                parent.selected = doneTask;
        });
    }
}

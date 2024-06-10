/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.jakubwawak.done.frontend.windows.SelectTaskWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

/**
 * UI object for creating viewer for timebox object
 */
public class TimeBoxDetailsComponent extends VerticalLayout {

    DoneTimeBox timeBoxObject;

    TextArea notesArea;

    TimeBoxTaskListComponent tbtlc;

    DoneTask selected;

    HorizontalLayout buttonLayout;
    Button addTask, removeTask, detailsTask;

    /**
     * Constructor
     * @param timeBoxObject
     */
    public TimeBoxDetailsComponent(DoneTimeBox timeBoxObject){
        this.timeBoxObject = timeBoxObject;
        addClassName("time-box-details");
        selected = null;
        prepareLayout();

        setHeight("100%");
        setWidth(DoneApplication.UI_WIDTH);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     * Function for preparing content
     */
    void prepareContent(){
        notesArea = new TextArea("Notes");
        notesArea.setValue(timeBoxObject.timebox_description);
        notesArea.setClassName("textfield-white");

        tbtlc = new TimeBoxTaskListComponent(timeBoxObject,selected);

        buttonLayout = new HorizontalLayout();

        addTask = new Button("", VaadinIcon.PLUS.create(),this::setAddTask);
        addTask.setClassName("buttonprimary");
        removeTask = new Button("", VaadinIcon.TRASH.create());
        removeTask.setClassName("buttonprimary");
        detailsTask = new Button("", VaadinIcon.INFO.create());
        detailsTask.setClassName("buttonprimary");

        buttonLayout.add(addTask,removeTask,detailsTask);

    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareContent();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setVerticalComponentAlignment(Alignment.CENTER);

        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();

        leftLayout.setSizeFull();
        leftLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        leftLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        leftLayout.getStyle().set("text-align", "center");

        rightLayout.setSizeFull();
        rightLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        rightLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightLayout.getStyle().set("text-align", "center");

        leftLayout.add(notesArea);

        rightLayout.add(new H6(timeBoxObject.timebox_id.toString()));
        rightLayout.add(tbtlc,buttonLayout);

        mainLayout.add(leftLayout,rightLayout);
        add(mainLayout);
    }

    /**
     * Function for setting add task
     * @param ex
     */
    private void setAddTask(ClickEvent ex){
        SelectTaskWindow stw = new SelectTaskWindow(timeBoxObject,tbtlc);
        add(stw.main_dialog);
        stw.main_dialog.open();
    }
}

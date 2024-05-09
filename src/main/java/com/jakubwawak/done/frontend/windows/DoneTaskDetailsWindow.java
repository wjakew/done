/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.maintanance.GridElement;
import com.jakubwawak.done.datamanager.TaskDataManager;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;

/**
 * Window for logging user to the app
 */
public class DoneTaskDetailsWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    ComboBox<GridElement> statusCombobox;

    DoneTask doneTask;

    Grid<GridElement> commentsGrid;
    ArrayList<GridElement> commentsContent;

    Button addComment_button;

    HorizontalLayout addCommentLayout;
    TextField comment_field;
    Button addCommentField_button;

    /**
     * Constructor
     */
    public DoneTaskDetailsWindow(DoneTask doneTask){
        this.doneTask = doneTask;
        main_dialog = new Dialog();
        main_dialog.addClassName("done-task-details-window");
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components

        ArrayList<GridElement> statusContent = new ArrayList<>();
        statusContent.add(new GridElement("NEW"));
        statusContent.add(new GridElement("IN PROGRESS"));
        statusContent.add(new GridElement("DONE"));

        statusCombobox = new ComboBox();
        statusCombobox.addClassName("textfield-white");
        statusCombobox.setItems(statusContent);
        statusCombobox.setItemLabelGenerator(gridElement -> {
            if (gridElement instanceof GridElement) {
                return ((GridElement) gridElement).getGridelement_text();
            } else {
                return "";
            }
        });
        statusCombobox.setValue(new GridElement(doneTask.getStatus()));

        statusCombobox.addValueChangeListener(e->{
            GridElement ge = (GridElement) e.getValue();
            doneTask.task_status = ge.getGridelement_text();
            TaskDataManager tdm = new TaskDataManager();
            tdm.upgradeTask(doneTask);
            DoneApplication.ltc.reload();
        });

        commentsContent = new ArrayList<>();
        commentsContent.addAll(doneTask.getComments());

        commentsGrid = new Grid<>(GridElement.class,false);
        commentsGrid.addClassName("aim-grid");
        commentsGrid.addColumn(GridElement::getGridelement_text).setHeader("Task Comments");
        commentsGrid.setItems(commentsContent);

        commentsGrid.setWidth("100%");

        addComment_button = new Button("Add comment", VaadinIcon.COMMENT.create(),this::setaddComment_button);
        addComment_button.addClassName("buttonprimary-white");
        addComment_button.setWidthFull();

        // creating addcomment layout
        comment_field = new TextField("");
        comment_field.setPlaceholder("comment");comment_field.setPrefixComponent(VaadinIcon.COMMENT.create());
        comment_field.setWidth("80%");
        comment_field.addClassName("textfield-white");

        addCommentField_button = new Button("Add", VaadinIcon.PLUS.create(),this::setAddCommentField_button);
        addCommentField_button.addClassName("buttonprimary-white");

        addCommentLayout = new HorizontalLayout();
        addCommentLayout.setWidthFull();

        addCommentLayout.add(comment_field,addCommentField_button);
        addCommentLayout.setVisible(false);

    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

        main_layout.add(new H3(doneTask.task_name));
        main_layout.add(new H6(doneTask.task_id.toString()));
        main_layout.add(new H6("Time: "+doneTask.task_timestamp));
        main_layout.add(statusCombobox);
        main_layout.add(commentsGrid);
        main_layout.add(addComment_button);
        main_layout.add(addCommentLayout);
        main_layout.addClassName("done-task-details-window-layout");

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
     * Function for setting comment field to task UI
     * @param ex
     */
    private void setAddCommentField_button(ClickEvent ex){
        if ( comment_field.getValue().length() > 0 ){
            doneTask.task_comments.add(comment_field.getValue());
            comment_field.setValue("");
            TaskDataManager tdm = new TaskDataManager();
            tdm.upgradeTask(doneTask);
            commentsContent.clear();
            commentsContent.addAll(doneTask.getComments());
            commentsGrid.getDataProvider().refreshAll();
        }
    }

    /**
     * Function for setting comment in UI
     * @param ex
     */
    private void setaddComment_button(ClickEvent ex){
        if ( addCommentLayout.isVisible() ){
            addCommentLayout.setVisible(false);
        }
        else{
            addCommentLayout.setVisible(true);
        }
    }
}
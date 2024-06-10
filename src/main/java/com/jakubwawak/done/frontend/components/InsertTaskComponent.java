/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.datamanager.TaskDataManager;
import com.jakubwawak.done.frontend.windows.CreateATaskWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import static com.jakubwawak.done.DoneApplication.UI_WIDTH;

/**
 * Object for UI inserting task
 */
public class InsertTaskComponent extends HorizontalLayout {

    TextField taskname_field;

    Button insert_button;

    Button more_button;

    ListTaskComponent destinationObject;
    /**
     * Constructor
     */
    public InsertTaskComponent(ListTaskComponent destinationObject){
        setAlignItems(Alignment.CENTER);
        this.destinationObject = destinationObject;
        setVerticalComponentAlignment(Alignment.CENTER);
        setWidth(UI_WIDTH);

        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        taskname_field = new TextField("");
        taskname_field.setWidth("100%");
        taskname_field.setPlaceholder("the greatest task ever");
        taskname_field.setPrefixComponent(VaadinIcon.TASKS.create());
        taskname_field.addClassName("textfield");
        taskname_field.setMaxLength(250);

        insert_button = new Button("",VaadinIcon.INSERT.create(),this::setInsert_button);
        insert_button.addClassName("buttonprimary");

        more_button = new Button("",VaadinIcon.QUESTION.create(),this::setMore_button);
        more_button.addClassName("buttonprimary");

        taskname_field.addKeyPressListener(e->{
            if ( e.getKey().equals(Key.ENTER)){
                insertTask();
            }
        });
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();
        add(taskname_field,insert_button,more_button);
    }

    private void setInsert_button(ClickEvent e){
        insertTask();
    }

    private void setMore_button(ClickEvent e){
        CreateATaskWindow catw = new CreateATaskWindow();
        catw.main_dialog.open();

    }
    /**
     * Function for inserting task used in UI
     */
    void insertTask(){
        String task_name = taskname_field.getValue();
        if ( !task_name.isBlank() ){
            TaskDataManager tdm = new TaskDataManager();
            int ans = tdm.insertTask(task_name);
            if ( ans == 1 ){
                taskname_field.clear();
                DoneApplication.ltc.reload();   // reloading UI component on task view
                if ( destinationObject != null ){
                    destinationObject.reload(); // reloading UI component on passed list view
                }
            }
        }
    }
}

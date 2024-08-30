/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.frontend.components.DoneUserDetailsComponent;
import com.jakubwawak.done.frontend.components.TaskDetailsComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.ArrayList;

/**
 * Window for logging user to the app
 */
public class AdminWindow {

    // variables for setting x and y of window
    public String width = "80%";
    public String height = "80%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    Button enableaccoutcreation_button;
    Button createUser;

    VirtualList<DoneUser> user_list;
    ArrayList<DoneUser> user_content;




    /**
     * Constructor
     */
    public AdminWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        enableaccoutcreation_button = new Button("Enable Account Creation", VaadinIcon.USER_CARD.create(),this::setEnableaccoutcreation_button);
        enableaccoutcreation_button.setWidthFull();
        enableaccoutcreation_button.addClassName("buttonprimary");

        if (DoneApplication.enableUserCreationFlag == 1){
            enableaccoutcreation_button.setText("Disable Account Creation");
            enableaccoutcreation_button.getStyle().set("color","red");
        }
        else{
            enableaccoutcreation_button.setText("Enable Account Creation");
            enableaccoutcreation_button.getStyle().set("color","green");
        }
        DatabaseUser du = new DatabaseUser();
        user_list = new VirtualList<>();
        user_content = du.getAllUsers();
        user_list.addClassName("listtask");
        user_list.setItems(user_content);
        user_list.setRenderer(donetaskRenderer);
        user_list.setSizeFull();

        createUser = new Button("Create User", VaadinIcon.PLUS.create(),this::setCreateUser);
        createUser.addClassName("buttonprimary");
        createUser.setWidthFull();
    }

    /**
     * Renderer for done tasks
     */
    private ComponentRenderer<Component, DoneUser> donetaskRenderer = new ComponentRenderer<>(user ->{
        DoneUserDetailsComponent dudc = new DoneUserDetailsComponent(user,this);
        return dudc;
    });

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

        main_layout.add(new H6("ADMIN MANAGMENT"));
        main_layout.add(new H6("User Creation"),enableaccoutcreation_button);
        main_layout.add(new H6("User Managment"));
        main_layout.add(user_list);
        main_layout.add(createUser);
        main_layout.add(new H6(DoneApplication.version+"/"+DoneApplication.build));

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
     * Function for reloading virtual list
     */
    public void reload(){
        DatabaseUser du = new DatabaseUser();
        user_content.clear();
        user_content.addAll(du.getAllUsers());
        user_list.getDataProvider().refreshAll();
    }

    /**
     * Function for setting create user button
     * @param ex
     */
    private void setCreateUser(ClickEvent ex){
        CreateUserWindow cuw = new CreateUserWindow();
        main_layout.add(cuw.main_dialog);
        cuw.main_dialog.open();
    }

    /**
     * Function for setting create button
     */
    private void setEnableaccoutcreation_button(ClickEvent ex){
        if (DoneApplication.enableUserCreationFlag == 1){
            DoneApplication.enableUserCreationFlag = 0;
            enableaccoutcreation_button.setText("Enable Account Creation");
            enableaccoutcreation_button.getStyle().set("color","green");
        }
        else{
            DoneApplication.enableUserCreationFlag = 1;
            enableaccoutcreation_button.setText("Disable Account Creation");
            enableaccoutcreation_button.getStyle().set("color","red");
        }
    }
}
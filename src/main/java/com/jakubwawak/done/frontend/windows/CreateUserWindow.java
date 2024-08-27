/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.Password_Validator;
import com.jakubwawak.done.backend.maintanance.RandomWordGeneratorEngine;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Window for logging user to the app
 */
public class CreateUserWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField email_field, telephone_field;
    Button create_button;

    /**
     * Constructor
     */
    public CreateUserWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        email_field = new TextField("email");
        email_field.setPlaceholder("urmom@done.com");
        email_field.addClassName("textfield");
        email_field.setWidthFull();

        telephone_field = new TextField("telephone");
        telephone_field.setPlaceholder("997");
        telephone_field.addClassName("textfield");
        telephone_field.setWidthFull();

        create_button = new Button("Create Account", VaadinIcon.USER.create(),this::setCreate_button);
        create_button.addClassName("buttonprimary");
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

        main_layout.add(new H1("Start doing with done!"));
        main_layout.add(email_field);
        main_layout.add(telephone_field);
        main_layout.add(create_button);

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
     * Function for setting create button
     * @param ex
     */
    private void setCreate_button(ClickEvent ex){
        DatabaseUser databaseUser = new DatabaseUser();
        if ( email_field.getValue().isBlank() || telephone_field.getValue().isBlank() ){
            DoneApplication.notificationService("Fields cannot be empty",1);
        }
        else{
            if ( databaseUser.getUserByEmail(email_field.getValue()) == null ) {
                try{
                    DoneUser newUser = new DoneUser();
                    newUser.user_email = email_field.getValue();
                    newUser.user_telephone = telephone_field.getValue();
                    RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
                    newUser.user_role = "USER";
                    String password = rwge.generateRandomString(14,true,true);
                    Password_Validator pv = new Password_Validator(password);
                    newUser.user_password = pv.hash();
                    int ans = databaseUser.createUser(newUser);
                    if ( ans == 1 ){
                        MessageWindow mw = new MessageWindow("Account created","Password: "+password);
                        main_layout.add(mw.main_dialog);
                        mw.main_dialog.open();
                        main_dialog.close();
                    }
                    else{
                        DoneApplication.notificationService("Error occured",1);
                    }
                }catch(Exception e){
                    DoneApplication.notificationService("Error occured "+e.toString(),1);
                }

            }
            else{
                DoneApplication.notificationService("User with this email already exists",1);
            }
        }

    }
}
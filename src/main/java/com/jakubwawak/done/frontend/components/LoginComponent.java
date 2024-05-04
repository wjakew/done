
/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.datamanager.UserDataManager;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

/**
 * Object for creating login component for UI
 */
public class LoginComponent extends VerticalLayout {


    TextField login_field;
    PasswordField password_field;

    Button login_button;

    /**
     * Constructor
     */
    public LoginComponent(){
        prepareLayout();
        addClassName("logincomponent");
        setMinWidth("50%");setMinHeight("50%");
        setMaxWidth("50%");setMaxHeight("50%");
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        login_field = new TextField("E-Mail");
        login_field.setMinWidth("70%");
        login_field.addClassName("textfield");
        login_field.setPlaceholder("your@fuckin.email");

        password_field = new PasswordField("Password");
        password_field.setMinWidth("70%");
        password_field.addClassName("textfield");
        password_field.setPlaceholder("i hope this password is fire");

        login_button = new Button("Let's fuckin go!",this::setLogin_button);
        login_button.addClassName("buttonprimary");
        login_button.setMinWidth("70%");
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();
        add(new H1("done."));
        add(new H6("The best web application for getting shit done."));
        add(login_field);
        add(password_field);
        add(login_button);
    }

    /**
     * UI function for login user to the app
     * @param ex
     */
    private void setLogin_button(ClickEvent ex){
        String login = login_field.getValue();
        String password = password_field.getValue();
        if ( !login.isBlank() && !password.isBlank() ){
            UserDataManager udm = new UserDataManager();
            int ans = udm.loginUser(login,password);
            if ( ans == 1 ){
                // route to home screen
                login_button.getUI().ifPresent(ui ->
                        ui.navigate("/tasks"));
            }
            else{
                DoneApplication.notificationService("Wrong user or password!",1);
            }
        }
        else{
            DoneApplication.notificationService("Cannot find user ("+login+")",1);
        }
    }
}

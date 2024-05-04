/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.datamanager.UserDataManager;
import com.jakubwawak.done.frontend.components.LoginComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Main application web view
 */
@PageTitle("just do it")
@Route(value = "login")
@RouteAlias(value = "/")
public class LoginView extends VerticalLayout {

    LoginComponent loginComponent;

    /**
     * Constructor
     */
    public LoginView(){
        addClassName("loginview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        if (DoneApplication.loggedUser != null){
            UserDataManager udm = new UserDataManager();
            udm.logoutUser();
        }
        loginComponent = new LoginComponent();
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();
        add(loginComponent);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseAPI;
import com.jakubwawak.done.backend.entity.ApiKey;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Window for logging user to the app
 */
public class APIWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    H3 windowHeader;
    H6 apiKey, apiCode;
    Button generateButton, revokeButton;

    /**
     * Constructor
     */
    public APIWindow(){
        main_dialog = new Dialog();
        main_dialog.setClassName("done-task-details-window");
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        DatabaseAPI databaseAPI = new DatabaseAPI();
        ApiKey api_key = databaseAPI.getUserApiKey(DoneApplication.loggedUser.user_id);
        generateButton = new Button("Generate new API key", VaadinIcon.KEY.create(),this::setGenerateButton);
        generateButton.addClassName("buttonprimary");
        revokeButton = new Button("Revoke API key", VaadinIcon.TRASH.create(),this::setRevokeButton);
        revokeButton.addClassName("buttonprimary");
        revokeButton.getStyle().set("color","red");
        if ( api_key != null ){
            windowHeader = new H3("API Enabled");
            apiKey = new H6("API Key: "+api_key.api_key);
            apiCode = new H6("API Code: "+api_key.api_code);
        }
        else{
            windowHeader = new H3("API Disabled");
        }
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout
        main_layout.add(windowHeader);
        if ( apiKey != null ){
            main_layout.add(apiKey,apiCode,revokeButton);
        }
        else{
            main_layout.add(generateButton);
        }
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
     * Function for setting action for revoke button
     * @param ex
     */
    private void setRevokeButton(ClickEvent ex){
        DatabaseAPI databaseAPI = new DatabaseAPI();
        ApiKey api_key = databaseAPI.getUserApiKey(DoneApplication.loggedUser.user_id);
        if ( api_key != null ){
            if ( databaseAPI.removeApiKey(api_key.api_key) == 1 ){
                DoneApplication.notificationService("Api key revoked!",1);
                main_dialog.close();
            }
        }
    }

    /**
     * Function for setting action for generate button
     * @param ex
     */
    private void setGenerateButton(ClickEvent ex){
        DatabaseAPI databaseAPI = new DatabaseAPI();
        if ( databaseAPI.createAPIKeyForLoggedUser() == 1 ){
            DoneApplication.notificationService("Api key generated!",1);
            main_dialog.close();
        }
    }
}
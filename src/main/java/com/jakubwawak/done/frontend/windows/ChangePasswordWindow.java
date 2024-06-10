/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.datamanager.UserDataManager;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Window for logging user to the app
 */
public class ChangePasswordWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    PasswordField currentPassword_field, newPassword_field, confirmPassword_field;
    Button change_button;

    /**
     * Constructor
     */
    public ChangePasswordWindow() {
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        main_dialog.addClassName("done-task-details-window-white");
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components() {
        // set components
        currentPassword_field = new PasswordField("Current password");
        currentPassword_field.setPlaceholder("old pa$$word");
        currentPassword_field.addClassName("textfield");

        newPassword_field = new PasswordField("New password");
        newPassword_field.setPlaceholder("new pa$$word");
        newPassword_field.addClassName("textfield");

        confirmPassword_field = new PasswordField("New password");
        confirmPassword_field.setPlaceholder("new pa$$word");
        confirmPassword_field.addClassName("textfield");

        change_button = new Button("Change password", this::setChange_button);
        change_button.addClassName("buttonprimary");

    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog() {
        prepare_components();
        // set layout

        main_layout.add(currentPassword_field, newPassword_field, confirmPassword_field, change_button);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius", "25px");
        main_layout.getStyle().set("background-color", backgroundStyle);
        main_layout.getStyle().set("--lumo-font-family", "Monospace");
        main_dialog.add(main_layout);
        main_dialog.setWidth(width);
        main_dialog.setHeight(height);
    }


    /**
     * UI function for changing password
     *
     * @param ex
     */
    private void setChange_button(ClickEvent ex) {
        if (!currentPassword_field.getValue().isEmpty() && !newPassword_field.getValue().isEmpty() && !confirmPassword_field.getValue().isEmpty()) {
            if (newPassword_field.getValue().equals(confirmPassword_field.getValue())) {
                // password change
                UserDataManager udm = new UserDataManager();
                udm.changePassword(currentPassword_field.getValue(), newPassword_field.getValue());
            } else {
                // password mismatch
                DoneApplication.notificationService("Password mismatch", 1);
            }
        }
        else{
            DoneApplication.notificationService("Fill all fields",1);
        }
    }
}
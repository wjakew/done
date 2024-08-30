/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.GridElement;
import com.jakubwawak.done.datamanager.UserDataManager;
import com.jakubwawak.done.frontend.windows.AdminWindow;
import com.jakubwawak.done.frontend.windows.MessageWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Object for creating user details component for UI
 */
public class DoneUserDetailsComponent extends HorizontalLayout {

    DoneUser user;

    Button lockunlock_button, resetPassword_button;

    ComboBox<GridElement> userrole_combobox;

    AdminWindow parent;

    /**
     * Constructor
     * @param user
     */
    public DoneUserDetailsComponent(DoneUser user, AdminWindow parent){
        this.user = user;
        this.parent = parent;
        addClassName("tasklayout");
        setAlignItems(Alignment.AUTO);
        setVerticalComponentAlignment(Alignment.AUTO);
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        lockunlock_button = new Button("Lock",this::setLockunlock_button);
        lockunlock_button.addClassName("buttonprimary");

        userrole_combobox = new ComboBox<>("");
        ArrayList<GridElement> roles = new ArrayList<>();
        roles.add(new GridElement("USER"));
        roles.add(new GridElement("ADMIN"));

        userrole_combobox.setItems(roles);
        userrole_combobox.setItemLabelGenerator(GridElement::getGridelement_text);
        userrole_combobox.setValue(new GridElement(user.user_role));

        userrole_combobox.addValueChangeListener(event -> {
            GridElement selected = event.getValue();
            user.user_role = selected.getGridelement_text();
            DatabaseUser du = new DatabaseUser();
            int ans = du.updateUser(user);
            if ( ans == 1){
                DoneApplication.notificationService("User role updated",1);

            }
            else{
                DoneApplication.notificationService("Failed to update user role, check log!",1);
            }
            parent.reload();
        });

        resetPassword_button = new Button("Reset password",VaadinIcon.KEY.create(),this::setResetPassword_button);
        resetPassword_button.addClassName("buttonprimary");
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");

        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        center_layout.setAlignItems(Alignment.CENTER);
        center_layout.setWidth("80%");

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        right_layout.setAlignItems(Alignment.CENTER);
        right_layout.setWidth("80%");

        Icon icon = VaadinIcon.USER.create();

        if ( user.user_active == 1 ){
            icon.getStyle().set("color","green");
        }
        else{
            icon.getStyle().set("color","red");
        }

        left_layout.add(icon);

        center_layout.add(new H6(user.user_email));

        if ( DoneApplication.loggedUser.user_email.equals(user.user_email )){
            right_layout.add(new H6("Currently logged user"));
        }
        else{
            right_layout.add(new HorizontalLayout(userrole_combobox,lockunlock_button,resetPassword_button));
        }

        add(left_layout,center_layout,right_layout);
    }

    /**
     * Function for setting lockunlock button
     * @param ex
     */
    private void setLockunlock_button(ClickEvent ex){
        if ( user.user_active == 0){
            user.user_active = 1;
            lockunlock_button.setText("Unlock");
        }
        else{
            user.user_active = 0;
            lockunlock_button.setText("Lock");
        }
        DatabaseUser du = new DatabaseUser();
        int ans = du.updateUser(user);
        if ( ans == 1 ){
            DoneApplication.notificationService("User locked status updated",1);
        }
        else{
            DoneApplication.notificationService("Failed to update user locked status, check log!",1);
        }
        parent.reload();
    }

    /**
     * Function for action reset password
     * @param ex
     */
    private void setResetPassword_button(ClickEvent ex){
        UserDataManager udm = new UserDataManager();
        String password = udm.resetPassword(user);
        MessageWindow mw = new MessageWindow("New Password","New password for user\n"+user.user_email+"\nis "+password);
        add(mw.main_dialog);
        mw.main_dialog.open();
    }

}

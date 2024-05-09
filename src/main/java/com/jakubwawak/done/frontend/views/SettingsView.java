/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.maintanance.GridElement;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.jakubwawak.done.frontend.components.InsertTaskComponent;
import com.jakubwawak.done.frontend.components.ListTaskComponent;
import com.jakubwawak.done.frontend.windows.ChangePasswordWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.ArrayList;

import static com.jakubwawak.done.DoneApplication.UI_WIDTH;

/**
 * Main application web view
 */
@PageTitle("settings to change.")
@Route("settings")
public class SettingsView extends VerticalLayout {

    HeaderComponent header;

    Button changePasswordButton;


    /**
     * Constructor
     */
    public SettingsView(){
        addClassName("taskview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new HeaderComponent();

        changePasswordButton = new Button("change password", VaadinIcon.KEY.create(),this::setChangePasswordButton);
        changePasswordButton.addClassName("buttonbig");

    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){

        if (DoneApplication.loggedUser != null){
            prepareComponents();
            add(header);
            add(changePasswordButton);
        }

        // user is not logged
        else{
            add(new H1("no one is logged! forbidden access"));
        }
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void setChangePasswordButton(ClickEvent ex){
        ChangePasswordWindow cpw = new ChangePasswordWindow();
        cpw.main_dialog.open();
    }

}
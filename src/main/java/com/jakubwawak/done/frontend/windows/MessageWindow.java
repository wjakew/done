/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.done.frontend.windows;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

/**
 * Window for logging user to the app
 */
public class MessageWindow {

    // variables for setting x and y of window
    public String width = "70%";
    public String height = "30%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    String messageTitle, messageContent;

    H6 windowTitle;
    TextArea messageField;
    Button create_button;

    /**
     * Constructor
     */
    public MessageWindow(String messageTitle, String messageContent){
        main_dialog = new Dialog();
        this.messageContent = messageContent;
        this.messageTitle = messageTitle;
        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        windowTitle = new H6(messageTitle);
        windowTitle.getStyle().set("margin","10px");
        windowTitle.getStyle().set("font-size","20px");
        windowTitle.getStyle().set("font-weight","bold");
        windowTitle.getStyle().set("font-family","donesecondary");

        messageField = new TextArea();
        messageField.setValue(messageContent);
        messageField.getStyle().set("margin","10px");
        messageField.getStyle().set("font-size","15px");
        messageField.getStyle().set("font-family","donesecondary");
        messageField.getStyle().set("border-radius","15px");
        messageField.getStyle().set("background-color","white");
        messageField.getStyle().set("color","black");
        messageField.setReadOnly(true);
        messageField.setSizeFull();

        create_button = new Button("I read that...", VaadinIcon.CHECK.create(),this::setCreate_button);
        create_button.addClassName("buttonprimary");
        create_button.setWidthFull();
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

        main_layout.add(windowTitle);
        main_layout.add(messageField);
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
     */
    private void setCreate_button(ClickEvent ex){
        main_dialog.close();
    }
}
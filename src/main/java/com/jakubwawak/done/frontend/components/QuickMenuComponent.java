/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class QuickMenuComponent extends HorizontalLayout {

    Button openDrawerButton;
    TextField commandField;

    public QuickMenuComponent(){
        super();
        prepareLayout();
        setAlignItems(Alignment.END);
        setVerticalComponentAlignment(Alignment.CENTER);

    }

    /**
     * Funciton for preparing components
     */
    void prepareComponents(){
        openDrawerButton = new Button("", VaadinIcon.TOOLS.create(),this::setOpenDrawerButton);
        openDrawerButton.addClassName("buttonprimary");

        commandField = new TextField();
        commandField.setPlaceholder("command");
        commandField.setPrefixComponent(VaadinIcon.TERMINAL.create());
        commandField.addClassName("textfield");
        commandField.setVisible(false);
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();
        add(commandField);
        add(openDrawerButton);
    }

    /**
     * Function for setting up listeners
     */
    private void setOpenDrawerButton(ClickEvent ex){
        if ( commandField.isVisible() ){
            commandField.setVisible(false);
        }
        else{
            commandField.setVisible(true);
        }
    }
}

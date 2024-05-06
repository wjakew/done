/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import static com.jakubwawak.done.DoneApplication.UI_WIDTH;

/**
 * Object for creating header component
 */
public class HeaderComponent extends HorizontalLayout {

    MenuComponent menuComponent;

    Button menuButton;

    public HeaderComponent(){
        super();
        setWidth(UI_WIDTH);
        addClassName("header");
        setAlignItems(Alignment.CENTER);
        setVerticalComponentAlignment(Alignment.CENTER);
        prepareLayout();
    }

    /**
     * Function for preparing layout in application header
     */
    void prepareLayout(){

        menuComponent = new MenuComponent();

        menuButton = new Button("", VaadinIcon.MENU.create(),this::setMenuButton);
        menuButton.addClassName("buttonprimary");

        // prepare window layout and components
        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        center_layout.add(new H4("done."));

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add(menuButton,menuComponent);

        menuComponent.setVisible(false);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.START);
        right_layout.setAlignItems(FlexComponent.Alignment.END);
        right_layout.add();
        right_layout.setWidth("80%");

        add(left_layout,center_layout,right_layout);
    }

    /**
     * Function for setting action on button
     * @param ex
     */
    private void setMenuButton(ClickEvent ex){
        if( menuComponent.isVisible() ){
            menuComponent.setVisible(false);
        }
        else{
            menuComponent.setVisible(true);
        }
    }
}

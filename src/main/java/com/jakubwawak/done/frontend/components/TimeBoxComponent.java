/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseTimeBox;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.jakubwawak.done.frontend.windows.TimeBoxCreateWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

/**
 * UI object for creating timebox list
 */
public class TimeBoxComponent extends VerticalLayout {

    // creating picker layout
    HorizontalLayout pickerLayout;
    Button addTimeBoxButton;
    ComboBox<DoneTimeBox> timeBoxPicker;
    ArrayList<DoneTimeBox> content;




    /**
     * Constructor
     */
    public TimeBoxComponent(){
        super();
        prepareLayout();
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        pickerLayout = new HorizontalLayout();
        pickerLayout.setSpacing(true);
        pickerLayout.setPadding(true);
        pickerLayout.setMargin(true);
        pickerLayout.setWidth(DoneApplication.UI_WIDTH);
        pickerLayout.setAlignItems(Alignment.CENTER);
        pickerLayout.setVerticalComponentAlignment(Alignment.CENTER);

        addTimeBoxButton = new Button("", VaadinIcon.PLUS.create(),this::setAddTimeBoxButton);
        addTimeBoxButton.addClassName("buttonprimary");

        timeBoxPicker = new ComboBox<>();
        timeBoxPicker.setPlaceholder("Select timebox");
        timeBoxPicker.addClassName("textfield");
        timeBoxPicker.setWidth("100%");
        content = new ArrayList<>();
        timeBoxPicker.setItems(content);
        timeBoxPicker.setItemLabelGenerator(DoneTimeBox::createLabel);
        refeshTimeBoxList();

        timeBoxPicker.addValueChangeListener(e->{
            DoneTimeBox seleceted = e.getValue();
            TimeBoxDetailsComponent tbdc = new TimeBoxDetailsComponent(seleceted);
            prepareLayout(tbdc);
            DoneApplication.notificationService("Reload ("+seleceted.timebox_id.toString()+")",1);
        });

    }

    /**
     * Function for refreshing timebox list
     */
    public void refeshTimeBoxList(){
        content.clear();
        DatabaseTimeBox databaseTimeBox = new DatabaseTimeBox();
        content.addAll(databaseTimeBox.getLoggedUserTimeBoxList());
        timeBoxPicker.getDataProvider().refreshAll();
        timeBoxPicker.setValue(null);
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();
        removeAll();
        pickerLayout.add(timeBoxPicker,addTimeBoxButton);
        add(pickerLayout);
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(TimeBoxDetailsComponent tbdc){
        prepareComponents();
        removeAll();
        pickerLayout.add(timeBoxPicker,addTimeBoxButton);
        add(pickerLayout);
        // ? event triggers when value set ( value changed ) timeBoxPicker.setValue(tbdc.timeBoxObject);
        add(tbdc);
    }

    /**
     * Function for setting add timebox button
     */
    private void setAddTimeBoxButton(ClickEvent ex){
        TimeBoxCreateWindow tbcw = new TimeBoxCreateWindow(this);
        add(tbcw.main_dialog);
        tbcw.main_dialog.open();
    }
}

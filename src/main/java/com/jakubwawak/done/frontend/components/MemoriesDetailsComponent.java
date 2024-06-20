/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.backend.database.DatabaseMemory;
import com.jakubwawak.done.backend.entity.DoneMemory;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.addons.componentfactory.PaperSlider;

import static com.jakubwawak.done.DoneApplication.UI_WIDTH;
import static com.jakubwawak.done.DoneApplication.main;

/**
 * Object for creating a UI for Memories
 */
public class MemoriesDetailsComponent extends VerticalLayout {


    DoneMemory doneMemory;

    PaperSlider happyState_slider, stressState_slider;

    DatabaseMemory databaseMemory;

    TextArea quote_field, note_field;

    /**
     * Constructor
     */
    public MemoriesDetailsComponent(DoneMemory doneMemory){
        this.doneMemory = doneMemory;
        databaseMemory = new DatabaseMemory();
        // empty constructor
        prepareLayout();
        addClassName("memoriesdetails");
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        happyState_slider = new PaperSlider();
        happyState_slider.setMin(0);
        happyState_slider.setMax(10);
        happyState_slider.setValue(5);
        happyState_slider.setSnaps(true);
        happyState_slider.setLabel("Happiness state");
        happyState_slider.setWidth(UI_WIDTH);

        happyState_slider.addValueChangeListener(event -> {
            doneMemory.memory_happystate = event.getValue().intValue();
            databaseMemory.updateMemory(doneMemory);
        });

        stressState_slider = new PaperSlider();
        stressState_slider.setMin(0);
        stressState_slider.setMax(10);
        stressState_slider.setValue(5);
        stressState_slider.setSnaps(true);
        stressState_slider.setLabel("Stress state");
        stressState_slider.setWidth(UI_WIDTH);

        stressState_slider.addValueChangeListener(event -> {
            doneMemory.memory_stresstate = event.getValue().intValue();
            databaseMemory.updateMemory(doneMemory);
        });

        quote_field = new TextArea("Quote");
        quote_field.setSizeFull();
        quote_field.addClassName("textfield");

        quote_field.addValueChangeListener(e->{
            doneMemory.memory_quote = e.getValue();
            databaseMemory.updateMemory(doneMemory);
        });

        note_field = new TextArea("Note");
        note_field.setSizeFull();
        note_field.addClassName("textfield");

        note_field.addValueChangeListener(e->{
            doneMemory.memory_note = e.getValue();
            databaseMemory.updateMemory(doneMemory);
        });


        // adding values from object to the components

        happyState_slider.setValue(doneMemory.memory_happystate);
        stressState_slider.setValue(doneMemory.memory_stresstate);

        quote_field.setValue(doneMemory.memory_quote);
        note_field.setValue(doneMemory.memory_note);

    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();

        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();

        leftLayout.setSizeFull();
        leftLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        leftLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        leftLayout.getStyle().set("text-align", "center");

        rightLayout.setSizeFull();
        rightLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        rightLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightLayout.getStyle().set("text-align", "center");

        leftLayout.add(stressState_slider, happyState_slider, quote_field);
        rightLayout.add(new H6(doneMemory.memory_date.toString()),note_field);

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.CENTER); mainLayout.setVerticalComponentAlignment(Alignment.CENTER);
        mainLayout.add(leftLayout,rightLayout);
        add(mainLayout);
    }


}

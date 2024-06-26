/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.jakubwawak.done.backend.entity.DoneNote;

/**
 * Object for showing note details in UI
 */
public class NoteDetailsComponent extends HorizontalLayout {

    DoneNote doneNote;

    /**
     * Constructor
     * @param doneNote
     */
    public NoteDetailsComponent(DoneNote doneNote){
        this.doneNote = doneNote;

        prepareLayout();
        addClassName("memoriesdetails");
        setAlignItems(Alignment.CENTER);
        setVerticalComponentAlignment(Alignment.CENTER);
        setWidthFull();
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(Alignment.END);
        right_layout.setWidth("80%");

        left_layout.add(new H6(doneNote.note_title));
        add(left_layout,right_layout);
    }
}

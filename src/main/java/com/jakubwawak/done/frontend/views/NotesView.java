/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneNote;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.jakubwawak.done.frontend.components.NoteEditorComponent;
import com.jakubwawak.done.frontend.components.NoteListComponent;
import com.jakubwawak.done.frontend.windows.APIWindow;
import com.jakubwawak.done.frontend.windows.ChangePasswordWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Main application web view
 */
@PageTitle("thoughts to write.")
@Route("notes")
public class NotesView extends VerticalLayout {

    HeaderComponent header;

    NoteEditorComponent nec;

    Button openListDrawerButton;

    NoteListComponent nlc;

    /**
     * Constructor
     */
    public NotesView(){
        addClassName("taskview");
        getStyle().set("margin","15px");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new HeaderComponent();
        nec = new NoteEditorComponent(new DoneNote());
        nlc = new NoteListComponent(this);
        nlc.setVisible(false);
        nlc.reload();

        openListDrawerButton = new Button("",VaadinIcon.ARROW_RIGHT.create(),this::setOpenListDrawerButton);
        openListDrawerButton.addClassName("buttonprimary");
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){

        VerticalLayout mainLayout = new VerticalLayout();

        mainLayout.setWidth("100%");mainLayout.setHeight("90%");
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        mainLayout.getStyle().set("text-align", "center");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        if (DoneApplication.loggedUser != null){
            prepareComponents();
            add(header);

            horizontalLayout.add(nlc,openListDrawerButton,nec);

            mainLayout.add(horizontalLayout);

            add(mainLayout);
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

    /**
     * Function for reloading editor
     * @param note
     */
    public void reloadEditor(DoneNote note){
        nec = new NoteEditorComponent(note);
        removeAll();
        prepareLayout();
    }

    /**
     * Function for setting open list drawer button
     * @param ex
     */
    public void setOpenListDrawerButton(ClickEvent ex){
        if ( nlc.isVisible() ){
            nlc.setVisible(false);
            openListDrawerButton.setIcon(VaadinIcon.ARROW_RIGHT.create());
        }
        else{
            nlc.setVisible(true);
            openListDrawerButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        }
    }

}
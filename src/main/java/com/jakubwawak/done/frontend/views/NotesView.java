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
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
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

    public NoteListComponent nlc;

    VerticalLayout editorLayout;

    VerticalLayout listLayout;

    Button addNotebutton, deleteButton;

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

        editorLayout = new VerticalLayout();

        editorLayout.setSizeFull();
        editorLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        editorLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        editorLayout.getStyle().set("text-align", "center");

        listLayout = new VerticalLayout();

        listLayout.setWidth("30%");listLayout.setHeight("100%");
        listLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        listLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        listLayout.getStyle().set("text-align", "center");

        addNotebutton = new Button("Add",VaadinIcon.PLUS.create(),this::setAddNoteButton);
        addNotebutton.addClassName("buttonprimary");

        deleteButton = new Button("Delete",VaadinIcon.TRASH.create());
        deleteButton.addClassName("buttonprimary");
        deleteButton.getStyle().set("color","red");


        header = new HeaderComponent();
        nec = new NoteEditorComponent(new DoneNote(),this);
        nlc = new NoteListComponent(this);
        nlc.setSizeFull();
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

            HorizontalLayout buttonLayout = new HorizontalLayout(addNotebutton,deleteButton);

            listLayout.add(buttonLayout,nlc);
            listLayout.setVisible(false);
            editorLayout.add(nec);


            horizontalLayout.add(listLayout,openListDrawerButton,editorLayout);

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
     * Function for setting save button
     * @param ex
     */
    private void setAddNoteButton(ClickEvent ex){
        DoneNote new_note = new DoneNote();
        reloadEditor(new DoneNote());
    }

    /**
     * Function for reloading editor
     * @param note
     */
    public void reloadEditor(DoneNote note){
        nec = new NoteEditorComponent(note,this);
        editorLayout.removeAll();
        editorLayout.add(nec);
    }

    /**
     * Function for setting open list drawer button
     * @param ex
     */
    public void setOpenListDrawerButton(ClickEvent ex){
        if ( listLayout.isVisible() ){
            listLayout.setVisible(false);
            openListDrawerButton.setIcon(VaadinIcon.ARROW_RIGHT.create());
        }
        else{
            listLayout.setVisible(true);
            openListDrawerButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        }
    }

}
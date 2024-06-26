/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseNote;
import com.jakubwawak.done.backend.entity.DoneNote;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.frontend.views.NotesView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.List;

/**
 * UI object for creating task list
 */
public class NoteListComponent extends VirtualList {

    public List<DoneNote> content;
    DatabaseNote databaseNote;

    NotesView parent;
    public DoneNote selected;

    public NoteListComponent(NotesView parent){
        super();
        this.parent = parent;
        addClassName("listtask-border");
        databaseNote = new DatabaseNote();
        content = databaseNote.loadAllLoggedUserNotes();
        selected = null;
        setItems(content);
        setRenderer(donetaskRenderer);
        setWidth("30%");
        setHeight("100%");
    }

    private ComponentRenderer<Component, DoneNote> donetaskRenderer = new ComponentRenderer<>(note ->{
        NoteDetailsComponent ndc = new NoteDetailsComponent(note);
        ndc.addClickListener(e->{
            parent.reloadEditor(note);
            DoneApplication.notificationService("Reloaded: "+note.note_title,1);
            selected = note;
        });
        return ndc;
    });

    /**
     * Function for reloading data
     * reloadMode:
     * 0 - reloads all data
     * 1 - only NEW
     * 2 - only IN PROGRESS
     * 3 - only DONE
     * 4 - only CURRENT ( without DONE )
     */
    public void reload(){
        content.clear();
        content.addAll(databaseNote.loadAllLoggedUserNotes());
        getDataProvider().refreshAll();
    }
}

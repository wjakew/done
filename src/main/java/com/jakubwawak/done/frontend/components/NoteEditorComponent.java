/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseNote;
import com.jakubwawak.done.backend.entity.DoneNote;
import com.jakubwawak.done.frontend.views.NotesView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * Component for note editing in application
 */
public class NoteEditorComponent extends VerticalLayout {

    DoneNote note;

    HorizontalLayout titleHeader;

    TextField titleField;
    Button saveButton;

    TextArea noteField;

    HorizontalLayout toolboxHeader;
    Button previewButton;



    VerticalLayout left,right;

    NotesView parent;

    /**
     * Constructor
     */
    public NoteEditorComponent(DoneNote doneNote, NotesView parent){
        this.note = doneNote;
        this.parent = parent;
        addClassName("noteeditor");
        prepareLayout();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        // title header loader
        titleHeader = new HorizontalLayout();
        titleHeader.setWidth("100%");
        titleHeader.setJustifyContentMode(JustifyContentMode.CENTER);
        titleHeader.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        titleField = new TextField();
        titleField.setWidthFull();
        titleField.setValue(note.note_title);
        titleField.addClassName("textfield");
        titleField.setPlaceholder("note title");
        titleField.setPrefixComponent(VaadinIcon.PENCIL.create());

        saveButton = new Button("save", VaadinIcon.CHECK.create(),this::setSaveButton);
        saveButton.addClassName("buttonprimary");
        saveButton.setWidth("30%");

        titleHeader.add(new H6("note"),titleField,saveButton);

        // toolbox header loader
        toolboxHeader = new HorizontalLayout();
        toolboxHeader.setWidth("100%");
        toolboxHeader.setJustifyContentMode(JustifyContentMode.START);
        toolboxHeader.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        previewButton = new Button("preview", VaadinIcon.EYE.create(),this::setPreviewButton);
        previewButton.addClassName("buttonprimary");


        toolboxHeader.add(new H6("tools"),previewButton);


        noteField = new TextArea();
        noteField.setSizeFull();
        noteField.setValue(note.note_raw);
        noteField.addClassName("textfield");

        noteField.addValueChangeListener(e->{
            note.note_raw = noteField.getValue();
            reloadPreview();
        });
    }

    /**
     * Function for reloading preview
     */
    void reloadPreview(){
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(note.note_raw);
        String value = "<body><div>"+renderer.render(document)+"</div></body>";
        Html htmlPreview = new Html(value);
        htmlPreview.getStyle().set("width","100%"); htmlPreview.getStyle().set("height","100%");
        htmlPreview.getStyle().set("text-algin","left");
        right.removeAll();
        right.add(htmlPreview);
    }


    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();

        add(titleHeader,toolboxHeader);

        HorizontalLayout hl = new HorizontalLayout();

        hl.setSizeFull(); hl.setJustifyContentMode(JustifyContentMode.CENTER); hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        left = new VerticalLayout();
        left.setSizeFull();
        left.setJustifyContentMode(JustifyContentMode.CENTER);
        left.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        left.getStyle().set("text-align", "center");

        right = new VerticalLayout();
        right.setVisible(false);
        right.setSizeFull();
        right.setJustifyContentMode(JustifyContentMode.CENTER);
        right.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        right.getStyle().set("text-align", "center");

        left.add(noteField);
        reloadPreview();

        hl.add(left,right);

        add(hl);
    }

    /**
     * Function for setting save button
     * @param ex
     */
    private void setSaveButton(ClickEvent ex){
        note.note_title = titleField.getValue();
        note.note_raw = noteField.getValue();

        if ( note.note_title.equals("") ){
            DoneApplication.notificationService("note title cannot be empty",2);
            return;
        }
        if ( note.note_raw.equals("") ){
            DoneApplication.notificationService("note content cannot be empty",2);
            return;
        }

        DatabaseNote dn = new DatabaseNote();
        if ( note.note_id == null ){
            int ans = dn.insertNote(note);
            if ( ans == 1 ){
                DoneApplication.notificationService("note saved",1);
                parent.nlc.reload();
            }
            else {
                DoneApplication.notificationService("failed to save note", 2);
            }
        }
        else{
            int ans = dn.updateNote(note);
            if ( ans == 1 ){
                DoneApplication.notificationService("note updated",1);
                parent.nlc.reload();
            }
            else{
                DoneApplication.notificationService("failed to update note",2);
            }
        }

    }

    /**
     * Function for setting preview button
     * @param ex
     */
    private void setPreviewButton(ClickEvent ex){
        if ( right.isVisible() ){
            right.setVisible(false);
        }
        else{
            right.setVisible(true);
            reloadPreview();
        }
    }
}

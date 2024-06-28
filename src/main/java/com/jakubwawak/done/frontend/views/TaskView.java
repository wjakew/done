/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.maintanance.GridElement;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.jakubwawak.done.frontend.components.InsertTaskComponent;
import com.jakubwawak.done.frontend.components.ListTaskComponent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.ArrayList;

import static com.jakubwawak.done.DoneApplication.UI_WIDTH;

/**
 * Main application web view
 */
@PageTitle("task to be done.")
@Route("tasks")
public class TaskView extends VerticalLayout {

    HeaderComponent header;
    InsertTaskComponent insertTaskComponent;

    ComboBox<GridElement> filterCombobox;

    /**
     * Constructor
     */
    public TaskView(){
        addClassName("taskview");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new HeaderComponent();
        insertTaskComponent = new InsertTaskComponent(null);
        DoneApplication.ltc = new ListTaskComponent(0);

        filterCombobox = new ComboBox<>("Filters");

        ArrayList<GridElement> filters = new ArrayList<>();
        filters.add(new GridElement("All Tasks"));
        filters.add(new GridElement("Only New"));
        filters.add(new GridElement("Only In Progress"));
        filters.add(new GridElement("Only Done"));
        filters.add(new GridElement("Only Current"));

        filterCombobox.setItems(filters);
        filterCombobox.setWidth(DoneApplication.UI_WIDTH);
        filterCombobox.setPrefixComponent(VaadinIcon.TOOLS.create());
        filterCombobox.setItemLabelGenerator(GridElement::getGridelement_text);
        filterCombobox.addClassName("textfield");
        filterCombobox.addValueChangeListener(e->{
            GridElement selected = e.getValue();
            int index = filters.indexOf(selected);
            DoneApplication.ltc.reloadMode = index;
            DoneApplication.ltc.reload();
        });
        filterCombobox.setValue(filters.get(0));
        filterCombobox.setAllowCustomValue(false);
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){

        if (DoneApplication.loggedUser != null){
            prepareComponents();
            add(header);
            add(insertTaskComponent);
            add(filterCombobox);
            add(DoneApplication.ltc);
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

}
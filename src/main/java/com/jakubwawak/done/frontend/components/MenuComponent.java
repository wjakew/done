/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.database.DatabaseTimeBox;
import com.jakubwawak.done.frontend.views.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.html.Span;

import javax.xml.validation.Validator;

/**
 * Object for creating application menu
 */
public class MenuComponent extends SideNav {

    SideNavItem dashboardItem,chronicleItem,taskItem, settingsItem, timeboxItem, memoryItem, noteItem, logoutItem;

    /**
     * Constructor
     */
    public MenuComponent(){
        super();
        createItems();
        addItem(dashboardItem,chronicleItem,taskItem,timeboxItem,memoryItem,noteItem,settingsItem,logoutItem);
    }

    /**
     * Function for creating items
     */
    void createItems(){
        dashboardItem = new SideNavItem("Dashboard", DashboardView.class, VaadinIcon.CHART.create());
        dashboardItem.addClassName("buttonprimary");

        chronicleItem = new SideNavItem("Chronicle", ChronicleView.class, VaadinIcon.ARCHIVE.create());
        chronicleItem.addClassName("buttonprimary");

        taskItem = new SideNavItem("My Tasks", TaskView.class, VaadinIcon.TASKS.create());
        taskItem.addClassName("buttonprimary");

        DatabaseTask dt = new DatabaseTask();
        Span taskCounter = new Span(Integer.toString(dt.getNewInProgressTaskCount()));
        taskCounter.getElement().getThemeList().add("badge contrast pill");
        taskCounter.getElement().setAttribute("aria-label", Integer.toString(dt.getNewInProgressTaskCount())+" new or in progress tasks");
        taskItem.setSuffixComponent(taskCounter);

        timeboxItem = new SideNavItem("My TimeBox", TimeBoxView.class, VaadinIcon.COMBOBOX.create());
        timeboxItem.addClassName("buttonprimary");

        DatabaseTimeBox dtb = new DatabaseTimeBox();
        Span timeBoxCounter = new Span(Integer.toString(dtb.getUserTimeBoxCount()));
        timeBoxCounter.getElement().getThemeList().add("badge contrast pill");
        timeBoxCounter.getElement().setAttribute("aria-label", Integer.toString(dtb.getUserTimeBoxCount())+" timeboxes");
        timeboxItem.setSuffixComponent(timeBoxCounter);

        memoryItem = new SideNavItem("Memories", MemoryView.class, VaadinIcon.BOOK.create());
        memoryItem.addClassName("buttonprimary");

        noteItem = new SideNavItem("Notes", NotesView.class, VaadinIcon.NOTEBOOK.create());
        noteItem.addClassName("buttonprimary");


        settingsItem = new SideNavItem("Settings", SettingsView.class, VaadinIcon.BOOKMARK.create());
        settingsItem.addClassName("buttonprimary");
        logoutItem =new SideNavItem("Log out!", LoginView.class,VaadinIcon.EXIT.create());
        logoutItem.addClassName("buttonprimary");
    }
}

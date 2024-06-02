/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.components;

import com.jakubwawak.done.frontend.views.LoginView;
import com.jakubwawak.done.frontend.views.SettingsView;
import com.jakubwawak.done.frontend.views.TaskView;
import com.jakubwawak.done.frontend.views.TimeBoxView;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

import javax.xml.validation.Validator;

/**
 * Object for creating application menu
 */
public class MenuComponent extends SideNav {

    SideNavItem taskItem, settingsItem, timeboxItem, logoutItem;

    /**
     * Constructor
     */
    public MenuComponent(){
        super();
        createItems();
        addItem(taskItem,timeboxItem,settingsItem,logoutItem);
    }

    /**
     * Function for creating items
     */
    void createItems(){
        taskItem = new SideNavItem("My Tasks", TaskView.class, VaadinIcon.TASKS.create());
        taskItem.addClassName("buttonprimary");
        timeboxItem = new SideNavItem("My TimeBox", TimeBoxView.class, VaadinIcon.COMBOBOX.create());
        timeboxItem.addClassName("buttonprimary");
        settingsItem = new SideNavItem("Settings", SettingsView.class, VaadinIcon.BOOKMARK.create());
        settingsItem.addClassName("buttonprimary");
        logoutItem =new SideNavItem("Log out!", LoginView.class,VaadinIcon.EXIT.create());
        logoutItem.addClassName("buttonprimary");
    }
}

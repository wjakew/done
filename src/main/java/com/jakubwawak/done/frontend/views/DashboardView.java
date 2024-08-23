/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/
package com.jakubwawak.done.frontend.views;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseMemory;
import com.jakubwawak.done.backend.database.DatabaseUserStats;
import com.jakubwawak.done.frontend.components.HeaderComponent;
import com.jakubwawak.done.frontend.components.charts.TasksPieChart;
import com.jakubwawak.done.frontend.windows.APIWindow;
import com.jakubwawak.done.frontend.windows.ChangePasswordWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

/**
 * Main application web view
 */
@PageTitle("set perspective.")
@Route("dashboard")
public class DashboardView extends VerticalLayout {

    HeaderComponent header;

    VerticalLayout main_layout;

    DatabaseUserStats databaseUserStats;

    DatabaseMemory databaseMemory;

    TasksPieChart tpc;

    /**
     * Constructor
     */
    public DashboardView(){
        databaseUserStats = new DatabaseUserStats();
        databaseMemory = new DatabaseMemory();
        addClassName("taskview");
        getStyle().set("margin","15px");
        getStyle().set("overflow","hidden");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new HeaderComponent();

        main_layout = new VerticalLayout();
        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        tpc = new TasksPieChart();
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        if ( DoneApplication.loggedUser != null ){
            prepareComponents();

            ArrayList<Double> data = databaseUserStats.calculateTasks();
            ArrayList<Double> moodData = databaseMemory.calculateUserMood();

            HorizontalLayout stats = new HorizontalLayout(
                    new H6("tasks:"),new H1(Double.toString(data.get(0)).split("\\.")[0])
                    ,new H6("timeboxes:"),new H1(Integer.toString(databaseUserStats.calculateTimeBox()))
                    ,new H6("memories"),new H1(Integer.toString(databaseUserStats.calculateMemories()))
                    ,new H6("notes:"),new H1(Integer.toString(databaseUserStats.calculateNote())));

            main_layout.add(new H6("current mood"),new H1(moodData.get(0)+"/"+moodData.get(1)),stats);

            add(header);
            add(main_layout);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }
        else{
            add(new H1("no one is logged! forbidden access"));
        }
    }
}
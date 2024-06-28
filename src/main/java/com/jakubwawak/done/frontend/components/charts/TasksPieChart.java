/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.frontend.components.charts;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.jakubwawak.done.backend.database.DatabaseUserStats;

import java.util.ArrayList;

/**
 * Object for creating tasks pie chart of user tasks statistics
 */
public class TasksPieChart extends ApexChartsBuilder {

    DatabaseUserStats databaseUserStats;

    public TasksPieChart() {
        databaseUserStats = new DatabaseUserStats();
        ArrayList<Double> data = databaseUserStats.calculateTasks(); // all / new / in progress / done
        withChart(ChartBuilder.get().withType(Type.PIE).build())
                .withLabels("New", "In progress", "Done")
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.RIGHT)
                        .build())
                .withSeries(data.get(1), data.get(2), data.get(3))
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(LegendBuilder.get()
                                        .withPosition(Position.BOTTOM)
                                        .build())
                                .build())
                        .build());
    }
}
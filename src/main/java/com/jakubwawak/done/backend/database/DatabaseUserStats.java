/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTask;

import java.util.ArrayList;

/**
 * Object for creating database user stats
 */
public class DatabaseUserStats {

    Database database;

    DatabaseTask databaseTask;

    DatabaseTimeBox databaseTimeBox;

    DatabaseMemory databaseMemory;

    DatabaseNote databaseNote;

    /**
     * Constructor
     */
    public DatabaseUserStats (){
        this.database = DoneApplication.database;
        this.databaseTask = new DatabaseTask();
        this.databaseTimeBox = new DatabaseTimeBox();
        this.databaseMemory = new DatabaseMemory();
        this.databaseNote = new DatabaseNote();
    }

    /**
     * Function for caclulating task stats
     * @return Integer
     * all / new / in progress / done
     */
    public ArrayList<Double> calculateTasks(){
        ArrayList<Double> data = new ArrayList<>();
        data.add((double) databaseTask.loadAllTasks().size()); // all tasks
        int new_tasks = 0; int in_progress = 0; int done = 0;

        for(DoneTask task : databaseTask.loadAllTasks()){
            if ( task.task_status.equals("NEW") ){
                new_tasks++;
            }
            else if ( task.task_status.equals("IN PROGRESS") ){
                in_progress++;
            }
            else if ( task.task_status.equals("DONE") ){
                done++;
            }
        }
        data.add((double) new_tasks);
        data.add((double) in_progress);
        data.add((double) done);
        return data;
    }

    /**
     * Function for calculating timebox
     * @return
     */
    public int calculateTimeBox(){
        return databaseTimeBox.getUserTimeBoxCount();
    }

    /**
     * Function for calculating memories
     * @return
     */
    public int calculateMemories(){
        return databaseMemory.getCollectionOfMemoriesLoggedUser().size();
    }

    /**
     * Function for calculating notes
     * @return
     */
    public int calculateNote(){
        return databaseNote.loadAllLoggedUserNotes().size();
    }


}

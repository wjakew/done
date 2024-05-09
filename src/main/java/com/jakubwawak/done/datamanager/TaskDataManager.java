/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.Database;
import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.entity.DoneTask;

/**
 * Function for managing task data on database
 */
public class TaskDataManager {

    DatabaseTask databaseTask;

    /**
     * Constructor
     */
    public TaskDataManager(){
        this.databaseTask = new DatabaseTask();
    }

    /**
     * Function for inserting task to database
     * @param task_name
     * @return Integer
     */
    public int insertTask(String task_name){
        DoneTask doneTask = new DoneTask(task_name);
        if ( doneTask != null ){
            int ans = databaseTask.insertTask(doneTask);
            if (ans == 1){
                DoneApplication.notificationService("Added new task: "+task_name,1);
                return 1;
            }
            else{
                DoneApplication.notificationService("Something went wrong, check database log!",2);
            }
        }
        DoneApplication.notificationService("Task returned null, nothing to add",1);
        return -1;
    }

    /**
     * Function for inserting task to database
     * @param task
     * @return Integer
     */
    public int insertTask(DoneTask task){
        DoneTask doneTask = task;
        if ( doneTask != null ){
            int ans = databaseTask.insertTask(doneTask);
            if (ans == 1){
                DoneApplication.notificationService("Added new task: "+doneTask.task_name,1);
                return 1;
            }
            else{
                DoneApplication.notificationService("Something went wrong, check database log!",2);
            }
        }
        DoneApplication.notificationService("Task returned null, nothing to add",1);
        return -1;
    }

    /**
     * Function for updating task
     * @param taskToUpgrade
     * @return Integer
     */
    public int upgradeTask(DoneTask taskToUpgrade){
        String info = "";
        DoneTask previousState = databaseTask.getTaskById(taskToUpgrade.task_id);
        if ( previousState != null ){
            info = taskToUpgrade.compare(previousState);
        }
        boolean updateFlag = databaseTask.updateTask(taskToUpgrade);
        if ( updateFlag ){
            DoneApplication.notificationService("Task data updated ("+taskToUpgrade.task_id.toString()+"), changes ("+info+")"
                    ,1);
            return 1;
        }
        else{
            DoneApplication.notificationService("Failed to update task, check application log",2);
            return -1;
        }
    }

    /**
     * Function for deleting task
     * @param toDelete
     * @return Integer
     */
    public int deleteTask(DoneTask toDelete){
        if ( databaseTask.deleteTask(toDelete) == 1 ){
            DoneApplication.notificationService("Task deleted ("+toDelete.task_id.toString()+")",1);
            DoneApplication.ltc.reload();
            return 1;
        }
        else{
            DoneApplication.notificationService("Failed to delete task, check application log",2);
            return -1;
        }
    }
}

/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done;

import com.jakubwawak.done.backend.database.DatabaseMemory;
import com.jakubwawak.done.backend.entity.DoneMemory;
import com.jakubwawak.done.datamanager.UserDataManager;

/**
 * Object for creating tests
 */
public class DoneTest {

    /**
     * Constructor
     */
    public DoneTest(){
        System.out.println("Running tests...");
        run();
    }

    /**
     * Function for running tests
     */
    void run(){
        UserDataManager udm = new UserDataManager();
        udm.loginUser("kubawawak@gmail.com","Minidysk7+");
        DatabaseMemory dm = new DatabaseMemory();

        DoneMemory doneMemory = new DoneMemory();

        dm.createMemory(doneMemory);
    }
}

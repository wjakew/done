/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;
import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneNote;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Object for managing notes in the database
 */
public class DatabaseNote {

    Database database;

    /**
     * Constructor
     */
    public DatabaseNote(){
        this.database = DoneApplication.database;
    }

    /**
     * Function for inserting note to database
     * @param doneNote
     * @return Integer
     */
    public int insertNote(DoneNote doneNote){
        try{
            MongoCollection<Document> noteCollection = database.get_data_collection("done_note");
            if (doneNote != null){
                noteCollection.insertOne(doneNote.prepareDocument());
                DoneApplication.database.log("DB-NOTE-INSERT","Inserted new note for user ("+doneNote.user_id.toString()+")");
                return 1;
            }
            else{
                DoneApplication.database.log("DB-NOTE-INSERT-NULL","Failed to insert new note for user (NULL)");
                return 0;
            }
        }catch(Exception ex){
            DoneApplication.database.log("DB-NOTE-INSERT-EXCEPTION",""+ex.toString());
            return -1;
        }
    }
}

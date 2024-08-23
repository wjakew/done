/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;
import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneNote;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Objects;

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
                InsertOneResult result = noteCollection.insertOne(doneNote.prepareDocument());
                if ( result.wasAcknowledged() ){
                    DoneApplication.database.log("DB-NOTE-INSERT","Inserted new note for user ("+doneNote.user_id.toString()+")");
                    DoneApplication.databaseHistory.addHistoryEntry("note","Created new note "+doneNote.note_title,"CREATE",result.getInsertedId().asObjectId().getValue(),doneNote.user_id);
                    doneNote.note_id = Objects.requireNonNull(result.getInsertedId()).asObjectId().getValue();
                    return 1;
                }
                else{
                    DoneApplication.database.log("DB-NOTE-INSERT-FAILED","Failed to insert new note for user ("+doneNote.user_id.toString()+")");
                    return 0;
                }
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

    /**
     * Function for updating note on database
     * @param doneNote
     * @return
     */
    public int updateNote(DoneNote doneNote){
        try{
            MongoCollection<Document> noteCollection = database.get_data_collection("done_note");
            if (doneNote != null){
                Document query = new Document("_id",doneNote.note_id);
                Document update = new Document("note_title",doneNote.note_title)
                        .append("note_raw",doneNote.note_raw)
                        .append("note_creationtime",doneNote.note_creationtime)
                        .append("user_id",doneNote.user_id)
                        .append("note_connectedObjects",doneNote.note_connectedObjects);
                UpdateResult result = noteCollection.updateOne(query,new Document("$set",update));
                if ( result.wasAcknowledged() ){
                    DoneApplication.database.log("DB-NOTE-UPDATE","Updated note for user ("+doneNote.user_id.toString()+")");
                    return 1;
                }
                else{
                    DoneApplication.database.log("DB-NOTE-UPDATE-FAILED","Failed to update note for user ("+doneNote.user_id.toString()+")");
                    return 0;
                }
            }
            else{
                DoneApplication.database.log("DB-NOTE-UPDATE-NULL","Failed to update note for user (NULL)");
                return 0;
            }
        }catch(Exception ex){
            DoneApplication.database.log("DB-NOTE-UPDATE-EXCEPTION","Failed to update note for user"+ex.toString());
            return -1;
        }
    }

    /**
     * Function for removing note from database
     * @param toRemove
     * @return Integer
     */
    public int removeNote(DoneNote toRemove){
        try{
            MongoCollection<Document> noteCollection = database.get_data_collection("done_note");
            if (toRemove != null){
                Document query = new Document("_id",toRemove.note_id);
                noteCollection.deleteOne(query);
                DoneApplication.database.log("DB-NOTE-REMOVE","Removed note for user ("+toRemove.user_id.toString()+")");
                return 1;
            }
            else{
                DoneApplication.database.log("DB-NOTE-REMOVE-NULL","Failed to remove note for user (NULL)");
                return 0;
            }
        }catch(Exception ex){
            DoneApplication.database.log("DB-NOTE-REMOVE-EXCEPTION","Failed to remove note for user"+ex.toString());
            return -1;
        }
    }

    /**
     * Function for loading all logged user notes
     * @return
     */
    public ArrayList<DoneNote> loadAllLoggedUserNotes(){
        ArrayList<DoneNote> data = new ArrayList<>();
        try{
            MongoCollection<Document> noteCollection = database.get_data_collection("done_note");
            for (Document doc : noteCollection.find()){
                if ( doc.getObjectId("user_id").equals(DoneApplication.loggedUser.user_id) ){
                    DoneNote dn = new DoneNote(doc);
                    data.add(dn);
                }
            }
            DoneApplication.database.log("DB-NOTE-LOADALL","Loaded all notes for user ("+DoneApplication.loggedUser.user_id.toString()+"), count: "+data.size());
        }catch(Exception ex){
            DoneApplication.database.log("DB-NOTE-LOADALL-EXCEPTION","Failed to load all user notes ("+ex.toString()+")");
        }
        return data;
    }
}

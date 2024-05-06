/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.Password_Validator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

/**
 * Object for creating / maintaining user data on database
 */
public class DatabaseUser {

    Database database;

    /**
     * Constructor
     */
    public DatabaseUser(){
        this.database = DoneApplication.database;
    }

    /**
     * Function for creating user entry on database
     * @param userToAdd
     * @return Integer
     */
    public int createUser(DoneUser userToAdd){
        try{
            MongoCollection<Document> user_collection = database.get_data_collection("journal_user");
            if ( userToAdd != null ){
                InsertOneResult result = user_collection.insertOne(userToAdd.prepareDocument());
                if ( result.wasAcknowledged() ){
                    database.log("DB-JUSER-INSERT","New user ("+userToAdd.user_email+") added!");
                    return 1;
                }
                else{
                    database.log("DB-JUSER-INSERT-BLANK","No insert was acknowledged on database ("+userToAdd.user_email+")");
                    return 0;
                }
            }
            database.log("DB-JUSER-INSERT-EMPTY","Empty object given - nothing to insert");
            return 2;

        }catch(Exception ex){
            database.log("DB-JUSER-INSERT-FAILED","Failed to insert user on database ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for logging user to the app
     * @param userEmail
     * @param userPassword
     * @return DoneUser
     */
    public DoneUser loginUser(String userEmail, String userPassword){
        try{
            Password_Validator pv = new Password_Validator(userPassword);
            MongoCollection<Document> user_collection = database.get_data_collection("journal_user");
            Document user_document = user_collection.find(new Document("user_email",userEmail)).first();
            if ( user_document != null ){
                DoneUser user = new DoneUser(user_document);
                if ( user.user_password.equals(pv.hash()) ){
                    database.log("DB-JUSER-LOGIN","Found user ("+userEmail+") logging in!");
                    return user;
                }
                else{
                    database.log("DB-JUSER-LOGIN-FAILED","Failed to login user ("+userEmail+") wrong password");
                }
            }
            else{
                database.log("DB-JUSER-LOGIN-NOTFOUND","Cannot found user with ("+userEmail+")");
            }
            return null;
        }catch(Exception ex){
            database.log("DB-JUSER-LOGIN-FAILED","Failed to login user ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for getting user by given email
     * @param userEmail
     * @return DoneUser
     */
    public DoneUser getUserByEmail(String userEmail){
        try{
            MongoCollection<Document> user_collection = database.get_data_collection("journal_user");
            Document user_document = user_collection.find(new Document("user_email",userEmail)).first();
            if ( user_document != null ){
                DoneUser user = new DoneUser(user_document);
                database.log("DB-JUSER-GET","Found user ("+user.user_email+"/"+user.user_id.toString()+")");
                return user;
            }
            else{
                database.log("DB-JUSER-GET-NOTFOUND","Cannot found user with ("+userEmail+")");
                return null;
            }
        }catch(Exception ex){
            database.log("DB-JUSER-GET-FAILED","Failed to get user on database ("+ex.toString()+")");
            return null;
        }
    }
}
/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.Password_Validator;
import com.jakubwawak.done.backend.maintanance.RandomWordGeneratorEngine;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

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
            MongoCollection<Document> user_collection = database.get_data_collection("done_user");
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
            MongoCollection<Document> user_collection = database.get_data_collection("done_user");
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
            MongoCollection<Document> user_collection = database.get_data_collection("done_user");
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

    /**
     * Function for getting user by given id
     * @param user_id
     * @return DoneUser
     */
    public DoneUser getUserByID(ObjectId user_id){
        try{
            MongoCollection<Document> user_collection = database.get_data_collection("done_user");
            Document user_document = user_collection.find(new Document("_id",user_id)).first();
            if ( user_document != null ){
                DoneUser user = new DoneUser(user_document);
                database.log("DB-JUSER-GET","Found user ("+user.user_email+"/"+user.user_id.toString()+")");
                return user;
            }
            else{
                database.log("DB-JUSER-GET-NOTFOUND","Cannot found user with ("+user_id.toString()+")");
                return null;
            }
        }catch(Exception ex){
            database.log("DB-JUSER-GET-FAILED","Failed to get user on database ("+ex.toString()+")");
            return null;
        }
    }

        /**
        * Function for changing the password of the currently logged in user
        * @param newPassword The new password
        * @return boolean Returns true if the password was successfully changed, false otherwise
        */
        public boolean changePassword(String newPassword) {
        try {
            // Check if a user is currently logged in
            if (DoneApplication.loggedUser != null) {
                // Create a Password_Validator object for the new password
                Password_Validator pv = new Password_Validator(newPassword);

                // Get the collection
                MongoCollection<Document> user_collection = database.get_data_collection("done_user");

                // Create a new Document with the updated password
                Document updatedDocument = new Document("user_password", pv.hash());

                // Update the document in the collection
                user_collection.updateOne(Filters.eq("user_email", DoneApplication.loggedUser.user_email), new Document("$set", updatedDocument));

                // Update the password of the currently logged in user
                DoneApplication.loggedUser.user_password = pv.hash();

                // Log the successful password change
                database.log("DB-JUSER-PASSWORD-CHANGE", "Password changed for user (" + DoneApplication.loggedUser.user_email + ")");

                return true;
            } else {
                // Log the failed password change due to no user being logged in
                database.log("DB-JUSER-PASSWORD-CHANGE-FAILED", "No user is currently logged in");
                return false;
            }
        } catch (Exception e) {
            // Log the error
            database.log("DB-JUSER-PASSWORD-CHANGE-FAILED", "Failed to change password (" + e.toString() + ")");
            return false;
        }
        }

        /**
         * Function for changing the password of a user with a given email
         * @param userEmail The email of the user
         * @return boolean Returns true if the password was successfully changed, false otherwise
         */
        public boolean adminChangePassword(String userEmail) {
            try {
                RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
                String newPassword = rwge.generateRandomString(20,true,true);
                // Get the user with the given email
                DoneUser user = getUserByEmail(userEmail);

                // Check if a user with the given email exists
                if (user != null) {
                    // Create a Password_Validator object for the new password
                    Password_Validator pv = new Password_Validator(newPassword);

                    // Get the collection
                    MongoCollection<Document> user_collection = database.get_data_collection("done_user");

                    // Create a new Document with the updated password
                    Document updatedDocument = new Document("user_password", pv.hash());

                    // Update the document in the collection
                    user_collection.updateOne(Filters.eq("user_email", userEmail), new Document("$set", updatedDocument));

                    // Log the successful password change
                    database.log("DB-JUSER-PASSWORD-CHANGE", "Password changed for user (" + userEmail + ")");

                    return true;
                } else {
                    // Log the failed password change due to no user being found with the given email
                    database.log("DB-JUSER-PASSWORD-CHANGE-FAILED", "No user found with the email (" + userEmail + ")");
                    return false;
                }
            } catch (Exception e) {
                // Log the error
                database.log("DB-JUSER-PASSWORD-CHANGE-FAILED", "Failed to change password (" + e.toString() + ")");
                return false;
            }
        }


}
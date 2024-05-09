/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;


import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.Password_Validator;
import com.jakubwawak.done.backend.maintanance.RandomWordGeneratorEngine;

/**
 * Object for creating actions on user
 */
public class UserDataManager {

    DatabaseUser databaseUser;

    /**
     * Constructor
     */
    public UserDataManager(){
        databaseUser = new DatabaseUser();
    }
    /**
     * Function for registering user
     * @param userEmail
     * @param userPassword
     * @param userTelephone
     * @return DoneUser
     */
    public int registerUser(String userEmail, String userPassword, String userTelephone){
        try{
            DoneUser newUser = new DoneUser();
            RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
            Password_Validator pv = new Password_Validator(userPassword);
            if ( databaseUser.getUserByEmail(userEmail) == null ){
                newUser.user_email = userEmail;
                newUser.user_password = pv.hash();
                newUser.user_recoverypass = rwge.generateRandomString(30,false,false);
                newUser.user_settingstring = "blank";
                newUser.user_telephone = userTelephone;
                newUser.user_wallpaperURL = "blank";
                newUser.user_iconURL = "blank";
                int ans = databaseUser.createUser(newUser);
                if ( ans == 1 ){
                    DoneApplication.database.log("REGISTER-USER-CREATED","New user created ("+newUser.user_email+")");
                    return 1;
                }
                else{
                    DoneApplication.database.log("REGISTER-USER-FAILED","Database error during registration, check db log!");
                    return -2;
                }
            }
            else{
                DoneApplication.database.log("REGISTER-USER-EMPTY","User with given email exists already!");
                return 0;
            }
        }catch(Exception ex){
            DoneApplication.database.log("REGISTER-USER-FAILED","Failed to register user ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for logging user to the application
     * @param userEmail
     * @param userPassword
     * @return
     */
    public int loginUser(String userEmail,String userPassword){
        DoneUser user = databaseUser.loginUser(userEmail,userPassword);
        if ( user != null ){
            DoneApplication.loggedUser = user;
            DoneApplication.database.log("APP-LOGIN","User ("+user.user_email+") logged to the app!");
            DoneApplication.notificationService("Welcome back "+user.user_email+" :3",1);
            return 1;
        }
        else{
            DoneApplication.notificationService("Cannot find user ("+userEmail+") :C",1);
            return -1;
        }
    }

    /**
     * Function for changing password for the user
     * @param currentPassword
     * @param newPassword
     * @return Integer
     */
    public int changePassword(String currentPassword,String newPassword){
        try{
            Password_Validator pv = new Password_Validator(currentPassword);
            if (DoneApplication.loggedUser.user_password.equals(pv.hash())){
                boolean ans = databaseUser.changePassword(newPassword);
                if ( ans ){
                    DoneApplication.database.log("CHANGE-PASSWORD","Password changed for user ("+DoneApplication.loggedUser.user_email+")");
                    DoneApplication.notificationService("Password changed!",1);
                    return 1;
                }
                else{
                    DoneApplication.database.log("CHANGE-PASSWORD-FAILED","Failed to change password (database error)");
                    return -2;
                }
            }
            else{
                DoneApplication.database.log("CHANGE-PASSWORD-FAILED","Failed to change password (wrong current password)");
                DoneApplication.notificationService("Failed to change password, wrong current password",1);
                return 0;
            }
        }catch(Exception ex){
            DoneApplication.database.log("CHANGE-PASSWORD-FAILED","Failed to change password ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for logging out user
     * @return String
     */
    public void logoutUser(){
        if ( DoneApplication.loggedUser != null ){
            DoneApplication.notificationService("Logged out! ("+DoneApplication.loggedUser.user_email+")",1);
            DoneApplication.loggedUser = null;
        }
    }
}
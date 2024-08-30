/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;


import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseMemory;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.DoneMemory;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.Password_Validator;
import com.jakubwawak.done.backend.maintanance.RandomWordGeneratorEngine;

import java.util.ArrayList;

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
    public int registerUser(String userEmail, String userPassword, String userTelephone,String role){
        try{
            DoneUser newUser = new DoneUser();
            RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
            Password_Validator pv = new Password_Validator(userPassword);
            ArrayList<String> roles = new ArrayList<>();
            roles.add("USER");
            roles.add("ADMIN");
            if ( databaseUser.getUserByEmail(userEmail) == null ){
                newUser.user_email = userEmail;
                newUser.user_password = pv.hash();
                newUser.user_recoverypass = rwge.generateRandomString(30,false,false);
                newUser.user_settingstring = "blank";
                newUser.user_telephone = userTelephone;
                newUser.user_wallpaperURL = "blank";
                newUser.user_iconURL = "blank";
                if ( roles.contains(role)  ){
                    newUser.user_role = role;
                }
                else{
                    newUser.user_role = "USER";
                    DoneApplication.database.log("REGISTER-USER-ROLE","Role not found, set to USER");
                }
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
     * Function for reseting password
     * @param user_email
     * @return Integer
     */
    public int resetPassword(String user_email){
        DoneUser user = databaseUser.getUserByEmail(user_email);
        if ( user != null ){
            try{
                RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
                String password = rwge.generateRandomString(14,true,true);
                Password_Validator pv = new Password_Validator(password);
                user.user_password = pv.hash();
                int ans = databaseUser.updateUser(user);
                if ( ans == 1 ){
                    DoneApplication.database.log("RESET-PASSWORD","Password reset for user ("+user.user_email+")- new password: "+password);
                    return 1;
                }
                else{
                    DoneApplication.database.log("RESET-PASSWORD-FAILED","Failed to reset password for user ("+user.user_email+")");
                    return -2;
                }
            }catch (Exception ex){
                DoneApplication.database.log("RESET-PASSWORD-FAILED","Failed to reset password for user ("+user.user_email+") ("+ex.toString()+")");
                return -1;
            }
        }
        else{
            DoneApplication.database.log("RESET-PASSWORD-FAILED","User ("+user_email+") not found");
            DoneApplication.notificationService("User ("+user_email+") not found",1);
            return -1;
        }
    }

    /**
     *  Function for reseting password
     * @param user
     * @return String
     */
    public String resetPassword(DoneUser user){
        try{
            RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
            String password = rwge.generateRandomString(14,true,true);
            Password_Validator pv = new Password_Validator(password);
            user.user_password = pv.hash();
            int ans = databaseUser.updateUser(user);
            if ( ans == 1 ){
                DoneApplication.database.log("RESET-PASSWORD","Password reset for user ("+user.user_email+")- new password: "+password);
                return password;
            }
            else{
                DoneApplication.database.log("RESET-PASSWORD-FAILED","Failed to reset password for user ("+user.user_email+")");
                return null;
            }
        }catch (Exception ex){
            DoneApplication.database.log("RESET-PASSWORD-FAILED","Failed to reset password for user ("+user.user_email+") ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for locking user
     * @param user_email
     * @return
     */
    public int lockUser(String user_email){
        DoneUser user = databaseUser.getUserByEmail(user_email);
        if ( user != null ){
            if ( user.user_active == 1 ){
                user.user_active = 0;
                int ans = databaseUser.updateUser(user);
                if ( ans == 1 ){
                    DoneApplication.database.log("LOCK-USER","User ("+user.user_email+") locked");
                    DoneApplication.notificationService("User ("+user.user_email+") locked",1);
                    return 1;
                }
                else{
                    DoneApplication.database.log("LOCK-USER-FAILED","Failed to lock user ("+user.user_email+")");
                    return -2;
                }
            }
            else{
                DoneApplication.database.log("LOCK-USER-FAILED","User ("+user.user_email+") is already locked");
                DoneApplication.notificationService("User ("+user.user_email+") is already locked",1);
                return 0;
            }
        }
        else{
            DoneApplication.database.log("LOCK-USER-FAILED","User ("+user_email+") not found");
            DoneApplication.notificationService("User ("+user_email+") not found",1);
            return -1;
        }
    }

    /**
     * Function for unlocking user
     * @param user_email
     * @return Integer
     */
    public int unlockuser(String user_email){
        DoneUser user = databaseUser.getUserByEmail(user_email);
        if ( user != null ){
            if ( user.user_active == 0 ){
                user.user_active = 1;
                int ans = databaseUser.updateUser(user);
                if ( ans == 1 ){
                    DoneApplication.database.log("UNLOCK-USER","User ("+user.user_email+") unlocked");
                    DoneApplication.notificationService("User ("+user.user_email+") unlocked",1);
                    return 1;
                }
                else{
                    DoneApplication.database.log("UNLOCK-USER-FAILED","Failed to unlock user ("+user.user_email+")");
                    return -2;
                }
            }
            else{
                DoneApplication.database.log("UNLOCK-USER-FAILED","User ("+user.user_email+") is already unlocked");
                DoneApplication.notificationService("User ("+user.user_email+") is already unlocked",1);
                return 0;
            }
        }
        else{
            DoneApplication.database.log("UNLOCK-USER-FAILED","User ("+user_email+") not found");
            DoneApplication.notificationService("User ("+user_email+") not found",1);
            return -1;
        }
    }

    /**
     * Function for logging user to the application
     * @param userEmail
     * @param userPassword
     * @return Integer
     */
    public int loginUser(String userEmail,String userPassword){
        DoneUser user = databaseUser.loginUser(userEmail,userPassword);
        if ( user != null ){
            DoneApplication.loggedUser = user;
            DoneApplication.database.log("APP-LOGIN","User ("+user.user_email+") logged to the app!");
            if ( user.user_active == 0){
                DoneApplication.notificationService("User account is locked!",1);
                return 0;
            }
            DoneApplication.notificationService("Welcome back "+user.user_email+" :3",1);
            DatabaseMemory dm = new DatabaseMemory();
            if ( dm.getTodaysMemoryForLoggedUser(DoneApplication.loggedUser.user_id) != null){
                DoneApplication.notificationService("User has memory for today!",1);
            }
            else{
                DoneMemory doneMemory = new DoneMemory();
                dm.createMemory(doneMemory);
            }
            return 1;
        }
        else{
            DoneApplication.notificationService("Cannot find user ("+userEmail+") :C",1);
            return -1;
        }
    }

    /**
     * Function for making user admin
     * @param user_email
     * @return Integer
     */
    public int makeUserAdmin(String user_email){
        DoneUser user = databaseUser.getUserByEmail(user_email);
        if ( user != null ){
            if ( user.user_role.equals("USER") ){
                user.user_role = "ADMIN";
                int ans = databaseUser.updateUser(user);
                if ( ans == 1 ){
                    DoneApplication.database.log("MAKE-ADMIN","User ("+user.user_email+") promoted to ADMIN");
                    DoneApplication.notificationService("User ("+user.user_email+") promoted to ADMIN",1);
                    return 1;
                }
                else{
                    DoneApplication.database.log("MAKE-ADMIN-FAILED","Failed to promote user to ADMIN (database error)");
                    return -2;
                }
            }
            else{
                DoneApplication.database.log("MAKE-ADMIN-FAILED","User ("+user.user_email+") is already ADMIN");
                DoneApplication.notificationService("User ("+user.user_email+") is already ADMIN",1);
                return 0;
            }
        }
        else{
            DoneApplication.database.log("MAKE-ADMIN-FAILED","User ("+user_email+") not found");
            DoneApplication.notificationService("User ("+user_email+") not found",1);
            return -1;
        }
    }

    /**
     * Function for making user user
     * @param user_email
     * @return Integer
     */
    public int makeUserUser(String user_email){
        DoneUser user = databaseUser.getUserByEmail(user_email);
        if ( user != null ){
            if ( user.user_role.equals("ADMIN") ){
                user.user_role = "USER";
                int ans = databaseUser.updateUser(user);
                if ( ans == 1 ){
                    DoneApplication.database.log("MAKE-USER","User ("+user.user_email+") demoted to USER");
                    DoneApplication.notificationService("User ("+user.user_email+") demoted to USER",1);
                    return 1;
                }
                else{
                    DoneApplication.database.log("MAKE-USER-FAILED","Failed to demote user to USER (database error)");
                    return -2;
                }
            }
            else{
                DoneApplication.database.log("MAKE-USER-FAILED","User ("+user.user_email+") is already USER");
                DoneApplication.notificationService("User ("+user.user_email+") is already USER",1);
                return 0;
            }
        }
        else{
            DoneApplication.database.log("MAKE-USER-FAILED","User ("+user_email+") not found");
            DoneApplication.notificationService("User ("+user_email+") not found",1);
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
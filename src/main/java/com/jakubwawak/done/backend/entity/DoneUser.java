
/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.entity;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Object for storing entity information about application user
 */
public class DoneUser {
    public ObjectId user_id;
    public String user_email;
    public String user_password;
    public String user_settingstring;
    public String user_recoverypass;
    public String user_telephone;
    public String user_wallpaperURL;
    public String user_iconURL;
    public String user_role; // roles: USER, ADMIN, if empty disable login ability
    public int user_active; // 1 - active, 0 - inactive

    /**
     * Default constructor
     */
    public DoneUser(){
        user_id = null;
        user_email = "";
        user_password = "";
        user_settingstring = "";
        user_recoverypass = "";
        user_telephone = "";
        user_wallpaperURL = "";
        user_iconURL = "";
        user_role = "";
        user_active = 1;
    }

    /**
     * Constructor with database support
     * @param document
     */
    public DoneUser(Document document){
        user_id = document.getObjectId("_id");
        user_email = document.getString("user_email");
        user_password = document.getString("user_password");
        user_settingstring = document.getString("user_settingstring");
        user_recoverypass = document.getString("user_recoverypass");
        user_telephone = document.getString("user_telephone");
        user_wallpaperURL = document.getString("user_wallpaperURL");
        user_iconURL = document.getString("user_iconURL");
        user_role = document.getString("user_role");
        user_active = document.getInteger("user_active");
    }
    /**
     * Function for preparing document of object
     * @return Document
     */
    public Document prepareDocument(){
        Document document = new Document();
        document.append("user_email",user_email);
        document.append("user_password",user_password);
        document.append("user_settingstring",user_settingstring);
        document.append("user_recoverypass",user_recoverypass);
        document.append("user_telephone",user_telephone);
        document.append("user_wallpaperURL",user_wallpaperURL);
        document.append("user_iconURL",user_iconURL);
        document.append("user_role",user_role);
        document.append("user_active",user_active);
        return document;
    }
}

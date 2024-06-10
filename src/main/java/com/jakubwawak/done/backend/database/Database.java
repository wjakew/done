/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.maintanance.ConsoleColors;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Object for connecting to MongoDB database
 */
public class Database {
    public String database_url;
    public boolean connected;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    ArrayList<String> error_collection;

    /**
     * Constructor
     */
    public Database(){
        this.database_url = "";
        connected = false;
        error_collection = new ArrayList<>();
    }

    /**
     * Function for setting database URL
     * @param database_url
     */
    public void setDatabase_url(String database_url){
        this.database_url = database_url;
    }

    /**
     * Function for connecting to database
     * @return boolean
     */
    public void connect(){
        try{
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(database_url))
                    .serverApi(serverApi)
                    .build();
            mongoClient = MongoClients.create(settings);
            mongoDatabase = mongoClient.getDatabase("db_done");
            log("DB-CONNECTION","Connected succesffully with database - running application");
            connected = true;
        }catch(Exception ex){
            // catch error
            log("DB-CONNECTION-ERROR", "Failed to connect to database ("+ex.toString()+")");
            connected = false;
        }
    }

    /**
     * Function for loading collections
     * @param collection_name
     * @return MongoCollection<Document>
     */
    MongoCollection<Document> get_data_collection(String collection_name){
        return mongoDatabase.getCollection(collection_name);
    }

    /**
     * Function for story log data
     * @param log_category
     * @param log_text
     */
    public void log(String log_category, String log_text){
        error_collection.add(log_category+"("+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text);
        if ( log_category.contains("FAILED") || log_category.contains("ERROR")){
            System.out.println(ConsoleColors.RED_BRIGHT+log_category+"["+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text+"]"+ConsoleColors.RESET);
            try{
                Notification noti = Notification.show(log_text);
                noti.addThemeVariants(NotificationVariant.LUMO_ERROR);

            }catch(Exception ex){}
        }
        else{
            if (DoneApplication.debugLogPrintFlag == 1)
                System.out.println(ConsoleColors.GREEN_BRIGHT+log_category+ConsoleColors.RED_BOLD_BRIGHT+"["+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+"] - "+ConsoleColors.GREEN_BOLD_BRIGHT+log_text+"]"+ConsoleColors.RESET);
        }
        // TODO inserting log to database
    }
}
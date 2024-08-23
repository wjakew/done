/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done;

import com.jakubwawak.done.backend.database.DatabaseHistory;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.ConsoleColors;
import com.jakubwawak.done.datamanager.DoneMenu;
import com.jakubwawak.done.frontend.components.ListTaskComponent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.jakubwawak.done.backend.database.Database;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * Application for getting shit done
 */
@SpringBootApplication
@EnableVaadin({"com.jakubwawak"})
@PWA(
		name = "done",
		shortName = "done"
)
@Theme(value="donetheme")
public class DoneApplication extends SpringBootServletInitializer implements AppShellConfigurator {

	public static String build = "done230824REV1";
	public static String version = "1.2.0";
	public static int debugLogPrintFlag = 1;

	public static int debugFlag = 0;

	public static Database database; // database connector
	public static DatabaseHistory databaseHistory;

	public static DoneUser loggedUser;
	public static ListTaskComponent ltc;
	public static DoneTask selected;

	public static String runTime;

	public static DoneMenu menu;

	public static String UI_WIDTH = "80%";
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final String SERVER_NAME = "DONE-TEST";

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		showHeader();
		selected = null;
		runTime = LocalDateTime.now().toString();
		database = new Database();
		menu = new DoneMenu();
		if ( args.length > 0  ){
			String databaseURL = args[0];
			database.setDatabase_url(databaseURL);
			database.connect();
			if (database.connected) {
				databaseHistory = new DatabaseHistory();
				// run web application
				if ( debugFlag == 0 ){
					SpringApplication.run(DoneApplication.class, args);
					menu.run();
				}
				else{
					consoleWriteService("Running tests...");
					new DoneTest();
				}
			}
			else{
				consoleWriteService("Failed to connect to database!");
			}
		}
		else{
			consoleWriteService("No argument with database URL provided!");
		}
	}


	/**
	 * Function for showing header
	 */
	static void showHeader(){
		String header = "     _\n" +
				"  __| | ___  _ __   ___\n" +
				" / _` |/ _ \\| '_ \\ / _ \\\n" +
				"| (_| | (_) | | | |  __/_\n" +
				" \\__,_|\\___/|_| |_|\\___(_)\n"+version+"/"+build;

		System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+header+ConsoleColors.RESET);
	}

	/**
	 * Function for showing notification service data
	 * @param notificationString
	 * @param notificationState
	 */
	public static void notificationService(String notificationString, int notificationState){
		try{
			Notification noti = Notification.show(notificationString);
			noti.addClassName("notification");
			noti.setPosition(Notification.Position.BOTTOM_END);
			switch(notificationState){
				case 1:
					noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
					break;
				case 2:
					noti.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
					break;
				case 3:
					noti.addThemeVariants(NotificationVariant.LUMO_WARNING);
					break;
				case 4:
					noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
					break;
			}
		}catch(Exception ex){}
	}

	/**
	 * Function for printing data from application services to console
	 * @param message
	 */
	public static void consoleWriteService(String message){
		System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT+ LocalDateTime.now()+" - DONE APP INFO - "+message+ ConsoleColors.RESET);
	}

}

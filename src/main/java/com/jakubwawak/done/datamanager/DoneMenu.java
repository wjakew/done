/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;


import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.maintanance.ConsoleColors;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Object for creating menu for setting different parameters in application
 */
public class DoneMenu {

    public ArrayList<String> history;

    boolean runFlag;

    public DoneMenu(){
        history = new ArrayList<>();
        runFlag = true;
    }

    /**
     * Function for command recognition
     * @param userInput
     */
    void mind(String userInput){
        String[] words = userInput.split(" ");
        if ( words.length >= 1 ){
            switch(words[0]){
                case "exit":
                {
                    DoneApplication.consoleWriteService("Exiting...");
                    runFlag = false;
                    System.exit(0);
                }
                case "register":
                {
                    // 0        1     2        3
                    // register email password telephone
                    if (words.length == 4){
                        Matcher matcher = DoneApplication.VALID_EMAIL_ADDRESS_REGEX.matcher(words[1]);
                        if ( matcher.matches() ){
                            if ( words[2].length() > 8 ){
                                UserDataManager udm = new UserDataManager();
                                int ans = udm.registerUser(words[1],words[2],words[3]);
                                if ( ans == 1 ){
                                    DoneApplication.consoleWriteService("Account created!");
                                }
                                else{
                                    DoneApplication.consoleWriteService("Cannot create account, check help :3");
                                }
                            }
                            else{
                                DoneApplication.consoleWriteService("Password to short :3");
                            }
                        }
                        else{
                            DoneApplication.consoleWriteService("Provided email is not correct, please check :3");
                        }
                    }
                    else{
                        DoneApplication.consoleWriteService("Wrong command usage, check help :3");
                    }
                    break;
                }
                case "terminal":
                {
                    System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT+"you're so done> "+ ConsoleColors.RESET);
                    break;
                }
            }
        }
    }

    /**
     * Function for running loop
     */
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(runFlag){
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT+"journal :3> "+ConsoleColors.RESET);
            String userInput = scanner.nextLine();
            if ( userInput.isBlank() ){
                DoneApplication.consoleWriteService(ConsoleColors.PURPLE_BOLD_BRIGHT+"User input is empty, pleeease read help :3"+ConsoleColors.RESET);
            }
            else{
                // run command recognition
                mind(userInput);
            }
        }
    }
}
package com.app.temp_view;

import com.app.default_view.LogInTempView;
import com.app.default_view.SignUpTempView;
import java.util.Scanner;

public class MainMenuView {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREEN = "\u001B[32m";
    public static final String VIOLET = "\u001B[38;5;55m";
    public static final String user = "";
    boolean running = true;
    
    Scanner sc = new Scanner(System.in);

    public void printWelcomeMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println(BLUE + "==================================================================");
        System.out.println(BLUE + "* " + PURPLE + "\t  Welcome to Attendance Management System! \t\t" + BLUE + " *");
        System.out.println(BLUE + "* " + GREEN + "\t\t     _      _  _  ___ " + BLUE + "\t\t\t\t *");
        System.out.println(BLUE + "* " + CYAN + "\t\t    / \\    |  \\/  |/ ____|" + BLUE + "\t\t\t *");
        System.out.println(BLUE + "* " + GREEN + "\t\t   //  \\   | \\  / | (_  " + BLUE + "\t\t\t *");
        System.out.println(BLUE + "* " + CYAN + "\t\t  //___ \\  | |\\/| |\\___ \\ " + BLUE + "\t\t\t *");
        System.out.println(BLUE + "* " + GREEN + "\t\t /  _  \\ | |  | |____) |" + BLUE + "\t\t\t *");
        System.out.println(BLUE + "* " + CYAN + "\t\t/__/   \\__\\|_|  |_|_____/ " + BLUE + "\t\t\t *");
        System.out.println(BLUE + "*  \t\t\t\t\t\t\t\t *");
        System.out.println(BLUE + "==================================================================" + RESET);

        welcomeConfirmation(sc);
    }

    public void welcomeConfirmation(Scanner sc) {
        //sc.nextLine();
        System.out.print("Are you a Trainer? (Y/N): ");
        String confirm = sc.nextLine();
        if ('Y' == confirm.toUpperCase().charAt(0)) {
            adminAuthentication(true);
        } else if ('N' == confirm.toUpperCase().charAt(0)) {
            LogInTempView ltv = new LogInTempView();
            while (running) {
                ltv.logInView("others");
            }
        } else {
            System.out.println("> Invalid Input! Please Try Again");
            welcomeConfirmation(sc);
        }
    }

    public void adminAuthentication(boolean running) {
        while (running) {
            System.out.println(GREEN + "\t\t+--------+       +---------+ ");
            System.out.println(GREEN + "\t\t| LOG IN |       | SIGN UP |");
            System.out.println(GREEN + "\t\t+--------+       +---------+ ");
            System.out.println("");
            System.out.println(BLUE + "-------------------------- Please select --------------------------");
            System.out.println(GREEN + "\t\t\t    [1] Log In");
            System.out.println(GREEN + "\t\t\t    [2] Sign up");
            System.out.println(GREEN + "\t\t\t    [3] Back");
            System.out.println(BLUE + "-------------------------------------------------------------------");
            System.out.print(RED + "Enter a choice: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (confirmation(sc, choice)) {
                    case 1:
                        LogInTempView ltv = new LogInTempView();
                        ltv.logInView("admin");
                        break;
                    case 2:
                        SignUpTempView stv = new SignUpTempView();
                        stv.signUptView();
                        printWelcomeMessage();
                        break;
                    case 3:
                        printWelcomeMessage();
                        break;
                    default:
                        System.out.println(RED + "Please enter a number that is in the choices!\n" + RESET);
                }
            } catch (Exception ex) {
                sc.nextLine();
                System.out.println(RED + "Please enter a valid choice!\n" + RESET);
            }
        }
    }

    public int confirmation(Scanner sc, int choice) {
        int agree = choice;
        //sc.nextLine();
        System.out.print("Are you sure? (Y/N): ");
        String confirm = sc.nextLine();
        if ('Y' == confirm.toUpperCase().charAt(0)) {
            agree = choice;
        } else if ('N' == confirm.toUpperCase().charAt(0)) {
            printWelcomeMessage();
        } else {
            System.out.println("> Invalid Input! Please Try Again");
            confirmation(sc, choice);    
        }
        return agree;
    }  
}    
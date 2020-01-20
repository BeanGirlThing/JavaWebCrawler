package me.merhlim;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Java Web Crawler");
        System.out.print("Enter starting page to scrape:");
        String startingPage = scan.nextLine();
        System.out.print("How many webpages deep do you wish to go? (Time it will take will increase exponentially depending on depth)");
        int depth;
        while(true){
            try{
                depth = scan.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                scan.next();
            }
        }

        boolean db;
        String dbURL = "";

        scan = new Scanner(System.in);

        System.out.println("Do you wish to add the output to a database (Yes/No) (Default:No)");
        String choice = scan.nextLine();

        if(choice.equalsIgnoreCase("yes")) {
            db = true;
            System.out.println("Enter the URI that points to the location of the database and the file");
            dbURL = scan.nextLine();
        } else {
            db = false;
        }

        Crawler crawl = new Crawler(startingPage,depth,db,"jdbc:sqlite:"+dbURL);
        crawl.start();

        System.out.println(crawl.getLinksFound());
    }
}

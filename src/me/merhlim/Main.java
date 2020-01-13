package me.merhlim;

import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static String startingPage;
    static int depth;
    public static void main(String[] args) {
        System.out.println("Java Web Crawler");
        System.out.print("Enter starting page to scrape:");
        startingPage = scan.nextLine();
        System.out.print("How many webpages deep do you wish to go? (Time it will take will increase exponentially depending on depth)");
        while(true){
            try{
                depth = scan.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Please enter a number");
                scan.next();
            }
        }

        Crawler crawl = new Crawler(startingPage,depth);
        crawl.start();

        System.out.println(crawl.getLinksFound());
    }
}

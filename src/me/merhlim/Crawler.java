package me.merhlim;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {
    private String start;
    private String cursor;
    private ArrayList<String> linksFound = new ArrayList<>();
    private ArrayList<String> scannedUrls = new ArrayList<>();
    private ArrayList<Element> page;
    private int depth;
    private boolean fail = false;

    public Crawler(String start, int depth) {
        this.start = start;
        this.depth = depth;
    }

    public void start() {
        cursor = start;
        runCrawl();
        for(int i = 0; i<depth; i++) {
            ArrayList<String> tmpLinksFound = linksFound;
            for(int a = 0; a <= tmpLinksFound.size()-1; a++) {
                String item = tmpLinksFound.get(a);
                boolean test = false;
                for(String check : scannedUrls) {
                    if (check.equals(item)){
                        test = true;
                    }
                }
                if(test) {
                    continue;
                }
                cursor = item;
                runCrawl();
            }
        }

    }

    private void runCrawl() {
        if(!fail) {
            if (!cursor.equals("")) {
                System.out.println("Crawling " + cursor);
                page = scrape(cursor);
                elementListToValuesFound();
            }
        }
    }

    private void elementListToValuesFound() {
        for(Element element : page){
            try {
                boolean found = false;
                String target = element.attr("abs:href");
                for(Object item : linksFound) {
                    String i = (String) item;
                    if(i.equals(target)) {
                        found = true;
                    }
                }
                if (!found) {
                    linksFound.add(target);
                }
            } catch (NullPointerException npe){
                continue;
            }

        }
    }


    private ArrayList scrape(String url) {
        Document page;
        Elements elementsFound = null;
        ArrayList<Element> output = new ArrayList<>();
        try {
            page = Jsoup.connect(url).get();
            scannedUrls.add(url);
            elementsFound = page.body().getElementsByAttribute("href");
        } catch (IOException e) {
            if (url.equals(start)) {
                System.out.println("Url entered is invalid");
                fail = true;
            } else {
                // No need to do anything as there is a chance that the tool may hit a page that isnt up.
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal arguement, not a url");
            fail = true;
        }
        if (!fail) {
            try {
                for (int i = 0; i < elementsFound.size() - 1; i++) {
                    Element e = elementsFound.get(i);
                    output.add(e);
                }
            } catch (NullPointerException npe) {
                return output;
            }
        }
        return output;
    }

    public ArrayList<String> getLinksFound() {
        return linksFound;
    }
}

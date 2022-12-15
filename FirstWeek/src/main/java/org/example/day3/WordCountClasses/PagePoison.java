package org.example.day3.WordCountClasses;

public class PagePoison extends Page {
    public PagePoison(String title, String text) {
        super(title, text);
    }
    public PagePoison() {
        super("", "");
    }

    @Override
    public boolean isPoisonPill() {
        return true;
    }
}

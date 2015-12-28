package com.example.entity;

/**
 * Created by kathy on 2015/12/28.
 */
public class ContactRow  {

    private String title;
    private int icon;

    @Override
    public String toString() {
        return "ContactRow{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }

    public ContactRow(String title, int icon) {
        this.title = title;
        this.icon = icon;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
    public ContactRow(){
    }

}

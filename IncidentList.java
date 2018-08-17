package com.company;

import java.util.LinkedList;

/**
 * Created by luisricardo on 17/08/2018.
 */
public class IncidentList extends LinkedList<TextFile> {

    String listName;
    public IncidentList(String listName){
        this.listName = listName;
    }

    public void addFile(TextFile file){
        add(file);
    }
}

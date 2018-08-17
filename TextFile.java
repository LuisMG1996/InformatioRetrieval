package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by luisricardo on 17/08/2018.
 */
public class TextFile {
    String path;
    String nameFile;
    File txtFile;

    public TextFile(String path, String nameFile){
        try {
            txtFile = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));
        }catch (Exception e){
            System.out.println("Error loading file: " + e);
        }
    }
}

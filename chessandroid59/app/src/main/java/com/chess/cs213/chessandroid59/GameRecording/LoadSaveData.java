package com.chess.cs213.chessandroid59.GameRecording;

import android.content.Context;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jaemin Shin
 * @author Minyoung Chung
 *
 */

public class LoadSaveData implements Serializable {

    public static ArrayList<GameSave> list;
    public static Context context;

    public static void loadGameData() {
        File f = new File(context.getFilesDir(), "data.dat");
        if (f.exists()) {
            try {
                FileInputStream fileInputStream = context.openFileInput("data.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                list = (ArrayList<GameSave>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            list = new ArrayList<GameSave>();
        }
    }

    public static void saveGameData() {
        try {
            FileOutputStream fileOutputStream=context.openFileOutput("data.dat",0);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
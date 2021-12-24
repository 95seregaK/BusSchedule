package com.example.busschedule.json;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonHelper {
    public void writeToFile(Context context, String jsonString) throws IOException {
        FileOutputStream outputStream = context.openFileOutput("file.txt", Context.MODE_PRIVATE);
        outputStream.write(jsonString.getBytes());
        outputStream.close();
    }

    public String readFromFile(Context context) throws IOException {
        FileInputStream inputStream = context.openFileInput("file.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        /*
         * Производим построчное считывание данных из файла в конструктор строки,
         * Псоле того, как данные закончились, производим вывод текста в TextView
         */
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}

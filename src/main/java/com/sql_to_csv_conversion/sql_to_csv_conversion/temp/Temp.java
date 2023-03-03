package com.sql_to_csv_conversion.sql_to_csv_conversion.temp;

import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Temp {
    public static void main(String args[]) throws IOException {
        System.out.println("Hello bro 😇 !!");

        FileOutputStream fos = new FileOutputStream("D:\\CODE\\JAVA\\JAVA WEB SERVICE\\sql_to_csv_conversion\\src\\main\\resources\\data\\user_data.csv");
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        CSVWriter writer = new CSVWriter(osw);

        String[] header = { "Name", "Class", "Marks" };

        writer.writeNext(header);
        writer.close();
        osw.close();
        fos.close();
    }
}

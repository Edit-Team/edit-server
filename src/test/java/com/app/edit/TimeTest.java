package com.app.edit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeTest {

    public static void main(String[] args){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd ");
        String text = date.format(formatter);
        System.out.println(text);
    }
}

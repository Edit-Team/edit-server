package com.app.grip.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GetDateTime {

    public String getDataTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getCustomDataTime(String operation, Long number){
        if(operation.equals("plus")){
            return LocalDateTime.now().plusHours(number).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }else{
            return LocalDateTime.now().minusHours(number).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

}

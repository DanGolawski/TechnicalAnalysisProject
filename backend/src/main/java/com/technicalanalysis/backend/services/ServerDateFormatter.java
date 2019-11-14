package com.technicalanalysis.backend.services;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this class converts unix epoch time (1573689600) to normal date 14-11-2019
 */
public class ServerDateFormatter {

    private SimpleDateFormat formatter;


    public ServerDateFormatter(){
        formatter = new SimpleDateFormat("dd-MM-yyyy");
    }

    public String formatServerDateToSimpleDate(long number){
        return formatter.format(new Date(number*1000));
    }
}

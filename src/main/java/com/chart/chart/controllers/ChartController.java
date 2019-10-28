package com.chart.chart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {

    @RequestMapping(value = "/chartdata")
    public String getChartData(){
        return "XXXXXXXXXXXXXXXXXXXXXXXXXX";
    }
}

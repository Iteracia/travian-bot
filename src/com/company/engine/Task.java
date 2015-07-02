package com.company.engine;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public abstract class Task implements Runnable {
    WebDriver driver;
    Map<String,String> parametress = new HashMap<>();
    public Task driver(WebDriver driver){
        this.driver = driver;
        return this;
    }
    public Task params( Map<String,String> params ){
        this.parametress = params;
        return this;
    }
}

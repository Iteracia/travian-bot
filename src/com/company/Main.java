package com.company;

import com.company.engine.Engine;
import org.openqa.selenium.WebDriver;

public class Main {

    public static void main(String[] args) {
        Engine.init();
        WebDriver driver = Engine.newDriver();
        try {
            Engine.login( driver );
            Engine.analyzeExps( driver );
            System.in.read();
            driver.quit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
        }
    }
}

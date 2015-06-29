package com.company;

import com.company.engine.Engine;
import com.company.engine.currentExp;
import com.company.engine.executor;
import org.openqa.selenium.WebDriver;

public class Main {

    public static void main(String[] args) {
        try {
            executor.ince().run();
            System.in.read();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

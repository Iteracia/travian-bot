package com.company;

import com.company.engine.Executor;

public class Main {

    public static void main(String[] args) {
        try {
            Executor.ince().run();
            System.in.read();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.company;

import java.io.*;

public class Settings {
    public static String login;
    public static String password;
    public static final boolean printAllBuildings = false;

    static {
        File info = new File( "./settings.txt" );
        try ( BufferedReader br = new BufferedReader(new FileReader(info)) ) {
            login = br.readLine();
            password = br.readLine();
        }catch ( Exception e ){
            System.out.println("failed to load login and password");
            login = "";
            password = "";
        }
    }
}

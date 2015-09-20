package com.company;

import java.io.*;

public class Settings {
    public static String login;
    public static String password;
    public static String world = "http://ts5.travian.ru";
    public static final boolean printAllBuildings = false;
    public static final boolean deleteXMLCommandsOnRead = false;

    static {
        File info = new File( System.getProperty( "user.dir" )+"\\settings.txt");
        try ( BufferedReader br = new BufferedReader(new FileReader(info)) ) {
            login = br.readLine();
            password = br.readLine();
        }catch ( Exception e ){
            System.out.println("failed to load login and password from file "+info.getAbsolutePath());
            login = "";
            password = "";
        }
    }
}

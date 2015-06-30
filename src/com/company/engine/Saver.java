package com.company.engine;

import com.company.engine.building.Building;
import com.company.engine.village.Village;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Saver {
    public static void saveExpsNames(){
        List<String> exps = Engine.expansNames;
        try( FileOutputStream fileOut = new FileOutputStream("./save/expansNames.ser");
             ObjectOutputStream out = new ObjectOutputStream( fileOut ) ){
            out.writeObject( exps );
        }catch ( Exception e ){
            System.out.println("Saver.saveExpNames error");
            e.printStackTrace();
        }
    }
    public static void saveBuildings(){
        List<Village> villages = Engine.villages;
        try (FileOutputStream fileOut = new FileOutputStream("./save/buildings.ser");
             ObjectOutputStream out = new ObjectOutputStream( fileOut )) {
            for ( Village village : villages ) {
                List<Building> buildings = village.getBuildings();
                out.writeObject( buildings );
            }
        }catch ( Exception e ){
            System.out.println("Saver.saveBuildings error");
            e.printStackTrace();
        }
    }
    public static void LoadBuildings(){
        if ( CurrentExp.ince().getCvindex() != Engine.villages.size() ){
            return;
        }
        try ( FileInputStream fileIn = new FileInputStream( "./save/buildings.ser" );
        ObjectInputStream in = new ObjectInputStream( fileIn )){
            for ( Village village : Engine.villages ) {
                village.setBuildings( (List)in.readObject() );
            }
        }catch ( Exception e ){
            System.out.println("Saver.loadBuildings error");
            e.printStackTrace();
        }

    }
}

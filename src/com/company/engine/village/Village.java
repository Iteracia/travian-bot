package com.company.engine.village;

import com.company.Settings;
import com.company.engine.building.Building;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Village {
    private String name;
    private Resources resourcesLink = new Resources();
    private Troops troopsLink = new Troops();
    private List<Building> buildsInProgress = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>( 38 );

    public Village( String name ){
        this.name = name;
    }
    public Resources resources(){
        return resourcesLink;
    }
    public Troops troops(){
        return troopsLink;
    }

    public void addBInProc( Building building ){
        buildsInProgress.add( building );
    }
    public void addBuilding( Building building ){
        buildings.add( building );
    }

    @Override
    public String toString() {
        StringBuilder responce = new StringBuilder();
        responce.append("> ").append(name).append("\n");
        if( resources().isSet() ) {
            responce.append(" = Resources = \n");
            responce.append("Wood : ").append(resourcesLink.getWood()).append("\n");
            responce.append("Clay : ").append(resourcesLink.getClay()).append("\n");
            responce.append("Iron : ").append(resourcesLink.getIron()).append("\n");
            responce.append("Grain: ").append(resourcesLink.getGrain()).append("\n");
        }
        if (buildsInProgress.size()>0) {
            responce.append(" = Buildings in progress = \n");
            buildsInProgress.forEach( responce::append );
        }
        if (!troopsLink.isEmpty()){
            responce.append(" = Army = \n");
            responce.append( troopsLink.toString() );
        }
        if ( Settings.printAllBuildings ){
            responce.append( " = All Buildings = " );
            buildings.forEach( responce::append );
        }
        return responce.toString();
    }

    public List<Building> getBuildings() {
        return buildings;
    }
    private void saveBuildings(){
        try( FileOutputStream fileOut = new FileOutputStream("./save/buildings.ser");
             ObjectOutputStream out = new ObjectOutputStream( fileOut ) ){
            out.writeObject( buildings );
        }catch ( Exception e ){

        }
    }
}

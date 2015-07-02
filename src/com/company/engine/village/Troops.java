package com.company.engine.village;

import com.company.engine.Engine;

import java.io.Serializable;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class Troops {
    /*
     * Inner helper class
     */
    public static class troop implements Serializable{
        private int count;
        private String name;

        troop( int count, int id){
            this.count = count;
            this.name = troopNameMap.getK( id );
        }

        @Override
        public String toString() {
            return count+"  "+name;
        }
    }

    /*              ***
            == class itself ==
                    ***
     */
    private static KVmap<String, Integer> troopNameMap = new KVmap<>();

    private List<troop> army;

    public Troops(){
        army = new ArrayList<>( 11 );
        army.add( 0, new troop( 0 , 1 ));
        army.add( 1, new troop( 0 , 2 ));
        army.add( 2, new troop( 0 , 3 ));
        army.add( 3, new troop( 0 , 4 ));
        army.add( 4, new troop( 0 , 5 ));
        army.add( 5, new troop( 0 , 6 ));
        army.add( 6, new troop( 0 , 7 ));
        army.add( 7, new troop( 0 , 8 ));
        army.add( 8, new troop( 0 , 9 ));
        army.add( 9, new troop( 0 , 10 ));
        army.add( 10, new troop( 0 , 11 ));
    }

    static {
        troopNameMap.put( "Легионеров",1 );
        troopNameMap.put( "Преторианцев",2 );
        troopNameMap.put( "Империанцев",3 );
        troopNameMap.put( "Конных разведчиков",4 );
        troopNameMap.put( "Конниц императора",5 );
        troopNameMap.put( "Конниц Цезаря",6 );
        troopNameMap.put( "Таранов",7 );
        troopNameMap.put( "Огненных катапульт",8 );
        troopNameMap.put( "Сенаторов",9 );
        troopNameMap.put( "Поселенцев",10 );
        troopNameMap.put( "Герой",11 );
    }


    public Troops addTroop( int count, String name){
        int id = troopNameMap.getV( name );
        return addTroop( count, id+1 );
    }
    public Troops addTroop( int count, int id){
        army.get( id-1 ).count += count;
        return this;
    }
    public Troops clearTroops(){
        army.clear();
        return this;
    }

    public boolean isEmpty(){
        return army.size()==0;
    }

    public List<troop> getArmy() {
        return army;
    }

    public void setArmy( List<troop> army ) {
        this.army = army;
    }

    public static void copyToClipboardForGetterTools(){
        StringBuilder builder = new StringBuilder(  );
        builder.append( "Войска в деревнях\n" );
        builder.append( "Легионер\tПреторианец\tИмперианец\tКонный разведчик\tКонница императора\tКонница Цезаря\tТаран\tОгненная катапульта\tСенатор\tПоселенец\tГерой\n" );
        for ( Village village : Engine.villages ) {
            builder.append( village.getName()+"\t" );
            List<troop> army = village.troops().army;
            for ( int i = 0; i < army.size(); i++ ) {
                builder.append( army.get( i ).count );
                if ( i != army.size()-1 ){
                    builder.append( "\t" );
                }
            }
            builder.append( "\n" );
        }
        String myString = builder.toString();
        System.out.println("tools>"+myString+"<");
        StringSelection stringSelection = new StringSelection (myString);
        Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        clpbrd.setContents (stringSelection, null);
        System.out.println(" < Troops table put to clipboard");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        army.forEach( troop -> builder.append( troop.toString() ).append("\n"));
        return builder.toString();
    }
}

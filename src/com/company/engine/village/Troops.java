package com.company.engine.village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Troops {
    /*
     * Inner helper class
     */
    private class troop{
        private int count;
        private Integer type;
        private String name;

        troop( int count, String name ){
            this.count = count;
            this.name = name;
            this.type = troopNameMap.getV( name );
            if ( this.type == null){
                this.type = -1;
            }
        }
        troop( int count, int id){
            this.count = count;
            this.name = troopNameMap.getK( id );
            this.type = id;
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
    private List<troop> army = new ArrayList<>();


    static {
        troopNameMap.put( "Герой",11 );
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
    }


    public void addTroop( int count, String name){
        army.add( new troop( count, name ) );
    }
    public void addTroop( int count, int id){
        army.add( new troop( count, id ) );
    }

    public boolean isEmpty(){
        return army.size()==0;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        army.forEach( troop -> {
            builder.append( troop.toString() ).append("\n");
        });
        return builder.toString();
    }
}

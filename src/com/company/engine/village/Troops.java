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

        public troop( int count, String name ){
            this.count = count;
            this.name = name;
            this.type = troopNameMap.getV( name );
            if ( this.type == null){
                this.type = -1;
            }
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
        troopNameMap.put( "Герой",1 );
        troopNameMap.put( "Легионеров",2 );
        troopNameMap.put( "Преторианцев",3 );
        troopNameMap.put( "Империанцев",4 );
    }


    public void addTroop( int count, String name){
        army.add( new troop( count, name ) );
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

package com.company.engine.village;

import java.util.ArrayList;
import java.util.List;

public class troops {
    private class troop{
        private int count;
        private int type; //todo
        private String name;
        public troop( int count, String name ){
            this.count = count;
            this.name = name;
        }

        @Override
        public String toString() {
            return count+"  "+name;
        }
    }

    private List<troop> army = new ArrayList<>();

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

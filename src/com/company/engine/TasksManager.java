package com.company.engine;

import com.company.engine.building.Market;

public class TasksManager {
    public static Task manage (String type,String id) throws IndexOutOfBoundsException{
        switch ( type ) {
            case "Analyze" :
                switch ( id ) {
                    case "1":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeResources( driver );
                            }
                        };
                    case "2":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeVillageBuildingsInP( driver );
                            }
                        };
                    case "3":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeTroops( driver );
                            }
                        };
                    case "4":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeSlots( driver );
                            }
                        };
                    default:
                        throw new IndexOutOfBoundsException( "id analyze index out of range" );
                }
            case "Market" :
                switch ( id ){
                    case "2":
                        return new Task() {
                            @Override
                            public void run() {
                                Market.enterMarketWindow( driver );
                            }
                        };
                }
            case "Barraks" :
                switch ( id ){

                }
            case "Load" :
                switch ( id ){
                    case "3":
                        return new Task() {
                            @Override
                            public void run() {
                                Saver.loadTroops();
                            }
                        };
                    case "4":
                        return new Task() {
                            @Override
                            public void run() {
                                Saver.loadBuildings();
                            }
                        };
                    default:
                        throw new IndexOutOfBoundsException( "id load index out of range" );
                }
            default:
                throw new IndexOutOfBoundsException( "type index out of range" );
        }
    }
}

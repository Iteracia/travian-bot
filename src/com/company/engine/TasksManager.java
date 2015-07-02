package com.company.engine;

import com.company.engine.building.Market;

import java.util.Map;

public class TasksManager {
    public static Task manage (String type,String id, Map<String,String> params) throws IndexOutOfBoundsException{
        switch ( type ) {
            case "Analyze" :
                switch ( id ) {
                    case "1":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeResources( driver );
                            }

                            @Override
                            public String toString() {
                                return "analyzeResources";
                            }
                        };
                    case "2":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeVillageBuildingsInP( driver );
                            }

                            @Override
                            public String toString() {
                                return "analyzeVillageBuildings";
                            }
                        };
                    case "3":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeTroops( driver );
                            }

                            @Override
                            public String toString() {
                                return "analyzeTroops";
                            }
                        };
                    case "4":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeSlots( driver );
                            }

                            @Override
                            public String toString() {
                                return "analyzeSlots";
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
                                Market.sendResources( driver, this.parametress );
                            }
                        }.params( params );
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

                            @Override
                            public String toString() {
                                return "loadTroops";
                            }
                        };
                    case "4":
                        return new Task() {
                            @Override
                            public void run() {
                                Saver.loadBuildings();
                            }

                            @Override
                            public String toString() {
                                return "loadBuildings";
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

package com.company.engine;

import com.company.engine.building.Market;
import com.company.engine.building.Rallypoint;
import com.company.engine.village.Troops;

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
                    case "5":
                        return new Task() {
                            @Override
                            public void run() {
                                Troops.copyToClipboardForGetterTools();
                            }
                            @Override
                            public String toString() {
                                return "getterTable";
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
            case "RallyPoint" :
                switch ( id ){
                    case "3":
                        return new Task() {
                            @Override
                            public void run() {
                                Rallypoint.sendTroops( params );
                            }
                            @Override
                            public String toString() {
                                return "RallyPoint sendTroops";
                            }
                        };
                    case "5":
                        return new Task() {
                            @Override
                            public void run() {
                                Rallypoint.sendFarmList( params );
                            }
                            @Override
                            public String toString() {
                                return "RallyPoint sendFarmList";
                            }
                        };
                    case "6":
                        return new Task() {
                            @Override
                            public void run() {
                                Rallypoint.fillFarmList( params );
                            }
                            @Override
                            public String toString() {
                                return "RallyPoint fillFarmList";
                            }
                        };
                }
            default:
                throw new IndexOutOfBoundsException( "type index out of range" );
        }
    }
}

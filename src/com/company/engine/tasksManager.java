package com.company.engine;

public class tasksManager {
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
                                Engine.analyzeVillageBuildings( driver );
                            }
                        };
                    case "3":
                        return new Task() {
                            @Override
                            public void run() {
                                Engine.analyzeTroops( driver );
                            }
                        };
                    default:
                        throw new IndexOutOfBoundsException( "id index out of range" );
                }
            case "Market" :
                switch ( id ){

                }
            case "Barraks" :
                switch ( id ){

                }
            default:
                throw new IndexOutOfBoundsException( "type index out of range" );
        }
    }
}

package com.company.engine.village;

public class village {
    private String name;
    private resources resourcesLink = new resources();

    public village( String name ){
        this.name = name;
    }
    public resources resources(){
        return resourcesLink;
    }

    @Override
    public String toString() {
        StringBuilder responce = new StringBuilder();
        responce.append( name ).append( "\n");
        responce.append( " = Resources = \n");
        responce.append( "Wood : ").append(resourcesLink.getWood() ).append("\n");
        responce.append( "Clay : ").append(resourcesLink.getClay() ).append("\n");
        responce.append( "Iron : ").append(resourcesLink.getIron() ).append("\n");
        responce.append( "Grain: ").append(resourcesLink.getGrain() ).append("\n");

        return responce.toString();
    }
}

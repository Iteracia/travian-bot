package com.company.engine.village;

import java.util.ArrayList;
import java.util.List;

public class village {
    private String name;
    private resources resourcesLink = new resources();
    private troops troopsLink = new troops();
    private List<building> buildsInProgress = new ArrayList<>();

    public village( String name ){
        this.name = name;
    }
    public resources resources(){
        return resourcesLink;
    }
    public troops troops(){
        return troopsLink;
    }

    public void addBInProc( building building){
        buildsInProgress.add( building );
    }

    @Override
    public String toString() {
        StringBuilder responce = new StringBuilder();
        responce.append("Village ").append(name).append("\n");
        if( resources().isSet() ) {
            responce.append(" = Resources = \n");
            responce.append("Wood : ").append(resourcesLink.getWood()).append("\n");
            responce.append("Clay : ").append(resourcesLink.getClay()).append("\n");
            responce.append("Iron : ").append(resourcesLink.getIron()).append("\n");
            responce.append("Grain: ").append(resourcesLink.getGrain()).append("\n");
        }
        if (buildsInProgress.size()>0) {
            responce.append(" = Buildings in progress = \n");
            buildsInProgress.forEach(responce::append);
        }
        if (!troopsLink.isEmpty()){
            responce.append(" = Army = \n");
            responce.append( troopsLink.toString() );
        }
        return responce.toString();
    }
}

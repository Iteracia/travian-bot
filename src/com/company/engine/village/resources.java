package com.company.engine.village;

public class resources {
    private float wood;
    private float clay;
    private float iron;
    private float grain;
    private boolean isSet;

    public resources(){
        this.wood = 0;
        this.clay = 0;
        this.iron = 0;
        this.grain = 0;
        isSet = false;
    }
    public resources(float wood, float clay, float iron, float grain) {
        this.wood = wood;
        this.clay = clay;
        this.iron = iron;
        this.grain = grain;
        isSet = true;
    }

    public void set(resources reses){
        this.wood = reses.wood;
        this.clay = reses.clay;
        this.iron = reses.iron;
        this.grain = reses.grain;
    }

    public boolean isSet() {
        return isSet;
    }

    public float getWood() {
        return wood;
    }

    public void setWood(float wood) {
        this.wood = wood;
    }

    public float getClay() {
        return clay;
    }

    public void setClay(float clay) {
        this.clay = clay;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public float getGrain() {
        return grain;
    }

    public void setGrain(float grain) {
        this.grain = grain;
    }

    @Override
    public String toString() {
        return "W: "+wood+" | C: "+clay+" | I: "+iron+" | G: "+grain;
    }
}

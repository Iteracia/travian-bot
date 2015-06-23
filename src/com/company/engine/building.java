package com.company.engine;

public class building {
    private long timeLeft;
    private String name;
    private int level;
    private int slotId;
    private building(long timeV, String nameV, int levelV, int slotV){
        timeLeft = timeV;
        name = nameV;
        level = levelV;
        slotId = slotV;
    }
    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /*
                        ===Builder===
     */
    public static class Builder{
        private long timeLeft;
        private String name;
        private int level;
        private int slotId;
        public Builder timeLeft(String timeLeftInfo){
            //todo
            timeLeft = 0;
            return this;
        }
        public Builder name(String nameVal){
            name = nameVal;
            return this;
        }
        public Builder level(int levelVal){
            level = levelVal;
            return this;
        }
        public Builder slotId(int slotIdVal){
            slotId = slotIdVal;
            return this;
        }
        public building build(){
            return new building( timeLeft, name, level, slotId);
        }
    }
}

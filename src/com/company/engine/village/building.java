package com.company.engine.village;

public class building {
    private long timeLeftSecs;
    private String timeLeftStr;
    private String name;
    private int level;
    private int slotId;
    private building(long timeVS, String timeLeftVS, String nameV, int levelV, int slotV){
        timeLeftSecs = timeVS;
        timeLeftStr = timeLeftVS;
        name = nameV;
        level = levelV;
        slotId = slotV;
    }
    public String getTimeLeftStr() {
        return timeLeftStr;
    }

    public void setTimeLeftStr(String timeLeft) {
        this.timeLeftStr = timeLeft;
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

    public long getTimeLeftSecs() {
        return timeLeftSecs;
    }

    public void setTimeLeftSecs(long timeLeftSecs) {
        this.timeLeftSecs = timeLeftSecs;
    }

    /*
                        ===Builder===
     */
    public static class Builder{
        private String timeLeftStr;
        private long timeLeftSecs;
        private String name;
        private int level;
        private int slotId = -1;
        public static Builder ince(){
            return new Builder();
        }
        public Builder timeLeft(String timeLeftInfo){
            timeLeftStr = timeLeftInfo;
            String[] splited = timeLeftInfo.split(":");
            timeLeftSecs = Integer.parseInt( splited[0] )*360;
            timeLeftSecs += Integer.parseInt( splited[1] )*60;
            timeLeftSecs += Integer.parseInt( splited[2] );
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
            return new building( timeLeftSecs, timeLeftStr, name, level, slotId);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(" level ").append(level).append("| timeleft ").append(timeLeftStr).append(" inSeconds = ").append(timeLeftSecs).append("\n");
        return builder.toString();
    }
}

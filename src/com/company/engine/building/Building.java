package com.company.engine.building;

import com.company.engine.Engine;

import java.io.Serializable;

public class Building implements Serializable{
    private transient long timeLeftSecs;
    private transient String timeLeftStr;
    private boolean inProgress;
    private String name;
    private int level;
    private int slotId;
    private boolean freeSlot;
    private Building( long time, String timeStr, String name, int level, int slot, boolean inProgress ){
        timeLeftSecs = time;
        timeLeftStr = timeStr;
        this.name = name;
        this.level = level;
        this.slotId = slot;
        this.inProgress = inProgress;
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
        private boolean inProgress;
        public static Builder ince(){
            return new Builder();
        }
        private Builder(){
            inProgress = false;
            timeLeftSecs = -1;
            timeLeftStr = "";
        }
        public Builder timeLeft(String timeLeftInfo){
            timeLeftStr = timeLeftInfo;
            timeLeftSecs = Engine.timeToSecs( timeLeftInfo );
            inProgress = true;
            return this;
        }
        public Builder progress(boolean inProgress){
            this.inProgress = inProgress;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder level(int level){
            this.level = level;
            return this;
        }
        public Builder slotId(int slotId){
            this.slotId = slotId;
            return this;
        }
        public Building build(){
            return new Building( timeLeftSecs, timeLeftStr, name, level, slotId, inProgress);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (inProgress) {
            builder.append( name ).append( " level " ).append( level ).append( "  | timeleft " ).append( timeLeftStr ).append( " inSeconds = " ).append( timeLeftSecs ).append( "\n" );
        } else {
            builder.append( name ).append( " level " ).append( level ).append( "  | slotId " ).append( slotId ).append( "\n" );
        }
        return builder.toString();
    }
}

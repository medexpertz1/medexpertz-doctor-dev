package com.medexpertz.medexpertzdoctor.shankar.model;

public class DaysSelection {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private String id;
    private int pos;

    public DaysSelection(int pos, String id){
        this.id = id;
        this.pos = pos;
    }
}

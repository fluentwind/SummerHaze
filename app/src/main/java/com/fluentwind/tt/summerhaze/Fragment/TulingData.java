package com.fluentwind.tt.summerhaze.Fragment;

/**
 * Created by Administrator on 2017/2/25.
 */

public class TulingData {
    public final static int Left=1;
    public final static int Right=2;
    private String string;
    private int pos;
    public TulingData(String string,int pos){
        this.string=string;
        this.pos=pos;
    }

    public int getPos() {
        return pos;
    }

    public String getString() {
        return string;
    }
}

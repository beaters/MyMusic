package com.lxg.music.model;

import java.util.List;


public class ResponseMusicsListentity {
    private int r;
    private int version_max;
    private int is_show_quick_start;
    private List<MusicsListEntity> song;

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getVersion_max() {
        return version_max;
    }

    public void setVersion_max(int version_max) {
        this.version_max = version_max;
    }

    public int getIs_show_quick_start() {
        return is_show_quick_start;
    }

    public void setIs_show_quick_start(int is_show_quick_start) {
        this.is_show_quick_start = is_show_quick_start;
    }

    public List<MusicsListEntity> getSong() {
        return song;
    }

    public void setSong(List<MusicsListEntity> song) {
        this.song = song;
    }
}

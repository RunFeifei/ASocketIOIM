package com.run.im.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by PengFeifei on 2020/6/4.
 */
public class IMMessage extends LitePalSupport {

    private long formUserID;
    private long toUserID;
    private String text;
    private long timeStamp;

    public long getFormUserID() {
        return formUserID;
    }

    public void setFormUserID(long formUserID) {
        this.formUserID = formUserID;
    }

    public long getToUserID() {
        return toUserID;
    }

    public void setToUserID(long toUserID) {
        this.toUserID = toUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

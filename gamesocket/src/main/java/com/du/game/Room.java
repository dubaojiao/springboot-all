package com.du.game;

import java.io.Serializable;
import java.net.Socket;

public class Room implements Serializable {

    private String pwd;
    private Integer state;
    private Socket one;
    private String oneName;
    private Integer oneDirection;
    private int[][] onePosition;
    private Socket tow;
    private String towName;
    private Integer towDirection;
    private int[][] towPosition;
    private boolean flag;
    private int[][] spots;
    private int width;
    private int height;

    public int[][] getSpots() {
        return spots;
    }

    public void setSpots(int[][] spots) {
        this.spots = spots;
    }

    public int[][] getOnePosition() {
        return onePosition;
    }

    public void setOnePosition(int[][] onePosition) {
        this.onePosition = onePosition;
    }

    public int[][] getTowPosition() {
        return towPosition;
    }

    public void setTowPosition(int[][] towPosition) {
        this.towPosition = towPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Integer getOneDirection() {
        return oneDirection;
    }

    public void setOneDirection(Integer oneDirection) {
        this.oneDirection = oneDirection;
    }

    public Integer getTowDirection() {
        return towDirection;
    }

    public void setTowDirection(Integer towDirection) {
        this.towDirection = towDirection;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getOneName() {
        return oneName;
    }

    public void setOneName(String oneName) {
        this.oneName = oneName;
    }

    public String getTowName() {
        return towName;
    }

    public void setTowName(String towName) {
        this.towName = towName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Socket getOne() {
        return one;
    }

    public void setOne(Socket one) {
        this.one = one;
    }

    public Socket getTow() {
        return tow;
    }

    public void setTow(Socket tow) {
        this.tow = tow;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

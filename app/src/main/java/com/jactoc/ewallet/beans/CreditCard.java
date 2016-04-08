package com.jactoc.ewallet.beans;

import java.io.Serializable;

/**
 * Created by jactoc on 2016-03-19.
 */
public class CreditCard implements Serializable {

    private String name;
    private String number;
    private int expiration_date;
    private int ccv;
    private int status;
    private String picture;
    private String color;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public int getExpiration_date() {
        return expiration_date;
    }
    public void setExpiration_date(int expiration_date) {
        this.expiration_date = expiration_date;
    }
    public int getCcv() {
        return ccv;
    }
    public void setCcv(int ccv) {
        this.ccv = ccv;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getFileLocation() {
        String numberInString = String.valueOf(number);
        numberInString = numberInString.substring(numberInString.length()-4, numberInString.length());
        return numberInString;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

} //end
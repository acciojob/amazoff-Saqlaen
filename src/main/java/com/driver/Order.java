package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String timeInStr;

    public String getTimeInStr() {
        return timeInStr;
    }

    public void setTimeInStr(String timeInStr) {
        this.timeInStr = timeInStr;
    }

    public Order(String id, String deliveryTime) {
        
        this.id = id;
        this.timeInStr = deliveryTime;
        String HH = deliveryTime.substring(0, 2);
        String MM =  deliveryTime.substring(3);
        this.deliveryTime = Integer.parseInt(HH) * 60 + Integer.parseInt( MM );
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    @Override
    public String toString() {
        return "Order [id=" + id + ", deliveryTime=" + deliveryTime + ", timeInStr=" + timeInStr + "]";
    }

    
}

package com.renxl.realmall.feature.orders;

import com.renxl.realmall.feature.category.Wares;

import java.util.List;

/**
 * Created by renxl
 * On 2017/5/21 15:34.
 */

public class CompleteOrder {

    private long id;
    private int amount;
    private int status;
    private long userId;
    private String orderNum;
    private String createdTime;
    private Address address;
    private List<OrderWare> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderWare> getItems() {
        return items;
    }

    public void setItems(List<OrderWare> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    class OrderWare {
        private Wares wares;
        private long id;
        private long orderId;
        private long ware_id;

        public Wares getWares() {
            return wares;
        }

        public void setWares(Wares wares) {
            wares = wares;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public long getWare_id() {
            return ware_id;
        }

        public void setWare_id(long ware_id) {
            this.ware_id = ware_id;
        }

        @Override
        public String toString() {
            return "OrderWare{" +
                    "wares=" + wares.toString() +
                    ", id=" + id +
                    ", orderId=" + orderId +
                    ", ware_id=" + ware_id +
                    '}';
        }
    }
}

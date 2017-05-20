package com.renxl.realmall.feature.orders;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by renxl
 * On 2017/5/20 13:53.
 */

public class Address implements Parcelable, Comparable<Address> {


    /**
     * id : 731
     * userId : 274556
     * consignee : Ren
     * phone : 15632279666
     * addr : 北京市北京市海淀区 北京大学
     * zipCode : 100000
     * isDefault : false
     */

    private long id;
    private int userId;
    private String consignee;
    private String phone;
    private String addr;
    private String zipCode;
    private boolean isDefault;

    protected Address(Parcel in) {
        id = in.readLong();
        userId = in.readInt();
        consignee = in.readString();
        phone = in.readString();
        addr = in.readString();
        zipCode = in.readString();
        isDefault = in.readByte() != 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(userId);
        dest.writeString(consignee);
        dest.writeString(phone);
        dest.writeString(addr);
        dest.writeString(zipCode);
        dest.writeByte((byte) (isDefault ? 1 : 0));
    }

    @Override
    public int compareTo(@NonNull Address another) {
        return this.isIsDefault() ? -1 : 1;
    }
}

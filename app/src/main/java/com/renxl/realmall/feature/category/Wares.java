package com.renxl.realmall.feature.category;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renxl
 * On 2017/4/18 14:22.
 */

public class Wares implements Parcelable {


    /**
     * id : 12
     * categoryId : 1
     * campaignId : 6
     * name : 希捷（seagate）Expansion 新睿翼1TB 2.5英寸 USB3.0 移动硬盘 (STEA1000400)
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5519fdd0N02706f5e.jpg
     * price : 399
     * sale : 402
     */

    protected long id;
    private int categoryId;
    private int campaignId;
    protected String name;
    protected String imgUrl;
    protected double price;
    private double sale;

    public Wares() {
    }

    protected Wares(Parcel in) {
        id = in.readLong();
        categoryId = in.readInt();
        campaignId = in.readInt();
        name = in.readString();
        imgUrl = in.readString();
        price = in.readDouble();
        sale = in.readDouble();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Wares{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", campaignId=" + campaignId +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", sale=" + sale +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(categoryId);
        dest.writeInt(campaignId);
        dest.writeString(name);
        dest.writeString(imgUrl);
        dest.writeDouble(price);
        dest.writeDouble(sale);
    }

    public static final Creator<Wares> CREATOR = new Creator<Wares>() {
        @Override
        public Wares createFromParcel(Parcel in) {
            return new Wares(in);
        }

        @Override
        public Wares[] newArray(int size) {
            return new Wares[size];
        }
    };
}

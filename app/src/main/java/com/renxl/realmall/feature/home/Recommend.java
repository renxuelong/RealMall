package com.renxl.realmall.feature.home;

/**
 * Created by renxl
 * On 2017/4/13 9:22.
 */

public class Recommend {

    /**
     * cpOne : {"id":17,"title":"手机专享","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg"}
     * cpTwo : {"id":15,"title":"闪购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg"}
     * cpThree : {"id":11,"title":"团购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg"}
     * id : 1
     * title : 超值购
     * campaignOne : 17
     * campaignTwo : 15
     * campaignThree : 11
     */

    private CpOneBean cpOne;
    private CpTwoBean cpTwo;
    private CpThreeBean cpThree;
    private long id;
    private String title;
    private String campaignOne;
    private String campaignTwo;
    private String campaignThree;

    public CpOneBean getCpOne() {
        return cpOne;
    }

    public void setCpOne(CpOneBean cpOne) {
        this.cpOne = cpOne;
    }

    public CpTwoBean getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(CpTwoBean cpTwo) {
        this.cpTwo = cpTwo;
    }

    public CpThreeBean getCpThree() {
        return cpThree;
    }

    public void setCpThree(CpThreeBean cpThree) {
        this.cpThree = cpThree;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCampaignOne() {
        return campaignOne;
    }

    public void setCampaignOne(String campaignOne) {
        this.campaignOne = campaignOne;
    }

    public String getCampaignTwo() {
        return campaignTwo;
    }

    public void setCampaignTwo(String campaignTwo) {
        this.campaignTwo = campaignTwo;
    }

    public String getCampaignThree() {
        return campaignThree;
    }

    public void setCampaignThree(String campaignThree) {
        this.campaignThree = campaignThree;
    }

    public static class CpOneBean {
        /**
         * id : 17
         * title : 手机专享
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg
         */

        private int id;
        private String title;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class CpTwoBean {
        /**
         * id : 15
         * title : 闪购
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg
         */

        private int id;
        private String title;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class CpThreeBean {
        /**
         * id : 11
         * title : 团购
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg
         */

        private int id;
        private String title;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}

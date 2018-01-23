package com.yzx.xiaomusic.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yzx on 2018/1/23.
 * Description 音乐地址类
 */

public class MusicAddress extends BaseResposeBody {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 483671599
         * url : https://m7.music.126.net/20180123141140/878aab97aa129d03ee8f1ecd31593db5/ymusic/98aa/a01b/2293/d7b8beba3dba7f2aeb4dd4c87190663c.mp3
         * br : 192000
         * size : 5661301
         * md5 : d7b8beba3dba7f2aeb4dd4c87190663c
         * code : 200
         * expi : 1200
         * type : mp3
         * gain : -1.46
         * fee : 0
         * uf : null
         * payed : 0
         * flag : 0
         * canExtend : false
         */

        private int id;
        private String url;
        private int br;
        private int size;
        private String md5;
        @SerializedName("code")
        private int codeX;
        private int expi;
        private String type;
        private double gain;
        private int fee;
        private Object uf;
        private int payed;
        private int flag;
        private boolean canExtend;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getCodeX() {
            return codeX;
        }

        public void setCodeX(int codeX) {
            this.codeX = codeX;
        }

        public int getExpi() {
            return expi;
        }

        public void setExpi(int expi) {
            this.expi = expi;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getGain() {
            return gain;
        }

        public void setGain(double gain) {
            this.gain = gain;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public Object getUf() {
            return uf;
        }

        public void setUf(Object uf) {
            this.uf = uf;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public boolean isCanExtend() {
            return canExtend;
        }

        public void setCanExtend(boolean canExtend) {
            this.canExtend = canExtend;
        }
    }
}

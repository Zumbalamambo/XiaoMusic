package com.yzx.xiaomusic.entities;

/**
 * Created by yzx on 2018/2/5.
 * Description
 */

public class Lyric extends BaseResposeBody {

    /**
     * sgc : false
     * sfy : false
     * qfy : false
     * lrc : {"version":10,"lyric":"[00:00.00] 作曲 : 马敬\n[00:01.00] 作词 : 唐恬\n[00:09.320]编曲 : 黎偌天\n[00:24.650]如果说你是海上的烟火\n[00:29.640]我是浪花的泡沫\n[00:33.200]某一刻你的光照亮了我\n[00:38.080]如果说你是遥远的星河\n[00:42.960]耀眼得让人想哭\n[00:48.450]我是追逐着你的眼眸\n[00:51.600]总在孤单时候眺望夜空\n[01:00.880]我可以跟在你身后\n[01:04.130]像影子追着光梦游\n[01:07.530]我可以等在这路口\n[01:10.870]不管你会不会经过\n[01:14.300]每当我为你抬起头\n[01:17.600]连眼泪都觉得自由\n[01:20.680]有的爱像阳光倾落边拥有边失去着\n[01:41.300]如果说你是夏夜的萤火\n[01:46.220]孩子们为你唱歌\n[01:49.930]那么我是想要画你的手\n[01:54.640]你看我多么渺小一个我\n[01:59.510]因为你有梦可做\n[02:05.120]也许你不会为我停留\n[02:08.360]那就让我站在你的背后\n[02:14.290]我可以跟在你身后\n[02:17.460]像影子追着光梦游\n[02:20.770]我可以等在这路口\n[02:24.110]不管你会不会经过\n[02:27.490]每当我为你抬起头\n[02:30.840]连眼泪都觉得自由\n[02:34.520]有的爱像大雨滂沱却依然相信彩虹\n[03:07.590]我可以跟在你身后\n[03:10.870]像影子追着光梦游\n[03:14.440]我可以等在这路口\n[03:17.580]不管你会不会经过\n[03:20.890]每当我为你抬起头\n[03:24.130]连眼泪都觉得自由\n[03:27.760]有的爱像大雨滂沱却依然相信彩虹\n[03:37.010]\n[03:37.110]制作人 : 黎偌天\n[03:37.420]吉他 : 劳国贤\n[03:37.700]弦乐 : 国际首席爱乐乐团\n[03:38.050]Bass : 大宇\n[03:38.460]监制 : 宋鹏飞\n[03:38.800]音乐出品发行公司 : 听见时代传媒\n"}
     * klyric : {"version":0,"lyric":null}
     * tlyric : {"version":0,"lyric":null}
     */

    private boolean sgc;
    private boolean sfy;
    private boolean qfy;
    private LrcBean lrc;
    private KlyricBean klyric;
    private TlyricBean tlyric;

    public boolean isSgc() {
        return sgc;
    }

    public void setSgc(boolean sgc) {
        this.sgc = sgc;
    }

    public boolean isSfy() {
        return sfy;
    }

    public void setSfy(boolean sfy) {
        this.sfy = sfy;
    }

    public boolean isQfy() {
        return qfy;
    }

    public void setQfy(boolean qfy) {
        this.qfy = qfy;
    }

    public LrcBean getLrc() {
        return lrc;
    }

    public void setLrc(LrcBean lrc) {
        this.lrc = lrc;
    }

    public KlyricBean getKlyric() {
        return klyric;
    }

    public void setKlyric(KlyricBean klyric) {
        this.klyric = klyric;
    }

    public TlyricBean getTlyric() {
        return tlyric;
    }

    public void setTlyric(TlyricBean tlyric) {
        this.tlyric = tlyric;
    }

    public static class LrcBean {
        /**
         * version : 10
         * lyric : [00:00.00] 作曲 : 马敬
         [00:01.00] 作词 : 唐恬
         [00:09.320]编曲 : 黎偌天
         [00:24.650]如果说你是海上的烟火
         [00:29.640]我是浪花的泡沫
         [00:33.200]某一刻你的光照亮了我
         [00:38.080]如果说你是遥远的星河
         [00:42.960]耀眼得让人想哭
         [00:48.450]我是追逐着你的眼眸
         [00:51.600]总在孤单时候眺望夜空
         [01:00.880]我可以跟在你身后
         [01:04.130]像影子追着光梦游
         [01:07.530]我可以等在这路口
         [01:10.870]不管你会不会经过
         [01:14.300]每当我为你抬起头
         [01:17.600]连眼泪都觉得自由
         [01:20.680]有的爱像阳光倾落边拥有边失去着
         [01:41.300]如果说你是夏夜的萤火
         [01:46.220]孩子们为你唱歌
         [01:49.930]那么我是想要画你的手
         [01:54.640]你看我多么渺小一个我
         [01:59.510]因为你有梦可做
         [02:05.120]也许你不会为我停留
         [02:08.360]那就让我站在你的背后
         [02:14.290]我可以跟在你身后
         [02:17.460]像影子追着光梦游
         [02:20.770]我可以等在这路口
         [02:24.110]不管你会不会经过
         [02:27.490]每当我为你抬起头
         [02:30.840]连眼泪都觉得自由
         [02:34.520]有的爱像大雨滂沱却依然相信彩虹
         [03:07.590]我可以跟在你身后
         [03:10.870]像影子追着光梦游
         [03:14.440]我可以等在这路口
         [03:17.580]不管你会不会经过
         [03:20.890]每当我为你抬起头
         [03:24.130]连眼泪都觉得自由
         [03:27.760]有的爱像大雨滂沱却依然相信彩虹
         [03:37.010]
         [03:37.110]制作人 : 黎偌天
         [03:37.420]吉他 : 劳国贤
         [03:37.700]弦乐 : 国际首席爱乐乐团
         [03:38.050]Bass : 大宇
         [03:38.460]监制 : 宋鹏飞
         [03:38.800]音乐出品发行公司 : 听见时代传媒

         */

        private int version;
        private String lyric;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }
    }

    public static class KlyricBean {
        /**
         * version : 0
         * lyric : null
         */

        private int version;
        private Object lyric;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public Object getLyric() {
            return lyric;
        }

        public void setLyric(Object lyric) {
            this.lyric = lyric;
        }
    }

    public static class TlyricBean {
        /**
         * version : 0
         * lyric : null
         */

        private int version;
        private Object lyric;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public Object getLyric() {
            return lyric;
        }

        public void setLyric(Object lyric) {
            this.lyric = lyric;
        }
    }
}

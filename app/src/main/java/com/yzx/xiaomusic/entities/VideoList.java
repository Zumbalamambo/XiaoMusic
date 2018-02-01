package com.yzx.xiaomusic.entities;

import java.util.List;

/**
 * Created by yzx on 2018/1/30.
 * Description
 */

public class VideoList extends BaseResposeBody {


    /**
     * updateTime : 1517284113643
     * data : [{"id":5779389,"cover":"http://p4.music.126.net/XxoxzEU_U50D0tzVvx4u-Q==/109951163099908165.jpg","name":"体面  电影《前任3：再见前任》插曲","playCount":13025660,"briefDesc":"电影《前任3：再见前任》插曲","desc":null,"artistName":"于文文","artistId":816248,"duration":0,"mark":0,"lastRank":1,"score":364366,"subed":false,"artists":[{"id":816248,"name":"于文文"}]},{"id":5741462,"cover":"http://p4.music.126.net/8SDmin8tf7BrvARpvWuJcA==/109951163081516042.jpg","name":"说散就散","playCount":10117244,"briefDesc":"袁娅维献声电影《前任3：再见前任》","desc":null,"artistName":"袁娅维","artistId":10473,"duration":0,"mark":0,"lastRank":2,"score":126207,"subed":false,"artists":[{"id":10473,"name":"袁娅维"}]},{"id":5809231,"cover":"http://p3.music.126.net/KG_kO4YgfrGu8O4QLR2B-Q==/109951163117910407.jpg","name":"在家乡","playCount":527124,"briefDesc":"李剑青最新MV《在家乡》","desc":null,"artistName":"李剑青","artistId":861282,"duration":0,"mark":0,"lastRank":3,"score":59201,"subed":false,"artists":[{"id":861282,"name":"李剑青"}]},{"id":5780008,"cover":"http://p4.music.126.net/2y1_otEw3ACQmLj0qTGFow==/109951163111403192.jpg","name":"像风一样","playCount":935064,"briefDesc":"薛之谦新专辑收录曲《像风一样》MV公开！","desc":null,"artistName":"薛之谦","artistId":5781,"duration":0,"mark":0,"lastRank":4,"score":51888,"subed":false,"artists":[{"id":5781,"name":"薛之谦"}]},{"id":5782021,"cover":"http://p3.music.126.net/2cZibmZn4xrw4WTErfwwJw==/109951163089540795.jpg","name":"再见前任","playCount":1672054,"briefDesc":"电影《前任3：再见前任》宣传曲","desc":null,"artistName":"冯提莫","artistId":12107534,"duration":0,"mark":0,"lastRank":7,"score":32202,"subed":false,"artists":[{"id":12107534,"name":"冯提莫"}]},{"id":5741567,"cover":"http://p3.music.126.net/PV3wP-acLdsnYh1PPS-ZOw==/109951163083437976.jpg","name":"说散就散","playCount":2757804,"briefDesc":"电影《前任3：再见前任》主题曲","desc":null,"artistName":"艾福杰尼","artistId":12127564,"duration":0,"mark":0,"lastRank":5,"score":28419,"subed":false,"artists":[{"id":12127564,"name":"艾福杰尼"},{"id":12065096,"name":"BooM黄旭"},{"id":12931942,"name":"田羽生"}]},{"id":5809143,"cover":"http://p3.music.126.net/NyBC24pquwcyIpV79oE3wQ==/109951163112701965.jpg","name":"十九岁","playCount":759956,"briefDesc":"赵雷带你回到美好的十九岁！","desc":null,"artistName":"赵雷","artistId":6731,"duration":0,"mark":0,"lastRank":6,"score":26520,"subed":false,"artists":[{"id":6731,"name":"赵雷"}]},{"id":5793038,"cover":"http://p4.music.126.net/gfjUoi0Le9I8qVE8_AiJ3A==/109951163094351283.jpg","name":"奴隶","playCount":1685261,"briefDesc":"Jony J最新MV《奴隶》","desc":null,"artistName":"Jony J","artistId":784257,"duration":0,"mark":0,"lastRank":9,"score":21294,"subed":false,"artists":[{"id":784257,"name":"Jony J"}]},{"id":5779261,"cover":"http://p4.music.126.net/gt0Cdxr3ccQPW9CfKN8mLg==/109951163095327407.jpg","name":"Stage 第02期 后海大鲨鱼 城市早班车","playCount":2171228,"briefDesc":null,"desc":null,"artistName":"后海大鲨鱼","artistId":11760,"duration":0,"mark":0,"lastRank":8,"score":21022,"subed":false,"artists":[{"id":11760,"name":"后海大鲨鱼"}]},{"id":5779390,"cover":"http://p3.music.126.net/3tJEvfpLHjM1zUic28Tp1w==/109951163099925745.jpg","name":"骆驼","playCount":917670,"briefDesc":"薛之谦最新MV《骆驼》","desc":null,"artistName":"薛之谦","artistId":5781,"duration":0,"mark":0,"lastRank":10,"score":16474,"subed":false,"artists":[{"id":5781,"name":"薛之谦"}]}]
     * hasMore : true
     */

    private long updateTime;
    private boolean hasMore;
    private List<DataBean> data;

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5779389
         * cover : http://p4.music.126.net/XxoxzEU_U50D0tzVvx4u-Q==/109951163099908165.jpg
         * name : 体面  电影《前任3：再见前任》插曲
         * playCount : 13025660
         * briefDesc : 电影《前任3：再见前任》插曲
         * desc : null
         * artistName : 于文文
         * artistId : 816248
         * duration : 0
         * mark : 0
         * lastRank : 1
         * score : 364366
         * subed : false
         * artists : [{"id":816248,"name":"于文文"}]
         */

        private int id;
        private String cover;
        private String name;
        private int playCount;
        private String briefDesc;
        private Object desc;
        private String artistName;
        private int artistId;
        private int duration;
        private int mark;
        private int lastRank;
        private int score;
        private boolean subed;
        private List<ArtistsBean> artists;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public String getBriefDesc() {
            return briefDesc;
        }

        public void setBriefDesc(String briefDesc) {
            this.briefDesc = briefDesc;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public int getArtistId() {
            return artistId;
        }

        public void setArtistId(int artistId) {
            this.artistId = artistId;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public int getLastRank() {
            return lastRank;
        }

        public void setLastRank(int lastRank) {
            this.lastRank = lastRank;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public boolean isSubed() {
            return subed;
        }

        public void setSubed(boolean subed) {
            this.subed = subed;
        }

        public List<ArtistsBean> getArtists() {
            return artists;
        }

        public void setArtists(List<ArtistsBean> artists) {
            this.artists = artists;
        }

        public static class ArtistsBean {
            /**
             * id : 816248
             * name : 于文文
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

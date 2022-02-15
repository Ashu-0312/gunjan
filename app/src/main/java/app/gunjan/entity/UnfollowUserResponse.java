package app.gunjan.entity;

import java.io.Serializable;

public class UnfollowUserResponse implements Serializable {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * code : 1
     * message : Removed from following list
     * data : {"following_count":0,"follower_count":0}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public int getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(int following_count) {
            this.following_count = following_count;
        }

        public int getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        /**
         * following_count : 0
         * follower_count : 0
         */

        private int following_count;
        private int follower_count;
    }
}

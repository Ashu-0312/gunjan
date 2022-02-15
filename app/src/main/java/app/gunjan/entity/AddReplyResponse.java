package app.gunjan.entity;

import java.io.Serializable;

public class AddReplyResponse implements Serializable {

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
     * message : Reply added
     * data : {"id":3,"userId":8,"commentId":28,"commentType":"text","message":"good post","image":"","video":"","total_like":0,"total_unlike":0,"like_type":"","createdAt":"2022-02-15T07:18:57.000Z"}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        /**
         * id : 3
         * userId : 8
         * commentId : 28
         * commentType : text
         * message : good post
         * image :
         * video :
         * total_like : 0
         * total_unlike : 0
         * like_type :
         * createdAt : 2022-02-15T07:18:57.000Z
         */

        private int id;
        private int userId;
        private int commentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getCommentType() {
            return commentType;
        }

        public void setCommentType(String commentType) {
            this.commentType = commentType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getTotal_like() {
            return total_like;
        }

        public void setTotal_like(int total_like) {
            this.total_like = total_like;
        }

        public int getTotal_unlike() {
            return total_unlike;
        }

        public void setTotal_unlike(int total_unlike) {
            this.total_unlike = total_unlike;
        }

        public String getLike_type() {
            return like_type;
        }

        public void setLike_type(String like_type) {
            this.like_type = like_type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        private String commentType;
        private String message;
        private String image;
        private String video;
        private int total_like;
        private int total_unlike;
        private String like_type;
        private String createdAt;
    }
}

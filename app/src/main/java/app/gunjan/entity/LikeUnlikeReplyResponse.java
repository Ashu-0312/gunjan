package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class LikeUnlikeReplyResponse implements Serializable {

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
     * message : like on comment changed!
     * data : {"replyId":"2","commentType":"text","message":"good post","image":"","video":"","total_like":1,"total_unlike":0,"createdAt":"2022-01-19T14:26:02.000Z","isLiked":true,"like_type":["love"],"my_liked_type":"love"}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isLiked() {
            return isLiked;
        }

        public void setLiked(boolean liked) {
            isLiked = liked;
        }

        public String getMy_liked_type() {
            return my_liked_type;
        }

        public void setMy_liked_type(String my_liked_type) {
            this.my_liked_type = my_liked_type;
        }

        public List<String> getLike_type() {
            return like_type;
        }

        public void setLike_type(List<String> like_type) {
            this.like_type = like_type;
        }

        /**
         * replyId : 2
         * commentType : text
         * message : good post
         * image :
         * video :
         * total_like : 1
         * total_unlike : 0
         * createdAt : 2022-01-19T14:26:02.000Z
         * isLiked : true
         * like_type : ["love"]
         * my_liked_type : love
         */

        private String replyId;
        private String commentType;
        private String message;
        private String image;
        private String video;
        private int total_like;
        private int total_unlike;
        private String createdAt;
        private boolean isLiked;
        private String my_liked_type;
        private List<String> like_type;
    }
}

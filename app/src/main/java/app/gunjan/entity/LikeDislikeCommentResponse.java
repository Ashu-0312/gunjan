package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class LikeDislikeCommentResponse implements Serializable {


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
     * message : Comment liked!
     * data : {"commentType":"video","message":"good post new","image":"","video":"url","commented_by":{"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111122","countryCode":"91","gender":"Male","dob":"2020-03-02","image":"url","email":"test@test.com","profile_stage":"5","active":true,"about":"About","active_community":2},"total_like":2,"total_unlike":0,"createdAt":"2022-01-13T14:18:01.000Z","isLiked":true,"like_type":["love"],"my_liked_type":"love"}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
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

        public CommentedByBean getCommented_by() {
            return commented_by;
        }

        public void setCommented_by(CommentedByBean commented_by) {
            this.commented_by = commented_by;
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
         * commentType : video
         * message : good post new
         * image :
         * video : url
         * commented_by : {"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111122","countryCode":"91","gender":"Male","dob":"2020-03-02","image":"url","email":"test@test.com","profile_stage":"5","active":true,"about":"About","active_community":2}
         * total_like : 2
         * total_unlike : 0
         * createdAt : 2022-01-13T14:18:01.000Z
         * isLiked : true
         * like_type : ["love"]
         * my_liked_type : love
         */

        private String commentType;
        private String message;
        private String image;
        private String video;
        private CommentedByBean commented_by;
        private int total_like;
        private int total_unlike;
        private String createdAt;
        private boolean isLiked;
        private String my_liked_type;
        private List<String> like_type;

        public static class CommentedByBean implements Serializable {
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getProfile_stage() {
                return profile_stage;
            }

            public void setProfile_stage(String profile_stage) {
                this.profile_stage = profile_stage;
            }

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public int getActive_community() {
                return active_community;
            }

            public void setActive_community(int active_community) {
                this.active_community = active_community;
            }

            /**
             * id : 2
             * first_name : first name
             * last_name : last name
             * pincode : 121212
             * mobile : 11111122
             * countryCode : 91
             * gender : Male
             * dob : 2020-03-02
             * image : url
             * email : test@test.com
             * profile_stage : 5
             * active : true
             * about : About
             * active_community : 2
             */

            private int id;
            private String first_name;
            private String last_name;
            private String pincode;
            private String mobile;
            private String countryCode;
            private String gender;
            private String dob;
            private String image;
            private String email;
            private String profile_stage;
            private boolean active;
            private String about;
            private int active_community;
        }
    }
}

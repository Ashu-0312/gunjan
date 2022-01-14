package app.gunjan.entity;

import java.io.Serializable;

public class AddCommentResponse implements Serializable {

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
     * message : Comments on post saved successfully!
     * data : {"id":4,"commented_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"createdAt":"2022-01-14T11:24:24.000Z","commentType":"text","message":"good post","image":"","video":""}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public CommentedByBean getCommented_by() {
            return commented_by;
        }

        public void setCommented_by(CommentedByBean commented_by) {
            this.commented_by = commented_by;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        /**
         * id : 4
         * commented_by : {"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16}
         * createdAt : 2022-01-14T11:24:24.000Z
         * commentType : text
         * message : good post
         * image :
         * video :
         */

        private int id;
        private CommentedByBean commented_by;
        private String createdAt;
        private String commentType;
        private String message;
        private String image;
        private String video;

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
             * id : 8
             * first_name : first name
             * last_name : last name
             * pincode : 121212
             * mobile : 2222222222
             * countryCode : +91
             * gender : Male
             * dob : 1995-03-02
             * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg
             * email : testt@test.com
             * profile_stage : 5
             * active : true
             * about : dghhtff
             * active_community : 16
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

package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class ReplyListResponse implements Serializable {

    /**
     * code : 1
     * message : Reply list
     * data : {"reply_list":[{"id":4,"commentType":"text","message":"thanks","image":"","video":"","replied_by":{"id":8,"first_name":"Test","last_name":"Test","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"created_at_date":"15-02-2022 | 8:16:9","createdAt":"2022-02-15T08:16:09.000Z","total_like":0,"total_unlike":0,"like_type":[],"isLiked":"","my_liked_type":""},{"id":3,"commentType":"text","message":"good post","image":"","video":"","replied_by":{"id":8,"first_name":"Test","last_name":"Test","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"created_at_date":"15-02-2022 | 7:18:57","createdAt":"2022-02-15T07:18:57.000Z","total_like":1,"total_unlike":0,"like_type":["love"],"isLiked":"1","my_liked_type":"love"}]}
     */

    private int code;

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

    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<ReplyListBean> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<ReplyListBean> reply_list) {
            this.reply_list = reply_list;
        }

        private List<ReplyListBean> reply_list;

        public static class ReplyListBean implements Serializable {
            /**
             * id : 4
             * commentType : text
             * message : thanks
             * image :
             * video :
             * replied_by : {"id":8,"first_name":"Test","last_name":"Test","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16}
             * created_at_date : 15-02-2022 | 8:16:9
             * createdAt : 2022-02-15T08:16:09.000Z
             * total_like : 0
             * total_unlike : 0
             * like_type : []
             * isLiked :
             * my_liked_type :
             */

            private int id;
            private String commentType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public RepliedByBean getReplied_by() {
                return replied_by;
            }

            public void setReplied_by(RepliedByBean replied_by) {
                this.replied_by = replied_by;
            }

            public String getCreated_at_date() {
                return created_at_date;
            }

            public void setCreated_at_date(String created_at_date) {
                this.created_at_date = created_at_date;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
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

            public String getIsLiked() {
                return isLiked;
            }

            public void setIsLiked(String isLiked) {
                this.isLiked = isLiked;
            }

            public String getMy_liked_type() {
                return my_liked_type;
            }

            public void setMy_liked_type(String my_liked_type) {
                this.my_liked_type = my_liked_type;
            }

            public List<?> getLike_type() {
                return like_type;
            }

            public void setLike_type(List<?> like_type) {
                this.like_type = like_type;
            }

            private String message;
            private String image;
            private String video;
            private RepliedByBean replied_by;
            private String created_at_date;
            private String createdAt;
            private int total_like;
            private int total_unlike;
            private String isLiked;
            private String my_liked_type;
            private List<?> like_type;

            public static class RepliedByBean implements Serializable {
                /**
                 * id : 8
                 * first_name : Test
                 * last_name : Test
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
}

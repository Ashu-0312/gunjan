package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class OtherUserDetailsResponse implements Serializable {

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
     * message : User details
     * data : {"user":{"id":18,"first_name":"Fgtdfgg","last_name":"Dvrscby","pincode":"8522","mobile":"8888888889","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg","email":null,"profile_stage":"5","active":true,"about":"ctf gdcff","active_community":16},"post_list":[{"id":8,"userId":18,"title":"","description":"fdjfjffjfjfcmfmfm","file":"","file_width":null,"file_height":null,"feed_type":"post","content_type":"text","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"like_type":[],"my_liked_type":"","isLiked":false,"isCommented":false,"created_by":{"id":18,"first_name":"Fgtdfgg","last_name":"Dvrscby","pincode":"8522","mobile":"8888888889","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg","email":null,"profile_stage":"5","active":true,"about":"ctf gdcff","active_community":16},"createdAt":"2022-01-18T06:34:34.000Z","lastCommentAt":"2022-01-18T06:34:34.000Z"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<PostListBean> getPost_list() {
            return post_list;
        }

        public void setPost_list(List<PostListBean> post_list) {
            this.post_list = post_list;
        }

        /**
         * user : {"id":18,"first_name":"Fgtdfgg","last_name":"Dvrscby","pincode":"8522","mobile":"8888888889","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg","email":null,"profile_stage":"5","active":true,"about":"ctf gdcff","active_community":16}
         * post_list : [{"id":8,"userId":18,"title":"","description":"fdjfjffjfjfcmfmfm","file":"","file_width":null,"file_height":null,"feed_type":"post","content_type":"text","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"like_type":[],"my_liked_type":"","isLiked":false,"isCommented":false,"created_by":{"id":18,"first_name":"Fgtdfgg","last_name":"Dvrscby","pincode":"8522","mobile":"8888888889","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg","email":null,"profile_stage":"5","active":true,"about":"ctf gdcff","active_community":16},"createdAt":"2022-01-18T06:34:34.000Z","lastCommentAt":"2022-01-18T06:34:34.000Z"}]
         */

        private UserBean user;
        private List<PostListBean> post_list;
        private String follower_count;
        private String following_count;

        public Boolean getFollowing_this_user() {
            return following_this_user;
        }

        public void setFollowing_this_user(Boolean following_this_user) {
            this.following_this_user = following_this_user;
        }

        private Boolean following_this_user;

        public String getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(String follower_count) {
            this.follower_count = follower_count;
        }

        public String getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(String following_count) {
            this.following_count = following_count;
        }


        public static class UserBean implements Serializable {
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

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public Object getDob() {
                return dob;
            }

            public void setDob(Object dob) {
                this.dob = dob;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
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
             * id : 18
             * first_name : Fgtdfgg
             * last_name : Dvrscby
             * pincode : 8522
             * mobile : 8888888889
             * countryCode : +91
             * gender : null
             * dob : null
             * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg
             * email : null
             * profile_stage : 5
             * active : true
             * about : ctf gdcff
             * active_community : 16
             */

            private int id;
            private String first_name;
            private String last_name;
            private String pincode;
            private String mobile;
            private String countryCode;
            private Object gender;
            private Object dob;
            private String image;
            private Object email;
            private String profile_stage;
            private boolean active;
            private String about;

            public String getTotal_available_coins() {
                return total_available_coins;
            }

            public void setTotal_available_coins(String total_available_coins) {
                this.total_available_coins = total_available_coins;
            }

            private String total_available_coins;
            private int active_community;
        }

        public static class PostListBean implements Serializable {
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public Object getFile_width() {
                return file_width;
            }

            public void setFile_width(Object file_width) {
                this.file_width = file_width;
            }

            public Object getFile_height() {
                return file_height;
            }

            public void setFile_height(Object file_height) {
                this.file_height = file_height;
            }

            public String getFeed_type() {
                return feed_type;
            }

            public void setFeed_type(String feed_type) {
                this.feed_type = feed_type;
            }

            public String getContent_type() {
                return content_type;
            }

            public void setContent_type(String content_type) {
                this.content_type = content_type;
            }

            public String getPrivacy() {
                return privacy;
            }

            public void setPrivacy(String privacy) {
                this.privacy = privacy;
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

            public int getTotal_comment() {
                return total_comment;
            }

            public void setTotal_comment(int total_comment) {
                this.total_comment = total_comment;
            }

            public String getMy_liked_type() {
                return my_liked_type;
            }

            public void setMy_liked_type(String my_liked_type) {
                this.my_liked_type = my_liked_type;
            }

            public boolean isLiked() {
                return isLiked;
            }

            public void setLiked(boolean liked) {
                isLiked = liked;
            }

            public boolean isCommented() {
                return isCommented;
            }

            public void setCommented(boolean commented) {
                isCommented = commented;
            }

            public CreatedByBean getCreated_by() {
                return created_by;
            }

            public void setCreated_by(CreatedByBean created_by) {
                this.created_by = created_by;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getLastCommentAt() {
                return lastCommentAt;
            }

            public void setLastCommentAt(String lastCommentAt) {
                this.lastCommentAt = lastCommentAt;
            }

            public List<?> getLike_type() {
                return like_type;
            }

            public void setLike_type(List<?> like_type) {
                this.like_type = like_type;
            }

            /**
             * id : 8
             * userId : 18
             * title :
             * description : fdjfjffjfjfcmfmfm
             * file :
             * file_width : null
             * file_height : null
             * feed_type : post
             * content_type : text
             * privacy : community_member
             * total_like : 0
             * total_unlike : 0
             * total_comment : 0
             * like_type : []
             * my_liked_type :
             * isLiked : false
             * isCommented : false
             * created_by : {"id":18,"first_name":"Fgtdfgg","last_name":"Dvrscby","pincode":"8522","mobile":"8888888889","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg","email":null,"profile_stage":"5","active":true,"about":"ctf gdcff","active_community":16}
             * createdAt : 2022-01-18T06:34:34.000Z
             * lastCommentAt : 2022-01-18T06:34:34.000Z
             */

            private int id;
            private int userId;
            private String title;
            private String description;
            private String file;
            private Object file_width;
            private Object file_height;
            private String feed_type;
            private String content_type;
            private String privacy;
            private int total_like;
            private int total_unlike;
            private int total_comment;
            private String my_liked_type;
            private boolean isLiked;
            private boolean isCommented;
            private CreatedByBean created_by;
            private String createdAt;
            private String lastCommentAt;
            private List<?> like_type;

            public static class CreatedByBean implements Serializable {
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

                public Object getGender() {
                    return gender;
                }

                public void setGender(Object gender) {
                    this.gender = gender;
                }

                public Object getDob() {
                    return dob;
                }

                public void setDob(Object dob) {
                    this.dob = dob;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public Object getEmail() {
                    return email;
                }

                public void setEmail(Object email) {
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
                 * id : 18
                 * first_name : Fgtdfgg
                 * last_name : Dvrscby
                 * pincode : 8522
                 * mobile : 8888888889
                 * countryCode : +91
                 * gender : null
                 * dob : null
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6665.jpg
                 * email : null
                 * profile_stage : 5
                 * active : true
                 * about : ctf gdcff
                 * active_community : 16
                 */

                private int id;
                private String first_name;
                private String last_name;
                private String pincode;
                private String mobile;
                private String countryCode;
                private Object gender;
                private Object dob;
                private String image;
                private Object email;
                private String profile_stage;
                private boolean active;
                private String about;
                private int active_community;
            }
        }
    }
}

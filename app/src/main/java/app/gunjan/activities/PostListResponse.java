package app.gunjan.activities;

import java.io.Serializable;
import java.util.List;

public class PostListResponse implements Serializable {

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
     * message : Post list!
     * data : {"total":3,"page":"1","limit":"10","post":[{"id":6,"title":"","feed_type":"post","description":"","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20211026_200436_0_COMPRESSED.mp4","file_width":null,"file_height":null,"content_type":"video","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"created_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"like_type":[],"isLiked":false,"isCommented":false,"my_liked_type":""},{"id":5,"title":"","feed_type":"post","description":"","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-8144.jpg","file_width":null,"file_height":null,"content_type":"image","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"created_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"like_type":[],"isLiked":false,"isCommented":false,"my_liked_type":""},{"id":4,"title":"","feed_type":"post","description":"post on community.post on community.post on community.post on community.post on community.","file":"","file_width":null,"file_height":null,"content_type":"text","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"created_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"like_type":[],"isLiked":false,"isCommented":false,"my_liked_type":""}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        /**
         * total : 3
         * page : 1
         * limit : 10
         * post : [{"id":6,"title":"","feed_type":"post","description":"","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20211026_200436_0_COMPRESSED.mp4","file_width":null,"file_height":null,"content_type":"video","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"created_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"like_type":[],"isLiked":false,"isCommented":false,"my_liked_type":""},{"id":5,"title":"","feed_type":"post","description":"","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-8144.jpg","file_width":null,"file_height":null,"content_type":"image","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"created_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"like_type":[],"isLiked":false,"isCommented":false,"my_liked_type":""},{"id":4,"title":"","feed_type":"post","description":"post on community.post on community.post on community.post on community.post on community.","file":"","file_width":null,"file_height":null,"content_type":"text","privacy":"community_member","total_like":0,"total_unlike":0,"total_comment":0,"created_by":{"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16},"like_type":[],"isLiked":false,"isCommented":false,"my_liked_type":""}]
         */

        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public List<PostBean> getPost() {
            return post;
        }

        public void setPost(List<PostBean> post) {
            this.post = post;
        }

        private String page;
        private String limit;
        private List<PostBean> post;

        public static class PostBean implements Serializable {
            /**
             * id : 6
             * title :
             * feed_type : post
             * description :
             * file : https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20211026_200436_0_COMPRESSED.mp4
             * file_width : null
             * file_height : null
             * content_type : video
             * privacy : community_member
             * total_like : 0
             * total_unlike : 0
             * total_comment : 0
             * created_by : {"id":8,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","profile_stage":"5","active":true,"about":"dghhtff","active_community":16}
             * like_type : []
             * isLiked : false
             * isCommented : false
             * my_liked_type :
             */

            private int id;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getFeed_type() {
                return feed_type;
            }

            public void setFeed_type(String feed_type) {
                this.feed_type = feed_type;
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

            public CreatedByBean getCreated_by() {
                return created_by;
            }

            public void setCreated_by(CreatedByBean created_by) {
                this.created_by = created_by;
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

            private String feed_type;
            private String description;
            private String file;
            private Object file_width;
            private Object file_height;
            private String content_type;
            private String privacy;
            private int total_like;
            private int total_unlike;
            private int total_comment;
            private CreatedByBean created_by;
            private boolean isLiked;
            private boolean isCommented;
            private String my_liked_type;
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
}

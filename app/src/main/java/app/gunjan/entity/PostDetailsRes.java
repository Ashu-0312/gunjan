package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class PostDetailsRes implements Serializable {

    /**
     * code : 1
     * message : success
     * data : {"post":{"id":747,"title":"","feed_type":"disccusion","description":"new post","file":"https://media-appsinvo.s3.us-east-2.amazonaws.com/16799808874327651.jpg","file_width":null,"file_height":null,"content_type":"image","privacy":"community_member","total_like":1,"total_unlike":1,"total_comment":0,"created_by":{"id":109,"first_name":"Manish","last_name":"Kumar","pincode":"201314","mobile":"8800310932","countryCode":"+91","gender":"Male","dob":"1996-08-22","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG_20220821_163854.jpg","email":"vibhorkp@gmail.com","state":"Uttar Pradesh\r","city":"GAUTAM BUDDHA NAGAR","designation":"Doctor","profile_stage":"5","active":true,"about":"Home · Specialist; Dr. Manish Gupta. Dr. Manish Gupta Associate Director, Department of Neurology. MD (Internal Medicine), DM (Neurology). Qualification.\n\nBook appointments Online, View Fees, User Feedbacks for Dr. Manish Nigam | Practo.","active_community":63,"language":"hi","total_available_coins":0},"like_type":["love"],"total_coins":0,"isLiked":false,"isCommented":false,"my_liked_type":"","time":"NaN:NaN:NaN","start_date":"0000-00-00","end_date":"0000-00-00","start_time":"00:00:00","isJoinedThisEvent":false,"total_joined_member":0}}
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
        public PostBean getPost() {
            return post;
        }

        public void setPost(PostBean post) {
            this.post = post;
        }

        /**
         * post : {"id":747,"title":"","feed_type":"disccusion","description":"new post","file":"https://media-appsinvo.s3.us-east-2.amazonaws.com/16799808874327651.jpg","file_width":null,"file_height":null,"content_type":"image","privacy":"community_member","total_like":1,"total_unlike":1,"total_comment":0,"created_by":{"id":109,"first_name":"Manish","last_name":"Kumar","pincode":"201314","mobile":"8800310932","countryCode":"+91","gender":"Male","dob":"1996-08-22","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG_20220821_163854.jpg","email":"vibhorkp@gmail.com","state":"Uttar Pradesh\r","city":"GAUTAM BUDDHA NAGAR","designation":"Doctor","profile_stage":"5","active":true,"about":"Home · Specialist; Dr. Manish Gupta. Dr. Manish Gupta Associate Director, Department of Neurology. MD (Internal Medicine), DM (Neurology). Qualification.\n\nBook appointments Online, View Fees, User Feedbacks for Dr. Manish Nigam | Practo.","active_community":63,"language":"hi","total_available_coins":0},"like_type":["love"],"total_coins":0,"isLiked":false,"isCommented":false,"my_liked_type":"","time":"NaN:NaN:NaN","start_date":"0000-00-00","end_date":"0000-00-00","start_time":"00:00:00","isJoinedThisEvent":false,"total_joined_member":0}
         */

        private PostBean post;

        public static class PostBean implements Serializable {
            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public String getTotal_like() {
                return total_like;
            }

            public void setTotal_like(String total_like) {
                this.total_like = total_like;
            }

            public String getTotal_unlike() {
                return total_unlike;
            }

            public void setTotal_unlike(String total_unlike) {
                this.total_unlike = total_unlike;
            }

            public String getTotal_comment() {
                return total_comment;
            }

            public void setTotal_comment(String total_comment) {
                this.total_comment = total_comment;
            }

            public CreatedByBean getCreated_by() {
                return created_by;
            }

            public void setCreated_by(CreatedByBean created_by) {
                this.created_by = created_by;
            }

            public String getTotal_coins() {
                return total_coins;
            }

            public void setTotal_coins(String total_coins) {
                this.total_coins = total_coins;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public boolean isJoinedThisEvent() {
                return isJoinedThisEvent;
            }

            public void setJoinedThisEvent(boolean joinedThisEvent) {
                isJoinedThisEvent = joinedThisEvent;
            }

            public String getTotal_joined_member() {
                return total_joined_member;
            }

            public void setTotal_joined_member(String total_joined_member) {
                this.total_joined_member = total_joined_member;
            }

            public List<String> getLike_type() {
                return like_type;
            }

            public void setLike_type(List<String> like_type) {
                this.like_type = like_type;
            }

            /**
             * id : 747
             * title :
             * feed_type : disccusion
             * description : new post
             * file : https://media-appsinvo.s3.us-east-2.amazonaws.com/16799808874327651.jpg
             * file_width : null
             * file_height : null
             * content_type : image
             * privacy : community_member
             * total_like : 1
             * total_unlike : 1
             * total_comment : 0
             * created_by : {"id":109,"first_name":"Manish","last_name":"Kumar","pincode":"201314","mobile":"8800310932","countryCode":"+91","gender":"Male","dob":"1996-08-22","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG_20220821_163854.jpg","email":"vibhorkp@gmail.com","state":"Uttar Pradesh\r","city":"GAUTAM BUDDHA NAGAR","designation":"Doctor","profile_stage":"5","active":true,"about":"Home · Specialist; Dr. Manish Gupta. Dr. Manish Gupta Associate Director, Department of Neurology. MD (Internal Medicine), DM (Neurology). Qualification.\n\nBook appointments Online, View Fees, User Feedbacks for Dr. Manish Nigam | Practo.","active_community":63,"language":"hi","total_available_coins":0}
             * like_type : ["love"]
             * total_coins : 0
             * isLiked : false
             * isCommented : false
             * my_liked_type :
             * time : NaN:NaN:NaN
             * start_date : 0000-00-00
             * end_date : 0000-00-00
             * start_time : 00:00:00
             * isJoinedThisEvent : false
             * total_joined_member : 0
             */

            private String id;

            public String getCommunity() {
                return community;
            }

            public void setCommunity(String community) {
                this.community = community;
            }

            private String community;
            private String title;
            private String feed_type;
            private String description;
            private String file;
            private Object file_width;
            private Object file_height;
            private String content_type;
            private String privacy;
            private String total_like;
            private String total_unlike;
            private String total_comment;
            private CreatedByBean created_by;
            private String total_coins;
            private boolean isLiked;
            private boolean isCommented;

            public boolean isYourPost() {
                return isYourPost;
            }

            public void setYourPost(boolean yourPost) {
                isYourPost = yourPost;
            }

            public boolean isMemberOfPostCommunity() {
                return isMemberOfPostCommunity;
            }

            public void setMemberOfPostCommunity(boolean memberOfPostCommunity) {
                isMemberOfPostCommunity = memberOfPostCommunity;
            }

            public boolean isActiveInPostCommunity() {
                return isActiveInPostCommunity;
            }

            public void setActiveInPostCommunity(boolean activeInPostCommunity) {
                isActiveInPostCommunity = activeInPostCommunity;
            }

            private boolean isYourPost;
            private boolean isMemberOfPostCommunity;
            private boolean isActiveInPostCommunity;
            private String my_liked_type;
            private String time;
            private String start_date;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            private String createdAt;
            private String end_date;
            private String start_time;
            private boolean isJoinedThisEvent;
            private String total_joined_member;
            private List<String> like_type;

            public static class CreatedByBean implements Serializable {
                public String getId() {
                    return id;
                }

                public void setId(String id) {
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

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getDesignation() {
                    return designation;
                }

                public void setDesignation(String designation) {
                    this.designation = designation;
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

                public String getActive_community() {
                    return active_community;
                }

                public void setActive_community(String active_community) {
                    this.active_community = active_community;
                }

                public String getLanguage() {
                    return language;
                }

                public void setLanguage(String language) {
                    this.language = language;
                }

                public String getTotal_available_coins() {
                    return total_available_coins;
                }

                public void setTotal_available_coins(String total_available_coins) {
                    this.total_available_coins = total_available_coins;
                }

                /**
                 * id : 109
                 * first_name : Manish
                 * last_name : Kumar
                 * pincode : 201314
                 * mobile : 8800310932
                 * countryCode : +91
                 * gender : Male
                 * dob : 1996-08-22
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG_20220821_163854.jpg
                 * email : vibhorkp@gmail.com
                 * state : Uttar Pradesh
                 * city : GAUTAM BUDDHA NAGAR
                 * designation : Doctor
                 * profile_stage : 5
                 * active : true
                 * about : Home · Specialist; Dr. Manish Gupta. Dr. Manish Gupta Associate Director, Department of Neurology. MD (Internal Medicine), DM (Neurology). Qualification.

                 Book appointments Online, View Fees, User Feedbacks for Dr. Manish Nigam | Practo.
                 * active_community : 63
                 * language : hi
                 * total_available_coins : 0
                 */

                private String id;
                private String first_name;
                private String last_name;
                private String pincode;
                private String mobile;
                private String countryCode;
                private String gender;
                private String dob;
                private String image;
                private String email;
                private String state;
                private String city;
                private String designation;
                private String profile_stage;
                private boolean active;
                private String about;
                private String active_community;
                private String language;
                private String total_available_coins;
            }
        }
    }
}

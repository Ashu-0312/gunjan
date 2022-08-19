package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class ReceivedCoinListResponse implements Serializable {

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
     * message : success
     * data : {"donation_list":[{"id":117,"postId":243,"donor_community":70,"receiver_community":70,"total_coins":5,"post_details":{"id":243,"userId":113,"community":70,"title":"","description":"fffff","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20220726_123206.mp4","file_width":null,"file_height":null,"feed_type":"disccusion","content_type":"video","privacy":"community_member","start_date":"0000-00-00","start_time":"00:00:00","end_date":"0000-00-00","total_like":0,"total_unlike":0,"total_comment":0,"like_type":null,"lastCommentAt":"2022-07-26T07:02:44.000Z","total_coins":15,"createdAt":"2022-07-26T07:02:44.000Z","created_by":{"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985},"community_details":{"id":70,"userId":113,"category":32,"title":"Suraj Community","about":"djdjddjdjdjdjdjdjddjdjdjdjdkdj","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-4690.jpg"}},"user_details":{"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985}},{"id":116,"postId":243,"donor_community":70,"receiver_community":70,"total_coins":10,"post_details":{"id":243,"userId":113,"community":70,"title":"","description":"fffff","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20220726_123206.mp4","file_width":null,"file_height":null,"feed_type":"disccusion","content_type":"video","privacy":"community_member","start_date":"0000-00-00","start_time":"00:00:00","end_date":"0000-00-00","total_like":0,"total_unlike":0,"total_comment":0,"like_type":null,"lastCommentAt":"2022-07-26T07:02:44.000Z","total_coins":15,"createdAt":"2022-07-26T07:02:44.000Z","created_by":{"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985},"community_details":{"id":70,"userId":113,"category":32,"title":"Suraj Community","about":"djdjddjdjdjdjdjdjddjdjdjdjdkdj","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-4690.jpg"}},"user_details":{"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985}}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<DonationListBean> getDonation_list() {
            return donation_list;
        }

        public void setDonation_list(List<DonationListBean> donation_list) {
            this.donation_list = donation_list;
        }

        private List<DonationListBean> donation_list;

        public static class DonationListBean implements Serializable {
            /**
             * id : 117
             * postId : 243
             * donor_community : 70
             * receiver_community : 70
             * total_coins : 5
             * post_details : {"id":243,"userId":113,"community":70,"title":"","description":"fffff","file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20220726_123206.mp4","file_width":null,"file_height":null,"feed_type":"disccusion","content_type":"video","privacy":"community_member","start_date":"0000-00-00","start_time":"00:00:00","end_date":"0000-00-00","total_like":0,"total_unlike":0,"total_comment":0,"like_type":null,"lastCommentAt":"2022-07-26T07:02:44.000Z","total_coins":15,"createdAt":"2022-07-26T07:02:44.000Z","created_by":{"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985},"community_details":{"id":70,"userId":113,"category":32,"title":"Suraj Community","about":"djdjddjdjdjdjdjdjddjdjdjdjdkdj","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-4690.jpg"}}
             * user_details : {"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985}
             */

            private int id;
            private int postId;
            private String donor_community;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public String getDonor_community() {
                return donor_community;
            }

            public void setDonor_community(String donor_community) {
                this.donor_community = donor_community;
            }

            public String getReceiver_community() {
                return receiver_community;
            }

            public void setReceiver_community(String receiver_community) {
                this.receiver_community = receiver_community;
            }

            public String getTotal_coins() {
                return total_coins;
            }

            public void setTotal_coins(String total_coins) {
                this.total_coins = total_coins;
            }

            public PostDetailsBean getPost_details() {
                return post_details;
            }

            public void setPost_details(PostDetailsBean post_details) {
                this.post_details = post_details;
            }

            public UserDetailsBean getUser_details() {
                return user_details;
            }

            public void setUser_details(UserDetailsBean user_details) {
                this.user_details = user_details;
            }

            private String receiver_community;
            private String total_coins;
            private PostDetailsBean post_details;
            private UserDetailsBean user_details;

            public static class PostDetailsBean implements Serializable {
                /**
                 * id : 243
                 * userId : 113
                 * community : 70
                 * title :
                 * description : fffff
                 * file : https://s3.us-east-2.amazonaws.com/media-appsinvo/VID_20220726_123206.mp4
                 * file_width : null
                 * file_height : null
                 * feed_type : disccusion
                 * content_type : video
                 * privacy : community_member
                 * start_date : 0000-00-00
                 * start_time : 00:00:00
                 * end_date : 0000-00-00
                 * total_like : 0
                 * total_unlike : 0
                 * total_comment : 0
                 * like_type : null
                 * lastCommentAt : 2022-07-26T07:02:44.000Z
                 * total_coins : 15
                 * createdAt : 2022-07-26T07:02:44.000Z
                 * created_by : {"id":113,"first_name":"Suraj","last_name":"Jaiswal","pincode":"534340","mobile":"9151134737","countryCode":"+91","gender":"Male","dob":"2002-07-26","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg","email":"ashutosh@gmail.com","state":"Andhra Pradesh","city":"Kovvur","designation":"Developer","profile_stage":"5","active":true,"about":"djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf","active_community":70,"language":"en","total_available_coins":9985}
                 * community_details : {"id":70,"userId":113,"category":32,"title":"Suraj Community","about":"djdjddjdjdjdjdjdjddjdjdjdjdkdj","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-4690.jpg"}
                 */

                private int id;
                private int userId;
                private int community;
                private String title;

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

                public int getCommunity() {
                    return community;
                }

                public void setCommunity(int community) {
                    this.community = community;
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

                public String getStart_date() {
                    return start_date;
                }

                public void setStart_date(String start_date) {
                    this.start_date = start_date;
                }

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public String getEnd_date() {
                    return end_date;
                }

                public void setEnd_date(String end_date) {
                    this.end_date = end_date;
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

                public Object getLike_type() {
                    return like_type;
                }

                public void setLike_type(Object like_type) {
                    this.like_type = like_type;
                }

                public String getLastCommentAt() {
                    return lastCommentAt;
                }

                public void setLastCommentAt(String lastCommentAt) {
                    this.lastCommentAt = lastCommentAt;
                }

                public String getTotal_coins() {
                    return total_coins;
                }

                public void setTotal_coins(String total_coins) {
                    this.total_coins = total_coins;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public CreatedByBean getCreated_by() {
                    return created_by;
                }

                public void setCreated_by(CreatedByBean created_by) {
                    this.created_by = created_by;
                }

                public CommunityDetailsBean getCommunity_details() {
                    return community_details;
                }

                public void setCommunity_details(CommunityDetailsBean community_details) {
                    this.community_details = community_details;
                }

                private String description;
                private String file;
                private Object file_width;
                private Object file_height;
                private String feed_type;
                private String content_type;
                private String privacy;
                private String start_date;
                private String start_time;
                private String end_date;
                private String total_like;
                private String total_unlike;
                private String total_comment;
                private Object like_type;
                private String lastCommentAt;
                private String total_coins;
                private String createdAt;
                private CreatedByBean created_by;
                private CommunityDetailsBean community_details;

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

                    public int getActive_community() {
                        return active_community;
                    }

                    public void setActive_community(int active_community) {
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
                     * id : 113
                     * first_name : Suraj
                     * last_name : Jaiswal
                     * pincode : 534340
                     * mobile : 9151134737
                     * countryCode : +91
                     * gender : Male
                     * dob : 2002-07-26
                     * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg
                     * email : ashutosh@gmail.com
                     * state : Andhra Pradesh
                     * city : Kovvur
                     * designation : Developer
                     * profile_stage : 5
                     * active : true
                     * about : djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf
                     * active_community : 70
                     * language : en
                     * total_available_coins : 9985
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
                    private String state;
                    private String city;
                    private String designation;
                    private String profile_stage;
                    private boolean active;
                    private String about;
                    private int active_community;
                    private String language;
                    private String total_available_coins;
                }

                public static class CommunityDetailsBean implements Serializable {
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

                    public int getCategory() {
                        return category;
                    }

                    public void setCategory(int category) {
                        this.category = category;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getAbout() {
                        return about;
                    }

                    public void setAbout(String about) {
                        this.about = about;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    /**
                     * id : 70
                     * userId : 113
                     * category : 32
                     * title : Suraj Community
                     * about : djdjddjdjdjdjdjdjddjdjdjdjdkdj
                     * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-4690.jpg
                     */

                    private int id;
                    private int userId;
                    private int category;
                    private String title;
                    private String about;
                    private String image;
                }
            }

            public static class UserDetailsBean implements Serializable {
                /**
                 * id : 113
                 * first_name : Suraj
                 * last_name : Jaiswal
                 * pincode : 534340
                 * mobile : 9151134737
                 * countryCode : +91
                 * gender : Male
                 * dob : 2002-07-26
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-9473.jpg
                 * email : ashutosh@gmail.com
                 * state : Andhra Pradesh
                 * city : Kovvur
                 * designation : Developer
                 * profile_stage : 5
                 * active : true
                 * about : djdjdjdjdjdjddjdjdjdjddjdjdjdjdjdjddjdjdjdjdjdjdjddjdjdjdjdjdjdjddjdjxjdjdjdjddjxjxjxjdjcjdjdjdjjccjfjfjf
                 * active_community : 70
                 * language : en
                 * total_available_coins : 9985
                 */

                private int id;
                private String first_name;
                private String last_name;
                private String pincode;
                private String mobile;
                private String countryCode;
                private String gender;

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

                public int getActive_community() {
                    return active_community;
                }

                public void setActive_community(int active_community) {
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

                private String dob;
                private String image;
                private String email;
                private String state;
                private String city;
                private String designation;
                private String profile_stage;
                private boolean active;
                private String about;
                private int active_community;
                private String language;
                private String total_available_coins;
            }
        }
    }
}

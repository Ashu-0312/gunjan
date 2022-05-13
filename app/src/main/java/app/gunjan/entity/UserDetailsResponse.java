package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class UserDetailsResponse implements Serializable {

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
     * data : {"user":{"id":89,"profile_name":"Dummyuser","first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","device_type":"android","device_token":"eO80MqzkSnSyLE4_-sxMgH:APA91bEaGZCZZJJsMXBXkUxrlHh5uqTA-kWQsQX392DHk62E133WYot1b9EixKOdYRCbti8lDMpVFR5QUUl9fQ2OudhKyhU6KLuZjzecDfTTu5JVcd8Ng91RPguo72PP3EBBCBt3cAjb","profile_stage":"5","active":true,"notification_permission":"allow","about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","identification_file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-1635.jpg","active_community":50,"language":"en","total_available_coins":500,"interest_list":[{"id":39,"interestId":1,"interestDetails":{"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}},{"id":40,"interestId":2,"interestDetails":{"id":2,"name":"testing interests 2","image":"url","createdAt":"2022-01-10T10:05:49.000Z"}}],"social_media_details":{"id":2,"facebook":"fb","youtube":"youtube","instagram":"insta","linkedin":"linked"}},"following_count":0,"follower_count":0,"active_community_details":{"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"},"isCommunityAdmin":true,"isActiveMember":true}
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

        public ActiveCommunityDetailsBean getActive_community_details() {
            return active_community_details;
        }

        public void setActive_community_details(ActiveCommunityDetailsBean active_community_details) {
            this.active_community_details = active_community_details;
        }

        public String isCommunityAdmin() {
            return isCommunityAdmin;
        }

        public void setCommunityAdmin(String communityAdmin) {
            isCommunityAdmin = communityAdmin;
        }

        public String isActiveMember() {
            return isActiveMember;
        }

        public void setActiveMember(String activeMember) {
            isActiveMember = activeMember;
        }

        /**
         * user : {"id":89,"profile_name":"Dummyuser","first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","device_type":"android","device_token":"eO80MqzkSnSyLE4_-sxMgH:APA91bEaGZCZZJJsMXBXkUxrlHh5uqTA-kWQsQX392DHk62E133WYot1b9EixKOdYRCbti8lDMpVFR5QUUl9fQ2OudhKyhU6KLuZjzecDfTTu5JVcd8Ng91RPguo72PP3EBBCBt3cAjb","profile_stage":"5","active":true,"notification_permission":"allow","about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","identification_file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-1635.jpg","active_community":50,"language":"en","total_available_coins":500,"interest_list":[{"id":39,"interestId":1,"interestDetails":{"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}},{"id":40,"interestId":2,"interestDetails":{"id":2,"name":"testing interests 2","image":"url","createdAt":"2022-01-10T10:05:49.000Z"}}],"social_media_details":{"id":2,"facebook":"fb","youtube":"youtube","instagram":"insta","linkedin":"linked"}}
         * following_count : 0
         * follower_count : 0
         * active_community_details : {"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}
         * isCommunityAdmin : true
         * isActiveMember : true
         */

        private UserBean user;
        private int following_count;
        private int follower_count;
        private ActiveCommunityDetailsBean active_community_details;
        private String isCommunityAdmin;
        private String isActiveMember;

        public static class UserBean implements Serializable {
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProfile_name() {
                return profile_name;
            }

            public void setProfile_name(String profile_name) {
                this.profile_name = profile_name;
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

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
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

            public String getNotification_permission() {
                return notification_permission;
            }

            public void setNotification_permission(String notification_permission) {
                this.notification_permission = notification_permission;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getIdentification_file() {
                return identification_file;
            }

            public void setIdentification_file(String identification_file) {
                this.identification_file = identification_file;
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

            public int getTotal_available_coins() {
                return total_available_coins;
            }

            public void setTotal_available_coins(int total_available_coins) {
                this.total_available_coins = total_available_coins;
            }

            public SocialMediaDetailsBean getSocial_media_details() {
                return social_media_details;
            }

            public void setSocial_media_details(SocialMediaDetailsBean social_media_details) {
                this.social_media_details = social_media_details;
            }

            public List<InterestListBean> getInterest_list() {
                return interest_list;
            }

            public void setInterest_list(List<InterestListBean> interest_list) {
                this.interest_list = interest_list;
            }

            /**
             * id : 89
             * profile_name : Dummyuser
             * first_name : Dummy
             * last_name : User
             * pincode : 741150
             * mobile : 8299727845
             * countryCode : +91
             * gender : Male
             * dob : 1996-05-13
             * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg
             * email : rd@gmail.com
             * state : Uttar Pradesh
             * city : Aliganj
             * designation : Development
             * device_type : android
             * device_token : eO80MqzkSnSyLE4_-sxMgH:APA91bEaGZCZZJJsMXBXkUxrlHh5uqTA-kWQsQX392DHk62E133WYot1b9EixKOdYRCbti8lDMpVFR5QUUl9fQ2OudhKyhU6KLuZjzecDfTTu5JVcd8Ng91RPguo72PP3EBBCBt3cAjb
             * profile_stage : 5
             * active : true
             * notification_permission : allow
             * about : djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg
             * identification_file : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-1635.jpg
             * active_community : 50
             * language : en
             * total_available_coins : 500
             * interest_list : [{"id":39,"interestId":1,"interestDetails":{"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}},{"id":40,"interestId":2,"interestDetails":{"id":2,"name":"testing interests 2","image":"url","createdAt":"2022-01-10T10:05:49.000Z"}}]
             * social_media_details : {"id":2,"facebook":"fb","youtube":"youtube","instagram":"insta","linkedin":"linked"}
             */

            private int id;
            private String profile_name;
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
            private String device_type;
            private String device_token;
            private String profile_stage;
            private boolean active;
            private String notification_permission;
            private String about;
            private String identification_file;
            private int active_community;
            private String language;
            private int total_available_coins;
            private SocialMediaDetailsBean social_media_details;
            private List<InterestListBean> interest_list;

            public static class SocialMediaDetailsBean implements Serializable {
                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getFacebook() {
                    return facebook;
                }

                public void setFacebook(String facebook) {
                    this.facebook = facebook;
                }

                public String getYoutube() {
                    return youtube;
                }

                public void setYoutube(String youtube) {
                    this.youtube = youtube;
                }

                public String getInstagram() {
                    return instagram;
                }

                public void setInstagram(String instagram) {
                    this.instagram = instagram;
                }

                public String getLinkedin() {
                    return linkedin;
                }

                public void setLinkedin(String linkedin) {
                    this.linkedin = linkedin;
                }

                /**
                 * id : 2
                 * facebook : fb
                 * youtube : youtube
                 * instagram : insta
                 * linkedin : linked
                 */

                private int id;
                private String facebook;
                private String youtube;
                private String instagram;
                private String linkedin;
            }

            public static class InterestListBean implements Serializable {
                /**
                 * id : 39
                 * interestId : 1
                 * interestDetails : {"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}
                 */

                private int id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getInterestId() {
                    return interestId;
                }

                public void setInterestId(int interestId) {
                    this.interestId = interestId;
                }

                public InterestDetailsBean getInterestDetails() {
                    return interestDetails;
                }

                public void setInterestDetails(InterestDetailsBean interestDetails) {
                    this.interestDetails = interestDetails;
                }

                private int interestId;
                private InterestDetailsBean interestDetails;

                public static class InterestDetailsBean implements Serializable {
                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public String getCreatedAt() {
                        return createdAt;
                    }

                    public void setCreatedAt(String createdAt) {
                        this.createdAt = createdAt;
                    }

                    /**
                     * id : 1
                     * name : testing interests
                     * image : url
                     * createdAt : 2022-01-10T09:56:50.000Z
                     */

                    private int id;
                    private String name;
                    private String image;
                    private String createdAt;
                }
            }
        }

        public static class ActiveCommunityDetailsBean implements Serializable {
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
             * id : 50
             * userId : 89
             * category : 6
             * title : My Community Added
             * about : fnfjffifkfkfkfjfif
             * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg
             */

            private int id;
            private int userId;
            private int category;
            private String title;
            private String about;
            private String image;
        }
    }
}

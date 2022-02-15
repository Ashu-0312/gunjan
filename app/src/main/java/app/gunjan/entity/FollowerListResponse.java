package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class FollowerListResponse implements Serializable {


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
     * message : User list!
     * data : {"user_list":[{"id":22,"userId":24,"partnerId":22,"community":18,"userDetails":{"id":24,"first_name":"User","last_name":"Demo3","pincode":"8986","mobile":"3333333333","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-7365.jpg","email":null,"profile_stage":"5","active":true,"about":"djdjdjdjdj","active_community":18}}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        private List<UserListBean> user_list;

        public static class UserListBean implements Serializable {
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

            public int getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(int partnerId) {
                this.partnerId = partnerId;
            }

            public int getCommunity() {
                return community;
            }

            public void setCommunity(int community) {
                this.community = community;
            }

            public UserDetailsBean getUserDetails() {
                return userDetails;
            }

            public void setUserDetails(UserDetailsBean userDetails) {
                this.userDetails = userDetails;
            }

            /**
             * id : 22
             * userId : 24
             * partnerId : 22
             * community : 18
             * userDetails : {"id":24,"first_name":"User","last_name":"Demo3","pincode":"8986","mobile":"3333333333","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-7365.jpg","email":null,"profile_stage":"5","active":true,"about":"djdjdjdjdj","active_community":18}
             */

            private int id;
            private int userId;
            private int partnerId;
            private int community;
            private UserDetailsBean userDetails;

            public static class UserDetailsBean implements Serializable {
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
                 * id : 24
                 * first_name : User
                 * last_name : Demo3
                 * pincode : 8986
                 * mobile : 3333333333
                 * countryCode : +91
                 * gender : null
                 * dob : null
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-7365.jpg
                 * email : null
                 * profile_stage : 5
                 * active : true
                 * about : djdjdjdjdj
                 * active_community : 18
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

package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class MemberListResponse implements Serializable {

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
     * message : Community member list
     * data : {"member_list":[{"id":35,"userId":32,"community":22,"isAdmin":false,"isActive":true,"userDetails":{"id":32,"first_name":"User1","last_name":"User1","pincode":"868","mobile":"6060606060","countryCode":"+91","gender":"Male","dob":"2001-02-16","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-8254.jpg","email":"evvdvdv@gmail.com","state":"AP","city":"Cumbum","profile_stage":"5","active":true,"about":"cccc","active_community":22}}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<MemberListBean> getMember_list() {
            return member_list;
        }

        public void setMember_list(List<MemberListBean> member_list) {
            this.member_list = member_list;
        }

        private List<MemberListBean> member_list;
        public static class MemberListBean implements Serializable {
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

            public boolean isAdmin() {
                return isAdmin;
            }

            public void setAdmin(boolean admin) {
                isAdmin = admin;
            }

            public boolean isActive() {
                return isActive;
            }

            public void setActive(boolean active) {
                isActive = active;
            }

            public UserDetailsBean getUserDetails() {
                return userDetails;
            }

            public void setUserDetails(UserDetailsBean userDetails) {
                this.userDetails = userDetails;
            }

            /**
             * id : 35
             * userId : 32
             * community : 22
             * isAdmin : false
             * isActive : true
             * userDetails : {"id":32,"first_name":"User1","last_name":"User1","pincode":"868","mobile":"6060606060","countryCode":"+91","gender":"Male","dob":"2001-02-16","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-8254.jpg","email":"evvdvdv@gmail.com","state":"AP","city":"Cumbum","profile_stage":"5","active":true,"about":"cccc","active_community":22}
             */

            private int id;
            private int userId;
            private int community;
            private boolean isAdmin;
            private boolean isActive;
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
                 * id : 32
                 * first_name : User1
                 * last_name : User1
                 * pincode : 868
                 * mobile : 6060606060
                 * countryCode : +91
                 * gender : Male
                 * dob : 2001-02-16
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-8254.jpg
                 * email : evvdvdv@gmail.com
                 * state : AP
                 * city : Cumbum
                 * profile_stage : 5
                 * active : true
                 * about : cccc
                 * active_community : 22
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
                private String profile_stage;
                private boolean active;
                private String about;
                private int active_community;
            }
        }
    }
}

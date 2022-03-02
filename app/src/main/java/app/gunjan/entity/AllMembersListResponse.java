package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class AllMembersListResponse implements Serializable {

    /**
     * code : 1
     * message : User list!
     * data : {"user_list":[{"id":23,"userId":22,"community":18,"isAdmin":true,"isActive":true,"userDetails":{"id":22,"first_name":"User1","last_name":"Demo","pincode":"2585","mobile":"1111111111","countryCode":"+91","gender":"Male","dob":"2000-02-15","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-5787.jpg","email":"user1@gmail.com","state":null,"city":null,"profile_stage":"5","active":true,"about":"dcfffkfndmdndndk","active_community":18}},{"id":24,"userId":22,"community":18,"isAdmin":false,"isActive":true,"userDetails":{"id":22,"first_name":"User1","last_name":"Demo","pincode":"2585","mobile":"1111111111","countryCode":"+91","gender":"Male","dob":"2000-02-15","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-5787.jpg","email":"user1@gmail.com","state":null,"city":null,"profile_stage":"5","active":true,"about":"dcfffkfndmdndndk","active_community":18}},{"id":25,"userId":23,"community":18,"isAdmin":false,"isActive":true,"userDetails":{"id":23,"first_name":"User","last_name":"Demo2","pincode":"8855","mobile":"2222222222","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-1867.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"djffjdjdjfj","active_community":18}},{"id":26,"userId":24,"community":18,"isAdmin":false,"isActive":true,"userDetails":{"id":24,"first_name":"User","last_name":"Demo3","pincode":"8986","mobile":"3333333333","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-7365.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"djdjdjdjdj","active_community":18}},{"id":27,"userId":26,"community":19,"isAdmin":true,"isActive":true,"userDetails":{"id":26,"first_name":"Nikhil","last_name":"Lamba","pincode":"201301","mobile":"9971531430","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Screenshot_20220215-190112_Instagram.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"wow wow ow","active_community":27}},{"id":30,"userId":28,"community":19,"isAdmin":false,"isActive":true,"userDetails":{"id":28,"first_name":"Djdjdj","last_name":"Djdj","pincode":"5656","mobile":"4747474747","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-918.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"djdjdjdj","active_community":19}},{"id":31,"userId":26,"community":19,"isAdmin":false,"isActive":true,"userDetails":{"id":26,"first_name":"Nikhil","last_name":"Lamba","pincode":"201301","mobile":"9971531430","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Screenshot_20220215-190112_Instagram.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"wow wow ow","active_community":27}},{"id":28,"userId":28,"community":20,"isAdmin":true,"isActive":true,"userDetails":{"id":28,"first_name":"Djdjdj","last_name":"Djdj","pincode":"5656","mobile":"4747474747","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-918.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"djdjdjdj","active_community":19}},{"id":29,"userId":28,"community":20,"isAdmin":false,"isActive":true,"userDetails":{"id":28,"first_name":"Djdjdj","last_name":"Djdj","pincode":"5656","mobile":"4747474747","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-918.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"djdjdjdj","active_community":19}},{"id":32,"userId":29,"community":21,"isAdmin":true,"isActive":true,"userDetails":{"id":29,"first_name":"Djdjdjdj","last_name":"Djdjd","pincode":"8989","mobile":"8989898989","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6166.jpg","email":null,"state":null,"city":null,"profile_stage":"5","active":true,"about":"jfjfjf","active_community":32}}]}
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
             * id : 23
             * userId : 22
             * community : 18
             * isAdmin : true
             * isActive : true
             * userDetails : {"id":22,"first_name":"User1","last_name":"Demo","pincode":"2585","mobile":"1111111111","countryCode":"+91","gender":"Male","dob":"2000-02-15","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-5787.jpg","email":"user1@gmail.com","state":null,"city":null,"profile_stage":"5","active":true,"about":"dcfffkfndmdndndk","active_community":18}
             */

            private int id;
            private int userId;
            private int community;
            private boolean isAdmin;
            private boolean isActive;
            private UserDetailsBean userDetails;
            public Boolean getSelected() {
                return isSelected;
            }

            public void setSelected(Boolean selected) {
                isSelected = selected;
            }

            private Boolean isSelected=false;

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

                public Object getState() {
                    return state;
                }

                public void setState(Object state) {
                    this.state = state;
                }

                public Object getCity() {
                    return city;
                }

                public void setCity(Object city) {
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
                 * id : 22
                 * first_name : User1
                 * last_name : Demo
                 * pincode : 2585
                 * mobile : 1111111111
                 * countryCode : +91
                 * gender : Male
                 * dob : 2000-02-15
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-5787.jpg
                 * email : user1@gmail.com
                 * state : null
                 * city : null
                 * profile_stage : 5
                 * active : true
                 * about : dcfffkfndmdndndk
                 * active_community : 18
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
                private Object state;
                private Object city;
                private String profile_stage;
                private boolean active;
                private String about;
                private int active_community;
            }
        }
    }
}

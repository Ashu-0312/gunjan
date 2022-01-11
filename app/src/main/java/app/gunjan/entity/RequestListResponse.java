package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class RequestListResponse implements Serializable {

    /**
     * code : 1
     * message : Request list
     * data : {"request_list":[{"id":2,"userId":2,"community":1,"status":"0","userDetails":{"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111111","countryCode":"91","gender":"Male","dob":"2020-03-02","image":"url","email":"test@test.com","profile_stage":"5","active":true,"about":"About"}},{"id":4,"userId":10,"community":1,"status":"0","userDetails":{"id":10,"first_name":"Fff","last_name":"Fft","pincode":"5555","mobile":"1111111111","countryCode":"+91","gender":"Male","dob":"1998-01-10","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0016.jpg","email":"abcd@gmail.com","profile_stage":"5","active":true,"about":"ghffvgg"}}]}
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
        public List<RequestListBean> getRequest_list() {
            return request_list;
        }

        public void setRequest_list(List<RequestListBean> request_list) {
            this.request_list = request_list;
        }

        private List<RequestListBean> request_list;
        public static class RequestListBean implements Serializable {
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public UserDetailsBean getUserDetails() {
                return userDetails;
            }

            public void setUserDetails(UserDetailsBean userDetails) {
                this.userDetails = userDetails;
            }

            /**
             * id : 2
             * userId : 2
             * community : 1
             * status : 0
             * userDetails : {"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111111","countryCode":"91","gender":"Male","dob":"2020-03-02","image":"url","email":"test@test.com","profile_stage":"5","active":true,"about":"About"}
             */

            private int id;
            private int userId;
            private int community;
            private String status;
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

                /**
                 * id : 2
                 * first_name : first name
                 * last_name : last name
                 * pincode : 121212
                 * mobile : 11111111
                 * countryCode : 91
                 * gender : Male
                 * dob : 2020-03-02
                 * image : url
                 * email : test@test.com
                 * profile_stage : 5
                 * active : true
                 * about : About
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
            }
        }
    }
}

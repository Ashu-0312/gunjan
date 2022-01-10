package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class EditProfileResponse implements Serializable {

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
     * message : Profile updated successfully.
     * data : {"user":{"id":8,"profile_name":"profile name","first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","device_type":"android","device_token":"","profile_stage":"5","active":true,"notification_permission":"allow","about":"About","identification_file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6556.jpg","interest_list":[{"id":3,"interestId":1,"interestDetails":{"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}},{"id":4,"interestId":2,"interestDetails":{"id":2,"name":"testing interests 2","image":"url","createdAt":"2022-01-10T10:05:49.000Z"}}]}}
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

        /**
         * user : {"id":8,"profile_name":"profile name","first_name":"first name","last_name":"last name","pincode":"121212","mobile":"2222222222","countryCode":"+91","gender":"Male","dob":"1995-03-02","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg","email":"testt@test.com","device_type":"android","device_token":"","profile_stage":"5","active":true,"notification_permission":"allow","about":"About","identification_file":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6556.jpg","interest_list":[{"id":3,"interestId":1,"interestDetails":{"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}},{"id":4,"interestId":2,"interestDetails":{"id":2,"name":"testing interests 2","image":"url","createdAt":"2022-01-10T10:05:49.000Z"}}]}
         */

        private UserBean user;

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

            public List<InterestListBean> getInterest_list() {
                return interest_list;
            }

            public void setInterest_list(List<InterestListBean> interest_list) {
                this.interest_list = interest_list;
            }

            /**
             * id : 8
             * profile_name : profile name
             * first_name : first name
             * last_name : last name
             * pincode : 121212
             * mobile : 2222222222
             * countryCode : +91
             * gender : Male
             * dob : 1995-03-02
             * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0010.jpg
             * email : testt@test.com
             * device_type : android
             * device_token :
             * profile_stage : 5
             * active : true
             * notification_permission : allow
             * about : About
             * identification_file : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-6556.jpg
             * interest_list : [{"id":3,"interestId":1,"interestDetails":{"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}},{"id":4,"interestId":2,"interestDetails":{"id":2,"name":"testing interests 2","image":"url","createdAt":"2022-01-10T10:05:49.000Z"}}]
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
            private String device_type;
            private String device_token;
            private String profile_stage;
            private boolean active;
            private String notification_permission;
            private String about;
            private String identification_file;
            private List<InterestListBean> interest_list;

            public static class InterestListBean implements Serializable {
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

                /**
                 * id : 3
                 * interestId : 1
                 * interestDetails : {"id":1,"name":"testing interests","image":"url","createdAt":"2022-01-10T09:56:50.000Z"}
                 */

                private int id;
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
    }
}

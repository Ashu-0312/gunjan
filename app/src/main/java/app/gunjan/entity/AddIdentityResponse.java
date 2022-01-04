package app.gunjan.entity;

import java.io.Serializable;

public class AddIdentityResponse implements Serializable {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    /**
     * code : 1
     * data : {"user":{"id":7,"profile_name":"profile name","first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111112","countryCode":"\" 91\"","gender":null,"dob":null,"image":"url","device_token":"","profile_stage":"3","active":true,"notification_permission":"allow","about":"About","identification_file":"url","user_id":7}}
     */

    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
         * user : {"id":7,"profile_name":"profile name","first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111112","countryCode":"\" 91\"","gender":null,"dob":null,"image":"url","device_token":"","profile_stage":"3","active":true,"notification_permission":"allow","about":"About","identification_file":"url","user_id":7}
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

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            /**
             * id : 7
             * profile_name : profile name
             * first_name : first name
             * last_name : last name
             * pincode : 121212
             * mobile : 11111112
             * countryCode : " 91"
             * gender : null
             * dob : null
             * image : url
             * device_token :
             * profile_stage : 3
             * active : true
             * notification_permission : allow
             * about : About
             * identification_file : url
             * user_id : 7
             */

            private int id;
            private String profile_name;
            private String first_name;
            private String last_name;
            private String pincode;
            private String mobile;
            private String countryCode;
            private Object gender;
            private Object dob;
            private String image;
            private String device_token;
            private String profile_stage;
            private boolean active;
            private String notification_permission;
            private String about;
            private String identification_file;
            private int user_id;
        }
    }
}

package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class CommunityListResponse implements Serializable {
    /**
     * code : 1
     * message : Community list
     * data : {"community_list":[{"id":2,"category":1,"title":"testing 2","about":"testing 2","image":"url","members":[{"id":1,"userId":2,"community":2,"isAdmin":true,"userDetails":{"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111111","countryCode":"91","gender":null,"dob":null,"image":"url","profile_stage":"5","active":true,"about":"About"}}]},{"id":3,"category":1,"title":"testing 2","about":"testing 2","image":"url","members":[{"id":2,"userId":2,"community":3,"isAdmin":true,"userDetails":{"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111111","countryCode":"91","gender":null,"dob":null,"image":"url","profile_stage":"5","active":true,"about":"About"}}]},{"id":1,"category":1,"title":"testing","about":"testing","image":null,"members":[]}]}
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
        public List<CommunityListBean> getCommunity_list() {
            return community_list;
        }

        public void setCommunity_list(List<CommunityListBean> community_list) {
            this.community_list = community_list;
        }

        private List<CommunityListBean> community_list;

        public static class CommunityListBean implements Serializable {
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public List<MembersBean> getMembers() {
                return members;
            }

            public void setMembers(List<MembersBean> members) {
                this.members = members;
            }

            /**
             * id : 2
             * category : 1
             * title : testing 2
             * about : testing 2
             * image : url
             * members : [{"id":1,"userId":2,"community":2,"isAdmin":true,"userDetails":{"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111111","countryCode":"91","gender":null,"dob":null,"image":"url","profile_stage":"5","active":true,"about":"About"}}]
             */

            private int id;
            private int category;
            private String title;
            private String about;

            public String getTotal_request() {
                return total_request;
            }

            public void setTotal_request(String total_request) {
                this.total_request = total_request;
            }

            private String total_request;
            private String image;

            public Boolean getRequested() {
                return isRequested;
            }

            public void setRequested(Boolean requested) {
                isRequested = requested;
            }

            private Boolean isRequested;

            public Boolean getActiveCommunity() {
                return isActiveCommunity;
            }

            public void setActiveCommunity(Boolean activeCommunity) {
                isActiveCommunity = activeCommunity;
            }

            private Boolean isActiveCommunity;
            private List<MembersBean> members;

            public static class MembersBean implements Serializable {
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

                public UserDetailsBean getUserDetails() {
                    return userDetails;
                }

                public void setUserDetails(UserDetailsBean userDetails) {
                    this.userDetails = userDetails;
                }

                /**
                 * id : 1
                 * userId : 2
                 * community : 2
                 * isAdmin : true
                 * userDetails : {"id":2,"first_name":"first name","last_name":"last name","pincode":"121212","mobile":"11111111","countryCode":"91","gender":null,"dob":null,"image":"url","profile_stage":"5","active":true,"about":"About"}
                 */

                private int id;
                private int userId;
                private int community;
                private boolean isAdmin;
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
                     * gender : null
                     * dob : null
                     * image : url
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
                    private Object gender;
                    private Object dob;
                    private String image;
                    private String profile_stage;
                    private boolean active;
                    private String about;
                }
            }
        }
    }
}

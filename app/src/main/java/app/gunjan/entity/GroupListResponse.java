package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class GroupListResponse implements Serializable {

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
     * message : Group list.
     * data : {"default_group_list":{"id":8,"group_sid":"CH843768cc200a4f13a88cd863701fcd65","group_name":"New Community Testing","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg","group_details":{"id":8,"group_sid":"CH843768cc200a4f13a88cd863701fcd65","group_name":"New Community Testing","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg"},"admin_member_details":[{"id":11,"group_id":"8","member_sid":"MBb8a2b13eeaca4f12a17577ac556a116f","member_id":32,"status":"active","role":"admin"}],"chat_type":"group_chat","participants":[{"id":12,"group_id":"8","member_sid":"MBa7216e5690904b2bb0448ab4255053ff","status":"active","role":"member","participants_details":{"id":51,"first_name":"New","last_name":"User","pincode":"5822","mobile":"2424242424","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3169.jpg","email":null,"state":"Uttar Pradesh","city":"Gorakhpur","profile_stage":"5","active":true,"about":"rcffggh","active_community":35}},{"id":11,"group_id":"8","member_sid":"MBb8a2b13eeaca4f12a17577ac556a116f","status":"active","role":"admin","participants_details":{"id":32,"first_name":"User1","last_name":"User1","pincode":"868","mobile":"6060606060","countryCode":"+91","gender":"Male","dob":"2001-02-16","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0016.jpg","email":"evvdvdv@gmail.com","state":"Goa","city":"Cavelossim","profile_stage":"5","active":true,"about":"cccc","active_community":35}}]},"group_list":[]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        /**
         * default_group_list : {"id":8,"group_sid":"CH843768cc200a4f13a88cd863701fcd65","group_name":"New Community Testing","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg","group_details":{"id":8,"group_sid":"CH843768cc200a4f13a88cd863701fcd65","group_name":"New Community Testing","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg"},"admin_member_details":[{"id":11,"group_id":"8","member_sid":"MBb8a2b13eeaca4f12a17577ac556a116f","member_id":32,"status":"active","role":"admin"}],"chat_type":"group_chat","participants":[{"id":12,"group_id":"8","member_sid":"MBa7216e5690904b2bb0448ab4255053ff","status":"active","role":"member","participants_details":{"id":51,"first_name":"New","last_name":"User","pincode":"5822","mobile":"2424242424","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3169.jpg","email":null,"state":"Uttar Pradesh","city":"Gorakhpur","profile_stage":"5","active":true,"about":"rcffggh","active_community":35}},{"id":11,"group_id":"8","member_sid":"MBb8a2b13eeaca4f12a17577ac556a116f","status":"active","role":"admin","participants_details":{"id":32,"first_name":"User1","last_name":"User1","pincode":"868","mobile":"6060606060","countryCode":"+91","gender":"Male","dob":"2001-02-16","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0016.jpg","email":"evvdvdv@gmail.com","state":"Goa","city":"Cavelossim","profile_stage":"5","active":true,"about":"cccc","active_community":35}}]}
         * group_list : []
         */

        private DefaultGroupListBean default_group_list;

        public DefaultGroupListBean getDefault_group_list() {
            return default_group_list;
        }

        public void setDefault_group_list(DefaultGroupListBean default_group_list) {
            this.default_group_list = default_group_list;
        }

        public List<?> getGroup_list() {
            return group_list;
        }

        public void setGroup_list(List<?> group_list) {
            this.group_list = group_list;
        }

        private List<?> group_list;

        public static class DefaultGroupListBean implements Serializable {
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGroup_sid() {
                return group_sid;
            }

            public void setGroup_sid(String group_sid) {
                this.group_sid = group_sid;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public GroupDetailsBean getGroup_details() {
                return group_details;
            }

            public void setGroup_details(GroupDetailsBean group_details) {
                this.group_details = group_details;
            }

            public String getChat_type() {
                return chat_type;
            }

            public void setChat_type(String chat_type) {
                this.chat_type = chat_type;
            }

            public List<AdminMemberDetailsBean> getAdmin_member_details() {
                return admin_member_details;
            }

            public void setAdmin_member_details(List<AdminMemberDetailsBean> admin_member_details) {
                this.admin_member_details = admin_member_details;
            }

            public List<ParticipantsBean> getParticipants() {
                return participants;
            }

            public void setParticipants(List<ParticipantsBean> participants) {
                this.participants = participants;
            }

            /**
             * id : 8
             * group_sid : CH843768cc200a4f13a88cd863701fcd65
             * group_name : New Community Testing
             * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg
             * group_details : {"id":8,"group_sid":"CH843768cc200a4f13a88cd863701fcd65","group_name":"New Community Testing","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg"}
             * admin_member_details : [{"id":11,"group_id":"8","member_sid":"MBb8a2b13eeaca4f12a17577ac556a116f","member_id":32,"status":"active","role":"admin"}]
             * chat_type : group_chat
             * participants : [{"id":12,"group_id":"8","member_sid":"MBa7216e5690904b2bb0448ab4255053ff","status":"active","role":"member","participants_details":{"id":51,"first_name":"New","last_name":"User","pincode":"5822","mobile":"2424242424","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3169.jpg","email":null,"state":"Uttar Pradesh","city":"Gorakhpur","profile_stage":"5","active":true,"about":"rcffggh","active_community":35}},{"id":11,"group_id":"8","member_sid":"MBb8a2b13eeaca4f12a17577ac556a116f","status":"active","role":"admin","participants_details":{"id":32,"first_name":"User1","last_name":"User1","pincode":"868","mobile":"6060606060","countryCode":"+91","gender":"Male","dob":"2001-02-16","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/IMG-20211104-WA0016.jpg","email":"evvdvdv@gmail.com","state":"Goa","city":"Cavelossim","profile_stage":"5","active":true,"about":"cccc","active_community":35}}]
             */

            private int id;
            private String group_sid;
            private String group_name;
            private String image;
            private GroupDetailsBean group_details;
            private String chat_type;
            private List<AdminMemberDetailsBean> admin_member_details;
            private List<ParticipantsBean> participants;

            public static class GroupDetailsBean implements Serializable {
                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getGroup_sid() {
                    return group_sid;
                }

                public void setGroup_sid(String group_sid) {
                    this.group_sid = group_sid;
                }

                public String getGroup_name() {
                    return group_name;
                }

                public void setGroup_name(String group_name) {
                    this.group_name = group_name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                /**
                 * id : 8
                 * group_sid : CH843768cc200a4f13a88cd863701fcd65
                 * group_name : New Community Testing
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-761.jpg
                 */

                private int id;
                private String group_sid;
                private String group_name;
                private String image;
            }

            public static class AdminMemberDetailsBean implements Serializable {
                /**
                 * id : 11
                 * group_id : 8
                 * member_sid : MBb8a2b13eeaca4f12a17577ac556a116f
                 * member_id : 32
                 * status : active
                 * role : admin
                 */

                private int id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(String group_id) {
                    this.group_id = group_id;
                }

                public String getMember_sid() {
                    return member_sid;
                }

                public void setMember_sid(String member_sid) {
                    this.member_sid = member_sid;
                }

                public int getMember_id() {
                    return member_id;
                }

                public void setMember_id(int member_id) {
                    this.member_id = member_id;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getRole() {
                    return role;
                }

                public void setRole(String role) {
                    this.role = role;
                }

                private String group_id;
                private String member_sid;
                private int member_id;
                private String status;
                private String role;
            }

            public static class ParticipantsBean implements Serializable {
                /**
                 * id : 12
                 * group_id : 8
                 * member_sid : MBa7216e5690904b2bb0448ab4255053ff
                 * status : active
                 * role : member
                 * participants_details : {"id":51,"first_name":"New","last_name":"User","pincode":"5822","mobile":"2424242424","countryCode":"+91","gender":null,"dob":null,"image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3169.jpg","email":null,"state":"Uttar Pradesh","city":"Gorakhpur","profile_stage":"5","active":true,"about":"rcffggh","active_community":35}
                 */

                private int id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(String group_id) {
                    this.group_id = group_id;
                }

                public String getMember_sid() {
                    return member_sid;
                }

                public void setMember_sid(String member_sid) {
                    this.member_sid = member_sid;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getRole() {
                    return role;
                }

                public void setRole(String role) {
                    this.role = role;
                }

                public ParticipantsDetailsBean getParticipants_details() {
                    return participants_details;
                }

                public void setParticipants_details(ParticipantsDetailsBean participants_details) {
                    this.participants_details = participants_details;
                }

                private String group_id;
                private String member_sid;
                private String status;
                private String role;
                private ParticipantsDetailsBean participants_details;

                public static class ParticipantsDetailsBean implements Serializable {
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
                     * id : 51
                     * first_name : New
                     * last_name : User
                     * pincode : 5822
                     * mobile : 2424242424
                     * countryCode : +91
                     * gender : null
                     * dob : null
                     * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3169.jpg
                     * email : null
                     * state : Uttar Pradesh
                     * city : Gorakhpur
                     * profile_stage : 5
                     * active : true
                     * about : rcffggh
                     * active_community : 35
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
}

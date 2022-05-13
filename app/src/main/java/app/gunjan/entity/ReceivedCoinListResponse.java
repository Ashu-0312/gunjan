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
     * data : {"donation_list":[{"id":45,"postId":137,"donor_community":50,"receiver_community":50,"total_coins":50,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":{"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}},{"id":44,"postId":136,"donor_community":50,"receiver_community":50,"total_coins":15,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":{"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}},{"id":43,"postId":137,"donor_community":50,"receiver_community":50,"total_coins":10,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":{"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}},{"id":41,"postId":145,"donor_community":null,"receiver_community":50,"total_coins":50,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null},{"id":40,"postId":144,"donor_community":null,"receiver_community":50,"total_coins":20,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null},{"id":39,"postId":145,"donor_community":null,"receiver_community":50,"total_coins":10,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null},{"id":38,"postId":144,"donor_community":null,"receiver_community":50,"total_coins":20,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null},{"id":37,"postId":145,"donor_community":null,"receiver_community":50,"total_coins":20,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null},{"id":36,"postId":145,"donor_community":null,"receiver_community":50,"total_coins":10,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null},{"id":35,"postId":145,"donor_community":null,"receiver_community":50,"total_coins":5,"user_details":{"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425},"donor_community_details":null}]}
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

            public int getDonor_community() {
                return donor_community;
            }

            public void setDonor_community(int donor_community) {
                this.donor_community = donor_community;
            }

            public int getReceiver_community() {
                return receiver_community;
            }

            public void setReceiver_community(int receiver_community) {
                this.receiver_community = receiver_community;
            }

            public int getTotal_coins() {
                return total_coins;
            }

            public void setTotal_coins(int total_coins) {
                this.total_coins = total_coins;
            }

            public UserDetailsBean getUser_details() {
                return user_details;
            }

            public void setUser_details(UserDetailsBean user_details) {
                this.user_details = user_details;
            }

            public DonorCommunityDetailsBean getDonor_community_details() {
                return donor_community_details;
            }

            public void setDonor_community_details(DonorCommunityDetailsBean donor_community_details) {
                this.donor_community_details = donor_community_details;
            }

            /**
             * id : 45
             * postId : 137
             * donor_community : 50
             * receiver_community : 50
             * total_coins : 50
             * user_details : {"id":89,"first_name":"Dummy","last_name":"User","pincode":"741150","mobile":"8299727845","countryCode":"+91","gender":"Male","dob":"1996-05-13","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3427.jpg","email":"rd@gmail.com","state":"Uttar Pradesh","city":"Aliganj","designation":"Development","profile_stage":"5","active":true,"about":"djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg","active_community":50,"language":"en","total_available_coins":425}
             * donor_community_details : {"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}
             */

            private int id;
            private int postId;
            private int donor_community;
            private int receiver_community;
            private int total_coins;
            private UserDetailsBean user_details;
            private DonorCommunityDetailsBean donor_community_details;

            public static class UserDetailsBean implements Serializable {
                /**
                 * id : 89
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
                 * profile_stage : 5
                 * active : true
                 * about : djfjfjfrjfkfkfkffkfkfkfkrkrfkfkfkfkfkffkfkfkfkfkfkffkckckckckckcmcmcmccmcmckfkfkckffkfkfkfkfkfkfkckckfkfkcckckckckfkffkfkfkfkfkfjfkfjffjfkfkfkfkfkffkkffkkffkgkgkfkfkkggkfkfkffkfkfkfjfckfkfjfkfkffkfkfkfkfkfkg
                 * active_community : 50
                 * language : en
                 * total_available_coins : 425
                 */

                private int id;
                private String first_name;
                private String last_name;
                private String pincode;

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

                public int getTotal_available_coins() {
                    return total_available_coins;
                }

                public void setTotal_available_coins(int total_available_coins) {
                    this.total_available_coins = total_available_coins;
                }

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
                private int total_available_coins;
            }

            public static class DonorCommunityDetailsBean implements Serializable {
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

                private int category;
                private String title;
                private String about;
                private String image;
            }
        }
    }
}

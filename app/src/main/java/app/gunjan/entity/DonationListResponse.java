package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class DonationListResponse implements Serializable {

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
     * data : {"donation_list":[{"id":44,"postId":136,"donor_community":50,"receiver_community":50,"total_coins":15,"receiver_community_details":{"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}},{"id":43,"postId":137,"donor_community":50,"receiver_community":50,"total_coins":10,"receiver_community_details":{"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}}]}
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

            public ReceiverCommunityDetailsBean getReceiver_community_details() {
                return receiver_community_details;
            }

            public void setReceiver_community_details(ReceiverCommunityDetailsBean receiver_community_details) {
                this.receiver_community_details = receiver_community_details;
            }

            /**
             * id : 44
             * postId : 136
             * donor_community : 50
             * receiver_community : 50
             * total_coins : 15
             * receiver_community_details : {"id":50,"userId":89,"category":6,"title":"My Community Added","about":"fnfjffifkfkfkfjfif","image":"https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg"}
             */

            private int id;
            private int postId;
            private int donor_community;
            private int receiver_community;
            private int total_coins;
            private ReceiverCommunityDetailsBean receiver_community_details;

            public static class ReceiverCommunityDetailsBean implements Serializable {
                /**
                 * id : 50
                 * userId : 89
                 * category : 6
                 * title : My Community Added
                 * about : fnfjffifkfkfkfjfif
                 * image : https://s3.us-east-2.amazonaws.com/media-appsinvo/Image-3149.jpg
                 */

                private int id;

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

                private int userId;
                private int category;
                private String title;
                private String about;
                private String image;
            }
        }
    }
}

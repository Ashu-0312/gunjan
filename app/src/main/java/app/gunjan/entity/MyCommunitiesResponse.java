package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class MyCommunitiesResponse implements Serializable {

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
     * message : Community list
     * data : {"community_list":[{"id":1,"category":1,"title":"testing","about":"testing","image":null,"isRequested":true,"reuest_status":"0","total_request":2},{"id":2,"category":1,"title":"testing 2","about":"testing 2","image":"url","isRequested":false,"reuest_status":null,"total_request":1},{"id":3,"category":1,"title":"testing 2","about":"testing 2","image":"url","isRequested":false,"reuest_status":null,"total_request":0}]}
     */

    private int code;
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

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public boolean isRequested() {
                return isRequested;
            }

            public void setRequested(boolean requested) {
                isRequested = requested;
            }

            public String getReuest_status() {
                return reuest_status;
            }

            public void setReuest_status(String reuest_status) {
                this.reuest_status = reuest_status;
            }

            public int getTotal_request() {
                return total_request;
            }

            public void setTotal_request(int total_request) {
                this.total_request = total_request;
            }

            /**
             * id : 1
             * category : 1
             * title : testing
             * about : testing
             * image : null
             * isRequested : true
             * reuest_status : 0
             * total_request : 2
             */

            private int id;
            private int category;
            private String title;
            private String about;
            private Object image;
            private boolean isRequested;
            private String reuest_status;
            private int total_request;
        }
    }
}

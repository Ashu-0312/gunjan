package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class InterestListResponse implements Serializable {

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
     * message : Interest list
     * data : {"page":"1","limit":"10","interest":[{"id":1,"name":"testing interests","image":"url"},{"id":2,"name":"testing interests 2","image":"url"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public List<InterestBean> getInterest() {
            return interest;
        }

        public void setInterest(List<InterestBean> interest) {
            this.interest = interest;
        }

        /**
         * page : 1
         * limit : 10
         * interest : [{"id":1,"name":"testing interests","image":"url"},{"id":2,"name":"testing interests 2","image":"url"}]
         */

        private String page;
        private String limit;
        private List<InterestBean> interest;

        public static class InterestBean implements Serializable {
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

            /**
             * id : 1
             * name : testing interests
             * image : url
             */

            private int id;
            private String name;
            private String image;

            public Boolean getAdded() {
                return isAdded;
            }

            public void setAdded(Boolean added) {
                isAdded = added;
            }

            private Boolean isAdded;
        }
    }
}

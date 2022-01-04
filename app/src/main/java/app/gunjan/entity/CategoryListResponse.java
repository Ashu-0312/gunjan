package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class CategoryListResponse implements Serializable {

    /**
     * code : 1
     * message : Category list
     * data : {"category_list":[{"id":1,"name":"0"}]}
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
        public List<CategoryListBean> getCategory_list() {
            return category_list;
        }

        public void setCategory_list(List<CategoryListBean> category_list) {
            this.category_list = category_list;
        }

        private List<CategoryListBean> category_list;

        public static class CategoryListBean implements Serializable {
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

            /**
             * id : 1
             * name : 0
             */

            private int id;
            private String name;
        }
    }
}

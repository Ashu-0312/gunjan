package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class ReasonListResponse implements Serializable {

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
     * message : Reason list
     * data : {"reason_list":[{"id":1,"reason":"testing reason"},{"id":2,"reason":"testing reason"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<ReasonListBean> getReason_list() {
            return reason_list;
        }

        public void setReason_list(List<ReasonListBean> reason_list) {
            this.reason_list = reason_list;
        }

        private List<ReasonListBean> reason_list;

        public static class ReasonListBean implements Serializable {
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            /**
             * id : 1
             * reason : testing reason
             */

            private int id;
            private String reason;
        }
    }
}

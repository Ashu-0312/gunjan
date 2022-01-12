package app.gunjan.entity;

import java.io.Serializable;

public class GetHelplineNumberResponse implements Serializable {

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
     * data : {"email":"support@gunjan.com","mobile":"+1 11111111"}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        /**
         * email : support@gunjan.com
         * mobile : +1 11111111
         */

        private String email;
        private String mobile;
    }
}

package app.gunjan.entity;

import java.io.Serializable;

public class TermsResponse implements Serializable {
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
     * data : {"termAndConditions":"Term and conditions","image":"url"}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public String getTermAndConditions() {
            return termAndConditions;
        }

        public void setTermAndConditions(String termAndConditions) {
            this.termAndConditions = termAndConditions;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        /**
         * termAndConditions : Term and conditions
         * image : url
         */

        private String termAndConditions;
        private String image;
    }
}

package app.gunjan.entity;

import java.io.Serializable;

public class PrivacyPolicyResponse implements Serializable {

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
     * data : {"privacyAndPolicy":"Privacy And Policy","image":"url "}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public String getPrivacyAndPolicy() {
            return privacyAndPolicy;
        }

        public void setPrivacyAndPolicy(String privacyAndPolicy) {
            this.privacyAndPolicy = privacyAndPolicy;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        /**
         * privacyAndPolicy : Privacy And Policy
         * image : url
         */

        private String privacyAndPolicy;
        private String image;
    }
}

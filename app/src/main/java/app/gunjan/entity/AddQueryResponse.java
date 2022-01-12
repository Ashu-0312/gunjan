package app.gunjan.entity;

import java.io.Serializable;

public class AddQueryResponse implements Serializable {

    /**
     * code : 1
     * message : Your query submitted successfully , we will contact you soon
     * data : {}
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
    }
}

package app.gunjan.entity;

import java.io.Serializable;

public class NotificationCountResponse implements Serializable {

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
     * message : Notification count
     * data : {"notification":7}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public int getNotification() {
            return notification;
        }

        public void setNotification(int notification) {
            this.notification = notification;
        }

        /**
         * notification : 7
         */

        private int notification;
    }
}

package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class NotificationListResponse implements Serializable {

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
     * message : Notification list
     * data : {"notification":[{"id":2,"userId":2,"title":"Community Request","device_token":"","device_type":"ios","body":"first name sent you a community request!","data":"{\"title\":\"Community Request\",\"body\":\"first name sent you a community request!\",\"type\":\"community\"}","notification_type":"community","isRead":true,"createdAt":"2022-01-11T10:53:20.000Z"},{"id":1,"userId":2,"title":"Community Request","device_token":"","device_type":"ios","body":"null sent you a community request!","data":"{\"title\":\"Community Request\",\"body\":\"null sent you a community request!\",\"type\":\"friend\"}","notification_type":"friend","isRead":true,"createdAt":"2022-01-11T10:43:16.000Z"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<NotificationBean> getNotification() {
            return notification;
        }

        public void setNotification(List<NotificationBean> notification) {
            this.notification = notification;
        }

        private List<NotificationBean> notification;

        public static class NotificationBean implements Serializable {
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getNotification_type() {
                return notification_type;
            }

            public void setNotification_type(String notification_type) {
                this.notification_type = notification_type;
            }

            public boolean isRead() {
                return isRead;
            }

            public void setRead(boolean read) {
                isRead = read;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            /**
             * id : 2
             * userId : 2
             * title : Community Request
             * device_token :
             * device_type : ios
             * body : first name sent you a community request!
             * data : {"title":"Community Request","body":"first name sent you a community request!","type":"community"}
             * notification_type : community
             * isRead : true
             * createdAt : 2022-01-11T10:53:20.000Z
             */

            private int id;
            private int userId;
            private String title;
            private String device_token;
            private String device_type;
            private String body;
            private String data;
            private String notification_type;
            private boolean isRead;
            private String createdAt;
        }
    }
}

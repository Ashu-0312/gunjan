package app.gunjan.entity;

import java.io.Serializable;

public class AddCoinResponse implements Serializable {

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
     * data : {"total_available_coins":500}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public int getTotal_available_coins() {
            return total_available_coins;
        }

        public void setTotal_available_coins(int total_available_coins) {
            this.total_available_coins = total_available_coins;
        }

        /**
         * total_available_coins : 500
         */

        private int total_available_coins;
    }
}

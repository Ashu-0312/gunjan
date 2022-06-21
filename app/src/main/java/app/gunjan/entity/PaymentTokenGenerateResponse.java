package app.gunjan.entity;

import java.io.Serializable;

public class PaymentTokenGenerateResponse implements Serializable {

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    /**
     * code : 1
     * message : success
     * data : {"data":{"tokenData":"cJ9JCN4MzUIJiOicGbhJCLiQ1VKJiOiAXe0Jye.nX0nI4MWO1YDNlBTNxImM2IiOiQHbhN3XiwCO0ADO4cTN1YTM6ICc4VmIsIiUOlkI6ISej5WZyJXdDJXZkJ3biwiIwAzMiojI05Wdv1WQyVGZy9mIsICO4UzNyITNxITNyITMfJXZkJ3biojIklkclRmcvJye.uhOJojmyCGvLtrkl2KTKwpmxj_JhQOKgO4j2ulZXP8KQt7Tnj4D14Qo7o9xyAPxFba","orderId":"order_1225215227588","orderAmount":"300","orderCurrency":"INR","orderNote":"Testing","customerEmail":null,"customerName":"Ashutosh","customerPhone":"8299727845"}}
     */

    private int code;
    private String message;
    private DataBeanX data;

    public static class DataBeanX implements Serializable {
        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        /**
         * data : {"tokenData":"cJ9JCN4MzUIJiOicGbhJCLiQ1VKJiOiAXe0Jye.nX0nI4MWO1YDNlBTNxImM2IiOiQHbhN3XiwCO0ADO4cTN1YTM6ICc4VmIsIiUOlkI6ISej5WZyJXdDJXZkJ3biwiIwAzMiojI05Wdv1WQyVGZy9mIsICO4UzNyITNxITNyITMfJXZkJ3biojIklkclRmcvJye.uhOJojmyCGvLtrkl2KTKwpmxj_JhQOKgO4j2ulZXP8KQt7Tnj4D14Qo7o9xyAPxFba","orderId":"order_1225215227588","orderAmount":"300","orderCurrency":"INR","orderNote":"Testing","customerEmail":null,"customerName":"Ashutosh","customerPhone":"8299727845"}
         */

        private DataBean data;

        public static class DataBean implements Serializable {
            public String getTokenData() {
                return tokenData;
            }

            public void setTokenData(String tokenData) {
                this.tokenData = tokenData;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(String orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getOrderCurrency() {
                return orderCurrency;
            }

            public void setOrderCurrency(String orderCurrency) {
                this.orderCurrency = orderCurrency;
            }

            public String getOrderNote() {
                return orderNote;
            }

            public void setOrderNote(String orderNote) {
                this.orderNote = orderNote;
            }

            public String getCustomerEmail() {
                return customerEmail;
            }

            public void setCustomerEmail(String customerEmail) {
                this.customerEmail = customerEmail;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            /**
             * tokenData : cJ9JCN4MzUIJiOicGbhJCLiQ1VKJiOiAXe0Jye.nX0nI4MWO1YDNlBTNxImM2IiOiQHbhN3XiwCO0ADO4cTN1YTM6ICc4VmIsIiUOlkI6ISej5WZyJXdDJXZkJ3biwiIwAzMiojI05Wdv1WQyVGZy9mIsICO4UzNyITNxITNyITMfJXZkJ3biojIklkclRmcvJye.uhOJojmyCGvLtrkl2KTKwpmxj_JhQOKgO4j2ulZXP8KQt7Tnj4D14Qo7o9xyAPxFba
             * orderId : order_1225215227588
             * orderAmount : 300
             * orderCurrency : INR
             * orderNote : Testing
             * customerEmail : null
             * customerName : Ashutosh
             * customerPhone : 8299727845
             */

            private String tokenData;
            private String orderId;
            private String orderAmount;
            private String orderCurrency;
            private String orderNote;
            private String customerEmail;
            private String customerName;
            private String customerPhone;
        }
    }
}

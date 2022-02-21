package app.gunjan.entity;

import java.io.Serializable;

public class GenerateTokenResponse implements Serializable {

    /**
     * code : 1
     * message : Token generated successfully
     * data : {"token":{"identity":32,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzVjNGUxNDY5NWRjZDEwYTJmNTYxZDMxMjI5YTEzYmUyLTE2NDU0Mzg0NTMiLCJncmFudHMiOnsiaWRlbnRpdHkiOiIzMiIsImNoYXQiOnsic2VydmljZV9zaWQiOiJJU2EwZTUwODFkZTYzMDQ1YTk5NDBhYzI0Y2M1Zjc2ZWI1IiwiZW5kcG9pbnRfaWQiOiJGYW1pbHlIaXZlczozMiIsInB1c2hfY3JlZGVudGlhbF9zaWQiOiJJUzQxMDQyM2U4YzZhMWYyMGVhMWYxNDY1MmQ1MzlmNWFhIn19LCJpYXQiOjE2NDU0Mzg0NTMsImV4cCI6MTY0NTQ0MjA1MywiaXNzIjoiU0s1YzRlMTQ2OTVkY2QxMGEyZjU2MWQzMTIyOWExM2JlMiIsInN1YiI6IkFDMTJjNWUzMDQzMWYzMTVkNDgzNDMzNDc2YmIwNzIzNzYifQ.TEoeom3MbMkVIDxkIIqHxn3IJhFa4r6vnCkGb8gXkrA","device":"","token_type":"chat","room":""}}
     */

    private int code;
    private String message;

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

    private DataBean data;

    public static class DataBean implements Serializable {
        public TokenBean getToken() {
            return token;
        }

        public void setToken(TokenBean token) {
            this.token = token;
        }

        /**
         * token : {"identity":32,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzVjNGUxNDY5NWRjZDEwYTJmNTYxZDMxMjI5YTEzYmUyLTE2NDU0Mzg0NTMiLCJncmFudHMiOnsiaWRlbnRpdHkiOiIzMiIsImNoYXQiOnsic2VydmljZV9zaWQiOiJJU2EwZTUwODFkZTYzMDQ1YTk5NDBhYzI0Y2M1Zjc2ZWI1IiwiZW5kcG9pbnRfaWQiOiJGYW1pbHlIaXZlczozMiIsInB1c2hfY3JlZGVudGlhbF9zaWQiOiJJUzQxMDQyM2U4YzZhMWYyMGVhMWYxNDY1MmQ1MzlmNWFhIn19LCJpYXQiOjE2NDU0Mzg0NTMsImV4cCI6MTY0NTQ0MjA1MywiaXNzIjoiU0s1YzRlMTQ2OTVkY2QxMGEyZjU2MWQzMTIyOWExM2JlMiIsInN1YiI6IkFDMTJjNWUzMDQzMWYzMTVkNDgzNDMzNDc2YmIwNzIzNzYifQ.TEoeom3MbMkVIDxkIIqHxn3IJhFa4r6vnCkGb8gXkrA","device":"","token_type":"chat","room":""}
         */

        private TokenBean token;

        public static class TokenBean implements Serializable {
            public int getIdentity() {
                return identity;
            }

            public void setIdentity(int identity) {
                this.identity = identity;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
            }

            public String getToken_type() {
                return token_type;
            }

            public void setToken_type(String token_type) {
                this.token_type = token_type;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }

            /**
             * identity : 32
             * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzVjNGUxNDY5NWRjZDEwYTJmNTYxZDMxMjI5YTEzYmUyLTE2NDU0Mzg0NTMiLCJncmFudHMiOnsiaWRlbnRpdHkiOiIzMiIsImNoYXQiOnsic2VydmljZV9zaWQiOiJJU2EwZTUwODFkZTYzMDQ1YTk5NDBhYzI0Y2M1Zjc2ZWI1IiwiZW5kcG9pbnRfaWQiOiJGYW1pbHlIaXZlczozMiIsInB1c2hfY3JlZGVudGlhbF9zaWQiOiJJUzQxMDQyM2U4YzZhMWYyMGVhMWYxNDY1MmQ1MzlmNWFhIn19LCJpYXQiOjE2NDU0Mzg0NTMsImV4cCI6MTY0NTQ0MjA1MywiaXNzIjoiU0s1YzRlMTQ2OTVkY2QxMGEyZjU2MWQzMTIyOWExM2JlMiIsInN1YiI6IkFDMTJjNWUzMDQzMWYzMTVkNDgzNDMzNDc2YmIwNzIzNzYifQ.TEoeom3MbMkVIDxkIIqHxn3IJhFa4r6vnCkGb8gXkrA
             * device :
             * token_type : chat
             * room :
             */

            private int identity;
            private String token;
            private String device;
            private String token_type;
            private String room;
        }
    }
}

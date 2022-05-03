package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class PincodeListResponse implements Serializable {

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
     * data : {"pincodes":[{"officeName":"Bhagalpur S.O","pincode":274602,"taluk":"NA","districtName":"Deoria","stateName":"UTTAR PRADESH"},{"officeName":"Bhagalpur B.O","pincode":722141,"taluk":"Kotalpur","districtName":"Bankura","stateName":"WEST BENGAL"},{"officeName":"Bhagalpur H.O","pincode":812001,"taluk":"Jagdishpur","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Bhagalpur City S.O","pincode":812002,"taluk":"Jagdishpur","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Jagdishpur S.O (Bhagalpur)","pincode":813105,"taluk":"Jagdishpur","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Habibpur S.O (Bhagalpur)","pincode":813113,"taluk":"Jagdishpur","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Sangrampur S.O (Bhagalpur)","pincode":813212,"taluk":"Sangrampur","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Sultanganj S.O (Bhagalpur)","pincode":813213,"taluk":"Sultanganj","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Bhagalpur Devgaon B.O","pincode":813221,"taluk":"Tarapur","districtName":"Munger","stateName":"BIHAR"},{"officeName":"Mathurapur S.O (Bhagalpur)","pincode":813222,"taluk":"Kahalgaon","districtName":"Bhagalpur","stateName":"BIHAR"},{"officeName":"Narayanpur S.O (Bhagalpur)","pincode":853203,"taluk":"Narayanpur","districtName":"Bhagalpur","stateName":"BIHAR"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<PincodesBean> getPincodes() {
            return pincodes;
        }

        public void setPincodes(List<PincodesBean> pincodes) {
            this.pincodes = pincodes;
        }

        private List<PincodesBean> pincodes;
        public static class PincodesBean implements Serializable {
            public String getOfficeName() {
                return officeName;
            }

            public void setOfficeName(String officeName) {
                this.officeName = officeName;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getTaluk() {
                return taluk;
            }

            public void setTaluk(String taluk) {
                this.taluk = taluk;
            }

            public String getDistrictName() {
                return districtName;
            }

            public void setDistrictName(String districtName) {
                this.districtName = districtName;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }

            /**
             * officeName : Bhagalpur S.O
             * pincode : 274602
             * taluk : NA
             * districtName : Deoria
             * stateName : UTTAR PRADESH
             */

            private String officeName;
            private String pincode;
            private String taluk;
            private String districtName;
            private String stateName;
        }
    }
}

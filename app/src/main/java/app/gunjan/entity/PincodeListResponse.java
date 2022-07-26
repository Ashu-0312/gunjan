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
     * data : {"pincodes":[{"id":4476,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Gudur Division","officeName":"Adurupalli B.O","pincode":"524342","officeType":"BO","delivery":"Delivery","district":"NELLORE","stateName":"Andhra Pradesh"}]}
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
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCircleName() {
                return circleName;
            }

            public void setCircleName(String circleName) {
                this.circleName = circleName;
            }

            public String getRegionName() {
                return regionName;
            }

            public void setRegionName(String regionName) {
                this.regionName = regionName;
            }

            public String getDivisionName() {
                return divisionName;
            }

            public void setDivisionName(String divisionName) {
                this.divisionName = divisionName;
            }

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

            public String getOfficeType() {
                return officeType;
            }

            public void setOfficeType(String officeType) {
                this.officeType = officeType;
            }

            public String getDelivery() {
                return delivery;
            }

            public void setDelivery(String delivery) {
                this.delivery = delivery;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }

            /**
             * id : 4476
             * circleName : Andhra Pradesh Circle
             * regionName : Vijayawada Region
             * divisionName : Gudur Division
             * officeName : Adurupalli B.O
             * pincode : 524342
             * officeType : BO
             * delivery : Delivery
             * district : NELLORE
             * stateName : Andhra Pradesh
             */

            private int id;
            private String circleName;
            private String regionName;
            private String divisionName;
            private String officeName;
            private String pincode;
            private String officeType;
            private String delivery;
            private String district;
            private String stateName;
        }
    }
}

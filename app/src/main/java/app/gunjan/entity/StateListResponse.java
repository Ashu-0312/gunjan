package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class StateListResponse implements Serializable {

    /**
     * code : 1
     * message : State list
     * data : {"state_list":[{"id":52266,"circleName":"regionName","regionName":"divisionName","divisionName":"officeName","officeName":"pincode","pincode":"officeType","officeType":"delivery","delivery":"district","district":"StateName\r","stateName":null},{"id":1,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Anantapur Division","officeName":"A Narayanapuram B.O","pincode":"515004","officeType":"BO","delivery":"Delivery","district":"ANANTHAPUR","stateName":"Andhra Pradesh"},{"id":6744,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Tadepalligudem Division","officeName":"Dhumantunigudem B.O","pincode":"534313","officeType":"BO","delivery":"Delivery","district":"WEST GODAVARI","stateName":"Arunachal Pradesh"},{"id":10497,"circleName":"Assam Circle","regionName":"NA","divisionName":"Cachar Division","officeName":"Adarkona B.O","pincode":"788701","officeType":"BO","delivery":"Delivery","district":"KARIMGANJ","stateName":"Assam"},{"id":14507,"circleName":"Bihar Circle","regionName":"NA","divisionName":"AurangabadBihar Division","officeName":"Adai BO","pincode":"824207","officeType":"BO","delivery":"Delivery","district":"GAYA","stateName":"Bihar"},{"id":23596,"circleName":"Chattisgarh Circle","regionName":"NA","divisionName":"Bilaspur Division","officeName":"Achanakmar BO","pincode":"495113","officeType":"BO","delivery":"Delivery","district":"BILASPURCGH","stateName":"Chattisgarh"},{"id":36197,"circleName":"Gujarat Circle","regionName":"Vadodara Region","divisionName":"Valsad Division","officeName":"Amboli BO","pincode":"396230","officeType":"BO","delivery":"Delivery","district":"DADRA  NAGAR HAVELI","stateName":"Dadra and Nagar Hav."},{"id":31292,"circleName":"Gujarat Circle","regionName":"Rajkot Region","divisionName":"Junagadh Division","officeName":"Bucharvada BO","pincode":"362570","officeType":"BO","delivery":"Delivery","district":"DIU","stateName":"Daman and Diu"},{"id":27027,"circleName":"Delhi Circle","regionName":"NA","divisionName":"Delhi East Division","officeName":"Anand Vihar SO","pincode":"110092","officeType":"SO","delivery":"Non Delivery","district":"EAST DELHI","stateName":"Delhi"},{"id":20121,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Baskatti BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Goa"},{"id":17597,"circleName":"Bihar Circle","regionName":"East Region, Bhagalpur","divisionName":"Bhagalpur Division","officeName":"Rajpur BO","pincode":"813101","officeType":"BO","delivery":"Delivery","district":"BANKA","stateName":"Gujarat"},{"id":20210,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Habidih BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Haryana"},{"id":20223,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Itwa Shivnagar BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Himachal Pradesh"},{"id":20308,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Mahwa BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Jammu and Kashmir"},{"id":43695,"circleName":"Jharkhand Circle","regionName":"NA","divisionName":"Dhanbad Division","officeName":"ACC Colony SO","pincode":"828124","officeType":"SO","delivery":"Delivery","district":"DHANBAD","stateName":"Jharkhand"},{"id":20401,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Sher Bijulia BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Karnataka"},{"id":20408,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Sihaul BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Kerala"},{"id":20390,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Saho BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Madhya Pradesh"},{"id":20432,"circleName":"Bihar Circle","regionName":"Muzaffarpur Region","divisionName":"Darbhanga Division","officeName":"Tarwara BO","pincode":"847202","officeType":"BO","delivery":"Delivery","district":"DARBHANGA","stateName":"Maharashtra"},{"id":38840,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Daboda Khurd BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Manipur"},{"id":38857,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Dulhera BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Megalaya"},{"id":38871,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Goela Kalan BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Mizoram"},{"id":38912,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Kassar BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Nagaland"},{"id":38921,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Kharman BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Odisha"},{"id":38944,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Lowa Kalan BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Punjab"},{"id":38945,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Luksar BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Rajasthan"},{"id":38981,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Noona Majra BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Sikkim"},{"id":39008,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Sankhol BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Tamil Nadu"},{"id":8916,"circleName":"Andhra Pradesh Circle","regionName":"Visakhapatnam Region","divisionName":"Parvathipuram Division","officeName":"Komatipalli B.O","pincode":"535559","officeType":"BO","delivery":"Delivery","district":"VIZIANAGARAM","stateName":"Telangana"},{"id":39018,"circleName":"Haryana Circle","regionName":"NA","divisionName":"Rohtak Division","officeName":"Soldha BO","pincode":"124508","officeType":"BO","delivery":"Delivery","district":"JHAJJAR","stateName":"Tripura"},{"id":14719,"circleName":"Bihar Circle","regionName":"NA","divisionName":"AurangabadBihar Division","officeName":"Kataiya","pincode":"824202","officeType":"BO","delivery":"Delivery","district":"AURANGABAD","stateName":"Uttar Pradesh"},{"id":44614,"circleName":"Jharkhand Circle","regionName":"NA","divisionName":"Hazaribagh Division","officeName":"Salgawan BO","pincode":"825302","officeType":"BO","delivery":"Delivery","district":"HAZARIBAG","stateName":"Uttarakhand"}]}
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
        public List<StateListBean> getState_list() {
            return state_list;
        }

        public void setState_list(List<StateListBean> state_list) {
            this.state_list = state_list;
        }

        private List<StateListBean> state_list;

        public static class StateListBean implements Serializable {
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
             * id : 52266
             * circleName : regionName
             * regionName : divisionName
             * divisionName : officeName
             * officeName : pincode
             * pincode : officeType
             * officeType : delivery
             * delivery : district
             * district : StateName
             * stateName : null
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

package app.gunjan.entity;

import java.io.Serializable;
import java.util.List;

public class CityListResponse implements Serializable {

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
     * message : City list
     * data : {"city_list":[{"id":1792,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Hindupur Division","officeName":"Thumukunta Industrial Estate B.O","pincode":"515211","officeType":"BO","delivery":"Delivery","district":"ANANTAPUR","stateName":"Andhra Pradesh"},{"id":1,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Anantapur Division","officeName":"A Narayanapuram B.O","pincode":"515004","officeType":"BO","delivery":"Delivery","district":"ANANTHAPUR","stateName":"Andhra Pradesh"},{"id":1352,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Hindupur Division","officeName":"Achampalle B.O","pincode":"515621","officeType":"BO","delivery":"Delivery","district":"Ananthapur","stateName":"Andhra Pradesh"},{"id":5158,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Machilipatnam Division","officeName":"Salempalem B.O","pincode":"521328","officeType":"BO","delivery":"Delivery","district":"Avanigadda","stateName":"Andhra Pradesh"},{"id":3212,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Tirupati Division","officeName":"A.P.H.B.Colony B.O","pincode":"517507","officeType":"BO","delivery":"Non Delivery","district":"CHITTOOR","stateName":"Andhra Pradesh"},{"id":472,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Chittoor Division","officeName":"Adavibudugur B.O","pincode":"517425","officeType":"BO","delivery":"Delivery","district":"Chittoor","stateName":"Andhra Pradesh"},{"id":1650,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Hindupur Division","officeName":"Mundy Bazaar B.O","pincode":"515591","officeType":"BO","delivery":"Delivery","district":"Dharmavaram","stateName":"Andhra Pradesh"},{"id":6792,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Tadepalligudem Division","officeName":"Kondamodalu B.O","pincode":"534315","officeType":"BO","delivery":"Delivery","district":"East Godavari","stateName":"Andhra Pradesh"},{"id":4816,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Guntur Division","officeName":"A.P.Secretariat S.O","pincode":"522238","officeType":"SO","delivery":"Delivery","district":"GUNTUR","stateName":"Andhra Pradesh"},{"id":4815,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Guntur Division","officeName":"A.P.High Court S.O","pincode":"522237","officeType":"SO","delivery":"Non Delivery","district":"Guntur","stateName":"Andhra Pradesh"},{"id":1382,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Hindupur Division","officeName":"BIT College BO","pincode":"515202","officeType":"BO","delivery":"Delivery","district":"Hindupur","stateName":"Andhra Pradesh"},{"id":900,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Cuddapah Division","officeName":"Abbavaram B.O","pincode":"516270","officeType":"BO","delivery":"Delivery","district":"KADAPA","stateName":"Andhra Pradesh"},{"id":4223,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Gudivada Division","officeName":"Achavaram B.O","pincode":"521333","officeType":"BO","delivery":"Delivery","district":"KRISHNA","stateName":"Andhra Pradesh"},{"id":1946,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Kurnool Division","officeName":"Eddupenta B.O","pincode":"518222","officeType":"BO","delivery":"Delivery","district":"KURNOOL","stateName":"Andhra Pradesh"},{"id":6707,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Tadepalligudem Division","officeName":"Arikirevula B.O","pincode":"534340","officeType":"BO","delivery":"Delivery","district":"Kovvur","stateName":"Andhra Pradesh"},{"id":4326,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Gudivada Division","officeName":"Komatigunta Lock B.O","pincode":"521260","officeType":"BO","delivery":"Delivery","district":"Krishna","stateName":"Andhra Pradesh"},{"id":1824,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Kurnool Division","officeName":"A.Gokulapadu B.O","pincode":"518467","officeType":"BO","delivery":"Delivery","district":"Kurnool","stateName":"Andhra Pradesh"},{"id":5020,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Machilipatnam Division","officeName":"Chinnapuram B.O","pincode":"521003","officeType":"BO","delivery":"Delivery","district":"Machilipatnam","stateName":"Andhra Pradesh"},{"id":478,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Chittoor Division","officeName":"Angallu B.O","pincode":"517326","officeType":"BO","delivery":"Delivery","district":"Madanapalle","stateName":"Andhra Pradesh"},{"id":4476,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Gudur Division","officeName":"Adurupalli B.O","pincode":"524342","officeType":"BO","delivery":"Delivery","district":"NELLORE","stateName":"Andhra Pradesh"},{"id":5722,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Nellore Division","officeName":"Gummalladibba BO","pincode":"524137","officeType":"BO","delivery":"Delivery","district":"Nellore","stateName":"Andhra Pradesh"},{"id":2343,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Nandyal Division","officeName":"Akaveedu B.O","pincode":"523372","officeType":"BO","delivery":"Delivery","district":"PRAKASAM","stateName":"Andhra Pradesh"},{"id":3782,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Bhimavaram Division","officeName":"Kaza B.O","pincode":"534268","officeType":"BO","delivery":"Delivery","district":"Palakol","stateName":"Andhra Pradesh"},{"id":2553,"circleName":"Andhra Pradesh Circle","regionName":"Kurnool Region","divisionName":"Nandyal Division","officeName":"Kavalakuntla BO","pincode":"523327","officeType":"BO","delivery":"Delivery","district":"Prakasam","stateName":"Andhra Pradesh"},{"id":6774,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Tadepalligudem Division","officeName":"Kadakatla B.O","pincode":"534102","officeType":"BO","delivery":"Non Delivery","district":"Tadepalligudem","stateName":"Andhra Pradesh"},{"id":4023,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Eluru Division","officeName":"G.Pangidigudem B.O","pincode":"534455","officeType":"BO","delivery":"Delivery","district":"VIZIANAGARAM","stateName":"Andhra Pradesh"},{"id":3681,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Bhimavaram Division","officeName":"Adavipalem B.O","pincode":"534268","officeType":"BO","delivery":"Delivery","district":"WEST GODAVARI","stateName":"Andhra Pradesh"},{"id":6696,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Tadepalligudem Division","officeName":"Adavikolanu B.O","pincode":"534198","officeType":"BO","delivery":"Delivery","district":"Warangal","stateName":"Andhra Pradesh"},{"id":3679,"circleName":"Andhra Pradesh Circle","regionName":"Vijayawada Region","divisionName":"Bhimavaram Division","officeName":"A.Vemavaram B.O","pincode":"534267","officeType":"BO","delivery":"Delivery","district":"West Godavari","stateName":"Andhra Pradesh"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public static class DataBean implements Serializable {
        public List<CityListBean> getCity_list() {
            return city_list;
        }

        public void setCity_list(List<CityListBean> city_list) {
            this.city_list = city_list;
        }

        private List<CityListBean> city_list;

        public static class CityListBean implements Serializable {
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
             * id : 1792
             * circleName : Andhra Pradesh Circle
             * regionName : Kurnool Region
             * divisionName : Hindupur Division
             * officeName : Thumukunta Industrial Estate B.O
             * pincode : 515211
             * officeType : BO
             * delivery : Delivery
             * district : ANANTAPUR
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

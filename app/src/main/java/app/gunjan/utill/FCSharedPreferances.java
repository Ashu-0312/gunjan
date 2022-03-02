package app.gunjan.utill;

import android.content.Context;
import android.content.SharedPreferences;

public class FCSharedPreferances {

    private static SharedPreferences sharedPreferences;
    private static FCSharedPreferances fcSharedPreferances;
    private final String TOKEN="token";
    private final String PROFILE_STAGE="profilestage";
    private final String STATUS_LOGIN="statuslogin";
    private final String STATUS="status";
    private final String USER_ID="userid";
    private final String ACTIVE_COMMUNITY="active_community";
    private final String REASON_ID="reasonid";
    private final String DEVICE_ID="DEVICEID";
    private final String OTHER_ID="otherid";
    private final String CHAT_TOKEN="chattoken";
    private final String IS_ADMIN="isadmin";
    private final String IS_ACTIVE="isactive";
    private final String VALUE1="value1";
    private final String VALUE2="value2";

    private FCSharedPreferances(Context context){
        if(sharedPreferences==null){
            sharedPreferences = context.getSharedPreferences("app.gunjan", Context.MODE_PRIVATE);
        }
    }

    public static FCSharedPreferances getSharedPreferance(Context context){
        if(fcSharedPreferances!=null){
            return fcSharedPreferances;
        }else {
            return fcSharedPreferances=new FCSharedPreferances(context);
        }

    }

    public void setCHAT_TOKEN(String chat_token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHAT_TOKEN,chat_token);
        editor.commit();
        editor.apply();
    }

    public String getCHAT_TOKEN(){
        return sharedPreferences.getString(CHAT_TOKEN,"");
    }

    public void setVALUE1(String value1){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VALUE1,value1);
        editor.commit();
        editor.apply();
    }

    public String getVALUE1(){
        return sharedPreferences.getString(VALUE1,"");
    }

    public void setVALUE2(String value2){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VALUE2,value2);
        editor.commit();
        editor.apply();
    }

    public String getVALUE2(){
        return sharedPreferences.getString(VALUE2,"");
    }


    public void setIS_ADMIN(String is_admin){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_ADMIN,is_admin);
        editor.commit();
        editor.apply();
    }

    public String getIS_ADMIN(){
        return sharedPreferences.getString(IS_ADMIN,"");
    }

    public void setIS_ACTIVE(String is_active){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_ACTIVE,is_active);
        editor.commit();
        editor.apply();
    }

    public String getIS_ACTIVE(){
        return sharedPreferences.getString(IS_ACTIVE,"");
    }

    public void setTOKEN(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN,token);
        editor.commit();
        editor.apply();
    }

    public String getREASON_ID(){
        return sharedPreferences.getString(REASON_ID,"");
    }

    public void setREASON_ID(String reason_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REASON_ID,reason_id);
        editor.commit();
        editor.apply();
    }

    public String getOTHER_ID(){
        return sharedPreferences.getString(OTHER_ID,"");
    }

    public void setOTHER_ID(String other_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OTHER_ID,other_id);
        editor.commit();
        editor.apply();
    }

    public String getTOKEN(){
        return sharedPreferences.getString(TOKEN,"");
    }

    public void setACTIVE_COMMUNITY(String active_community){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACTIVE_COMMUNITY,active_community);
        editor.commit();
        editor.apply();
    }

    public String getACTIVE_COMMUNITY(){
        return sharedPreferences.getString(ACTIVE_COMMUNITY,"");
    }

    public void setDEVICE_ID(String device_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_ID,device_id);
        editor.commit();
        editor.apply();
    }

    public String getDEVICE_ID(){
        return sharedPreferences.getString(DEVICE_ID,"");
    }

    public void setUSER_ID(String user_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID,user_id);
        editor.commit();
        editor.apply();
    }

    public String getUSER_ID(){
        return sharedPreferences.getString(USER_ID,"");
    }

    public void setSTATUS(String status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STATUS,status);
        editor.commit();
        editor.apply();
    }

    public String getSTATUS(){
        return sharedPreferences.getString(STATUS,"");
    }

    public void setPROFILE_STAGE(String profile_stage){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_STAGE,profile_stage);
        editor.commit();
        editor.apply();
    }

    public String getPROFILE_STAGE(){
        return sharedPreferences.getString(PROFILE_STAGE,"");
    }

    public void setSTATUS_LOGIN(String status_login){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STATUS_LOGIN,status_login);
        editor.commit();
        editor.apply();
    }

    public String getSTATUS_LOGIN(){
        return sharedPreferences.getString(STATUS_LOGIN,"");
    }
}

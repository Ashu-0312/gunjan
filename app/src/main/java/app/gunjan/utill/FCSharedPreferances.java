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
    private final String OTHER_ID="otherid";

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

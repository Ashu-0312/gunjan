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

    public String getTOKEN(){
        return sharedPreferences.getString(TOKEN,"");
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

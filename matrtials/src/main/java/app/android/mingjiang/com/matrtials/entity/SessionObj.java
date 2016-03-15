package app.android.mingjiang.com.matrtials.entity;

import com.google.gson.Gson;

/**
 *
 */
public class SessionObj {
    public String session_id="";
    public String error = "";
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

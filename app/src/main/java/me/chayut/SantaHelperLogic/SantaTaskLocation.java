package me.chayut.SantaHelperLogic;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chayut on 25/03/16.
 */
public class SantaTaskLocation extends SantaTask implements Parcelable  {

    private static final String TAG = "SantaTaskLocation";

    public SantaTaskLocation(SantaAction mAction, float latitude, float longitude, float mRange) {
        this.mAction = mAction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mRange = mRange;
    }

    private SantaAction mAction;
    private float latitude = 0.0f;
    private float longitude =0.0f ;
    private float mRange = 0.0f;

    public SantaTaskLocation(SantaAction action) {
        mAction =action;
    }


    @Override
    public JSONObject toJSONObject (){

        JSONObject mObject = new JSONObject();

        try{
            mObject.put(SantaLogic.JTAG_SANTA_TASK_TYPE,SantaLogic.JTAG_SANTA_TASK_LOC);
            mObject.put(SantaLogic.JTAG_SANTA_LAT,latitude);
            mObject.put(SantaLogic.JTAG_SANTA_LONG,longitude);
            mObject.put(SantaLogic.JTAG_SANTA_Range,mRange);
            Gson gson = new Gson();
            mObject.put(SantaLogic.JTAG_SANTA_ACTION,new JSONObject(gson.toJson(mAction)));
            Log.d(TAG,gson.toJson(mAction));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return  mObject;
    }

    /** Setter and Getter */
    public SantaAction getAction() {
        return mAction;
    }

    public void setAction(SantaAction mAction) {
        this.mAction = mAction;
    }

    public boolean isConditionMet(Location loc){

        if(SantaUtilities.getDistance(loc.getLongitude(),loc.getLatitude(),longitude,latitude) < mRange){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mAction, flags);
        dest.writeFloat(this.latitude);
        dest.writeFloat(this.longitude);
        dest.writeFloat(this.mRange);
    }

    protected SantaTaskLocation(Parcel in) {
        this.mAction = in.readParcelable(SantaAction.class.getClassLoader());
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.mRange = in.readFloat();
    }

    public static final Creator<SantaTaskLocation> CREATOR = new Creator<SantaTaskLocation>() {
        @Override
        public SantaTaskLocation createFromParcel(Parcel source) {
            return new SantaTaskLocation(source);
        }

        @Override
        public SantaTaskLocation[] newArray(int size) {
            return new SantaTaskLocation[size];
        }
    };
}

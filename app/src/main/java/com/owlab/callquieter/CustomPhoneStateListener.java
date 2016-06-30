package com.owlab.callquieter;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * Created by ernest on 6/29/16.
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    public static final String TAG = CustomPhoneStateListener.class.getSimpleName();

    Context ctx;
    AudioManager audioManager;

    int ringerMode = -1;

    public CustomPhoneStateListener(Context ctx) {
        this.ctx = ctx;
        audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfoList) {
        super.onCellInfoChanged(cellInfoList);
        Log.d(TAG, "onCellInfoChanged: " + cellInfoList);
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);

        switch(direction) {
            case TelephonyManager.DATA_ACTIVITY_NONE:
                Log.d(TAG, "onDataActivity: DATA_ACTIVITY_NONE");
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                Log.d(TAG, "onDataActivity: DATA_ACTIVITY_IN");
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                Log.d(TAG, "onDataActivity: DATA_ACTIVITY_OUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                Log.d(TAG, "onDataActivity: DATA_ACTIVITY_INOUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                Log.d(TAG, "onDataActivity: DATA_ACTIVITY_DORMANT");
                break;
            default:
                Log.w(TAG, "onDataActivity: UNKNOWN " + direction);
                break;
        }
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        Log.d(TAG, "onServiceStateChanged: " + serviceState.toString());

        switch (serviceState.getState()) {
            case ServiceState.STATE_IN_SERVICE:
                Log.d(TAG, "onServiceStateChanged: STATE_IN_SERVICE");
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                Log.d(TAG, "onServiceStateChanged: STATE_OUT_OF_SERVICE");
                break;
            case ServiceState.STATE_EMERGENCY_ONLY:
                Log.d(TAG, "onServiceStateChanged: STATE_EMERGENCY_ONLY");
                break;
            case ServiceState.STATE_POWER_OFF:
                Log.d(TAG, "onServiceStateChanged: STATE_POWER_OFF");
                break;
        }
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        //super.onCallStateChanged(state, incomingNumber);
        Log.d(TAG, ">>>>> incomingNumber: " + incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "onCallStateChanged: CALL_STATE_IDLE");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                boolean incoming = true;
                long callTime = System.currentTimeMillis();
                Date newDate = new Date();
                Log.d(TAG, ">>>>> callTime: " + callTime);
                //Log.d(TAG, "onCallStateChanged: CALL_STATE_RINGING");
                //Toast.makeText(ctx, "to silence", Toast.LENGTH_SHORT).show();
                //audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                //if(incomingNumber.equals("777")) {
                //    Log.d(TAG, "matching number found");
                //} else {
                //    Log.d(TAG, "no matching number");
                //    Toast.makeText(ctx, "to normal", Toast.LENGTH_SHORT).show();
                //    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                //}
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "onCallStateChanged: CALL_STATE_OFFHOOK");
                break;
            default:
                Log.d(TAG, "onCallStateChanged: UNKNOWN_STATE: " + state);
                break;
        }


    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        if (location instanceof GsmCellLocation) {
            GsmCellLocation gcLoc = (GsmCellLocation) location;
            Log.d(TAG,
                    "onCellLocationChanged: GsmCellLocation "
                            + gcLoc.toString());
            Log.d(TAG, "onCellLocationChanged: GsmCellLocation getCid "
                    + gcLoc.getCid());
            Log.d(TAG, "onCellLocationChanged: GsmCellLocation getLac "
                    + gcLoc.getLac());
            Log.d(TAG, "onCellLocationChanged: GsmCellLocation getPsc"
                    + gcLoc.getPsc()); // Requires min API 9
        } else if (location instanceof CdmaCellLocation) {
            CdmaCellLocation ccLoc = (CdmaCellLocation) location;
            Log.d(TAG,
                    "onCellLocationChanged: CdmaCellLocation "
                            + ccLoc.toString());
            Log.d(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationId "
                            + ccLoc.getBaseStationId());
            Log.d(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLatitude "
                            + ccLoc.getBaseStationLatitude());
            Log.d(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLongitude"
                            + ccLoc.getBaseStationLongitude());
            Log.d(TAG,
                    "onCellLocationChanged: CdmaCellLocation getNetworkId "
                            + ccLoc.getNetworkId());
            Log.d(TAG,
                    "onCellLocationChanged: CdmaCellLocation getSystemId "
                            + ccLoc.getSystemId());
        } else {
            Log.d(TAG, "onCellLocationChanged: " + location.toString());
        }
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        super.onCallForwardingIndicatorChanged(cfi);
        Log.d(TAG, "onCallForwardingIndicatorChanged: " + cfi);
    }

    @Override
    public void onMessageWaitingIndicatorChanged(boolean mwi) {
        super.onMessageWaitingIndicatorChanged(mwi);
        Log.d(TAG, "onMessageWaitingIndicatorChanged: " + mwi);
    }
}

package com.nawin.navintelephonymanagertutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1=(TextView)findViewById(R.id.textView1);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
                TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                //Calling the methods of TelephonyManager the returns the information
                String IMEINumber=tm.getDeviceId();
                String subscriberID=tm.getDeviceId();
                String SIMSerialNumber=tm.getSimSerialNumber();
                String networkCountryISO=tm.getNetworkCountryIso();
                String SIMCountryISO=tm.getSimCountryIso();
                String softwareVersion=tm.getDeviceSoftwareVersion();
                String voiceMailNumber=tm.getVoiceMailNumber();

                //Get the phone type
                String strphoneType="";

                int phoneType=tm.getPhoneType();

                switch (phoneType)
                {
                    case (TelephonyManager.PHONE_TYPE_CDMA):
                        strphoneType="CDMA";
                        break;
                    case (TelephonyManager.PHONE_TYPE_GSM):
                        strphoneType="GSM";
                        break;
                    case (TelephonyManager.PHONE_TYPE_NONE):
                        strphoneType="NONE";
                        break;
                }

                //getting information if phone is in roaming
                boolean isRoaming=tm.isNetworkRoaming();

                String info="Phone Details:\n";
                info+="\n IMEI Number:"+IMEINumber;
                info+="\n SubscriberID:"+subscriberID;
                info+="\n Sim Serial Number:"+SIMSerialNumber;
                info+="\n Network Country ISO:"+networkCountryISO;
                info+="\n SIM Country ISO:"+SIMCountryISO;
                info+="\n Software Version:"+softwareVersion;
                info+="\n Voice Mail Number:"+voiceMailNumber;
                info+="\n Phone Network Type:"+strphoneType;
                info+="\n In Roaming? :"+isRoaming;

                textView1.setText(info);//displaying the information in the textView


            } else {

//If the app doesn’t have the CALL_PHONE permission, request it//

                requestPermission();
            }
        }


    }
    public boolean checkPermission() {

        int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);

        return CallPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        Manifest.permission.READ_PHONE_STATE
                }, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:


                if (grantResults.length > 0) {

                    boolean CallPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (CallPermission ) {

                        Toast.makeText(MainActivity.this,
                                "Permission accepted", Toast.LENGTH_LONG).show();


//If the permission is denied….//

                    } else {
                        Toast.makeText(MainActivity.this,

//...display the following toast...//

                                "Permission denied", Toast.LENGTH_LONG).show();

//...and disable the call button.//



                    }
                    break;
                }
        }
    }
}

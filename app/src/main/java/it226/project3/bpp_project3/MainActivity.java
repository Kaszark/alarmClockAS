package it226.project3.bpp_project3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    TextView textView;

    int mHour, mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        textView = (TextView) findViewById(R.id.timeTextView);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                mHour = hourOfDay;
                mMin = minute;
                if(mMin < 10) {
                    textView.setText(textView.getText().toString() + " " + mHour + ":0" + mMin);
                }
                else{
                    textView.setText(textView.getText().toString() + " " + mHour + ":" + mMin);
                }
            }
        });
    }

    public void setTimer(View v){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_Alarm = Calendar.getInstance();
        Calendar cal_Now = Calendar.getInstance();

        cal_Now.setTime(date);
        cal_Alarm.setTime(date);

        cal_Alarm.set(Calendar.HOUR_OF_DAY,mHour);
        cal_Alarm.set(Calendar.MINUTE,mMin);
        cal_Alarm.set(Calendar.SECOND,0);

        if(cal_Alarm.before(cal_Now)){
            cal_Alarm.add(Calendar.DATE,1);
        }

        Intent i = new Intent(MainActivity.this,BroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,24444,i,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal_Alarm.getTimeInMillis(),pendingIntent);
    }
}

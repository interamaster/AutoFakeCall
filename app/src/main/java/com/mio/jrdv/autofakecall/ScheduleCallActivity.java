package com.mio.jrdv.autofakecall;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.safety.audio_recorder.AudioListener;
import br.com.safety.audio_recorder.AudioRecordButton;
import br.com.safety.audio_recorder.AudioRecording;
import br.com.safety.audio_recorder.RecordingItem;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


//V03 añadido permiso para poder detcatr el marcado de 111 para volver a mostar icono en LAUNCHER!!!
//V032 AÑADIDO S6 XML
//v035 añadido anim android puro
//v038 añadido s6 con funcion ok menos ANIMACIOONES
//v04 añadido radiogrup para elegir Samsung o google, animacions de samsung ok(falla imagen del caller esta cuadarad y mal centrada) y quitar anim
//de android cuando se contesta
//v05 añadido radiogroup de tiempo rapido y coreegidas S6 foto redonda y nombre y numero ok
//v051 corregida anim android y timer s6
//v055 cooregido que seguia vibrando y sonando en samsun al rechazar y añadida logica grabacion..pte probar en dispositivo real
//v056 empezadoa cambiar el layout pral

public class ScheduleCallActivity extends AppCompatActivity implements SelectTimeFragment.IEventListener, SelectContactFragment.IEventListener {

    private static final int FILE_SELECT = 1002;

    private static final int HANA_UP_AFTER = 35;

    private static final int DURATION = 63;

    //para pedir acceso a contactos
    private static final int REQUEST_CODE_READ_CONTACTS = 99;
    //IDEM CALL LOGS

    private static final int REQUEST_CODE_CALL_LOGS = 199;

    //IDEM OUTGOING CALL

    private static final int REQUEST_CODE_OUTGOING_CALL = 299;

    //IDEM RECORD AUDIO

    private static final int REQUEST_CODE_RECORD_AUDIO = 399;


    Calendar calendar = null;

    String contactImage = null;

    String voice = null;

    EditText voiceInput = null;

    //RadioGroup callType;//no lo vpoy a usar ..solo hara fake calls

    RadioGroup BrandType;//para elegir Samsung o Google

    RadioGroup TimeFastSelection;//para elegir tiempo rapido





    //para el record audio


    private AudioRecordButton audioRecordButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule_call);


        //TODO new pedir permisos para Nougat de mejor manera
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////SILENCIO/////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////CONTACTOS/////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.READ_CONTACTS },
                    REQUEST_CODE_READ_CONTACTS);
        }


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////write call log/////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALL_LOG)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.WRITE_CALL_LOG },
                    REQUEST_CODE_CALL_LOGS);
        }




        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////detectatr outgoing call para vokver a poner icono///////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.PROCESS_OUTGOING_CALLS)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.PROCESS_OUTGOING_CALLS },
                    REQUEST_CODE_OUTGOING_CALL);
        }



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////detectatr RECORD AUDIO///////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(this,
                RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ RECORD_AUDIO },
                    REQUEST_CODE_RECORD_AUDIO);
        }

*/

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, READ_EXTERNAL_STORAGE},0);

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 0);



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


       // callType = (RadioGroup)findViewById(R.id.callTypeRadioGroup);//no lo vpoy a usar ..solo hara fake calls
        BrandType=(RadioGroup)findViewById(R.id.BrandTypeRadioGroup);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////RADIO BUTTON TIEMPO RAPIDO 10 SECS/1MIN / 5MIN//////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////


        final EditText timeInput = (EditText)findViewById(R.id.scheduleTimePicker);
        TimeFastSelection=(RadioGroup)findViewById(R.id.callTimeRadioGroup);
        TimeFastSelection.clearCheck();//para que no paraezca ninguno pulsdo por decfecto



        //le añadimos listener al radiogroup

        TimeFastSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                calendar= Calendar.getInstance();
                int cHour = calendar.get(Calendar.HOUR_OF_DAY);

                int cMinute = calendar.get(Calendar.MINUTE);

                int cSecs=calendar.get(Calendar.SECOND);




                if (checkedId == R.id.tensecs) {
                    //dañado 10 secs

                     calendar.add(Calendar.SECOND,10);

                } else  if (checkedId == R.id.oneminute) {
                    //añado 1 min

                    calendar.add(Calendar.SECOND,60);
                }
             else  if (checkedId == R.id.fiveminutes) {
                //añado 5 min

                    calendar.add(Calendar.SECOND,300);
            }




                Date scheduleTime = calendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);

                timeInput.setText(dateFormat.format(scheduleTime));
            }
        });





        voiceInput = (EditText)findViewById(R.id.voiceFileInput);

        voiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("audio/*");

                startActivityForResult(intent, FILE_SELECT);

            }
        });



        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////AUDIO RECORD//////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        //https://github.com/safetysystemtechnology/audio-recorder-button
        //https://stackoverflow.com/questions/11116051/how-can-i-record-voice-in-android-as-long-as-hold-a-button

        audioRecordButton = (AudioRecordButton) findViewById(R.id.audio_record_button);

        audioRecordButton.setOnAudioListener(new AudioListener() {
            @Override
            public void onStop(RecordingItem recordingItem) {
                Toast.makeText(getBaseContext(), "Audio...", Toast.LENGTH_SHORT).show();
                new AudioRecording(getBaseContext()).play(recordingItem);

                //n3cesitmaos una referencia a esa grabacion

                String RecordedFilePath=recordingItem.getFilePath();
                String RecordeName=recordingItem.getName();
                Log.d("INFO RECORD PATH",RecordedFilePath);//este lo lleva completo con nombre tambinne
                Log.d("INFO RECORD NAME",RecordeName);

                String Recorded=RecordedFilePath;//este lo lleva completo con nombre tambinne

                //lo guardamos en pref

                Myapplication.preferences.edit().putString(Myapplication.PREF_RECORDFILEPATHNAME,Recorded).commit();


                //actualziamos el path
                voiceInput.setText(Recorded);
                voice=Recorded;



            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Log.d("MainActivity", "Error: " + e.getMessage());
            }
        });



        //chequemaos si ya habia grabacion anterior

        //para que ponga el valor ya elegido en ele spinner
        String RecordFilealready = Myapplication.preferences.getString(Myapplication.PREF_RECORDFILEPATHNAME,"NO");//the value you want the position for

        if (!RecordFilealready.equals("NO")) {

            //ya habia algo antes actulaizamos el path


            voiceInput.setText(RecordFilealready);
            voice=RecordFilealready;

        }

        else {
            Log.d("NO RECORDING BEFORE!!","poseso");
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.settings_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        Intent intent;

        switch(menuItem.getItemId()) {
            case R.id.exitOption:

                finish();

                return true;

            case R.id.aboutOption:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle(R.string.about);

                alertDialogBuilder.setMessage(R.string.about_message);

                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);

                alertDialogBuilder.show();

                return true;

            case R.id.settingsOption:

                intent = new Intent(this, SettingsActivity.class);

                startActivity(intent);

                return true;

            case R.id.smsOption:

               // intent = new Intent(this, ScheduleSMSActivity.class);

                //startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    public void onClickSchedule(View view) {

        EditText nameInput = (EditText)findViewById(R.id.nameInput);

        EditText numberInput = (EditText)findViewById(R.id.numberInput);

        EditText timeInput = (EditText)findViewById(R.id.scheduleTimePicker);

        //EditText durationInput = (EditText)findViewById(R.id.callDurationInput);//no lo vpoy a usar ..la duration sera fija de 30 segs

        EditText hangUpAfterInput = (EditText)findViewById(R.id.hangUpAfterInput);//no lo vpoy a usar ..la duration sera fija de 30 segs

        String name = nameInput.getText().toString();

        String number = numberInput.getText().toString();

        String time = timeInput.getText().toString();

        //String duration = durationInput.getText().toString();//no lo vpoy a usar ..la duration sera fija de 30 segs
        String duration="30";

        String hangUpAfter = hangUpAfterInput.getText().toString();//no lo vpoy a usar ..la duration sera fija de 30 segs


        if (number.equals("")) {

            Toast.makeText(this, "Number can't be empty!", Toast.LENGTH_SHORT).show();

            return;

        }

        if (time.equals("")) {

            Toast.makeText(this, "Call time can't be empty", Toast.LENGTH_SHORT).show();

            return;

        }

        if (name.equals("")) {

            name = getResources().getString(R.string.unknown);

        }

        if (duration.equals("")) {

            duration = Integer.toString(DURATION);

        }

        if (hangUpAfter.equals("")) {

            hangUpAfter = Integer.toString(HANA_UP_AFTER);

        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////RADIO BUTTON TIPO LLAMADA//////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////

      //  RadioButton radioButton = (RadioButton)findViewById(callType.getCheckedRadioButtonId());//no lo vpoy a usar ..solo hara fake calls

       // int radioButtonIndex = callType.indexOfChild(radioButton);//no lo vpoy a usar ..solo hara fake calls

        ContentResolver contentResolver = getContentResolver();

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////RADIO BUTTON TIPO XML SAMSUNG/GOOGLE...//////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        RadioButton radioButtonBrandType = (RadioButton)findViewById(BrandType.getCheckedRadioButtonId());

        int radioButtonIndexBrandType = BrandType.indexOfChild(radioButtonBrandType);



       // if (radioButtonIndex == 0) {//no lo vpoy a usar ..solo hara fake calls

            if (radioButtonIndexBrandType == 0) {

                Intent intent = new Intent(this, S6caller.class);


                intent.putExtra("name", name);

                intent.putExtra("number", "Mobile " + number);

                intent.putExtra("contactImage", contactImage);

                intent.putExtra("duration", Integer.parseInt(duration));

                intent.putExtra("hangUpAfter", Integer.parseInt(hangUpAfter));

                intent.putExtra("voice", voice);

                final int fakeCallID = (int) System.currentTimeMillis();

                PendingIntent pendingIntent = PendingIntent.getActivity(this, fakeCallID, intent, PendingIntent.FLAG_ONE_SHOT);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(this, "Fake call scheduled", Toast.LENGTH_SHORT).show();

                finish();
            }

            else {


                Intent intent = new Intent(this, FakeRingerActivity.class);


                intent.putExtra("name", name);

                intent.putExtra("number", "Mobile " + number);

                intent.putExtra("contactImage", contactImage);

                intent.putExtra("duration", Integer.parseInt(duration));

                intent.putExtra("hangUpAfter", Integer.parseInt(hangUpAfter));

                intent.putExtra("voice", voice);

                final int fakeCallID = (int) System.currentTimeMillis();

                PendingIntent pendingIntent = PendingIntent.getActivity(this, fakeCallID, intent, PendingIntent.FLAG_ONE_SHOT);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(this, "Fake call scheduled", Toast.LENGTH_SHORT).show();

                finish();
            }

/*
//no lo vpoy a usar ..solo hara fake calls
        } else if (radioButtonIndex == 1) {

            CallLogUtilities.addCallToLog(contentResolver, number, Integer.parseInt(duration), CallLog.Calls.OUTGOING_TYPE, calendar.getTimeInMillis());

            Toast.makeText(this, "Fake outgoing call added to log", Toast.LENGTH_SHORT).show();

        } else if (radioButtonIndex == 2) {

            CallLogUtilities.addCallToLog(contentResolver, number, 0, CallLog.Calls.MISSED_TYPE, calendar.getTimeInMillis());

            Toast.makeText(this, "Fake missed call added to log", Toast.LENGTH_SHORT).show();

        }

*/


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("INFO","onactivityresult de ScheduleCallActivity: "+resultCode);

        if (resultCode != RESULT_OK) return;

        switch (requestCode) {

            case FILE_SELECT:

                voice = data.getDataString();

                voiceInput.setText(voice);

                break;

        }

    }

    @Override
    public void sendTime(Calendar calendar) {

        this.calendar = calendar;

    }

    @Override
    public void sendContactImage(String contactImage) {

        this.contactImage = contactImage;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

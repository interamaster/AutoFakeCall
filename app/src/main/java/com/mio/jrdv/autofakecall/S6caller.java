package com.mio.jrdv.autofakecall;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneNumberUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;

public class S6caller extends AppCompatActivity {


    private static final int INCOMING_CALL_NOTIFICATION = 1001;
    private static final int MISSED_CALL_NOTIFICATION = 1002;

   // private ImageButton callActionButton;
    private ImageButton answer;
    private ImageButton decline;
   // private ImageButton text;
    private ImageButton endCall;

    private ImageView contactPhoto;
    private ImageView loadingAnim;

    private ImageView ring;//TODO animacion flechas

    private  ImageView pulsadoBotonVerde;
    private  ImageView pulsadoBotonRojo;

    private TextView incomingTV;
    private Chronometer callDuration;

    private RelativeLayout main;

    private RelativeLayout callActionButtons;//el layout de la llmada
    private LinearLayout endCallLayout;//el layout de una vez respñondido)boton +, extravolumen,bluettoth...)

    private AudioManager audioManager;

    private long secs;

    private int duration;

    private String number;

    private String name;

    private String voice;

    private Ringtone ringtone;

    private Vibrator vibrator;

    private PowerManager.WakeLock wakeLock;

    private NotificationManager notificationManager;

    private ContentResolver contentResolver;

    private MediaPlayer voicePlayer;

    private Resources resources;

    private int currentRingerMode;

    private int currentRingerVolume;

    private String contactImageString;

    private int currentMediaVolume;

    final Handler handler = new Handler();

    private Runnable hangUP = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };







    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_s6caller);

        Window window = getWindow();

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);

        TextView phoneNumber = (TextView) findViewById(R.id.numberTV);

        TextView callerName = (TextView) findViewById(R.id.nameTV);







        contactPhoto = (ImageView)findViewById(R.id.circle_head_photo);

        contentResolver = getContentResolver();

        resources = getResources();

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "Tag");

        currentRingerMode = audioManager.getRingerMode();

        currentRingerVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);

        currentMediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //loslayputs de estar llamando o haber aceptado:
        callActionButtons = (RelativeLayout)findViewById(R.id.receive_layout);
        endCallLayout = (LinearLayout)findViewById(R.id.endcall_layout);

        //callActionButton = (ImageButton) findViewById(R.id.callActionButton);

        answer = (ImageButton) findViewById(R.id.receive);

        decline = (ImageButton) findViewById(R.id.reject);

        incomingTV = (TextView) findViewById(R.id.incomingTV);

        endCall = (ImageButton) findViewById(R.id.end_call);

        //callStatus = (TextView) findViewById(R.id.callStatus);

         callDuration = (Chronometer) findViewById(R.id.chronometer);

        main = (RelativeLayout) findViewById(R.id.mains6);

        ring = (ImageView) findViewById(R.id.ring);

        loadingAnim = (ImageView) findViewById(R.id.loading);

        pulsadoBotonRojo=(ImageView)findViewById(R.id.note5_big_red);
        pulsadoBotonVerde=(ImageView)findViewById(R.id.note5_big_blue);

        //mio anim TODO

     //   this.animcircular = (Android44ReceiveAnim) findViewById(R.id.receive_anim);
      //  this.animcircular.setVisibility(View.VISIBLE);

        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////


        //TODO quitar tars pruebas

        name = extras.getString("name");

        voice = extras.getString("voice", "");

        duration = extras.getInt("duration");

        number = extras.getString("number");

        contactImageString = extras.getString("contactImage");

        int hangUpAfter = extras.getInt("hangUpAfter");




                //TODO quitar tars pruebas
/*
        name ="name";

        voice =  "";

        duration = 5;

        number = "132456789";

        //contactImageString = extras.getString("contactImage");

        int hangUpAfter = 20;
*/

        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////

        getSupportActionBar().hide();

        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        wakeLock.setReferenceCounted(false);

        nBuilder.setSmallIcon(R.drawable.ic_call);

        nBuilder.setOngoing(true);

        nBuilder.setContentTitle(name);

        nBuilder.setColor(Color.rgb(4, 137, 209));

        nBuilder.setContentText(resources.getString(R.string.incoming_call));

        notificationManager.notify(INCOMING_CALL_NOTIFICATION, nBuilder.build());

        handler.postDelayed(hangUP, hangUpAfter * 1000);

        muteAll();

        setContactImage(true);



        ////////////////////////////////////////////////////////////////////////////////
        /////////////////////////listener boton VERDE//////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////


        answer.setOnTouchListener(new View.OnTouchListener() {

            float x1 = 0, x2 = 0, y1 = 0, y2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int a = event.getAction();

                if (a == MotionEvent.ACTION_DOWN) {

                    x1 = event.getX();

                    y1 = event.getY();

                  //  Log.d("info"," tocado boton aceptar x1:"+x1+"  y y1="+y1);




                    pulsadoBotonVerde.setVisibility(View.VISIBLE);



                } else if (a == MotionEvent.ACTION_MOVE) {

                    x2 = event.getX();

                    y2 = event.getY();


                  //  Log.d("info"," moviendo boton aceptar x2:"+x2+"  y y2="+y2);

                    if ((x2 - 200) > x1) {

                     //acepto llalamda

                      //  Log.d("INFO","llamada ACEPTADA");

                        wakeLock.acquire();
                        //oculto loading y m,ensake de incoming call
                        loadingAnim.setVisibility(View.INVISIBLE);
                        incomingTV.setVisibility(View.INVISIBLE);

                        //muestro el tienmpo que lleva

                        callDuration.setVisibility(View.VISIBLE);


                        //ocultamos layout de llmada y ponemps el de haber acpertado
                            callActionButtons.setVisibility(View.INVISIBLE);
                            endCallLayout.setVisibility(View.VISIBLE);

                        stopRinging();

                        playVoice();
/*
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                long min = (secs % 3600) / 60;

                                long seconds = secs % 60;

                                String dur = String.format(Locale.US, "%02d:%02d", min, seconds);

                                secs++;

                                callDuration.setText(dur);

                                handler.postDelayed(this, 1000);

                            }
                        }, 10);
**/
//quito handler y uso cronometer!!

                        Chronometer mChronometer=(Chronometer) findViewById(R.id.chronometer);
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                        mChronometer.start();


                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                finish();
                            }

                        }, duration * 1000);


                    }

                } else if (a == MotionEvent.ACTION_UP || a == MotionEvent.ACTION_CANCEL) {



                    //no hemos aceptado quitar le boton de cirvulo animado en boton verde



                    pulsadoBotonVerde.setVisibility(View.INVISIBLE);



                }

                return false;

            }
        });




        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////lsitener boton ROJO///////////////////////////
        ////////////////////////////////////////////////////////////////////////////////


        decline.setOnTouchListener(new View.OnTouchListener() {

            float x1 = 0, x2 = 0, y1 = 0, y2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int a = event.getAction();

                if (a == MotionEvent.ACTION_DOWN) {

                    x1 = event.getX();

                    y1 = event.getY();

                  //  Log.d("info"," tocado boton declkine x1:"+x1+"  y y1="+y1);



                    pulsadoBotonRojo.setVisibility(View.VISIBLE);



                } else if (a == MotionEvent.ACTION_MOVE) {

                    x2 = event.getX();

                    y2 = event.getY();


                  //  Log.d("info"," moviendo boton decline x2:"+x2+"  y y2="+y2);

                    if ((x2 + 200) < x1) {

                        //rechazo la  llalamda

              //  Log.d("INFO","llamada declina");

                        finish();

                    }

                } else if (a == MotionEvent.ACTION_UP || a == MotionEvent.ACTION_CANCEL) {



                    //no hemos aceptado quitar le boton de cirvulo animado en boton verde


                    pulsadoBotonRojo.setVisibility(View.INVISIBLE);



                }

                return false;

            }
        });


        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////








        Animation animCallStatusPulse = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.call_status_pulse);

       // callStatus.startAnimation(animCallStatusPulse);//TODO

        number = PhoneNumberUtils.formatNumber(number, "ET");

        phoneNumber.setText("Mobile " + number);

        callerName.setText(name);

        Uri ringtoneURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//usa el mismo tono que tenga el movil!!!


        ringtone = RingtoneManager.getRingtone(getApplicationContext(), ringtoneURI);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ringtone.play();

        long[] pattern = {1000, 1000, 1000, 1000, 1000};

        vibrator.vibrate(pattern, 0);



    }

    public void onClickEndCall(View view) {

        stopVoice();

        finish();

    }

    private void stopVoice() {

        if (voicePlayer != null && voicePlayer.isPlaying()) {
            voicePlayer.stop();
        }

    }

    private void stopRinging() {

        vibrator.cancel();

        ringtone.stop();




    }

    private void playVoice() {

        if (!voice.equals("")) {

            Uri voiceURI = Uri.parse(voice);

            voicePlayer = new MediaPlayer();

            try {
                voicePlayer.setDataSource(this, voiceURI);
            } catch (Exception e) {
                e.printStackTrace();
            }

            voicePlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);

            voicePlayer.prepareAsync();

            voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

        }

    }



    private void muteAll() {

        audioManager.setStreamMute(AudioManager.STREAM_ALARM, true);

        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);

    }

    private void unMuteAll() {

        audioManager.setStreamMute(AudioManager.STREAM_ALARM, false);

        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);

    }





    private void setContactImage(boolean tint) {

        if (!(contactImageString == null)) {

            Uri contactImageUri = Uri.parse(contactImageString);

            try {

                InputStream contactImageStream = contentResolver.openInputStream(contactImageUri);

                Drawable contactImage = Drawable.createFromStream(contactImageStream, contactImageUri.toString());

                if(tint) {
                    contactImage.setTint(getResources().getColor(R.color.contact_photo_tint));
                    contactImage.setTintMode(PorterDuff.Mode.DARKEN);
                }

                contactPhoto.setImageDrawable(contactImage);

            } catch (Exception e) {

            }


        }
    }




    // adds a missed call to the log and shows a notification
    private void missedCall() {

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);

        nBuilder.setSmallIcon(android.R.drawable.stat_notify_missed_call);

        nBuilder.setContentTitle(name);

        nBuilder.setContentText(resources.getString(R.string.missed_call));

        nBuilder.setColor(Color.rgb(4, 137, 209));

        nBuilder.setAutoCancel(true);

        Intent showCallLog = new Intent(Intent.ACTION_VIEW);

        showCallLog.setType(CallLog.Calls.CONTENT_TYPE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, showCallLog, PendingIntent.FLAG_CANCEL_CURRENT);

        nBuilder.setContentIntent(pendingIntent);

        showCallLog.setType(CallLog.Calls.CONTENT_TYPE);

        notificationManager.notify(MISSED_CALL_NOTIFICATION, nBuilder.build());

        CallLogUtilities.addCallToLog(contentResolver, number, 0, CallLog.Calls.MISSED_TYPE, System.currentTimeMillis());

    }

    private void incomingCall() {

        CallLogUtilities.addCallToLog(contentResolver, number, secs, CallLog.Calls.INCOMING_TYPE, System.currentTimeMillis());

    }


    @Override
    protected void onDestroy() {




        super.onDestroy();

        stopVoice();

        notificationManager.cancel(INCOMING_CALL_NOTIFICATION);

        if (secs > 0) {

            incomingCall();

        } else {

            missedCall();

        }

        wakeLock.release();

        audioManager.setRingerMode(currentRingerMode);

        audioManager.setStreamVolume(AudioManager.STREAM_RING, currentRingerVolume, 0);

        stopRinging();

        unMuteAll();

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentMediaVolume, 0);

    }


}

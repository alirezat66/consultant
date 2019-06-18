package greencode.ir.consulant.calling;

import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import greencode.ir.consulant.R;
import greencode.ir.consulant.retrofit.reqobject.CallUpdateReq;
import greencode.ir.consulant.services.SinchService;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Utility;

public class CallScreenActivity extends BaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();
    @BindView(R.id.callState)
    TextView callState;
    @BindView(R.id.callDuration)
    TextView callDuration;
    @BindView(R.id.img_remote)
    CircleImageView imgRemote;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.hangupButton)
    Button hangupButton;

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;

    private String mCallId;
    private int ourCall = 0;
    CallPresenter presenter;

    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callscreen);
        ButterKnife.bind(this);

        mAudioPlayer = new AudioPlayer(this);

        hangupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
        if(getIntent().getExtras().containsKey("ourcallId")) {
            ourCall = getIntent().getIntExtra("ourcallId",0);
            presenter = new CallPresenter();
            presenter.updateCall(new CallUpdateReq(Utility.getToken(),Utility.getId(),ourCall,"call",0,
                    0,"call start"));

        }
        Typeface tf = Utility.getRegularTypeFace(this);
        callState.setTypeface(tf);
        callDuration.setTypeface(tf);
        txtUserName.setTypeface(tf);
        hangupButton.setTypeface(tf);

    }

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            txtUserName.setText(call.getRemoteUserId());
            callState.setText(call.getState().toString());
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mDurationTask.cancel();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            if(presenter!=null) {
                presenter.updateCall(new CallUpdateReq(Utility.getToken(), Utility.getId(), ourCall, "DA", call.getDetails().getEstablishedTime(),
                        call.getDetails().getDuration(), call.getDetails().getEndCause().toString()));
            }
            call.hangup();


        }
        finish();
    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            callDuration.setText(formatTimespan(call.getDetails().getDuration()));
        }
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
           // Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            if(ourCall!=0){

            }
        //    String endMsg = "Call ended: " + call.getDetails().toString();
            Toast.makeText(CallScreenActivity.this, "مکالمه به پایان رسید.", Toast.LENGTH_LONG).show();
            endCall();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            callState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            if(presenter!=null) {
                presenter.updateCall(new CallUpdateReq(Utility.getToken(), Utility.getId(), ourCall, "call", 0,
                        0, "call established"));
            }
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

    }
}

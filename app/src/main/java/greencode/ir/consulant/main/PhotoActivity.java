package greencode.ir.consulant.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.jsibbold.zoomage.ZoomageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class PhotoActivity extends BaseActivity {
    @BindView(R.id.myImage)
    ZoomageView myImage;
    @BindView(R.id.lyBack)
    LinearLayout lyBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);
        ButterKnife.bind(this);
        String b64 = PreferencesData.getString("myb64", this);
        myImage.setImageBitmap(Utility.getBitmapFromBase(b64));
        lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}

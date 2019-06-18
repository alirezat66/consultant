package greencode.ir.consulant.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import greencode.ir.consulant.R;
import greencode.ir.consulant.objects.MenuItem;
import greencode.ir.consulant.objects.ProfileInfo;
import greencode.ir.consulant.utils.Utility;

public class MenuDialog extends BottomSheetDialog implements MenuAdapter.onItemClick {
    MenuDialogInterface myInterface;

    Context context;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.txt_User_Name)
    TextView txtUserName;
    @BindView(R.id.menu_recycle)
    RecyclerView menuRecycle;
    @BindView(R.id.imgClose)
    AppCompatImageView imgClose;
    ArrayList<MenuItem> list = new ArrayList<>();
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    ProfileInfo profile;

    public MenuDialog(Context context, ProfileInfo profile) {
        super(context);
        this.context = context;
        this.profile = profile;
    }

    public void setListener(MenuDialogInterface listener) {
        this.myInterface = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_main_menu, null);
        setContentView(view);
        setCancelable(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);
        ButterKnife.bind(this, view);
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.onProfileClick();
            }
        });

        list.add(new MenuItem(1, "مشاوره های من"));
        list.add(new MenuItem(2, "مدیریت مالی"));
        list.add(new MenuItem(3, "ارتباط با پشتیبانی"));
        list.add(new MenuItem(4, "حریم خصوصی"));
        list.add(new MenuItem(5, "درباره ما"));
        list.add(new MenuItem(6, "خروج"));
        MenuAdapter adapter = new MenuAdapter(list, this, context);
        menuRecycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        menuRecycle.setAdapter(adapter);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.onFinish();
            }
        });

        if(profile!=null){
            if(!(profile.getFName()+profile.getLName()).equals("")) {
                txtUserName.setText(profile.getFName() + " " + profile.getLName());
            }else {
                txtUserName.setText("کاربر گرامی");
            }
            if(profile.getUImg().equals("")){
                imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.myimage));

            }else {
                imgProfile.setImageBitmap(Utility.getBitmapFromBase(profile.getUImg()));
            }
        }else {
            imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.myimage));
            txtUserName.setText("کاربر گرامی");
        }
    }


    @Override
    public void onClick(int position) {
        myInterface.onChosenItem(list.get(position-1));
    }
}

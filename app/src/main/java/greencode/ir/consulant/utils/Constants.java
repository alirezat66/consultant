package greencode.ir.consulant.utils;

import android.os.Environment;

import java.io.File;


/**
 * Created by alireza on 1/21/2017.
 */

public class Constants {

    public  static  String PREF_USER_NAME ="userName";
    public  static  String PREF_USER_FAMILY ="userFamily";
    public  static  String PREF_USER_IMAGE ="userImage";
    public  static  String PREF_TOKEN ="token";
    public static String Pref_USER_ID="id";


    public static String PREF_MOAREF_CODE="moarefCode";
    public static String PREF_CODE="validCode";
    public static String PREF_FIRST="firstTime";
    public static String PREF_LOGIN="isLogin";

    public static String PREF_FIRST_PILL="firstPill";


    public static File ImagesDir =  new File(
            Environment.getExternalStoragePublicDirectory(
                    Environment.getRootDirectory().getParent()
            ),
            "/Pillcci/Images"
    );
    public static String PREF_REMIND_COUNT="reminderCount";
    public static String PREF_DISTANCE="distance";
    public static String PREF_VIBRATE="isvibrate";
    public static String PREF_LOGHT="isLight";




    public static String PREF_FIRST_EDIT="firstEdit";
    public static String WaitingTitle = "لطفا منتظر باشید";
    public static String WaitingDesc="در حال بررسی صلاحیت ورود...";
    public static String WaitingDescNormal = "درحال دریافت اطلاعات...";
    public static String Pref_USER_PHONE = "userphone";
    public static String PREF_Waited="isWaited";
    public static String Pref_USer_Active ="activeState";

    public static String Pref_User_State ="userState";
}

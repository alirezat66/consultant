package greencode.ir.consulant.addinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greencode.ir.consulant.R;
import greencode.ir.consulant.objects.AdviserLocation;
import greencode.ir.consulant.objects.Category;
import greencode.ir.consulant.objects.Course;
import greencode.ir.consulant.objects.CourseList;
import greencode.ir.consulant.objects.JobInfo;
import greencode.ir.consulant.objects.ListOfFreeTimes;
import greencode.ir.consulant.objects.MultiChoseObject;
import greencode.ir.consulant.objects.PersonalInfo;
import greencode.ir.consulant.objects.SingleChoseObject;
import greencode.ir.consulant.objects.UploadList;
import greencode.ir.consulant.retrofit.reqobject.AddInfoRequest;
import greencode.ir.consulant.retrofit.reqobject.CatReq;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.retrofit.respObject.Bank;
import greencode.ir.consulant.retrofit.respObject.DegreesName;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

public class ProfileAddInfoActivity extends BaseActivity implements StepOneFragment.onActionStepOne,StepTwoFragment.onActionStepTwo,StepThreeFragment.onActionStepThree,AddInfoInterface{
    @BindView(R.id.txt_step_title)
    TextView txtStepTitle;
    @BindView(R.id.txt_step)
    TextView txtStep;
    @BindView(R.id.container)
    LinearLayout container;
    ArrayList<Fragment> fragments = new ArrayList<>();

    static  PersonalInfo info;
    static  JobInfo jobInfo;
    static  UploadList uploadList;
    static ArrayList<SingleChoseObject> banks;
    static List<MultiChoseObject> cats = new ArrayList<>();
    static List<MultiChoseObject> selectedCats = new ArrayList<>();
    static List<SingleChoseObject> advocatedTypes = new ArrayList<>();
    static List<SingleChoseObject> degrees = new ArrayList<>();

    AddInfoPresenter presenter;
    KProgressHUD progressHUD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        ButterKnife.bind(this);

        presenter = new AddInfoPresenter(this);
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        makeBanks();



    }

    public static PersonalInfo getPersonalInfo(){
        return info;
    }
    public static JobInfo getJobInfo(){
        return jobInfo;
    }
    public static UploadList getListInfo(){
        return uploadList;
    }

    private void startFragment(int page) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(page)).commit();
        txtStep.setText("مرحله " + (page+1) +" از ۲");
        if(page==0){
            txtStepTitle.setText("تکمیل اطلاعات فردی");
        }else if(page==1){
            txtStepTitle.setText("تکمیل اطلاعات شغلی");
        }else {
            txtStepTitle.setText("بارگزاری مدارک هویتی");
        }
    }


    private  void makeBanks(){

        banks = new ArrayList<>();
        presenter.getBanks(0);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void addFragmentList() {
        fragments.add(new StepOneFragment());
        fragments.add(new StepTwoFragment());
      //  fragments.add(new StepThreeFragment());
    }

    @Override
    public void onSaveButtonOne(PersonalInfo info) {
        this.info = info;
        PreferencesData.saveString("info",info.toJson().toString(),this);
        finish();
    }

    @Override
    public void onNextButtonOne(PersonalInfo info) {
        this.info = info;
        startFragment(1);
    }

    @Override
    public void onSaveButtonThree(UploadList list) {
        this.uploadList = list;
        PreferencesData.saveString("info",info.toJson().toString(),this);
        PreferencesData.saveString("jobInfo",jobInfo.toJson().toString(),this);
        PreferencesData.saveString("uploads",uploadList.toJson().toString(),this);
        finish();

    }

    @Override
    public void onFinalButtonThree(UploadList list) {
        this.uploadList = list;

       if(checkValidation()){

            jobInfo.changeDays();
            progressHUD = Utility.makeWaiting(this);
            progressHUD.show();
           presenter.addInfo(new AddInfoRequest(Utility.getToken(),Utility.getId(),info.toJson().toString(),jobInfo.toJson().toString(),uploadList.toJson().toString()));

       }else {
           Toast.makeText(this, "بخشی از اطلاعات وارد شده، صحیح نمی باشند.", Toast.LENGTH_LONG).show();
       }

    }

    private boolean checkValidation() {
        if(info!=null && jobInfo!=null){
            if(validInfo()){
                if(validJob()){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    private boolean validUploads() {
        if(uploadList.getUploads().size()>2) {
            return true;
        }else {
            Toast.makeText(this, "وارد کردن کلیه اسناد بجز تصویر صفحه تمدید پروانه وکالت اجباری می باشد.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validJob() {
        if(
                !jobInfo.getJobTitleId().equals("")
               ){
            return true;
        }else {
            Toast.makeText(this, "وارد کردن نوع وکالت اجباری می باشد.", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private boolean validInfo() {
        if(info.getFirstName().length()>0 && info.getLastName().length()>0 && info.getNationalCode().length()==10 &&
                info.getShParvane().length()>0 ){
            return true;
        }else {
            Toast.makeText(this, "وارد نام و نام خانوادگی همچنین شماره پروانه و شماره ملی اجباری می باشد..", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onBackButtonThree() {
        startFragment(1);
    }

    @Override
    public void onSaveButtonTwo(JobInfo item) {
        this.jobInfo = item;
        PreferencesData.saveString("info",info.toJson().toString(),this);
        PreferencesData.saveString("jobInfo",jobInfo.toJson().toString(),this);
        finish();
    }

    @Override
    public void onNextButtonTwo(JobInfo item) {
        this.jobInfo = item;
        if(checkValidation()){
            jobInfo.changeDays();
            progressHUD = Utility.makeWaiting(this);
            progressHUD.show();
            uploadList = new UploadList();
            presenter.addInfo(new AddInfoRequest(Utility.getToken(),Utility.getId(),info.toJson().toString(),jobInfo.toJson().toString(),uploadList.toJson().toString()));

        }else {
            Toast.makeText(this, "بخشی از اطلاعات وارد شده، صحیح نمی باشند.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackButtonTwo() {
        startFragment(0);
    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        progressHUD.dismiss();
        PreferencesData.saveInt(Constants.Pref_USer_Active,2,this);
        Intent intent = new Intent(this,WaitedPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onreadyBanks(List<Bank> posts) {
        for(Bank bank : posts){
            banks.add(new SingleChoseObject(bank.getId(),bank.getName()));
        }

        presenter.getCats(new CatReq(Utility.getToken(),0,""));




    }

    public void getSavesData() {
      String stinfo  =PreferencesData.getString("info",this);
      String infoJob = PreferencesData.getString("jobInfo",this);
      String infoUploads =  PreferencesData.getString("uploads",this);
        Gson gson = new Gson() ;
        if(!stinfo.equals("")){
          info = gson.fromJson(stinfo,PersonalInfo.class);
      }
      if(!infoJob.equals("")){
            jobInfo = gson.fromJson(infoJob,JobInfo.class);
      }if(!infoUploads.equals("")){
            uploadList = gson.fromJson(infoUploads,UploadList.class);
      }
    }


    @Override
    public void onReadyCats(List<Category> posts) {
        cats.clear();
        for(Category cat : posts){
            cats.add(new MultiChoseObject(cat.getCatId(),cat.getCatName()));
        }
        advocatedTypes = new ArrayList<>();
        presenter.getAdvocacyTypes(0);
    }
    @Override
    public void onReadyAdvocacyType(List<AdvocancyTypeName> posts) {
        for(AdvocancyTypeName advocancyTypeName : posts){
            advocatedTypes.add(new SingleChoseObject(advocancyTypeName.getId(),advocancyTypeName.getName()));
        }
        degrees = new ArrayList<>();
        presenter.getDegreeTypeNames(0);
    }

    @Override
    public void onReadyDegrees(List<DegreesName> posts) {
        progressHUD.dismiss();
        for(DegreesName degree : posts){
            degrees.add(new SingleChoseObject(degree.getId(),degree.getName()));
        }

        addFragmentList();
        getSavesData();
        startFragment(0);


        //  presenter.getDegreeTypeNames(0);
    }


}

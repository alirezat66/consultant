package greencode.ir.consulant.addinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.EachHoursDialog;
import greencode.ir.consulant.dialog.EachHoursInterface;
import greencode.ir.consulant.dialog.ExprienceYearsDialog;
import greencode.ir.consulant.dialog.ExprienceYearsInterface;
import greencode.ir.consulant.dialog.MultiChoseDialog;
import greencode.ir.consulant.dialog.MultiChoserInterface;
import greencode.ir.consulant.dialog.QuestionDialog;
import greencode.ir.consulant.dialog.QuestionListener;
import greencode.ir.consulant.dialog.SingleChoseDialog;
import greencode.ir.consulant.dialog.SingleChoserInterface;
import greencode.ir.consulant.objects.AdviserLocation;
import greencode.ir.consulant.objects.Category;
import greencode.ir.consulant.objects.Course;
import greencode.ir.consulant.objects.CourseList;
import greencode.ir.consulant.objects.Degree;
import greencode.ir.consulant.objects.FreeTime;
import greencode.ir.consulant.objects.FreeTimeBaseList;
import greencode.ir.consulant.objects.FreeTimeList;
import greencode.ir.consulant.objects.JobInfo;
import greencode.ir.consulant.objects.JobItem;
import greencode.ir.consulant.objects.ListOfFreeTimes;
import greencode.ir.consulant.objects.MultiChoseObject;
import greencode.ir.consulant.objects.SingleChoseObject;
import greencode.ir.consulant.retrofit.reqobject.CatReq;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.retrofit.respObject.DegreesName;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;

import static android.app.Activity.RESULT_OK;

public class StepTwoFragment extends Fragment implements JobItemAdapter.onItemClick,StepTwoInterface {
    onActionStepTwo onAction;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btn_perivious)
    Button btnPerivious;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_next)
    Button btnNext;
    List<MultiChoseObject> cats = new ArrayList<>();
    List<MultiChoseObject> selectedCats = new ArrayList<>();
    List<SingleChoseObject> advocatedTypes = new ArrayList<>();
    SingleChoseObject selectedAdvocatedTypes ;
    
    List<SingleChoseObject> degrees = new ArrayList<>();
    SingleChoseObject selectedDegree;
    ArrayList<JobItem> items = new ArrayList<>();
    KProgressHUD progressHUD;
    StepTwoPresenter presenter;
    JobItemAdapter adapter;
    String yearOfExprience = "0";
    String chosenTime = "";
    String eachHours="0";
    String chosenAddress ="";
    String chosenImages = "";
    ListOfFreeTimes chosenListTime;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_step_two, container, false);
        ButterKnife.bind(this, view);
       presenter = new StepTwoPresenter(this);
       cats = new ArrayList<>();
       cats = ProfileAddInfoActivity.cats;
       advocatedTypes = new ArrayList<>();
       advocatedTypes = ProfileAddInfoActivity.advocatedTypes;
       degrees = new ArrayList<>();
       degrees = ProfileAddInfoActivity.degrees;
        makeList();
        checkBefor();
       return view;
    }

    private void makeList() {
        items = new ArrayList<>();
        JobItem item = new JobItem(1,"حیطه تخصص");
        JobItem item2 = new JobItem(2,"نوع وکالت اجباری");
        JobItem item3 = new JobItem(3,"آخرین مدرک تحصیلی");
        JobItem item4 = new JobItem(4,"سابقه کار");
        JobItem item5 = new JobItem(5,"ساعات پاسخ گویی");
        JobItem item6 = new JobItem(6,"هزینه هر ساعت مشاوره تلفنی");
        JobItem item7 = new JobItem(7,"آدرس محل کار");
        JobItem item8 = new JobItem(8,"مدارک و دوره های مرتبط");
        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        items.add(item7);
        items.add(item8);

        list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new JobItemAdapter(items,this,getContext());
        list.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAction = (onActionStepTwo) context;
    }

    @OnClick({R.id.btn_perivious, R.id.btn_save, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_perivious:
                onAction.onBackButtonTwo();
                break;
            case R.id.btn_save:
                showInsureDialog();
                break;
            case R.id.btn_next:
                onAction.onNextButtonTwo(getJobInfo());
                break;
        }
    }

    private void showInsureDialog() {
        final QuestionDialog dialog = new QuestionDialog(getContext(),"","آیا از دخیره اطلاعات و خروج از برنامه اطمینان دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                onAction.onSaveButtonTwo(getJobInfo());
            }
        });
        dialog.show();
    }
    @Override
    public void onClick(JobItem item) {
        if(item.getId()==1){
            openJobAreaDialog();
        }else if(item.getId()==2){
            openAdvocatedTypes();
        }else if(item.getId()==3){
            openDegreeDialog();
        }else if(item.getId()==4){
            openExprienceDialog();
        }else if(item.getId()==5){
            Intent intent = new Intent(getContext(),TimeActivity.class);
            startActivityForResult(intent,5817);
        }else if(item.getId()==6){
            openEachHoursDialog();
        }
        else if(item.getId()==7){
            Intent intent = new Intent(getContext(),AddressActivity.class);
            intent.putExtra("addressInfo",chosenAddress);
            startActivityForResult(intent,5818);
        }else if(item.getId()==8){
            Intent intent = new Intent(getContext(),AddDocumentActivity.class);
            PreferencesData.saveString("chosenImages",chosenImages,getContext());

            startActivityForResult(intent,5819);
        }
    }

    private void openEachHoursDialog() {
        final EachHoursDialog dialog = new EachHoursDialog(getContext(),eachHours);
        dialog.setListener(new EachHoursInterface() {
            @Override
            public void onRejected() {
                dialog.dismiss();
                Utility.hideKeyboard(getActivity());
            }

            @Override
            public void onAccept(String s) {
                eachHours = s;
                adapter.eachOursSet(s);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                Utility.hideKeyboard(getActivity());
            }
        });
        dialog.show();
    }

    private void openDegreeDialog() {
        final SingleChoseDialog dialog = new SingleChoseDialog(getContext(),degrees,"نوع مدرک تحصیلی");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                selectedDegree = selectedObject;
                adapter.degreeSelect(selectedObject.getTitle());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void openAdvocatedTypes() {
        final SingleChoseDialog dialog = new SingleChoseDialog(getContext(),advocatedTypes, "نوع وکالت");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                selectedAdvocatedTypes = selectedObject;
                adapter.selectAdvocateType(selectedObject.getTitle());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void openExprienceDialog() {
        final ExprienceYearsDialog dialog = new ExprienceYearsDialog(getContext(),yearOfExprience);
        dialog.setListener(new ExprienceYearsInterface() {
            @Override
            public void onRejected() {
                dialog.dismiss();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.hideKeyboard(getActivity());
                    }
                });


            }

            @Override
            public void onAccept(String s) {

                yearOfExprience = s;
                adapter.selectExprience(s);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.hideKeyboard(getActivity());
                    }
                });
            }
        });
        dialog.show();
    }

    private void openJobAreaDialog() {

        final MultiChoseDialog dialog = new MultiChoseDialog(getContext(),cats,"انتخاب حیطه های تخصص");
        dialog.setListener(new MultiChoserInterface() {
            @Override
            public void onSuccess(List<MultiChoseObject> chosenObjects) {
                selectedCats = chosenObjects;
                if(chosenObjects.size()>0){
                    adapter.selectCats(chosenObjects.size()+" دسته");
                    adapter.notifyDataSetChanged();

                }
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    @Override
    public void onError(String message) {
        progressHUD.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    private void checkBefor() {
        JobInfo info = ProfileAddInfoActivity.getJobInfo();
        if(info!=null){
            List<Integer> categories = info.getCategories();
            String jobTitleId = info.getJobTitleId();
            if(advocatedTypes.size()>0){
                if(jobTitleId!=null){
                    for(SingleChoseObject object : advocatedTypes){
                        if((object.getId()+"").equals(jobTitleId)){
                            adapter.selectAdvocateType(object.getTitle());
                            selectedAdvocatedTypes = object;
                        }
                    }
                }
            }
            String experience = info.getExperience();
            ListOfFreeTimes response_hours = info.getResponse_hours();
            String hourCost = info.getHourCost();
            AdviserLocation location = info.getLocation();
            String degree = info.getDegree();
            List<Course> courses = info.getCourses();


            if(categories.size()>0){
                for(MultiChoseObject cat : cats){
                    if(categoriesContain(categories,cat.getId())){
                        selectedCats.add(cat);
                        cats.get(cats.indexOf(cat)).setChecked(true);
                    }
                }
                adapter.selectCats(selectedCats.size()+" دسته");
                adapter.notifyDataSetChanged();

            }

            yearOfExprience = experience;
            eachHours = hourCost;
            adapter.eachOursSet(eachHours);
            adapter.selectExprience(yearOfExprience);

            if(location!=null) {
                adapter.setchosenAddress("انتخاب شد");
                chosenAddress = location.toJson().toString();
            }
            for(SingleChoseObject deg : degrees){
                if((deg.getId()+"").equals(degree)){
                    selectedDegree = deg;
                }

            }
            if(selectedDegree!=null) {
                adapter.degreeSelect(selectedDegree.getTitle());
            }
            CourseList courseList ;

            if(courses.size()>0){
                courseList = new CourseList(courses);
                chosenImages = courseList.toJson().toString();
                adapter.setchosenImages("انجام شد");

            }
            if(response_hours!=null) {
                if (response_hours.getFreeTimes().size() > 0) {
                    chosenListTime = response_hours;
                    adapter.setChosenTime("انتخاب شد");
                }
            }

            adapter.notifyDataSetChanged();
        }
    }

    private boolean categoriesContain(List<Integer> categories,int id) {
        boolean contain = false;
        for(int a : categories){
            if(id==a){
                contain = true;
                break;
            }
        }
        return contain;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==5817){
            if(resultCode == RESULT_OK){
                chosenTime = data.getStringExtra("chosenTime");
                if(!chosenTime.equals("")){
                    adapter.setChosenTime("زمان ثبت شد");
                    adapter.notifyDataSetChanged();
                }

            }
        }else if(requestCode==5818){
                if(resultCode == RESULT_OK){
                    chosenAddress = data.getStringExtra("chosenAddress");
                    if(!chosenAddress.equals("")) {
                        adapter.setchosenAddress("آدرس مشخص شد");
                        adapter.notifyDataSetChanged();
                    }
                }
        }
            else if(requestCode==5819){
                if(resultCode==RESULT_OK){
                    chosenImages = PreferencesData.getString("chosenImages",getContext());
                    adapter.setchosenImages("انتخاب شد");
                    adapter.notifyDataSetChanged();
                }
            }

    }

    public JobInfo getJobInfo() {
        List<Integer> cats = new ArrayList<>();
        if(selectedCats!=null){
            for(MultiChoseObject cat : selectedCats){
                cats.add(cat.getId());
            }
        }

        Gson gson = new Gson();
        if(!chosenTime.equals("")){
            chosenListTime = gson.fromJson(chosenTime,ListOfFreeTimes.class);
        }
        AdviserLocation location = null;
        if(!chosenAddress.equals("")){
            location = gson.fromJson(chosenAddress,AdviserLocation.class);
        }
        String degree = "";
        if(selectedDegree!=null){
            degree =selectedDegree.getId()+"";
        }
        List<Course> courses = new ArrayList<>();
        if(!chosenImages.equals("")){
            CourseList courseList = gson.fromJson(chosenImages,CourseList.class);
            for(Course course : courseList.getList()){
                courses.add(course);
            }
        }
        String titleId ="";
        if(selectedAdvocatedTypes!=null){
            titleId = selectedAdvocatedTypes.getId()+"";
        }
        JobInfo jobInfo = new JobInfo(cats,titleId,yearOfExprience,chosenListTime,eachHours,location,degree,courses);
        return jobInfo;
    }

    public interface onActionStepTwo {
        public void onSaveButtonTwo(JobInfo item);

        public void onNextButtonTwo(JobInfo item);

        public void onBackButtonTwo();
    }
}


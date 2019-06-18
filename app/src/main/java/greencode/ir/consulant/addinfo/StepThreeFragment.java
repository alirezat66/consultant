package greencode.ir.consulant.addinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.ChosePhotoTakerDialog;
import greencode.ir.consulant.dialog.PhotoChoserInterface;
import greencode.ir.consulant.dialog.QuestionDialog;
import greencode.ir.consulant.dialog.QuestionListener;
import greencode.ir.consulant.main.PhotoActivity;
import greencode.ir.consulant.objects.DocumentImg;
import greencode.ir.consulant.objects.Upload;
import greencode.ir.consulant.objects.UploadList;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class StepThreeFragment extends Fragment implements DocumentsAdapter.onItemClick {
    onActionStepThree onAction;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btn_perivious)
    Button btnPerivious;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    DocumentsAdapter adapter;
    List<DocumentImg> docs = new ArrayList<>();
    DocumentImg selectedDoc;

    String b64Image = "";
    String[] mPermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    KProgressHUD kProgressHUD;
    ChosePhotoTakerDialog choseDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void makeList() {
        docs = new ArrayList<>();
        docs.add(new DocumentImg(1,"تصویر روی کارت ملی","0",""));
        docs.add(new DocumentImg(2,"تصویر صفحه اول شناسنامه","0",""));
        docs.add(new DocumentImg(3,"تصویر پروانه وکالت","0",""));
        docs.add(new DocumentImg(4,"تصویر صفحه تمدید پروانه وکالت","0",""));

       adapter  = new DocumentsAdapter(docs,this,getContext());
       list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
       list.setAdapter(adapter);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_step_three, container, false);
        ButterKnife.bind(this, view);
        makeList();
        checkBefor();
        return view;
    }

    private void checkBefor() {
        UploadList uploadList = ProfileAddInfoActivity.getListInfo();
        if(uploadList!=null){
            if(uploadList.getUploads().size()>0){
                List<Upload> uploads = uploadList.getUploads();
                for(Upload upload : uploads){
                    if(upload.getType()==1){
                        adapter.update(new DocumentImg(1,"تصویر روی کارت ملی","0",upload.getUpload()));
                    }else if(upload.getType()==2){
                        adapter.update(new DocumentImg(2,"تصویر صفحه اول شناسنامه","0",upload.getUpload()));
                    }else if(upload.getType()==3){
                        adapter.update(new DocumentImg(3,"تصویر پروانه وکالت","0",upload.getUpload()));
                    }else if(upload.getType()==4){
                        adapter.update(new DocumentImg(4,"تصویر صفحه تمدید پروانه وکالت","0",upload.getUpload()));
                    }
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAction = (onActionStepThree) context;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.btn_perivious, R.id.btn_save, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_perivious:
                onAction.onBackButtonThree();
                break;
            case R.id.btn_save:
                showInsureDialog();
                break;
            case R.id.btn_next:
                onAction.onFinalButtonThree(getUploads());
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
                onAction.onSaveButtonThree(getUploads());
            }
        });
        dialog.show();
    }
    private UploadList getUploads() {
       List<DocumentImg> docs =  adapter.getList();
       List<Upload> uploads = new ArrayList<>();
       for(DocumentImg doc:docs){
           if(!doc.getDocSource().equals("")){
               uploads.add(new Upload(doc.getId(),doc.getDocSource()));
           }
       }
       UploadList upList = new UploadList(uploads);
       return  upList;
    }

    @Override
    public void onClick(DocumentImg item) {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        PreferencesData.saveString("myb64",item.getDocSource(),getContext());
        startActivity(intent);
    }

    @Override
    public void onDelete(DocumentImg item) {
        adapter.removeDoc(item);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemCLick(DocumentImg item) {
        selectedDoc = item;
        checkPermissionExtera();
    }

    public interface onActionStepThree {
        public void onSaveButtonThree(UploadList list);

        public void onFinalButtonThree(UploadList list);

        public void onBackButtonThree();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5817) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED
                    ) {
                showDialogForImageSelector();

            } else {
                Toast.makeText(getContext(), "بدون دادن اجازه نمی توانید مستندی اضافه کنید.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void showDialogForImageSelector() {
        choseDialog = new ChosePhotoTakerDialog(getContext());
        choseDialog.setListener(new PhotoChoserInterface() {
            @Override
            public void onSuccess(int type) {

                if (type == 1) {
                    EasyImage.openCamera(StepThreeFragment.this, 500);
                    //camera chosemn
                } else {
                    EasyImage.openGallery(StepThreeFragment.this, 600);
                    //gallery chosen
                }
                choseDialog.dismiss();
            }

            @Override
            public void onRejected() {
                choseDialog.dismiss();
            }
        });
        choseDialog.show();

    }


    private void checkPermissionExtera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ActivityCompat.checkSelfPermission(getContext(), mPermission[0])
                        != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), mPermission[1])
                                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), mPermission[2])
                        != PackageManager.PERMISSION_GRANTED
                        ) {
                    ActivityCompat.requestPermissions(getActivity(),
                            mPermission, 5817);
                    // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                } else {
                    showDialogForImageSelector();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showDialogForImageSelector();
        }
    }
    public void showWaiting(){
        kProgressHUD=  KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("لطفا منتظر بمانید")
                .setDetailsLabel("در حال انجام عملیات")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .setBackgroundColor(getResources().getColor(R.color.blueBack))
                .show();
    }
    public void disMissWaiting(){
        if(kProgressHUD!=null){
            kProgressHUD.dismiss();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                showWaiting();
                if(imageFile!=null) {
                    new StepThreeFragment.ConvertToB64().execute(imageFile);
                }

            }
        });
    }
    class ConvertToB64 extends AsyncTask<File, String, String> {
        @Override
        protected String doInBackground(File... files) {
            File imageFile = files[0];
            Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            bm = Utility.resizeBitmap(bm, 800);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//added lines
            bm.recycle();
            bm = null;
//added lines
            byte[] b = baos.toByteArray();
            String b64 = Base64.encodeToString(b, Base64.DEFAULT);
            return b64;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            b64Image = result;
            selectedDoc.setDocSource(b64Image);
            adapter.update(selectedDoc);
            adapter.notifyDataSetChanged();
            disMissWaiting();

        }

    }

}

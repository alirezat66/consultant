package greencode.ir.consulant.addinfo;

import android.Manifest;
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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.ChosePhotoTakerDialog;
import greencode.ir.consulant.dialog.PhotoChoserInterface;
import greencode.ir.consulant.main.PhotoActivity;
import greencode.ir.consulant.objects.Course;
import greencode.ir.consulant.objects.CourseList;
import greencode.ir.consulant.objects.DocumentImg;
import greencode.ir.consulant.retrofit.respObject.AdvocancyTypeName;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.widget.GridLayout.HORIZONTAL;

public class AddDocumentActivity extends BaseActivity implements CourseAdapter.onItemClick {
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnBack)
    Button btnBack;

    KProgressHUD kProgressHUD;
    String[] mPermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    String b64Image = "";
    List<Course> docList = new ArrayList<>();
    ChosePhotoTakerDialog choseDialog;
    int size;
    CourseAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        ButterKnife.bind(this);

        String  chosenImages = PreferencesData.getString("chosenImages",this);

        if(!chosenImages.equals("")) {
            Gson gson = new Gson();
            CourseList list = gson.fromJson(chosenImages,CourseList.class);
            if (list.getList().size() > 0) {
                docList = list.getList();
            }
        }
        adapter = new CourseAdapter(docList,this,this);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        list.addItemDecoration(itemDecor);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);
    }

    @OnClick({R.id.btn_add, R.id.btnSave, R.id.btnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                checkPermissionExtera();
                break;
            case R.id.btnSave:
                Intent intent = new Intent();
                List<Course> imgs = adapter.getitems();
                CourseList docs = new CourseList(imgs);
                PreferencesData.saveString("chosenImages",docs.toJson().toString(),this);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
    private void checkPermissionExtera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, mPermission[1])
                                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, mPermission[2])
                        != PackageManager.PERMISSION_GRANTED
                        ) {
                    ActivityCompat.requestPermissions(AddDocumentActivity.this,
                            mPermission, 5820);
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

    public void showDialogForImageSelector() {
        choseDialog = new ChosePhotoTakerDialog(this);
        choseDialog.setListener(new PhotoChoserInterface() {
            @Override
            public void onSuccess(int type) {

                if (type == 1) {
                    EasyImage.openCamera(AddDocumentActivity.this, 500);
                    //camera chosemn
                } else {
                    EasyImage.openGallery(AddDocumentActivity.this, 600);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5820) {
            boolean hasPermission = true;
            for(int result : grantResults){
                if(result!=PackageManager.PERMISSION_GRANTED){
                    hasPermission = false;
                }
            }
            if (hasPermission) {
                showDialogForImageSelector();
            } else {
                Toast.makeText(this, "بدون دادن اجازه نمی توانید مستندی اضافه کنید.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                showWaiting();
               new ConvertToB64().execute(imageFile);


            }
        });
    }



    @Override
    public void onClick(Course item) {
        PreferencesData.saveString("myb64",item.getSource(),this);
        Intent intent = new Intent(this,PhotoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDelete(Course item) {
        adapter.deleteItem(item);
        adapter.notifyDataSetChanged();
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
            size = b.length/1024;

            String b64 = Base64.encodeToString(b, Base64.DEFAULT);
            return b64;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            b64Image = result;
            disMissWaiting();
            openDialogForName();

        }

    }

    private void openDialogForName() {
        final AddDocumentDialog dialog = new AddDocumentDialog(this);
        dialog.setListener(new AddDocListener() {
            @Override
            public void onRejected() {
                dialog.dismiss();
                b64Image ="";
            }

            @Override
            public void onAccept(String title, String desc) {
                dialog.dismiss();
                Course doc = new Course(title,desc,b64Image);
                docList.add(doc);
                updateList();
            }
        });
        dialog.show();
    }

    private void updateList() {
        adapter.updateList(docList);
        adapter.notifyDataSetChanged();
    }

    private void disMissWaiting() {
        kProgressHUD.dismiss();
    }

    private void showWaiting() {
        kProgressHUD= Utility.makeWaiting(this);
        kProgressHUD.show();
    }
}

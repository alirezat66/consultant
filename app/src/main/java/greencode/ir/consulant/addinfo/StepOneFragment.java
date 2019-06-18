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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.ChosePhotoTakerDialog;
import greencode.ir.consulant.dialog.PhotoChoserInterface;
import greencode.ir.consulant.dialog.QuestionDialog;
import greencode.ir.consulant.dialog.QuestionListener;
import greencode.ir.consulant.dialog.SingleChoseDialog;
import greencode.ir.consulant.dialog.SingleChoserInterface;
import greencode.ir.consulant.objects.PersonalInfo;
import greencode.ir.consulant.objects.SingleChoseObject;
import greencode.ir.consulant.utils.Constants;
import greencode.ir.consulant.utils.GenderPickerDialog;
import greencode.ir.consulant.utils.PersianCalculater;
import greencode.ir.consulant.utils.PreferencesData;
import greencode.ir.consulant.utils.Utility;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import saman.zamani.persiandate.PersianDate;

public class StepOneFragment extends Fragment implements DateSetListener, InterfaceStepOne {
    onActionStepOne onAction;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.edt_fname)
    EditText edtFname;
    @BindView(R.id.edt_lname)
    EditText edtLname;
    @BindView(R.id.edt_ncode)
    EditText edtNcode;
    @BindView(R.id.edt_birth_date)
    EditText edtBirthDate;
    @BindView(R.id.edt_sex)
    EditText edtSex;
    @BindView(R.id.edt_bank)
    EditText edtBank;
    @BindView(R.id.edt_sheba)
    EditText edtSheba;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_mail)
    EditText edtMail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.footer)
    LinearLayout footer;
    PresenterStepOne presenter;
    long birthDate = 0;
    String sex = "";
    String b64Image = "";
    String[] mPermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    KProgressHUD kProgressHUD;
    ChosePhotoTakerDialog choseDialog;
    ArrayList<SingleChoseObject> banks = new ArrayList<>();
    @BindView(R.id.edt_sh_parvane)
    EditText edtShParvane;
    private int chosenBank = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAction = (onActionStepOne) context;


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_step_one, container, false);
        ButterKnife.bind(this, view);
        edtPhone.setText(PreferencesData.getString(Constants.Pref_USER_PHONE, getContext()));
        edtMail.setEnabled(true);
        edtMail.setFocusable(true);
        edtFname.requestFocus();
        presenter = new PresenterStepOne(this);
        makeBanks();
        return view;
    }

    private void checkLastInfo() {
        PersonalInfo info = ProfileAddInfoActivity.getPersonalInfo();
        if (info != null) {
            edtFname.setText(info.getFirstName());
            edtLname.setText(info.getLastName());
            for (SingleChoseObject bank : banks) {
                if (bank.getId() == info.getBankId()) {
                    edtBank.setText(bank.getTitle());
                    chosenBank = bank.getId();
                }
            }
            PersianDate persianDate = new PersianDate((info.getBirthDate() * 1000));
            birthDate = info.getBirthDate();
            edtBirthDate.setText(PersianCalculater.getYearMonthAndDay(persianDate.getTime()));
            edtShParvane.setText(info.getShParvane());
            edtMail.setText(info.getEmail());
            edtNcode.setText(info.getNationalCode());
            sex = info.getSex();
            if (info.getSex().equals("male")) {
                edtSex.setText("مرد");
            } else if (info.getSex().equals("female")) {
                edtSex.setText("زن");
            }
            edtSheba.setText(info.getShabaNumber());
            if (!info.getUserImg().equals("")) {
                b64Image = info.getUserImg();
                Bitmap bitmap = Utility.getBitmapFromBase(b64Image);
                imgProfile.setImageBitmap(bitmap);
            }

        }
    }

    private void makeBanks() {

        banks = ProfileAddInfoActivity.banks;
        checkLastInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                    EasyImage.openCamera(StepOneFragment.this, 500);
                    //camera chosemn
                } else {
                    EasyImage.openGallery(StepOneFragment.this, 600);
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

    @OnClick({R.id.img_profile, R.id.btn_save, R.id.btn_next, R.id.edt_birth_date, R.id.edt_sex, R.id.edt_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_profile:
                checkPermissionExtera();
                break;
            case R.id.btn_save:
                showInsureDialog();
                break;
            case R.id.btn_next:
                onAction.onNextButtonOne(getPersonalInfo());
                break;
            case R.id.edt_birth_date:
                showBirthDialog();
                break;
            case R.id.edt_sex:
                openSexDialog();
                break;
            case R.id.edt_bank:
                openBankDialog();
                break;
        }
    }

    private void showInsureDialog() {
        final QuestionDialog dialog = new QuestionDialog(getContext(), "", "آیا از دخیره اطلاعات و خروج از برنامه اطمینان دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                onAction.onSaveButtonOne(getPersonalInfo());
            }
        });
        dialog.show();
    }

    private void openBankDialog() {
        final SingleChoseDialog dialog = new SingleChoseDialog(getContext(), banks, "انتخاب بانک");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                chosenBank = selectedObject.getId();
                edtBank.setText(selectedObject.getTitle());
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();
            }
        });
        dialog.show();
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

    public void showWaiting() {
        kProgressHUD = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("لطفا منتظر بمانید")
                .setDetailsLabel("در حال انجام عملیات")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .setBackgroundColor(getResources().getColor(R.color.blueBack))
                .show();
    }

    public void disMissWaiting() {
        if (kProgressHUD != null) {
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
                if (imageFile != null) {
                    Picasso.with(getContext()).load(imageFile)
                            .fit()
                            .centerCrop()
                            .into(imgProfile);
                    new ConvertToB64().execute(imageFile);
                }

            }
        });
    }

    private void showBirthDialog() {
        long justNow = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(justNow);
        PersianDate date = new PersianDate(System.currentTimeMillis());
        PersianDate defultYear = new PersianDate(System.currentTimeMillis());
        defultYear.setShYear(1368);
        calendar.setTimeInMillis(defultYear.getTime());
        new DatePicker.Builder()
                .id(1)

                .minYear(1300)
                .maxYear(1380)
                .maxMonth(date.getShMonth())
                .showYearFirst(true)
                .closeYearAutomatically(true)
                .theme(R.style.DialogTheme)
                .date(calendar)
                .build(this)
                .show(getActivity().getSupportFragmentManager(), "انتخاب تاریخ تولد");
    }

    private void openSexDialog() {
        final GenderPickerDialog dialog = new GenderPickerDialog(getContext());
        dialog.setOnSelectingGender(new GenderPickerDialog.OnGenderSelectListener() {
            @Override
            public void onSelectingGender(String value) {
                edtSex.setText(value);
                if (value.equals("زن")) {
                    sex = "female";
                } else {
                    sex = "male";
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
        PersianDate pdate = new PersianDate(System.currentTimeMillis());
        pdate.setShYear(year);
        pdate.setMinute(0);
        pdate.setHour(12);
        pdate.setShMonth(month);
        pdate.setShDay(day);
        birthDate = pdate.getTime() / 1000;
        edtBirthDate.setText(PersianCalculater.getYearMonthAndDay(year, month, day));
    }


    public PersonalInfo getPersonalInfo() {
        PersonalInfo personalInfo = new PersonalInfo(edtFname.getText().toString(), edtLname.getText().toString(),
                birthDate, sex, edtNcode.getText().toString(), edtMail.getText().toString(), b64Image, chosenBank, edtSheba.getText().toString(), edtPhone.getText().toString(),edtShParvane.getText().toString());
        return personalInfo;
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
            disMissWaiting();

        }

    }

    public interface onActionStepOne {
        public void onSaveButtonOne(PersonalInfo info);

        public void onNextButtonOne(PersonalInfo info);
    }
}

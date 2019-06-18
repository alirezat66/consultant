package greencode.ir.consulant.addinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greencode.ir.consulant.R;
import greencode.ir.consulant.dialog.AddressDetailDialog;
import greencode.ir.consulant.dialog.AddressDetailInterface;
import greencode.ir.consulant.dialog.SingleChoseDialog;
import greencode.ir.consulant.dialog.SingleChoserInterface;
import greencode.ir.consulant.objects.AdviserLocation;
import greencode.ir.consulant.objects.JobItem;
import greencode.ir.consulant.objects.SingleChoseObject;
import greencode.ir.consulant.retrofit.respObject.Area;
import greencode.ir.consulant.retrofit.respObject.City;
import greencode.ir.consulant.retrofit.respObject.Country;
import greencode.ir.consulant.retrofit.respObject.Province;
import greencode.ir.consulant.utils.BaseActivity;
import greencode.ir.consulant.utils.DividerDecorator;
import greencode.ir.consulant.utils.Utility;

public class AddressActivity extends BaseActivity implements AddressItemAdapter.onItemClick, AddressInterface {
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.edt_detail_address)
    EditText edtDetailAddress;

    AddressItemAdapter adapter;
    List<JobItem> items = new ArrayList<>();
    List<SingleChoseObject> countries = new ArrayList<>();
    List<SingleChoseObject> provinces = new ArrayList<>();
    List<SingleChoseObject> arias = new ArrayList<>();
    List<SingleChoseObject> cities = new ArrayList<>();
    KProgressHUD progressHUD;
    AddressPresenter presenter;

    SingleChoseObject chosenCountry;
    SingleChoseObject chosenProvince;
    SingleChoseObject chosenCity;
    SingleChoseObject chosenArea;
    String chosenDetail = "";

    String addressInfo ="";

    int cityId =0;
    int stateId= 0;
    int countryId =0;
    String detailAddress ="";
    AdviserLocation location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        presenter = new AddressPresenter(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            addressInfo = bundle.getString("addressInfo");
            Gson gson = new Gson();
            location = gson.fromJson(addressInfo,AdviserLocation.class);

        }

        if(location!=null){
            edtDetailAddress.setText(location.getAddress());
            cityId = location.getCityId();
            countryId = location.getCountryId();
            stateId = location.getProvinceId();
        }
        makeItems();
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        presenter.getCountries(0);

    }

    private void makeItems() {
        JobItem item = new JobItem(1, "کشور");
        JobItem item2 = new JobItem(2, "استان");
        JobItem item3 = new JobItem(3, "شهر");
        //   JobItem item4 = new JobItem(4,"محله");
        //  JobItem item5 = new JobItem(5,"سایر جزئیات آدرس");
        //     JobItem item6 = new JobItem(6,"انتخاب آدرس از روی نقشه");

        items.add(item);
        items.add(item2);
        items.add(item3);
        //  items.add(item4);
        //  items.add(item5);
        //      items.add(item6);
        adapter = new AddressItemAdapter(items, this, this);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL );
        list.addItemDecoration(decoration);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);


    }

    @OnClick({R.id.btnSave, R.id.btnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                AdviserLocation adviserLocation = new AdviserLocation(countryId, stateId, cityId, 0, edtDetailAddress.getText().toString());
                String addviserLoc = adviserLocation.toJson().toString();
                Intent intent = new Intent();
                intent.putExtra("chosenAddress", addviserLoc);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    @Override
    public void onClick(JobItem item) {
        if (item.getId() == 1) {
            if (countries.size() > 0) {
                openContriesDialog();
            } else {
                Toast.makeText(this, "کشوری وجود ندارد.", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getId() == 2) {
            if (provinces.size() > 0) {
                openProvinceDialog();
            } else {
                Toast.makeText(this, "ابتدا کشور خود را انتخاب کنید.", Toast.LENGTH_SHORT).show();

            }
        }
        if (item.getId() == 3) {
            if (cities.size() > 0) {
                openCityDialog();
            } else {
                Toast.makeText(this, "ابتدا استان خود را انتخاب کنید.", Toast.LENGTH_SHORT).show();

            }
        }
        if (item.getId() == 4) {
            if (arias.size() > 0) {
                openAreaDialog();
            } else {
                Toast.makeText(this, "ابتدا شهر خود را انتخاب کنید.", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getId() == 5) {
            openDetailAddress();
        }
    }

    private void openDetailAddress() {
        final AddressDetailDialog dialog = new AddressDetailDialog(this);
        dialog.setListener(new AddressDetailInterface() {
            @Override
            public void onRejected() {
                dialog.dismiss();
            }

            @Override
            public void onAccept(String s) {
                chosenDetail = s;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openAreaDialog() {
        final SingleChoseDialog dialog = new SingleChoseDialog(this, arias, "انتخاب ناحیه");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                chosenArea = selectedObject;
                adapter.setAreaChose(selectedObject.getTitle());
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

    private void openCityDialog() {
        final SingleChoseDialog dialog = new SingleChoseDialog(this, cities, "انتخاب شهر");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                chosenArea = null;
                chosenCity = selectedObject;
                cityId =chosenCity.getId();
                arias = new ArrayList<>();
                //     progressHUD.show();
            //    presenter.getAreas(chosenCity.getId(), 0);
                adapter.choseCity(selectedObject.getTitle());
                adapter.notifyDataSetChanged();
                ;
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void openProvinceDialog() {
        final SingleChoseDialog dialog = new SingleChoseDialog(this, provinces, "انتخاب استان");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                chosenProvince = selectedObject;
                chosenCity = null;
                stateId = chosenProvince.getId();
                adapter.setProviceChecked(selectedObject.getTitle());
                adapter.notifyDataSetChanged();
                progressHUD.show();
                cities = new ArrayList<>();
                presenter.getCities(selectedObject.getId(), 0);
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    private void openContriesDialog() {


        final SingleChoseDialog dialog = new SingleChoseDialog(this, countries, "انتخاب کشور");
        dialog.setListener(new SingleChoserInterface() {
            @Override
            public void onSuccess(SingleChoseObject selectedObject) {
                chosenCountry = selectedObject;
                countryId = chosenCountry.getId();
                provinces = new ArrayList<>();
                chosenProvince = null;
                progressHUD.show();
                presenter.getProvices(chosenCountry.getId(), 0);
                dialog.dismiss();
                adapter.setCountryChecked(selectedObject.getTitle());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onRejected() {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onReadyCountry(List<Country> posts) {

        for (Country contry : posts) {
            countries.add(new SingleChoseObject(contry.getCountryId(), contry.getCountryName()));
        }
        if(countryId!=0){
            String chosenCount ="";
            for(SingleChoseObject country : countries){
                if(country.getId()==countryId){
                    chosenCount=country.getTitle();
                    break;
                }
            }
            adapter.setCountryChecked(chosenCount);
            adapter.notifyDataSetChanged();
            presenter.getProvices(countryId,0);
        }else {
            progressHUD.dismiss();
        }


    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReadyProvince(List<Province> posts) {
        if(stateId!=0){
            String chosenState= "";
            for (Province province : posts) {
                provinces.add(new SingleChoseObject(province.getProvinceId(), province.getProvinceName()));
                if(province.getProvinceId()==stateId){
                    chosenState=province.getProvinceName();
                }
            }
            adapter.setProviceChecked(chosenState);
            adapter.notifyDataSetChanged();

            presenter.getCities(stateId,0);
        }else {
            progressHUD.dismiss();
            for (Province province : posts) {
                provinces.add(new SingleChoseObject(province.getProvinceId(), province.getProvinceName()));
            }
        }

    }

    @Override
    public void onReadyCities(List<City> posts) {
        progressHUD.dismiss();
        String chosenCity="";
        for (City city : posts) {
            cities.add(new SingleChoseObject(city.getCityId(), city.getCityName()));
            if(cityId!=0){
                if(city.getCityId()==cityId){
                    chosenCity = city.getCityName();
                }
            }
        }
        adapter.choseCity(chosenCity);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onReadyArea(List<Area> posts) {
        progressHUD.dismiss();
        for (Area area : posts) {
            arias.add(new SingleChoseObject(area.getAreaId(), area.getAreaName()));
        }
    }
}

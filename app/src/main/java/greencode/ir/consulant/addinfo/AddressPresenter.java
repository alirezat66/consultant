package greencode.ir.consulant.addinfo;

import java.util.List;

import greencode.ir.consulant.Pojo.POJOModel;
import greencode.ir.consulant.retrofit.respObject.Area;
import greencode.ir.consulant.retrofit.respObject.City;
import greencode.ir.consulant.retrofit.respObject.Country;
import greencode.ir.consulant.retrofit.respObject.Province;

public class AddressPresenter {
    AddressInterface myInterface;
    POJOModel model;

    public AddressPresenter(AddressInterface myInterface) {
        this.myInterface = myInterface;
        this.model = new POJOModel(this);
    }

    public void getCountries(int timeStamp) {
        model.getContries(timeStamp);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void onReadyCountry(List<Country> posts) {
        myInterface.onReadyCountry(posts);
    }

    public void getProvices(int countryId, long timeStamp) {
        model.getProvinces(countryId,timeStamp);
    }

    public void onReadyProvince(List<Province> posts) {
        myInterface.onReadyProvince(posts);
    }

    public void getCities(int id, long timeStamp) {
        model.getCities(id,timeStamp);
    }

    public void onReadyCities(List<City> posts) {
        myInterface.onReadyCities(posts);
    }

    public void getAreas(int id, int timeStamp) {
        model.getAreas(id,timeStamp);
    }

    public void onReadyArea(List<Area> posts) {
        myInterface.onReadyArea(posts);
    }
}

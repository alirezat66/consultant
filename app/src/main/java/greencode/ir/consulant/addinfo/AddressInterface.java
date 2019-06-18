package greencode.ir.consulant.addinfo;

import java.util.List;

import greencode.ir.consulant.retrofit.respObject.Area;
import greencode.ir.consulant.retrofit.respObject.City;
import greencode.ir.consulant.retrofit.respObject.Country;
import greencode.ir.consulant.retrofit.respObject.Province;

public interface AddressInterface {

    void onReadyCountry(List<Country> posts);

    void onError(String str);

    void onReadyProvince(List<Province> posts);

    void onReadyCities(List<City> posts);

    void onReadyArea(List<Area> posts);
}

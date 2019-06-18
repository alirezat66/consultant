package greencode.ir.consulant.objects;

import greencode.ir.consulant.utils.ToJsonClass;

public class AdviserLocation extends ToJsonClass {
    int countryId;
    int provinceId;
    int cityId;
    int areaId;
    String address;
    double lat;
    double lng;

    public AdviserLocation(int countryId, int provinceId, int cityId, int areaId, String address) {
        this.countryId = countryId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.areaId = areaId;
        this.address = address;
        this.lat = 0;
        this.lng=0;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

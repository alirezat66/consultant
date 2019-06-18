package greencode.ir.consulant.retrofit.respObject;

import greencode.ir.consulant.utils.ToJsonClass;

public class Offer extends ToJsonClass{
    int offerId;
    long offerTime;
    String title;
    String subTitle;
    int catId;
    int subCatId;
    String imgCat;
    int state;//درانتظار تایید = 0 ، لغوشده = 2، تایید شده = 1 ،انجام شده = 3،عدم تایید = 4،درحال مشاوره=

    public int getOfferId() {
        return offerId;
    }

    public long getOfferTime() {
        return offerTime;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public int getCatId() {
        return catId;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public String getImgCat() {
        return imgCat;
    }

    public int getState() {
        return state;
    }
}

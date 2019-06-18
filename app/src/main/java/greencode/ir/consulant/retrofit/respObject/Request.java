package greencode.ir.consulant.retrofit.respObject;

import greencode.ir.consulant.utils.ToJsonClass;

public class Request extends ToJsonClass {
    int requestId;
    String title;
    String subTitle;
    int catId;
    int subCatId;
    String imgCat;
    long requestTime;

    public int getRequestId() {
        return requestId;
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

    public long getRequestTime() {
        return requestTime;
    }
}

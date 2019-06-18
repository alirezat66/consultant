package greencode.ir.consulant.objects;

public class Advice {
    int requestId ;
    String title ;
    String  subTitle;
    int state ;//0 waited = dar entezare tayid,  1 accepted , 2 payed
    int catId ;
    int subCatId ;
    String imgCat ;
    int adviserCont ;
    int offerId;

    long callTimestammp;

    public long getCallTimestammp() {
        return callTimestammp;
    }

    public int getOfferId() {
        return offerId;
    }

    public int getAdviceId() {
        return requestId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public int getState() {
        return state;
    }

    public int getCatId() {
        return catId;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public String getImgCat() {
        if(imgCat!=null) {
            return imgCat;
        }else {
            return "";
        }
    }

    public int getLawersCont() {
        return adviserCont;
    }
}

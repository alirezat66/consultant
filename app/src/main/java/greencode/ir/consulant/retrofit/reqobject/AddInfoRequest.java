package greencode.ir.consulant.retrofit.reqobject;

public class AddInfoRequest {
    String token;
    int userId;
    String personalInfo;
    String jobInfo;
    String uploads;

    public AddInfoRequest(String token, int userId, String personalInfo, String jobInfo, String uploads) {
        this.token = token;
        this.userId = userId;
        this.personalInfo = personalInfo;
        this.jobInfo = jobInfo;
        this.uploads = uploads;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public String getJobInfo() {

        String jobWithOutFreeTime = jobInfo.replace("{\"freeTimes\":","");
        jobWithOutFreeTime = jobWithOutFreeTime.substring(0,jobWithOutFreeTime.length()-1);
        return jobWithOutFreeTime;
    }

    public String getUploads() {
        String almahdi;
        if(uploads.length()>13) {
             almahdi = uploads.substring(11, uploads.length() - 1);
        }else {
            almahdi = "";
        }
        return almahdi;
    }
}

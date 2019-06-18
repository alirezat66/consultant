package greencode.ir.consulant.objects;

public class FreeTime {
    int timeId;
    int shift;
    String startTime;
    String endTime;
    String dayOfWeek;


    public FreeTime(int timeId, int shift, String startTime, String endTime, String dayOfWeek) {
        this.timeId = timeId;
        this.shift = shift;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    public FreeTime(int timeId, int shift, String startTime, String endTime) {
        this.timeId = timeId;
        this.shift = shift;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public FreeTime(int timeId, int shift) {
        this.timeId = timeId;
        this.shift = shift;
        this.startTime="";
        this.endTime="";
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTimeId() {
        return timeId;
    }

    public int getShift() {
        return shift;
    }

    public String getStartTime() {
        return startTime;

    }

    public String getEndTime() {
        return endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getHourStartTime() {
        if(startTime.equals("")){
            return "";
        }else {
            String[]starts = this.startTime.split(":");
            if(starts.length>1) {
                return starts[0];
            }else {
                return "";
            }
        }
    }

    public String getHourEndTime() {
        if(this.endTime.equals("")){
            return "";
        }else {
            String[]starts = this.endTime.split(":");
            if(starts.length>1) {
                return starts[0];
            }else {
                return "";
            }
        }
    }

    public void updateDay(String pName) {
        this.dayOfWeek = pName;
    }
}

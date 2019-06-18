package greencode.ir.consulant.objects;

import java.util.List;

import greencode.ir.consulant.utils.ToJsonClass;

public class FreeTimeList extends ToJsonClass {
    String dayName;
    List<FreeTime> freeTimes;

    public FreeTimeList(String dayName, List<FreeTime> freeTimes) {
        this.dayName = dayName;
        this.freeTimes = freeTimes;
    }
    public void changeFreeTime(int shift,String start,String end){
        freeTimes.get(shift-1).setStartTime(start);
        freeTimes.get(shift-1).setEndTime(end);
    }

    public String getDayName() {
        return dayName;
    }

    public List<FreeTime> getFreeTimes() {
        return freeTimes;
    }
}

package greencode.ir.consulant.objects;

import java.util.ArrayList;
import java.util.List;

import greencode.ir.consulant.utils.ToJsonClass;

public class ListOfFreeTimes extends ToJsonClass{
    public ListOfFreeTimes(List<FreeTime> freeTimes) {
        this.freeTimes = freeTimes;
    }

    List<FreeTime> freeTimes
            ;

    public List<FreeTime> getFreeTimes() {
        return freeTimes;
    }

    public void updateDay(String pName, FreeTime freeTime) {
        freeTimes.get(freeTimes.indexOf(freeTime)).updateDay(pName);
    }
}

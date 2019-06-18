package greencode.ir.consulant.objects;

import java.util.ArrayList;
import java.util.List;

import greencode.ir.consulant.utils.ToJsonClass;

public class FreeTimeBaseList extends ToJsonClass{
    List<FreeTimeList> list;

    public FreeTimeBaseList(List<FreeTimeList> list) {
        this.list = list;
    }

    public List<FreeTimeList> getList() {
        return list;
    }
}

package greencode.ir.consulant.objects;

import java.util.List;

import greencode.ir.consulant.utils.ToJsonClass;

public class CourseList extends ToJsonClass{
    List<Course>list ;

    public List<Course> getList() {
        return list;
    }

    public CourseList(List<Course> list) {
        this.list = list;
    }
}

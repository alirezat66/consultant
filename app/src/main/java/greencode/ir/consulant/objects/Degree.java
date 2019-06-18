package greencode.ir.consulant.objects;

import greencode.ir.consulant.utils.ToJsonClass;

public class Degree extends ToJsonClass{
    String id ;

    public String getId() {
        return id;
    }

    public Degree(String id) {
        this.id = id;
    }
}

package greencode.ir.consulant.objects;

public class SingleChoseObject {
    String title;
    int id;

    public SingleChoseObject(int id,String title) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}

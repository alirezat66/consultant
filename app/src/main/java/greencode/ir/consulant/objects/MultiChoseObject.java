package greencode.ir.consulant.objects;

public class MultiChoseObject {
    int id;
    String title;
    boolean checked;

    public MultiChoseObject(int id, String title) {
        this.id = id;
        this.title = title;
        this.checked = false;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isChecked() {
        return checked;
    }
}

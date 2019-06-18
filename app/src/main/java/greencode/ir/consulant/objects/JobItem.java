package greencode.ir.consulant.objects;

public class JobItem {
    int id;
    String title;
    String chosenItem;
    public JobItem(int id, String title) {
        this.id = id;
        this.title = title;
        this.chosenItem = "";
    }


    public String getChosenItem() {
        return chosenItem;
    }

    public void setChosenItem(String chosenItem) {
        this.chosenItem = chosenItem;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

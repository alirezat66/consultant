package greencode.ir.consulant.objects;

public class Course {
    String title;
    String description;
    String upload;


    public Course(String title, String desc, String upload) {
        this.title = title;
        this.description = desc;
        this.upload = upload;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return description;
    }

    public String getSource() {
        return upload;
    }

    public String getDescription() {
        return description;
    }

    public String getDocSource() {
        return upload;
    }


}

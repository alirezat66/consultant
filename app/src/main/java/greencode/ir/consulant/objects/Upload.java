package greencode.ir.consulant.objects;

public class Upload {
    int type;
    String upload;

    public Upload(int type, String upload) {
        this.type = type;
        this.upload = upload;
    }

    public int getType() {
        return type;
    }

    public String getUpload() {
        return upload;
    }
}

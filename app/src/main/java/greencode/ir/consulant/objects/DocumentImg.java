package greencode.ir.consulant.objects;

public class DocumentImg {
    int id;
    String docTitle;
    String docSize;
    String docSource;

    public DocumentImg(int id ,String docTitle, String docSize, String docSource) {
        this.id = id;
        this.docTitle = docTitle;
        this.docSize = docSize;
        this.docSource = docSource;
    }

    public void setDocSource(String docSource) {
        this.docSource = docSource;
    }

    public int getId() {
        return id;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public String getDocSize() {
        return docSize;
    }

    public String getDocSource() {
        return docSource;
    }
}

package greencode.ir.consulant.addinfo;

public interface AddDocListener {
    void onRejected();
    void onAccept(String title,String desc);
}

package greencode.ir.consulant.main;


import greencode.ir.consulant.objects.MenuItem;

public interface MenuDialogInterface {
    public void onProfileClick();
    public void onChosenItem(MenuItem item);
    public void onFinish();
}

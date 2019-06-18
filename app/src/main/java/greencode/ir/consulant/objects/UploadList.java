package greencode.ir.consulant.objects;

import java.util.List;

import greencode.ir.consulant.utils.ToJsonClass;

public class UploadList extends ToJsonClass {
    List<Upload> uploads;

    public UploadList() {
    }

    public UploadList(List<Upload> uploads) {
        this.uploads = uploads;
    }

    public List<Upload> getUploads() {
        return uploads;
    }
}

package test.com.selfdefineview.dragtime;

import java.util.Calendar;

/**
 * Created by zhangsixia on 18/5/18.
 */

public class VideoInfo {

    private Calendar startTime;
    private Calendar endTime;
    private String fileName;


    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

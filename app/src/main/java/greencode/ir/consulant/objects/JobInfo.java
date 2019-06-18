package greencode.ir.consulant.objects;

import java.util.ArrayList;
import java.util.List;

import greencode.ir.consulant.utils.ToJsonClass;

public class JobInfo extends ToJsonClass{
    List<Integer> categories = new ArrayList<>();
    String jobTitleId;
    String experience;
    ListOfFreeTimes response_hours;
    String hourCost;
    AdviserLocation location;
    String degree;
    List<Course> courses;

    public JobInfo(List<Integer> categories, String jobTitleId, String experience,ListOfFreeTimes response_hours, String hourCost, AdviserLocation location, String degree, List<Course> courses) {
        this.categories = categories;
        this.jobTitleId = jobTitleId;
        this.experience = experience;
        this.response_hours = response_hours;
        this.hourCost = hourCost;
        this.location = location;
        this.degree = degree;
        this.courses = courses;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ListOfFreeTimes getResponse_hours() {
        return response_hours;
    }

    public void setResponse_hours(ListOfFreeTimes response_hours) {
        this.response_hours = response_hours;
    }

    public String getHourCost() {
        return hourCost;
    }

    public void setHourCost(String hourCost) {
        this.hourCost = hourCost;
    }

    public AdviserLocation getLocation() {
        return location;
    }

    public void setLocation(AdviserLocation location) {
        this.location = location;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void changeDays() {
        if(response_hours!=null){
        if(response_hours.getFreeTimes().size()>0) {
            for (FreeTime freeTime : response_hours.getFreeTimes()) {
                String pName = freeTime.getDayOfWeek();
                switch (freeTime.getDayOfWeek()) {

                    case "شنبه":
                        pName = "saturday";
                        break;
                    case "یک شنبه":
                        pName = "sunday";
                        break;
                    case "دوشنبه":
                        pName = "monday";
                        break;
                    case "سه شنبه":
                        pName = "tuesday";
                        break;
                    case "چهارشنبه":
                        pName = "wednesday";
                        break;
                    case "پنج شنبه":
                        pName = "thursday";
                        break;
                    case "جمعه":
                        pName = "friday";
                        break;
                }
                response_hours.updateDay(pName, freeTime);
            }
        }
        }
    }
}

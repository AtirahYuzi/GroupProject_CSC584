package com.esukarelawan.model;

public class ParticipationBean {

    private int activityId;
    private int volunteerId;

    private String volunteerName;
    private String activityTitle;

    private String status;
    private int hourEarned;
    
    private int totalParticipants;

    public ParticipationBean() {
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHourEarned() {
        return hourEarned;
    }

    public void setHourEarned(int hourEarned) {
        this.hourEarned = hourEarned;
    }
    
    public int getTotalParticipants() {
    return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
    this.totalParticipants = totalParticipants;
    }
}
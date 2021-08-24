package com.silvia.english.model;

public class Model_Meeting  {

        private String meeting_id;
        private String dosen_id;
        private String meeting_nama;
        private String meeting_status;
        private String created_at;
        private String updated_at;

    public Model_Meeting(String meeting_id, String dosen_id, String meeting_nama, String meeting_status, String created_at, String updated_at) {
        this.meeting_id = meeting_id;
        this.dosen_id = dosen_id;
        this.meeting_nama = meeting_nama;
        this.meeting_status = meeting_status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getDosen_id() {
        return dosen_id;
    }

    public void setDosen_id(String dosen_id) {
        this.dosen_id = dosen_id;
    }

    public String getMeeting_nama() {
        return meeting_nama;
    }

    public void setMeeting_nama(String meeting_nama) {
        this.meeting_nama = meeting_nama;
    }

    public String getMeeting_status() {
        return meeting_status;
    }

    public void setMeeting_status(String meeting_status) {
        this.meeting_status = meeting_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

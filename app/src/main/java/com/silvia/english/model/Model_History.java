package com.silvia.english.model;


public class Model_History  {

        private String meeting_skor_id;
        private String meeting_materi_id;
        private String siswa_id;
        private String skor;
        private String meeting_id;
        private String meeting_materi_tipe;
        private String meeting_materi_audio1;
        private Object meeting_materi_audio2;
        private String meeting_materi_teks;
        private String created_at;
        private String updated_at;
        private String dosen_id;
        private String meeting_nama;
        private String meeting_status;

    public Model_History(String meeting_skor_id, String meeting_materi_id, String siswa_id, String skor, String meeting_id, String meeting_materi_tipe, String meeting_materi_audio1, Object meeting_materi_audio2, String meeting_materi_teks, String created_at, String updated_at, String dosen_id, String meeting_nama, String meeting_status) {
        this.meeting_skor_id = meeting_skor_id;
        this.meeting_materi_id = meeting_materi_id;
        this.siswa_id = siswa_id;
        this.skor = skor;
        this.meeting_id = meeting_id;
        this.meeting_materi_tipe = meeting_materi_tipe;
        this.meeting_materi_audio1 = meeting_materi_audio1;
        this.meeting_materi_audio2 = meeting_materi_audio2;
        this.meeting_materi_teks = meeting_materi_teks;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.dosen_id = dosen_id;
        this.meeting_nama = meeting_nama;
        this.meeting_status = meeting_status;
    }

    public String getMeeting_skor_id() {
        return meeting_skor_id;
    }

    public void setMeeting_skor_id(String meeting_skor_id) {
        this.meeting_skor_id = meeting_skor_id;
    }

    public String getMeeting_materi_id() {
        return meeting_materi_id;
    }

    public void setMeeting_materi_id(String meeting_materi_id) {
        this.meeting_materi_id = meeting_materi_id;
    }

    public String getSiswa_id() {
        return siswa_id;
    }

    public void setSiswa_id(String siswa_id) {
        this.siswa_id = siswa_id;
    }

    public String getSkor() {
        return skor;
    }

    public void setSkor(String skor) {
        this.skor = skor;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getMeeting_materi_tipe() {
        return meeting_materi_tipe;
    }

    public void setMeeting_materi_tipe(String meeting_materi_tipe) {
        this.meeting_materi_tipe = meeting_materi_tipe;
    }

    public String getMeeting_materi_audio1() {
        return meeting_materi_audio1;
    }

    public void setMeeting_materi_audio1(String meeting_materi_audio1) {
        this.meeting_materi_audio1 = meeting_materi_audio1;
    }

    public Object getMeeting_materi_audio2() {
        return meeting_materi_audio2;
    }

    public void setMeeting_materi_audio2(Object meeting_materi_audio2) {
        this.meeting_materi_audio2 = meeting_materi_audio2;
    }

    public String getMeeting_materi_teks() {
        return meeting_materi_teks;
    }

    public void setMeeting_materi_teks(String meeting_materi_teks) {
        this.meeting_materi_teks = meeting_materi_teks;
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
}

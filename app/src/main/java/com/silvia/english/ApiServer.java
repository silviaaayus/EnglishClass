package com.silvia.english;

public class ApiServer {


    private String HOST ="http://192.168.100.15/english/";


    public  String LOGIN = HOST + "login.php";
    public  String MEETING = HOST + "list_meeting.php?dosen_id=";
    public  String LISTENING = HOST + "listening.php?meeting_id=";
    public  String SOAL = HOST + "getSoal.php?meeting_id=";
    public  String SKOR = HOST + "insert_nilai.php?meeting_id=";
    public  String SPEAKING = HOST + "record.php?meeting_id=";
    public  String HISTORY = HOST + "history.php?siswa_id=";
    public  String CHANGE_PASSWORD = HOST + "update_password.php";

    public  String UPLOAD_SUARA = HOST + "insert_record.php";
    public  String SUARA = HOST + "audio/";
}

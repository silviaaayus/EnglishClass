package com.silvia.english;

public class ApiServer {


    private String HOST ="http://englishclass.hukumptpadang.com/";


    public  String LOGIN = HOST + "ApiEnglish/login.php";
    public  String MEETING = HOST + "ApiEnglish/list_meeting.php?dosen_id=";
    public  String LISTENING = HOST + "ApiEnglish/listening.php?meeting_id=";
    public  String SOAL = HOST + "ApiEnglish/getSoal.php?meeting_id=";
    public  String SKOR = HOST + "ApiEnglish/insert_nilai.php?meeting_id=";
    public  String SPEAKING = HOST + "ApiEnglish/record.php?meeting_id=";
    public  String HISTORY = HOST + "ApiEnglish/history.php?siswa_id=";
    public  String CHANGE_PASSWORD = HOST + "ApiEnglish/update_password.php";

    public  String UPLOAD_SUARA = HOST + "ApiEnglish/insert_record.php";
    public  String SUARA = HOST + "assets/audio/";
}

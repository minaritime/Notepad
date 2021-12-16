package kr.octo.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import kr.octo.notepad.FileIOStream.FileIOStreamCheckFile;
import kr.octo.notepad.FileIOStream.FileIOStreamRead;
import kr.octo.notepad.FileIOStream.FileIOStreamWrite;

public class SubActivity extends AppCompatActivity {

    FileIOStreamCheckFile cFileIOStreamCheckFile;
    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;

    // 토스트 메시지 생성자, 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    EditText etTitle;   // EditText 생성자, 제목역할
    EditText etContent; // EditText 생성자, 내용역할
    String title;       // 문자열 생성자, 제목역할

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);  // activity_sub 레이아웃 연결

        cFileIOStreamCheckFile = new FileIOStreamCheckFile(this);       // FileIOStreamCheckFile 클래스 객체생성
        cFileIOStreamWrite = new FileIOStreamWrite(this);               // FileIOStreamWrite 클래스 객체생성
        cFileIOStreamRead = new FileIOStreamRead(this);                 // FileIOStreamRead 클래스 객체생성

        etTitle = (EditText) findViewById(R.id.etTitle);          // etTitle에 title id 연결
        etContent = (EditText) findViewById(R.id.etContents);     // etContent에 contents id 연결

        Intent intent = getIntent();                            // 인텐트 생성자
        title = intent.getStringExtra("title");           // 인텐트로 제목 인자값 가져오기

        //제목에 setText함수 호출
        etTitle.setText(title);                                 // 제목 텍스트 set
        etContent.setText(cFileIOStreamRead.readData(title));   // 내용 텍스트 set 읽기
    }

    @Override
    public void onBackPressed() {
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 토스트 메시지 띄우기
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 저장됩니다.", Toast.LENGTH_SHORT);    // 토스트 메시지
            toast.show();   // 토스트 메시지 보여주기
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {

            System.out.println("삭제시작함");
            String path = getFilesDir().getAbsolutePath() + "/memo/" + title;
            System.out.println("삭제경로 : " + path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            boolean deleted = directory.delete();
            System.out.println("삭제완료");

            // 파일에 문자 쓰기
            cFileIOStreamCheckFile.checkFile(etTitle.getText().toString());
            cFileIOStreamWrite.writeData(etTitle.getText().toString(), etContent.getText().toString());

            // 저장후  서브액티비티  종료
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            toast.cancel(); // 토스트 메시지 cancel
        }
    }
}
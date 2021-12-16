package kr.octo.notepad;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.octo.notepad.FileIOStream.FileIOStreamCheckDir;

public class MainActivity extends AppCompatActivity {

    FileIOStreamCheckDir cFileIOStreamCheckDir;

    public static final int sub = 1001;             // 다른 액티비티를 띄우기 위한 요청코드(상수)

    List<String> lstFilesNameList = new ArrayList<>(); //  맨 앞에 ArrayList 추가하기
    ListView ListView ;                             // 리스트뷰 생성자
    ListViewAdapter listViewAdapter;                // 리스트뷰어댑터 생성자

    @Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 리스트뷰 변수에 ListView 레이아웃 연결
        ListView = (ListView) findViewById(R.id.ListView);

        String sPath = getFilesDir().getAbsolutePath() + "/memo/";

        // 파일 디렉토리 = 파일 경로로 객체 생성
        File directory = new File(sPath);
        File[] files = directory.listFiles();

        /**
         * 파일 안에 있는 텍스트 길이가 없으면 파일에 있는 길이를 쓸때마다 그 길이를 늘려준다
         */
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                lstFilesNameList.add(files[i].getName());
            }
        }
        System.out.println("파일목록 : " + lstFilesNameList);

        // 경로확인
        cFileIOStreamCheckDir = new FileIOStreamCheckDir(this);
        cFileIOStreamCheckDir.checkDir();
        
        View footer = getLayoutInflater().inflate(R.layout.listview_footer, null, false);
        ListView.addFooterView(footer);

        listViewAdapter = new ListViewAdapter();    // 리스트뷰어댑터 객체 생성
        ListView.setAdapter(listViewAdapter);       // 리스트뷰어댑터 값 지정

        // 파일 이름을 additem에 size를 추가한다
        for (int i = 0; i < lstFilesNameList.size(); i++) {
            listViewAdapter.addItem(lstFilesNameList.get(i));
        }
        
        // 메모장 추가 버튼 클릭, 페이지 전환버튼
        Button addButton = (Button) footer.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SubActivity.class); //  버튼을 클릭하면 SubActivity로 화면전환
                startActivity(intent);  // SubActivity 액티비티 시작
                finish();
            }
        });

        /**
         * 아이템클릭
         */
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                String sTitle = item.getTitle() ;     // 아이템클릭에 타이틀이름 연결
                lstFilesNameList.get(position);
                String sText = parent.getItemAtPosition(position).toString();
                DialogClick(v, position);   // 다이얼로그 클릭과 연동
            }
        });
    }


    public void DialogClick(View view, int listpos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getTitle()).setMessage(getTitle());

        /**
         * 수정버튼
         */
        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);     // 수정버튼을 클릭하면 SubActivity로 이동
                intent.putExtra("title", lstFilesNameList.get(listpos));              // 인텐트로 타이틀 값 전달
                startActivity(intent);  // 액티비티 띄우기
            }
        });

        /**
         * 취소버튼
         */
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {     
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        /**
         * 삭제버튼
         */
        builder.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listViewAdapter.listViewItemList.remove(listpos);       // 리스트뷰아이템 목록 포지션

                System.out.println("삭제시작함");                         // 삭제 시작했다고 출력
                String path = getFilesDir().getAbsolutePath() + "/memo/" + lstFilesNameList.get(listpos);
                System.out.println("삭제경로 : " + path);                 // 삭제 경로 출력
                File directory = new File(path);                        // 파일 디렉토리 객체생성
                File[] files = directory.listFiles();
                boolean deleted = directory.delete();                   // 디렉토리 삭제
                System.out.println("삭제완료");                           // 삭제완료 출력
                listViewAdapter.notifyDataSetChanged();                 // 리스트뷰 갱신

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
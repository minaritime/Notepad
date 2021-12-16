package kr.octo.notepad;

public class ListViewItem {
    private String sTitle ;   //  타이틀 변수 생성

    /* 타이틀 값 지정*/
    public void setTitle(String title) {
        sTitle = title ;
    }
    /* 타이틀 값 리턴*/
    public String getTitle() {
        return this.sTitle ;
    }
}

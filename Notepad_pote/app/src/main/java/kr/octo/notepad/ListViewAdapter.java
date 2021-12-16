package kr.octo.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    public ListViewAdapter() {  // 리스트뷰어댑터 메소드
    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }   // 리스트뷰아이템목록을 사이즈만큼 리턴

    @Override
    public View getView(int iPosition, View vConvertView, ViewGroup vgParent) {
        final int pos = iPosition;   // 포지션 상수 선언
        final Context context = vgParent.getContext();    // 콘텐츠 상수 선언
        // 리스트뷰아이템 레이아웃을 inflate하여 convertView 참조 획득
        if (vConvertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vConvertView = inflater.inflate(R.layout.listview_item, vgParent, false);
        }
        // 화면에 표시될 titleTextView(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) vConvertView.findViewById(R.id.TextView);

        ListViewItem listViewItem = listViewItemList.get(iPosition);
        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        return vConvertView;
    }

    // 지정된 위치(position)에 있는 데이터와 관계된 아이템(row)의 id를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정된 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String title) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        listViewItemList.add(item);
    }
}

package com.heyi.UniversityNews.View;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heyi.UniversityNews.R;
import com.taobao.api.internal.toplink.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by heyi on 2016/12/7.
 */

public class RefreshListView extends ListView implements  android.widget.AdapterView.OnItemClickListener{
    private View header;
    private int measuredHeight;
    private int downY=-1;
    private int moveY;
    private int distance;
    private int moveDistance;
    private int current=0;
    private int PULL_REFRESH=1;
    private int SONG_REFRESH=2;
    private int REFRESHING=3;
    private ImageView arrow;
    private ProgressBar pb_freshing;
    private TextView refresh_time;
    private TextView refresh_text;
    private RotateAnimation rotateAnimation;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    private void initHeaderView() {
       header=View.inflate(getContext(), R.layout.refresh_list_view,null);
        arrow= (ImageView) header.findViewById(R.id.arrow_refresh);
        pb_freshing= (ProgressBar) header.findViewById(R.id.pb_refreshing);
        refresh_time= (TextView) header.findViewById(R.id.refresh_time);
        refresh_text= (TextView) header.findViewById(R.id.refresh_text);

        addHeaderView(header);
        header.measure(0,0);
        measuredHeight = header.getMeasuredHeight();
        setPadding(0,-measuredHeight,0,0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY= (int) ev.getRawY();
            break;
            case MotionEvent.ACTION_MOVE:
                if(downY==-1){
                    downY= (int) ev.getRawY();
                }
                if (current == REFRESHING) {// 正在刷新时不做处理
                    break;
                }
                   moveY= (int) ev.getRawY();
                   distance=moveY-downY;
                    moveDistance=-measuredHeight+distance;
                    if(distance>0 && getFirstVisiblePosition()==0){
                        setPadding(0,moveDistance,0,0);
                        if(current==0&&moveDistance<0){
                            pullRefresh();
                            current=PULL_REFRESH;
                        }else if(moveDistance>=0&&current==PULL_REFRESH){
                              current=SONG_REFRESH;
                            songRefresh();
                        }else if(current==SONG_REFRESH&&moveDistance<0){
                            pullRefresh();
                            current=PULL_REFRESH;
                        }

                    }



                break;
            case MotionEvent.ACTION_UP:
                downY=-1;
                if(current==PULL_REFRESH){
                    setPadding(0,-measuredHeight,0,0);
                    current=0;
                }else if(current==SONG_REFRESH){
                    setPadding(0,0,0,0);
                    current=REFRESHING;
                    refreshing();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void pullRefresh(){
        if(current==SONG_REFRESH){
            refresh_text.setText("下拉刷新");
            rotateAnimation=new RotateAnimation(180,360, Animation.RELATIVE_TO_SELF
                    ,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(500);
            rotateAnimation.setFillAfter(true);
            arrow.startAnimation(rotateAnimation);
        }else {
            refresh_text.setText("下拉刷新");
        }
    }
    private void songRefresh(){
        refresh_text.setText("松开刷新");
        rotateAnimation = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF
                ,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        arrow.startAnimation(rotateAnimation);
    }
    private void refreshing(){
        arrow.clearAnimation();
        arrow.setVisibility(INVISIBLE);
        pb_freshing.setVisibility(VISIBLE);
        refresh_text.setText("刷新中...");
        if (mListener!=null){
            mListener.onRefresh();
        }
    }
    OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener listener){
        mListener=listener;
    }
    public interface OnRefreshListener{
        public void onRefresh();
    }
    public void onRefershComplete(boolean success){
        current=0;
        refresh_text.setText("下拉刷新");
        pb_freshing.setVisibility(View.INVISIBLE);
        arrow.setVisibility(View.VISIBLE);
        if(success){
            refresh_time.setText("上次刷新:"+new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        }
        setPadding(0, -measuredHeight, 0, 0);
    }




    OnItemClickListener mItemClickListener;

    @Override
    public void setOnItemClickListener(
            android.widget.AdapterView.OnItemClickListener listener) {
        super.setOnItemClickListener(this);

        mItemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(parent, view, position
                    - getHeaderViewsCount(), id);
        }
    }
}

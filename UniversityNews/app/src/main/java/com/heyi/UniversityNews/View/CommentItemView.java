package com.heyi.UniversityNews.View;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.heyi.UniversityNews.R;
import com.lidroid.xutils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by heyi on 2016/12/25.
 */

public class CommentItemView {
    private View view;
    private CircleImageView comment_icon;
    private TextView user_nick_name;
    private TextView user_comment;
    private TextView comment_date;
    private TextView comment_edit_num;
    private TextView comment_bad_num;
    private TextView comment_good_num;
    private Context context;

    public CommentItemView(Context context){
        this.context=context;
        view=View.inflate(context, R.layout.comment_item,null);
        initView();
    }
    private void initView(){
        comment_icon = (CircleImageView) view.findViewById(R.id.comment_user_icon);
        user_nick_name = (TextView) view.findViewById(R.id.comment_user_nick_name);
        user_comment = (TextView) view.findViewById(R.id.comment_user_comment_tv);
        comment_date = (TextView) view.findViewById(R.id.comment_user_date);
        comment_edit_num = (TextView) view.findViewById(R.id.comment_user_comment_num);
        comment_bad_num = (TextView) view.findViewById(R.id.comment_user_bad_num);
        comment_good_num = (TextView) view.findViewById(R.id.comment_user_good_num);
    }
    public void setUserIcon(String iconUrl){
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.display(comment_icon,iconUrl);
    }

    public void setUserNickName(String nickName){
        user_nick_name.setText(nickName);
    }

    public void setUserEdit(String edit){
        user_comment.setText(edit);
    }

    public void setCommentDate(String date){
        comment_date.setText(date);
    }

    public void setCommentEditNum(int editNum){
        if(editNum>999){
            comment_edit_num.setText("999+");
        }else{
            comment_edit_num.setText(""+editNum);
        }
    }

    public void setCommentBadNum(int badNum){
        if(badNum>999){
            comment_bad_num.setText("999+");
        }else{
            comment_bad_num.setText(""+badNum);
        }
    }

    public void setCommentGoodNum(int goodNum){
        if(goodNum>999){
            comment_good_num.setText("999+");
        }else{
            comment_good_num.setText(""+goodNum);
        }
    }
    public View getView(){
        return view;
    }

}

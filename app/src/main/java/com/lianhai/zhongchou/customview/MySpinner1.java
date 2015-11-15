package com.lianhai.zhongchou.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.CommonAdapter;
import com.lianhai.zhongchou.adapter.ViewHolder;
import com.lianhai.zhongchou.config.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asdfgh on 15/10/15.
 */
public class MySpinner1 extends TextView {
    private int width;
    private int height;
    private List<String> list;//数据
    private String currentText;//当前选中的
    private MySpinnerAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PopupWindows popupWindows= (PopupWindows) msg.obj;
            popupWindows.dismiss();
        }
    };

    public MySpinner1(Context context) {
        this(context,null);

    }

    public MySpinner1(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public MySpinner1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        list=new ArrayList<String>();
        currentText="";
    }

    /**
     * 点击时调用
     * @return
     */
    @Override
    public boolean performClick() {
        Log.e("点击","--------");
        PopupWindows popupWindows=new PopupWindows(getContext(),list);
        popupWindows.showAsDropDown(this);
        return true;
    }

    /**
     * 设置当前textview的值
     * @param text
     */
    public void setCurrentText(String text){
        setText(text);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);   //获取ViewGroup宽度
        height = MeasureSpec.getSize(heightMeasureSpec);//获取ViewGroup高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class PopupWindows extends PopupWindow{
        private View view;
        private List<String> list;

        public PopupWindows(Context context,List<String> list) {
            this.list=list;
            view = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null);
            setContentView(view);
            setWidth(width);
            if (list.size()>5){
                setHeight(MyApplication.getScreen_height() / 3);
            }else {
                setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corners_white));
            setFocusable(true);//设置窗口可点
            //如果是弹窗外的点击 就取消
            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {

                    int height = view.getMeasuredHeight();
                    int y = (int) arg1.getY();
                    if (arg1.getAction() == MotionEvent.ACTION_UP) {
                        if (y > height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
            ListView listView= (ListView) view.findViewById(R.id.spinner_lsitview);
            adapter=new MySpinnerAdapter(context,list,R.layout.spinner_item_layout_1);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView label = (TextView) view .findViewById(R.id.spinner_item_text);
                    currentText=label.getText().toString().trim();
                    setCurrentText(currentText);
                    dismiss();
                    listener.onClick(position);

                }
            });
        }

    }
    private class MySpinnerAdapter extends CommonAdapter<String>{

        protected MySpinnerAdapter(Context context, List<String> list, int resource) {
            super(context, list, resource);
        }

        @Override
        public void setDataToItem(ViewHolder holder, String s) {

            TextView label = (TextView) holder .getView(R.id.spinner_item_text);
            label.setText(s);


        }
    }

    /**
     * 设置数据
     */
    public void setList(List<String> list){
        this.list=list;
    }


    private onSpinnerClickListener listener;

    public interface onSpinnerClickListener{
        public void onClick(int position);
    };

    public void setClickListener(onSpinnerClickListener listener){
        this.listener=listener;
    }


}

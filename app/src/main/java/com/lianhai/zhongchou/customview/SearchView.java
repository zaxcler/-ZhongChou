package com.lianhai.zhongchou.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianhai.zhongchou.R;

/**
 * Created by zaxcler on 15/11/11.
 */
public class SearchView extends LinearLayout{
    private LinearLayout mlinearlayout;
    private EditText mEditText;//输入框
    private TextView textView;//搜索文字
    private ImageView imageView;//搜索图标
    private Context context;
    private CharSequence currentText;//当前内容
    private Drawable search;
    private View searchView;//搜索
    private int resourceid;

    private TextChangeLisenter textChangeLisenter;
    private SearchLisenter searchLisenter;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        int resource[]=new int[]{android.R.drawable.ic_menu_search};
        //获取系统搜索图标
//        TypedArray ta=context.getTheme().obtainStyledAttributes(resource);
//        search =ta.getDrawable(resource[0]);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.SearchView,defStyle,0);
//        search=ta.getDrawable(R.styleable.SearchView_searchImgae);
        resourceid=ta.getResourceId(R.styleable.SearchView_searchImgae,0);
        Log.e("id",resourceid+"");
        initView();
        ta.recycle();

    }

    /**
     * 初始化界面
     */
    private void initView() {

        this.setBackgroundColor(new Color().rgb(224, 224, 224));//背景色为灰色
        ViewGroup.LayoutParams wlp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
        this.setLayoutParams(wlp);


        /**
         * 外层linearlayout
         */
        mlinearlayout =new LinearLayout(context);
        mlinearlayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams vlp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vlp.setMargins(40, 20, 40, 20);
        vlp.gravity= Gravity.CENTER;
        mlinearlayout.setBackgroundColor(Color.WHITE);
        this.addView(mlinearlayout, vlp);


        /**
         * 搜索框
         */
        mEditText=new EditText(context);
        mEditText.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams elp=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        elp.weight=1;
        mEditText.setHint("请输入搜索内容");
        mEditText.setPadding(5, 5, 5, 5);
        mlinearlayout.addView(mEditText, elp);


        /**
         * 搜索的图片
         */

        if (resourceid==0){
            textView=new TextView(context);
            textView.setText("搜索");
            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0,0,5,0);
            mlinearlayout.addView(textView, llp);
            searchView=textView;
        }else {
            imageView=new ImageView(context);
            imageView.setImageDrawable(context.getResources().getDrawable(resourceid));
            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0,0,2,0);
            mlinearlayout.addView(imageView, llp);
            searchView=imageView;
        }




        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textChangeLisenter != null) {
                    textChangeLisenter.onTextChange(charSequence);
                    currentText = charSequence;
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchLisenter != null) {
                    searchLisenter.doSearch(SearchView.this, currentText);
                }

            }
        });

    }


    public interface TextChangeLisenter{
         void onTextChange(CharSequence result);

    }

    public interface SearchLisenter{
        void doSearch(View view,CharSequence currentText);

    }


    public void setSearchLisenter(SearchLisenter searchLisenter) {
        this.searchLisenter = searchLisenter;
    }

    public void setTextChangeLisenter(TextChangeLisenter textChangeLisenter) {
        this.textChangeLisenter = textChangeLisenter;
    }
}

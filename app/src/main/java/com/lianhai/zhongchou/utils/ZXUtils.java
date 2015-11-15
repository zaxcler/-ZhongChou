package com.lianhai.zhongchou.utils;


import com.lianhai.zhongchou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

public class ZXUtils {

    private static TextView title;// 标题
    private static ImageView back;// 返回键
    private static ImageView rightbutton;//右侧按钮
    private static RightButtonLisenter button;//右边按钮的回调

    /**
     * 初始化title
     *
     * @param activity          依附的activity
     * @param titleString       名字
     * @param isShowRightButton 是否有右侧按钮
     */
    public static void initTitle(final Activity activity, String titleString, boolean isShowRightButton) {
        title = (TextView) activity.findViewById(R.id.title);
        back = (ImageView) activity.findViewById(R.id.back);
        rightbutton = (ImageView) activity.findViewById(R.id.more);
        title.setText(titleString);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                activity.finish();
            }
        });
        if (isShowRightButton) {
            button.addRightButton(rightbutton);
        } else {
            rightbutton.setVisibility(View.GONE);
        }

    }

    /*
     * title右侧按键的接口  如果右侧有按钮需调用该接口 实现按钮功能
     */
    public interface RightButtonLisenter {
        public void addRightButton(ImageView imageView);
    }

    /*
     * 设置右侧接口的方法
     */
    public static void setRightButton(RightButtonLisenter lisenter) {
        button = lisenter;
    }

    /**
     * 跳转activity 不带数据直接跳转
     *
     * @param activity
     * @param clazz
     */
    public static void goActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * 打开activity 返回结果
     *
     * @param activity
     * @param clazz
     * @param requestCode
     */
    public static void goActivityForResult(Activity activity, Class clazz, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 显示输入法
     */
    public static void showInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(0,
                InputMethodManager.SHOW_FORCED);
        // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的

    }

    /**
     * 隐藏输入法
     */
    public static void closeInputMethod(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {
            // 如果是开启状态，则关闭
//			imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            imm.toggleSoftInput(0,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /*
     * 打开系统相册的方法
     * path 设置保存的uri地址
     */
    public static void chosePhoto(Activity activity, int requestCode) {
        //解决4.4和4.4以前的打开图库获取的图片路径不同的问题
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, path);//设置保存的uri地址
        activity.startActivityForResult(intent, requestCode);

    }

    /**
     * 剪裁图片
     *
     * @param activity    上下文
     * @param outputX     宽
     * @param outputY     高
     * @param path        保存图片的uri
     * @param requestCode 请求码
     */
    public static void cropPhoto(Activity activity, int outputX, int outputY, int requestCode, Uri path) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(path, "image/*");//设置数据path（地址） 和类型
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);//X方向上的比例
        intent.putExtra("aspectY", 1);//Y方向上的比例
        intent.putExtra("outputX", outputX);//宽
        intent.putExtra("outputY", outputY);//高
        intent.putExtra("scale", false);//是否保持保留缩放比例
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * dp转换为px
     */
    public static float dp2Px(Activity activity, float dp) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float scale = dm.density;
        return dp / scale;
    }

    /**
     * px转换为dp
     */
    public static float px2Dp(Activity activity, float px) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float scale = dm.density;
        return px * scale;
    }

    /**
     * byte转换成String
     *
     * @param bytes
     * @return
     */
    public static String byte2String(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            result.append(i);
        }
        return result.toString();

    }

    public final static String MD5(String s) {
        try {
            byte[] btInput = s.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(btInput);
            byte[] md = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = md[i] & 0xff;
                if (val < 16) sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 显示富文本
     * @param html
     */
    public static CharSequence showFWB(TextView textView,String html){
        return Html.fromHtml(html, new MyImagegetter(), null);

    }

    public static class  MyImagegetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String s) {
            Drawable drawable=null;
            try {
                URL url=new URL(s);
                drawable=Drawable.createFromStream(url.openStream(),"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

            return drawable;
        }
    }

}

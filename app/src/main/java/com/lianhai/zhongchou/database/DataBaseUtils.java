package com.lianhai.zhongchou.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zaxcler on 15/11/9.
 */
public class DataBaseUtils {
    private static String filename="meituan_cities.db";//数据库名
    private static String filePath="";//数据库存储路径
    private static String path="/zhongchoudb";//数据库存文件夹

    private static SQLiteDatabase database;
    private static Context mContext;

    public static synchronized SQLiteDatabase opDatabase(Context context){
        mContext=context;
        filePath= Environment.getExternalStorageDirectory()+path+filename;
        File db=new File(filePath);
        /**
         * 存在数据库就直接打开
         */
        if (db.exists()){
            Log.e("database","数据库创建成功");
             database=SQLiteDatabase.openOrCreateDatabase(filePath,null);
            return SQLiteDatabase.openOrCreateDatabase(filePath,null);
        }else {
            /**
             * 不存在，则先创建文件夹
             */
            File filepack=new File(Environment.getExternalStorageDirectory()+path);
            if (filepack.mkdir()){
                Log.i("test", "创建成功");
            }else{
                Log.i("test", "创建失败");
            };


            try {
                /**
                 * 得到asset文件件下的资源
                 */
                AssetManager am=context.getAssets();
                //打开输入流
                InputStream in=am.open("meituan_cities.db");
                //用输出流写到sd卡
                FileOutputStream out=new FileOutputStream(filePath);
                byte[] buffer=new byte[1024];
                int count=0;
                while ((count=in.read(buffer))>0){
                    out.write(buffer,0,count);
                }
                out.flush();
                out.close();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("e", e.toString());
                return null;
            }
            return opDatabase(context);

        }

    }

    /**
     * 查询
     * @param sql sql语句，若上sql语句中存在占位符，则对应下面参数
     * @param param 占位符对应的字段
     */
    public static String query(String sql,String[] param){
        Cursor cursor=database.rawQuery(sql,param);
        String result=null;
        if (cursor.moveToFirst()){
            result=cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        return result;

    }

    /**
     * 得到数据库
     * @return
     */
    public static SQLiteDatabase getDatabase(){
        return database;
    }



}

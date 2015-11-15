package com.lianhai.zhongchou.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.database.DataBaseUtils;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

;

public class MyApplication extends Application {

	private static int SCREEN_WIDTH;// 屏幕宽度
	private static int SCREEN_HEIGHT;// 屏幕高度
	public static SharedPreferences preferences;// 本地保存用户信息
	public static SharedPreferences.Editor editor;// 信息编辑器

	public static DisplayImageOptions options_image;// 下载图片的默认配置
	public static DisplayImageOptions options_photo;// 下载图片的默认配置

	private static Map<String, Set<Activity>> activityMAP;
	private static Context context;//全局上下文对象





	@Override
	public void onCreate() {
		context=getApplicationContext();
		super.onCreate();
		preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);// 初始化sharepreference
		editor = preferences.edit();// 初始化编辑器

		initImageLoader(getApplicationContext());// 初始化imageloader
		// initImageLoader1(getApplicationContext());//初始化imageloader
		// 初始化时得到屏幕尺寸
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
		// 初始化保存activity的集合
		@SuppressWarnings("unused")
		Set<Activity> activities = new HashSet<Activity>();
		activityMAP = new HashMap();
		Log.e("screen_width--------", SCREEN_WIDTH + "");

		//初始化数据库
		DataBaseUtils.opDatabase(getContext());


	}

	public static void initImageLoader(Context context) {


		
		File cacheDir =StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache"); 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// taotao
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new LimitedAgeDiscCache(cacheDir, 1000*60*60*24*3))
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileCount(100)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		
		

		L.disableLogging();
//		ImageLoader.getInstance().init(config);
		options_image = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.showImageOnFail(R.drawable.add).showImageForEmptyUri(R.drawable.add)
				.showImageOnFail(R.drawable.add).build();
//		options_photo = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
//				.bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//				.showImageOnFail(R.drawable.photo).showImageOnLoading(R.drawable.photo)
//				.showImageForEmptyUri(R.drawable.photo)
//				.showImageOnFail(R.drawable.photo).build();
	}

	public static int getScreen_width() {
		// Log.e("screen_width", SCREEN_WIDTH+"");
		return SCREEN_WIDTH;
	}

	public static Context getContext() {
		return context;
	}

	public static void setScreen_width(int sCREEN_WIDTH) {
		SCREEN_WIDTH = sCREEN_WIDTH;
	}

	public static int getScreen_height() {
		return SCREEN_HEIGHT;
	}

	public static void setScreen_height(int sCREEN_HEIGHT) {
		SCREEN_HEIGHT = sCREEN_HEIGHT;
	}

	/**
	 * 添加activity到map集合(同一流程的要一起关闭的添加到一个group)
	 * 
	 * @param activity
	 * @param tag
	 *            activity的标志（用来控制开关）
	 */
	public synchronized static void addActivityToMap(Activity activity, String tag) {

		Set<Activity> group = activityMAP.get(tag);
		if (group == null) {
			group = new HashSet<Activity>();
			activityMAP.put(tag, group);
		}
		group.add(activity);
	}

	/**
	 * 根据标签关闭activity
	 * 
	 * @param tag
	 */
	public synchronized static void destoryActivity(String tag) {

		Set<Activity> group = activityMAP.get(tag);
		if (group == null) {
			return;
		}
		for (Activity one : group) {
			if (one != null) {
				one.finish();
			}
			activityMAP.remove(tag);
		}

	}

	/**
	 * 关闭所有activity 调用上面关闭每个group的代码
	 */
	public synchronized static void destoryAllActivity() {

		for (Map.Entry<String, Set<Activity>> entry : activityMAP.entrySet()) {

			destoryActivity(entry.getKey());
		}
		activityMAP = null;
	}

	/**
	 * 保存用户信息
	 * @param info
	 */
	public static void saveUserInfo(UserInfo info) {
		Log.e("info",info.toString());
		SharedPreferences.Editor editor = MyApplication.editor;
		if (info.getId() != 0) {
			editor.putInt("UserId", info.getId());
		}
		if (info.getUsername() != null) {
			editor.putString("UserName", info.getUsername());
		}
		if (info.getRole() != null) {
			editor.putString("Role", info.getRole());
		}
		if (info.getMoney() != null) {
			editor.putString("money", info.getMoney());
		}

		  editor.putInt("Auth_status", info.getAuth_status());

		if (info.getAuth_type() != null) {
			editor.putString("Auth_type", info.getAuth_type());
		}
		if (info.getEmail() != null) {
			editor.putString("Email", info.getEmail());
		}
		if (info.getEmail_auth() != null) {
			editor.putString("Email_auth", info.getEmail_auth());
		}
		if (info.getGravatar() != null) {
			editor.putString("Gravatar", info.getGravatar());
		}
		if (info.getIdentify() != null) {
			editor.putString("Identify", info.getIdentify());
		}
		if (info.getIdentify_fan() != null) {
			editor.putString("Identify_fan", info.getIdentify_fan());
		}
		if (info.getIdentify_zheng() != null) {
			editor.putString("Identify_zheng", info.getIdentify_zheng());
		}
		if (info.getLogtime() != null) {
			editor.putString("Logtime", info.getLogtime());
		}
		if (info.getPassword() != null) {
			editor.putString("Password", info.getPassword());
		}
		if (info.getIp()!= null) {
			editor.putString("Ip", info.getIp());
		}
		if (info.getRealname()!= null) {
			editor.putString("Realname", info.getRealname());
		}
		if (info.getTelephone_auth()!= null) {
			editor.putString("Telephone_auth", info.getTelephone_auth());
		}
		if (info.getTelephone()!= null) {
			editor.putString("Telephone", info.getTelephone());
		}
		if (info.getRegtime()!= null) {
			editor.putString("Regtime", info.getRegtime());
		}
		if (info.getSalt()!= null) {
			editor.putString("Salt", info.getSalt());
		}
		if (info.getStatus()!= null) {
			editor.putString("Status", info.getStatus());
		}
		editor.commit();

	}
	
	

}

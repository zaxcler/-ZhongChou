
package com.lianhai.zhongchou.pay.lianlianpay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lianhai.zhongchou.bean.OrderInfo;
import com.lianhai.zhongchou.config.BaseInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LianLianPay {
	private Context context;
	private OrderInfo info;

	public LianLianPay(Context context){
		this.context=context;
		mHandler=createHandler(context);
	}

	/*
	 * 支付验证方式 0：标准版本， 1：卡前置方式，此两种支付方式接入时，只需要配置一种即可，Demo为说明用。可以在menu中选择支付方式。
	 */
	private int pay_type_flag = 1;

	public void pay(OrderInfo info) {

		this.info=info;
		PayOrder order = null;

			// TODO 卡前置方式, 如果传入的是卡号，卡号必须大于等于14位
//
			order = constructPreCardPayOrder();
			String content4Pay = BaseHelper.toJSONString(order);
			Log.e("order",order.toString());
			Log.i(LianLianPay.class.getSimpleName(), content4Pay);

			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(content4Pay, mHandler, Constants.RQF_PAY, (Activity)context, false);

			Log.i(LianLianPay.class.getSimpleName(), String.valueOf(bRet));


	}

	private Handler mHandler;

	private Handler createHandler(final Context context) {
		return new Handler() {
			public void handleMessage(Message msg) {
				String strRet = (String) msg.obj;
				switch (msg.what) {
				case Constants.RQF_PAY: {
					JSONObject objContent = BaseHelper.string2JSON(strRet);
					String retCode = objContent.optString("ret_code");
					String retMsg = objContent.optString("ret_msg");
					// TODO 先判断状态码，状态码为 成功或处理中 的需要 验签
					if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
						// TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
//						if (pay_type_flag == 1) {
//							TextView tv_agree_no = (TextView) findViewById(R.id.tv_agree_no);
//							tv_agree_no.setVisibility(View.VISIBLE);
//							tv_agree_no.setText(objContent.optString("agreementno", ""));
//
//						}

						String resulPay = objContent.optString("result_pay");
						if (Constants.RESULT_PAY_SUCCESS.equalsIgnoreCase(resulPay)) {
							BaseHelper.showDialog((Activity)context, "提示", "支付成功，交易状态码：" + retCode,
									android.R.drawable.ic_dialog_alert);

//							new Handler().postDelayed(new Runnable(){
//								@Override
//								public void run() {
//
//								}
//							},1000);

							// TODO 支付成功后续处理
						} else {
							BaseHelper.showDialog((Activity)context, "提示", retMsg + "，交易状态码:" + retCode,
									android.R.drawable.ic_dialog_alert);
						}

					} else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
						String resulPay = objContent.optString("result_pay");
						if (Constants.RESULT_PAY_PROCESSING.equalsIgnoreCase(resulPay)) {
							BaseHelper.showDialog((Activity)context, "提示",
									objContent.optString("ret_msg") + "交易状态码：" + retCode,
									android.R.drawable.ic_dialog_alert);
						}

					} else {
						BaseHelper.showDialog((Activity)context, "提示", retMsg + "，交易状态码:" + retCode,
								android.R.drawable.ic_dialog_alert);
					}
				}
					break;
				}
				super.handleMessage(msg);
			}
		};

	}



	private PayOrder constructPreCardPayOrder() {

		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String timeString = dataFormat.format(date);

		PayOrder order = new PayOrder();
		order.setBusi_partner("101001");

		order.setNo_order(info.getOrdersn());

//		long dt_order=Long.valueOf(info.getCreate_order_time()+"000");
//		Date date_dt=new Date(dt_order);
//		Log.e("date_dt", dataFormat.format(date_dt));
//		if (date_dt!=null && dataFormat.format(date_dt)!=null){
//			order.setDt_order(dataFormat.format(date_dt));
//		}
		order.setDt_order(timeString);

		order.setName_goods(info.getName());

		order.setNotify_url(BaseInfo.GQ_HuiDiao);




		// MD5 签名方式
		order.setSign_type(PayOrder.SIGN_TYPE_MD5);
		// RSA 签名方式
		// order.setSign_type(PayOrder.SIGN_TYPE_RSA);


		order.setValid_order("100");

		order.setUser_id(info.getId());
        order.setId_no(info.getIdentify());

        order.setAcct_name(info.getRealname());
        order.setMoney_order(info.getMoney());//支付费用

        // 银行卡卡号，该卡首次支付时必填
        order.setCard_no(info.getCardId());//卡号
        // 银行卡历次支付时填写，可以查询得到，协议号匹配会进入SDK，
        order.setNo_agree("");


		// 风险控制参数。
		order.setRisk_item(constructRiskItem());

		String sign = "";
		order.setOid_partner(EnvConstants.PARTNER);
		String content = BaseHelper.sortParam(order);
		// MD5 签名方式
		sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
		// RSA 签名方式
		// sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
		order.setSign(sign);
		return order;
	}

	/**
	 * TODO 风险控制参数生成例子，请根据文档动态填写。最后返回时必须调用.toString()
	 */
	private String constructRiskItem() {
		JSONObject mRiskItem = new JSONObject();
		try {
			mRiskItem.put("user_info_bind_phone", info.getTelephone());
			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
			long register=Long.valueOf(info.getTime()+"000");
			Date register_date=new Date(register);
			Log.e("date_dt", dataFormat.format(register));
			if (register_date!=null && dataFormat.format(register_date)!=null){
				mRiskItem.put("user_info_dt_register", dataFormat.format(register_date));
			}

			mRiskItem.put("frms_ware_category", "2009");
			mRiskItem.put("request_imei", "1122111221");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mRiskItem.toString();
	}

//	private PayOrder constructPreCardPayOrder() {
//
//		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String timeString = dataFormat.format(date);
//
//		PayOrder order = new PayOrder();
//		order.setBusi_partner("101001");
//		order.setNo_order(timeString);
//		order.setDt_order(timeString);
//		order.setName_goods("龙禧大酒店中餐厅：2-3人浪漫套餐X1");
//		order.setNotify_url("http://beta.www.youtx.com/youtxpay/LianlianPay/YTPay_Notify.aspx");
//		// MD5 签名方式
//		order.setSign_type(PayOrder.SIGN_TYPE_MD5);
//		// RSA 签名方式
//		// order.setSign_type(PayOrder.SIGN_TYPE_RSA);
//
//		order.setValid_order("100");
//
//		order.setUser_id("123112312412");
//		order.setId_no("500236199212161735");
//
//		order.setAcct_name("钟炫");
//		order.setMoney_order("0.1");
//
//		// 银行卡卡号，该卡首次支付时必填
//		order.setCard_no("6217996530007316397");
//		// 银行卡历次支付时填写，可以查询得到，协议号匹配会进入SDK，
//		order.setNo_agree("");
//		// 风险控制参数。
//		order.setRisk_item(constructRiskItem());
//
//		String sign = "";
//		order.setOid_partner(EnvConstants.PARTNER);
//		String content = BaseHelper.sortParam(order);
//		// MD5 签名方式
//		sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
//		// RSA 签名方式
//		// sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
//		order.setSign(sign);
//		return order;
//	}

	private PayOrder constructSignCard() {

		PayOrder order = new PayOrder();
		order.setSign_type(PayOrder.SIGN_TYPE_MD5);
		// RSA 签名方式
		// order.setSign_type(PayOrder.SIGN_TYPE_RSA);

		
		
		order.setUser_id("asdbasdf1234");
        order.setId_no("");
        order.setAcct_name("");
        // 银行卡卡号，该卡首次支付时必填
        order.setCard_no("");//卡号

        
		String sign = "";
		order.setOid_partner(EnvConstants.PARTNER);
		String content = BaseHelper.sortParamForSignCard(order);
		// MD5 签名方式
		sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
		// RSA 签名方式
		// sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
		order.setSign(sign);
		return order;
	}

	
}

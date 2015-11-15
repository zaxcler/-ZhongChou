package com.lianhai.zhongchou.config;

/**
 * Created by zaxcler on 15/10/23.
 */
public class BaseInfo {

    //--------徐吉宁的接口-------
    public static String BaseUrl_xu ="http://zhongchouxm.hzlianhai.com/zc4/zs/";

    public static String IsLogin =BaseUrl_xu+"project.php/Inner/isLogin";

    public static String Home= BaseUrl_xu +"project.php/Inner/home";//首页
    public static String Project_list= BaseUrl_xu +"project.php/Inner/ajaxproList";//列表
    public static String area= BaseUrl_xu +"project.php/Inner/area";//获取地区
    public static String Project_detail= BaseUrl_xu +"project.php/Inner/stock";//股权详情
    public static String Project_detail_entity= BaseUrl_xu +"project.php/Inner/entity";//实物详情
    public static String Project_introduce_gq= BaseUrl_xu +"project.php/Inner/stockDetail";//股权介绍
    public static String Project_introduce_gy_xf= BaseUrl_xu +"project.php/Inner/entityDetail";//公益消费介绍
    public static String Comment_list= BaseUrl_xu +"project.php/Inner/comments";//评论列表
    public static String Comment= BaseUrl_xu +"project.php/Inner/comment";//评论
    public static String collection= BaseUrl_xu +"project.php/Inner/joinFavorite";//收藏项目
    public static String swift= BaseUrl_xu +"project.php/inner/swift";//现金流水
    public static String Collection_list= BaseUrl_xu +"project.php/Inner/favoriteList";//收藏的项目
    public static String Support_list= BaseUrl_xu +"project.php/inner/myStock";//投资的项目
    public static String Sponsor_list= BaseUrl_xu +"project.php/inner/myPush";//发起的项目
    public static String Invests_list= BaseUrl_xu +"project.php/inner/getInvestsByPid";//投资人列表
    public static String Join_Stock= BaseUrl_xu +"project.php/Inner/joinStock";//确人投资股权
    public static String Cancel_Stock= BaseUrl_xu +"project.php/Inner/cancelStock";//确人投资股权
    public static String Contract= BaseUrl_xu +"project.php/Inner/contContent";//合同
    public static String Agree_Contract= BaseUrl_xu +"project.php/Inner/agreementCont";//签合同
    public static String Order_Stock= BaseUrl_xu +"project.php/Inner/stockOrder";//下订单（股权）
    public static String Get_Order= BaseUrl_xu +"project.php/inner/getOrder";//获取订单
    public static String EntityDetail= BaseUrl_xu +"project.php/Inner/entityDetail";//公益消费回报详情
    public static String EntityOrder= BaseUrl_xu +"project.php/Inner/entityOrder";//公益消费订单
    public static String IsFirst= BaseUrl_xu +"project.php/inner/isFirst";//是否第一次支付

    public static String Card_Info= BaseUrl_xu +"project.php/inner/bankcardquery";//获取银行卡信息
    public static String withdraw= BaseUrl_xu +"project.php/inner/extract";//提现
    public static String Attention= BaseUrl_xu +"project.php/inner/attention";//关注和取消
    public static String MyBalance= BaseUrl_xu +"project.php/inner/myBalance";//查看余额

    public static String ReturnUrlExtract= BaseUrl_xu +"project.php/pay/returnUrlExtract";//提现回调
    public static String Search= BaseUrl_xu +"project.php/inner/search";//搜索
    public static String getCont= BaseUrl_xu +"project.php/inner/getCont";//获取协议




    //--------金晨程的接口---------
    public static String BaseUrl_jin="http://zhongchouxm.hzlianhai.com/zc4/zs/index.php";

    //-----连连回调地址---------
    public static String GQ_HuiDiao=BaseUrl_xu+"project.php/pay/norifyUrlStock/terminal/1";




}

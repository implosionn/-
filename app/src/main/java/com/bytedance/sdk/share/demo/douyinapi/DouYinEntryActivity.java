package com.bytedance.sdk.share.demo.douyinapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.aweme.share.Share;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.bytedance.sdk.share.demo.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Utf8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 主要功能：接受授权返回结果的activity
 * <p>
 * <p>
 * 也可通过request.callerLocalEntry = "com.xxx.xxx...activity"; 定义自己的回调类
 */
public class DouYinEntryActivity extends Activity implements IApiEventHandler {
    DouYinOpenApi douYinOpenApi;
    public static String client_token="";//存放client_token
    public static String access_token="";//存放access_token
    public static String open_id="";//用户信息ID
    public static String client_key="awgbg6d164ck9qqr";
    public static String client_secret="1b819dfa303d97629fa9d59976643720";
    public static String directors="";//导演
    public static String actors="";//演员
    public static String discussion_hot="";//讨论热度
    public static String hot = "";//热度
    public static String topic_hot="";//话题热度
    public static String influence_hot="";//影响热度
    public static String search_hot="";//搜索热度
    public static String areas="";//地区
    public static String release_date="";//上映时间;
    public static String poster="";//海报网址;
    public static String name="";//电影名;
    public static String name_en="";//电视剧名/综艺名，英文;
    public static String tags="";//电视标签;
    public static String avatar="";//头像链接
    public static String country="";//国家
    public static String province="";//省
    public static String city="";//城市
    public static String nickname="";//昵称
    public static int gender;//性别，0未知,1男,2女
    public static String gender_id="";//存放性别字符串
    public static String followingcountry="";//关注国家
    public static String followingprovince="";//关注省
    public static String followingcity="";//关注城市
    public static String followinavatar="";//关注头像链接
    public static String followingnickname="";//关注昵称
    public static int followinggender;//关注性别，0未知,1男,2女
    public static String following_gender_id="";//存放关注性别字符串
    public static String Fanscountry="";//粉丝国家
    public static String Fansprovince="";//粉丝省
    public static String Fanscity="";//粉丝城市
    public static String Fansavatar="";//粉丝头像链接
    public static String Fansnickname="";//粉丝昵称
    public static int Fansgender;//粉丝性别，0未知,1男,2女
    public static String Fans_gender_id="";//存放关注性别字符串
    public static String title="";
    public static String create_time="";
    public static String cover="";
    public static String is_top="";
    public static String item_id="";
    public static String[] item_ids={""};
    public static int comment_count=0;
    public static int digg_count=0;
    public static int play_count=0;
    public static int download_count=0;
    public static int share_count=0;
    public static int forward_count=0;
    //public static String //存放个人简介，暂时不知道字段
    //public static String json="{\"data\":{\"active_time\":\"2022-08-13\",\"description\":\"\",\"error_code\":0,\"list\":[{\"actors\":[\"沈腾\",\"马丽\",\"常远\",\"李诚儒\",\"黄才伦\",\"辣目洋子\",\"郝瀚\",\"黄子韬\",\"王成思\",\"高海宝\",\"杨铮\",\"史彭元\",\"张熙然\",\"黄若萌\",\"杨皓宇\"],\"directors\":[\"张吃鱼\"],\"discussion_hot\":3102628,\"hot\":12568015,\"id\":\"6903365126108381703\",\"influence_hot\":945630,\"maoyan_id\":\"1359034\",\"name\":\"独行月球\",\"name_en\":\"Moon Man\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/93204d13cf5a4fb080e74ea6d057eca1?from=552310259\",\"release_date\":\"2022-07-29\",\"search_hot\":1204843,\"topic_hot\":1088454,\"type\":1},{\"actors\":[\"古天乐\",\"刘青云\",\"刘嘉玲\",\"姜皓文\",\"谢君豪\",\"吴倩\",\"万国鹏\",\"张家辉\",\"刘浩良\",\"麦天枢\",\"朱鉴然\",\"程小夏\"],\"areas\":[\"香港\"],\"directors\":[\"吴炫辉\"],\"discussion_hot\":2873014,\"hot\":11641647,\"id\":\"6446787399010746893\",\"influence_hot\":852333,\"maoyan_id\":\"249033\",\"name\":\"明日战记\",\"name_en\":\"Warriors of Future\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/5d9299715de44f1db6645f3466d73173?from=552310259\",\"release_date\":\"2022-08-05\",\"search_hot\":1153738,\"tags\":[\"动作\",\"科幻\"],\"topic_hot\":1014113,\"type\":1},{\"actors\":[\"马思纯\",\"王俊凯\",\"范伟\",\"曾美慧孜\",\"李晓川\",\"赵润南\",\"龚蓓苾\",\"黄璐\",\"莫西子诗\",\"曾慕梅\",\"万茜\",\"刘琳\"],\"areas\":[\"中国大陆\"],\"directors\":[\"李玉\"],\"discussion_hot\":2798334,\"hot\":11359443,\"id\":\"6894133081402245639\",\"influence_hot\":866950,\"maoyan_id\":\"1369917\",\"name\":\"断·桥\",\"name_en\":\"The Fallen Bridge\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/ad80a23419ea4e12bc8520378cd8e88a?from=552310259\",\"release_date\":\"2022-08-13\",\"search_hot\":1228098,\"tags\":[\"剧情\"],\"topic_hot\":870352,\"type\":1},{\"actors\":[\"李汶翰\",\"徐若晗\",\"王博文\",\"高秋梓\",\"柯蓝\",\"苇青\"],\"directors\":[\"落落\"],\"discussion_hot\":2863153,\"hot\":11000782,\"id\":\"7073662696507474446\",\"influence_hot\":878102,\"maoyan_id\":\"1331205\",\"name\":\"遇见你\",\"name_en\":\"Almost Love\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/c2204b96045341ab9c2eaa6c33edeef6?from=552310259\",\"release_date\":\"2022-08-04\",\"search_hot\":1131291,\"topic_hot\":940925,\"type\":1},{\"actors\":[\"王千源\",\"郭晓东\",\"王迅\",\"许龄月\",\"马渝捷\",\"倪大红\",\"关晓彤\",\"张兆辉\",\"张光北\",\"童蕾\",\"郭晓峰\",\"杨洛仟\"],\"areas\":[\"中国大陆\"],\"directors\":[\"郭晓峰\"],\"discussion_hot\":2460264,\"hot\":7922314,\"id\":\"6858859543187849741\",\"influence_hot\":0,\"maoyan_id\":\"1302480\",\"name\":\"猎屠\",\"name_en\":\"Butcher Hunter\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/a5de28bf4370a36f5e8261f007faee35?from=552310259\",\"release_date\":\"2022-06-03\",\"search_hot\":0,\"topic_hot\":788691,\"type\":1},{\"actors\":[\"杨凝\",\"李兰陵\",\"刘知否\",\"瞳音\",\"常文涛\",\"佟心竹\",\"山新\",\"李翰林\",\"图特哈蒙\",\"阎么么\",\"王琳熙\"],\"directors\":[\"王云飞\"],\"discussion_hot\":1707717,\"hot\":7675241,\"id\":\"7118672168472281631\",\"influence_hot\":790904,\"maoyan_id\":\"\",\"name\":\"疯了！桂宝之三星夺宝\",\"name_en\":\"CRAZY.KWAI.BOO Sanxingdui Spirited Away\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/078a28d12cae4e3daa3ab6092fe17c56?from=552310259\",\"release_date\":\"2022-07-29\",\"search_hot\":837874,\"topic_hot\":483435,\"type\":1},{\"actors\":[\"张译\"],\"areas\":[\"中国大陆\"],\"directors\":[\"黄健明\"],\"discussion_hot\":2230680,\"hot\":7375809,\"id\":\"6531973976749507076\",\"influence_hot\":0,\"maoyan_id\":\"346331\",\"name\":\"再见怪兽\",\"name_en\":\"\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/8dc89481d0a2332fd56f4c28a5d05c56?from=552310259\",\"release_date\":\"\",\"search_hot\":0,\"tags\":[\"动画\"],\"topic_hot\":740864,\"type\":1},{\"actors\":[\"倪妮\",\"张鲁一\",\"辛柏青\",\"池松壮亮\",\"中野良子\",\"新音\",\"王佳佳\",\"耐安\",\"毛乐\"],\"directors\":[\"张律\"],\"discussion_hot\":2564503,\"hot\":7271062,\"id\":\"7118672661575959076\",\"influence_hot\":0,\"maoyan_id\":\"\",\"name\":\"漫长的告白\",\"name_en\":\"Yanagawa\",\"poster\":\"https://p9-dy.byteimg.com/obj/compass/15f516da827b4233a5a9b25be3fa09b9?from=552310259\",\"release_date\":\"2022-08-12\",\"search_hot\":0,\"topic_hot\":529417,\"type\":1},{\"actors\":[\"陆双\",\"谢蔚\",\"吉莹\",\"吴海涛\",\"白文显\",\"陈志荣\"],\"directors\":[\"陆锦辉\",\"钟彧\"],\"discussion_hot\":1630072,\"hot\":7117645,\"id\":\"7069703033898140168\",\"influence_hot\":489133,\"maoyan_id\":\"1358119\",\"name\":\"猪猪侠大电影·海洋日记\",\"name_en\":\"GG BOND：Ocean Mission\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/dcef320b1d774b07908a90b7b26a4fdd?from=552310259\",\"release_date\":\"\",\"search_hot\":793204,\"topic_hot\":417320,\"type\":1},{\"actors\":[\"黄渤\",\"荣梓杉\",\"姚晨\",\"范伟\",\"贾冰\",\"许君聪\",\"于洋\",\"梅婷\",\"黄觉\",\"李泽宇\",\"范湉湉\"],\"directors\":[\"陈思诚\"],\"discussion_hot\":2447105,\"hot\":6688404,\"id\":\"6898272571448689159\",\"influence_hot\":853589,\"maoyan_id\":\"1357145\",\"name\":\"外太空的莫扎特\",\"name_en\":\"Mozart from Space\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/2333bc223d5a4cc591093f4e893bbf6f?from=552310259\",\"release_date\":\"2022-07-15\",\"search_hot\":948463,\"topic_hot\":776105,\"type\":1},{\"actors\":[\"周依然\",\"吴念轩\",\"汤加文\",\"翁楚汉\",\"吴彦姝\",\"张歆艺\",\"袁弘\",\"谢治勋\",\"耿军\",\"詹妮\"],\"directors\":[\"吴家凯\",\"张智鸿\"],\"discussion_hot\":2201424,\"hot\":6399257,\"id\":\"7074839902659183111\",\"influence_hot\":785060,\"maoyan_id\":\"1331600\",\"name\":\"一直一直都很喜欢你\",\"name_en\":\"Love Can't Be Said\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/d1c81cff25824f84a921fd9300c5ca97?from=552310259\",\"release_date\":\"2022-07-22\",\"search_hot\":898121,\"topic_hot\":665638,\"type\":1},{\"actors\":[\"刘红韵\",\"邓玉婷\",\"严彦子\",\"祖晴\",\"高全胜\",\"苗浩生\",\"李团\",\"申克\",\"胡严彦\",\"李薇薇\"],\"areas\":[\"中国大陆\"],\"directors\":[\"黄伟明\"],\"discussion_hot\":2167551,\"hot\":6390396,\"id\":\"6803949067538792968\",\"influence_hot\":949177,\"maoyan_id\":\"1297952\",\"name\":\"开心超人之英雄的心\",\"name_en\":\"The Stones\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/2c9639d6d5a74b8baa9c888096ffbd43?from=552310259\",\"release_date\":\"2022-07-22\",\"search_hot\":854528,\"tags\":[\"动画\"],\"topic_hot\":534875,\"type\":1},{\"actors\":[\"李孝谦\",\"漆昱辰\",\"林俊毅\",\"修雨秀\",\"许童心\",\"丁冠森\",\"李光洁\",\"周深\",\"沈月\",\"张奕聪\",\"高至霆\",\"孙浩\",\"姜卓君\",\"张熙然\",\"夏子轩\"],\"directors\":[\"王梓骏\"],\"discussion_hot\":1719204,\"hot\":6382947,\"id\":\"7077392575660818980\",\"influence_hot\":0,\"maoyan_id\":\"1310222\",\"name\":\"我们的样子像极了爱情\",\"name_en\":\"Close to Love\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/ec798d50b0bd4053a8436b26d31f0aaa?from=552310259\",\"release_date\":\"2022-08-04\",\"search_hot\":0,\"topic_hot\":577653,\"type\":1},{\"actors\":[\"刘青云\",\"蔡卓妍\",\"林峯\",\"谭凯\",\"陈家乐\",\"汤怡\",\"何珮瑜\",\"李若彤\",\"吴浩康\",\"洪天明\",\"车婉婉\",\"斌子\",\"李菁\",\"马志威\",\"杨天宇\",\"胡子彤\",\"朱鉴然\",\"马睿瀚\"],\"areas\":[\"香港\"],\"directors\":[\"韦家辉\"],\"discussion_hot\":2407422,\"hot\":6193746,\"id\":\"6531972731066384900\",\"influence_hot\":912887,\"maoyan_id\":\"1203439\",\"name\":\"神探大战\",\"name_en\":\"Cold Detective\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/73a0215e0c2d41efb338aa248e6d2978?from=552310259\",\"release_date\":\"2022-07-08\",\"search_hot\":985118,\"tags\":[\"动作\",\"悬疑\",\"犯罪\"],\"topic_hot\":825541,\"type\":1},{\"actors\":[\"周迅\",\"郑秀文\",\"易烊千玺\",\"许娣\",\"冯德伦\",\"黄米依\",\"鲍起静\",\"白客\",\"苏小明\",\"巴图\",\"朱雅芬\",\"方平\",\"马昕墨\"],\"areas\":[\"中国大陆\"],\"directors\":[\"张艾嘉\",\"李少红\",\"陈冲\"],\"discussion_hot\":2237710,\"hot\":6124302,\"id\":\"6920024822365815309\",\"influence_hot\":692343,\"maoyan_id\":\"1359285\",\"name\":\"世间有她\",\"name_en\":\"HerStory\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/bb9e052600d647b485289db9be540cfd?from=552310259\",\"release_date\":\"2022-09-09\",\"search_hot\":866052,\"topic_hot\":784763,\"type\":1},{\"discussion_hot\":1778380,\"hot\":6005286,\"id\":\"7069703142962627080\",\"influence_hot\":0,\"maoyan_id\":\"1439146\",\"name\":\"迷你世界之觉醒\",\"name_en\":\"KAKA\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/d0b5a94a41aa4f8088a63577d1ffbb8e?from=552310259\",\"release_date\":\"\",\"search_hot\":0,\"topic_hot\":208603,\"type\":1},{\"actors\":[\"许烈英\",\"王思蓉\",\"刘子涵\"],\"directors\":[\"陶涛\",\"张琪\",\"秦博\",\"范士广\"],\"discussion_hot\":2469850,\"hot\":5985304,\"id\":\"7039325286676038174\",\"influence_hot\":747376,\"maoyan_id\":\"1429716\",\"name\":\"人间世\",\"name_en\":\"Once Upon a Life\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/7930e8b762a44382bc96621a06398952?from=552310259\",\"release_date\":\"\",\"search_hot\":0,\"topic_hot\":247404,\"type\":1},{\"actors\":[\"武仁林\",\"海清\",\"杨光锐\",\"武赟志\",\"李生甫\",\"张敏\",\"赵登平\",\"王彩兰\",\"曾建贵\",\"马占红\",\"王翠兰\",\"续彩霞\"],\"directors\":[\"李睿珺\"],\"discussion_hot\":2635360,\"hot\":5787545,\"id\":\"7065249891530113543\",\"influence_hot\":729374,\"maoyan_id\":\"1336601\",\"name\":\"隐入尘烟\",\"name_en\":\"Return to Dust\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/89b1dda86dfd43ecac9e2e1c22f1a9dd?from=552310259\",\"release_date\":\"2022-07-08\",\"search_hot\":1083878,\"tags\":[\"剧情\",\"爱情\"],\"topic_hot\":710975,\"type\":1},{\"actors\":[\"王千源\",\"谭卓\",\"黄杨钿甜\",\"艾米\",\"周铁\",\"安得路\",\"王大陆\",\"巩金国\"],\"directors\":[\"黄岳泰\"],\"discussion_hot\":2122311,\"hot\":5595268,\"id\":\"7092236761799066143\",\"influence_hot\":653867,\"maoyan_id\":\"1450064\",\"name\":\"世界上最爱我的人\",\"name_en\":\"Beating Heart\",\"poster\":\"https://p9-dy.byteimg.com/obj/compass/276096b70a374490b5fb15b962e44b50?from=552310259\",\"release_date\":\"2022-08-26\",\"search_hot\":767744,\"topic_hot\":625566,\"type\":1},{\"actors\":[\"锦鲤\",\"李岱昆\",\"张磊\",\"阎萌萌\",\"杨凝\",\"晨宁溪\",\"张遥函\",\"齐斯伽\",\"高枫\",\"赵明洲\",\"敖磊\",\"郭政建\"],\"areas\":[\"中国大陆\"],\"directors\":[\"胡一泊\"],\"discussion_hot\":1968256,\"hot\":5531616,\"id\":\"6531988673146126851\",\"influence_hot\":848812,\"maoyan_id\":\"1212472\",\"name\":\"冲出地球\",\"name_en\":\"Rainbow Sea Fly High\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/dcfe0db1f97b47ecb801e1858dd93798?from=552310259\",\"release_date\":\"2022-07-16\",\"search_hot\":889477,\"tags\":[\"科幻\",\"动画\",\"冒险\"],\"topic_hot\":599197,\"type\":1},{\"actors\":[\"普加·巴哈卢卡\",\"Parth Suri\",\"石天龙\",\"母其弥雅\",\"何椰子\",\"Abhimanyu Sharma\",\"拉兹巴·亚达夫\",\"Ravi Kale\",\"Prateek Parma\"],\"directors\":[\"拉姆·戈帕尔·维马\",\"刘静\"],\"discussion_hot\":1552542,\"hot\":4655790,\"id\":\"7034344828066333215\",\"influence_hot\":660444,\"maoyan_id\":\"1437427\",\"name\":\"龙女孩\",\"name_en\":\"Enter the Girl Dragon\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/22886838135e473183c4a37a33a8105f?from=552310259\",\"release_date\":\"2022-07-15\",\"search_hot\":675940,\"topic_hot\":323103,\"type\":1},{\"actors\":[\"朱一龙\",\"杨恩又\",\"王戈\",\"刘陆\",\"罗京民\",\"吴倩\",\"郑卫莉\",\"陈创\",\"李春嫒\",\"钟宇升\",\"刘亚津\",\"刘浩\",\"韩延\"],\"directors\":[\"刘江江\"],\"discussion_hot\":2454453,\"hot\":4635384,\"id\":\"6966889198838252045\",\"influence_hot\":975106,\"maoyan_id\":\"1397016\",\"name\":\"人生大事\",\"name_en\":\"Lighting Up the Stars\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/e58231caf9eb4e88ab5e5575e493b667?from=552310259\",\"release_date\":\"2022-06-24\",\"search_hot\":1026509,\"topic_hot\":882134,\"type\":1},{\"discussion_hot\":1285798,\"hot\":4595213,\"id\":\"7108554671324660237\",\"influence_hot\":0,\"maoyan_id\":\"\",\"name\":\"迷失之城 The Lost City\",\"name_en\":\"The Lost City\",\"poster\":\"\",\"release_date\":\"\",\"search_hot\":0,\"topic_hot\":232614,\"type\":1},{\"actors\":[\"马赛\",\"任达华\",\"吴镇宇\",\"洪天明\",\"余香凝\",\"吴澋滔\",\"元华\",\"林恺铃\",\"伍咏诗\",\"胡子彤\",\"徐浩昌\",\"龚慈恩\",\"张达明\",\"张锦程\",\"林雪\",\"刘国昌\"],\"areas\":[\"香港\",\"中国大陆\",\"中国香港\"],\"directors\":[\"杜琪峰\",\"徐克\",\"许鞍华\",\"林岭东\",\"谭家明\",\"洪金宝\",\"袁和平\"],\"discussion_hot\":1310585,\"hot\":4590748,\"id\":\"6446319343134310926\",\"influence_hot\":0,\"maoyan_id\":\"248182\",\"name\":\"七人乐队\",\"name_en\":\"Eight And A Half\",\"poster\":\"https://p3-dy.byteimg.com/obj/compass/b1437fe277312c5e18bdf69fe74f2f17?from=552310259\",\"release_date\":\"2022-07-29\",\"search_hot\":0,\"tags\":[\"剧情\",\"历史\"],\"topic_hot\":200732,\"type\":1},{\"actors\":[\"泰莉莎·加拉赫\",\"西蒙·格林诺\",\"迈克尔·墨菲\",\"保罗·潘廷\",\"韦恩·格雷森\",\"罗布·拉克斯特拉夫\",\"Helen Walsh\",\"基思·威克姆\",\"安德烈斯·威廉姆斯\",\"乔·怀亚特\",\"珍妮·约科波里\"],\"areas\":[\"英国\"],\"directors\":[\"布莱尔·西蒙斯\"],\"discussion_hot\":1544434,\"hot\":4494982,\"id\":\"7094203739543863816\",\"influence_hot\":643917,\"maoyan_id\":\"\",\"name\":\"海底小纵队：洞穴大冒险\",\"name_en\":\"Octonauts and the Caves of Sac Actun\",\"poster\":\"https://p9-dy.byteimg.com/obj/compass/04476718610b4bfa9db229ee86064a7d?from=552310259\",\"release_date\":\"2022-07-09\",\"search_hot\":754872,\"tags\":[\"喜剧\",\"动画\",\"冒险\"],\"topic_hot\":407721,\"type\":1},{\"actors\":[\"陈永胜\",\"章宇\",\"曹操\",\"柯国庆\",\"刘奕铁\",\"黄炎\",\"赵琥成\",\"王梓屹\",\"陈铭杨\",\"王乃训\",\"李汶聪\",\"程泓鑫\",\"张译\",\"林博洋\",\"钱焜\",\"AJ Donnelly\",\"柯南·何裴\",\"凯文·李\",\"勃小龙\"],\"directors\":[\"张艺谋\",\"张末\"],\"discussion_hot\":1196186,\"hot\":3875271,\"id\":\"6951008600240325157\",\"influence_hot\":870128,\"maoyan_id\":\"1367251\",\"name\":\"狙击手\",\"name_en\":\"Snipers\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/04a7512d8f104c01ace497b2c8ba3289?from=552310259\",\"release_date\":\"2022-02-01\",\"search_hot\":1018498,\"tags\":[\"剧情\",\"战争\",\"历史\"],\"topic_hot\":600477,\"type\":1},{\"actors\":[\"吴京\",\"易烊千玺\",\"段奕宏\",\"张涵予\",\"朱亚文\",\"李晨\",\"韩东君\",\"耿乐\",\"杜淳\",\"胡军\"],\"directors\":[\"陈凯歌\",\"徐克\",\"林超贤\"],\"discussion_hot\":1310355,\"hot\":3607013,\"id\":\"7024141505304625700\",\"influence_hot\":1025784,\"maoyan_id\":\"1446115\",\"name\":\"长津湖之水门桥\",\"name_en\":\"The Battle at Lake Changjin II\",\"poster\":\"https://p9-dy.byteimg.com/obj/compass/800a70903eeb44fa9548237ae201d3fb?from=552310259\",\"release_date\":\"2022-02-01\",\"search_hot\":886840,\"tags\":[\"剧情\",\"历史\",\"战争\"],\"topic_hot\":687030,\"type\":1},{\"actors\":[\"吴京\",\"易烊千玺\",\"段奕宏\",\"张涵予\",\"朱亚文\",\"李晨\",\"韩东君\",\"胡军\",\"黄轩\",\"欧豪\",\"史彭元\",\"李岷城\",\"唐国强\",\"杨一威\",\"周小斌\",\"林永健\",\"王宁\",\"刘劲\",\"卢奇\",\"王伍福\",\"耿乐\",\"曹阳\",\"李军\",\"王同辉\",\"艾米\",\"石昊正\",\"许明虎\",\"卢奇\",\"曹阳\",\"李军\",\"郑恺\",\"张国立\"],\"areas\":[\"中国大陆\"],\"directors\":[\"陈凯歌\",\"徐克\",\"林超贤\",\"冯小刚\"],\"discussion_hot\":1407280,\"hot\":3518261,\"id\":\"6455308856976212493\",\"influence_hot\":1025784,\"maoyan_id\":\"257706\",\"name\":\"长津湖\",\"name_en\":\"The Battle at Lake Changjin\",\"poster\":\"https://p9-dy.byteimg.com/obj/compass/44e3a92362504154980c603a776c2e6e?from=552310259\",\"release_date\":\"2021-09-30\",\"search_hot\":1009167,\"tags\":[\"剧情\",\"历史\",\"战争\"],\"topic_hot\":822801,\"type\":1},{\"actors\":[\"易烊千玺\",\"田雨\",\"陈哈琳\",\"齐溪\",\"公磊\",\"许君聪\",\"王宁\",\"黄尧\",\"巩金国\",\"田壮壮\",\"王传君\",\"徐峥\",\"章宇\",\"张志坚\",\"咏梅\",\"杨新鸣\",\"朱俊麟\",\"贾弘逍\",\"陈翊曈\",\"陈哈琳\",\"岳小军\",\"朱俊麟\",\"王丽涵\",\"贾弘逍\",\"韩笑\",\"孙征宇\",\"黄艺馨\",\"修梦迪\",\"苏子航\",\"郑伊倩\",\"丁文博\",\"王一博\",\"白宇\",\"王圣迪\"],\"areas\":[\"中国大陆\"],\"directors\":[\"文牧野\"],\"discussion_hot\":1147050,\"hot\":3386973,\"id\":\"6947574949951308318\",\"influence_hot\":931466,\"maoyan_id\":\"1383307\",\"name\":\"奇迹·笨小孩\",\"name_en\":\"Nice View\",\"poster\":\"https://p9-dy.byteimg.com/obj/compass/a25a554fba8742938b14f430c1428929?from=552310259\",\"release_date\":\"2022-02-01\",\"search_hot\":864471,\"tags\":[\"剧情\"],\"topic_hot\":624347,\"type\":1},{\"actors\":[\"张译\",\"吴京\",\"李九霄\",\"魏晨\",\"Vladimir Ershov\",\"邱天\",\"周思羽\",\"邓超\",\"欧豪\",\"张承\",\"刘显达\",\"杨轶\",\"齐超\",\"朱梓瑜\",\"刘雨辰\",\"万沛鑫\",\"石昊正\"],\"directors\":[\"管虎\",\"郭帆\",\"路阳\"],\"discussion_hot\":1317801,\"hot\":2965074,\"id\":\"6882965933837517326\",\"influence_hot\":781849,\"maoyan_id\":\"1339160\",\"name\":\"金刚川\",\"name_en\":\"The Sacrifice\",\"poster\":\"https://p1-dy.byteimg.com/obj/compass/4c973ee2521487e9e9895cdb87b8824c?from=552310259\",\"release_date\":\"2020-10-23\",\"search_hot\":835499,\"tags\":[\"剧情\",\"战争\"],\"topic_hot\":652225,\"type\":1}]},\"extra\":{\"logid\":\"2022081400083901020816217300816D08\",\"now\":1660406919984}}\n";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(@NonNull BaseResp resp) {
        if (resp.getType() == CommonConstants.ModeType.SHARE_CONTENT_TO_TT_RESP) {
            Share.Response response = (Share.Response) resp;
            Toast.makeText(this, " code：" + response.errorCode + " 文案：" + response.errorMsg, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (resp.getType() == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            Authorization.Response response = (Authorization.Response) resp;
            Intent intent = null;
            if (resp.isSuccess()) {

                /*Toast.makeText(this, "授权成功，获得权限：" + response.grantedPermissions+response.authCode,
                        Toast.LENGTH_LONG).show();*/
                client_tokenGet();//获取client_token
                access_token_open_idGet(response.authCode);//把获取到的authcode作为参数去获取access_token
            }
        }
        finish();
    }

    @Override
    public void onErrorIntent(Intent intent) {
        // 错误数据
        Toast.makeText(this, "Intent出错", Toast.LENGTH_LONG).show();
        finish();
    }
    public void client_tokenGet() {
        String path = "https://open.douyin.com/oauth/client_token/?client_secret="+client_secret+"&grant_type=client_credential&client_key="+client_key;
        //上面这行是GET方法获取client_token的接口
        System.out.println(path);//调试用，查看上面网址是否正确
        OkHttpClient client = new OkHttpClient();//OK HTTP方法实现GET
        Request request = new Request.Builder().url(path).get().build();
        client.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                String message=e.getLocalizedMessage();
                Log.d("MainActivity", "连接失败" + e.getLocalizedMessage());
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("MainActivity", result);//把接口传回来的结果保存到运行日志
                //System.out.println("\n\nLoginGet结果是："+result+"\n\n\n");
                GetClient_tokenMessage(result);//client_token提取函数
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }//登录授权，获取client_token
    public void access_token_open_idGet(String scode){
        String url="https://open.douyin.com/oauth/access_token/";
        OkHttpClient client=new OkHttpClient();
        //构建表单参数
        FormBody.Builder requestBuild=new FormBody.Builder();
        //添加请求体
        RequestBody requestBody=requestBuild
                .add("client_secret",client_secret)
                .add("code",scode)
                .add("grant_type","authorization_code")
                .add("client_key",client_key)
                .build();
        Request request = new Request.Builder().url("https://open.douyin.com/oauth/access_token/").
                addHeader("Content-Type","application/x-www-form-urlencoded").post(requestBody).build();
        System.out.println(request.toString());
        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivityPost","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("POST access_token请求：",result);
                Getaccess_token_open_idMessage(result);//access_token提取函数
                if (response.body()!=null){
                    response.body().close();
                }
            }

        });
    }//登录授权，POST获取access_token、open_id*/
    public static void GetClient_tokenMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            Saveclient_token(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取返回数据并送去解析
    public static void GetFollowingMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            Savefollowing(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取返回数据并送去解析
    public static void Getaccess_token_open_idMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            Saveaccess_token_open_id(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取返回数据并送去解析
    public static void Saveclient_token(String s) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {// 获取access_token
            String token=jsonObj.getString("access_token");//查询client_token关键字
            //String refresh_token=jsonObj.getString("refresh_token");
            client_token=token;
            System.out.println("client_access_token = " + client_token);//把client_token关键字对应的键值显示到日志
            //itemGet("1","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//client_token提取，获取item
    public static void Saveaccess_token_open_id(String s) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {// 获取access_token
            String token=jsonObj.getString("access_token");//查询access_token关键字
            String id=jsonObj.getString("open_id");//查询access_token关键字
            access_token=token;
            open_id=id;
            PersonGet();
            System.out.println("access_token = " + access_token +"\n"+ "open_id = "+open_id);//把access_token关键字对应的键值显示到日志
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//access_token、open_id提取
    public static void itemGet(int Type,int Version) {//榜单获取
        String path = "https://open.douyin.com/discovery/ent/rank/item/?access_token="+client_token+"&type="+Type+"&version="+Version;
        System.out.println(path);//调试用，查看上面网址是否正确
        OkHttpClient client = new OkHttpClient();//OK HTTP方法实现GET
        Request request = new Request.Builder().url(path).get().build();
        client.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                String message=e.getLocalizedMessage();
                Log.d("MainActivity", "连接失败" + e.getLocalizedMessage());
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("MainActivity", result);//把接口传回来的结果保存到运行日志
                //System.out.println("\n\nitemGet结果是："+result+"\n\n\n");
                GetitemMessage(result,Type,Version);
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }//获取item网络请求
    public static void PersonGet(){
        String url="https://open.douyin.com/oauth/userinfo/";
        OkHttpClient client=new OkHttpClient();
        //构建表单参数
        FormBody.Builder requestBuild=new FormBody.Builder();
        //添加请求体
        RequestBody requestBody=requestBuild
                .add("access_token",access_token)
                .add("open_id",open_id)
                .build();
        Request request = new Request.Builder().url("https://open.douyin.com/oauth/userinfo/")
                .addHeader("Content-Type","application/json")
                .addHeader("access-token",access_token)
                .post(requestBody)
                .build();
        System.out.println(request.toString());
        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivityPost","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("user_info请求：",result);
                GetPersonMessage(result);
                if (response.body()!=null){
                    response.body().close();
                }
            }
        });
    }
    public static void Person(String s) {//个人信息
        JSONObject jsonObj = null;
        JSONArray jsonArray=null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            avatar=jsonObj.getString("avatar");
        } catch (JSONException e) {
            avatar="";
        }
        try {
            country=jsonObj.getString("country");
        }catch (JSONException e) {
            country="";
        }
        try {
            province=jsonObj.getString("province");
        }catch (JSONException e) {
            province="";
        }
        try {
            city=jsonObj.getString("city");
        }catch (JSONException e) {
            city="";
        }
        try {
            nickname=jsonObj.getString("nickname");
        }catch (JSONException e) {
            nickname="";
        }
        try {
            gender=jsonObj.getInt("gender");
        }catch (JSONException e) {
            gender=0;
        }
        System.out.println("个人信息如下：avatar=" + avatar + "\ncountry=" + country+"\nprovince=" + province+"\ncity=" + city+"\nnickname=" + nickname+"\ngender=" + gender);
    }
    public static void GetPersonMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        JSONArray jsonArray=null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            Person(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取详细item数据
    public static void FollowingGet() {//关注名单获取
        String path = "https://open.douyin.com/following/list/?count="+6+"&open_id="+open_id+"&cursor="+0;
        System.out.println(path);//调试用，查看上面网址是否正确
        OkHttpClient client = new OkHttpClient();//OK HTTP方法实现GET
        Request request = new Request.Builder()
                .addHeader("access-token",access_token)
                .url(path)
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                String message=e.getLocalizedMessage();
                Log.d("关注获取", "连接失败" + e.getLocalizedMessage());
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("关注获取:", result);//把接口传回来的结果保存到运行日志
                if (response.body() != null) {
                    response.body().close();
                    GetFollowingMessage(result);
                }
            }
        });
    }
    public static void FansGet() {//关注名单获取
        String path = "https://open.douyin.com/fans/list/?count="+6+"&open_id="+open_id+"&cursor="+0;
        System.out.println(path);//调试用，查看上面网址是否正确
        OkHttpClient client = new OkHttpClient();//OK HTTP方法实现GET
        Request request = new Request.Builder()
                .addHeader("access-token",access_token)
                .url(path)
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                String message=e.getLocalizedMessage();
                Log.d("粉丝获取", "连接失败" + e.getLocalizedMessage());
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("粉丝获取获取:", result);//把接口传回来的结果保存到运行日志
                if (response.body() != null) {
                    response.body().close();
                    GetFansMessage(result);
                }
            }
        });
    }
    public static void GetFansMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            SaveFans(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取返回数据并送去解析
    public static void SaveFans(String s) {//movie item
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                try {
                    Fansnickname = jsonObject.getString("nickname");
                } catch (Exception e) {
                    Fansnickname = "";
                }
                try {
                    Fansavatar = jsonObject.getString("avatar");
                } catch (Exception e) {
                    Fansavatar = "";
                }
                try {
                    Fanscountry = jsonObject.getString("country");
                } catch (Exception e) {
                    Fanscountry = "";
                }
                try {
                    Fansprovince = jsonObject.getString("province");
                } catch (Exception e) {
                    Fansprovince = "";
                }
                try {
                    Fanscity = jsonObject.getString("city");
                } catch (Exception e) {
                    Fanscity = "";
                }
                try {
                    Fansgender = jsonObject.getInt("gender");//影响力热度
                    if(Fansgender==1) {
                        Fans_gender_id = "男";
                    }
                    else if(Fansgender==2)
                    {
                        Fans_gender_id="女";
                    }
                    else if(Fansgender==0)
                    {
                        Fans_gender_id="未知";
                    }
                } catch (Exception e) {
                    Fansgender = 0;
                }
                Fansavatar=compare(Fansavatar);
                System.out.println("\n名字:"+Fansnickname+" \n国家："+Fanscountry+" \n省："+Fansprovince+" \n市:"+Fanscity+" \n头像链接："+Fansavatar+" \n性别："+Fans_gender_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void VideoGet() {//关注名单获取
        String path = "https://open.douyin.com/video/list/?count="+10+"&open_id="+open_id+"&cursor="+0;
        System.out.println(path);//调试用，查看上面网址是否正确
        OkHttpClient client = new OkHttpClient();//OK HTTP方法实现GET
        Request request = new Request.Builder()
                .addHeader("access-token",access_token)
                .url(path)
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                String message=e.getLocalizedMessage();
                Log.d("视频获取", "连接失败" + e.getLocalizedMessage());
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("视频获取:", result);//把接口传回来的结果保存到运行日志
                if (response.body() != null) {
                    response.body().close();
                    GetVideoMessage(result);
                }
            }
        });
    }
    public static void GetVideoMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            SaveVideo(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取返回数据并送去解析
    public static void SaveVideo(String s) {//movie item
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                int j=0;
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                try {
                    title = jsonObject.getString("title");
                } catch (Exception e) {
                    title = "";
                }
                try {
                    create_time = jsonObject.getString("create_time");
                } catch (Exception e) {
                    create_time = "";
                }
                try {
                    cover = jsonObject.getString("cover");
                } catch (Exception e) {
                    cover = "";
                }
                try {
                    is_top = jsonObject.getString("is_top");
                } catch (Exception e) {
                    is_top = "";
                }
                try {
                    item_id = jsonObject.getString("item_id");
                    item_ids[j]=item_id;
                    j++;
                } catch (Exception e) {
                    item_id = "";
                }
                try {
                    play_count = jsonObject.getInt("play_count");
                } catch (Exception e) {
                    play_count = 0;
                }
                try {
                    comment_count = jsonObject.getInt("comment_count");
                } catch (Exception e) {
                    comment_count = 0;
                }
                cover=compare(cover);
                System.out.println("\n视频标题:"+title+"\n时间:"+create_time+"\n封面:"+cover+"\n置顶:"+is_top+"\n评论:"+comment_count+"\n播放:"+play_count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void VideoAllGet(){
        OkHttpClient client=new OkHttpClient();
        String itemids="{@9VwD1PTEWJhma3SqOcksUs791GbrOvqKPpVxoQ+iJ1IVavn/60zdRmYqig357zEBWXXQS2MTrHSmK+sA8mvF5A==}";
        FormBody.Builder requestBuild=new FormBody.Builder();
        //添加请求体
        /*HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("\"item_ids",itemids);*/
        RequestBody requestBody=requestBuild
                .add("item_ids",itemids)
                .build();
        Request request = new Request.Builder()
                .url("https://open.douyin.com/video/data/?open_id="+open_id)
                .addHeader("access-token",access_token)
                .addHeader("Content-Type","application/json")
                //.addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        System.out.println(request.toString());
        System.out.println("\nitem_id:"+item_id);
        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("视频详细信息","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("视频详细信息请求：",result);
                GetVideoAllMessage(result);
                if (response.body()!=null){
                    response.body().close();
                }
            }

        });
    }
    public static void GetVideoAllMessage(String s) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            SaveVideoAll(data);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取返回数据并送去解析
    public static void SaveVideoAll(String s) {//movie item
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                try {
                    share_count = jsonObject.getInt("share_count");
                } catch (Exception e) {
                    share_count = 0;
                }
                try {
                    download_count = jsonObject.getInt("download_count");
                } catch (Exception e) {
                    download_count = 0;
                }
                try {
                    comment_count = jsonObject.getInt("comment_count");
                } catch (Exception e) {
                    comment_count = 0;
                }
                try {
                    play_count = jsonObject.getInt("play_count");
                } catch (Exception e) {
                    play_count = 0;
                }
                try {
                    digg_count = jsonObject.getInt("digg_count");
                } catch (Exception e) {
                    digg_count = 0;
                }
                try {
                    forward_count = jsonObject.getInt("forward_count");
                } catch (Exception e) {
                    forward_count = 0;
                }
                System.out.println("\n视频标题:"+title+"\n时间:"+create_time+"\n封面:"+cover+"\n置顶:"+is_top+"\n评论:"+comment_count+"\n点赞:"+digg_count+"\n转发:"+forward_count+"\n分享:"+share_count+"\n下载:"+download_count+"\n播放:"+play_count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void Savefollowing(String s) {//movie item
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    try {
                        followingnickname = jsonObject.getString("nickname");
                    } catch (Exception e) {
                        followingnickname = "";
                    }
                    try {
                        followinavatar = jsonObject.getString("avatar");
                    } catch (Exception e) {
                        followinavatar = "";
                    }
                    try {
                        followingcountry = jsonObject.getString("country");
                    } catch (Exception e) {
                        followingcountry = "";
                    }
                    try {
                        followingprovince = jsonObject.getString("province");
                    } catch (Exception e) {
                        followingprovince = "";
                    }
                    try {
                        followingcity = jsonObject.getString("city");
                    } catch (Exception e) {
                        followingcity = "";
                    }
                    try {
                        followinggender = jsonObject.getInt("gender");//影响力热度
                        if(followinggender==1) {
                            following_gender_id = "男";
                        }
                        else if(followinggender==2)
                        {
                            following_gender_id="女";
                        }
                        else if(followinggender==0)
                        {
                            following_gender_id="未知";
                        }
                    } catch (Exception e) {
                        followinggender = 0;
                    }
                    followinavatar=compare(followinavatar);
                    System.out.println("\n名字:"+followingnickname+" \n国家："+followingcountry+" \n省："+followingprovince+" \n市:"+followingcity+" \n头像链接："+followinavatar+" \n性别："+following_gender_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void Saveitem(String s,int type,int version) {//movie item
        try {
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if(type==1)
                {
                    try {
                        name = jsonObject.getString("name");//电影名
                    } catch (Exception e) {
                        name = "";//无名字
                    }
                    try {
                        directors = jsonObject.getString("directors");//导演
                    } catch (Exception e) {
                        directors = "";//无导演
                    }
                    try {
                        actors = jsonObject.getString("actors");//演员，名单需处理
                    } catch (Exception e) {
                        actors = "";
                    }
                    try {
                        discussion_hot = jsonObject.getString("discussion_hot");//讨论热度
                    } catch (Exception e) {
                        discussion_hot = "";
                    }
                    try {
                        influence_hot = jsonObject.getString("influence_hot");//影响力热度
                    } catch (Exception e) {
                        influence_hot = "";
                    }
                    try {
                        search_hot = jsonObject.getString("search_hot");//搜索热度
                    } catch (Exception e) {
                        search_hot = "";
                    }
                    try {
                        hot = jsonObject.getString("hot");//热度
                    } catch (Exception e) {
                        hot = "";
                    }
                    try {
                        topic_hot = jsonObject.getString("topic_hot");//话题热度
                    } catch (Exception e) {
                        topic_hot = "";
                    }
                    try {
                        areas = jsonObject.getString("areas");//地区
                    } catch (Exception e) {
                        areas="";
                    }
                    try {
                        release_date = jsonObject.getString("release_date");//上映时间
                    } catch (Exception e) {
                        release_date = "";//上映时间;
                    }
                    try {
                        poster = jsonObject.getString("poster");//海报地址，需处理
                    } catch (Exception e) {
                        poster = "";//上映时间;
                    }
                    poster=compare(poster);
                    directors=compare(directors);
                    actors=compare(actors);
                    System.out.println("名字:"+name+" 导演："+directors+" 演员："+actors+" 时间:"+release_date+" 讨论热度："+discussion_hot+" 热度："+hot+" 话题热度："+topic_hot+"海报:"+poster);
                }
                else if(type==2)
                {
                    try {
                        name = jsonObject.getString("name");//电影名
                    } catch (Exception e) {
                        name = "";//无名字
                    }
                    try {
                        tags = jsonObject.getString("tags");//标签
                    }catch (Exception e) {
                        tags ="";
                    }
                    try {
                        name_en = jsonObject.getString("name_en");//英文名
                    }catch (Exception e) {
                        name_en ="";
                    }
                    try {
                        topic_hot = jsonObject.getString("topic_hot");//话题热度
                    }catch (Exception e) {
                        topic_hot ="";
                    }
                    try {
                        areas = jsonObject.getString("areas");//地区
                    }catch (Exception e) {
                        areas ="";
                    }
                    try {
                        release_date = jsonObject.getString("release_date");//上映时间
                    }catch (Exception e) {
                        release_date ="";
                    }
                    try {
                        poster = jsonObject.getString("poster");//海报地址，需处理
                    }catch (Exception e) {
                        poster ="";
                    }
                    poster=compare(poster);
                    directors=compare(directors);
                    actors=compare(actors);
                    System.out.println("名字:"+name+" 导演："+directors+" 演员："+actors+" 时间:"+release_date+" 讨论热度："+discussion_hot+" 热度："+hot+" 话题热度："+topic_hot+"海报:"+poster);
                }
                else if(type==3)
                {
                    try {
                        name = jsonObject.getString("name");//名
                    } catch (Exception e) {
                        name = "";//上映时间;
                    }
                    try {
                        directors = jsonObject.getString("directors");//导演
                    } catch (Exception e) {
                        directors = "";//上映时间;
                    }
                    try {
                        discussion_hot = jsonObject.getString("discussion_hot");//讨论热度
                    } catch (Exception e) {
                        discussion_hot = "";//上映时间;
                    }
                    try {
                        influence_hot = jsonObject.getString("influence_hot");//影响力热度
                    } catch (Exception e) {
                        influence_hot = "";//上映时间;
                    }
                    try {
                        search_hot = jsonObject.getString("search_hot");//搜索热度
                    } catch (Exception e) {
                        search_hot = "";//上映时间;
                    }
                    try {
                        hot = jsonObject.getString("hot");//热度
                    } catch (Exception e) {
                        hot = "";//上映时间;
                    }
                    try {
                        name_en = jsonObject.getString("name_en");//英文名
                    } catch (Exception e) {
                        name_en = "";//上映时间;
                    }
                    try {
                        topic_hot = jsonObject.getString("topic_hot");//话题热度
                    } catch (Exception e) {
                        topic_hot = "";//上映时间;
                    }
                    try {
                        release_date = jsonObject.getString("release_date");//上映时间
                    } catch (Exception e) {
                        release_date = "";//上映时间;
                    }
                    try {
                        poster = jsonObject.getString("poster");//海报地址，需处理
                    } catch (Exception e) {
                        poster = "";//上映时间;
                    }
                    poster=compare(poster);
                    directors=compare(directors);
                    System.out.println("名字:"+name+" 导演："+directors+" 时间:"+release_date+" 讨论热度："+discussion_hot+" 热度："+hot+" 话题热度："+topic_hot+"海报链接"+poster);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//item数据分离
    public static void GetitemMessage(String s,int type,int version) {//其大体思路是，新建一个JSON对象，然后把得到的JSON字符串赋值给这个JSON对象，通过getstring查询匹配的关键字然后输出这个关键字对应的数据提取
        JSONObject jsonObj = null;
        JSONArray jsonArray=null;
        try {
            jsonObj = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {//解析上一级数据
            String data=jsonObj.getString("data");//查询data关键字
            //System.out.println("data = " + data);//把data关键字对应的键值显示到日志
            Saveitem(data,type,version);//进入下一级解析
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//获取详细item数据
    public static String compare(@NonNull String input)//字符串处理函数
    {
        String delete="";
        for (int i=0;i<input.length();i++)
        {
            if (input.charAt(i)!=34&&input.charAt(i)!=91&&input.charAt(i)!=93)
            {
                if(input.charAt(i)==44)
                    delete+="/";
                else
                    delete+=input.charAt(i);
            }
        }
        return delete;
    }
}

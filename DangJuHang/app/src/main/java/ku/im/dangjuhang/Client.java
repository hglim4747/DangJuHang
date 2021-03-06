package ku.im.dangjuhang;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gyu on 2016-05-23.
 */
public class Client {
    OAuthLogin mOAuthLoginModule;
    OAuthLoginHandler handler;
    Context context;
    DefaultHttpClient httpClient = new DefaultHttpClient();
    public static HashMap<String, String> userdata= null;

    public static String SERVER_IP = "172.16.49.176";
    static final String URL = "http://"+SERVER_IP+"/djh/index.php";

    public String RegisterEvent( Hangsa h )
    {
        if(userdata == null) return null;
        JSONObject params = new JSONObject();
        try {
            params.accumulate("userid", userdata.get("id"));
            params.accumulate("title", h.getMtitle());
            params.accumulate("start_date", h.getMstart_date());
            params.accumulate("end_date", h.getMend_date());
            params.accumulate("time", h.getMtime());
            params.accumulate("place", h.getMplace());
            params.accumulate("org_link", userdata.get("email"));
            params.accumulate("main_img", h.getMmain_img());
            params.accumulate("use_fee", h.getMuse_fee());
            params.accumulate("inquiry", h.getMinquiry());
            params.accumulate("contents", h.getMcontents());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("register_event", params);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject == null || result == false) return null;
        String cultcode = null;
        try {
            cultcode = jsonObject.getString("cultcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cultcode;
    }

    public boolean CancelEvent( String hangsaID )
    {
        if(userdata == null) return false;
        JSONObject params = new JSONObject();
        try {
            params.accumulate("cultcode", hangsaID);
            params.accumulate("userid", userdata.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("cancel_event", params);
        try {
            JSONObject jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean LikeEvent( String hangsaID )
    {
        if(userdata == null) return false;
        JSONObject params = new JSONObject();
        try {
            params.accumulate("cultcode", hangsaID);
            params.accumulate("userid", userdata.get("id"));
            params.accumulate("age", userdata.get("age"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("like_event", params);
        try {
            JSONObject jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    public boolean CancelLike ( String hangsaID )
    {
        if(userdata == null) return false;
        JSONObject params = new JSONObject();
        try {
            params.accumulate("cultcode", hangsaID);
            params.accumulate("userid", userdata.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("cancel_like", params);
        try {
            JSONObject jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean GetLike( String hangsaID ){
        if(userdata == null) return false;
        JSONObject params = new JSONObject();
        try {
            params.accumulate("cultcode", hangsaID);
            params.accumulate("userid", userdata.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("get_like", params);
        try {
            JSONObject jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    } // 이미 내가 행사를 좋아했으면 true, 아니면 false


    public JSONObject GetLikeNum( String hangsaID )
    {
        if(userdata == null) return null;
        JSONObject params = new JSONObject();
        try {
            params.accumulate("cultcode", hangsaID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("get_like_num", params);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject; // 012345  total
    } // 연령대 별 Like 수

    public ArrayList<Hangsa> GetAllEvent()
    {
        if(userdata == null) return null;
        JSONObject params = new JSONObject();

        boolean result = false;
        String t = request("get_all_event", params);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Hangsa> list = parseToHangsaList(jsonObject);
        return list;
    }

    public ArrayList<Hangsa> GetMyEvent()
    {
        String userid = userdata.get("id");
        if(userdata == null) return null;
        JSONObject params = new JSONObject();
        try {
            params.put("userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("get_my_event", params);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Hangsa> list = parseToHangsaList(jsonObject);
        return list;
    }

    public ArrayList<String> GetAllLikeEvent()
    {
        if(userdata == null) return null;
        String userid = userdata.get("id");
        JSONObject params = new JSONObject();
        try {
            params.put("userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean result = false;
        String t = request("get_all_like_event", params);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(t);
            result = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonObject.length() - 1; i++) {
                list.add(jsonObject.getString(String.valueOf(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Hangsa> parseToHangsaList(JSONObject jsonObject)
    {
        ArrayList<Hangsa> list = new ArrayList<Hangsa>();
        int size = jsonObject.length();

        try {
            for (int i = 0; i < size - 1; i++) {
                JSONObject row = jsonObject.getJSONObject(String.valueOf(i));
                Hangsa hangsa = parseToHangsa(row);
                list.add(hangsa);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Hangsa parseToHangsa(JSONObject jsonObject)
    {
        Hangsa hangsa = null;
        try {
            String cultcode = jsonObject.getString("cultcode");
            String userid = jsonObject.getString("userid");
            String title = jsonObject.getString("title");
            String start_date = jsonObject.getString("start_date");
            String end_date = jsonObject.getString("end_date");
            String time = jsonObject.getString("time");
            String place = jsonObject.getString("place");
            String org_link = jsonObject.getString("org_link");
            String main_img = jsonObject.getString("main_img");
            String use_fee = jsonObject.getString("use_fee");
            String inquiry = jsonObject.getString("inquiry");
            String contents = jsonObject.getString("contents");

            hangsa = new Hangsa(title, start_date, end_date, time, place, org_link, main_img, use_fee, inquiry, contents, cultcode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hangsa;
    }

    public String request(String func, JSONObject params)
    {
        httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        try {
            params.accumulate("func", func);
        } catch (JSONException e) {
            e.printStackTrace();
        };

        AsyncTask<JSONObject, String, String> task = new RequestTask().execute(params);
        String page = "default";
        JSONArray result = null;

        try {
            page = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return page;
    }


    class RequestTask extends AsyncTask<JSONObject, String, String>
    {
        @Override
        protected String doInBackground(JSONObject... params) {
            JSONObject jsonObject = params[0];
            String result = "";
            try {
                HttpPost httpPost = new HttpPost(URL);
                //httpPost.setParams(httpParams);

                httpPost.setEntity(new StringEntity(jsonObject.toString(), HTTP.UTF_8));

                HttpResponse response = httpClient.execute(httpPost);
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while((line = br.readLine()) != null){
                    result += line;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    class MyAuthHandler extends OAuthLoginHandler
    {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(context);
                String refreshToken = mOAuthLoginModule.getRefreshToken(context);
                long expiresAt = mOAuthLoginModule.getExpiresAt(context);
                String tokenType = mOAuthLoginModule.getTokenType(context);
                //   mOauthAT.setText(accessToken);
                //   mOauthRT.setText(refreshToken);
                //   mOauthExpires.setText(String.valueOf(expiresAt));
                //   mOauthTokenType.setText(tokenType);
                //   mOAuthState.setText(mOAuthLoginModule.getState(getApplicationContext()).toString());
                String info = mOAuthLoginModule.requestApi(context, accessToken, "https://apis.naver.com/nidlogin/nid/getUserProfile.xml");
                String in = info;
                info = info + "";

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(context);
//                Toast.makeText(context, "errorCode:" + errorCode
//                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean NaverLogin(Activity act)
    {
        context = act.getApplicationContext();
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                context
                , "S6nKrH6CGNXyt2TKEYZY"
                , "DJtUt0iCNx"
                , "DangJuHang"
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );

        handler = new MyAuthHandler();
        mOAuthLoginModule.startOauthLoginActivity(act, handler);
        RequestApiTask task = new RequestApiTask();
        task.execute();
        String page = "";
        try {
            page = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        HashMap<String, String> getData = new HashMap<String, String>();
        XMLParsing(page, getData);

        if(getData.get("message").equals("success"))
        {
            userdata = getData;
            String[] age = getData.get("age").split("-");
            int a = Integer.parseInt(age[0]) / 10;
            getData.put("age", String.valueOf(a)); // 연령대 변환
            return true;
        }
        userdata = null;
        return false;
    }

    class RequestPlace extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = null;
            try {
                url = "https://openapi.naver.com/v1/search/local.xml?query="+ URLEncoder.encode(params[0], "UTF-8") +"&display=10&start=1&sort=vote";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("Content-Type", "application/json");
            httpget.setHeader("Accept", "*/*");
            httpget.setHeader("X-Naver-Client-Id", "S6nKrH6CGNXyt2TKEYZY");
            httpget.setHeader("X-Naver-Client-Secret", "DJtUt0iCNx");
            String result = "";
            try {
                HttpResponse response = httpClient.execute(httpget);
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while((line = br.readLine()) != null){
                    result += line;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    };


    class RequestCoord extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = null;
            try {
                url = "https://openapi.naver.com/v1/map/geocode?encoding=utf-8&coord=latlng&output=xml&query=" + URLEncoder.encode(params[0], "EUC-KR");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("Content-Type", "application/json");
            httpget.setHeader("Accept", "*/*");
            httpget.setHeader("X-Naver-Client-Id", "S6nKrH6CGNXyt2TKEYZY");
            httpget.setHeader("X-Naver-Client-Secret", "DJtUt0iCNx");
            String result = "";
            try {
                HttpResponse response = httpClient.execute(httpget);
                String line = null;
                     BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                     while((line = br.readLine()) != null){
                             result += line;
                     }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    };

    public boolean SearchPlace(String search, Double lat[], Double longi[])
    {
        String page = "";
        HashMap<String, String> getSearchData = new HashMap<>();
        HashMap<String, String> getCoordData = new HashMap<>();

        try {
            page = new RequestPlace().execute(search).get();
            XMLParsing(page, getSearchData);
            String tpage = new RequestCoord().execute(search).get();
            if(tpage != null)
            {
                getCoordData.clear();
                XMLParsing(tpage, getCoordData);
                if(getCoordData.get("x") != null)
                {
                    if(lat != null) lat[0] = Double.parseDouble(getCoordData.get("x"));
                    if(longi != null) longi[0] = Double.parseDouble(getCoordData.get("y"));
                    return true;
                }
            }

            if(getSearchData.size() <= 0) return false;
            if(getSearchData.get("address") == null) return false;
            page = new RequestCoord().execute(getSearchData.get("address")).get();
            XMLParsing(page, getCoordData);
            if(getCoordData.size() <= 0) return false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(lat != null) lat[0] = Double.parseDouble(getCoordData.get("x"));
        if(longi != null) longi[0] = Double.parseDouble(getCoordData.get("y"));
        return true;
    }

    boolean XMLParsing(String content, HashMap<String, String> getData)
    {
        XmlPullParserFactory factory = null;
        XmlPullParser xpp;
        int eventType;
        try {
            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(content));
            eventType = xpp.getEventType();
            boolean bSet = false;
            String tag_name = "default";
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType==XmlPullParser.START_DOCUMENT){
                    ;
                }else if(eventType== XmlPullParser.START_TAG){
                    tag_name = xpp.getName();
                    //if(tag_name.equals("id")||tag_name.equals("name")|| tag_name.equals("birthday")|| tag_name.equals("gender")|| tag_name.equals("age"))
                    {
                        bSet=true;
                    }
                }else if(eventType==XmlPullParser.TEXT){
                    if(bSet){
                        String data = xpp.getText();
                        getData.put(tag_name, data);
                        bSet = false;
                    }
                }else if(eventType==XmlPullParser.END_TAG){
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = mOAuthLoginModule.getAccessToken(context);

            String content = mOAuthLoginModule.requestApi(context, at, url);
            //String result = XMLParsing(content, getData);
            return content;
        }
        protected void onPostExecute(String content) {
//            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        }
    }
}

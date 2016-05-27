package ku.im.dangjuhang;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Gyu on 2016-05-23.
 */
public class Client {
    OAuthLogin mOAuthLoginModule;
    OAuthLoginHandler handler;
    Context context;

    class MyAuthHandler extends OAuthLoginHandler
    {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(context);
                String refreshToken = mOAuthLoginModule.getRefreshToken(context);
                long expiresAt = mOAuthLoginModule.getExpiresAt(context);
                String tokenType = mOAuthLoginModule.getTokenType(context);
                // mOauthAT.setText(accessToken);
                //  mOauthRT.setText(refreshToken);
                //   mOauthExpires.setText(String.valueOf(expiresAt));
                //   mOauthTokenType.setText(tokenType);
                //   mOAuthState.setText(mOAuthLoginModule.getState(getApplicationContext()).toString());
                String info = mOAuthLoginModule.requestApi(context, accessToken, "https://apis.naver.com/nidlogin/nid/getUserProfile.xml");
                String in = info;
                info = info + "";

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(context);
                Toast.makeText(context, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    }

    void NaverLogin(Activity act)
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
        new RequestApiTask().execute();
    }

    String XMLParsing(String content)
    {
        String result = "";
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
                        result += (tag_name + " : " + data+"\n");
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

        return result;
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
            String result = XMLParsing(content);
            return result;
        }
        protected void onPostExecute(String content) {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        }
    }
}

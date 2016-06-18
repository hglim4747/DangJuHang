package ku.im.dangjuhang;

import android.os.Handler;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by miran lee on 2016-06-02.
 */
public class SeoulXMLParser extends XMLParser implements Runnable{
    static ArrayList<Hangsa> mDataList = new ArrayList<Hangsa>();
    private Handler mHandler = new Handler();

    String tag;
    String mtitle;
    String mstart_date;
    String mend_date;
    String mtime;
    String mplace;
    String morg_link;
    String mmain_img;
    String muse_fee;
    String minquiry;
    String mcontents;

    public SeoulXMLParser(String addr, Handler handler) {
        super(addr);
        mHandler = handler;
    }

    public void startParsing() {
        XmlPullParser parser = getXMLParser("utf-8");

        if(parser == null) {
            mDataList = null;
            Log.d("SeoulXMLParser", "Parser object is null");
        } else {
            mDataList = new ArrayList<Hangsa>();
            try {
                int event = parser.getEventType();
                int tagIdentifier = 0;

                while(event != XmlPullParser.END_DOCUMENT) {
                    switch(event){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if(tag.equals("TITLE")){
                                tagIdentifier = 1;
                            }else if(tag.equals("STRTDATE")) {
                                tagIdentifier = 2;
                            }else if(tag.equals("END_DATE")) {
                                tagIdentifier = 3;
                            }else if(tag.equals("TIME")) {
                                tagIdentifier = 4;
                            }else if(tag.equals("PLACE")) {
                                tagIdentifier = 5;
                            }else if(tag.equals("ORG_LINK")) {
                                tagIdentifier = 6;
                            }else if(tag.equals("MAIN_IMG")) {
                                tagIdentifier = 7;
                            }else if(tag.equals("USE_FEE")) {
                                tagIdentifier = 8;
                            }else if(tag.equals("INQUIRY")) {
                                tagIdentifier = 9;
                            }else if(tag.equals("CONTENTS")) {
                                tagIdentifier = 10;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            if(tagIdentifier == 1){
                                mtitle = parser.getText().trim();
                            }else if(tagIdentifier == 2) {
                                mstart_date = parser.getText().trim(); // 날짜가 어떤 형식인지 보기
                            }else if(tagIdentifier == 3) {
                                mend_date = parser.getText().trim();
                            }else if(tagIdentifier == 4) {
                                mtime = parser.getText().trim();
                            }else if(tagIdentifier == 5) {
                                mplace = parser.getText().trim();
                            }else if(tagIdentifier == 6) {
                                morg_link = parser.getText().trim();
                            }else if(tagIdentifier == 7) {
                                mmain_img = parser.getText().trim();
                            }else if(tagIdentifier == 8) {
                                muse_fee = parser.getText().trim();
                            }else if(tagIdentifier == 9) {
                                minquiry = parser.getText().trim();
                            }else if(tagIdentifier == 10) {
                                mcontents = parser.getText().trim();
                                Hangsa data = new Hangsa(mtitle,mstart_date,mend_date,mtime,mplace,morg_link,mmain_img,muse_fee,minquiry,mcontents);
                                mDataList.add(data);
                            }
                            tagIdentifier = 0;
                            break;
                    }
                    event = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Hangsa> getArray() {
        return mDataList;
    }
    public Hangsa getHangsa(int postition){
        ArrayList<Hangsa> a = this.getArray();
        Hangsa hangsa = a.get(postition);
        return hangsa;
    }

    public void run() {
        startParsing();
        mHandler.sendEmptyMessage(0);
    }

}

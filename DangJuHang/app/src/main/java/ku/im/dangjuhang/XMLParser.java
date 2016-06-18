package ku.im.dangjuhang;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by miran lee on 2016-06-02.
 */
public abstract class XMLParser {
    private String mAddr;

    public XMLParser(String addr) {
        mAddr = addr;
    }

    public XmlPullParser getXMLParser(String type) {
        try {
            URL target = new URL(mAddr);
            InputStream is = target.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, type);

            return parser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract void startParsing();
}

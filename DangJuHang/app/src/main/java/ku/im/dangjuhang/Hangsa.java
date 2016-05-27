package ku.im.dangjuhang;

/**
 * Created by kim on 2016-04-17.
 */
public class Hangsa {


    int imagersc;
    String content;
    public Hangsa( int imagersc,String content) {
        this.content = content;
        this.imagersc = imagersc;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImagersc() {
        return imagersc;
    }

    public void setImagersc(int imagersc) {
        this.imagersc = imagersc;
    }



}

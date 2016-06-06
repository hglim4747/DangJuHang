package ku.im.dangjuhang;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kim on 2016-04-17.
 */
public class Hangsa implements Parcelable{

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

    public double x,y;

    public Hangsa(String title, String start_date,
                  String end_date, String time,
                  String place, String org_link,
                  String main_img, String use_fee,
                  String inquiry, String contents) {
        mtitle = title.replace("&#39;","");
        mstart_date = start_date.replace("&#39;","");
        mend_date = end_date.replace("&#39;","");
        mtime = time.replace("&#39;","");
        mplace = place.replace("&#39;","");
        morg_link = org_link.replace("&#39;","");
        mmain_img = main_img.replace("&#39;","");
        muse_fee = use_fee.replace("&#39;","");
        minquiry = inquiry.replace("&#39;","");
        mcontents = contents.replace("&#39;","");

        x=0;
        y=0;
    }

    private void readFromParcel(Parcel in) {
        mtitle = in.readString();
        mstart_date = in.readString();
        mend_date = in.readString();
        mtime = in.readString();
        mplace = in.readString();
        morg_link=in.readString();
        mmain_img = in.readString();
        muse_fee = in.readString();
        minquiry = in.readString();
        mcontents = in.readString();
    }

    public Hangsa(Parcel in) {
        readFromParcel(in);
    }

    public Hangsa() {}

    public String getTitle() {
        return mtitle;
    }

    public void settitle(String title) {
        mtitle = title;
    }

    public String gettime() {
        return mtime;
    }

    public String getplace() {
        return mplace;
    }

    public String getimg() {
        return mmain_img;
    }

    public String getcontents() {
        return mcontents;
    }

    public String getmain_img() {
        return mmain_img;
    }

    public void setmain_img(String img) {
        mmain_img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mtitle);
        dest.writeString(mstart_date);
        dest.writeString(mend_date);
        dest.writeString(mtime);
        dest.writeString(mplace);
        dest.writeString(morg_link);
        dest.writeString(mmain_img);
        dest.writeString(muse_fee);
        dest.writeString(minquiry);
        dest.writeString(mcontents);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Hangsa createFromParcel(Parcel source) {
            return new Hangsa(source);
        }

        @Override
        public Hangsa[] newArray(int size) {
            return new Hangsa[size];
        }
    };
}

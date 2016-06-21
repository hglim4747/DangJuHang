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
    String mcultcode;

    public double x,y;

    public Hangsa(String title, String start_date,
                  String end_date, String time,
                  String place, String org_link,
                  String main_img, String use_fee,
                  String inquiry, String contents,
                  String cultcode) {

        if(title != null) mtitle = title.replace("&#39;","");
        if(start_date != null) mstart_date = start_date.replace("&#39;","");
        if(end_date != null)  mend_date = end_date.replace("&#39;","");
        if(time != null)  mtime = time.replace("&#39;","");
        if(place != null)  mplace = place.replace("&#39;","");
        if(org_link != null) morg_link = org_link.replace("&#39;","");
        if(main_img != null)  mmain_img = main_img.replace("&#39;","");
        if(use_fee != null) muse_fee = use_fee.replace("&#39;","");
        if(inquiry != null)  minquiry = inquiry.replace("&#39;","");
        if(contents != null)  mcontents = contents.replace("&#39;","");
        if(cultcode != null)  mcultcode = cultcode.replace("&#39", "");

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
        mcultcode = in.readString();
    }

    public Hangsa(Parcel in) {
        readFromParcel(in);
    }

    public Hangsa() {}

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
        dest.writeString(mcultcode);
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

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMstart_date() {
        return mstart_date;
    }

    public void setMstart_date(String mstart_date) {
        this.mstart_date = mstart_date;
    }

    public String getMend_date() {
        return mend_date;
    }

    public void setMend_date(String mend_date) {
        this.mend_date = mend_date;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getMplace() {
        return mplace;
    }

    public void setMplace(String mplace) {
        this.mplace = mplace;
    }

    public String getMorg_link() {
        return morg_link;
    }

    public void setMorg_link(String morg_link) {
        this.morg_link = morg_link;
    }

    public String getMmain_img() {
        return mmain_img;
    }

    public void setMmain_img(String mmain_img) {
        this.mmain_img = mmain_img;
    }

    public String getMuse_fee() {
        return muse_fee;
    }

    public void setMuse_fee(String muse_fee) {
        this.muse_fee = muse_fee;
    }

    public String getMinquiry() {
        return minquiry;
    }

    public void setMinquiry(String minquiry) {
        this.minquiry = minquiry;
    }

    public String getMcontents() {
        return mcontents;
    }

    public void setMcontents(String mcontents) {
        this.mcontents = mcontents;
    }

    public String getMcultcode() {
        return mcultcode;
    }

    public void setMcultcode(String mcultcode) {
        this.mcultcode = mcultcode;
    }
}

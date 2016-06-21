package ku.im.dangjuhang;

/**
 * Created by kim on 2016-05-26.
*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kim on 2016-03-29.
 */
public class HangsaAdapter extends ArrayAdapter<Hangsa> {
    ArrayList<Hangsa> items;
    Context context;

    public HangsaAdapter(Context context, int resource, ArrayList<Hangsa> objects) {
        super(context, resource, objects);
        items = objects;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        View v = convertview;

        if( v == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }

        final Hangsa p = items.get(position);
        ImageView ii = (ImageView)v.findViewById(R.id.row_img);
        TextView bt = (TextView)v.findViewById(R.id.row_text);
        TextView where = (TextView)v.findViewById(R.id.where);
        TextView when = (TextView)v.findViewById(R.id.when);
        TextView district = (TextView)v.findViewById(R.id.district);

        bt.setText(p.getMtitle());
        where.setText(p.getMplace());
        when.setText(p.getMstart_date() + " ~ " + p.getMend_date());

        double xa = MainActivity.la;
        double xn = MainActivity.ln;

        double dx = (p.x - xn) * 92;
        double dy = (p.y - xa) * 114;

        double dis = Math.sqrt(dx * dx + dy * dy);
        String out = String.format("%.2f",dis);
        district.setText("내 위치로부터 " + out + "km"); // 여기에 거리 정보 넣어야돼애ㅐㅐ

        new DownloadImageTask(ii).execute(p.getMmain_img());

        return v;
    }
}

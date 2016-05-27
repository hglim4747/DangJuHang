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

        Hangsa p = items.get(position);
        if (p != null){
            ImageView ii = (ImageView)v.findViewById(R.id.row_img);
            TextView bt = (TextView)v.findViewById(R.id.row_text);
            ii.setImageResource(p.getImagersc());
            bt.setText(p.getContent());

        }
        return v;
    }
}

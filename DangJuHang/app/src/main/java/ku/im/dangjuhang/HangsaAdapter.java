package ku.im.dangjuhang;

/**
 * Created by kim on 2016-05-26.
*/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
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
        bt.setText(p.getTitle());

        new DownloadImageTask(ii).execute(p.getmain_img());

        return v;
    }
}

package ku.im.dangjuhang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016-06-21.
 */
public class MyRegAdapter extends ArrayAdapter {

    ArrayList<Hangsa> items;
    Context context;

    public MyRegAdapter(Context context, int resource, ArrayList<Hangsa> objects) {
        super(context, resource, objects);
        items = objects;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        View v = convertview;

        if( v == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.myreg_listview_row, null);
        }

        final Hangsa p = items.get(position);
        ImageView image = (ImageView)v.findViewById(R.id.myfrag_listview_row_img);
        TextView text = (TextView)v.findViewById(R.id.myfrag_listview_row_text);
        ImageView imagedelete = (ImageView)v.findViewById(R.id.myfrag_listview_row_imgdelete);


        text.setText(p.getMtitle());
        new DownloadImageTask(image).execute(p.getMmain_img());


        return v;
    }

}

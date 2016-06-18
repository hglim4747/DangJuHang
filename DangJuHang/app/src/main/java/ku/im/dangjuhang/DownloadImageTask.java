package ku.im.dangjuhang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by miran lee on 2016-06-06.
 */
public class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{

    ImageView ii;

    public DownloadImageTask(ImageView image) {
        this.ii = image;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urldisplay = params[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ii.setImageBitmap(bitmap);
    }
}

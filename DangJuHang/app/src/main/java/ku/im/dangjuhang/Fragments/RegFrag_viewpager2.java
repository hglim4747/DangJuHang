package ku.im.dangjuhang.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ku.im.dangjuhang.Client;
import ku.im.dangjuhang.Hangsa;
import ku.im.dangjuhang.MainActivity;
import ku.im.dangjuhang.R;
import ku.im.dangjuhang.Request;

/**
 * Created by user on 2016-06-02.
 */
public class RegFrag_viewpager2 extends Fragment{
    String name2, address2;
    String startdate2, enddate2, starttime2, endtime2 , time;
    String cel,explane;

    Button regbtn, canclebtn,selectBtn;
    EditText telEdit, explaneEdit;
    public ImageView imageView;

    private static final int RESULT_SELECT_IMAGE = 1;
    RegFrag_viewpager1 page1;
    public RegFrag_viewpager2(){}
    public RegFrag_viewpager2(RegFrag_viewpager1 page1)
    {
        this.page1 = page1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewpager_childview2,container,false);
        init(root);
        return root;
    }
    private void selectImage(){
        //open album to select image
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }

    void init(View root){
        explaneEdit = (EditText)root.findViewById(R.id.regfrag_Explane);
        telEdit = (EditText)root.findViewById(R.id.regfrag_tel);
        regbtn = (Button)root.findViewById(R.id.regfrag_insertbtn);
        canclebtn= (Button)root.findViewById(R.id.regfrag_cancelbtn);
        imageView = (ImageView)root.findViewById(R.id.image);
        selectBtn = (Button)root.findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegFrag_viewpager1  regfrag1 = page1;
                name2= regfrag1.name.getText().toString();
                address2= regfrag1.address.getText().toString();
                startdate2= regfrag1.startdateEdit.getText().toString();
                enddate2= regfrag1.enddateEdit.getText().toString();
                starttime2= regfrag1.starttimeEdit.getText().toString();
                endtime2= regfrag1.endtimeEdit.getText().toString();
                time= startdate2+"~"+endtime2;
                cel = telEdit.getText().toString();
                explane = explaneEdit.getText().toString();



                Hangsa hangsa = new Hangsa(name2,startdate2,enddate2,time,address2,null,
                        null,null,null,explane,null);

                String result = new Client().RegisterEvent(hangsa);

                if(result != null)
                {
                    //Client 코드 받아와서 이미지 이름으로 넣기
                    Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    //execute the async task and upload the image to server
                    new Upload(image,result).execute();

                    Toast.makeText(getActivity(),"등록성공ㅎㅎ",Toast.LENGTH_SHORT).show();
                    // 성공했습니다 토스트
                }
                else
                {
                    Toast.makeText(getActivity(),"등록실패ㅠㅠ",Toast.LENGTH_SHORT).show();

                    // 실패했습니다 토스트
                }

            }
        });

        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explaneEdit.setText("");
                telEdit.setText("");
                RegFrag_viewpager1 regfrag1 = page1;
                regfrag1.name.setText("");
                regfrag1.address.setText("");
                regfrag1.startdateEdit.setText("");
                regfrag1.enddateEdit.setText("");
                regfrag1.starttimeEdit.setText("");
                regfrag1.endtimeEdit.setText("");
            }
        });

    }
    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


    //async task to upload image
    private class Upload extends AsyncTask<Void,Void,String> {
        private Bitmap image;
        private String name;

        public Upload(Bitmap image,String name){
            this.image = image;
            this.name = name;
        }

        @Override
        protected String doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //compress the image to jpg format
            image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            /*
            * encode image to base64 so that it can be picked by saveImage.php file
            * */
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            //generate hashMap to store encodedImage and the name
            HashMap<String,String> detail = new HashMap<>();
            detail.put("name", name);
            detail.put("image", encodeImage);

            try{
                //convert this HashMap to encodedUrl to send to php file
                String dataToSend = hashMapToUrl(detail);
                //make a Http request and send data to saveImage.php file
                String response = Request.post("http://" + Client.SERVER_IP + "/djh/login.php", dataToSend);

                //return the response
                return response;

            }catch (Exception e){
                e.printStackTrace();
                Log.e(MainActivity.class.getSimpleName(), "ERROR  " + e);
                return null;
            }
        }



        @Override
        protected void onPostExecute(String s) {
            //show image uploaded
            Toast.makeText(getActivity(),"Image Uploaded",Toast.LENGTH_SHORT).show();
        }
    }
}

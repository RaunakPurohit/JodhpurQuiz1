package raunak.com.jodhpurquiz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageDB extends AppCompatActivity {
ImageView imgv;
Button btn;
DBAdapter obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_db);

        imgv = (ImageView)findViewById(R.id.imgview);
        btn = (Button)findViewById(R.id.btn);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.couples1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[]img = byteArrayOutputStream.toByteArray();

        //////////////////////////////////////////////////////////////////
        obj= DBAdapter.getDBAdapter(getApplicationContext());

        if(obj.checkDatabase()==false)
            obj.createDatabase(getApplicationContext());

        obj.openDatabase();
        //////////////////////////////////////////////////////////////////

        obj.insertimg(img);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imgb = obj.getimg();
                Bitmap b1 = BitmapFactory.decodeByteArray(imgb,0,imgb.length);
                imgv.setImageBitmap(b1);
            }
        });

    }


}

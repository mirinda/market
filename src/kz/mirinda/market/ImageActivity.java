package kz.mirinda.market;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ImageView;

public class ImageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView= (ImageView) findViewById(R.id.image);
        
        imageView.setImageBitmap(MainActivity.getBmp());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image, menu);
        return true;
    }
}

package kz.mirinda.market;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView= (ImageView) findViewById(R.id.image);
        TextView textView = (TextView) findViewById(R.id.saved_path);
        textView.setText(getResources().getString(R.string.path_to_img)+MainActivity.SAVED_PATH+MainActivity.FILE_NAME);
        
        imageView.setImageBitmap(MainActivity.getBmp());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image, menu);
        return true;
    }
}

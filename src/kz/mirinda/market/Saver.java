package kz.mirinda.market;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class Saver {
	public boolean saveBitmap(Bitmap bitmap,String filePath){
		String state = Environment.getExternalStorageState();
		try {
		if(state.equals(Environment.MEDIA_MOUNTED)){
			File  file = new File( Environment.getExternalStorageDirectory()+MainActivity.SAVED_PATH,MainActivity.FILE_NAME);
			if(file.exists()) Log.i("mirinda1", file.delete() ? "deleted" : "do not " + "delete:" + file.getPath());
			FileOutputStream fileOutputStream;
			Log.e("mirinda1", file.getAbsolutePath());
				fileOutputStream = new FileOutputStream(file);
			
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
			return true;
		}
		} catch (FileNotFoundException e) {
			Log.e("mirinda1", "problem with sdcard");
			e.printStackTrace();
		}
		
		return false;
		
	}

}

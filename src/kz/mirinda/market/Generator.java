package kz.mirinda.market;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Generator {
	
	public Bitmap generateBitmap(int[] count,Bitmap paidBitmap){
		Bitmap resBitmap = Bitmap.createBitmap(MainActivity.IMG_WIDTH,MainActivity.IMG_WIDTH,Config.ARGB_8888);
		//Paint paint = new Paint();
		Canvas canvas = new Canvas(resBitmap) ;
		canvas.drawColor(Color.RED);
		
		
		
		return resBitmap;
		
	}

}

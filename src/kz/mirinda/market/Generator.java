package kz.mirinda.market;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Generator {
	private static final int PRODUCT_OFFSET=50;
	private static final int OFFSET = 30;
	private static final int BETWEEN_PRODUCT_OFFSET=Math.round(MainActivity.FONT_SIZE)*2;
	
	public Bitmap generateBitmap(int[] count,Bitmap paidBitmap){		
		Bitmap resBitmap = Bitmap.createBitmap(MainActivity.IMG_WIDTH,MainActivity.IMG_HEIGHT,Config.ARGB_8888);
		Canvas canvas = new Canvas(resBitmap) ;
		Paint arialPaint = MainActivity.getArialPaint();
		
		
		canvas.drawColor(Color.WHITE);
		canvas.drawText("Ваша покупка:",OFFSET,BETWEEN_PRODUCT_OFFSET, arialPaint);
		int strCount=2;
		for(int i=0;i<count.length;i++){
			if(count[i]>0){
				canvas.drawText(String.format("Товар №%d(%d штук)", i,count[i]), PRODUCT_OFFSET, BETWEEN_PRODUCT_OFFSET*strCount, arialPaint);
				strCount++;
			}
		}

		canvas.drawBitmap(paidBitmap,resBitmap.getWidth()-paidBitmap.getWidth(),canvas.getHeight()/2-paidBitmap.getHeight()/2,null);
		
		return resBitmap;
		
	}

}

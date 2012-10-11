package kz.mirinda.market;

import java.util.ArrayList;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnClickListener {
	private static final int HOLDER_COUNT=30;
	private static final int PRODUCT_COUNT = 30;
	private static final int PAID_COUNT = 5;
	private static final int MAX_COST = 500;
	private static final int DURATION = 300; 
	private static final String JPG_URL = "http://digitalclick.ru/oplacheno.jpg";
	

	private static Bitmap bmp = null;
	private static Paint arialPaint = new Paint();

	public static final int IMG_WIDTH = 640;
	public static final int IMG_HEIGHT = 350;
	public static final String FONT_NAME = "arial.ttf";
	public static final float FONT_SIZE = 24;
	public static final String SAVED_PATH="";// from sdcard(not root).
	public static final String FILE_NAME="Paid.png";
	
	public static Bitmap getBmp() {
		return bmp;
	}

	public static Paint getArialPaint() {
		return arialPaint;
	}

	private String[] productsName = new String[PRODUCT_COUNT]; //
	private int[] productsCount = new int[PRODUCT_COUNT];
	private int[] productsCost = new int[PRODUCT_COUNT];
	private int freeCount = PAID_COUNT;
	private MyTask task = null;// new MyTask(this);
	private Button paidButton, cancelButton;

	private class ViewHolder {
		public TextView name;
		public TextView cost;
		public EditText count;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		paidButton = (Button) findViewById(R.id.paid_button);
		paidButton.setOnClickListener(this);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(this);

		init();
		setListAdapter(new MyAdapter(this));

		Typeface typeface = Typeface.createFromAsset(getAssets(), FONT_NAME);
		arialPaint.setTextSize(FONT_SIZE);
		arialPaint.setTypeface(typeface);
		arialPaint.setColor(Color.BLACK);
	}

	private void init() {
		Random random = new Random();
		for (int i = 0; i < productsName.length; i++) {
			productsName[i] = "Товар N " + i;
			productsCount[i] = 0;
			productsCost[i] = random.nextInt(MAX_COST);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.paid_button) {
			Log.i("mirinda1", "Paid");
			if (freeCount == 5) {
				Toast.makeText(this,
						getResources().getString(R.string.wrn_choose_product),
						DURATION).show();
				return;
			}
			if (task == null) {
				task = new MyTask(this);
				task.execute(productsCount);
			} else {
				Toast.makeText(
						this,
						getResources().getString(R.string.wrn_task_running_now),
						DURATION).show();
			}
		}else if (v.getId()==R.id.cancel_button) {
			Log.i("mirinda1", "Cancel");
			if(task!=null){
				task.cancel(false);
			}
			for(int i=0;i<productsCount.length;i++) productsCount[i]=0;
			freeCount=PAID_COUNT;
			task=null;
			((MyAdapter)getListAdapter()).clear();
			
		}
	}

	private class MyAdapter extends BaseAdapter {
		ArrayList<ViewHolder> holders =new ArrayList<MainActivity.ViewHolder>(HOLDER_COUNT);
		
		Context context;

		public MyAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return PRODUCT_COUNT;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			ViewHolder viewHolder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.main_row, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) rowView
						.findViewById(R.id.product_name_row);
				viewHolder.cost = (TextView) rowView
						.findViewById(R.id.product_costs_row);
				viewHolder.count = (EditText) rowView
						.findViewById(R.id.product_counts_row);
				viewHolder.count
						.setOnFocusChangeListener(new OnFocusChangeListener() {
							@Override
							public void onFocusChange(View v, boolean hasFocus) {
								Log.i("mirinda1", "OnFocus Changed: "
										+ hasFocus);
								if (hasFocus)
									return;
								int pos = (Integer) v.getTag();
								EditText editText = (EditText) v;
								int count = parseInt(editText.getText()+"");
								count = verifyFreeCount(pos, count);
								editText.setText(count + "");

							}
						});
				holders.add(viewHolder);
			} else {
				viewHolder = (ViewHolder) rowView.getTag();

				int oldPosition = Integer.parseInt(viewHolder.count.getTag()
						.toString());
				int count = parseInt(viewHolder.count.getText()+"");
				verifyFreeCount(oldPosition, count);

			}

			viewHolder.name.setText(productsName[position]);
			viewHolder.cost.setText(productsCost[position] + "");
			viewHolder.count.setText(productsCount[position] + "");
			viewHolder.count.setTag(position);
			rowView.setTag(viewHolder);
			return rowView;
		}
		
		public void clear(){
			for (ViewHolder holder : holders) {
				holder.count.setText(0+"");
			}
		}
		
		/**
		 * this function see, that users chosen products count < 5
		 * 
		 * @param pos
		 *            - position of current element
		 * @param count
		 *            - number in current element
		 */
		private int verifyFreeCount(int pos, int count) {
			if (productsCount[pos] < count) {
				if (count > freeCount) {
					Toast.makeText(
							context,
							context.getResources().getString(
									R.string.error_max_count), DURATION).show();
					count = freeCount + productsCount[pos];
				}
				freeCount -= count - productsCount[pos];
			} else {
				freeCount += productsCount[pos] - count;
			}
			//Log.i("mirinda1", "count=" + count + "; freeCount=" + freeCount + "; pos=" + pos);
			productsCount[pos] = count;
			return count;
		}
		
		private int parseInt(String s){
			return s!=""?Integer.parseInt(s):0;
		}
	}

	private class MyTask extends AsyncTask<int[], Void, Bitmap> {
		private Context context;
		
		MyTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(int[]... params) {
			Log.i("mirinda1", "start background");
			Downloader downloader = new Downloader();
			Generator generator = new Generator();
			Saver saver = new Saver();
			
			Bitmap paidBitmap = downloader.downloadImage(MainActivity.JPG_URL);
			if(isCancelled()||paidBitmap==null){ 
				return null;
			}
			Bitmap bitmap = generator.generateBitmap(params[0], paidBitmap);
			if(isCancelled()){
				return null;
			}
			boolean isSaved = saver.saveBitmap(bitmap, "");
			if(!isSaved){
				return null;
			}	
			if(isCancelled()){
				return null;
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {

			super.onPostExecute(result);
			task=null;
			if(isCancelled()){
				return;
			}			
			if(result==null){
				Toast.makeText(context, getResources().getString(R.string.err_task_is_stopped), DURATION).show();
				return;
			}
			
			bmp = result;
			Intent intent = new Intent();
			intent.setClass(context, ImageActivity.class);
			startActivity(intent);
		}

	}

}

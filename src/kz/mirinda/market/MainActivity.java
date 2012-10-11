package kz.mirinda.market;

import java.util.Random;

import android.R.color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.widget.LinearLayout;

public class MainActivity extends ListActivity {
	private static final int PRODUCT_COUNT = 30;
	private static final int PAID_COUNT = 5;
	private static final int MAX_COST = 500;
	private static final int DURATION = 200; //duration of toast

	private String[] productsName = new String[PRODUCT_COUNT]; //
	private int[] productsCount = new int[PRODUCT_COUNT];
	private int[] productsCost = new int[PRODUCT_COUNT];
	private int freeCount = PAID_COUNT;
	
	
	private class ViewHolder {
		public TextView name;
		public TextView cost;
		public EditText count;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setListAdapter(new MyAdapter(this));
		
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

	private class MyAdapter extends BaseAdapter {

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
								Log.i("mirinda1","OnFocus Changed: "+hasFocus);
								if(hasFocus)return;
								int pos = (Integer) v.getTag();
								EditText editText = (EditText) v;
								int count = Integer.parseInt(editText.getText()
										.toString());
								count = verifyFreeCount(pos, count);								
								editText.setText(count+"");
								
							}
						});
			} else {
				viewHolder = (ViewHolder) rowView.getTag();
				
				
				int oldPosition = Integer.parseInt(viewHolder.count.getTag().toString());
				int count = Integer.parseInt(viewHolder.count.getText().toString());
				verifyFreeCount(oldPosition, count);
				
			}
			
			viewHolder.name.setText(productsName[position]);
			viewHolder.cost.setText(productsCost[position] + "");
			viewHolder.count.setText(productsCount[position] + "");
			viewHolder.count.setTag(position);
			rowView.setTag(viewHolder);
			return rowView;
		}
		
		/**
		 * this function see, that users chosen products count < 5 
		 * 
		 * @param pos - position of current element
		 * @param count - number in current element
		 */
		int verifyFreeCount(int pos, int count){
			if (productsCount[pos] < count) {
				if (count > freeCount) {
					Toast.makeText(
							context,
							context.getResources()
									.getString(
											R.string.error_max_count),
							DURATION).show();
					count = freeCount+productsCount[pos];
				}
				freeCount -= count - productsCount[pos];
			}else{
				freeCount+=  productsCount[pos]-count;
			}
			Log.i("mirinda1","count="+count+"; freeCount="+freeCount+"; pos="+pos);
			productsCount[pos]=count;
			return count;
		}
		
		
	}

	
}

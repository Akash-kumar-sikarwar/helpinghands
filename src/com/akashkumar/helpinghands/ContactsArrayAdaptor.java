package com.akashkumar.helpinghands;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactsArrayAdaptor extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public ContactsArrayAdaptor(Context context, String[] values) {
		super(context, R.layout.contacts, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.contacts, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		
		LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.linearLayout);
		textView.setText(values[position]);
		String s = values[position];
		if(s.equals("")||s.equals("field is empty")){
			imageView.setVisibility(View.GONE);
			textView.setVisibility(View.GONE);
		}
		/*if (s.startsWith("Add new contact")) {
			imageView.setImageResource(R.drawable.plus);
		} */
		return rowView;
	}
}


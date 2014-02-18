package com.brahmkiran.todo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class EditItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		// Show the Up button in the action bar.
		setupActionBar();
		
		etEditItem = (EditText)findViewById(R.id.etEditItem);
		
		savedText = getIntent().getStringExtra("text");
	    itemPos = getIntent().getIntExtra("pos", -1);
	    
	    etEditItem.setText(savedText);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSaved(View v) {
		// Save the text
		savedText = etEditItem.getText().toString();
		Intent data = new Intent();
		data.putExtra("text", savedText);
		data.putExtra("pos", itemPos);
		setResult(RESULT_OK, data);
		finish();
	}

	// Private stuff.
	private EditText etEditItem;
	private String savedText;
	private int itemPos;
}

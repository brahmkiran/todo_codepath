package com.brahmkiran.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);
		readItems();
		todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
		
		lvItems = (ListView) findViewById(R.id.lvItems);
		lvItems.setAdapter(todoAdapter);
		
		etNewItem = (EditText)findViewById(R.id.etNewItem);
		
		setupListViewListner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list, menu);
		return true;
	}
	
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		todoAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
	}

	// Protected
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     String newText = data.getExtras().getString("text");
	     int pos = data.getExtras().getInt("pos");
	     todoItems.set(pos, newText);
	     todoAdapter.notifyDataSetChanged();
	     // Toast the name to display temporarily on screen
	     Toast.makeText(this, "Value Edited", Toast.LENGTH_SHORT).show();
	     writeItems();
	  }
	}
	
	// Private stuff here
	private void setupListViewListner() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return false;
			}
			
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				// Create an intent which opens the edit item activity with the current item as parameter.
				Intent i = new Intent(TodoListActivity.this, EditItemActivity.class);
				// put "extras" into the bundle for access in the second activity
				Log.i("TODO App : ", "Pos : " + pos);
				i.putExtra("pos", pos); 
				i.putExtra("id", id);
				i.putExtra("text", todoItems.get(pos).toString());
				// brings up the second activity
				startActivityForResult(i, REQUEST_CODE);
			}
			
		});
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 1;
}

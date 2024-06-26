package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private ArrayAdapter<Task> adapter;
    private EditText editTextTask;
    private ListView listViewTasks;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);

        editTextTask = findViewById(R.id.editTextTask);
        listViewTasks = findViewById(R.id.listViewTasks);
        listViewTasks.setAdapter(adapter);

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = tasks.get(position);
                editTextTask.setText(task.getName());
                selectedIndex = position;
                findViewById(R.id.buttonAdd).setVisibility(View.GONE);
                findViewById(R.id.buttonUpdate).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonDelete).setVisibility(View.VISIBLE);
            }
        });

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void addTask(View view) {
        String taskName = editTextTask.getText().toString();
        if (!taskName.isEmpty()) {
            tasks.add(new Task(taskName));
            adapter.notifyDataSetChanged();
            editTextTask.setText("");
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTask(View view) {
        String taskName = editTextTask.getText().toString();
        if (selectedIndex != -1 && !taskName.isEmpty()) {
            tasks.get(selectedIndex).setName(taskName);
            adapter.notifyDataSetChanged();
            editTextTask.setText("");
            selectedIndex = -1;
            findViewById(R.id.buttonAdd).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonUpdate).setVisibility(View.GONE);
            findViewById(R.id.buttonDelete).setVisibility(View.GONE);
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please select a task to update", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTask(View view) {
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            adapter.notifyDataSetChanged();
            editTextTask.setText("");
            selectedIndex = -1;
            findViewById(R.id.buttonAdd).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonUpdate).setVisibility(View.GONE);
            findViewById(R.id.buttonDelete).setVisibility(View.GONE);
            Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please select a task to delete", Toast.LENGTH_SHORT).show();
        }
    }
}

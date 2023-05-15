package com.example.reminderapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.Adapter.EventAdapter;
import com.example.reminderapp.Database.DatabaseClass;
import com.example.reminderapp.Database.EntityClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton createEvent;
    EventAdapter eventAdapter;
    RecyclerView recyclerview;
    DatabaseClass databaseClass;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createEvent = findViewById(R.id.btn_createEvent);
        recyclerview = findViewById(R.id.recyclerview);
        createEvent.setOnClickListener(this);
        databaseClass = DatabaseClass.getDatabase(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();

    }

    private void setAdapter() {
        List<EntityClass> classList = databaseClass.EventDao().getAllData();

        int count = classList.size();
        if (count>0){
            TextView ev = findViewById(R.id.no_reminder_text);
            ev.setVisibility(View.GONE);
        }

        eventAdapter = new EventAdapter(getApplicationContext(), classList);
        recyclerview.setAdapter(eventAdapter);

        eventAdapter.setDialog(new EventAdapter.Dialog() {
            @Override
            public void OnClick(int position) {
                final CharSequence[] dialogitem = {"edit", "delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                                intent.putExtra("id", classList.get(position).id);
                                startActivity(intent);
                                break;
                            case 1:
                                EntityClass entityClass = classList.get(position);
                                databaseClass.EventDao().delete(entityClass);
                                onResume();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == createEvent) {
            goToCreateEventActivity();
        }
    }

    private void goToCreateEventActivity() {
        Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
        startActivity(intent);
    }
}

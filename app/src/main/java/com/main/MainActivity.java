package com.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.main.model.Item;
import com.main.model.ItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemListener, SearchView.OnQueryTextListener {

    private int listPosition;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> items;
    private RadioButton male,female;
    private Button add, update, dateChecker;

    private EditText job,date;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerView = findViewById(R.id.rview);
        adapter = new ItemAdapter(items,getApplicationContext());
        adapter.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(this);
        add.setOnClickListener(view -> {
            String jobName = job.getText().toString();
            String dateValue = date.getText().toString();
            int imgValue;
            if(male.isChecked()) imgValue = R.drawable.male;
            else  imgValue = R.drawable.female;
            if(jobName.isEmpty() || dateValue.isEmpty() || (!male.isChecked() && !female.isChecked())){
                Toast.makeText(this, "Vui Long Dien Day Du", Toast.LENGTH_SHORT).show();
            }
            else{
                adapter.add(new Item(imgValue,jobName, dateValue));
                Toast.makeText(this, "DA THEM THANH CONG", Toast.LENGTH_SHORT).show();
                job.setText("");
                date.setText("");
            }
        });

        dateChecker.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    date.setText(d + "/" + (m+1) + "/" + y);
                }
            },y,m,d);
            dialog.show();
        });

        update.setOnClickListener(view -> {
            String jobName = job.getText().toString();
            String dateValue = date.getText().toString();
            int imgValue;
            if(male.isChecked()) imgValue = R.drawable.male;
            else  imgValue = R.drawable.female;
            if(jobName.isEmpty() || dateValue.isEmpty() || (!male.isChecked() && !female.isChecked())){
                Toast.makeText(this, "Vui Long Dien Day Du", Toast.LENGTH_SHORT).show();
            }
            else{
                adapter.update(listPosition,new Item(imgValue,jobName, dateValue));
                Toast.makeText(this, "Cap nhanh thanh cong", Toast.LENGTH_SHORT).show();
                job.setText("");
                date.setText("");
                add.setEnabled(true);
                update.setEnabled(false);
            }
        });
    }
    public void init(){
        items = new ArrayList<>();
        items.add(new Item(R.drawable.male,"RUA BAT", "26/03/2023"));
        items.add(new Item(R.drawable.male,"LAU NHA", "26/03/2023"));
        items.add(new Item(R.drawable.male,"QUET RAC", "26/03/2023"));
        items.add(new Item(R.drawable.female,"RUA BAT", "26/03/2023"));
        items.add(new Item(R.drawable.female,"CHUYEN DO", "26/03/2023"));
        items.add(new Item(R.drawable.female,"AN HAI", "26/03/2023"));

        this.job = findViewById(R.id.mjob);
        this.date = findViewById(R.id.datetext);
        this.male = findViewById(R.id.male);
        this.female = findViewById(R.id.female);
        this.add = findViewById(R.id.add);
        this.update = findViewById(R.id.update);
        this.dateChecker = findViewById(R.id.datebutton);
        this.searchView = findViewById(R.id.search);

    }

    @Override
    public void onItemCLick(View view, int position) {
        Item item = adapter.getItemAt(position);
        job.setText(item.getJob());
        date.setText(item.getDate());
        if(item.getImg() == R.drawable.male){
            male.setChecked(true);
        } else female.setChecked(true);
        add.setEnabled(false);
        update.setEnabled(true);
        listPosition = position;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Item> filterList = new ArrayList<>();
        for(Item i : adapter.getbList()){
            if(i.getJob().toLowerCase().contains(s.toLowerCase())){
                filterList.add(i);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.filterList(filterList);
        }
    }
}
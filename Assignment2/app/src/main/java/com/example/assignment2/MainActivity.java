package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> items;
    private CustomAdapter itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = findViewById(R.id.lvItems);
        items = new ArrayList<>();

        itemsAdapter = new CustomAdapter(items);
        lvItems.setAdapter(itemsAdapter);
    }

    public void onAddItem(View v) {
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        if (!itemText.isEmpty()) {
            items.add(itemText);
            itemsAdapter.notifyDataSetChanged();
            etNewItem.setText("");
        } else {
            Toast.makeText(this, "Please enter a valid item", Toast.LENGTH_SHORT).show();
        }
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter(List<String> items) {
            super(MainActivity.this, R.layout.list_item, items);
        }

        @Override
        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            View itemView = getLayoutInflater().inflate(R.layout.list_item, parent, false);

            String currentItem = items.get(position);
            android.widget.TextView tvItem = itemView.findViewById(R.id.tvItem);
            tvItem.setText(currentItem);

            Button btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(v -> {
                items.remove(position);
                notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
            });

            return itemView;
        }
    }
}

package pl.patrykheciak.apkatodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class NewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        final EditText listName = findViewById(R.id.edit_text_list_name);
        final Spinner spinner = findViewById(R.id.spinner);

        findViewById(R.id.button_date_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("name", listName.getText().toString());
                returnIntent.putExtra("color", spinner.getSelectedItem().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}

package pl.patrykheciak.apkatodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.patrykheciak.apkatodo.db.AppDatabase;
import pl.patrykheciak.apkatodo.db.Task;

public class NewTaskActivity extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {

    public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";
    public static final String EXTRA_SUBLIST_ID = "EXTRA_SUBLIST_ID";

    private String KEY_YEAR = "KEY_YEAR";
    private String KEY_MONTH = "KEY_MONTH";
    private String KEY_DAY = "KEY_DAY";
    private String KEY_MINUTE = "KEY_MINUTE";
    private String KEY_HOUR = "KEY_HOUR";
    private String FRAG_TAG_DATE_PICKER = "FRAG_TAG_DATE_PICKER";
    private String FRAG_TAG_TIME_PICKER = "FRAG_TAG_TIME_PICKER";
    private String KEY_DATE_TIME_COMPLETED = "KEY_DATE_TIME_COMPLETED";

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    boolean dateTimeCompleted;

    private Button buttonDate;
    private Button buttonSave;
    private EditText etName;
    private EditText etNotes;
    private CheckBox ckPriority;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        buttonDate = findViewById(R.id.button_date_time);
        buttonSave = findViewById(R.id.button_save);
        etName = findViewById(R.id.new_task_name);
        etNotes = findViewById(R.id.new_task_notes);
        ckPriority = findViewById(R.id.priority);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar();
                calendar.add(Calendar.MINUTE, 10);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(NewTaskActivity.this)
                        .setFirstDayOfWeek(Calendar.MONDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDoneText("Yay")
                        .setCancelText("Nop");
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String notes = etNotes.getText().toString();
                boolean priority = ckPriority.isChecked();

                if (task == null) {
                    task = new Task();
                    if (getIntent().hasExtra(EXTRA_SUBLIST_ID)) {
                        int sublistId = getIntent().getIntExtra(EXTRA_SUBLIST_ID, -1);
                        task.setId_tasksublist(sublistId);
                    }
                }


                if (!name.isEmpty()) { // nie zapisuje gdy nazwa nie jest wypelniona
                    task.setName(name);
                    task.setNote(notes);
                    task.setPrioritiy(priority);

                    AppDatabase dbInstance = ((TodoApplication) getApplication()).getDbInstance();
                    dbInstance.taskDao().insert(task);
                    finish();
                }
            }
        });

        if (getIntent().hasExtra(EXTRA_TASK_ID)) {
            // EDYTOWANIE ZADANIA
            int id = getIntent().getIntExtra(EXTRA_TASK_ID, -1);
            AppDatabase dbInstance = ((TodoApplication) getApplication()).getDbInstance();
            task = dbInstance.taskDao().findById(id);
            etName.setText(task.getName());
            etNotes.setText(task.getNote());
            ckPriority.setChecked(task.getPrioritiy());

            Date reminderDate = task.getReminderDate();

            if (reminderDate != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(reminderDate);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                dateTimeCompleted = true;
                setDateOnButton();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        setDateOnButton();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            year = savedInstanceState.getInt(KEY_YEAR);
            month = savedInstanceState.getInt(KEY_MONTH);
            day = savedInstanceState.getInt(KEY_DAY);
            hour = savedInstanceState.getInt(KEY_HOUR);
            minute = savedInstanceState.getInt(KEY_MINUTE);
            dateTimeCompleted = savedInstanceState.getBoolean(KEY_DATE_TIME_COMPLETED);
        }

        if (dateTimeCompleted)
            setDateOnButton();
    }

    private void setDateOnButton() {
        String days = day > 9 ? String.valueOf(day) : "0" + day;
        String months = month + 1 > 9 ? String.valueOf(month + 1) : "0" + (month + 1);
        String minutes = minute > 9 ? String.valueOf(minute) : "0" + minute;
        String hours = hour > 9 ? String.valueOf(hour) : "0" + hour;
        if (dateTimeCompleted)
            buttonDate.setText(year + "." + months + "." + days + " " + hours + ":" + minutes);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_YEAR, year);
        outState.putInt(KEY_MONTH, month);
        outState.putInt(KEY_DAY, day);
        outState.putInt(KEY_HOUR, hour);
        outState.putInt(KEY_MINUTE, minute);
        outState.putBoolean(KEY_DATE_TIME_COMPLETED, dateTimeCompleted);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Log.d("datetime", "onDateSet: y " + year + " m " + monthOfYear + " d " + dayOfMonth);
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, 10);
        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(NewTaskActivity.this)
                .setStartTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
                .setDoneText("Yay")
                .setCancelText("Nop");
        rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        Log.d("datetime", "onTimeSet: h " + hourOfDay + " m " + minute);
        this.hour = hourOfDay;
        this.minute = minute;

        dateTimeCompleted = true;
        setDateOnButton();
    }
}

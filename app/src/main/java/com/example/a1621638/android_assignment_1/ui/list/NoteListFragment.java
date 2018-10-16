package com.example.a1621638.android_assignment_1.ui.list;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a1621638.android_assignment_1.R;
import com.example.a1621638.android_assignment_1.model.Note;
import com.example.a1621638.android_assignment_1.ui.util.DatePickerDialogFragment;
import com.example.a1621638.android_assignment_1.ui.util.NoteDataHandler;
import com.example.a1621638.android_assignment_1.ui.util.NoteItemDecoration;
import com.example.a1621638.android_assignment_1.ui.util.NoteViewAdapter;
import com.example.a1621638.android_assignment_1.ui.util.TimePickerDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View root;
    private List<Note> notes;
    private NoteViewAdapter adapter;
    private RecyclerView recyclerView;

    public NoteListFragment() {
        notes = new ArrayList<>();
        notes.addAll(NoteDataHandler.getSortedByCreation());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_note_list, container, false);
        initRecyclerView();
        initSpinner();
        return root;
    }

    public Date startReminder(final Note note) {
        final Calendar calendar = Calendar.getInstance();

        final TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.create(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        adapter.updateReminder(note, calendar.getTime());
                    }
                }
        );

        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.create(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        timePickerDialogFragment.show(getFragmentManager(), "timePicker");
                    }
                }
        );
        datePickerDialogFragment.show(getFragmentManager(), "datePicker");
        return calendar.getTime();
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.note_RecyclerView);
        adapter = new NoteViewAdapter(notes, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));

        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
        recyclerView.addItemDecoration(new NoteItemDecoration(padding));
    }

    private void initSpinner() {
        Spinner sortSpinner = root.findViewById(R.id.sort_Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.note_spinner_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        processSelection(selection);
        Toast.makeText(parent.getContext(), selection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Nothing", Toast.LENGTH_SHORT).show();
    }

    private void processSelection(String selection) {
        NoteViewAdapter adapter = (NoteViewAdapter) recyclerView.getAdapter();
        if(adapter == null) return;
        switch(selection) {
            case "Title":
                adapter.updateData(NoteDataHandler.getSortedByTitle());
                break;
            case "Creation Date":
                adapter.updateData(NoteDataHandler.getSortedByCreation());
                break;
            case "Last Modified":
                adapter.updateData(NoteDataHandler.getSortedByModified());
                break;
            case "Reminder":
                adapter.updateData(NoteDataHandler.getSortedByReminder());
                break;
            case "Category":
                adapter.updateData(NoteDataHandler.getSortedByCategories());
                break;
        }
    }
}

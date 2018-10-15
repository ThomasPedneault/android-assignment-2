package com.example.a1621638.android_assignment_1.ui.list;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1621638.android_assignment_1.R;
import com.example.a1621638.android_assignment_1.model.Note;
import com.example.a1621638.android_assignment_1.model.SampleData;
import com.example.a1621638.android_assignment_1.ui.util.NoteItemDecoration;
import com.example.a1621638.android_assignment_1.ui.util.NoteViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View root;
    private List<Note> notes;

    private RecyclerView recyclerView;

    public NoteListFragment() {
        notes = new ArrayList<>();
        notes.addAll(SampleData.getSortedByCreation());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_note_list, container, false);
        initRecyclerView();
        initSpinner();
        return root;
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.note_RecyclerView);
        NoteViewAdapter adapter = new NoteViewAdapter(notes, root.getContext());
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
                adapter.updateData(SampleData.getSortedByTitle());
                break;
            case "Creation Date":
                adapter.updateData(SampleData.getSortedByCreation());
                break;
            case "Last Modified":
                adapter.updateData(SampleData.getSortedByModified());
                break;
            case "Reminder":
                adapter.updateData(SampleData.getSortedByReminder());
                break;
            case "Category":
                adapter.updateData(SampleData.getSortedByCategories());
                break;
        }
    }
}

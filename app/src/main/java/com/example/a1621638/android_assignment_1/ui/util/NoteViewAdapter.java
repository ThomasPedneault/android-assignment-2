package com.example.a1621638.android_assignment_1.ui.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1621638.android_assignment_1.R;
import com.example.a1621638.android_assignment_1.model.Category;
import com.example.a1621638.android_assignment_1.model.Note;
import com.example.a1621638.android_assignment_1.ui.editor.NoteEditActivity;
import com.example.a1621638.android_assignment_1.ui.list.NoteListActivity;
import com.example.a1621638.android_assignment_1.ui.list.NoteListFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder> {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";

    // Contains the note information to be displayed.
    private List<Note> notes;
    private NoteListFragment fragment;
    private Context context;
    private DateFormat format;
    private ActionMode actionMode;

    public NoteViewAdapter(List<Note> notes, NoteListFragment fragment) {
        this.notes = notes;
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.layout_note,
                viewGroup,
                false
        );

        NoteViewHolder holder = new NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, final int i) {
        final Note note = notes.get(i);
        Category category = note.getCategory();
        noteViewHolder.parentLayout.setBackgroundColor(context.getColor(category.getResourceId()));
        noteViewHolder.noteTitleTextView.setText(note.getTitle());
        noteViewHolder.noteBodyTextView.setText(note.getBody());
        if(note.isHasReminder()) {
            noteViewHolder.noteReminderTextView.setText(format.format(note.getReminder()));
            noteViewHolder.noteReminderTextView.setVisibility(View.VISIBLE);
        } else {
            noteViewHolder.noteReminderTextView.setVisibility(View.GONE);
        }

        noteViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent intent = new Intent(context, NoteEditActivity.class);
                NoteDataHandler.selectedId = notes.get(i).getId();
                c.startActivity(intent);
            }
        });

        noteViewHolder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(actionMode != null) {
                    return false;
                }

                actionMode = v.startActionMode(new ActionMode.Callback2() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.menu_floating, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.reminder_MenuItem:
                                fragment.startReminder(notes.get(i));
                                mode.finish();
                                return true;
                            case R.id.trash_MenuItem:
                                NoteDataHandler.removeNote(notes.get(i));
                                updateData(NoteDataHandler.getNotes());
                                Toast.makeText(context, "Removed " + notes.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                                mode.finish();
                                return true;
                            case R.id.close_MenuItem:
                                mode.finish();
                                return true;
                            default:
                                return false;

                        }
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode = null;
                    }

                }, ActionMode.TYPE_FLOATING);

                return true;
            }
        });
    }

    public void updateReminder(Note note, Date date) {
        NoteDataHandler.updateReminder(note, date);
        updateData(NoteDataHandler.getNotes());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateData(List<Note> data) {
        notes.clear();
        notes.addAll(data);
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTextView;
        TextView noteBodyTextView;
        TextView noteReminderTextView;
        ConstraintLayout parentLayout;

        public NoteViewHolder(View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitle_TextView);
            noteBodyTextView = itemView.findViewById(R.id.noteBody_TextView);
            noteReminderTextView = itemView.findViewById(R.id.noteReminder_TextView);
            parentLayout = itemView.findViewById(R.id.noteLayout_ConstraintLayout);
        }
    }
}

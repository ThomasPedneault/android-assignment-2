package com.example.a1621638.android_assignment_1.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1621638.android_assignment_1.sqlite.DatabaseException;
import com.example.a1621638.android_assignment_1.sqlite.Table;

import java.text.ParseException;

public class NoteTable extends Table<Note> {
    /**
     * Create a database table
     *
     * @param dbh  the handler that connects to the sqlite database.
     * @param name the table name.
     */
    public NoteTable(SQLiteOpenHelper dbh, String name) {
        super(dbh, name);
    }

    @Override
    public ContentValues toContentValues(Note element) throws DatabaseException {
        ContentValues values = new ContentValues();

        values.put("noteId", element.getId());

        values.put("title", element.getTitle());
        values.put("body", element.getBody());
        values.put("category", element.getCategory().getColorId());

        values.put("hasReminder", element.isHasReminder() ? 1 : 0);

        if(element.getReminder() != null) {
            values.put("reminder", DATE_FORMAT.format(element.getReminder()));
        } else {
            values.put("reminder", "");
        }

        if(element.getCreated() == null) {
            throw new DatabaseException("Created date cannot be null");
        }
        values.put("created", DATE_FORMAT.format(element.getCreated()));

        if(element.getModified() == null) {
            throw new DatabaseException("Modified date cannot be null");
        }
        values.put("modified", DATE_FORMAT.format(element.getModified()));

        return values;
    }

    @Override
    public Note fromCursor(Cursor cursor) throws DatabaseException {
        String[] columns = getSelectAll();
        int[] indices = new int[columns.length];
        for(int i = 0; i < columns.length; i++) {
            indices[i] = cursor.getColumnIndex(columns[i]);
        }

        cursor.moveToFirst();
        if(cursor.isAfterLast()) throw new DatabaseException("No records returned");

        Note note = new Note();
        note.setId(cursor.getLong(indices[0]));
        note.setTitle(cursor.getString(indices[1]));
        note.setBody(cursor.getString(indices[2]));
        note.setCategory(Category.fromColorId(cursor.getInt(indices[3])));
        note.setHasReminder(cursor.getInt(indices[4]) == 1);
        try {
            note.setReminder(DATE_FORMAT.parse(cursor.getString(indices[5])));
            note.setCreated(DATE_FORMAT.parse(cursor.getString(indices[6])));
            note.setModified(DATE_FORMAT.parse(cursor.getString(indices[7])));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return note;
    }

    @Override
    public boolean hasInitialData() {
        return super.hasInitialData();
    }

    @Override
    public void initialize(SQLiteDatabase database) {
        super.initialize(database);
    }
}

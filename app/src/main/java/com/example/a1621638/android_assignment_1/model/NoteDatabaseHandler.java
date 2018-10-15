package com.example.a1621638.android_assignment_1.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1621638.android_assignment_1.sqlite.Column;

public class NoteDatabaseHandler extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "NotesTable";

    private static NoteTable table;

    public NoteDatabaseHandler(Context context) {
        super(context, "notes.db", null, 2);
        table = new NoteTable(this, TABLE_NAME);
        table.addColumn(new Column("title", Column.Type.TEXT).notNull());
        table.addColumn(new Column("body", Column.Type.TEXT));
        table.addColumn(new Column("category", Column.Type.INTEGER));
        table.addColumn(new Column("hasReminder", Column.Type.INTEGER));
        table.addColumn(new Column("reminder", Column.Type.TEXT));
        table.addColumn(new Column("created", Column.Type.TEXT));
        table.addColumn(new Column("modified", Column.Type.TEXT));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table.getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(table.getDropTableStatement());
        onCreate(db);
    }

    public NoteTable getNoteTable() {
        return table;
    }
}

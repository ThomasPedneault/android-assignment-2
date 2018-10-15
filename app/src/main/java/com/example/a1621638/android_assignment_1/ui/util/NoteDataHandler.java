package com.example.a1621638.android_assignment_1.ui.util;

import android.content.Context;

import com.example.a1621638.android_assignment_1.model.Category;
import com.example.a1621638.android_assignment_1.model.Note;
import com.example.a1621638.android_assignment_1.model.NoteDatabaseHandler;
import com.example.a1621638.android_assignment_1.sqlite.DatabaseException;

import java.sql.Timestamp;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NoteDataHandler {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static long selectedId;
    public static Context context;

    private static NoteDatabaseHandler dbh;

    private static List<Note> notes;

    public static List<Note> getNotes() {
        if(notes == null) {
            notes = readNotesFromDatabase();
        }
        return notes;
    }

    public static List<Note> getSortedByCategories() {
        Collections.sort(getNotes(), new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return o1.getCategory().compareTo(o2.getCategory());
            }
        });

        return notes;
    }

    public static List<Note> getSortedByCreation() {
        Collections.sort(getNotes(), new Comparator<Note>() {
            @Override
            public int compare(Note note, Note t1) {
                return note.getCreated().compareTo(t1.getCreated());
            }
        });
        return notes;
    }

    public static List<Note> getSortedByTitle() {
        final Collator usCollator = Collator.getInstance(Locale.US);
        usCollator.setStrength(Collator.PRIMARY);
        Collections.sort(getNotes(), new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return usCollator.compare(o1.getTitle(), o2.getTitle());
            }
        });
        return notes;
    }

    public static List<Note> getSortedByModified() {
        Collections.sort(getNotes(), new Comparator<Note>() {
            @Override
            public int compare(Note note, Note t1) {
                return note.getModified().compareTo(t1.getModified());
            }
        });
        return notes;
    }

    public static List<Note> getSortedByReminder() {
        List<Note> withReminders = new ArrayList<>();
        List<Note> noReminders = new ArrayList<>();
        for(Note note : getNotes()) {
            if(note.isHasReminder())
                withReminders.add(note);
            else
                noReminders.add(note);
        }
        Collections.sort(withReminders, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return o2.getReminder().compareTo(o1.getReminder());
            }
        });
        notes.clear();
        notes.addAll(withReminders);
        notes.addAll(noReminders);
        return notes;
    }

    public static void removeNote(Note note) {
        notes.remove(note);
    }

    public static void updateReminder(Note note, Date date) {
        note.setReminder(date);
        note.setHasReminder(true);
    }

    private static List<Note> createRandomNotes() {
        List<Note> notes = new ArrayList<>();
        Random rng = new Random();
        for(int i = 0; i < 100; i++) {
            Note note = new Note(i);
            StringBuilder builder = new StringBuilder();
            for(int j = 0; j < 10; j++) {
                int character = (int)(Math.random() * ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
            note.setTitle(builder.toString());
            note.setCategory(Category.values()[rng.nextInt(8)]);
            note.setBody("FIJWEAOIFJAOIWEFJAPIOWEFJWAOPJEFAOPJDSFLK;WAEFJIOQPJ");
            note.setCreated(getRandomDate());
            note.setModified(getRandomDate());
            int willRemind = rng.nextInt(2);
            if(willRemind == 0) {
                note.setHasReminder(true);
                note.setReminder(getRandomDate());
            }
            notes.add(note);
        }
        return notes;
    }

    private static List<Note> readNotesFromDatabase() {
        if(dbh == null) {
            dbh = new NoteDatabaseHandler(context);
        }
        try {
            notes = dbh.getNoteTable().readAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return notes;
    }

    private static Date getRandomDate() {
        long begin = Timestamp.valueOf("2018-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2018-12-31 23:59:59").getTime();
        return new Date(begin + (long) (Math.random() * (end - begin + 1)));
    }
}

package com.example.a1621638.android_assignment_1.model;

import java.sql.Timestamp;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SampleData {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static long selectedId = 0;
    private static List<Note> notes;

    private static List<Note> getNotes() {
        if(notes == null) {
            notes = new ArrayList<>();
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
        }
        return notes;
    }

    public static List<Note> getSortedByCategories() {
        List<Note> sortedNotes = new ArrayList<>();

        for(Category cat : Category.values()) {
            for(Note note : getNotes()) {
                if(note.getCategory() == cat) {
                    sortedNotes.add(note);
                }
            }
        }

        return sortedNotes;
    }

    public static List<Note> getSortedByCreation() {
        List<Note> sortedNotes = new ArrayList<>();
        sortedNotes.addAll(getNotes());
        Collections.sort(sortedNotes, new Comparator<Note>() {
            @Override
            public int compare(Note note, Note t1) {
                return note.getCreated().compareTo(t1.getCreated());
            }
        });
        return sortedNotes;
    }

    public static List<Note> getSortedByTitle() {
        List<Note> sortedNotes = new ArrayList<>();
        sortedNotes.addAll(getNotes());
        final Collator usCollator = Collator.getInstance(Locale.US);
        usCollator.setStrength(Collator.PRIMARY);
        Collections.sort(sortedNotes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return usCollator.compare(o1.getTitle(), o2.getTitle());
            }
        });
        return sortedNotes;
    }

    public static List<Note> getSortedByModified() {
        List<Note> sortedNotes = new ArrayList<>();
        sortedNotes.addAll(getNotes());
        Collections.sort(sortedNotes, new Comparator<Note>() {
            @Override
            public int compare(Note note, Note t1) {
                return note.getCreated().compareTo(t1.getCreated());
            }
        });
        return sortedNotes;
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
        withReminders.addAll(noReminders);
        return withReminders;
    }

    private static Date getRandomDate() {
        long begin = Timestamp.valueOf("2018-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2018-12-31 23:59:59").getTime();
        return new Date(begin + (long) (Math.random() * (end - begin + 1)));
    }
}

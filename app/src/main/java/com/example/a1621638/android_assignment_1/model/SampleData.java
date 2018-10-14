package com.example.a1621638.android_assignment_1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SampleData {

    public static long selectedId = 0;
    private static List<Note> notes;

    public static List<Note> getNotes() {
        if(notes == null) {
            notes = new ArrayList<>();
            Random rng = new Random();
            for(int i = 0; i < 50; i++) {
                Note note = new Note(i);
                note.setTitle("This is note number: " + (i + 1));
                note.setCategory(Category.values()[rng.nextInt(8)]);
                note.setBody("This is the body of my note which is pretty neat if you ask me");
                int willRemind = rng.nextInt(2);
                if(willRemind == 0) {
                    note.setHasReminder(true);
                    note.setReminder(new Date());
                }
                notes.add(note);
            }
        }
        return notes;
    }

    public static List<Note> getSortedByCategories() {
        List<Note> sortedNotes = new ArrayList<>();

        for(Category cat : Category.values()) {
            for(Note note : notes) {
                if(note.getCategory() == cat) {
                    sortedNotes.add(note);
                }
            }
        }

        return sortedNotes;
    }
}

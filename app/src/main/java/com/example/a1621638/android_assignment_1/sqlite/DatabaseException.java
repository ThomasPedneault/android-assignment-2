package com.example.a1621638.android_assignment_1.sqlite;

import java.text.ParseException;

/**
 * Represents a database error.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class DatabaseException extends Throwable {
    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(Exception e) {
        super(e);
    }
}

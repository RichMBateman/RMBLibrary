package com.bateman.rich.rmblibrary.persistence;

import android.database.sqlite.SQLiteDatabase;

/**
 * Reusable class for helping with creating database functions.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AppDatabaseHelper {

    /**
     * A constant to use when referring to TRUE values in SQLite.  SQLite does have a boolean type,
     * which just uses 0 and 1.
     */
    public static final int SQLITE_INT_TRUE = 1;
    /**
     * A constant to use when referring to FALSE values in SQLite.  SQLite does have a boolean type,
     * which just uses 0 and 1.
     */
    public static final int SQLITE_INT_FALSE = 0;

    /**
     * Executes a Create Trigger statement that, on deleting rows from the parent table, all child
     * rows will be deleted that the specified foreign key matches on the parent's primary key.
     * @param db The database
     * @param triggerName The name of the trigger.
     * @param parentTableName The name of the table from which we are deleting records
     * @param childTableName The child table that will have its referring rows deleted.
     * @param parentIdColName The name of the parent table's id column
     * @param childFkIdColName The name of the child table's foreign key id column
     */
    public static void createTriggerOnDeleteParentRecord(SQLiteDatabase db, String triggerName, String parentTableName, String childTableName,
                                                         String parentIdColName, String childFkIdColName) {
        String sSqlStatement = "CREATE TRIGGER " + triggerName
                + " AFTER DELETE ON " + parentTableName
                + " FOR EACH ROW"
                + " BEGIN"
                + " DELETE FROM " + childTableName
                + " WHERE " + childTableName + "." + childFkIdColName + " = OLD." + parentIdColName + ";"
                + " END;";
        db.execSQL(sSqlStatement);
    }
}

package todos.com.todos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lavinaramnani on 3/17/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todosapp.db";
    private static final int DATABASE_VERSION = 1;

    //Constant for Creating Categories Entry Table
    private static final String TABLE_CATEGORIES_CREATE = "CREATE TABLE " + TodoContract.CategoriesEntry.TABLE_NAME +
            " (" + TodoContract.CategoriesEntry._ID + " INTEGER PRIMARY KEY, "+
            TodoContract.CategoriesEntry.COLUMN_DESCRIPTION + " TEXT )";

    //Constant for Creating Todos Entry Table
    private static final String TABLE_TODOS_CREATE = "CREATE TABLE " + TodoContract.TodosEntry.TABLE_NAME +
            " (" + TodoContract.TodosEntry._ID + " INTEGER PRIMARY KEY, "
            + TodoContract.TodosEntry.COLUMN_CATEGORY + " INTEGER, "
            + TodoContract.TodosEntry.COLUMN_CREATED + " TEXT default CURRENT_TIMESTAMP, "
            + TodoContract.TodosEntry.COLUMN_TEXT + " TEXT, "
            + TodoContract.TodosEntry.COLUMN_EXPIRED + " TEXT, "
            + TodoContract.TodosEntry.COLUMN_DONE + " INTEGER, "
            + "FOREIGN KEY (" + TodoContract.TodosEntry.COLUMN_CATEGORY + ") REFERENCES "
            + TodoContract.CategoriesEntry.TABLE_NAME
            + "(" + TodoContract.CategoriesEntry._ID + ") "+")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORIES_CREATE);
        db.execSQL(TABLE_TODOS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TodoContract.TodosEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TodoContract.CategoriesEntry.TABLE_NAME);
        onCreate(db);
    }
}

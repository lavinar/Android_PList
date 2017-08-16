package todos.com.todos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import todos.com.todos.data.DatabaseHelper;
import todos.com.todos.data.TodoContract;

public class TodoListActivity extends AppCompatActivity {

    String[] itemname ={
            "Get theatre tickets",
            "Order pizza for tonight",
            "Buy groceries",
            "Running session at 19.30",
            "Call Uncle Sam"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseHelper helper = new DatabaseHelper(this);
        //SQLiteDatabase db = helper.getReadableDatabase();

        //createRecord();
        getRecords();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView lv=(ListView) findViewById(R.id.lvTodos);
//adds the custom layout
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.todo_list_item,
                R.id.tvNote,itemname));
//adds the click event to the listView, reading the content
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = new Intent(TodoListActivity.this, TodoActivity.class);
                String content= (String) lv.getItemAtPosition(pos);
                intent.putExtra("Content", content);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void createRecord(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String insertQuery = "INSERT INTO todos (" +
                TodoContract.TodosEntry.COLUMN_CATEGORY +"," +
                TodoContract.TodosEntry.COLUMN_DONE+"," +
                TodoContract.TodosEntry.COLUMN_TEXT+"," +
                TodoContract.TodosEntry.COLUMN_CREATED+"," +
                TodoContract.TodosEntry.COLUMN_EXPIRED+") values (1,0,\"Call Mr Bean\",\"03\19\2017\",\"No\")";

        db.execSQL(insertQuery);


        ContentValues values = new ContentValues();
        values.put(TodoContract.TodosEntry.COLUMN_CATEGORY,"1");
        values.put(TodoContract.TodosEntry.COLUMN_DONE,"0");
        values.put(TodoContract.TodosEntry.COLUMN_TEXT, "Do grocery shopping!!");
        values.put(TodoContract.TodosEntry.COLUMN_CREATED,"03\19\2017");
        values.put(TodoContract.TodosEntry.COLUMN_EXPIRED,"No");

        long id = db.insert(TodoContract.TodosEntry.TABLE_NAME,null, values);
        Log.d("Insert value", ""+id);
    }

    private void getRecords(){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projections = new String[]{TodoContract.TodosEntry.COLUMN_EXPIRED
        String[] projections = new String[]{TodoContract.TodosEntry.COLUMN_EXPIRED
                , TodoContract.TodosEntry.COLUMN_CREATED
                , TodoContract.TodosEntry.COLUMN_TEXT
                , TodoContract.TodosEntry.COLUMN_CATEGORY
                , TodoContract.TodosEntry.COLUMN_DONE};

        String selections = TodoContract.TodosEntry.COLUMN_CATEGORY +"= ?";

        String[] selectionArgs = new String[] {"1"};

        Cursor c = db.query(TodoContract.TodosEntry.TABLE_NAME,projections,selections, selectionArgs,null,null,null);

        Log.d("Record Count:",""+c.getCount());

    }
}
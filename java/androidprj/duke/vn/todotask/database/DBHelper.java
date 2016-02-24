package androidprj.duke.vn.todotask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidprj.duke.vn.todotask.model.ModelTodo;

/**
 * Created by datnt on 2/23/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TodoTask.db";
    public static final String TODO_TABLE_NAME = "todo";
    public static final String TODO_COLUMN_ID = "id";
    public static final String TODO_COLUMN_NAME = "name";
    public static final String TODO_COLUMN_PRIORITY = "priority";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table todo (id text primary key, name text,priority integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(sqLiteDatabase);
    }

    public boolean insertTodo(String id, String name, int priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("priority", priority);
        db.insert("todo", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from todo where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TODO_TABLE_NAME);
        return numRows;
    }

    public boolean updateTodo(String id, String name, int priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("priority", priority);
        db.update("todo", contentValues, "id = ?", new String[]{id});
//        db.update("todo", contentValues, "id = " + id, null);
        return true;
    }

    public Integer deleteTodo(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("todo", "id = ?", new String[]{id});
    }

    public ArrayList<ModelTodo> getAllTodos() {
        ArrayList<ModelTodo> array_list = new ArrayList<ModelTodo>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from todo", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            ModelTodo modelTodo = new ModelTodo();
            modelTodo.setId(res.getString(res.getColumnIndex(TODO_COLUMN_ID)));
            modelTodo.setName(res.getString(res.getColumnIndex(TODO_COLUMN_NAME)));
            modelTodo.setPriority(res.getInt(res.getColumnIndex(TODO_COLUMN_PRIORITY)));
            array_list.add(modelTodo);
            res.moveToNext();
        }
        return array_list;
    }
}
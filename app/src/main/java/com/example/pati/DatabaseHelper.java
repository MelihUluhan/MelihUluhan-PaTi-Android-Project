package com.example.pati;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pativ2.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_POST = "post";
    private static final String POST_COLUMN_ID = "id";
    private static final String POST_COLUMN_PHONE_NUM = "phone_number";
    private static final String POST_COLUMN_IMAGE = "image";
    private static final String POST_COLUMN_ADDRESS = "address";
    private static final String POST_COLUMN_EXP = "exp";
    private static final String POST_COLUMN_IS_LOST = "is_lost";
    private static final String POST_COLUMN_SENDER = "sender";
    private String loggedInUsername = "username";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static DatabaseHelper instance;

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPostTableQuery = "CREATE TABLE " + TABLE_POST + " (" +
                POST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                POST_COLUMN_PHONE_NUM + " TEXT," +
                POST_COLUMN_IMAGE + " BLOB," +
                POST_COLUMN_ADDRESS + " TEXT," +
                POST_COLUMN_EXP + " TEXT," +
                POST_COLUMN_IS_LOST + " INTEGER, " +
                POST_COLUMN_SENDER + " TEXT)";
        db.execSQL(createPostTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        onCreate(db);
    }


    public long addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(POST_COLUMN_PHONE_NUM, post.phoneNumber);
        values.put(POST_COLUMN_IMAGE, post.image);
        values.put(POST_COLUMN_ADDRESS, post.address);
        values.put(POST_COLUMN_EXP, post.explanation);
        values.put(POST_COLUMN_IS_LOST, post.isLost ? 1 : 0);
        values.put(POST_COLUMN_SENDER, loggedInUsername);


        long postID = db.insert(TABLE_POST, null, values);

        return postID;
    }

    @SuppressLint("Range")
    public ArrayList<Post> getAllPosts(boolean isLost) {
        ArrayList<Post> postList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_POST + " ORDER BY " + POST_COLUMN_ID + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null, null);

        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.phoneNumber = cursor.getString(cursor.getColumnIndex(POST_COLUMN_PHONE_NUM));
                post.image = cursor.getBlob(cursor.getColumnIndex(POST_COLUMN_IMAGE));
                post.address = cursor.getString(cursor.getColumnIndex(POST_COLUMN_ADDRESS));
                post.explanation = cursor.getString(cursor.getColumnIndex(POST_COLUMN_EXP));
                post.isLost = cursor.getInt(cursor.getColumnIndex(POST_COLUMN_IS_LOST)) == 1;
                post.sender = cursor.getString(cursor.getColumnIndex(POST_COLUMN_SENDER));

                if (post.isLost == isLost) {
                    postList.add(post);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return postList;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }
}
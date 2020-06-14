package com.example.dicodingapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.dicodingapp.db.FavoriteDatabase;

import java.util.HashMap;

import static com.example.dicodingapp.db.FavoriteDatabase.KEY_ID;
import static com.example.dicodingapp.db.FavoriteDatabase.TABLE_NAME;

public class FavoriteProvider extends ContentProvider {

    private SQLiteDatabase db;

    static final int FAVORITE = 1;
    static final int FAVORITE_ID = 2;

    static final String PROVIDER_NAME = "com.example.dicodingapp";

    static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    static final String URL_ID = "content://" + PROVIDER_NAME + "/" + TABLE_NAME + "/" + KEY_ID;

    public static final Uri CONTENT_URI = Uri.parse(URL);
    public static final Uri CONTENT_URI_ID = Uri.parse(URL_ID);

    private static final UriMatcher uriMatcher;

    private static HashMap<String, String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME, FAVORITE);
        uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME + "/*", FAVORITE_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        FavoriteDatabase database = new FavoriteDatabase(context);
        db = database.getWritableDatabase();
        if (db != null){
            return true;
        }
        return false;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                return "vnd.android.cursor.dir/favorites";
            case FAVORITE_ID:
                return "vnd.android.cursor.item/favorites";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null,
                null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME, null, values);
        try {
            if (rowID > 0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
        return uri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                //do nothing
                break;
            case FAVORITE_ID:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

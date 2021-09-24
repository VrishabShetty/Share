package com.example.shareapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

public class DataInfo {

    private Uri mUri;
            private Context mContext;

            DataInfo(Uri uri, Context context){
                mUri = uri;
                mContext = context.getApplicationContext();
            }

            public String getName() {
                String name = null;

                // "query" has a default implementation which return DISPLAY_NAME, SIZE
                Cursor cursor = mContext.getContentResolver().query(mUri,null,null,null,null);

                try {
            if(cursor.getColumnCount() > 0) {
                cursor.moveToFirst();

                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

                name = cursor.getString(nameIndex);
            }
        }finally {
            cursor.close();
        }
        return name;
    }

    public String getType() {
        return mContext.getContentResolver().getType(mUri);
    }

    public Uri getUri() {
        return mUri;
    }
}

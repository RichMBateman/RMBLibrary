package com.bateman.rich.rmblibrary.persistence;


import android.content.ContentUris;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Reeusable helper class when working with ContentProviders and Uris.
 * https://developer.android.com/guide/topics/providers/content-provider-basics
 *
 * Usage Examples:
 * >You want to define a Contract (for a database table)
 *  public static class Contract {
 *         public static final String TABLE_NAME = "DaySchedules";
 *         public static final Uri CONTENT_URI = ContentProviderHelper.buildContentUri(ExerciseAppProvider.CONTENT_AUTHORITY_URI, TABLE_NAME);
 *         public static final String CONTENT_TYPE = ContentProviderHelper.buildContentTypeString(ExerciseAppProvider.CONTENT_AUTHORITY, TABLE_NAME);
 *         public static final String CONTENT_ITEM_TYPE = ContentProviderHelper.buildContentItemTypeString(ExerciseAppProvider.CONTENT_AUTHORITY, TABLE_NAME);
 *
 *         private Contract() {} // prevent instantiation
 *
 *         public static class Columns {
 *             public static final String COL_NAME_ID = BaseColumns._ID;
 *             public static final String COL_NAME_EXERCISE_ENTRY_ID = "ExerciseEntryId";
 *             public static final String COL_NAME_POSITION = "Position";
 *             public static final String COL_NAME_IS_DAY_SEPARATOR = "IsDaySeparator";
 *
 *             private Columns() {  } // private constructor; no instantiation allowed
 *         }
 *   }
 *
 * >You want to provide a URI to a UriMatcher (needed for your custom AppProvider)
 *      matcher.addURI(CONTENT_AUTHORITY, ExerciseEntry.Contract.TABLE_NAME, EXERCISE_ENTRIES);
 *
 * >You want to provide a URI to perform some kind of CRUD operation.
 *      getContentResolver().delete(ContentProviderHelper.buildUriFromId(ExerciseEntry.Contract.CONTENT_URI, exerciseId), null, null);
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ContentProviderHelper {
    private static final String TAG = "ContentProviderHelper";
    private static final String CONTENT_AUTHORITY_URI_PREFIX = "content://";
    private static final String PATH_ID_SEPARATOR = "/#";
    private static final String CONTENT_TYPE_PREFIX = "vnd.android.cursor.dir/vnd.";
    private static final String CONTENT_ITEM_TYPE_PREFIX = "vnd.android.cursor.item/vnd.";
    private static final String CONTENT_TYPE_TABLE_PREFIX = ".";

    /**
     * Given a content authority string (which seems like a value that represent your app, to distinguish it
     * from other apps, so your package name seems valid, like "com.bateman.rich.exercisetrack".
     * @param contentAuthority A string representing the Content Authority name.
     * @return A Uri representing the content authority.
     */
    public static Uri buildContentAuthorityUri(@NonNull String contentAuthority) {
        Log.d(TAG, "buildContentAuthorityUri: start");
        String uriStr = CONTENT_AUTHORITY_URI_PREFIX + contentAuthority;
        Log.d(TAG, "buildContentAuthorityUri: Parsing: " + uriStr);
        Uri contentAuthorityUri = Uri.parse(uriStr);
        if(contentAuthorityUri == null) throw new IllegalStateException("A null contentAuthorityUri was returned from Uri.parse.");
        return contentAuthorityUri;
    }

    /**
     * builds a content uri.
     */
    public static Uri buildContentUri(@NonNull Uri contentAuthorityUri, @NonNull String tableName) {
        return Uri.withAppendedPath(contentAuthorityUri, tableName);
    }

    public static String buildContentTypeString(@NonNull String contentAuthority, @NonNull String tableName) {
        return CONTENT_TYPE_PREFIX + contentAuthority + CONTENT_TYPE_TABLE_PREFIX + tableName;
    }

    public static String buildContentItemTypeString(String contentAuthority, String tableName) {
        return CONTENT_ITEM_TYPE_PREFIX + contentAuthority + CONTENT_TYPE_TABLE_PREFIX + tableName;
    }

    /**
     * Creates a URI that represents some table and will be used in conjunction with an id.
     * This function basically just appends a "/#" to the table name supplied.
     */
    public static String buildUriPathForId(String tableName) {
        return tableName + PATH_ID_SEPARATOR;
    }

    /**
     * Given a URI, returns the id.
     * Useful for retrieving the generated id from an insert statement.
     */
    public static long getId(Uri uri) {return ContentUris.parseId(uri);}

    /**
     * Builds a URI given a Content URI and an id.
     */
    public static Uri buildUriFromId(Uri contentUri, long id) {return ContentUris.withAppendedId(contentUri, id);}
}

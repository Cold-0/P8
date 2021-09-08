package com.cold0.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.annotation.Nullable
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.database.EstateDatabase


class EstateContentProvider : ContentProvider() {

	companion object {
		// FOR DATA
		const val AUTHORITY = "com.cold0.realestatemanager.provider"
		val TABLE_NAME = Estate::class.java.simpleName
		val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
	}

	override fun onCreate(): Boolean {
		return true
	}

	@Nullable
	override fun query(
		uri: Uri,
		@Nullable projection: Array<String?>?,
		@Nullable selection: String?,
		@Nullable selectionArgs: Array<String?>?,
		@Nullable sortOrder: String?,
	): Cursor {
		if (context != null) {
			val userId = ContentUris.parseId(uri)
			val cursor: Cursor = EstateDatabase.getInstance(context!!).estateDao().getCursor(userId)
			cursor.setNotificationUri(context!!.contentResolver, uri)
			return cursor
		}
		throw IllegalArgumentException("Failed to query row for uri $uri")
	}

	@Nullable
	override fun getType(uri: Uri): String {
		return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
	}

	@Nullable
	override fun insert(uri: Uri, @Nullable contentValues: ContentValues?): Uri {
		if (context != null) {
			val id: Long = EstateDatabase.getInstance(context!!).estateDao().insert(Estate.fromContentValues(contentValues))
			context!!.contentResolver.notifyChange(uri, null)
			return ContentUris.withAppendedId(uri, id)
		}
		throw IllegalArgumentException("Failed to insert row into $uri")
	}

	override fun delete(uri: Uri, @Nullable s: String?, @Nullable strings: Array<String?>?): Int {
		if (context != null) {
			val count = EstateDatabase.getInstance(context!!).estateDao().delete(ContentUris.parseId(uri))
			context!!.contentResolver.notifyChange(uri, null)
			return count
		}
		throw IllegalArgumentException("Failed to delete row into $uri")
	}

	override fun update(uri: Uri, @Nullable contentValues: ContentValues?, @Nullable s: String?, @Nullable strings: Array<String?>?): Int {
		if (context != null) {
			val count: Int = EstateDatabase.getInstance(context!!).estateDao().update(Estate.fromContentValues(contentValues))
			context!!.contentResolver.notifyChange(uri, null)
			return count
		}
		throw IllegalArgumentException("Failed to update row into $uri")
	}
}
package com.cold0.realestatemanager.model

import androidx.room.Entity

@Entity
data class Photo(
    val name: String = "",
    val url: String = "",
    var data: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (name != other.name) return false
        if (url != other.url) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data.contentEquals(other.data)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + (data?.contentHashCode() ?: 0)
        return result
    }
}
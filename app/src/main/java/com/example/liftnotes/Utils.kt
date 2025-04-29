package com.example.liftnotes

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.Serializable

object Utils {

    inline fun <reified T : Serializable> SavedStateHandle.navArgs(): T =
        checkNotNull(this["args"]) { "Missing navigation arguments" }
}
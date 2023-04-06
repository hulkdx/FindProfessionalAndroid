package com.hulkdx.findprofessional.common.config.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull

fun DataStore<Preferences>.getFlowAsString(key: String) =
    data.map { it[stringPreferencesKey(key)] }

suspend fun DataStore<Preferences>.getAsString(key: String) =
    getFlowAsString(key).firstOrNull()

suspend fun DataStore<Preferences>.set(key: String, value: String) {
    edit { it[stringPreferencesKey(key)] = value }
}

suspend fun DataStore<Preferences>.remove(key: String) {
    edit { it.remove(stringPreferencesKey(key)) }
}

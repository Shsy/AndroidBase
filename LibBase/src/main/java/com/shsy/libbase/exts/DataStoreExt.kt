package com.shsy.libbase.exts

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * @ClassName DataStoreExt
 * @Description DataStore 扩展
 * @Author 嘟嘟侠
 * @Date 2021/10/29 10:11 上午
 * @Version 1.0
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = appContext.packageName + "_preferences"
)
val dataStore = appContext.dataStore
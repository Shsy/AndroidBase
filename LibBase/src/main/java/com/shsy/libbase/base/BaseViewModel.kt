package com.shsy.libbase.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @ClassName BaseViewModel
 * @Description BaseViewModel
 * @Author 嘟嘟侠
 * @Date 2021/10/28 3:29 下午
 * @Version 1.0
 */
open class BaseViewModel : ViewModel() {

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T): T {
        return withContext(Dispatchers.IO) {
            block()
        }
    }

    sealed class UiState<out T> {
        object None : UiState<Nothing>()
        object Loading : UiState<Nothing>()
        class Error(val errMsg: String) : UiState<Nothing>()
        class Success<T>(val data: T) : UiState<T>()
    }
}
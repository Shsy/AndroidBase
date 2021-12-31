package com.shsy.libbase.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @ClassName BaseRepository
 * @Description Repository 基类
 * @Author 嘟嘟侠
 * @Date 2021/11/1 10:17 上午
 * @Version 1.0
 */
open class BaseRepository {

    protected fun <T> commonFlow(
        block: suspend FlowCollector<BaseViewModel.UiState<T>>.() -> Unit
    ): Flow<BaseViewModel.UiState<T>> {

        return flow {
            block.invoke(this)
        }.onStart {
            emit(BaseViewModel.UiState.Loading)
        }.flowOn(Dispatchers.IO)
            .catch {
                it.printStackTrace()
                emit(BaseViewModel.UiState.Error("系统错误，请联系管理员"))
            }
    }

    protected fun errorState(errMsg: String): BaseViewModel.UiState.Error {
        return BaseViewModel.UiState.Error(errMsg)
    }

    protected fun <T> successState(successData: T): BaseViewModel.UiState.Success<T> {
        return BaseViewModel.UiState.Success(successData)
    }
}
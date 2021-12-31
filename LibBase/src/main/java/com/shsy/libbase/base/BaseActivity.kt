package com.shsy.libbase.base

import android.os.Bundle
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.drake.statusbar.darkMode
import com.drake.statusbar.statusBarColorRes
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.shsy.libbase.R
import com.shsy.libbase.exts.doClick
import com.shsy.libbase.exts.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @ClassName BaseActivity
 * @Description 基Activity
 * @Author 嘟嘟侠
 * @Date 2021/10/28 3:46 下午
 * @Version 1.0
 */

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes val layoutId: Int) :
    AppCompatActivity() {

    protected abstract val viewModel: VM
    protected val viewDataBinding: VDB by lazy {
        DataBindingUtil.inflate<VDB>(
            layoutInflater,
            layoutId,
            window.decorView.findViewById(android.R.id.content),
            false
        ).apply {
            lifecycleOwner = this@BaseActivity
        }
    }

    private var loadingDialog: LoadingPopupView? = null

    private val mErrorView by lazy {
        val errView = layoutInflater.inflate(
            R.layout.view_state_error,
            window.decorView.findViewById(android.R.id.content),
            false
        )
        errView.findViewById<Button>(R.id.bt_state_error_retry).doClick {
            initData()
        }
        errView
    }

    // 是否 延迟设置View
    protected open val delayContentView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBar()

        if (!delayContentView) {
            setContentView(viewDataBinding.root)
        }

        startObserve()
        initView()
        initData()
    }

    protected open fun initStatusBar() {
        statusBarColorRes(R.color.white)
        darkMode()
    }

    protected abstract fun startObserve()
    protected abstract fun initView()
    protected abstract fun initData()

    protected fun <T> collectUiStateFlow(
        uiStateFlow: StateFlow<BaseViewModel.UiState<T>>,
        needLoadingDialog: Boolean = true,
        onError: ((errMsg: String) -> Unit)? = null,
        successBlock: suspend CoroutineScope.(T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                uiStateFlow.collect { uiState ->
                    when (uiState) {
                        is BaseViewModel.UiState.None -> {
                        }
                        is BaseViewModel.UiState.Loading -> {
                            if (needLoadingDialog) {
                                showLoading()
                            }
                        }
                        is BaseViewModel.UiState.Error -> {
                            if (needLoadingDialog) {
                                hideLoading()
                            }
                            if (viewDataBinding.root.parent == null) {
                                setContentView(mErrorView)
                            } else {
                                uiState.errMsg.showToast()
                            }
                            onError?.invoke(uiState.errMsg)
                        }
                        is BaseViewModel.UiState.Success -> {
                            successBlock.invoke(this, uiState.data)
                            if (needLoadingDialog) {
                                hideLoading()
                            }
                            if (delayContentView && viewDataBinding.root.parent == null) {
                                setContentView(viewDataBinding.root)
                            }
                        }
                    }
                }
            }
        }
    }

    fun showLoading() {
        if (!isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = XPopup.Builder(this@BaseActivity).asLoading()
            }

            loadingDialog?.dialog?.isShowing?.let {
                if (it) loadingDialog?.dismiss()
            }

            loadingDialog?.show()
        }
    }

    fun hideLoading() {
        if (!isFinishing) {
            try {
                loadingDialog?.dismiss()
                loadingDialog = null
            } catch (e: Exception) {
                loadingDialog = null
                e.printStackTrace()
            }
        }
    }
}
package com.shsy.libbase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shsy.libbase.R
import com.shsy.libbase.exts.doClick
import com.shsy.libbase.exts.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @ClassName BaseFragment
 * @Description Fragment 基类
 * @Author 嘟嘟侠
 * @Date 2021/10/29 3:09 下午
 * @Version 1.0
 * todo stateLayout 要修改的
 */
abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes val layoutId: Int) :
    Fragment(layoutId) {

    protected abstract val viewModel: VM
    protected lateinit var viewDataBinding: VDB

    private fun <T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseFragment
    }

    private var contentViewRoot: FrameLayout? = null

    // 是否 延迟设置View
    protected open val delayContentView = true

    private val mErrorView by lazy {
        val errView = layoutInflater.inflate(
            R.layout.view_state_error,
            activity?.window?.decorView?.findViewById(android.R.id.content),
            false
        )
        errView.findViewById<Button>(R.id.bt_state_error_retry).doClick {
            initData()
        }
        errView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (contentViewRoot == null) {
            contentViewRoot = FrameLayout(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            if (!this::viewDataBinding.isInitialized) {
                viewDataBinding = binding(inflater, layoutId, container)
            }

            if (!delayContentView) {
                contentViewRoot?.removeAllViews()
                contentViewRoot?.addView(viewDataBinding.root)
            }
        }
        return contentViewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startObserve()
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun startObserve()
    abstract fun initView()
    abstract fun initData()

    protected fun <T> collectUiStateFlow(
        uiStateFlow: StateFlow<BaseViewModel.UiState<T>>,
        successBlock: suspend CoroutineScope.(T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                uiStateFlow.collect { uiState ->
                    when (uiState) {
                        is BaseViewModel.UiState.None -> {
                            hideLoading()
                        }
                        is BaseViewModel.UiState.Loading -> {
                            showLoading()
                        }
                        is BaseViewModel.UiState.Error -> {
                            hideLoading()
                            if (viewDataBinding.root.parent == null) {
                                contentViewRoot?.removeAllViews()
                                contentViewRoot?.addView(mErrorView)
                            } else {
                                uiState.errMsg.showToast()
                            }
                        }
                        is BaseViewModel.UiState.Success -> {
                            successBlock.invoke(this, uiState.data)
                            hideLoading()
                            if (viewDataBinding.root.parent == null) {
                                contentViewRoot?.removeAllViews()
                                contentViewRoot?.addView(viewDataBinding.root)
                            }
                        }
                    }
                }
            }
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        contentViewRoot?.removeAllViews()
//        contentViewRoot = null
//    }

    private fun showLoading() {
        (activity as? BaseActivity<*, *>)?.showLoading()
    }

    private fun hideLoading() {
        (activity as? BaseActivity<*, *>)?.hideLoading()
    }
}
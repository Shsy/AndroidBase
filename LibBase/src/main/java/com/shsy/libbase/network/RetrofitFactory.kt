package com.shsy.libbase.network

import com.shsy.libbase.BuildConfig
import com.shsy.libbase.exts.appContext
import com.shsy.libbase.exts.logV
import com.shsy.libbase.exts.serverBaseUrl
import com.shsy.libbase.utils.DataStoreUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @ClassName RetrofitFactory
 * @Description Retrofit工厂
 * @Author 嘟嘟侠
 * @Date 2021/10/29 10:22 上午
 * @Version 1.0
 */
object RetrofitFactory {

    private const val NET_TIME_OUT = 10L
    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(serverBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(initOkHttpClient())
            .build()
    }

    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(NET_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(NET_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(NET_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(initLogInterceptor())
            .addInterceptor(initCurrencyInterceptor())
            .build()
    }

    /**
     * 初始化网络请求日志拦截器
     */
    private fun initLogInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    /**
     * 初始化请求通用拦截器
     * 向header中添加需要参数
     */
    private fun initCurrencyInterceptor(): Interceptor {
        return Interceptor { chain ->

            val userToken = DataStoreUtil.getSyncData(DataStoreUtil.USER_TOKEN, "")

            if (BuildConfig.DEBUG) {
                userToken.logV()
            }

            val addHeaderRequest = chain.request().newBuilder()
                .addHeader("Content_Type", "application/json")
                .addHeader("charset", "UTF-8")
                .addHeader("token", userToken)
                .build()
            chain.proceed(addHeaderRequest)
        }
    }

    /**
     * 实例化服务
     * @param service Class<T>
     * @return T
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}
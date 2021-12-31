package com.shsy.libbase.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * @ClassName RetrofitFactory
 * @Description Retrofit工厂
 * @Author 嘟嘟侠
 * @Date 2021/10/29 10:25 上午
 * @Version 1.0
 */
class RequestBodyBuilder {

    private val mRequestDataMap by lazy { HashMap<String, Any?>() }

    fun addParam(k: String, v: Any?): RequestBodyBuilder {
        mRequestDataMap[k] = v
        return this
    }

    fun build(): RequestBody {
        val map = mRequestDataMap.clone() as Map<*, *>
        mRequestDataMap.clear()
        return jsonToRequestBody(Gson().toJson(map))

    }

    companion object {
        fun jsonToRequestBody(jsonStr: String): RequestBody {
            return jsonStr.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        }

        fun pictureFilePathToRequestBody(filePath: String): RequestBody {
            return File(filePath).asRequestBody("image/png".toMediaTypeOrNull())
        }
    }
}
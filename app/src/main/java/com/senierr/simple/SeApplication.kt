package com.senierr.simple

import android.app.Application
import com.senierr.sehttp.SeHttp
import com.senierr.sehttp.https.SSLFactory
import com.senierr.sehttp.util.HttpLogInterceptor

/**
 * 应用入口
 *
 * @author zhouchunjie
 * @date 2018/7/27
 */
class SeApplication : Application() {

    companion object {
        @JvmStatic lateinit var instance: SeApplication
            private set
    }

    private lateinit var seHttp: SeHttp

    override fun onCreate() {
        super.onCreate()
        instance = this

        initHttp()
    }

    /**
     * 初始化网络请求器
     */
    private fun initHttp() {
        seHttp = SeHttp.Builder()
                .setDebug("SeHttp", HttpLogInterceptor.LogLevel.BODY)
                .setConnectTimeout(10 * 1000)
                .setReadTimeout(10 * 1000)
                .setWriteTimeout(10 * 1000)
                .addCommonHeader("com_header", "com_header_value")
                .addCommonHeader("language", "English")
                .addCommonUrlParam("com_url_param", "com_url_param_value")
                .setSSLSocketFactory(SSLFactory.create())
                .setRefreshInterval(200)
                .setRetryCount(3)
                .build()
    }

    fun getHttp(): SeHttp {
        return seHttp
    }
}
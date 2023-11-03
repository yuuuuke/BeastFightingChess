package com.zhpew.beastfightingchess

import android.app.Application

/**
 * description:application
 *
 * @author zwp
 * @since 2021/6/8
 */
class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
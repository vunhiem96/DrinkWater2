package com.kolorfox.watertracker.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.kolorfox.watertracker.R

fun Activity.shareApp() {
    val linkApk = getString(R.string.base_link_apk) + applicationContext.packageName
    val appName = getString(R.string.app_name)
    Log.d("Link_Apk", linkApk)
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.run {
        putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))  // title
        putExtra(
            Intent.EXTRA_TEXT,
            "$appName : " + getString(R.string.app_name) + ": $linkApk"  // content
        )
    }
    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)))
}

fun Activity.newApp(idDeveloper: String) {
    val uri = Uri.parse("market://search?q=pub:$idDeveloper")
    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(myAppLinkToMarket)
    } catch (e: ActivityNotFoundException) {
    }
}

fun Fragment.newApp(idDeveloper: String) {
    val uri = Uri.parse("market://search?q=pub:$idDeveloper")
    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(myAppLinkToMarket)
    } catch (e: ActivityNotFoundException) {
    }
}

fun Fragment.shareApp() {
    val linkApk = getString(R.string.base_link_apk) + context!!.packageName
    val appName = getString(R.string.app_name)
    Log.d("Link_Apk", linkApk)
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.run {
        putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))  // title
        putExtra(
            Intent.EXTRA_TEXT,
            "$appName : " + getString(R.string.app_name) + ": $linkApk"  // content
        )
    }
    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)))
}
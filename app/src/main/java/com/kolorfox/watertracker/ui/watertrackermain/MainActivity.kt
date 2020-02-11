package com.kolorfox.watertracker.ui.watertrackermain

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.data.WaterDatabase
import com.kolorfox.watertracker.data.model.WaterHistory
import com.kolorfox.watertracker.data.model.WaterMonth
import com.kolorfox.watertracker.data.model.WaterWeek
import com.kolorfox.watertracker.data.model.Weight
import com.kolorfox.watertracker.fragment.DialogFirst
import com.kolorfox.watertracker.fragment.historyfragment.HistorysFragment
import com.kolorfox.watertracker.fragment.mainfragment.MainFragment
import com.kolorfox.watertracker.fragment.settingfragment.SettingsFragment
import com.kolorfox.watertracker.fragment.weightfragment.WeightsFragment
import com.kolorfox.watertracker.waterservices.Alarm
import com.kolorfox.watertracker.waterservices.MyJobService
import com.kolorfox.watertracker.waterservices.WaterTrackerService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_after_enought.*
import kotlinx.android.synthetic.main.dialog_enought_wt.*
import kotlinx.android.synthetic.main.dialog_exit.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), DialogFirst {
    lateinit var waterDB: WaterDatabase
    lateinit var mInterstitialAd: InterstitialAd
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    var checkAds = 0
    var ads = 1
//    private var loadedAd: AppLovinAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        AppLovinSdk.initializeSdk(this)
//        loadAdss()
        getAds()
        addFragmentDefault()
        updateChart()
        serviceStart()
        bottomOnclick()
        addWater()
        saveWeightToDatabase()
        addButton()
        splashGone()


    }

    private fun splashGone() {
        val dialog =  ShareReference.getFirstTimeLogin(this)
        if (dialog == true){
          splash.visibility = View.GONE
    }else{
            val handler = Handler()
            handler.postDelayed({ splash.visibility = View.GONE }, 3500)}

    }


    private fun addButton() {
        val dialog = ShareReference.getFirstTimeLogin(this)
        if (dialog == true) {
            btn_add.visibility = View.INVISIBLE
        } else {
            btn_add.visibility = View.VISIBLE
        }
    }

    private fun getAds() {

        val dialog = ShareReference.getFirstTimeLogin(this)
        checkAds = ShareReference.getCheckAds(this)!!.toInt()
        val checkAdsNew = checkAds + 1
        ShareReference.setCheckAds(checkAdsNew, this)
        if (dialog == false) {
            val idApp = getString(R.string.app_id)
            val idInterstitialAds = getString(R.string.interstitial_ads_id)



            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd.adUnitId = idInterstitialAds
            mInterstitialAd.loadAd(AdRequest.Builder().build())

            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    Log.i("hahahahahah","load")

                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    Log.i("hahahahahah","fail")
                }

                override fun onAdOpened() {

                }

                override fun onAdClicked() {

                }

                override fun onAdLeftApplication() {

                }

                override fun onAdClosed() {
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }
        }
    }


    private fun updateChart() {
        waterDB = WaterDatabase(this)
        val c = Calendar.getInstance()
        val sdf2 = SimpleDateFormat("dd")
        val currentDate2 = sdf2.format(c.getTime()).toInt()
        if (currentDate2 == 1) {
            for (i in 2..31) {
                waterDB.updateWaterMonth(WaterMonth(i, 0, 0, 0))
                waterDB.updateWeightMonth(Weight(i, 0f))
            }
        }

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == Calendar.MONDAY) {
            val tue = WaterWeek(3, 0, 0, 0)
            val wed = WaterWeek(4, 0, 0, 0)
            val thu = WaterWeek(5, 0, 0, 0)
            val fri = WaterWeek(6, 0, 0, 0)
            val sar = WaterWeek(7, 0, 0, 0)
            val sun = WaterWeek(8, 0, 0, 0)
            waterDB.updateWaterWeek(tue)
            waterDB.updateWaterWeek(thu)
            waterDB.updateWaterWeek(fri)
            waterDB.updateWaterWeek(wed)
            waterDB.updateWaterWeek(sar)
            waterDB.updateWaterWeek(sun)

            val tuew = Weight(3, 0f)
            val wedw = Weight(4, 0f)
            val thuw = Weight(5, 0f)
            val friw = Weight(6, 0f)
            val sarw = Weight(7, 0f)
            val sunw = Weight(8, 0f)

            waterDB.updateWeightWeek(tuew)
            waterDB.updateWeightWeek(wedw)
            waterDB.updateWeightWeek(thuw)
            waterDB.updateWeightWeek(friw)
            waterDB.updateWeightWeek(sarw)
            waterDB.updateWeightWeek(sunw)
        }
    }


    private fun saveWeightToDatabase() {
        waterDB = WaterDatabase(this)
        waterDB.createDefaultWeightWeek()
        waterDB.createDefaultWeightMonth()
        waterDB.createDefaultWeightYear()
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        val weight = ShareReference.getWeights(this)
        val weightAdd = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()
        if (day == Calendar.MONDAY) {
            waterDB.updateWeightWeek(Weight(2, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        } else if (day == Calendar.TUESDAY) {
            waterDB.updateWeightWeek(Weight(3, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        } else if (day == Calendar.WEDNESDAY) {
            waterDB.updateWeightWeek(Weight(4, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        } else if (day == Calendar.THURSDAY) {
            waterDB.updateWeightWeek(Weight(5, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        } else if (day == Calendar.FRIDAY) {
            waterDB.updateWeightWeek(Weight(6, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        } else if (day == Calendar.SATURDAY) {
            waterDB.updateWeightWeek(Weight(7, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()

            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            Log.i("vcvcff", "$currentDate2")
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        } else if (day == Calendar.SUNDAY) {
            waterDB.updateWeightWeek(Weight(8, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            waterDB.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            waterDB.updateWeightYear(weightYear)
        }
    }

    private fun serviceStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(Intent(this, WaterTrackerService::class.java))
        } else {
            this.startService(Intent(this, WaterTrackerService::class.java))
        }
        val intent = Intent(this, MyJobService::class.java)
        startService(intent)
    }


    private fun addWater() {
        btn_add.setOnClickListener {
            clickButtonAdd()

        }
//        ln_add.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    btn_add.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
//                    v.invalidate()
//                }
//                MotionEvent.ACTION_UP -> {
//                    v.background.clearColorFilter()
//                    v.invalidate()
//                }
//            }
//            false
//        }

        ln_add.setOnClickListener {

            clickButtonAdd()
        }
    }

    fun clickButtonAdd() {


        val count2 = ShareReference.getAds(this)!!.toInt()
        Log.i("vcvcvcvc","$count2")
        ShareReference.setAds(count2 + 1, this)
        if (count2 % 2 != 0 && checkAds > 1) {
            mInterstitialAd.show()
            
        }
        view.setBackgroundColor(Color.parseColor("#1F36A7"))
        btn_add.setBackgroundResource(R.drawable.button_selector2)
        ln_bottom.setBackgroundColor(Color.parseColor("#1F36A7"))
        ShareReference.setCheckBedTime(true, this)
        val main = tgbtn_waterdaily.isChecked
        if (main == false) {
            tgbtn_history.isChecked = false
            tgbtn_waterdaily.isChecked = true
            tgbtn_weight.isChecked = false
            tgbtn_setting.isChecked = false
            val fragment =
                MainFragment()
            insertFragment(fragment)
        }
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat("HH:mm")
        val sdf2 = SimpleDateFormat("hh:mm a")

        val lastTimeDrink = sdf2.format((c.getTime()))
        ShareReference.setTimeDrink2("$lastTimeDrink", this)

        val currentDate = sdf.format(c.getTime())
        ShareReference.setTimeDrink("$currentDate", this)


        val now = Calendar.getInstance()
        val time = ShareReference.getIntervalTime(this)
        val timeinterval = time!!.replace("[^\\d.]".toRegex(), "").toInt()


        var x = now.add(Calendar.MINUTE, timeinterval)
        val intervaltime = sdf.format(now.getTime())
        ShareReference.setTimeNextDrink(intervaltime, this)

        val now2 = Calendar.getInstance()
        now2.add(Calendar.MINUTE, 30)
        val intervaltime30 = sdf.format(now2.getTime())
        ShareReference.setTimeNextDrink30(intervaltime30, this)

        val now3 = Calendar.getInstance()
        now3.add(Calendar.MINUTE, 60)
        val intervaltime60 = sdf.format(now3.getTime())
        ShareReference.setTimeNextDrink60(intervaltime60, this)

        val now4 = Calendar.getInstance()
        now4.add(Calendar.MINUTE, 90)
        val intervaltime90 = sdf.format(now4.getTime())
        ShareReference.setTimeNextDrink90(intervaltime90, this)

        val now5 = Calendar.getInstance()
        now5.add(Calendar.MINUTE, 120)
        val intervaltime120 = sdf.format(now5.getTime())
        ShareReference.setTimeNextDrink120(intervaltime120, this)


        val count = ShareReference.getCount(this)
        ShareReference.setCount(count!! + 1, this)
        val waterlevel = ShareReference.getWaterLevel(this)
        val waterUP: Int? = ShareReference.getWaterup(this)?.let { it1 ->
            waterlevel?.plus(
                it1
            )
        }
        ShareReference.setWaterlevel(waterUP!!, this)
        val waterup = ShareReference.getWaterup(this)
        val intent1 = Intent()
        intent1.action = "textChange"
        intent1.putExtra("text", "$waterUP")
        intent1.putExtra("water", "$waterup")
        ShareReference.setWaterup2(waterup!!.toInt(), this)
        this.sendBroadcast(intent1)
        val waterGold = ShareReference.getGoalDrink(this)
        val y = waterGold!!.replace("[.]".toRegex(), "").toInt()
        val histoty = WaterHistory(1, "$currentDate", waterup)
        waterDB.addHistoty(histoty)
        if (waterUP < y) {
            val waterup = ShareReference.getWaterup(this)

        } else {
            val checkFull = ShareReference.getCheckfull(this)
            if (checkFull == false) {
                ShareReference.setCheckFull(true, this)

                val mDialogView =
                    LayoutInflater.from(this).inflate(R.layout.dialog_enought_wt, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mAlertDialog.img_remove_enought2.setOnClickListener {
                    mAlertDialog.dismiss()

                }


            } else {

                val mDialogView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_after_enought, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mAlertDialog.img_remove__after_enought.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }


        }

        ShareReference.setRecord(true, this)


        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, Alarm::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        val reminder = ShareReference.getReminder(this)
        if (reminder == true) {
            alarmMgr?.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + (timeinterval * 60 * 1000) - (900 * timeinterval),
                ((timeinterval * 60 * 1000) - (900 * timeinterval)).toLong(),
                alarmIntent
            )

            //timeinterval
        }


        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == Calendar.MONDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(2, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.TUESDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(3, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.WEDNESDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(4, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.THURSDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(5, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.FRIDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(6, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SATURDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(7, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SUNDAY) {

            val waterDrink2 = ShareReference.getWaterLevel(this)!!.toInt()
            val waterDrink = ShareReference.getWaterLevel(this)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(this)
            val water = WaterWeek(8, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            waterDB.updateWaterMonth(waterMonth)
        }
    }

    override fun onResume() {
        super.onResume()
        this.registerReceiver(changeIconReceiver, IntentFilter("imgChange"))
    }



    override fun onDestroy() {

        super.onDestroy()
        this.unregisterReceiver(changeIconReceiver)
    }

    override fun onStop() {
        ShareReference.setAds(1, this)
        super.onStop()
    }

    private fun bottomOnclick() {
        ln_main.setOnClickListener {
            addMainFragment()
        }
        ln_histoty.setOnClickListener {
            addHistoryFragment()
        }
        ln_setting.setOnClickListener {
            addWSettingFragment()
        }
        ln_weight.setOnClickListener {
            addWeightFragment()
        }
    }

    private fun addFragmentDefault() {
        val check = intent.getIntExtra("drinkwater", 1)
        if (check == 1) {
            addMainFragment()
        }
        if (check == 2) {
            addMainFragment()
        }
        if (check == 3) {
            addWSettingFragment()
        }


        val waterUp = ShareReference.getWaterup(this)

        if (waterUp == 100) {
            btn_add.text = "100+"
        }
        if (waterUp == 200) {
            btn_add.text = "200+"
        }
        if (waterUp == 300) {
            btn_add.text = "300+"
        }
        if (waterUp == 400) {
            btn_add.text = "400+"
        }
    }

    fun addMainFragment() {

        view.setBackgroundColor(Color.parseColor("#1F36A7"))
        btn_add.setBackgroundResource(R.drawable.bg_button_userdata)
        ln_bottom.setBackgroundColor(Color.parseColor("#1F36A7"))
        val fragment = MainFragment()
        insertFragment(fragment)
        tgbtn_history.isChecked = false
        tgbtn_waterdaily.isChecked = true
        tgbtn_weight.isChecked = false
        tgbtn_setting.isChecked = false
    }

    fun addHistoryFragment() {

        view.setBackgroundColor(Color.parseColor("#F3F0F0"))
        val frag = HistorysFragment()
        insertFragment(frag)
        btn_add.setBackgroundResource(R.drawable.round_bg)
        ln_bottom.setBackgroundColor(Color.parseColor("#ffffff"))
        tgbtn_history.isChecked = true
        tgbtn_waterdaily.isChecked = false
        tgbtn_weight.isChecked = false
        tgbtn_setting.isChecked = false
    }

    fun addWeightFragment() {
        view.setBackgroundColor(Color.parseColor("#F3F0F0"))
        val frag = WeightsFragment()
        insertFragment(frag)
        btn_add.setBackgroundResource(R.drawable.round_bg)
        ln_bottom.setBackgroundColor(Color.parseColor("#ffffff"))
        tgbtn_history.isChecked = false
        tgbtn_waterdaily.isChecked = false
        tgbtn_weight.isChecked = true
        tgbtn_setting.isChecked = false

    }

    fun addWSettingFragment() {
        view.setBackgroundColor(Color.parseColor("#F3F0F0"))
        btn_add.setBackgroundResource(R.drawable.round_bg)
        ln_bottom.setBackgroundColor(Color.parseColor("#ffffff"))
        val fragment = SettingsFragment()
        insertFragment(fragment)
        tgbtn_history.isChecked = false
        tgbtn_waterdaily.isChecked = false
        tgbtn_weight.isChecked = false
        tgbtn_setting.isChecked = true

    }

    override fun onBackPressed() {
        val checkRate = ShareReference.getCheckRateApp(this)
        if (checkRate == false) {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mAlertDialog.btn_yes.setOnClickListener {
                ShareReference.setCheckRateApp(true, this)
                mAlertDialog.dismiss()
                val uri = Uri.parse("market://details?id=" + this.packageName)
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                goToMarket.addFlags(
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                )
                try {
                    startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)
                        )
                    )
                }
            }
            mAlertDialog.btn_no.setOnClickListener {
                finish()
            }
        } else {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_2,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mAlertDialog.btn_yes.setOnClickListener {
                finish()
            }
            mAlertDialog.btn_no.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }

    private fun insertFragment(frag: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                com.kolorfox.watertracker.R.id.container,
                frag,
                frag.javaClass.getSimpleName()
            )
            .commit()
    }

    internal var changeIconReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "imgChange") {
                var img = intent.getIntExtra("img", 1)
                if (img == 1) {
                    btn_add.text = "100+"
                } else if (img == 2) {
                    btn_add.text = "200+"
                } else if (img == 3) {
                    btn_add.text = "300+"
                } else if (img == 4) {
                    btn_add.text = "400+"
                }

            }
        }
    }

    override fun drinkWater(check: Boolean) {
        if (check == true) {
            clickButtonAdd()
            btn_add.visibility = View.VISIBLE
        } else {
            btn_add.visibility = View.VISIBLE
        }
    }

    //    private fun loadAdsBanner() {
//        val isTablet = AppLovinSdkUtils.isTablet(this)
//        val adSize = if (isTablet) AppLovinAdSize.LEADER else AppLovinAdSize.BANNER
//        val adView = AppLovinAdView(adSize, this)
//        adView.id = ViewCompat.generateViewId()
//
//        adView.loadNextAd()
//
//        adView.setAdLoadListener(object : AppLovinAdLoadListener
//        {
//            override fun adReceived(ad: AppLovinAd)
//            {
//
//            }
//
//            override fun failedToReceiveAd(errorCode: Int)
//            {
//
//            }
//        })
//
//        adView.setAdDisplayListener(object : AppLovinAdDisplayListener
//        {
//            override fun adDisplayed(ad: AppLovinAd)
//            {
//
//            }
//
//            override fun adHidden(ad: AppLovinAd)
//            {
//
//            }
//        })
//
//        adView.setAdClickListener {
//
//        }
//
//        adView.setAdViewEventListener(object : AppLovinAdViewEventListener
//        {
//            override fun adOpenedFullscreen(ad: AppLovinAd?, adView: AppLovinAdView?)
//            {
//
//            }
//
//            override fun adClosedFullscreen(ad: AppLovinAd?, adView: AppLovinAdView?)
//            {
//
//
//            }
//
//            override fun adLeftApplication(ad: AppLovinAd?, adView: AppLovinAdView?)
//            {
//
//            }
//
//            override fun adFailedToDisplay(ad: AppLovinAd?, adView: AppLovinAdView?, code: AppLovinAdViewDisplayErrorCode?)
//            {
//
//            }
//        })
//
//        // Add programmatically created banner into our container
//        root_main.addView(adView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppLovinSdkUtils.dpToPx(this, 50)))
//
//        val constraintSet = ConstraintSet()
////        constraintSet.clone(root_main)
//        constraintSet.connect(adView.id, ConstraintSet.TOP, R.id.root_main, ConstraintSet.TOP, 0)
////        constraintSet.applyTo(root_main)
//
//        // Load an ad!
//        adView.loadNextAd()
//
//    }

//    private fun loadAdss() {
//        System.out.println("ads")
//        AppLovinSdk.getInstance(applicationContext).adService.loadNextAd(AppLovinAdSize.INTERSTITIAL, object :
//            AppLovinAdLoadListener
//        {
//            override fun adReceived(ad: AppLovinAd)
//            {
//                System.out.println("ads2")
//                loadedAd = ad
//
//            }
//
//            override fun failedToReceiveAd(errorCode: Int)
//            {
//
//            }
//        })
//        val interstitialAdDialog = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(this), this)
//
////            interstitialAdDialog.showAndRender(loadedAd)
//        interstitialAdDialog.show()
//
//
//    }



}

package com.kolorfox.watertracker.fragment.mainfragment


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.applovin.adview.AppLovinAdView
import com.applovin.adview.AppLovinAdViewDisplayErrorCode
import com.applovin.adview.AppLovinAdViewEventListener
import com.applovin.sdk.AppLovinAd
import com.applovin.sdk.AppLovinAdDisplayListener
import com.applovin.sdk.AppLovinAdLoadListener
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.fragment.DialogFirst
import com.kolorfox.watertracker.waterservices.MyJobService
import com.kolorfox.watertracker.waterservices.NewsDaysReceiver
import kotlinx.android.synthetic.main.dialog_first.*
import kotlinx.android.synthetic.main.dialog_permession.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {

    var count: Int = 0
    private var alarmMgr0h00: AlarmManager? = null
    private var alarmIntent0h00: PendingIntent? = null
    lateinit var drinkWater: DialogFirst
    lateinit var tgBt100: ToggleButton
    lateinit var tgBt200: ToggleButton
    lateinit var tgBt300: ToggleButton
    lateinit var tgBt400: ToggleButton
    lateinit var rlBt100: RelativeLayout
    lateinit var rlBt200: RelativeLayout
    lateinit var rlBt300: RelativeLayout
    lateinit var rlBt400: RelativeLayout
    lateinit var tvWaterDrink: TextView
    lateinit var tvLastDrinkMain: TextView
    lateinit var tvGoal:TextView
    lateinit var tv_tgbtn_100:TextView
    lateinit var tv_tgbtn_200:TextView
    lateinit var tv_tgbtn_300:TextView
    lateinit var tv_tgbtn_400:TextView
    lateinit var ad_view: AppLovinAdView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(
                R.layout.fragment_main,
                container,
                false
            )

        tv_tgbtn_100 = view.findViewById(R.id.tv_tgbtn_100)
        tv_tgbtn_200 = view.findViewById(R.id.tv_tgbtn_200)
        tv_tgbtn_300 = view.findViewById(R.id.tv_tgbtn_300)
        tv_tgbtn_400 = view.findViewById(R.id.tv_tgbtn_400)
        rlBt100 = view.findViewById(R.id.rl_100ml)
        rlBt200 = view.findViewById(R.id.rl_200ml)
        rlBt300 = view.findViewById(R.id.rl_300ml)
        rlBt400 = view.findViewById(R.id.rl_400ml)
        tgBt100 = view.findViewById(R.id.tg_btn_10)
        tgBt200 = view.findViewById(R.id.tg_btn_20)
        tgBt300 = view.findViewById(R.id.tg_btn_30)
        tgBt400 = view.findViewById(R.id.tg_btn_40)
        tvWaterDrink = view.findViewById(R.id.tv_water_drink)
        tvLastDrinkMain = view.findViewById(R.id.tv_last_drink_fragmain)
        tvGoal = view.findViewById(R.id.tv_daily_gold_main)
//        ad_view = view.findViewById(R.id.ad_view)

        createDialog()
        chooseWaterLevel()
        getData()
        jobService()
        updateView()

//        loadBannerAds()

        return view

    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(context)){
                Log.i("lockkkkk","ko co")
                ShareReference.setNotifiWm(false, context!!)
            } else {
                Log.i("lockkkkk","co")
                ShareReference.setNotifiWm(true, context!!)
            }
        }
    }

//    private fun loadBannerAds() {
//        ad_view.loadNextAd()
//        ad_view.setAdLoadListener(object : AppLovinAdLoadListener
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
//        ad_view.setAdDisplayListener(object : AppLovinAdDisplayListener
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
//        ad_view.setAdClickListener {
//
//        }
//
//        ad_view.setAdViewEventListener(object : AppLovinAdViewEventListener
//        {
//            override fun adOpenedFullscreen(ad: AppLovinAd?, adView: AppLovinAdView?)
//            {
//
//            }
//
//            override fun adClosedFullscreen(ad: AppLovinAd?, adView: AppLovinAdView?)
//            {
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
//
//        ad_view.loadNextAd()
//    }

    private fun updateView() {
        val handler = Handler()
        handler.postDelayed({
            ln_white.visibility = View.VISIBLE
        }, 100)
    }

    private fun jobService() {

        alarmMgr0h00 = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent0h00 = Intent(context, NewsDaysReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 5, intent, 0)
        }
        val calendar2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 0)


        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr0h00?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar2.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent0h00
            )
        }
        val sdf2 =
            SimpleDateFormat("HH:mm")// here set the pattern as you date in string was containing like date/month/year
        val bed = ShareReference.getBedTimes(context!!)
        val wakeUp = ShareReference.getWakeUpTime(context!!)
        val d = sdf2.parse(bed)
        val d2 = sdf2.parse((wakeUp))
        val cal = Calendar.getInstance()
        cal.time = d
        val hoursBed = cal.get(Calendar.HOUR_OF_DAY)
        val minutesBed = cal.get(Calendar.MINUTE)

        cal.time = d2
        val hoursWake = cal.get(Calendar.HOUR_OF_DAY)
        val minutesWake = cal.get(Calendar.MINUTE)
        val calendar3: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hoursWake)
            set(Calendar.MINUTE, minutesWake)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)


        }
        val calendar4: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hoursBed)
            set(Calendar.MINUTE, minutesBed)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)


        }



        MyJobService.schedule(context!!, calendar2.timeInMillis)
    }



    private fun getData() {
        val waterGoal =  ShareReference.getGoalDrink(context!!)
        val waterTime = ShareReference.getTimeDrink2(context!!)
        val waterLevel = ShareReference.getWaterLevel(context!!)
        val waterUp = ShareReference.getWaterup(context!!)

        if(waterUp == 100){
            tv_tgbtn_100.setTextColor(Color.parseColor("#ffffff"))
            tv_tgbtn_200.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#859fec"))
            tgBt100.isChecked = true
            tgBt200.isChecked = false
            tgBt300.isChecked = false
            tgBt400.isChecked = false
        }
        if(waterUp == 200){
            tv_tgbtn_200.setTextColor(Color.parseColor("#ffffff"))
            tv_tgbtn_100.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#859fec"))
            tgBt100.isChecked = false
            tgBt200.isChecked = true
            tgBt300.isChecked = false
            tgBt400.isChecked = false


        }
        if(waterUp == 300){
            tv_tgbtn_200.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_100.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#ffffff"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#859fec"))

            tgBt100.isChecked = false
            tgBt200.isChecked = false
            tgBt300.isChecked = true
            tgBt400.isChecked = false
        }

        if(waterUp == 400){
            tv_tgbtn_200.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_100.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#ffffff"))

            tgBt100.isChecked = false
            tgBt200.isChecked = false
            tgBt300.isChecked = false
            tgBt400.isChecked = true
        }

        tvWaterDrink.text = "$waterLevel ml"
        tvLastDrinkMain.text = "$waterTime"
        tvGoal.text= "$waterGoal ml"


    }

    override fun onResume() {
        super.onResume()
        context!!.registerReceiver(waterUpReceiver, IntentFilter("textChange"))
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(waterUpReceiver)

    }

    internal var waterUpReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            count = ShareReference.getCount(context)!!
            if (intent.action == "textChange") {
                val text = intent.getStringExtra("text")
                tvWaterDrink.text = "$text ml"

                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm")
                val currentDate = sdf.format(c.getTime())

                val sdf2 = SimpleDateFormat("hh:mm a")
                val lastTimeDrink = sdf2.format((c.getTime()))
                ShareReference.setTimeDrink2("$lastTimeDrink",context)
                tvLastDrinkMain.text = "$lastTimeDrink"
                ShareReference.setTimeDrink("$currentDate", context)
                val now = Calendar.getInstance()

                val time = context?.let { ShareReference.getIntervalTime(it) }
                val timeinterval = time!!.replace("[^\\d.]".toRegex(), "").toInt()

                now.add(Calendar.MINUTE, timeinterval)
                val intervaltime = sdf.format(now.getTime())
                ShareReference.setTimeNextDrink(intervaltime, context)



            }
        }
    }

    private fun chooseWaterLevel() {
        rlBt100.setOnClickListener {
            tv_tgbtn_100.setTextColor(Color.parseColor("#ffffff"))
            tv_tgbtn_200.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#859fec"))
            tgBt100.isChecked = true
            tgBt200.isChecked = false
            tgBt300.isChecked = false
            tgBt400.isChecked = false
            context?.let { it1 ->
                ShareReference.setWaterUp(100, it1)
                val intentWaterChoose = Intent()
                intentWaterChoose.action = "imgChange"
                intentWaterChoose.putExtra("img", 1)
                context!!.sendBroadcast(intentWaterChoose)
            }
        }
        rlBt200.setOnClickListener {
            tv_tgbtn_200.setTextColor(Color.parseColor("#ffffff"))
            tv_tgbtn_100.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#859fec"))
            tgBt100.isChecked = false
            tgBt200.isChecked = true
            tgBt300.isChecked = false
            tgBt400.isChecked = false
            context?.let { it1 ->
                ShareReference.setWaterUp(200, it1)
                val intentWaterChoose = Intent()
                intentWaterChoose.action = "imgChange"
                intentWaterChoose.putExtra("img", 2)
                context!!.sendBroadcast(intentWaterChoose)
            }
        }
        rlBt300.setOnClickListener {
            tv_tgbtn_200.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_100.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#ffffff"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#859fec"))

            tgBt100.isChecked = false
            tgBt200.isChecked = false
            tgBt300.isChecked = true
            tgBt400.isChecked = false
            context?.let { it1 ->
                ShareReference.setWaterUp(300, it1)
                val intentWaterChoose = Intent()
                intentWaterChoose.action = "imgChange"
                intentWaterChoose.putExtra("img", 3)
                context!!.sendBroadcast(intentWaterChoose)
            }
        }
        rlBt400.setOnClickListener {
            tv_tgbtn_200.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_100.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_300.setTextColor(Color.parseColor("#859fec"))
            tv_tgbtn_400.setTextColor(Color.parseColor("#ffffff"))

            tgBt100.isChecked = false
            tgBt200.isChecked = false
            tgBt300.isChecked = false
            tgBt400.isChecked = true
            context?.let { it1 ->
                ShareReference.setWaterUp(400, it1)
                val intentWaterChoose = Intent()
                intentWaterChoose.action = "imgChange"
                intentWaterChoose.putExtra("img", 4)
                context!!.sendBroadcast(intentWaterChoose)
            }
        }
    }
    fun allowPermission(){

        val packageName = context!!.packageName
        val check = ShareReference.getNotifiWm(context!!)
        if(check == false) {
            if (Build.VERSION.SDK_INT >= 23) {
                val mDialogView =
                    LayoutInflater.from(context!!).inflate(R.layout.dialog_permession, null)
                val mBuilder = AlertDialog.Builder(context!!)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mAlertDialog.btn_allow.setOnClickListener {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (!Settings.canDrawOverlays(context)) {
                            val intent = Intent(
                                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:$packageName")
                            )
                            startActivityForResult(intent, 1234)
                        }
                    }
                    mAlertDialog.dismiss()
                }
                mAlertDialog.btn_deny.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234) {

            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Settings.canDrawOverlays(context)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            ) {
                ShareReference.setNotifiWm(true, context!!)
            } else {
                ShareReference.setNotifiWm(false, context!!)
            }
        }
    }
    private fun createDialog() {
        val dialog = context?.let { ShareReference.getFirstTimeLogin(it) }
        if (dialog == true) {


            val calendar2: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 0)


            }

            drinkWater = activity as DialogFirst
            MyJobService.schedule(context!!, calendar2.timeInMillis)
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_first, null)


            val mBuilder = context?.let {
                AlertDialog.Builder(it)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.window!!.setGravity(Gravity.BOTTOM)
            val layoutParams = mAlertDialog.window!!.attributes
            val pixel: Int = context!!.getResources().getDisplayMetrics().heightPixels


            val marindiaLog = pixel - (37 * pixel / 38) - (pixel / 200)
            layoutParams.y = marindiaLog


            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            context?.let { ShareReference.setSettingIcon(false, it) }
            mAlertDialog.btn_add_dialog.setOnClickListener {
                drinkWater.drinkWater(true)
                mAlertDialog.dismiss()
            }
            mAlertDialog.ln_dialog.setOnClickListener {
                drinkWater.drinkWater(false)
                mAlertDialog.dismiss()
            }
            mAlertDialog.setOnCancelListener {
                drinkWater.drinkWater(false)
            }
            allowPermission()

        }


    }


}

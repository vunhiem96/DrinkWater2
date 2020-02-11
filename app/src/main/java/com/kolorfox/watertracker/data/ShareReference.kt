package com.kolorfox.watertracker.data

import android.content.Context

object ShareReference {
    val PREF_NAME = "NavaTech"
    fun setGender(genDer: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("gender", genDer)
        editor.apply()
    }

    fun getGender(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("gender", "Male")
    }

    fun setWeights(weight: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("weight", weight)
        editor.apply()
    }

    fun getWeights(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("weight", "80 kg")
    }

    fun setHeight(height: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("height", height)
        editor.apply()
    }

    fun getHeight(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("height", "170 cm")
    }

    fun setWakeUpTime(waveUp: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("waveup", waveUp)
        editor.apply()
    }

    fun getWakeUpTime(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("waveup", "07:00")
    }

    fun setBedTimes(bedTime: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("bedTime", bedTime)
        editor.apply()
    }

    fun getBedTimes(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("bedTime", "22:00")
    }

    fun setIntervalTimes(interValTime: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("intervalTime", interValTime)
        editor.apply()
    }

    fun getIntervalTime(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("intervalTime", "60F min")
    }

    fun setGoalDrink(goldDrink: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("golddrink", goldDrink)
        editor.apply()
    }

    fun getGoalDrink(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("golddrink", "1900 ml")
    }

    fun setCheckHistoryLogin(historyLoign: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("historyLoign", historyLoign)
        editor.apply()
    }

    fun getHistoryLogin(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("historyLoign", false)

    }

    fun setSettingIcon(settingIcon: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("settingIcon", settingIcon)
        editor.apply()
    }

    fun getFirstTimeLogin(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("settingIcon", true)
    }

    fun setReminder(Reminder: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Reminder", Reminder)
        editor.apply()
    }

    fun getReminder(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Reminder", true)

    }

    fun setReminder2(Reminder2: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Reminder2", Reminder2)
        editor.apply()
    }

    fun getReminder2(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Reminder2", true)

    }

    fun setRecord(Record: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Record", Record)
        editor.apply()
    }

    fun getRecord(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Record", true)
    }

    fun setWaterlevel(waterLevel: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("waterLevel", waterLevel)
        editor.apply()
    }

    fun getWaterLevel(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("waterLevel", 0)
    }

    fun setWaterUp(Waterup: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("Waterup", Waterup)
        editor.apply()
    }

    fun getWaterup(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("Waterup", 100)
    }

    fun setTimeDrink(TimeDrink: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeDrink", TimeDrink)
        editor.apply()
    }

    fun getTimeDrink2(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeDrink2", "00:00")
    }
    fun setTimeDrink2(TimeDrink2: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeDrink2", TimeDrink2)
        editor.apply()
    }

    fun getTimeDrink(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeDrink", "00:00")
    }

    fun setTimeNextDrink(TimeNextDrink: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeNextDrink", TimeNextDrink)
        editor.apply()
    }

    fun getTimeNextDrink(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeNextDrink", "00:00")
    }

    fun setTimeNextDrink30(TimeNextDrink30: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeNextDrink30", TimeNextDrink30)
        editor.apply()
    }

    fun getTimeNextDrink30(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeNextDrink30", "00:00")
    }

    fun setTimeNextDrink60(TimeNextDrink60: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeNextDrink60", TimeNextDrink60)
        editor.apply()
    }

    fun getTimeNextDrink60(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeNextDrink60", "00:00")
    }

    fun setTimeNextDrink90(TimeNextDrink90: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeNextDrink90", TimeNextDrink90)
        editor.apply()
    }

    fun getTimeNextDrink90(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeNextDrink90", "00:00")
    }

    fun setTimeNextDrink120(TimeNextDrink120: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TimeNextDrink120", TimeNextDrink120)
        editor.apply()
    }

    fun getTimeNextDrink120(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("TimeNextDrink120", "00:00")
    }


    fun setRemiderMode(mode: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("mode", mode)
        editor.apply()
    }

    fun getRemiderMode(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("mode", 2)
    }

    fun setTargetWeight(target: String, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("target", target)
        editor.apply()
    }

    fun getTargetWeight(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("target", "")
    }

    fun getCheckBed(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckBed", true)

    }

    fun setCheckBedTime(CheckBed: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckBed", CheckBed)
        editor.apply()
    }

    fun getCheckfull(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckFull", false)

    }

    fun setCheckFull(CheckFull: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckFull", CheckFull)
        editor.apply()
    }

    fun getCount(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("count", 0)
    }

    fun setCount(count: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("count", count)
        editor.apply()
    }

    fun getCheckDailyGold(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckDailyGold", true)

    }

    fun setDailyGold(CheckDailyGold: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckDailyGold", CheckDailyGold)
        editor.apply()
    }

    fun setWaterup2(Waterup2: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("Waterup2", Waterup2)
        editor.apply()
    }

    fun getWaterup2(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("Waterup2", 0)
    }

    fun setCheckAds(checkAds: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("checkAds", checkAds)
        editor.apply()
    }

    fun getCheckAds(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("checkAds", 1)
    }

    fun setAds(Ads: Int, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("Ads", Ads)
        editor.apply()
    }

    fun getAds(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("Ads", 1)
    }

    fun setCheckRateApp(CheckRateApp: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("CheckRateApp", CheckRateApp)
        editor.apply()
    }

    fun getCheckRateApp(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("CheckRateApp", false)
    }

    fun setNotifiWm(NotifiWm: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("NotifiWm", NotifiWm)
        editor.apply()
    }

    fun getNotifiWm(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("NotifiWm", false)
    }

    fun setCheck(Check: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Check", Check)
        editor.apply()
    }

    fun getCheck(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Check", false)
    }

    fun setCheckNoti(CheckNoti: Boolean, context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Checknoti", CheckNoti)
        editor.apply()
    }

    fun getCheckNoti(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Checknoti", false)
    }




}
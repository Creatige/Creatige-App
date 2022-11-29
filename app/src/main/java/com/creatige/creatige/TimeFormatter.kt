package com.creatige.creatige

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Given a date String of the format given by the Twitter API, returns a display-formatted
 * String representing the relative time difference, e.g. "2m", "6d", "23 May", "1 Jan 14"
 * depending on how great the time difference between now and the given date is.
 * This, as of 2016-06-29, matches the behavior of the official Twitter app.
 */
object TimeFormatter {
    fun getTimeDifference(rawJsonDate: Date?): String {
        var time = ""
        val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
        val format = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
        format.isLenient = true
        try {
            val diff = (System.currentTimeMillis() - format.parse(rawJsonDate.toString()).time) / 1000
            if (diff < 5) time = "Just now" else if (diff < 60) time =
                String.format(Locale.ENGLISH, "%ds", diff) else if (diff < 60 * 60) time =
                String.format(
                    Locale.ENGLISH, "%dm", diff / 60
                ) else if (diff < 60 * 60 * 24) time = String.format(
                Locale.ENGLISH, "%dh", diff / (60 * 60)
            ) else if (diff < 60 * 60 * 24 * 30) time = String.format(
                Locale.ENGLISH, "%dd", diff / (60 * 60 * 24)
            ) else {
                val now = Calendar.getInstance()
                val then = Calendar.getInstance()
                then.time = format.parse(rawJsonDate.toString())
                time = if (now[Calendar.YEAR] == then[Calendar.YEAR]) {
                    (then[Calendar.DAY_OF_MONTH].toString() + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US))
                } else {
                    (then[Calendar.DAY_OF_MONTH].toString() + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                            + " " + (then[Calendar.YEAR] - 2000).toString())
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    /**
     * Given a date String of the format given by the Twitter API, returns a display-formatted
     * String of the absolute date of the form "30 Jun 16".
     * This, as of 2016-06-30, matches the behavior of the official Twitter app.
     */
    fun getTimeStamp(rawJsonDate: String?): String {
        var time = ""
        val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
        val format = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
        format.isLenient = true
        try {
            val then = Calendar.getInstance()
            then.time = format.parse(rawJsonDate)
            val date = then.time
            val format1 = SimpleDateFormat("h:mm a \u00b7 dd MMM yy")
            time = format1.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }
}
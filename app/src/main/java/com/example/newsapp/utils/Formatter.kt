package com.example.newsapp.utils

object Formatter {

    fun date(date: String): String{
        // 2020-09-14T09:21:00Z
        val dateSplit = date.split("T")
        val time = dateSplit[1].substringBeforeLast(":")
        return dateSplit[0].substringAfterLast("-") + "/" + dateSplit[0].substring(5,7) + "/" + " " + time
    }

    fun stringLength(string: String?, length: Int): String {
        if (string == null){
            return "No Author"
        }
        if (string.length > length){
            return string.substring(0,length - 3) + "..."
        }
        return string
    }
}
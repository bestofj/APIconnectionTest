package com.example.apiconnectiontest.datas

import java.io.Serializable;
import java.util.Calendar;

class DayOfMonth : Serializable {
    private var day :Int = 0
    private var isSelected:Boolean = false
    private var date: Calendar? = null


    fun getDay() :Int {
        return day
    }

    fun setDay(day:Int) {
        this.day = day
    }

    fun isSelected() :Boolean{
        return isSelected
    }

    fun setSelected(selected : Boolean) {
        isSelected = selected
    }

    fun getDate() : Calendar? {
        return date
    }

    fun setDate(date :Calendar) {
        this.date = date
    }

}

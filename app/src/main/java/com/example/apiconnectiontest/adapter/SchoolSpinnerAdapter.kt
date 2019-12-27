package com.beanfactory.woorischool.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.beanfactory.woorischool.datas.School
import java.util.*
import com.example.apiconnectiontest.LoginParentInfoActivity

class SchoolSpinnerAdapter(context: Context, spinnerData: List<School>) : BaseAdapter() {
    private val context: Context? = null
    private var spinnerData: List<School> = ArrayList<School>()

    private val inflater: LayoutInflater

    fun setChangeData(spinnerData: List<School>) {
        this.spinnerData = spinnerData
    }

    override fun getCount(): Int {
        return spinnerData.size
    }

    override fun getItem(i: Int): Any {
        return spinnerData[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(
        i: Int,
        convertView: View,
        viewGroup: ViewGroup
    ): View {
        var convertView = convertView
        val data: School = spinnerData[i]
        convertView = inflater.inflate(R.layout.spinner_selected_item, viewGroup, false)
        val tv =
            convertView.findViewById<View>(R.id.txtvSpinnerItem) as TextView
        if (data.getId() === -1) {
            tv.setTextColor(context!!.resources.getColor(R.color.gray_9b9b9b))
        } else {
            tv.setTextColor(context!!.resources.getColor(R.color.gray_4a4a4a))
        }
        tv.text = String.format("%s", data.getName())
        return convertView
    }

    override fun getDropDownView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val data: School = spinnerData[position]
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_dropdown_item, parent, false)
        }
        val tv =
            convertView.findViewById<View>(R.id.txtvSpinnerItem) as TextView
        if (data.getId() === -1) {
            tv.setTextColor(context!!.resources.getColor(R.color.gray_9b9b9b))
        } else {
            tv.setTextColor(context!!.resources.getColor(R.color.gray_4a4a4a))
        }
        tv.text = String.format("%s", data.getName())
        return convertView
    }

    init {
        this.context = context
        this.spinnerData = spinnerData
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}
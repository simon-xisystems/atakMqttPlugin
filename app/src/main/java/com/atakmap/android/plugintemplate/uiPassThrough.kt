package com.atakmap.android.plugintemplate

import androidx.lifecycle.MutableLiveData

object uiPassThrough {

    val ownReport = MutableLiveData<String>()


    fun ownLocationReport(report: String){
        ownReport.postValue(report)
        println("bob: sending ownReport $report")

    }


}
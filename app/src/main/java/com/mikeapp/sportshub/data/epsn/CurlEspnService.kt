package com.mikeapp.sportshub.data.epsn

class CurlEspnService(private val timeStampUtil: TimeStampUtil) {
    fun getTeamScheduleJson(curlDoc: String): String? {
        val index0 = curlDoc.indexOf(teamScheduleCurlStart)
        val index1 = curlDoc.indexOf(teamScheduleCurlEnd)
        if (index0 > 0 && index1 > 0) {
            val mid = curlDoc.substring(index0, index1 + teamScheduleCurlEnd.length)
            val dataVersion = timeStampUtil.getTimeVersionWithMinute()
            return "{\"dataVersion\": $dataVersion, $mid}"
        }
        return null
    }

    companion object {
        private const val teamScheduleCurlStart = "\"teamSchedule\":"
        private const val teamScheduleCurlEnd = "\"No Data Available\""
    }
}
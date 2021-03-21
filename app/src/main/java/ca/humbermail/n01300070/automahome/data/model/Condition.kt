package ca.humbermail.n01300070.automahome.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condition(
		val id: String = "",
		val position: Int = -1,
		val type: String = "",
		val deviceId: String = ""
):Parcelable
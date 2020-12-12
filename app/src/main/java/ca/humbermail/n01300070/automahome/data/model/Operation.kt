package ca.humbermail.n01300070.automahome.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Operation(
		val id: String = "",
		val position: Int = -1,
		val type: String = "",
		val deviceId: String = ""
):Parcelable
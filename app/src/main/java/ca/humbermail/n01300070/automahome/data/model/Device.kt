package ca.humbermail.n01300070.automahome.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Device(
		val id: String = "",
		val homeId: String = "",
		val name: String = "",
		val type: String = "",
		val category: String = ""
): Parcelable
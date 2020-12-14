package ca.humbermail.n01300070.automahome.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
		val id: String = "",
		val name: String = "",
		val email: String = ""
): Parcelable
package ca.humbermail.n01300070.automahome.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
		val id: String = "",
		val homeId: String = "",
		val name: String = "",
		val note: String = "",
		val category: String = ""
): Parcelable
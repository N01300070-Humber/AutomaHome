package ca.humbermail.n01300070.automahome.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
		val id: String = "",
		val name: String = "",
		val owner: String = ""
): Parcelable
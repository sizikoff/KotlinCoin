package tr.sizikoff.coin

import com.google.gson.annotations.SerializedName
import tr.sizikoff.coin.data.USD

data class Quote(
    @SerializedName("USD")
    val uSD: USD?
)

package tr.sizikoff.coin

import com.google.gson.annotations.SerializedName
import tr.sizikoff.coin.data.Status

data class CryptoResponse(
    @SerializedName("status")
    val status: Status?,
    @SerializedName("data")
    val `data`: List<Data>?

)

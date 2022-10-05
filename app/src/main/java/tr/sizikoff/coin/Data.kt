package tr.sizikoff.coin

import com.google.gson.annotations.SerializedName
import tr.sizikoff.coin.data.Platform

data class Data(
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("platform")
    val platform: Platform?,
    @SerializedName("quote")
    val quote: Quote?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("tags")
    val tags: List<String?>?,
    @SerializedName("tvl_ratio")
    val tvlRatio: Any?
)

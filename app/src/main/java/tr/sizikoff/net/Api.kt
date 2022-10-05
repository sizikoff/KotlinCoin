package tr.sizikoff.net

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import tr.sizikoff.coin.CryptoResponse

interface Api {

    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getData(@Header("X-CMC_PRO_API_KEY")apiKey:String
                ,@Query("limit")limit:Int): Response<CryptoResponse>
}
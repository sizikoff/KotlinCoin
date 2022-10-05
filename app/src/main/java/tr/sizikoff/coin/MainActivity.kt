package tr.sizikoff.coin

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tr.sizikoff.coin.Constans.API_KEY
import tr.sizikoff.coin.Constans.LIMIT
import tr.sizikoff.coin.recycler.CoinsAdapter
import tr.sizikoff.net.Api
import tr.sizikoff.net.Retrofit

@SuppressLint("Registered")
class MainActivity : AppCompatActivity(),ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var madapter :CoinsAdapter
    lateinit var recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }


    private fun loadCoins() {
        recycler = findViewById(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO+coroutineExceptionHandler).launch {
        val response = Retrofit.instance.create(Api::class.java).getData(API_KEY, LIMIT)
            if (response.isSuccessful){
                runOnUiThread {
                    madapter = CoinsAdapter((response.body()?.data)!!)
                    madapter.notifyDataSetChanged()
                    recycler.adapter = madapter
                }
            }else{
                runOnUiThread {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(this@MainActivity,"что то не так", duration)
                    toast.show()
                }
            }
        }
    }


    private fun showMessage(isConnected: Boolean) {

        if (!isConnected) {

            Toast.makeText(this@MainActivity,"Включи интернет мудилдо", Toast.LENGTH_SHORT).show()
        } else {
            loadCoins()
            Toast.makeText(this@MainActivity,"bYNTHYTN TCNM", Toast.LENGTH_SHORT).show()

        }


    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }


    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}
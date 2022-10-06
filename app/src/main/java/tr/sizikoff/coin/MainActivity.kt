package tr.sizikoff.coin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    lateinit var madapter :CoinsAdapter
    lateinit var recycler:RecyclerView
    lateinit var builder:AlertDialog.Builder
    lateinit var mAlertDialog:AlertDialog
    lateinit var progressBar:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = ProgressDialog(this@MainActivity)
        progressBar.setTitle("Загрузка")
        progressBar.setMessage("Грузятся данные")
        progressBar.show()
        builder = AlertDialog.Builder(this)
        mAlertDialog = builder.create()
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
                progressBar.cancel()
                runOnUiThread {
                    madapter = CoinsAdapter((response.body()?.data)!!)
                    madapter.notifyDataSetChanged()
                    recycler.adapter = madapter
                }
            }else{
                runOnUiThread {
                    progressBar.cancel()
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(this@MainActivity,"что то не так", duration)
                    toast.show()
                }
            }
        }
    }
    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            with(mAlertDialog)
            {
                setTitle("Error Connection")
                setMessage("Check your internet")
                setCancelable(false)
                show()

            }
        } else {
            with(mAlertDialog)
            {
            cancel()
            }
            loadCoins()
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
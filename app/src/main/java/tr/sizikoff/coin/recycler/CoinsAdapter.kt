package tr.sizikoff.coin.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_coin.view.*
import tr.sizikoff.coin.Data
import tr.sizikoff.coin.R


class CoinsAdapter(private var list: List<Data>): RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false))
    }

    override fun getItemCount():Int = list.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        list.let {
        val coin = it[position]

            val cost:Double = coin.quote?.uSD?.price!!
            val percent:Double = coin.quote.uSD.percentChange1h!!
            val result = String.format("%8.2f", cost).replace(',', '.')
            val resPr = String.format("%8.3f", percent).replace(',', '.')
            Log.d("cost",resPr)

            holder.view.tvSymbol.text = coin.symbol
            holder.view.tvName.text = coin.name
            if (resPr > "0" ) {
                holder.view.tvPriceChange.text = "+ $resPr %"
            }else{
                holder.view.tvPriceChange.text = "- $resPr %"
            }
            holder.view.tvPrice.text = result
       }

    }

}
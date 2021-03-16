package com.azhar.kbbi.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azhar.kbbi.R
import com.azhar.kbbi.model.ModelMain
import com.github.akshay_naik.texthighlighterapi.TextHighlighter
import com.github.vipulasri.timelineview.TimelineView

/**
 * Created by Azhar Rivaldi on 11-03-2021
 */

class MainAdapter(private val modelMainList: List<ModelMain>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelMainList[position]
        val highlighter = TextHighlighter()
        highlighter.setColorForTheToken("a", "red")
        highlighter.setColorForTheToken("cak", "red")
        highlighter.setColorForTheToken("n", "red")
        highlighter.setColorForTheToken("v", "red")
        highlighter.setColorForTheToken("Huk", "red")
        highlighter.setColorForTheToken("Prm", "red")
        highlighter.setColorForTheToken("ki", "purple")
        highlighter.setColorForTheToken("Ling", "red")
        highlighter.setColorForTheToken("ark", "red")
        highlighter.setColorForTheToken("Sas", "red")
        highlighter.setColorForTheToken("Kim", "red")
        highlighter.setColorForTheToken("Komp", "red")
        highlighter.setColorForTheToken("sing", "red")
        highlighter.setColorForTheToken("adv", "red")
        highlighter.setColorForTheToken("num", "red")
        highlighter.setColorForTheToken("Ek", "red")
        highlighter.setColorForTheToken("kas", "red")
        highlighter.setColorForTheToken("Fis", "red")
        highlighter.setColorForTheToken("Mat", "red")
        highlighter.setColorForTheToken("kp", "red")
        val highlightedText = highlighter.getHighlightedText(data.strArti)
        holder.tvKata.text = data.strKata
        holder.tvArti.text = Html.fromHtml(highlightedText)
    }

    override fun getItemCount(): Int {
        return modelMainList.size
    }

    internal class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var timelineView: TimelineView
        var tvKata: TextView
        var tvArti: TextView

        init {
            timelineView = itemView.findViewById(R.id.timelineView)
            tvKata = itemView.findViewById(R.id.tvKata)
            tvArti = itemView.findViewById(R.id.tvArti)
        }
    }
}
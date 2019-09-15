package ru.visdom.raiffeisenbusinessad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.visdom.raiffeisenbusinessad.R
import ru.visdom.raiffeisenbusinessad.domain.Ad

class AdsAdapter : RecyclerView.Adapter<AdsAdapter.ViewHolder>(){

    var data = listOf<Ad>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init{
        data = listOf(Ad(0, "JavaHack", "Райффайзенбанк и компания Deworkacy проводят хакатон JAVA HACK для начинающих Java-разработчиков", true),
            Ad(1, "HOT Challenge 2019", "Образовательная программа форума будет состоять из интенсивного обучения по индивидуальным трекам и самого хакатона «HOT Challenge 2019».", false))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activeTextView: TextView = itemView.findViewById(R.id.ad_active_status)
        private val titleTextView: TextView = itemView.findViewById(R.id.ad_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.ad_description)
        private val moreButton: MaterialButton = itemView.findViewById(R.id.more_button)

        fun bind(item: Ad) {
            val res = itemView.context.resources
            activeTextView.text =
                if (item.isActive) res.getString(R.string.active) else res.getString(
                    R.string.no_active
                )

            titleTextView.text = item.title
            descriptionTextView.text = item.description
            // ToDo: setOnClick more button listener
            //moreButton.setOnClickListener {  }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.ad_item, parent, false)

                return ViewHolder(view)
            }
        }
    }
}
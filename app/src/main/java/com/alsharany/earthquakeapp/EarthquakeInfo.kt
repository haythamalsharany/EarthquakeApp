package com.alsharany.earthquakeapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class EarthquakeInfo : Fragment() {
    private lateinit var earthViewModel: EarthViewModel

    private lateinit var earthquakeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthViewModel =
            ViewModelProviders.of(this).get(EarthViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var e = EarthquakeFetchr()
        val earthquakeLiveData = e.feachData()
        earthquakeLiveData.observe(this, Observer {
            Log.d("test", "Response received: ${it}")
            earthquakeRecyclerView.adapter = EarthquakeAdapter(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_earthquake_info, container, false)
        earthquakeRecyclerView = view.findViewById(R.id.earthquakeRecyclerView)
        earthquakeRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    companion object {
        fun newInstance() = EarthquakeInfo()
    }

    private inner class EarthquakeHolder(view: View) : RecyclerView.ViewHolder(view) {

        val magButton = view.findViewById(R.id.Earthquake_digree) as Button
        val titleTextView = view.findViewById(R.id.title) as TextView
        val destanceTextView = view.findViewById(R.id.destance) as TextView
        val dateTextView = view.findViewById(R.id.date) as TextView
        val timeTextView = view.findViewById(R.id.time) as TextView
        var earthquake = Earthquake()

        fun bind(earthquake: Earthquake) {
            this.earthquake = earthquake
            magButton.text = this.earthquake.pro.mag.toString()
            titleTextView.text = this.earthquake.pro.title
            destanceTextView.text = this.earthquake.pro.place
            dateTextView.text = this.earthquake.pro.time.toString()
            timeTextView.text = earthquake.pro.time.toString()
        }


    }

    inner class EarthquakeAdapter(var earthquakes: List<Earthquake>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View
            view = layoutInflater.inflate(
                R.layout.earthquake_list,
                parent, false
            )







            return EarthquakeHolder(view)

        }


        //

        override fun getItemCount(): Int {

            return earthquakes.size

        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            val earthquake = earthquakes[position]
            if (holder is EarthquakeHolder)
                holder.bind(earthquake)
        }
    }
}
package com.alsharany.earthquakeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.util.*


class EarthquakeInfo : Fragment() {
    private lateinit var earthViewModel: EarthViewModel
    private lateinit var earthquakeRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // requireActivity().actionBar?.setTitle("Earthquake Report")

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
        progressBar = view.findViewById(R.id.progressbar)
        return view
    }

    companion object {
        fun newInstance() = EarthquakeInfo()
    }

    private inner class EarthquakeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val magButton = view.findViewById(R.id.Earthquake_digree) as Button
        val titleTextView = view.findViewById(R.id.title) as TextView
        val destanceTextView = view.findViewById(R.id.destance) as TextView
        val dateTextView = view.findViewById(R.id.date) as TextView
        val timeTextView = view.findViewById(R.id.time) as TextView
        private var longtude: Double = 0.0
        private var latitude: Double = 0.0

        // private var mapZooming: Double = 0.0
        init {
            view.setOnClickListener(this)
        }

        var earthquake = Earthquake()
        fun bind(earthquake: Earthquake) {
            this.earthquake = earthquake
            setMagButtonShape(this.earthquake.pro.mag)
            if (this.earthquake.pro.title.contains(" of ".toRegex())) {
                destanceTextView.text = this.earthquake.pro.title.split("of")[0] + "of"
                titleTextView.text = this.earthquake.pro.title.split("of")[1]
            } else {
                titleTextView.text = this.earthquake.pro.title
                destanceTextView.text = this.earthquake.pro.place

            }
            // titleTextView.text=this.earthquake.pro.title

            dateTextView.text = converToDate(earthquake.pro.time)
            timeTextView.text = converToTime(earthquake.pro.time)
            setCoordinates(this.earthquake.geo.geos)
        }

        fun setMagButtonShape(mag: Double) {
            magButton.apply {
                text = mag.toBigDecimal().setScale(1, RoundingMode.CEILING).toString()
                when {
                    mag < 4.0 -> setBackgroundResource(R.drawable.mag_buton_low_level_shape)
                    mag < 5.0 -> setBackgroundResource(R.drawable.mag_buton_premed_level_shape)
                    mag <= 6.0 -> setBackgroundResource(R.drawable.mag_buton_medium_level_shape)
                    mag in 6.0..10.0 -> setBackgroundResource(R.drawable.mag_buton_high_level_shape)
                }
            }
        }

        fun converToDate(dateTime: Long): String {
            val calendar = Calendar.getInstance()

            calendar.time = Date(dateTime)

            val earthquakeDate: String = "${calendar.get(Calendar.YEAR)}-" +
                    "${calendar.get(Calendar.MONTH)}-" +
                    "${calendar.get(Calendar.DAY_OF_MONTH)}"

            return earthquakeDate
        }

        fun converToTime(dateTime: Long): String {
            val calendar = Calendar.getInstance()

            calendar.time = Date(dateTime)

            val earthquakeTime: String = "${calendar.get(Calendar.HOUR_OF_DAY)}:" +
                    "${calendar.get(Calendar.MINUTE)}"

            return earthquakeTime
        }

        fun setCoordinates(coordinates: List<Double>) {
            longtude = coordinates[0]
            latitude = coordinates[1]

        }

        override fun onClick(v: View?) {
            val uri = Uri.parse("geo:$latitude,$longtude")
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
            }
            requireActivity().startActivity(intent)

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
        override fun getItemCount(): Int {
            if (earthquakes.size <= 0)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
            return earthquakes.size
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val earthquake = earthquakes[position]
            if (holder is EarthquakeHolder)
                holder.bind(earthquake)
        }
    }
}
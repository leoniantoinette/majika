package com.example.majika.header

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.majika.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [header_menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class header_menu : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var temperatureTextView: TextView
    private var currentTemperature: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header_menu, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // temperature
        temperatureTextView = view.findViewById(R.id.temp)

    }

    override fun onResume() {
        super.onResume()

        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (temperatureSensor != null) {
            sensorManager.registerListener(
                temperatureListener,
                temperatureSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            temperatureTextView.text = "Temperature sensor not available"
        }
    }

    override fun onPause() {
        super.onPause()
        val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(temperatureListener)
    }

    private val temperatureListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                currentTemperature = event.values[0]
                updateTemperatureText()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // do nothing
        }
    }

    private fun updateTemperatureText() {
        temperatureTextView.text = "Temp: ${currentTemperature}°C"
    }

}
package com.afauzi.task_coding.consume_api

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.afauzi.task_coding.R
import com.afauzi.task_coding.consume_api.dataModel.current_weather.ModelCurrentWeather
import com.afauzi.task_coding.consume_api.network.ApiConfig
import com.afauzi.task_coding.databinding.FragmentMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SetTextI18n")
class MainFragment : Fragment() {

    private val LOCATION_PERMISSION_REQ_CODE = 1000
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longtitude: Double = 0.0

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentWeather()
        binding.tvDate.text = currentTime()

        binding.BtnMoreOption.setOnClickListener {
            popupMenu()
        }

    }

    @SuppressLint("RtlHardcoded")
    private fun popupMenu() {

        val popupMenu = PopupMenu(activity, binding.BtnMoreOption, Gravity.RIGHT)
        popupMenu.menuInflater.inflate(com.afauzi.task_coding.R.menu.choose_task_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
//            dialogShowNotPage(it.title)
            when (it.itemId) {
                R.id.consume_api -> {
                    Toast.makeText(activity, "Consume Api", Toast.LENGTH_SHORT).show()
                }
                R.id.api_integration -> {
                    Toast.makeText(activity, "Api Integration", Toast.LENGTH_SHORT).show()
                }
                R.id.local_storage -> {
                    Toast.makeText(activity, "Local Storage", Toast.LENGTH_SHORT).show()
                }
                R.id.form_validator -> {
                    Toast.makeText(activity, "Form Validator", Toast.LENGTH_SHORT).show()
                }
                R.id.life_cycle -> {
                    Toast.makeText(activity, "Activity Life Cycle", Toast.LENGTH_SHORT).show()
                }
                R.id.use_git_basic -> {
                    Toast.makeText(activity, "Use Git Basic", Toast.LENGTH_SHORT).show()
                }
                R.id.sub_routine -> {
                    Toast.makeText(activity, "Sub Routine", Toast.LENGTH_SHORT).show()
                }
            }

            true
        }
        popupMenu.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(
                        activity, """
                        location gps is not enabled,
                        go to settings, enable gps location with high accuracy
                    """.trimIndent(),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Permission Denied
                    Toast.makeText(
                        activity, "You need to grant permission to access location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun getCurrentWeather() {

        // Checking location permission
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request Permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )

            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {
            latitude = it.latitude
            longtitude = it.longitude

            ApiConfig.instanceRetrofit.getCurrentWeather(
                "524901",
                "id",
                "$latitude",
                "$longtitude",
                "5a6f585765c828ed2b2ec4426d9f1325",
                "metric"
            ).enqueue(object : Callback<ModelCurrentWeather> {
                override fun onResponse(
                    call: Call<ModelCurrentWeather>,
                    response: Response<ModelCurrentWeather>
                ) {
                    val locationName = binding.tvLocationName
                    val temperature = binding.tvTemperature
                    val weather = binding.tvWeather
                    val description = binding.tvDesc
                    val speed = binding.tvKecepatanAngin
                    val humadity = binding.tvHumadity

                    locationName.text = response.body()!!.name
                    temperature.text = response.body()!!.main.temp.toInt().toString()
                    speed.text = "Kecepatan Angin: ${response.body()!!.wind.speed}m/s"
                    humadity.text = "Kelembaban: ${response.body()!!.main.humidity}%"

                    for (i in response.body()!!.weather) {
                        weather.text = i.main
                        description.text = i.description
                        if (i.icon == "01d" || i.icon == "01n") {
                            setUPLottieAnim(R.raw.clear_sky)
                        } else if (i.icon == "02d" || i.icon == "02n") {
                            setUPLottieAnim(R.raw.cloud_animation)
                        } else if (i.icon == "03d" || i.icon == "03n") {
                            setUPLottieAnim(R.raw.scattered_clouds)
                        } else if (i.icon == "04d" || i.icon == "04n") {
                            setUPLottieAnim(R.raw.broken_cloud)
                        } else if (i.icon == "09d" || i.icon == "09n") {
                            setUPLottieAnim(R.raw.rain_animation)
                        } else if (i.icon == "10d" || i.icon == "10n") {
                            setUPLottieAnim(R.raw.rain)
                        } else if (i.icon == "11d" || i.icon == "11n") {
                            setUPLottieAnim(R.raw.thunderstorm)
                        }
//                        else if (i.icon == "50d" || i.icon == "50n") {
//
//                        }
                    }
                }

                override fun onFailure(call: Call<ModelCurrentWeather>, t: Throwable) {
                    Toast.makeText(activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })


        }.addOnFailureListener {
            Toast.makeText(activity, "${it.message}", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun currentTime(): String {

        val currentDayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
        val currentMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())
        val currentDayInt = SimpleDateFormat("dd", Locale.getDefault()).format(Date())

        Log.i("date", "$currentDayString, $currentDayInt $currentMonth")

        return "$currentDayString, $currentDayInt $currentMonth"

    }

    private fun setUPLottieAnim(animationRaw: Int) {
        val iconTempAnim = binding.iconTemp

        iconTempAnim.setAnimation(animationRaw)
        iconTempAnim.playAnimation()
        iconTempAnim.repeatCount
    }

}
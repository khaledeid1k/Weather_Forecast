package com.kh.mo.weatherforecast.ui.alert

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.DialogOfAlertBinding
import com.kh.mo.weatherforecast.databinding.FragmentAlertBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.ui.base.BaseViewModelFactory
import com.kh.mo.weatherforecast.utils.Constants
import com.kh.mo.weatherforecast.utils.Constants.NOTIFICATION_DATA
import com.kh.mo.weatherforecast.utils.createDialog
import com.kh.mo.weatherforecast.utils.makeGone
import com.kh.mo.weatherforecast.utils.makeVisible
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class AlertFragment : Fragment() {


    private lateinit var binding: FragmentAlertBinding
    private lateinit var dialogOfAlert: DialogOfAlertBinding
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager
    private lateinit var alertViewModel: AlertViewModel
    private lateinit var startTimeValue: String
    private lateinit var endTimeValue: String
    private lateinit var startDateValue: String
    private lateinit var endDateValue: String
    private val alertAdapter: AlertAdapter by lazy {
        AlertAdapter(alertViewModel)
    }
    private var locationData: LocationData? = null
    private val alertCountryAdapter: AlertCountryAdapter by lazy {
        AlertCountryAdapter(alertViewModel)
    }
    private val calendarAlert: Calendar by lazy {
        Calendar.getInstance()
    }
    var flagDataComplete = false
    var flagStart = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_alert, container, false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        addAlertAdapterAdapter()
        getAlerts()
        addNewAlert()

    }

    private fun getAlerts() {
        lifecycleScope.launch {
            alertViewModel.alerts.collect {
                showLottieNoAlerts(it.size)
                alertAdapter.setItems(it)
            }
        }
    }
    private fun showLottieNoAlerts(size: Int) {
        binding.lottieAnimationNoAlarms.apply {
            if (size == 0) makeVisible() else makeGone()
        }
    }
    private fun intiViewModel() {
        val alertViewModelFactory =
            BaseViewModelFactory(
                RepoIm.getRepoImInstance
                    (
                    LocalDataImp.getLocalDataImpInstance(requireContext()),
                    RemoteDataImp.getRemoteDataImpInstance(requireContext())
                )
            )
        alertViewModel = ViewModelProvider(
            this,
            alertViewModelFactory
        )[AlertViewModel::class.java]
    }

    private fun addAlertAdapterAdapter() {
        binding.recycleAlarms.adapter = alertAdapter
    }

    private fun addNewAlert() {
        binding.floatingActionButtonAlarm.setOnClickListener {
            createDialog(
                getString(R.string.Add_new_alarm),
                getString(R.string.chose_your_start_and_end_time),
                view = createViewForDialog(),
                context = requireContext(),
                sure = {
                    if (flagDataComplete && locationData != null) {
                        val alertEntity = AlertEntity(
                            locationData!!.lat,
                            locationData!!.lon,
                            locationData!!.nameOfCountry,
                            locationData!!.nameOfCity,
                            startDateValue,
                            startTimeValue,
                            endDateValue,
                            endTimeValue
                        )
                        Log.d("TAG", "addNewAlert: $alertEntity")
                        alertViewModel.saveAlert(alertEntity)
                        createAlarManager(locationData)
                    }
                },
                cancel = {})
        }
    }


    private fun createViewForDialog(): View {
        dialogOfAlert = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.dialog_of_alert,
            null, false
        )
        alertViewModel.requestFavorites()
        getFavorites()
        getLocationData()
        clickStartTime()
        clickEndTime()
        addAlertCountryAdapterAdapter()
        return dialogOfAlert.root
    }

    private fun addAlertCountryAdapterAdapter() {
        dialogOfAlert.recycleAlertCountry.adapter = alertCountryAdapter
    }

    private fun clickStartTime() {
        dialogOfAlert.apply {
            startTimeCard.setOnClickListener {
                showDatePicker { date ->
                    if (date.isNotEmpty())
                        showTimePicker { time ->
                            if (date.isNotEmpty() && time.isNotEmpty()) {
                                fromDate.text = date
                                fromTime.text = time
                                startDateValue=date
                                startTimeValue=time
                                flagDataComplete = true
                                clickEndTime()
                            }
                        }
                }

            }

        }

    }

    private fun clickEndTime() {
        dialogOfAlert.apply {
            endTimeCard.setOnClickListener {
                showDatePicker { date ->
                    if (date.isNotEmpty())
                        showTimePicker { time ->
                            if (date.isNotEmpty() && time.isNotEmpty()) {
                                endDate.text = date
                                endTime.text = time
                                endDateValue=date
                                endTimeValue=time

                            }
                        }
                }

            }

        }

    }


    private fun preventUserShowsPreviousDate(constraintTime: (today: Long, constraintsBuilder: CalendarConstraints) -> Unit) {
        val today: Long = MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()
        constraintTime(today, constraintsBuilder)
    }

    private fun showDatePicker(getChosenDate: (selectedDate: String) -> Unit) {
        preventUserShowsPreviousDate { today, constraintsBuilder ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(today)
                .setCalendarConstraints(constraintsBuilder)
                .build()
            picker.addOnPositiveButtonClickListener { getChosenDate(formatDate(it)) }
            picker.show(requireActivity().supportFragmentManager, Constants.DATE)

        }

    }

    private fun showTimePicker(getChosenTime: (selectedTime: String) -> Unit) {

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText(getString(R.string.select_time))
            .build()
        timePicker.show(requireActivity().supportFragmentManager, Constants.TIME)

        timePicker.addOnPositiveButtonClickListener {
            getChosenTime(formatTime(timePicker))
        }

    }


    private fun formatTime(timePicker: MaterialTimePicker): String {
        val selectedHour = timePicker.hour
        val selectedMinute = timePicker.minute
        val selectedCalendar = Calendar.getInstance().apply {
            if (flagStart){
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
                flagStart=false
            }

            calendarAlert.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendarAlert.set(Calendar.MINUTE, selectedMinute)
        }

        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return timeFormat.format(selectedCalendar.time)

    }

    private fun formatDate(selectedDate: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val selectedCalendar = Calendar.getInstance().apply {
            timeInMillis = selectedDate
            if (flagStart) {
                calendarAlert.timeInMillis = selectedDate
            }
        }
        return dateFormat.format(selectedCalendar.time)
    }


    private fun createAlarManager(locationData: LocationData?) {
        Log.d("TAG", "createAlarManager: ")
        alarmManager = requireContext().getSystemService(AlarmManager::class.java)
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        intent.putExtra(NOTIFICATION_DATA, locationData)
        pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        Log.d("TAG", "createAlarManager: ${calendarAlert.timeInMillis}")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendarAlert.timeInMillis,
            pendingIntent
        )
    }

    private fun getFavorites() {
        lifecycleScope.launch {
            alertViewModel.favorites.collect {
                alertCountryAdapter.setItems(it)
            }
        }
    }

    private fun getLocationData() {
        alertViewModel.favoritesEvent.observe(viewLifecycleOwner) {
            locationData = it
            Toast.makeText(requireContext(), "You select ${it.nameOfCity} ", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
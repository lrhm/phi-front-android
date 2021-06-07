package xyz.lrhm.phiapp.ui.scheduleDayScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.core.util.isSameDay
import xyz.lrhm.phiapp.databinding.FragmentScheduleDayBinding

@AndroidEntryPoint
class ScheduleDayFragment : Fragment() {

    //    val args: ScheduleDayFragmentArgs by navArgs()
    private var columnCount = 1

    lateinit var binding: FragmentScheduleDayBinding
    val viewModel: ScheduleDayViewModel by viewModels({ requireActivity() })

    val formatter = PersianDateFormat("j F Y")
    val today = PersianDate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScheduleDayBinding.inflate(inflater, container, false)

        observeLiveDatas()

//        binding.dateTextView.setPersianTextHelper()

//        binding.dateTextView.typeface =
//            Typeface.createFromAsset(requireActivity().assets, "irsansz.ttf");
//
//        binding.dateBoldText.typeface =
//            Typeface.createFromAsset(requireActivity().assets, "irsansz.ttf");

        binding.nextDayButton.setOnClickListener {
            val pDate = PersianDate(viewModel.selectedDate.value!!.toDate())
            pDate.addDay(1)
            viewModel.setSelectedDate(pDate)
        }

        binding.prevDayButton.setOnClickListener {
            val pDate = PersianDate(viewModel.selectedDate.value!!.time - 24 * 60 * 60 * 1000)
            viewModel.setSelectedDate(pDate)
        }

        viewModel.appRepository.user.observe(viewLifecycleOwner) {

            viewModel.setSelectedDate(viewModel.selectedDate.value!!)
        }

        return binding.root
    }

    fun observeLiveDatas() {
        viewModel.selectedDate.observe(viewLifecycleOwner) {
            if (today.isSameDay(it)) {
                binding.dateBoldText.text = "امروز" + " " + it.dayName() + " "

                binding.todayButton.text = "امروز"
                binding.todayButton.setOnClickListener {

                }
            } else {
                binding.todayButton.text = "برو به امروز"
                binding.todayButton.setOnClickListener {
                    viewModel.setSelectedDate(today)
                }
                binding.dateBoldText.text = it.dayName() + " "

            }
            binding.dateTextView.text = formatter.format(it)
        }

        viewModel.selectedDay.observe(viewLifecycleOwner) { day ->

            Timber.d("day is ${day}")
            if (day == null) {
                binding.evalutioanButton.visibility = View.GONE
                setRestDay(true)
            } else {
                val enabledList = day.parameters!!.filter { it!!.enabled == true }
                initQuestionnareSection(day!!)

                if (enabledList.isEmpty()) {

                    setRestDay(true)
                } else {

                    setRestDay(false)
                    with(binding.recyclerView) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        adapter = ExerciseScheduleRecyclerViewAdapter(
                            enabledList,
                            viewModel.getExercises(), this@ScheduleDayFragment,
                            day
                        )

                    }

                }

            }

        }

    }

    fun initQuestionnareSection(day: APIQuery.Day) {

        val questionares = viewModel.getQuestionnairesForDayId(day.id)

        Timber.d("questionares are ${questionares}")
        if(questionares.isEmpty()){
            binding.evalutioanButton.visibility = View.GONE
        }else{
            binding.evalutioanButton.visibility = View.VISIBLE

            binding.evalutioanButton.setOnClickListener {
                val args = MobileNavigationDirections.actionGlobalQuestionnaireSelectScreen(
                    viewModel.selectedDay.value!!.id
                )
                findNavController().navigate(args)
            }
        }
    }

    fun setRestDay(isRest: Boolean) {
        binding.noExerciseTextView.visibility = if (isRest) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isRest) View.GONE else View.VISIBLE

    }


}
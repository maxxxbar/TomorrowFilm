package ws.worldshine.tomorrowfilm.ui.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import ws.worldshine.tomorrowfilm.R
import ws.worldshine.tomorrowfilm.databinding.BottomSheetFiltersBinding
import ws.worldshine.tomorrowfilm.discover.SORTING_KEY
import ws.worldshine.tomorrowfilm.discover.Sorting
import ws.worldshine.tomorrowfilm.utils.findNavController
import javax.inject.Inject

class FiltersBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val SORT_BY_KEY = "SORT_BY_KEY"
    }

    /*ViewModel*/
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FiltersBottomSheetViewModel> { viewModelFactory }

    /*Binding*/
    private var _binding: BottomSheetFiltersBinding? = null
    private val binding get() = _binding!!

    /*Views*/
    private lateinit var startRadioButton: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var llPopularity: LinearLayout
    private lateinit var llAvg: LinearLayout
    private lateinit var rbPopularity: RadioButton
    private lateinit var rbAvg: RadioButton
    private lateinit var llAllFilters: LinearLayout
    private lateinit var btnReset: Button
    private lateinit var btnApply: Button
    private lateinit var btnCLose: Button

    /**/
    private val TAG = javaClass.simpleName
    private var sortBy = Sorting.POPULARITY.sortBy
    private var currentSortBy: Sorting? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    @ExperimentalPagingApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = BottomSheetFiltersBinding.inflate(inflater, container, false)
        initialSetupViews()
        initialSetupAllClickListeners()

        return binding.root
    }

    private fun initialSetupViews() {
        radioGroup = binding.rgFilter
        llPopularity = binding.llPopularityFilter
        llAvg = binding.llVoteAvgFilter
        rbPopularity = binding.rbPopularityFilter
        rbAvg = binding.rbVoteAvgFilter
        llAllFilters = binding.llAllFilters
        btnReset = binding.btnReset
        startRadioButton = radioGroup.getChildAt(0) as RadioButton
        btnApply = binding.btnApply
        btnCLose = binding.btnClose
    }

    private fun initialSetupAllClickListeners() {
        initialSetupClickListenerForAllFiltersLinearLayout()
        initialSetupClickListenerForCloseButton()
        initialSetupClickListenerForApplyButton()
        initialSetupClickListenerForResetButton()
        initialSetupOnCheckedChangeListenerForRadioGroup()
        initialSetupCurrentPosition()
    }

    private fun initialSetupCurrentPosition() {
        currentSortBy = viewModel.getCurrentSortBy()
        currentSortBy?.let {
            val radioButton = radioGroup.getChildAt(it.ordinal) as RadioButton
            switchColorForFilters(radioButton.id)
            radioButton.isChecked = true
        }
    }

    private fun initialSetupClickListenerForResetButton() {
        btnReset.setOnClickListener {
            switchColorForFilters(startRadioButton.id)
            startRadioButton.isChecked = true
        }
    }

    private fun initialSetupClickListenerForApplyButton() {
        btnApply.setOnClickListener {
            viewModel.setSorting(sortBy)
            val bundle = Bundle().apply { putString(SORT_BY_KEY, sortBy) }
            setFragmentResult(SORTING_KEY, bundle)
            findNavController().navigateUp()
        }

    }

    private fun setColorForActiveViews(views: List<View>) {
        views.forEach {
            it.background = ContextCompat.getDrawable(requireContext(), R.color.ll_filter_actived_background_color)
        }
    }

    private fun clearColors(views: List<View>) {
        views.forEach {
            it.background = null
        }
    }

    private fun setSorting(childCount: Int) {
        /*
        * 0 - По популярности
        * 1 - По голосам в среднем
        * */
        if (childCount < Sorting.values().size) {
            sortBy = Sorting.values()[childCount].sortBy
        }
    }

    private fun initialSetupClickListenerForCloseButton() {
        btnCLose.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun initialSetupClickListenerForAllFiltersLinearLayout() {
        for (i in 0 until llAllFilters.childCount) {
            llAllFilters.getChildAt(i).setOnClickListener {
                val radioButton: RadioButton = radioGroup.getChildAt(i) as RadioButton
                switchColorForFilters(radioButton.id)
                radioButton.isChecked = true
            }
        }
    }

    private fun switchColorForFilters(radioButton: Int) {
        for (i in 0 until radioGroup.childCount) {
            if (radioGroup.getChildAt(i).id == radioButton) {
                setColorForActiveViews(listOf(radioGroup.getChildAt(i), llAllFilters.getChildAt(i)))
                setSorting(i)
            } else {
                clearColors(listOf(radioGroup.getChildAt(i), llAllFilters.getChildAt(i)))
            }
        }

    }

    private fun initialSetupOnCheckedChangeListenerForRadioGroup() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            switchColorForFilters(checkedId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
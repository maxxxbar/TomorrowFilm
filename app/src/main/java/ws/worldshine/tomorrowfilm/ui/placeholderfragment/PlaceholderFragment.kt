package ws.worldshine.tomorrowfilm.ui.placeholderfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ws.worldshine.tomorrowfilm.R
import ws.worldshine.tomorrowfilm.databinding.FragmentPlaceholderBinding

class PlaceholderFragment : Fragment(R.layout.fragment_placeholder) {

    private lateinit var binding: FragmentPlaceholderBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlaceholderBinding.inflate(layoutInflater)
        return binding.root
    }
}
package com.example.mymovies.etc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovies.R
import com.example.mymovies.ui.DemoFragmentFirst
import com.example.mymovies.ui.DemoFragmentTwo
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CollectionDemoFragment : Fragment() {
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.collection_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        demoCollectionAdapter = DemoCollectionAdapter(this)
        viewPager2 = view.findViewById(R.id.pager)
        viewPager2.adapter = demoCollectionAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "TAB ${position + 1}"
        }.attach()
    }
}

private class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
/*        val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }*/

        when (position) {
            0 -> return DemoFragmentFirst()
            1 -> return DemoFragmentTwo()

        }
        return DemoObjectFragment()
    }

}

private const val ARG_OBJECT = "object"

class DemoObjectFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey(ARG_OBJECT)
        }?.apply {
            val textView = view.findViewById<TextView>(R.id.text1)
            textView.text = "TAB ${getInt(ARG_OBJECT)}"
        }
    }
}

package com.elyeproj.bottombarfragmentsswitching

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_container.*

class ContainerFragment : Fragment() {

    companion object {
        const val KEY = "FragmentKey"
        const val COLOR = "FragmentColor"
        fun newInstance(key: String, color: String): Fragment {
            val fragment = ContainerFragment()
            val argument = Bundle()
            argument.putString(KEY, key)
            argument.putString(COLOR, color)
            fragment.arguments = argument
            return fragment
        }
    }

    private var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            count = childFragmentManager.backStackEntryCount
        }
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val key = it.getString(KEY)
            text_title.text = key
            container.setBackgroundColor(Color.parseColor(it.getString(COLOR)))


            button_open_child_fragment.setOnClickListener {
                val childKey = key + count.toString()
                childFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, ChildFragment.newInstance(childKey), childKey)
                    .addToBackStack(childKey)
                    .commit()
            }
        }

        childFragmentManager.addOnBackStackChangedListener { count = childFragmentManager.backStackEntryCount }
    }
}

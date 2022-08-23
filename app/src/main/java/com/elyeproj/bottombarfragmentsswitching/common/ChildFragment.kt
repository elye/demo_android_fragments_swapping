package com.elyeproj.bottombarfragmentsswitching.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.elyeproj.bottombarfragmentsswitching.R

class ChildFragment : Fragment() {

    companion object {
        const val KEY = "FragmentKey"
        fun newInstance(key: String): Fragment {
            val fragment = ChildFragment()
            val argument = Bundle()
            argument.putString(KEY, key)
            fragment.arguments = argument
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            view.findViewById<TextView>(R.id.child_text_title).text = it.getString(KEY)
        }
    }
}

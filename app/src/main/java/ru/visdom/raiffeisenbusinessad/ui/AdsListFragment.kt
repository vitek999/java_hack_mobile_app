package ru.visdom.raiffeisenbusinessad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.visdom.raiffeisenbusinessad.R
import ru.visdom.raiffeisenbusinessad.databinding.FragmentAdsLsitBinding

class AdsListFragment : Fragment() {

    private lateinit var binding: FragmentAdsLsitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ads_lsit, container, false)
        init()
        return binding.root
    }

    private fun init() {
        with(binding.fullRecommendationToolbar){
            setTitle(R.string.my_ad)
            setTitleTextColor(context.resources.getColor(R.color.colorAccent))
            setTitleMargin(32,16,0,16)

        }

    }
}
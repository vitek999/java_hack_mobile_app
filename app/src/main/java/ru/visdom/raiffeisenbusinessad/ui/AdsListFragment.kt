package ru.visdom.raiffeisenbusinessad.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.visdom.raiffeisenbusinessad.R
import ru.visdom.raiffeisenbusinessad.adapter.AdsAdapter
import ru.visdom.raiffeisenbusinessad.databinding.FragmentAdsLsitBinding
import ru.visdom.raiffeisenbusinessad.viewmodels.AdListViewModel

class AdsListFragment : Fragment() {

    private lateinit var binding: FragmentAdsLsitBinding

    private var progressDialog: ProgressDialog? = null

    private val viewModel: AdListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, AdListViewModel.Factory(activity.application))
            .get(AdListViewModel::class.java)
    }


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
        with(binding.fullRecommendationToolbar) {
            setTitle(R.string.my_ad)
            setTitleTextColor(context.resources.getColor(R.color.colorAccent))
            inflateMenu(R.menu.ads_list_menu)
        }

        val adapter = AdsAdapter()
        binding.adsList.adapter = adapter

        viewModel.isProgressShow.observe(this, Observer<Boolean>{isProgress ->
            binding.adSwipeRefresh.isRefreshing = isProgress
        })

        viewModel.ads.observe(this, Observer {
            it?.let{
                adapter.data = it
                //binding.adSwipeRefresh.isRefreshing = false
            }
        })

        binding.adSwipeRefresh.setOnRefreshListener {
            viewModel.update()
        }

        viewModel.update()
    }

    private fun showDialog() {
        progressDialog = ProgressDialog.show(this.activity, "", getString(R.string.please_wait))
    }

    private fun hideDialog() = progressDialog?.dismiss()
}
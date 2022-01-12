package com.thusee.tandemlisting.views.community

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thusee.tandemlisting.R
import com.thusee.tandemlisting.databinding.ActivityCommunityBinding
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import com.thusee.tandemlisting.views.community.adapter.CommunityAdapter
import com.thusee.tandemlisting.views.community.event.LikeButtonClickListener
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CommunityActivity: AppCompatActivity(), LikeButtonClickListener {

    private val viewModel: CommunityViewModel by inject()
    private lateinit var adapter: CommunityAdapter
    private lateinit var binding: ActivityCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        updateAdapter()

    }

    private fun initView() {
        rowLoadingAnim.visibility = View.GONE
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }

        adapter = CommunityAdapter( DataDiffCallbacks, this)
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.item_divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun updateAdapter() {
        lifecycleScope.launch {
            viewModel.fetchRemoteDataList().collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                rowLoadingAnim.visibility = View.VISIBLE
            } else {
                rowLoadingAnim.visibility = View.GONE
            }
        }

    }

    override fun likeButtonClick(dataMapper: CommunityDataMapper) {
        viewModel.like(dataMapper)
        adapter.notifyDataSetChanged()
    }

}
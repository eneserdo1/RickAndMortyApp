package com.app.rickandmortyapp.ui.DetailScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.rickandmortyapp.R
import com.app.rickandmortyapp.databinding.ActivityDetailBinding
import com.app.rickandmortyapp.ui.DetailScreen.adapter.EpisodeRecyclerviewAdapter
import com.app.rickandmortyapp.util.alertBox
import com.app.rickandmortyapp.util.clickListener
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels<DetailViewModel>()
    private lateinit var binding : ActivityDetailBinding
    private lateinit var episodeAdapter: EpisodeRecyclerviewAdapter
    var id:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getStringExtra("id")
        id?.let {
            viewModel.fetchCharacterDetail(it)
        }

        initRecyclerview()
        observers()
        buttonsListener()
    }

    private fun initRecyclerview() {
        episodeAdapter = EpisodeRecyclerviewAdapter()
        binding.episodeRecyclerview.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity)
        }
    }

    private fun buttonsListener() {
        binding.openExpandableLayout.clickListener{
            if (binding.episodeRecyclerview.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.expandableCardview, AutoTransition())
                binding.episodeRecyclerview.visibility=View.VISIBLE
                binding.arrow.setBackgroundResource(R.drawable.arrow_up_24)
            }else{
                TransitionManager.beginDelayedTransition(binding.expandableCardview,AutoTransition())
                binding.episodeRecyclerview.visibility=View.GONE
                binding.arrow.setBackgroundResource(R.drawable.arrow_down_24)
            }
        }

        binding.closeBtn.clickListener {
            onBackPressed()
        }
    }

    private fun observers() {
        viewModel._mutableCharacterList.observe(this, Observer { response ->
            if (response != null) {
                binding.apply {
                    nameTxt.text = response.name
                    descriptionTxt.text = "${response.status}, ${ response.species }"
                    genderTxt.text = response.gender
                    Glide.with(this@DetailActivity).load(response.image).into(profilePhoto)
                }
                episodeAdapter.setList(response.episode as ArrayList<String>)
            } else {
                alertBox(getString(R.string.hata),getString(R.string.bir_hata_olustu),this)
            }
        })

        viewModel._loading.observe(this, Observer {
            if (it){
                binding.progressbar.visibility = View.VISIBLE
            }else{
                binding.progressbar.visibility = View.GONE
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_top)
    }
}
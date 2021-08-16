package com.app.rickandmortyapp.ui.MainScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.app.rickandmortyapp.R
import com.app.rickandmortyapp.databinding.ActivityMainBinding
import com.app.rickandmortyapp.model.CharacterListResponse.Results
import com.app.rickandmortyapp.ui.DetailScreen.DetailActivity
import com.app.rickandmortyapp.ui.MainScreen.adapter.CharacterRecyclerviewAdapter
import com.app.rickandmortyapp.ui.MainScreen.adapter.ItemClickListener
import com.app.rickandmortyapp.util.alertBox
import com.app.rickandmortyapp.util.hideProgressDialog
import com.app.rickandmortyapp.util.showProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterAdapter: CharacterRecyclerviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerview()
        observers()
    }


    private fun observers() {

        lifecycleScope.launch {
            viewModel._characters.collect {
                characterAdapter.submitData(it)
            }
        }

        characterAdapter.addLoadStateListener { loadState->
            if (loadState.refresh is LoadState.Loading){
                this.showProgressDialog()
            }else{
                hideProgressDialog()
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    alertBox(getString(R.string.hata),getString(R.string.bir_hata_olustu),this)
                }
            }
        }

    }


    private fun setupRecyclerview() {
        characterAdapter = CharacterRecyclerviewAdapter(object : ItemClickListener {
            override fun selectedCharacter(character: Results) {
                val intent = Intent(this@MainActivity,DetailActivity::class.java)
                intent.putExtra("id",character.id.toString())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom)
            }
        })
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            adapter = characterAdapter
        }

    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }
}
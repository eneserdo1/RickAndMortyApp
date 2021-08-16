package com.app.rickandmortyapp.ui.MainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.imkbapp.model.Status
import com.app.rickandmortyapp.data.network.ApiService
import com.app.rickandmortyapp.data.remote.PagingDataSource
import com.app.rickandmortyapp.data.repository.RemoteRepository
import com.app.rickandmortyapp.model.CharacterListResponse.CharacterList
import com.app.rickandmortyapp.model.CharacterListResponse.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val dataSource: PagingDataSource) : ViewModel() {


    init {
        getCharacterList()
    }

    val _characters : Flow<PagingData<Results>> = getCharacterList()

    private fun getCharacterList() : Flow<PagingData<Results>>{
        return  Pager(PagingConfig(pageSize = 33)) {
            dataSource
        }.flow.cachedIn(viewModelScope)
    }



}
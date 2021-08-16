package com.app.rickandmortyapp.data.remote


import com.app.imkbapp.model.Resource
import com.app.rickandmortyapp.data.network.ApiService
import com.app.rickandmortyapp.model.CharacterDetailResponse.CharacterDetail
import com.app.rickandmortyapp.model.CharacterListResponse.CharacterList
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService){


    suspend fun getCharacterDetail(id:String): Resource<CharacterDetail> {
        val response = apiService.getSingleCharacters(id)
        return Resource.success(response)
    }

}
package com.app.rickandmortyapp.data.repository


import com.app.imkbapp.model.Resource
import com.app.imkbapp.model.Status
import com.app.rickandmortyapp.data.remote.RemoteDataSource
import com.app.rickandmortyapp.model.CharacterDetailResponse.CharacterDetail
import com.app.rickandmortyapp.model.CharacterListResponse.CharacterList
import kotlinx.coroutines.flow.flow
import  kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class RemoteRepository @Inject constructor(val remoteDataSource: RemoteDataSource){


    suspend fun fetchCharacterDetail(id:String) :Flow<Resource<CharacterDetail>>{
        return flow {
            emit(Resource.loading())
            try {
                emit(remoteDataSource.getCharacterDetail(id))
            }catch (e:Exception){
                emit(Resource.error(e.message.toString(), null))
            }
        }
    }

}
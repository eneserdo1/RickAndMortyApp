package com.app.rickandmortyapp.model.CharacterListResponse

import com.google.gson.annotations.SerializedName


data class CharacterList (

    @SerializedName("info") val info : Info,
    @SerializedName("results") val results : List<Results>
)
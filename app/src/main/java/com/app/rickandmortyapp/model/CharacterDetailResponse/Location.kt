package com.app.rickandmortyapp.model.CharacterDetailResponse

import com.google.gson.annotations.SerializedName


data class Location (

	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String
)
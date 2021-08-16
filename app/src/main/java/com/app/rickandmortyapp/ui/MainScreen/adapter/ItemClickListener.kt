package com.app.rickandmortyapp.ui.MainScreen.adapter

import com.app.rickandmortyapp.model.CharacterListResponse.Results

interface ItemClickListener {

    fun selectedCharacter(character: Results)

}
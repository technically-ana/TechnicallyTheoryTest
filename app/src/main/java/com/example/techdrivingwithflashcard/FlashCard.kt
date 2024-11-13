package com.example.techdrivingwithflashcard

data class FlashCard (
    val statement: String,
    val correct: Array<String>,
    val wrong: Array<String>) {

    fun randomCorrect(): String {
        return statement + " " + correct[correct.indices.random()]
    }
    fun randomWrong(): String {
        return statement + " " + wrong[wrong.indices.random()]
    }

}
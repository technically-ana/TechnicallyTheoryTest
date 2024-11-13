package com.example.techdrivingwithflashcard

data class RandomStatement (
    val flashCardStatement: String,
    val flashCardIsCorrect: Boolean) {

    companion object RandomStatement {

        fun fromFlashCard(flashCard: FlashCard): com.example.techdrivingwithflashcard.RandomStatement {
            val randomStatement = when((0..1).random()) {
                0 -> RandomStatement(flashCard.randomWrong(), flashCardIsCorrect = false)
                1 -> RandomStatement(flashCard.randomCorrect(), flashCardIsCorrect = true)
                else -> throw NoSuchElementException("Failed to generate statement.")
            }
            return randomStatement
        }
    }

}
package com.example.techdrivingwithflashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techdrivingwithflashcard.ui.theme.TechnicallyTheoryTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechnicallyTheoryTestTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) { FlashCardApp() }
            }
        }
    }
}

@Composable
fun CompleteLayout(modifier: Modifier = Modifier) {

    val theoryQuestions = Data.data
        .map { it.split(",") }
        .map { line ->
            FlashCard(
                line[0],
                line[1].split(";").toTypedArray(),
                line[2].split(";").toTypedArray()
            )
        }
        .map { RandomStatement.fromFlashCard(it) }
    val amountOfQuestions = theoryQuestions.size
    val finish = stringResource(id = R.string.hooray)

    var index by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var statement by remember { mutableStateOf(theoryQuestions[index].flashCardStatement) }
    var note by remember { mutableStateOf("") }
    var answerIsCorrect by remember { mutableStateOf(theoryQuestions[index].flashCardIsCorrect) }

    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        Row (modifier = modifier, horizontalArrangement = Arrangement.Absolute.Center) {
            QuestionNumber(index, modifier)
            Score(score, modifier)
        }
        FlashCard(statement = statement, note = note)
        Row (verticalAlignment = Alignment.Bottom, modifier = modifier) {
            OutlinedButton(
                onClick =
                {
                    index += 1
                    score += checkAnswer(answerIsCorrect, userChoice = true)
                    statement = if (amountOfQuestions != index) theoryQuestions[index].flashCardStatement else finish
                    note = if (score < 0) "I'm not judging." else ""
                    answerIsCorrect = if (amountOfQuestions != index) theoryQuestions[index].flashCardIsCorrect else true
                },
                enabled = index != amountOfQuestions,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(text = "Yes", fontSize = 36.sp, fontFamily = FontFamily.Monospace)
            }
            OutlinedButton(
                onClick =
                {
                    index += 1
                    score += checkAnswer(answerIsCorrect, userChoice = false)
                    statement = if (amountOfQuestions != index) theoryQuestions[index].flashCardStatement else finish
                    note = if (score < 0) "I hope you can do better." else ""
                    answerIsCorrect = if (amountOfQuestions != index) theoryQuestions[index].flashCardIsCorrect else true
                },
                enabled = index != amountOfQuestions,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(text = " No ", fontSize = 36.sp, fontFamily = FontFamily.Monospace)
            }
        }
    }
}
@Composable
fun FlashCard(statement: String, note: String) {
    Statement(statement = statement)
    Note(note = note)
}

@Composable
fun Statement(statement: String) {
    Text(
        text = statement,
        textAlign = TextAlign.Center,
        fontSize = 36.sp,
        lineHeight = 42.sp,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
    )

}
@Composable
fun Note(note: String) {
    Text(text = note, fontFamily = FontFamily.Monospace)
}
@Composable
fun Score(scoreValue: Int, modifier: Modifier) {
    Text(
        text = "Score: $scoreValue",
        modifier.padding(vertical = 36.dp),
        textAlign = TextAlign.Left,
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun QuestionNumber(number: Int, modifier: Modifier) {
    Text(
        text = "Question " + (number + 1).toString(),
        modifier.padding(vertical = 36.dp),
        textAlign = TextAlign.Right,
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace
    )
}

fun checkAnswer(answerIsCorrect: Boolean, userChoice: Boolean): Int {
    return if (answerIsCorrect == userChoice) 1 else -1
}



@Preview(showBackground = true)
@Composable
fun FlashCardApp() {
    TechnicallyTheoryTestTheme {
        CompleteLayout(Modifier.padding(vertical = 16.dp, horizontal = 16.dp))
    }
}
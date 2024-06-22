package org.example.multicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorContent()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    CalculatorContent()
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalculatorContent() {
    val displayState = remember { mutableStateOf("0") }

    Scaffold(
        backgroundColor = Color.LightGray,
        content = {
            CalcView(displayState)
        }
    )
}

@Composable
fun CalcView(displayState: MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(8.dp)
                .background(Color.White)
        ) {
            CalcDisplay(displayState)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Numeric Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Rows of Numeric Buttons
            for (i in 2 downTo 0) {
                CalcRow(startNum = i * 3 + 1, numButtons = 3, displayState = displayState)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Zero, Equals, and Clear Button Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CalcNumericButton(number = 0, displayState = displayState)
                CalcEqualsButton(displayState = displayState)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Operation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CalcOperationButton(operation = "/", displayState = displayState)
            CalcOperationButton(operation = "*", displayState = displayState)
            CalcOperationButton(operation = "-", displayState = displayState)
            CalcOperationButton(operation = "+", displayState = displayState)
        }
    }
}

@Composable
fun CalcRow(startNum: Int, numButtons: Int, displayState: MutableState<String>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        for (i in startNum until startNum + numButtons) {
            CalcNumericButton(number = i, displayState = displayState)
        }
    }
}

@Composable
fun CalcDisplay(displayState: MutableState<String>) {
    Text(
        text = displayState.value,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        color = Color.Black,
        textAlign = TextAlign.End
    )
}

@Composable
fun CalcNumericButton(number: Int, displayState: MutableState<String>, span: Int = 1) {
    Button(
        onClick = { displayState.value = if (displayState.value == "0") "$number" else "${displayState.value}$number" },
        modifier = Modifier
            .padding(4.dp)
            .width(if (span == 2) 160.dp else 80.dp)
            .height(80.dp)
            .background(color = Color.LightGray),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Text(text = "$number", style = MaterialTheme.typography.body1)
    }
}

@Composable
fun CalcOperationButton(operation: String, displayState: MutableState<String>) {
    Button(
        onClick = { displayState.value = "${displayState.value} $operation " },
        modifier = Modifier
            .padding(4.dp)
            .width(80.dp)
            .height(80.dp)
            .background(color = Color.LightGray),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Text(text = operation, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun CalcEqualsButton(displayState: MutableState<String>) {
    Button(
        onClick = {
            try {
                val result = evaluateExpression(displayState.value)
                displayState.value = result.toString()
            } catch (e: ArithmeticException) {
                displayState.value = "Error"
            }
        },
        modifier = Modifier
            .padding(4.dp)
            .width(80.dp)
            .height(80.dp)
            .background(color = Color.LightGray),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Text(text = "=", style = MaterialTheme.typography.body1)
    }
}

fun evaluateExpression(expression: String): Int {
    val parts = expression.split(" ")
    val left = parts[0].toInt()
    val right = parts[2].toInt()
    val operator = parts[1]

    return when (operator) {
        "+" -> left + right
        "-" -> left - right
        "*" -> left * right
        "/" -> left / right
        else -> throw IllegalArgumentException("Unknown operator")
    }
}

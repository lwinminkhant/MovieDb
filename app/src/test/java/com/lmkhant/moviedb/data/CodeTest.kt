import java.util.Stack

fun main() {
    val input = "9 - 5 / ( 8 - 3 ) * 2 + 6" // Replace with your arithmetic expression
    //val tokens = input.split("\\d".toRegex())
    val tokens = input.split("\\s+".toRegex())
    print(tokens)
    try {
        val result = evaluateExpression(input)
        println("$input = $result")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}

fun evaluateExpression(expression: String): Double {
    val postfix = infixToPostfix(expression)
    return evaluatePostfix(postfix)
}

fun infixToPostfix(expression: String): List<String> {
    val output = mutableListOf<String>()
    val stack = Stack<String>()
    val operators = setOf("+", "-", "*", "/")

    val tokens = expression.split("\\s+".toRegex())

    for (token in tokens) {
        when {
            token.matches(Regex("\\d+(\\.\\d+)?")) -> output.add(token) // Operand
            token == "(" -> stack.push(token)
            token == ")" -> {
                while (stack.isNotEmpty() && stack.peek() != "(") {
                    output.add(stack.pop())
                }
                if (stack.isEmpty() || stack.pop() != "(") {
                    throw IllegalArgumentException("Mismatched parentheses")
                }
            }
            token in operators -> {
                while (stack.isNotEmpty() && stack.peek() in operators && precedence(token) <= precedence(stack.peek())) {
                    output.add(stack.pop())
                }
                stack.push(token)
            }
            else -> throw IllegalArgumentException("Invalid token: $token")
        }
    }

    while (stack.isNotEmpty()) {
        if (stack.peek() == "(") {
            throw IllegalArgumentException("Mismatched parentheses")
        }
        output.add(stack.pop())
    }

    return output
}

fun evaluatePostfix(postfix: List<String>): Double {
    val stack = Stack<Double>()

    for (token in postfix) {
        when {
            token.matches(Regex("\\d+(\\.\\d+)?")) -> stack.push(token.toDouble())
            token in setOf("+", "-", "*", "/") -> {
                val operand2 = stack.pop()
                val operand1 = stack.pop()
                when (token) {
                    "+" -> stack.push(operand1 + operand2)
                    "-" -> stack.push(operand1 - operand2)
                    "*" -> stack.push(operand1 * operand2)
                    "/" -> stack.push(operand1 / operand2)
                }
            }
            else -> throw IllegalArgumentException("Invalid token: $token")
        }
    }

    if (stack.size != 1) {
        throw IllegalArgumentException("Invalid expression")
    }

    return stack.pop()
}

fun precedence(operator: String): Int {
    return when (operator) {
        "+", "-" -> 1
        "*", "/" -> 2
        else -> 0
    }
}
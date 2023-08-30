import kotlin.system.measureTimeMillis

fun main() {
    val hiddenWord = "o*s03"
    val characterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+"

    var found = false
    var length = 2  // Start with length 1

    val elapsedTime = measureTimeMillis {
        while (!found) {
            val current = CharArray(length)
            found = generateCombinations(characterSet.toCharArray(), current, hiddenWord, 0)
            if (found) {
                println("Cracked: ${String(current)}")
            }
            length++
        }
    }

    println("Elapsed time: $elapsedTime ms")
}

fun generateCombinations(set: CharArray, current: CharArray, hiddenWord: String, position: Int): Boolean {
    if (position == current.size) {
        return current.contentEquals(hiddenWord.toCharArray())
    }

    for (char in set) {
        current[position] = char
        if (generateCombinations(set, current, hiddenWord, position + 1)) {
            return true
        }
    }
    return false
}
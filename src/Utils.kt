import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/inputs", "$name.txt")
    .readLines()

/**
 * Reads text from the given input txt file.
 */
fun readText(fileName: String) =
    File("src/inputs", "$fileName.txt").readText()

/**
 * Reads text from the given input txt file and splits it into a list.
 */
fun readSplitText(fileName: String, separator: String) =
    File("src/inputs", "$fileName.txt").readText().trim().split(separator)

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

@file:Suppress("NOTHING_TO_INLINE")

package com.example
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.apache.logging.log4j.util.Strings
import org.intellij.lang.annotations.RegExp
import java.net.URLEncoder
import java.text.DateFormat
import java.text.ParseException
import java.util.*

/**
 * https://github.com/Carleslc/kotlin-extensions/
 * @author : nguyentthai96 - nguyentthai96@gmail.com
 * @created : 11/08/2022, Thursday
 * @last_modified :  11/08/2022, Thursday
 **/


inline infix operator fun String.times(n: Int): String = Strings.repeat(this, n)
inline infix operator fun Int.times(s: String): String = Strings.repeat(s, this)

inline fun Double.toFixed(digits: Int): String = java.lang.String.format("%.${digits}f", this)
inline fun Float.toFixed(digits: Int): String = toDouble().toFixed(digits)

inline fun String?.isNotNullOrBlank() = !isNullOrBlank()

inline fun <T> T.toStringTransform(transform: (T) -> String) = let(transform)

inline fun String.splitLines() = split('\n')

inline fun <T> T?.wrap() = "$this"

inline fun <T> T?.wrapString() = if (this is CharSequence) wrap() else toString()

inline fun concat(vararg params: Any?) = params.joinToString("")

inline fun join(vararg params: Any?) = params.joinToString(" ")

inline fun joinWith(separator: String = " ", vararg params: Any?) = params.joinToString(separator)

inline fun String.replace(ignoreCase: Boolean = false, vararg vars: Pair<String, String>): String {
    var copy = this
    vars.forEach { copy = copy.replace(it.first, it.second, ignoreCase) }
    return copy
}

fun String.replace(vararg pairs: Pair<String, String>): String =
    pairs.fold(this) { acc, (old, new) -> acc.replace(old, new, ignoreCase = true) }

/**
 * val mapping = mapOf("his" to "here", "John" to "alles")
 * "his dad is John".replace(mapping)  // here dad is alles
 */
fun String.replace(mapping: Map<String, String>): String {
    var str = this
    mapping.forEach { str = str.replace(it.key, it.value) }
    return str
}

fun String.replaceIndexExt(vararg exts: String): String {
    var str = this
    exts.forEachIndexed { key, value -> str = str.replace("{$key}", value) }
    return str
}

// https://github.com/FunkyMuse/KAHelpers/blob/master/string/src/main/java/com/crazylegend/string/StringExtensions4.kt
fun stringConcatenateArrays(first: List<Char>, second: List<Char>): List<Char> {
    var result = ArrayList<Char>(first.size + second.size)
    var j = 0
    for (i in first.indices) i.run {
        result[this] = first[this]
        j = this
    }
    for (k in second.indices) result[k + j + 1] = second[k]
    return result
}

fun String.searchForPattern(pattern: String): Int {
    //if the searched text is longer than the original
    if (pattern.length > this.length) return -1
    //if the searched text is null
    if (pattern.isEmpty()) return 0

    for (i in 0 until this.length - pattern.length) {
        var j = i
        while (this[j] == pattern[j - i]) {
            if ((j - i) + 1 == pattern.length) return i
            if (j + 1 == this.length) break
            j++
        }
    }
    return -1
}

fun String.removeSymbols(replacement: String = "�"): String {
    return this.replace(Regex("[^\\p{ASCII}‘’]"), replacement)
}

fun String.containsAny(vararg strings: String): Boolean {
    return strings.any { this.contains(it) }
}

fun String.capitalizeWords(): String {
    return this.split(" ")
        .joinToString(" ") {
            it.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else it.toString()
            }
        }
}

fun String.camelCaseWords(): String {
    return this.lowercase().capitalizeWords()
}

fun String.trimTo(length: Int): String {
    return if (this.length < length) {
        this
    } else {
        this.substring(0, length - 1).plus("…")
    }
}

fun List<String>.containsCaseInsensitive(string: String): Boolean {
    forEach {
        if (it.equals(string, true)) {
            return true
        }
    }
    return false
}

fun List<String>.indexCaseInsensitive(string: String): Int {
    forEachIndexed { index, s ->
        if (s.equals(string, true)) {
            return index
        }
    }
    return -1
}

val arabicChars = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')


/**
 * Removes all occurrences of the [value] in the string
 * @param value A vlaue
 * @param ignoreCase Ignore case?
 * @return A new string with all occurrences of the [value] removed
 */
fun String.remove(value: String, ignoreCase: Boolean = false): String = replace(value, "", ignoreCase)

/**
 * Removes decimal number format symbol
 * @return A new string without `,`
 */
fun String.removeNumberFormat(): String = remove(",")


/**
 * Removes decimal number format symbol
 * @return A new string without `,`
 */
fun String.removeNumberFormatDot(): String = remove(".")

/** The Char array representing by this string */
inline val String.ch: Array<Char> get() = this.toCharArray().toTypedArray()


fun String.abbreviateMiddle(middle: String, length: Int): String {
    val str = this

    if (this.isEmpty()) {
        return this
    }

    if (length >= str.length || length < middle.length + 2) {
        return this
    }

    val targetSting = length - middle.length
    val startOffset = targetSting / 2 + targetSting % 2
    val endOffset = str.length - targetSting / 2

    val builder = StringBuilder(length)
    builder.append(str.substring(0, startOffset))
    builder.append(middle)
    builder.append(str.substring(endOffset))

    return builder.toString()
}


val NON_DIGIT_REGEX = Regex("[^A-Za-z0-9]")
val DIGIT_REGEX = Regex("[^0-9]")

fun String?.replaceNonDigit(replacement: String = "") = this?.replace(NON_DIGIT_REGEX, replacement)

fun String?.replaceDigit(replacement: String = "") = this?.replace(DIGIT_REGEX, replacement)

fun String?.isInt(): Boolean {
    if (isNullOrBlank()) return false
    return try {
        this.toIntOrNull() != null
    } catch (exc: ParseException) {
        false
    }
}

fun String.removeSpace(): String = replace(" ", "")

fun String.toDate(format: DateFormat): Date? {
    return try {
        format.parse(this)
    } catch (exc: ParseException) {
        exc.printStackTrace()
        null
    }
}

fun String.getRequestUrl(vararg fields: Pair<String, Any>): String {

    val param = StringBuffer()
    for (item in fields) {
        param.append(item.first + "=" + URLEncoder.encode(item.second.toString(), "UTF-8") + "&")
    }
    val paramStr = param.toString().let {
        it.substring(0, it.length - 1)
    }
    return if (indexOf("?") >= 0) {
        "$this&$paramStr"
    } else {
        "$this?$paramStr"
    }
}


fun String.remove(@RegExp pattern: String) = remove(Regex(pattern, RegexOption.IGNORE_CASE))

fun String.remove(regex: Regex) = replace(regex, "")

fun String.capitalizeFirstLetter(): String {
    return this.substring(0, 1).uppercase() + this.substring(1)
}

fun String.removeSpaces(): String {
    return this.replace(" ", "")
}

fun String.versionNumberToInt(): Int {
    return split(".").joinToString("").toInt()
}

fun String.capitalizeFirstLetterEachWord(): String {
    return this.lowercase()
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
}


private val mapper: ObjectMapper =
    jacksonObjectMapper().registerModule(ParameterNamesModule()).registerModule(Jdk8Module())
        .registerModule(JavaTimeModule()).registerModule(
            KotlinModule.Builder().withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false).configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false).configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )


fun Any.serializeToJsonString(): String {
    try {
        return mapper.writeValueAsString(this)
    } catch (ex: Exception) {
        throw java.lang.Exception("data: {}", ex)
    }
}

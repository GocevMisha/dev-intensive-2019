package ru.skillbranch.devintensive.utils


object Utils {
    fun parseFullName(fullName:String?) :Pair<String?, String?>{
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)?.ifBlank{null}
        val lastName = parts?.getOrNull(1)?.ifBlank{null}
        return firstName to lastName
    }
    fun transliteration(payload: String, divider: String = " "): String{
        var result = ""
        payload.replace(" ", divider)
            .toCharArray()
            .forEach {
                val symbol = transliterator.getOrElse(it.toLowerCase().toString()) {it.toString()}
                result += if(it.isUpperCase()) symbol.capitalize() else symbol
            }
        return result
    }

    fun toInitials(firstName: String?, lastName: String?) : String ? {
        val first = firstName?.trim()?.getOrNull(0)?.toUpperCase()
        val second = lastName?.trim()?.getOrNull(0)?.toUpperCase()
        var result: String? = null
        if(first != null)
            result = first.toString()
        if(second != null ) {
            if( result == null)
                result = second.toString()
            else result +=second
        }
        return result
    }

    val transliterator = mapOf(
        "а" to "a",

        "б" to "b",

        "в" to  "v",

        "г" to  "g",

        "д" to "d",

        "е" to "e",

        "ё" to "e",

        "ж" to "zh",

        "з" to "z",

        "и" to "i",

        "й" to "i",

        "к" to "k",

        "л" to "l",

        "м" to "m",

        "н" to "n",

        "о" to "o",

        "п" to "p",

        "р" to "r",

        "с" to "s",

        "т" to "t",

        "у" to "u",

        "ф" to "f",

        "х" to "h",

        "ц" to "c",

        "ч" to "ch",

        "ш" to "sh",

        "щ" to "sh'",

        "ъ" to "",

        "ы" to "i",

        "ь" to "",

        "э" to "e",

        "ю" to "yu",

        "я" to "ya"
    )
}
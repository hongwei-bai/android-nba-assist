package com.mikeapp.sportshub.data.github.util

import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import java.lang.reflect.Type
import java.util.Base64

class Base64ContentDeserializer : JsonDeserializer<String> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): String {
        // Remove line breaks and whitespace
        val encodedContent = json.asString.replace("\\s".toRegex(), "")
        return String(Base64.getDecoder().decode(encodedContent))
    }
}
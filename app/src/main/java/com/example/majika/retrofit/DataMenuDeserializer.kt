package com.example.majika.retrofit

import com.example.majika.retrofit.data.DataListMenu
import com.example.majika.retrofit.data.DataMenu
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DataMenuDeserializer : JsonDeserializer<DataListMenu> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DataListMenu {
        val jsonObject = json?.asJsonObject

        val size = jsonObject?.get("size")?.asInt
        val dataArray = jsonObject?.getAsJsonArray("data")
        var id = 0
        val data = arrayListOf<DataMenu>()
        dataArray?.forEach {
            val name = it.asJsonObject.get("name").asString
            val description = it.asJsonObject.get("description").asString
            val currency = it.asJsonObject.get("currency").asString
            val price = it.asJsonObject.get("price").asDouble
            val sold = it.asJsonObject.get("sold").asInt
            val type = it.asJsonObject.get("type").asString
            data.add(DataMenu(id, name,description,currency, price,sold, type, 0))
            id++
        }

        return DataListMenu(data, size)
    }
}
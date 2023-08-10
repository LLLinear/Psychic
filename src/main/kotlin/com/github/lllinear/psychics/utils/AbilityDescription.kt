package com.github.lllinear.psychics.utils

import org.bukkit.ChatColor

class AbilityDescription(val type: AbilityType) {
    var description: List<String> = ArrayList()

    var castTime: Float? = null
    var coolDown: Int? = null
    var mana: Int? = null
    var range: Float? = null
    var damage: Float? = null
    var healing: Float? = null

    val list = ArrayList<String>()

    private fun addDescriptionLine(description: String) {
        list.add(ChatColor.translateAlternateColorCodes('&', description))
    }

    fun toDescription(): List<String> {
        if (list.size > 0) {
            list.clear()
        }

        if (description.size > 0) {
            for (line in description) {
                addDescriptionLine("&f$line")
            }
        }

        list.add("")

        if (castTime != null) {
            addDescriptionLine("&9&l캐스팅 &f: ${castTime}초")
        }

        if (coolDown != null) {
            addDescriptionLine("&8&l쿨다운 &f: ${coolDown!!.toFloat() / 20}초")
        }

        if (mana != null) {
            addDescriptionLine("&b&l마나 &f: ${mana}")
        }

        if (range != null) {
            addDescriptionLine("&6&l사거리 &f: ${range}m")
        }

        if (damage != null) {
            addDescriptionLine("&c&l대미지 &f: ${damage}")
        }

        if (healing != null) {
            addDescriptionLine("&a&l회복량 &f: ${healing}")
        }

        list.add("")

        addDescriptionLine(type.coloredName)

        return list
    }


}
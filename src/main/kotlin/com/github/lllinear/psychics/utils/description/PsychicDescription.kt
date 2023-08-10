package com.github.lllinear.psychics.utils.description

import com.github.lllinear.psychics.psychics.Psychic
import org.bukkit.ChatColor

class PsychicDescription(var psychic: Psychic) {
    val list = ArrayList<String>()

    private fun addDescriptionLine(description: String) {
        list.add(ChatColor.translateAlternateColorCodes('&', description))
    }

    fun toDescription(): List<String> {
        if (list.size > 0) {
            list.clear()
        }

        if (psychic.description.size > 0) {
            for (line in psychic.description) {
                addDescriptionLine("&f$line")
            }
        }

        list.add("")

        addDescriptionLine("&c&l최대 체력 &f: ${psychic.health}")

        if (psychic.maxMana > 0) {
            addDescriptionLine("&b&l마나 &f: ${psychic.maxMana}")
        }

        if (psychic.maxMana > 0) {
            addDescriptionLine("&b&l마나 회복 &f: ${psychic.manaRegen}/s")
        }

        return list
    }


}
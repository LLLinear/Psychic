package com.github.lllinear.psychics.utils.description

import com.github.lllinear.psychics.psychics.Ability
import com.github.lllinear.psychics.utils.AbilityType
import org.bukkit.ChatColor

class AbilityDescription(val ability: Ability) {

    val list = ArrayList<String>()

    private fun addDescriptionLine(description: String) {
        list.add(ChatColor.translateAlternateColorCodes('&', description))
    }

    fun toDescription(): List<String> {
        if (list.size > 0) {
            list.clear()
        }

        if (ability.description.size > 0) {
            for (line in ability.description) {
                addDescriptionLine("&f$line")
            }
        }

        list.add("")

        if (ability.castingTime > 0) {
            addDescriptionLine("&9&l캐스팅 &f: ${ability.castingTime!!.toDouble() / 20}s")
        }

        if (ability.coolDown > 0) {
            addDescriptionLine("&8&l쿨다운 &f: ${ability.coolDown!!.toDouble() / 20}s")
        }

        if (ability.mana > 0) {
            addDescriptionLine("&b&l마나 &f: ${ability.mana}")
        }

        if (ability.range > 0) {
            addDescriptionLine("&6&l사거리 &f: ${ability.range}m")
        }

        if (ability.duration > 0) {
            addDescriptionLine("&d&l지속 시간 &f: ${ability.duration!!.toDouble() / 20}m")
        }

        if (ability.damage > 0) {
            addDescriptionLine("&c&l대미지 &f: ${ability.damage}")
        }

        if (ability.healing > 0) {
            addDescriptionLine("&a&l회복량 &f: ${ability.healing}")
        }

        list.add("")

        addDescriptionLine(ability.abilityType.coloredName)

        return list
    }


}
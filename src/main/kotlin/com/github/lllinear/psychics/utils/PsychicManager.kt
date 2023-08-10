package com.github.lllinear.psychics.utils

import com.github.lllinear.psychics.psychics.None
import com.github.lllinear.psychics.psychics.Psychic
import com.github.lllinear.psychics.utils.bar.ManaBar
import org.bukkit.entity.Player

class PsychicManager {
    companion object {
        private val psychicList = ArrayList<Psychic>()

        fun getPsychicList(): List<Psychic> {
            return psychicList
        }

        fun registerPsychic(psychic: Psychic): Boolean {
            for (psy in psychicList) {
                if (psy::class.simpleName!!.equals(psychic::class.simpleName!!, ignoreCase = true)) {
                    return false
                }
            }

            psychicList.add(psychic)
            return true
        }

        fun getPsychic(name: String): Psychic {
            for (psychic in psychicList) {
                if (psychic::class.simpleName!!.lowercase() == name) {
                    return psychic.clone()
                }
            }

            return None()
        }

        private val psychicMap = HashMap<String, Psychic>()

        fun getPsychic(player: Player): Psychic {
            val name = player.name
            if (!psychicMap.containsKey(name)) {
                return None()
            }

            return psychicMap[name]!!
        }

        fun setPsychic(player: Player, psychic: Psychic) {
            psychicMap[player.name] = psychic
        }

        private val manaMap = HashMap<String, Int>()

        fun getMana(player: Player): Int {
            val name = player.name
            if (!manaMap.containsKey(name)) {
                return 0
            }

            return manaMap[name]!!
        }

        fun setMana(player: Player, mana: Int) {
            manaMap[player.name] = mana

            ManaBar.update(player)
        }
    }
}
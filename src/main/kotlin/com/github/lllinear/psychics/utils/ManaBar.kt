package com.github.lllinear.psychics.utils

import com.github.lllinear.psychics.psychics.mana
import com.github.lllinear.psychics.psychics.maxMana
import com.github.lllinear.psychics.psychics.psychic
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

class ManaBar {
    companion object {
        private val manaBarMap = HashMap<String, BossBar>()

        fun create(player: Player) {
            val bar = Bukkit.createBossBar("${player.mana} / ${player.maxMana}", BarColor.BLUE, BarStyle.SEGMENTED_10)
            bar.addPlayer(player)
            manaBarMap[player.name] = bar

            update(player)
        }

        fun update(player: Player) {
            val name = player.name
            if (manaBarMap.containsKey(name)) {
                manaBarMap[name]!!.progress = player.mana.toDouble() / player.maxMana
            }
        }

        fun hide(player: Player) {
            val name = player.name
            if (manaBarMap.containsKey(name)) {
                manaBarMap[name]!!.removePlayer(player)
                manaBarMap.remove(name)
            }
        }
    }
}
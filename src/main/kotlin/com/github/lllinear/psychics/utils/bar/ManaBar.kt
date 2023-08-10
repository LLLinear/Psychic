package com.github.lllinear.psychics.utils.bar

import com.github.lllinear.psychics.psychics.None
import com.github.lllinear.psychics.psychics.mana
import com.github.lllinear.psychics.psychics.maxMana
import com.github.lllinear.psychics.utils.PsychicManager
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

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
                manaBarMap[name]!!.setTitle("${player.mana} / ${player.maxMana}")
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

        fun hideAll() {
            for (name in manaBarMap.keys) {
                if (Bukkit.getPlayer(name) == null) {
                    continue
                }

                hide(Bukkit.getPlayer(name)!!)
            }
        }
    }
}

class ManaRegenTask: BukkitRunnable() {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val psychic = PsychicManager.getPsychic(player)
            if (psychic.name == None().name || psychic.maxMana <= 0 || psychic.manaRegen <= 0 || player.mana >= player.maxMana) {
                continue
            }

            val afterMana = player.mana + psychic.manaRegen
            player.mana = if (afterMana > player.maxMana) player.maxMana else afterMana
        }
    }

}
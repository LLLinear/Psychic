package com.github.lllinear.psychics.utils.bar

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.Ability
import com.github.lllinear.psychics.psychics.mana
import com.github.lllinear.psychics.psychics.maxMana
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Boss
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.scheduler.BukkitRunnable

class CastingBar(private val name: String) {
    companion object {
        private val castingBarMap = HashMap<String, HashMap<String, BossBar>>()

        fun create(player: Player, ability: Ability, event: Event) {
            val abilityName = ability.name
            val bar = Bukkit.createBossBar(abilityName, BarColor.YELLOW, BarStyle.SOLID)
            bar.progress = 0.0
            bar.addPlayer(player)

            val name = player.name
            if (!castingBarMap.containsKey(name)) {
                castingBarMap[name] = HashMap()
            }
            castingBarMap[name]!![abilityName] = bar

            val castingTask = CastingTask(player, bar, ability, event)
            castingTask.runTaskTimer(Psychics.getPlugin(), 0L, 1L)
        }

        fun hide(player: Player, abilityName: String) {
            val name = player.name
            if (castingBarMap.containsKey(name) && castingBarMap[name]!!.containsKey(abilityName)) {
                castingBarMap[name]!![abilityName]!!.removePlayer(player)
                castingBarMap[name]!!.remove(abilityName)

                if (castingBarMap[name]!!.size == 0) {
                    castingBarMap.remove(name)
                }
            }
        }

        fun hide(player: Player, ability: Ability) {
            hide(player, ability.name)
        }

        fun hideAll() {
            for (name in castingBarMap.keys) {
                if (Bukkit.getPlayer(name) == null) {
                    continue
                }

                for (abilityName in castingBarMap[name]!!.keys) {
                    hide(Bukkit.getPlayer(name)!!, abilityName)
                }
            }
        }
    }
}

class CastingTask(private val player: Player, private val bar: BossBar, private val ability: Ability, private val event: Event): BukkitRunnable() {
    private var currentTime = 0
    override fun run() {
        currentTime++

        bar.progress = currentTime.toDouble() / ability.castingTime

        if (currentTime >= ability.castingTime) {
            ability.onActivate(event)
            CastingBar.hide(player, ability)
            cancel()
        }
    }
}
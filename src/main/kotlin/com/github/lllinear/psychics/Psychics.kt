package com.github.lllinear.psychics

import com.github.lllinear.psychics.events.EventListener
import com.github.lllinear.psychics.psychics.None
import com.github.lllinear.psychics.psychics.zombie.Zombie
import com.github.lllinear.psychics.utils.PsychicManager
import com.github.lllinear.psychics.utils.bar.CastingBar
import com.github.lllinear.psychics.utils.bar.ManaBar
import com.github.lllinear.psychics.utils.bar.ManaRegenTask
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Psychics: JavaPlugin() {
    companion object {
        private lateinit var plugin: Psychics

        fun getPlugin(): Psychics {
            return plugin
        }

        val prefix = "[ &dPsychics &f] "

        fun sendMessage(player: Player, message: String): Boolean {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message))
            return true
        }

        fun broadcastMessage(message: String) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + message))
        }
    }

    override fun onLoad() {
        PsychicManager.registerPsychic(None())
        PsychicManager.registerPsychic(Zombie())
    }

    override fun onEnable() {
        plugin = this

        logger.info("Plugin Enabled.")

        getCommand("psychics")!!.setExecutor(PsychicCommandExecutor())

        server.pluginManager.registerEvents(EventListener(), this)

        val manaRegenTask = ManaRegenTask()
        manaRegenTask.runTaskTimer(this, 0L, 20L)
    }

    override fun onDisable() {
        ManaBar.hideAll()
        CastingBar.hideAll()
    }
}
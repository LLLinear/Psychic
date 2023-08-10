package com.github.lllinear.psychics

import com.github.lllinear.psychics.events.EventListener
import com.github.lllinear.psychics.psychics.None
import com.github.lllinear.psychics.psychics.zombie.Zombie
import com.github.lllinear.psychics.utils.PsychicManager
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
            if (player == null) {
                return false
            }

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
    }
}
package com.github.lllinear.psychics

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.log

class Psychics: JavaPlugin() {
    companion object {
        val prefix = "&l[ %dPsychics &f] "

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

    override fun onEnable() {
        logger.info("Plugin Enabled.")
    }
}
package com.github.lllinear.psychics

import com.github.lllinear.psychics.psychics.Psychic
import com.github.lllinear.psychics.utils.AbilityDescription
import com.github.lllinear.psychics.utils.AbilityType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Psychics: JavaPlugin(), Listener {
    companion object {
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

    override fun onEnable() {
        logger.info("Plugin Enabled.")

        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onChat(event: PlayerChatEvent) {
        Psychics.sendMessage(event.player, "테스트 메시지")

        val item1 = ItemStack(Material.DIAMOND_PICKAXE)
        val description1 = AbilityDescription(AbilityType.ACTIVE)
        description1.description = listOf("첫 번쨰 줄", "두 번째 줄", "세 번째 줄")
        description1.castTime = 1.0f
        description1.coolDown = 2
        description1.mana = 3
        description1.range = 4.0f
        description1.damage = 5.0f
        description1.healing = 6.0f
        val itemMeta1 = item1.itemMeta
        itemMeta1!!.lore = description1.toDescription()
        item1.itemMeta = itemMeta1
        event.player.inventory.addItem(item1)

        val item2 = ItemStack(Material.DIAMOND_PICKAXE)
        val description2 = AbilityDescription(AbilityType.PASSIVE)
        description2.castTime = 1.0f
        description2.coolDown = 2
        description2.mana = 3
        description2.range = 4.0f
        description2.damage = 5.0f
        description2.healing = 6.0f
        val itemMeta2 = item2.itemMeta
        itemMeta2!!.lore = description2.toDescription()
        item2.itemMeta = itemMeta2
        event.player.inventory.addItem(item2)

        val item3 = ItemStack(Material.DIAMOND_PICKAXE)
        val description3 = AbilityDescription(AbilityType.TOGGLE)
        description3.castTime = 1.0f
        description3.coolDown = 2
        description3.mana = 3
        description3.range = 4.0f
        description3.damage = 5.0f
        description3.healing = 6.0f
        val itemMeta3 = item3.itemMeta
        itemMeta3!!.lore = description3.toDescription()
        item3.itemMeta = itemMeta3
        event.player.inventory.addItem(item3)
    }
}
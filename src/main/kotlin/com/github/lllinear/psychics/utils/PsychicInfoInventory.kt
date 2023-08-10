package com.github.lllinear.psychics.utils

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.Psychic
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.ItemStack

class PsychicInfoInventory(private val player: Player, private val psychic: Psychic): Listener {
    private val inv = Bukkit.createInventory(player, 9, psychic.name)

    private var page = 0

    init {
        val nextItem = ItemStack(Material.PAPER)
        val nextItemMeta = nextItem.itemMeta!!
        nextItemMeta.setDisplayName("${ChatColor.BOLD}▶")
        nextItem.itemMeta = nextItemMeta
        inv.setItem(8, nextItem)

        setPage()

        player.openInventory(inv)

        Bukkit.getPluginManager().registerEvents(this, Psychics.getPlugin())
    }

    private fun setPage() {
        if (page == 0) {
            val item = psychic.icon.clone()
            val itemMeta = item.itemMeta!!
            itemMeta.setDisplayName(psychic.name)
            val description = ArrayList<String>()
            for (line in psychic.description) {
                description.add(ChatColor.translateAlternateColorCodes('&', "&f$line"))
            }
            itemMeta.lore = description
            item.itemMeta = itemMeta
            inv.setItem(0, item)
        } else {
            val prevItem = ItemStack(Material.PAPER)
            val prevItemMeta = prevItem.itemMeta!!
            prevItemMeta.setDisplayName("${ChatColor.BOLD}◀")
            prevItem.itemMeta = prevItemMeta
            inv.setItem(0, prevItem)
        }

        for (i in 1..7) {
            inv.setItem(i, ItemStack(Material.AIR))
        }

        for (i in 0..6) {
            val abilityList = psychic.abilityList
            if (abilityList.size <= page * 7 + i) {
                break
            }
            val ability = abilityList[page * 7 + i]
            val item = ability.icon.clone()
            val itemMeta = item.itemMeta!!
            itemMeta.setDisplayName(ability.name)
            itemMeta.lore = ability.description
            item.itemMeta = itemMeta

            inv.setItem(i + 1, item)
        }
    }

    @EventHandler
    private fun onClick(event: InventoryClickEvent) {
        if (event.clickedInventory != inv) {
            return
        }

        event.isCancelled = true

        var maxPage = psychic.abilityList.size / 7
        maxPage = if (psychic.abilityList.size % 7 == 0) maxPage - 1 else maxPage

        if (event.slot == 0 && page > 0) {
            page--
            setPage()
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f)
        } else if (event.slot == 8 && page < maxPage) {
            page++
            setPage()
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f)
        }
    }

    @EventHandler
    private fun onDrag(event: InventoryDragEvent) {
        if (event.inventory == inv) {
            event.isCancelled = true
        }
    }

    @EventHandler
    private fun onClose(event: InventoryCloseEvent) {
        if (event.inventory == inv) {
            HandlerList.unregisterAll(this)
        }
    }
}
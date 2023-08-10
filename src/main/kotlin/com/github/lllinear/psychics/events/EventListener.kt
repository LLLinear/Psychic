package com.github.lllinear.psychics.events

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.None
import com.github.lllinear.psychics.psychics.mana
import com.github.lllinear.psychics.psychics.psychic
import com.github.lllinear.psychics.utils.bar.ManaBar
import com.github.lllinear.psychics.utils.PsychicManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EventListener: Listener {
    @EventHandler
    fun onPsychicSet(event: PsychicSetEvent) {
        val player = event.player
        val psychic = event.getPsychic()

        ManaBar.hide(player)

        player.maxHealth = psychic.health
        player.health = psychic.health

        player.mana = psychic.maxMana

        if (psychic.maxMana > 0) {
            ManaBar.create(player)
        }

        Psychics.sendMessage(player, "Your psychic is now ${psychic.name}.")
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (player.psychic.name == None().name) {
            return
        }

        if (event.item == null || event.item!!.type == Material.AIR) {
            return
        }

        for (ability in PsychicManager.getPsychic(player).abilityList) {
            ability.onTrigger(event)
        }
    }
}
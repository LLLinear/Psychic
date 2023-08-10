package com.github.lllinear.psychics.psychics

import com.github.lllinear.psychics.utils.AbilityType
import com.github.lllinear.psychics.utils.PsychicManager
import com.github.lllinear.psychics.utils.bar.CastingBar
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

abstract class Ability(var psychic: Psychic): Cloneable {
    abstract val name: String

    abstract val abilityType: AbilityType

    abstract val description: List<String>

    abstract val icon: ItemStack

    abstract var isActivating: Boolean

    abstract var leftDuration: Int

    open val castingTime: Int = 0
    open val coolDown: Int = 0
    open val mana: Int = 0
    open val range: Double = 0.0
    open val duration: Int = 0
    open val damage: Double = 0.0
    open val healing: Double = 0.0

    open fun onActivate(event: Event) {}

    private fun onCast(event: Event) {
        CastingBar.create(psychic.player, this, event)
    }

    open fun onTrigger(event: Event) {
        val player = psychic.player

        if (coolDown > 0 && player.getCooldown(icon.type) > 0) {
            //TODO: 쿨타임 남음 메시지
            return
        }

        if (mana > 0 && player.mana < mana) {
            //TODO: 마나 부족 메시지
            return
        }

        player.setCooldown(icon.type, coolDown)
        player.mana = player.mana - mana

        if (castingTime > 0) {
            onCast(event)
        } else {
            onActivate(event)
        }
    }

    public override fun clone(): Ability {
        return super.clone() as Ability
    }
}
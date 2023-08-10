package com.github.lllinear.psychics.psychics

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.events.PsychicSetEvent
import com.github.lllinear.psychics.utils.AbilityType
import com.github.lllinear.psychics.utils.PsychicManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Psychic: Cloneable {
    abstract val name: String

    abstract val description: List<String>

    abstract val icon: ItemStack

    abstract val health: Double

    abstract val maxMana: Int

    abstract val manaRegen: Int

    abstract var abilityList: List<Ability>

    lateinit var player: Player

    public override fun clone(): Psychic {
        val psychic = super.clone() as Psychic
        val newAbilityList = ArrayList<Ability>()
        for (ability in psychic.abilityList) {
            val newAbility = ability.clone()
            newAbility.psychic = psychic
            newAbilityList.add(newAbility)
        }
        psychic.abilityList = newAbilityList
        return psychic
    }
}

var Player.psychic: Psychic
    get() { return PsychicManager.getPsychic(this) }
    set(value) {
        value.player = this

        PsychicManager.setPsychic(this, value)

        val psychicSetEvent = PsychicSetEvent(this, value)
        Bukkit.getPluginManager().callEvent(psychicSetEvent)
    }

val Player.maxMana: Int
    get() { return PsychicManager.getPsychic(this).maxMana }

var Player.mana: Int
    get() { return PsychicManager.getMana(this) }
    set(value) { PsychicManager.setMana(this, value) }
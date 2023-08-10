package com.github.lllinear.psychics.psychics

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.utils.AbilityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Psychic: Cloneable {
    abstract val name: String

    abstract val description: List<String>

    abstract val icon: ItemStack

    abstract val abilityList: List<Ability>

    lateinit var player: Player

    public override fun clone(): Psychic {
        return super.clone() as Psychic
    }
}

var Player.psychic: Psychic
    get() { return psychic }
    set(value) {}
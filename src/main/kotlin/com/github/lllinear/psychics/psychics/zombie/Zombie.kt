package com.github.lllinear.psychics.psychics.zombie

import com.github.lllinear.psychics.psychics.Ability
import com.github.lllinear.psychics.psychics.Psychic
import com.github.lllinear.psychics.psychics.zombie.abilities.HungryGround
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Zombie: Psychic() {
    override val name = "좀비"
    override val description = listOf("저주받은 마법을 다루는 괴수입니다.")
    override val icon = ItemStack(Material.ZOMBIE_HEAD)
    override val health = 40.0
    override val maxMana = 100
    override val manaRegen = 5
    override val abilityList: List<Ability> = listOf(HungryGround(this))
}
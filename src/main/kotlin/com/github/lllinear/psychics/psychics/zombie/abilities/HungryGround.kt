package com.github.lllinear.psychics.psychics.zombie.abilities

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.Ability
import com.github.lllinear.psychics.psychics.Psychic
import com.github.lllinear.psychics.utils.AbilityType
import com.github.lllinear.psychics.utils.PsychicManager
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class HungryGround(psychic: Psychic): Ability(psychic) {
    override val name: String = "헝그리 그라운드"
    override val abilityType: AbilityType = AbilityType.ACTIVE
    override val description: List<String> = listOf("스킬 사용 시 현재 위치에 영역을 형성합니다.", "영역 안 적들은 독 피해를 받고 배고픔이 감소합니다.")
    override val icon: ItemStack = ItemStack(Material.ROTTEN_FLESH)
    override var isActivating: Boolean = false
    override var leftDuration: Int = 0

    override val coolDown: Int = (0.5 * 20).toInt()
    override val castingTime: Int = 30
    override val mana: Int = 8
    override val duration: Int = 5 * 20
    override val damage: Double = 1.0

    private val maxHunger: Int = 6

    override fun onTrigger(event: Event) {
        if (event !is PlayerInteractEvent) {
            return
        }

        if (event.item!!.type != icon.type) {
            return
        }

        super.onTrigger(event)
    }

    override fun onActivate(event: Event) {
        isActivating = true
        leftDuration = duration

        psychic.player.foodLevel = maxHunger

        Psychics.sendMessage(psychic.player, "스킬 사용")
    }
}
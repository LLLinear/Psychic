package com.github.lllinear.psychics.utils

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.None
import com.github.lllinear.psychics.psychics.Psychic

class PsychicManager {
    companion object {
        private val psychicList = ArrayList<Psychic>()

        fun getPsychicList(): List<Psychic> {
            return psychicList
        }

        fun registerPsychic(psychic: Psychic): Boolean {
            for (psy in psychicList) {
                if (psy::class.simpleName!!.equals(psychic::class.simpleName!!, ignoreCase = true)) {
                    return false
                }
            }

            psychicList.add(psychic)
            return true
        }

        fun getPsychic(name: String): Psychic {
            for (psychic in psychicList) {
                if (psychic::class.simpleName!!.lowercase() == name) {
                    return psychic.clone()
                }
            }

            return None()
        }
    }
}
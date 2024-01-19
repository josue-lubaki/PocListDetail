package ca.josue_lubaki.poclistdetail.model

import androidx.compose.runtime.saveable.Saver

/**
 * created by Josue Lubaki
 * date : 2024-01-19
 * version : 1.0.0
 */

class Car(val id : Int) {
    companion object {
        val Saver : Saver<Car?, Int> = Saver(
            save = { car -> car?.id},
            restore = ::Car
        )
    }
}
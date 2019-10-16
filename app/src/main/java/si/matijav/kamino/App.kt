package si.matijav.kamino

import android.app.Application
import android.content.Context
import si.matijav.kamino.data.repositories.PlanetRepository
import si.matijav.kamino.data.repositories.ResidentRepository
import si.matijav.kamino.ui.ServiceLocator

class App : Application() {

    val planetRepository: PlanetRepository
        get() = ServiceLocator.providePlanetRepository()

    val residentRepository: ResidentRepository
        get() = ServiceLocator.provideResidentRepository()

    companion object {
        fun getApp(context: Context): App {
            return context.applicationContext as App
        }
    }
}
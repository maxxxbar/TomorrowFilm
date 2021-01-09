package ws.worldshine.tomorrowfilm

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ws.worldshine.tomorrowfilm.di.component.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory()
                .create(applicationContext)
    }

}

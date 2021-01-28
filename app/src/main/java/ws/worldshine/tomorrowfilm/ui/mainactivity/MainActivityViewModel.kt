package ws.worldshine.tomorrowfilm.ui.mainactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import ws.worldshine.tomorrowfilm.R

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = javaClass.simpleName
    fun createFCM() {
        Firebase.messaging.subscribeToTopic(getApplication<Application>().getString(R.string.notification_channel_name))
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "FCM good: ")
                    } else if (!it.isSuccessful) {
                        Log.d(TAG, "FCM bad: ${it.exception}")
                    }
                }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.d(TAG, "Fetching FCM registration token failed: ${it.exception}")
                return@addOnCompleteListener
            }
            val token = it.result
            Log.d(TAG, "Current FCM token: $token")

        }
    }
}
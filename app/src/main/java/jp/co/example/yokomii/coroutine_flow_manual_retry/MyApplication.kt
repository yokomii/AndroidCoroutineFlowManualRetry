package jp.co.example.yokomii.coroutine_flow_manual_retry

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val emojiConfig = BundledEmojiCompatConfig(this)
        EmojiCompat.init(emojiConfig)
    }
}
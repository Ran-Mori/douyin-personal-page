package zy.douyinpersonalpage.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * 自定义一个Application用来初始化Fresco
 * 且不要忘记去AndroidManifest注册
 */
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * 初始化fresco
         */
        Fresco.initialize(this);
//        Fresco.initialize(this, ImagePipelineConfig.newBuilder(applicationContext)
//            .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
//            .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
//            .experiment().setNativeCodeDisabled(true)
//            .build());
    }
}
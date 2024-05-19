object NamceSpace {

    const val applicationId = "com.halan.twittertask"

    object Core {
        private const val core = "com.halan.core"
        const val network = "$core.network"
        const val utils = "$core.utils"
        const val ui = "$core.ui"
        const val uiTest = "$core.uiTest"
    }

    object Feature {
        private const val feature = "com.halan.feature"
        const val twitter = "$feature.twitter"
    }
}
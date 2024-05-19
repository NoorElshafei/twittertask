object Modules {

    const val app = ":app"

    object Core {
        private const val core = ":core"
        const val network = "$core:Network"
        const val utils = "$core:utils"
        const val ui = "$core:ui"
        const val uiTest = "$core:uiTest"
    }

    object Features {
        private const val feature = ":feature"
        const val twitter = "$feature:twitter"
    }

}
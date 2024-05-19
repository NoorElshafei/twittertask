interface BuildType {
    val name: String
    val isMinifyEnabled: Boolean
    val isShrinkResources: Boolean
    val isDebuggable: Boolean
}

object BuildTypeDebug : BuildType {
    override val name: String = "debug"
    override val isMinifyEnabled = false
    override val isShrinkResources = false
    override val isDebuggable = true
}

object BuildTypeRelease : BuildType {
    override val name: String = "release"
    override val isMinifyEnabled = true
    override val isShrinkResources = true
    override val isDebuggable = false
}
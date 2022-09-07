/**
 * Precompiled [checkers.kotlin-library-conventions.gradle.kts][Checkers_kotlin_library_conventions_gradle] script plugin.
 *
 * @see Checkers_kotlin_library_conventions_gradle
 */
class Checkers_kotlinLibraryConventionsPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Checkers_kotlin_library_conventions_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}

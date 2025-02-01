package nz.co.example.app.features.navigation.models

import androidx.navigation.NavBackStackEntry

internal sealed class AppNavigationRoute(open val route: String) {

    data object NBAPlayers : AppNavigationRoute("nbaPlayers")

    class NBAPlayerDetail : AppNavigationRoute(ROUTE) {
        companion object {
            private const val BASE_URL = "nbaPlayer"
            private const val ROUTE = "${BASE_URL}/{id}"

            fun createRoute(id: String): String = "$BASE_URL/$id"

            fun getArg(navBackStackEntry: NavBackStackEntry): String? = navBackStackEntry.arguments?.getString("id")
        }
    }

    class NBATeamDetail : AppNavigationRoute(ROUTE) {
        companion object {
            private const val BASE_URL = "nbaTeam"
            private const val ROUTE = "${BASE_URL}/{id}"

            fun createRoute(id: String): String = "$BASE_URL/$id"

            fun getArg(navBackStackEntry: NavBackStackEntry): String? = navBackStackEntry.arguments?.getString("id")
        }
    }

}
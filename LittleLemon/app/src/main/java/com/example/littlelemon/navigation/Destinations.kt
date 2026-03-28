package com.example.littlelemon.navigation

// Step 3: Create the destinations

interface Destinations {
    val route: String
}

object Home: Destinations {
    override val route: String = "Home"
}

object Profile: Destinations {
    override val route: String = "Profile"
}

object Onboard: Destinations {
    override val route: String = "Onboarding"
}
# Final project for the Meta Android developer course

## Application screens
Before implementing navigation, let’s explore the various destinations required for the Little Lemon food ordering app. There should be three navigation destinations in your app, each represented by a Composable. These are the:

- Onboarding screen

- Home screen

- Profile screen

Users should be able to navigate between these destinations in your app. In more detail, the requirements for the navigation flow are as follows:
On app launch, the app should navigate to the **Onboarding** destination if a user is logged out.
On app launch, the app should navigate to the **Home** destination if a user is logged in.
Successful registration on the Onboarding destination lets the user navigate to the **Home** destination.
From the **Home** screen, a user must be able to navigate to the **Profile** destination.
When a user presses the Back button on the **Profile** destination the **Home** destination should open.
When a user presses the Logout button on the **Profile** destination the app should navigate from the **Profile** destination to the **Onboarding** destination. 

## Onboarding screen

Before new users start placing orders, they’ll need to be registered into the system. In this exercise, you’ll make this possible by creating an onboarding flow to collect a user’s first name, last name and email address.
The most suitable option for simple data independent of other values is to use the SharedPreferences.

### Profile screen  

Your app should allow users to view their personal information such as their first name, last name and email address on the **Profile** screen.
Note that you are not required to allow users to update their information for this project, but they should be able to log out via the **Profile** screen. 
When a user clicks on the Log out button, your app should clear all the data from shared preferences and navigate to the **Onboarding** screen.

### Fetching and storing the food menu.

To create the food menu for your Little Lemon ordering app you need to fetch data from a remote server formatted in JSON, store and display it. 
As soon as you get the JSON data from the server, you must convert it to a suitable Kotlin format.
After retrieving menu items from the network, store the menu in the local room database.

### Home screen
When users access the **Home** screen of the Little Lemon food ordering app, they’ll find a full list of all the items offered by the restaurant. But what if a user only wants to view a specific item, rather than navigating through everything or finding a particular dish by name? Fortunately, you can display only the results that match a search phrase.
There are multiple ways to filter the menu items. In fact, to develop the menu breakdown section you also use filtering. By using the category attribute present in the JSON you can break down the menu into categories such as Starters, Mains, Desserts and Drinks. When a user taps on a category button in the Menu breakdown section only menu items from the given category should display.

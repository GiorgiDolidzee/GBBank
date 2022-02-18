# GB Bank
GB Bank is a digital bank to manage your balance, track exchange rates,
and ease your life. Our mobile bank project is developed for the TBC Bank
technical assignment. GB Bank is a unique bank as it provides exchange rates,
has a built-in currency converter, and provides real-time cryptocurrency
information, such as maximum and minimum value in 24 hours, rank, and so on.
  #


## 🚀 Developers

- [Bachana Mosulishvili](https://www.github.com/bchmsl)

- [Giorgi Dolidze](https://www.github.com/GiorgiDolidzee)

We are beginner Android developers,
who study at [Business and Technology University](https://btu.edu.ge/). We studied
Android development at first at University and then the
[USAID](https://www.usaid.gov/) course came up and changed our lives. 
 ## 
## 📝 Technical documentation

Upon logging in for the first time, a user sees a login screen,
where they can choose whether they want to log in or sign up.
After user logs into their account, they are on their home screen.
The application has **Bottom Navigation Menu** so that users
can easily navigate between fragments, such as the *Home screen*, *exchangeTechnical documentation

Upon logging in for the first time, a user sees a login screen,
where they can choose whether they want to log in or sign up.
After user logs into their account, they are on their home screen.
The application has **Bottom Navigation Menu** so that users
can easily navigate between fragments, such as the *Home screen*, *exchange rates
screen*, *cryptocurrency screen* and *user profile screen*.

  
### Home
On the home screen, users can clearly see their card with one's name and balance on it.
Below the card, there is a deposit button, where they can **simulate depositing money**. They
can enter any amount and it will be added to their balance.
  

### Exchange Rates
On currency screen user can convert any currency to another and can track exchange rates.
  

### Crypto
On the Crypto Screen user can see the **TOP 50 Cryptocurrencies** with their symbols and real-time prices.
after clicking on any of the cryptocurrencies they can see more details about this particular
crypto.

  
### Profile
On the profile screen user sees information about themselves. They can change profile picture
using URL, they can change password, see information about the application and sign out.
  
# 
## 🤓 Documentation for geeks :)
GB Bank application uses **Kotlin** as its base language and standard Android
SDK with **XML layouts** and **MVVM** with **clean architecture**. The application follows all **SOLID
Principles** and uses **single activity architecture**. GB Bank application uses [Dagger Hilt](https://github.com/google/dagger/tree/master/java/dagger/hilt)
for **dependency injection** and [Retrofit2](https://github.com/square/retrofit) for **API requests**.
  

GB Bank application uses [Google's Firebase](https://firebase.google.com/) for most of its parts.
It uses **Firebase Authentication** and **Realtime Database** features from **Google**.
App also uses **TBC Bank's API** for [exchange rates](https://developers.tbcbank.ge/reference/exchangerates-get-commercial-rates-api) and [currency converting](https://developers.tbcbank.ge/reference/exchangerates-convert-with-commercial-rates-api) and
**CoinGecko's API** for [cryptocurrency information](https://www.coingecko.com/en/api/documentation).

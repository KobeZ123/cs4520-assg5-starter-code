GitHub Repository Link: https://github.com/KobeZ123/cs4520-assg5-starter-code

Application Structure
The application is similar to Assignment 4, except all the XML fragments have been converted into 
Jetpack Compose components. I am using the compose NavHost rather than the XML NavHost to 
navigate between two screens: the login screen and the product list screen. Both screens use
MVVM architecture handle and manage the UI state. 
<br/>
The user will be navigated to the product list fragment if they input "admin" as the username and password. 
Otherwise, they will be met with an appropriate toast message (and their input will be cleared if they input the wrong credentials). 
The product list fragment loads a list of products from an API endpoint and handles issues accordingly. 
If an error occurs, the app attempts to load the list of products from the Room database. 
If there are no products, a message is displayed in the fragment.
<br/>
Pagination is also implemented to query the API by page number. 
Since the API still behaves returning error messages and empty lists at times, 
the pagination implementation will display "No products available" o
n API responses when an empty list is returned. 
It will also load from the database when the API response is an error message. 
Therefore, sometimes it looks like the pagination shows the same page twice. 
That is because it attempts to load the new page and an error message occurs. 
Therefore, it will load the data from the Room database, which is the most recently successful API response.
<br/>
A WorkManager has been implemented to fetch the data every hour starting one hour after the user navigates to the 
product list page. The Worker will fetch data from the API and repopulate the Room database if the result is successful. 

How to Run Application
There is no special setup required to run the application. Simply connect your device or start the emulator and run 'app'.
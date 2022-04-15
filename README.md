# JobScraper
JobScraper is marketplace mobile application that scraps job offers from diffrent other platforms and put them in one place.

![plot](https://othmane-hanyf.me/images/project5.png)

## Prerequisites
You will need the following prerequisites:
1. [OpenJDK](https://openjdk.java.net/) 11.0.14.1 or later
2. [Android Studio](https://developer.android.com/studio?hl=fr&gclid=Cj0KCQjwr-SSBhC9ARIsANhzu16Gr0Kb59WyO4nG5Bfl1lL26IRokDPhO7nRrmjunqeThNFmpta3YIkaAiscEALw_wcB&gclsrc=aw.ds) 2020.3.1 or later
3. [MySQL](https://www.mysql.com/) 8.0.27 or later

## Installation
### 1. Clone the repository
```bash
git clone https://github.com/OthmaneHanyf/JobScraper.git
```
### 2. Setup the database
Log into mysql as root to create a new database and user
```bash
CREATE DATABASE android;
CREATE USER 'android'@'localhost' IDENTIFIED BY 'android';
GRANT all on android.* to 'android'@'localhost';
```
After that open a new terminal window and go the the root of the project and run the following command :
```bash
mysql -u root -p android < db.sql
```
### 3. Allow Remote Connections to MySQL
See [this](https://stackoverflow.com/questions/14779104/mysql-how-to-allow-remote-connection-to-mysql) question on stackoverfloow
### 3. Open the project with **Android Studion**
### 4. Change the database connection information
Open the file */app/src/main/java/com/job/scrape/Constants.java* and change the database connection details to the ones we specified in step **2**.
```bash
public class Constants {
    public static String dbUrl = "jdbc:mysql://<IP>/android";
    public static String dbUser = "android";
    public static String dbPassword = "android";
}
```
For the **IP** enter the IP address of the machine that MySQL server is running on.

And now, with android studio properly configured, you can run the application.
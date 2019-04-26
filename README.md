<h2 style="text-align: center;">CS 495 Capstone Project at The University of Alabama</h2>

### App Overview

[![App Overview](https://i9.ytimg.com/vi/CJr7WGcF044/mq1.jpg?sqp=COyGiuYF&rs=AOn4CLAf-SilOrx3zuj70n_7mnb7YNVuCg)](https://www.youtube.com/watch?v=CJr7WGcF044&t=1s "Alabama Redesign App")

### About

The goal of this project is to correct several design flaws found in the current implementation of the current Alabama app. This includes, but is not limited to:

* Home page navigation
* Multiple student/staff logins
* Linking to and relying on third party apps

These issues greatly impact usability, affecting the speed of performance, accuracy, and satisfaction of users. There are several places in the existing app where you must sign in to access a feature, but this sign in is not saved across the different features. So, you will be prompted to sign in again when accessing another feature, even if you already just signed in. With UA’s sign in now using 2FA, this can take a large amount of time. The existing app also uses a lot of webviews and linking to other apps for it’s functionality. The webviews make for a very inconsistent interface. For example, in some webviews the page shown also has a hamburger menu button in addition to the app’s, leading to two being stacked on top of each other. Also, the colors used in the pages shown in the webviews clash with the app’s colors and theme. For some features, the app simply links to other apps rather than having that functionality itself. When accessing a feature that is in another app, it gives an error if the app isn’t installed instead of providing a name or link to the app store. Of the 20 features/tasks listed in the app, 8 of them use a webview and another 4 link to a seperate app. Ultimately, our goal is to remove most of these webviews with the exception of the campus map and possibly laundry (further research needs to be done in the laundry implementation). Ridding the app of most of these or reimplementing them natively will provide the user with a significantly smoother experience and in turn help clean up the menu navigation.

A general design of our app can be seen in the video above. This new implementation will feature a refined design as well as new features.


## Team Members

### Michael Ahern

* Focus: News, Emergency, Directory, Course Catalog
* Past experience: Data modeling intern at the MITRE Corporation in Montgomery, AL
* Contact: mjahern@crimson.ua.edu

### Nicholas Brunelle

* Focus: Mock Database, Action Card, Schedule, MyTickets
* Past experience: Plant-wide SAP implementation at Mercedes-Benz U. S. I. in Vance, AL
* Contact: nabrunelle@crimson.ua.edu

### Tyler Gilam

* Focus: Navigation Menu, Performance, Events, Links, Transportation, Laundry
* Past experience: User interface development on similar redesign of Alabama app
* Contact: tdgillam@crimson.ua.edu

### Ethan Johnson

* Focus: Login, Home pages, Campus Map, Grades
* Past experience: Front-end development experience at Command Alkon in Birmingham, AL
* Contact: ekjohnson4@crimson.ua.edu

## Website
https://tgillam.github.io/BamaAppSite/

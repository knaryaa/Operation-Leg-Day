# Operation-Leg-Day

Membership Database

# Contributors:

Miriam Ekiye, Cody Noack, Yalcin Taf, Zachary Nabors, Johnathan Molina, David Holder, Ethan Bowles, Ederick Mendoza, Jesse Schultz

# Purpose

The purpose of this software is provide a membership tracking platform for use by a company and their members. We created it to be used as a gym membership database, but the application can be applied elsewhere. It is simple in design and easy to apply to any computer, giving it great flexibility.

# Features & Benefits

-Adding/Removing accounts

-Changing membership options

-Creating new utilities/activities

-Convenient member access for viewing & limited account changes

-Changes are immediate

# Front end

In use with eclipse and additional Jar plugins like WindowBuilder /
Swing Design, Java has been an efficient language to use. A few other
external Java JARs were imported for use of making a database
connection and developing a method to hash passwords created in the
Front-end of the application.

-The PostgreSQL JDBC Driver 42.2.8 had been used to be imported in
the GUI application for setting up a database connection, creating
SQL syntax to be passed as a query though these recorded statements
into a database and record results from the database.

-Mindrot’s, jBCrypt-0.4, a Java implementation of the hashing
algorithm was compiled and imported for part of the project library to
ensure every password created will be hashed and saved in the
database.

-For development tools, WindowBuilder was utilized in the Eclipse
IDE for a powerful, easy to use GUI designer to create buttons,
panels, labels, as well as fields onto the application frame for login
use once compiled.

# Hardware

-Minimum 32-bit processor

-1 GB storage minimum

-256 MB of free disk space for job result directory and log directory
(total 508 MB)

# Software

-Windows 7 or Newer

-Java JDK 11.0.5

-Eclipse IDE 2019-09

  Plug-ins / Packages Used: 
  
    PostgreSQL JDBC Driver
    
    Bcrypt Java Package
    
    Eclipse Swing add-on
    

-PostgreSQL Version 11.6

-AIX, FreeBSD, Linux, Solaris, Windows compatible

# Conceptual Design

List of Entities

  -Client
  
  -Login
  
  -Tier Options
  
  -Relationship
  

List of attributes

  Client:
  
    -E-mail
    
    -Phone number
    
    -Employee
    
      o (1 for yes 0 for no)
      
    -First Name
    
    -Last Name
    
    -Tier
    
      o Three levels of membership
      

Login

  -Username
  
  -Password

Tier Options

  -Phone number
  
  -Tier Level
  
  -Tennis Court
  
  -Football Field
  
  -Yoga Class
  
  -Zoomba Class
  
  -Pool

Relationship

  -E-mail
  
  -Username

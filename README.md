# time_scheduler
This is an application to create a computer application for scheduling events and setting up a timetable for daily tasks. 

The backend of the application is written in Java, whereas the front-end implements JavaFX and CSS. The application uses Oracle Database to store all the information of users as well as events, which can be accessed via the Internet. Simple Mail Transfer Protocol (SMTP) server is applied for sending emails and reminders due to its simple implementation. 

Our application includes essential operations of a scheduling application covering account login, account registration, creating new events, modifying old events, adding participants, sending reminders, and exporting a calendar as a pdf or text file.

<a href="https://ibb.co/b7HK6yT"><img src="https://i.ibb.co/rvbwQLY/z4011104655462-0667cdbf78379f40659bef0595552d35.jpg" alt="z4011104655462-0667cdbf78379f40659bef0595552d35" border="0" ></a>

## Requirements
To run the code with completed UI, you need to launch the app with the following VM Parameter:
      
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED,

--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED,

--add-exports=javafx.base/com.sun.javafx.binding=ALL-UNNAMED,

--add-exports=javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED,

--add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED

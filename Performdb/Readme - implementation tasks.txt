== Installation ==

	1. Unzip performTask.zip
	2. Run perform.bat to launch the application
	
	
== Command line usage ==

>report
Generates a raport with films statistics in the library (using JDBC connection)
>1
 Number of videos created on a day
>2
 Number of videos by Type
>3
 Number of videos by selected type

>list 
Displays all videos in the system (using Hibernate connection)
>1
 List videos in the system with tags
>2
 List videos in the system grouping by tags
>3
 View a list of tags in the system

>add
>1
>2
>3
>...
>Maximum number of files into classes
Ingests a video which are into videofiles folder in /classes/ (The current version should be four files that can be added to the library)


== Concept of categories ==

I used the keywords that can be assigned to video, in any quantity, as categories.
I expanded the Video entity on the list of objects VideoTag.
Each VideoTag object represent keywords assigned to video.

== List of improvement suggestions that have been implemented ==

- added support upload xml files with videos into classes (use JAXB library)
- expanded Video entity of keywords used as categories or labels
- added a validation of input data
- added raporting options (using jdbc connection - filled VideoReportingJDBCDAO.class)
- improved presentation of the results
- added a ResultSetMapper class to avoid code duplication
- added a new option to the presentation of the video library

I tried to hold forth the concept of the project
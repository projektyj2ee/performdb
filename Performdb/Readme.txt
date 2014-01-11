
This application is a mini video ingest. It offers capability to consult the video library, and to ingest additional videos using an XML file containing the metadata. It comes prepopulated with a couple of videos.


== Installation ==

	1. Unzip performTask.zip
	2. Run perform.bat to launch the application
	
	
== Command line usage ==

>list
Displays all videos in the system

>add
>video_1.xml
Ingests a video, where video_1.xml needs to be in the videofiles folder (currently under /classes/)


== Deliverables ==

=== Mandatory ===

	1. The ingest of a video is not currently functional. You are expected to implement XML parsing and save the video into the library. Running the "list" command after your ingest should display the existing two videos already present in the system, along with the one you've just added.
	
	2. Introduce the concept of categories or labels to tag a video with. Should be reflected in the displayed list. A video can have several categories attached to it.
	
	3. Write up a list of improvement suggestions to the existing code.

	4. Use the VideoReportingJDBCDAO to report on videos present in the system.

== Optional ==
	
	5. Implement some of the improvements suggested in point 3.
	
	
== Constraints and guidelines ==

	1. We are not expecting a complete rewrite of the application, ideas will be covered during the face to face interview when discussing the improvement areas you have listed.
	2. Keep in mind code quality and stability, and provide proof that all required features are working as expected.
	3. Your code should be easy for another developer to read and understand.
	
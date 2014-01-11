package com.performgroup.interview.cmd;

import java.io.Console;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * The main entry point for the video ingest task
 */
public class VideoTaskRunner {

	private static transient final Logger LOGGER = Logger.getLogger(VideoTaskRunner.class);

	private static transient final String USAGE = "\n====== Usage =================================================== \n"
			+ "report = generates a report with films statistics in the library \n"
			+ "  list = list videos in the system \n"
			+ "   add = add a video after prompting for file path \n"
			+ "  exit = exits the program \n" + " usage = prints this help";

	public static final void usage() {
		LOGGER.info(USAGE);
	}

	public static final void main(final String[] args) {

		Console c = System.console();

		if (c == null) {
			System.err.println("No console.");
			System.exit(1);
		}

		final GenericApplicationContext ctx = new GenericApplicationContext();
		final XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		String appContext = System.getProperty("appContext");
		xmlReader.loadBeanDefinitions(new ClassPathResource(appContext));
		ctx.refresh();

		VideoProcessor videoProcessor = (VideoProcessor) ctx.getBean("videoProcessor");

		usage(); // show usage always on start:D

		while (true) {

			System.out.print(">");

			String input = c.readLine();
			if (input.equalsIgnoreCase("usage")) {
				usage();
			
			} else if (input.equalsIgnoreCase("list")) {
				System.out.println(videoProcessor.getListingOption());
				System.out.print(">");
				videoProcessor.listVideos(LOGGER, c.readLine());
			
			} else if (input.equalsIgnoreCase("report")) {
				System.out.println(videoProcessor.getReportingOption());
				System.out.print(">");
				videoProcessor.reportsVideos(LOGGER, c.readLine());
				
			} else if (input.equalsIgnoreCase("add")) {
				System.out.println("Video file list in system: ");
				// get file name list
				videoProcessor.listFilesForFolder(LOGGER, null);

				System.out.println("Choose video file? " + videoProcessor.getVideoFileIndexesInfo());
				System.out.print(">");

				String videoDataFileIndex = c.readLine();
				videoProcessor.ingestVideo(LOGGER, videoDataFileIndex);
			
			} else if (input.equalsIgnoreCase("exit")) {
				System.exit(0);
			
			} else {
				System.out.println("Unrecognised command.  Running the \"usage\" command. ");
			}

		}
	}
}

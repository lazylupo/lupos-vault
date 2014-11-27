package de.inmotion.findme.servlet.utils;

import de.inmotion.findme.servlet.resources.AdventureRetrieval;

public class FindMatchRunner implements Runnable {
	
	long[] advList;

	public FindMatchRunner(long[] advList) {
		super();
		this.advList = advList;
	}

	@Override
	public void run() {

		AdventureRetrieval.checkForMatchingAdventure(advList);
		
	}

}

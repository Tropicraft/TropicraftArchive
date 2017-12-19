package net.tropicraft.entities;

import java.util.LinkedList;
import java.util.List;

public class TropicraftSentence {
	
	public int phraseCount;
	public int phraseIndex;
	
	public List phraseStrings;
	public List phraseTimings;
	//looks sexy - Cojo
	public TropicraftSentence() {
		phraseCount = phraseIndex = 0;
		phraseStrings = new LinkedList();
		phraseTimings = new LinkedList();
		
	}
	
	public void add(String phrase) {
		phraseStrings.add(phrase);
		phraseTimings.add(getTiming(phrase));
		phraseCount++;
	}
	
	public void add(String phrase, int timing) {
		phraseStrings.add(phrase);
		phraseTimings.add(timing);
		phraseCount++;
	}
	
	public void clear()
	{
		phraseStrings.clear();
		phraseTimings.clear();
		phraseCount = 0;
	}
	
	public int getTimeout() {
		if (phraseStrings.size() > 0) {
			return ((Integer)phraseTimings.get(phraseIndex)).intValue();
		}
		return -1;
	}
	
	public static int getTiming(String phrase) {
		return phrase.length() * 50;
	}
}

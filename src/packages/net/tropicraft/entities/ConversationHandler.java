package net.tropicraft.entities;

import net.minecraft.entity.EntityLiving;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ConversationHandler 
{
	protected TropicraftSentence speak;
	protected EntityKoaMemberNew entity;
	public Random rand;
	public boolean hasGreeted;
	public boolean hasSaidFirstSentence; //<_>
	public boolean playerInRange;
	/*<Cojo> What if the tribe was like
<Cojo> "If you have one of these items, you can have whatever you like out of my chest"*/
	public ConversationHandler(EntityLiving entity) 
	{
		speak = new TropicraftSentence();
		rand = entity.worldObj.rand;
		if (entity instanceof EntityKoaMemberNew) {
			this.entity = (EntityKoaMemberNew)entity;
		}
		
		hasGreeted = false;
		tribeQuestions = new LinkedList();
	//	speak.add("test");
	//	speak.add("doowwp");
	//	tribeQuestions.add(speak);
		addConversations();
	}

	protected void clearSpoken()
	{
		speak.clear();
	}
	
	protected void onUpdate()
	{
		
		/*EntityPlayer entityplayer = ModLoader.getMinecraftInstance().thePlayer;
		
		if (entity == null) return;
		
		if (entity.currentAction == EnumActState.IDLE) 
		//if (entity.currentAction == EnumKoaActivity.IDLE) 
		{
			//When player approaches
			if(entityplayer != null)
	        {
	            float f = entityplayer.getDistanceToEntity(entity);
	            if(f < entity.tradeDistTrigger)
	            {
	            	if(!hasGreeted)
	            		greetPlayer(f);
	            }
	        
	        }
		}
		
		if (entityplayer != null && entityplayer.getDistanceToEntity(entity) < entity.tradeDistTrigger) {
			playerInRange = true;
		} else {
			playerInRange = false;
		}*/
		
		//tribeQuestions.add(speak);
		
		int qIndex = 0;		
		if(tribeQuestions != null)// && tribeQuestions.get(0) != null)
		{		
			TropicraftSentence tSent = (TropicraftSentence)tribeQuestions.get(qIndex);
			
			//	mod_tropicraft.sysout(tSent.phraseIndex + "    " + (tSent.phraseCount - 1));
			
			if (!hasSaidFirstSentence) {
				if (tSent.phraseIndex < tSent.phraseCount) {	
					if (sentStart < System.currentTimeMillis()) {
						sentStart = System.currentTimeMillis() + tSent.getTimeout();
		//				out(tSent.phraseStrings.get(tSent.phraseIndex));
						say(" <" + '\247' + "a" + entity.name + '\247' + "f" + "> " + tSent.phraseStrings.get(tSent.phraseIndex) + "");
						tSent.phraseIndex++;
					}
				} else {
					hasSaidFirstSentence = true;
					tSent.phraseIndex = 0;
					//mod_tropicraft.setEngaged(false, -1);
		//			System.out.println("Engaged: " + mod_tropicraft.getIsEngaged(entity.entityId));
				}
			}
		}		
	}
	
	public void say(String s)
	{
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(s);
	}
	
	public void out(Object s)
	{
		System.out.println(s.toString());
	}
	
	public void addConversations() {
		TropicraftSentence question = new TropicraftSentence();
		question.add("Aloha!");
		/*question.add("balhalblahlablablahlalh");
		question.add("Hi, my name is " + entity.name + " which means " + entity.getNameMeaning()); // :D		
		question.add("this is a real phrase to test if you can read before it moves to the next one, DID YOU?!");
		question.add("if you didnt, you are a piece of shit and should die, or just fix the multiplier");
		question.add("here, have a tastey fish");
		question.add("Hello, friend. Here, have an ARROW IN THE KNEE. BWAHAHAHA");*/
		tribeQuestions.add(question);
		
		question = new TropicraftSentence();
		question.add("Oaloha!");
		//question.add("q22");
		tribeQuestions.add(question);
	}
	
	public static long sentStart = 0;
	public List tribeQuestions;
	
	public void onTradeInitiated(boolean b)
	{
		if(b)	//if trade offer is liked
		{
			speak.clear();
			speak.add(getContentPhrase());
			tribeQuestions.add(speak);
		}
		else
		{
			speak.clear();
			speak.add(getUpsetPhrase());
			tribeQuestions.add(speak);
		}
	}
	
	public String getUpsetPhrase()
	{
		return upsetlines[rand.nextInt(upsetlines.length)];
	}
	
	public String getContentPhrase()
	{
		return contentlines[(rand.nextInt(contentlines.length))];
	}
	
	public String getGreetingPhrase()
	{
		return greetinglines[rand.nextInt(greetinglines.length)];
	}
	
	public void greetPlayer(float f)
	{
		speak.clear();
		speak.add(getGreetingPhrase());
		tribeQuestions.add(speak);
		hasGreeted = true;
	}
	
	public final String[] greetinglines = new String[]{"Aloha!","Oaloha!"};
	public final String[] contentlines = new String[]{"Maika'i", "Hau'oli", "Hoaloha", "Laule'a"};
	public final String[] upsetlines = new String[]{'\247' + "4a'ole", '\247' + "4hu hu"};
	
	public final String[] tribalMaleNames = new String[]{"Akamu", "Ekewaka", "Ikaika", "Iukini", "Kai", "Kaimana", "Kaimi", "Kanoa", "Kapena", "Keahi", "Keaweaheulu","Kekipi",
    		"Kekoa", "Konani", "Makani", "Mano", "Nahele"};
    
	public final String[] nameMaleMeanings = new String[]{"Earth, or Red", "Guardian of Prosperity", "Well Born", "Strong", "Sea", "Diamond", "The Seeker",
    		"The Free One", "The Captain", "Flames", "Cool Breeze Over the Mountains","Rebel", "The Brave One", "Bright", "Wind", "Shark", "Forest"};
    
	public final String[] tribalFemaleNames = new String[]{"Alamea", "Alani", "Alaula", "Ewa", "Kai", "Kaiolohia", "Kalama", "Keala", "Kiele", "Mahina",
    		"Makala", "Maylea", "Noe"};
    
    public final String[] nameFemaleMeanings = new String[]{"Precious", "Orange Tree", "Dawn", "Life", "Sea", "Calm Sea", "Flaming Torch", "The Pathway", "Fragant Blossom",
    		"Moon, or Moonlight", "Myrtle", "Wildflower", "Mist or Misty Rain"};	
}













/*
[01:21] <newthead> Why don't you have the whole conversation system encapsulated in its own class?
[01:21] <newthead> And each entity can have an instance of it
[01:21] <Corosus> it is for the sentance data, i contemplated another class for management of the said data, but ill leave that to cojo to dowhatever he wants
[01:22] <Corosus> do that cojo
[01:22] <newthead> The constructor should take the EntityLiving as a parameter
[01:22] <Corosus> yeh in a perfect world, entity simply has an id it uses to fetch the convo data from the convo class in mod_blah
[01:22] <newthead> Then it can query the Entity to determine an appropriate message
[01:23] <newthead> Or the Entity can force the Conversation class to go to a specific sentence
[01:23] <Corosus> hmm that could work, if you have the convo class handle the gui logic of it all too
[01:23] <Corosus> should work nicely
[01:23] <Corosus> or that yeah
[01:24] <newthead> That would take care of random sentences as well as player-initiated sentences
[01:24] * Corosus throws on final destination 5 for the lulz while coding
[01:24] * Corosus starts chanting 'everybody dies!'
[01:24] * Cojo is so tired that he is having to read what newthead said over and over so it will sink in
[01:25] <Corosus> hehe
[01:25] <Corosus> i had to read your commented idea 3 times over for it to sink in
[01:25] <newthead> I'm thinking of something like a ConversationHandler as a field inside each NPC
[01:25] <Corosus> ya!
[01:25] <Corosus> definately
[01:26] <Cojo> also, lololol http://www.youtube.com/watch?v=SfmCKBtKDt4&feature=related
[01:26] <crow3> Cojo: Double D's epic Deadmau5 doorbell - length 20s - rated 3.00/5.0 (38) - 65234 views - AxcelKaru on 2010.09.23
[01:26] <newthead> And you would have "myConversationHandler = new ConversationHandler(this);"
[01:26] <newthead> So the handler gets a reference to that specific entity
[01:26] <Cojo> My brain is too tired to be able to do this right this moment. I will add the functionality of names to the tribes then go shower.
[01:27] <Cojo> Should the names still output above the tribe still?
[01:27] <newthead> Then when the entity updates, it also updates the ConversationHandler
*/
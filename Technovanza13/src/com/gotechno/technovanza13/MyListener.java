package com.gotechno.technovanza13;

import org.schwering.irc.lib.*;

import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;

public class MyListener extends IRCEventAdapter 
                        implements IRCEventListener {
	public String text,prevText;
	
	public MyListener (String et) {
		text=et;
		prevText="";
	}

  public void onConnect() {
	  Log.i("MYLOG", "Connected");
	  Update update=new Update();
	  update.text="\n"+"Connected to #technoapp";
	  update.execute();
  }

  public void onDisconnected() {
	  Log.i("MYLOG", "Disconnected");
	  Update update=new Update();
	  update.text="\n"+"Disconnected from #technoapp";
	  update.execute();
  }

  public void onError(String msg) {
	  Log.i("MYLOG", "Error");
    
  }

  public void onError(int num, String msg) {
	  Log.i("MYLOG", "Error no : " + num + " Msg : " + msg);
  }

  public void onInvite(String chan, IRCUser user, String nickPass) {
    Log.i("MYLOG", "INVITE: "+ user.getNick() 
            +" invites "+ nickPass +" to "+ chan);
  }

  public void onJoin(String chan, IRCUser user) {
	  Log.i("MYLOG", "JOIN: "+ user.getNick() 
		        +" joins "+ chan);
	  Update update=new Update();
	  update.text="\n"+user.getNick() +" joins "+ chan;
	  update.execute();
    // add the nickname to the nickname-table
  }

  public void onKick(String chan, IRCUser user, String nickPass, String msg) {
	  Log.i("MYLOG","KICK: "+ user.getNick() 
		        +" kicks "+ nickPass +"("+ msg +")");
    // remove the nickname from the nickname-table
  }

  public void onMode(String chan, IRCUser user, IRCModeParser modeParser) {
	  Log.i("MYLOG", "MODE: "+ user.getNick() +" changes modes in "+ chan +": "+ modeParser.getLine());
    // some operations with the modes
  }

  public void onNick(IRCUser user, String nickNew) {
	  Log.i("MYLOG", "NICK: "+ user.getNick() +" is now known as "+ nickNew);
	  Update update=new Update();
	  update.text="\n"+user.getNick() +" is now known as "+ nickNew;
	  update.execute();
    // update the nickname in the nickname-table
  }

  public void onPart(String chan, IRCUser user, String msg) {
	  Log.i("MYLOG", "PART: "+ user.getNick() 
		        +" parts from "+ chan +"("+ msg +")");
	  Update update=new Update();
	  if(!msg.equals("")) {
		  update.text="\n"+user.getNick() +" parts from "+ chan +"("+ msg +")";
	  }else {
		  update.text="\n"+user.getNick() +" parts from "+ chan;
	  }
	  update.execute();
    // remove the nickname from the nickname-table
  }

  public void onPrivmsg(String target, IRCUser user, String msg) {
	  Log.i("MYLOG", "PRIVMSG: "+ user.getNick() 
		        +" to "+ target +": "+ msg);
	  text="\n" + user.getNick() +" : "+ msg;
	  Update update=new Update();
	  update.text=text;
	  update.execute();
	  
  }

  public void onQuit(IRCUser user, String msg) {
	  Log.i("MYLOG", "QUIT: "+ user.getNick() +" ("+ 
		        user.getUsername() +"@"+ user.getHost() +") ("+ msg +")");
    // remove the nickname from the nickname-table
  }

  public void onReply(int num, String value, String msg) {
	  //Log.i("MYLOG", "Reply #"+ num +": Message: "+msg +" | Value: "+ value);
	  Log.i("MYLOG",msg);
	  text="\n" + msg;
	  if(!text.equals(prevText)) {
		  prevText=text;
		  //Update update=new Update();
		  //update.text=text;
		  //update.execute();
	  }
  }
  
  
  
  
  public void onTopic(String chan, IRCUser user, String topic) {
	  Log.i("MYLOG", "TOPIC: "+ user.getNick()+" changes topic of "+ chan +" into: "+ topic);   
  }
  
  private class Update extends AsyncTask<String, Void, String> {

	  public String text;
	  public void setText(String s) {
		  text=s;
	  }
      @Override
      protected String doInBackground(String... params) {
  		return "executed";
      }      

      @Override
      protected void onPostExecute(String result) {
            //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
      	//new Operation().execute();
    	//IrcActivity.text.setText(text);
    	BubblesActivity.addBubble(text);
      	//Log.i("MYLOG", "length : " + text.length());
      }

      @Override
      protected void onPreExecute() {
      }

      @Override
      protected void onProgressUpdate(Void... values) {
      	//text.setText(st);
      }
	}
	
  
  
}

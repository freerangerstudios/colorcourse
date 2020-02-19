package com.freeranger.colorcourse.handlers;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class LanguageManager {
	
	private int language;
	private Hashtable<String, String> text;
	private Preferences prefs2;
	
	public LanguageManager(){
		prefs2 = Gdx.app.getPreferences("com.freerangerstudios.colorcourse.options");
		
		language = prefs2.getInteger("com.freerangerstudios.colorcourse.options.language", 0); // Will be equal to preference: gamedata
		text = new Hashtable<String, String>();
		init();
	}
	
	public void init(){
		if(text.size() == 0) text.clear();
		
		String[] text = new String[19];
		
		if(language == 0){
			text[0] = "Play";
			text[1] = "Options";
			text[2] = "Credits";
			text[3] = "Remove Data";
			text[4] = "Additional SFX from https://www.zapsplat.com";
			text[5] = "Additional SFX from https://www.freesfx.co.uk";
			text[6] = "Additional assets from https://www.kenney.nl";
			text[7] = "Walking SFX from http://www.orangefreesounds.com (modified)";
			text[8] = "World";
			text[9] = "Coins";
			text[10] = "Time";
			text[11] = "Next Level";
			text[12] = "Play Again";
			text[13] = "Main Menu";
			text[14] = "Are you sure you want to remove all data?";
			text[15] = "Yes";
			text[16] = "No";
			text[17] = "Music by Eric Matyas. (www.soundimage.org)";
			text[18] = "Thank you for playing this game!";
		}else if(language == 1){
			// German translation
			text[0] = "Spielen";
			text[1] = "Optionen";
			text[2] = "Kredite";
			text[3] = "Löschen Data";
			text[4] = "Soundeffekte aus https://zapsplat.com";
			text[5] = "Soundeffekte aus https://freesfx.co.uk";
			text[6] = "Soundeffekte aus https://kenney.nl";
			text[7] = "Gehen Soundeffekte aus http://orangefreesounds.com (modifizierte)";
			text[8] = "Welt";
			text[9] = "Geld";
			text[10] = "Zeit";
			text[11] = "Nächste Level";
			text[12] = "Wieder Spielen";
			text[13] = "Hauptmenü";
			text[14] = "Machten Sie wirklich alle Daten entfernen?";
			text[15] = "Ja";
			text[16] = "Nein";
			text[17] = "Musik von Eric Matyas. (www.soundimage.org)";
			text[18] = "Danke für spielen dieses Spiel!";
		}else if(language == 2){
			// Spanish translation
			text[0] = "Jugar";
			text[1] = "Opciones";
			text[2] = "Créditos";
			text[3] = "Borrar Datos";
			text[4] = "Más efectos de sonido de https://zapsplat.com";
			text[5] = "Más efectos de sonido de https://freesfx.co.uk";
			text[6] = "Más efectos de sonido de https://kenney.nl";
			text[7] = "Ir efecto de sonido de http://orangefreesounds.com (modifizierte)";
			text[8] = "Mundo";
			text[9] = "Dinero";
			text[10] = "Tiempo";
			text[11] = "Siguiente Nivel";
			text[12] = "Jugar de nuevo";
			text[13] = "Menú principal";
			text[14] = "¿Estás seguro de que quieres eliminar todos los datos?";
			text[15] = "Sí";
			text[16] = "No";
			text[17] = "Músika de Eric Matyas. (www.soundimage.org)";
			text[18] = "Gracias por juagar este juego!";
		}else if(language == 3){
			// Swedish translation
			text[0] = "Spela";
			text[1] = "Alternativ";
			text[2] = "Eftertexter";
			text[3] = "Radera Data";
			text[4] = "Ytterligare ljudeffekter: https://zapsplat.com";
			text[5] = "Ytterligare ljudeffekter: https://freesfx.co.uk";
			text[6] = "Ytterligare resurser: https://kenney.nl";
			text[7] = "Walk-ljudeffekt: http://orangefreesounds.com (modifierad)";
			text[8] = "Del";
			text[9] = "Pengar";
			text[10] = "Tid";
			text[11] = "Nästa nivå";
			text[12] = "Spela igen";
			text[13] = "Huvudmeny";
			text[14] = "Är du säker på att du vill ta bort all data?";
			text[15] = "Ja";
			text[16] = "Nej";
			text[17] = "Musik av Eric Matyas. (www.soundimage.org)";
			text[18] = "Tack för att du har spelat det här spelet!";
		}else {
			System.out.println(language + " is not a known language.");
			Gdx.app.exit();
		}
	
		
		/////// Add language translations to hashtable ///////
		
		this.text.put("Play", text[0]);
		this.text.put("Options", text[1]);
		this.text.put("Credits", text[2]);
		this.text.put("Remove Data", text[3]);
		this.text.put("Credits 1", text[4]);
		this.text.put("Credits 2", text[5]);
		this.text.put("Credits 3", text[6]);
		this.text.put("Credits 4", text[7]);
		this.text.put("World", text[8]);
		this.text.put("Coins", text[9]);
		this.text.put("Time", text[10]);
		this.text.put("Next Level", text[11]);
		this.text.put("Play Again", text[12]);
		this.text.put("Main Menu", text[13]);
		this.text.put("Remove Data Question", text[14]);
		this.text.put("Yes", text[15]);
		this.text.put("No", text[16]);
		this.text.put("Credits 5", text[17]);
		this.text.put("FinalLabel", text[18]);

		/////////////////////////////////////////////////////
	}
	
	public void setLanguage(int language){
		this.language = language;
		prefs2.putInteger("com.freerangerstudios.colorcourse.options.language", language);
		prefs2.flush();
		init();
	}
	public int getLanguage(){
		return language;
	}
	public Hashtable<String, String> getText(){
		return text;
	}
}
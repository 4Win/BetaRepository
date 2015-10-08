package algorithmus; 
import database.Database;

public class KI {
	
	int hoehe = 6;
	int breite = 7;
	
	// Feldbezeichnung
	static int leer = 0;
	int spieler = 1;
	int gegner = -1;
	// Feldbewertungen max 1000 pkt = Sieg oder min -1000 pkt verloren 
	int muster4Spieler = 1000;
	int muster4Gegner = -1000;
	
	int muster3spieler = 100;
	int muster3gegner = 500;	
	
	int muster2spieler = 10;
	int muster2gegner = 10;
			
	
	
	int[][] feld = new int[hoehe][breite];


	
	//KI Objekt kann einlesen aufrufen 
	//bekommt als Eingabe Spalte und Name des Spielers aus der Serverdatei 
	public static void main(String[] args) throws Exception {

		KI KI = new KI();
//		
		KI.zugeinlesen(0, 2);
		KI.zugeinlesen(1, 2);
		
//
//		KI.berechnen();
		
//		
//		
//		//KI.zugeinlesen(spalte, player); // Übergabe fehlt noch
//		
//		KI.bewerten(KI);
//		
//		KI.zugausführen();

		
	}
	
	//Getter / Setter  Methoden
	public int[][] getFeld() {
		return this.feld; }
	public void setFeld(int x, int y, int wert) {
		this.feld[x][y] = wert; }
	
	
	// Feld aufbauen und befüllen - Start	
	public void befüllen (){
		for (int y=0; y<hoehe; y++){
			for (int x=0; x<breite; x++){
				feld[y][x] = leer;
				System.out.println(feld[y][x]);
			}	
		}			
	}
	
	// Methode die Zeile aus Spalte errechnet 
	// geht Array durch und findet erste Zeile  die in Spalte noch nicht befüllt ist 
	//schreibt die Spielernummer an der Stelle in den Array 
	public int zugeinlesen(int spalte, int spieler) throws Exception {
		Database db = new Database(); 
		int zeile = 0;
		for (int x = 5; x >= 0; x--) {
			if (feld[x][spalte] == 0) {
				zeile = x;
				feld[zeile][spalte] = spieler; // 2 ist Gegener 1 sind wir
				
				db.insertZuege(1, zeile, spalte, spieler); 
				db.selectZuege();
				int berechnung = berechnen();
				db.insertZuege(1, zeile, berechnung, 1); // Berechnung der Zeile für meinen Einwurf noch machen 
				return berechnung; 
			}
		}
		return zeile;
	}
	

	// Diese Methoden findet die Koordinaten für die nnächstmöglichen Einwürfe der KI heraus 
	// und schreibt die x und y Koordinaten an die Arrays; 
	public void bespielbareFelder(KI KI)
	{
		//kp warum das nicht funktioniert... 
		int [] xkoordinaten = new int[7]; 
		int [] ykoordinaten = new int[7]; 
		
		int[][]feld = KI.getFeld();
		
		for(int y=0; y<=6; y++) 
		{
			for (int x = 5; x >= 0; x--) 
			{
				if(feld[x][y] == 0)
				{	
					xkoordinaten[y] = x;
					ykoordinaten[y] = y; 
					
					System.out.println(xkoordinaten[x] +""+ ykoordinaten[x]);
					break;
				}
				
			}
			
		
		}
	}

	
	public int bewerten(KI KI){ // bewertet übergebenes feld
// 
		 int spieler2er = 0; int gegner2er = 0;
		 int spieler3er = 0; int gegner3er = 0;
		 
		 int[][]feld = KI.getFeld(); // holt sich feld von Objekt
		  
	// zuerst nur höhe überprüfen
		 for (int x=0; x<breite; x++) {
			    for (int y=0; y<hoehe; y++) {
			        // Ist noch Platz um 4 Chips nach oben zu setzen?
			        if (hoehe-y>=4) { // Ist platz für 4 nach oben ?
			        	if (mustercheck(feld,spieler,x,y,0,-1)==4){
			        		return muster4Spieler;
			        		}
			        		else if (mustercheck(feld,gegner,x,y,0,-1)==4){
				        		return muster4Gegner;
			        		}
			        		else if (mustercheck(feld,spieler,x,y,0,-1)==3){
			        			spieler3er ++;
			        		}
			        		else if (mustercheck(feld,gegner,x,y,0,-1)==3){
			        			gegner3er ++;
			        		}
			        		else if (mustercheck(feld,spieler,x,y,0,-1)==2){
			        			spieler2er ++;
			        		}
			        		else if (mustercheck(feld,gegner,x,y,0,-1)==2){
			        			gegner2er ++;
			        	      }
			            // Noch 4 Chips nach rechts oben moeglich?
			            if ((hoehe-y>=4) && (breite-x>=4)) {
			              // 4 gleiche Chips nach rechts oben?
			              if (mustercheck(feld,spieler,x,y,1,1)==4) 
			                return muster4Spieler;  // gewonnen
			              
			              else if (mustercheck(feld,gegner,x,y,1,1)==4)
			                return muster4Gegner;  // verloren
			              // 3 gleiche Chips uebereinander?
			              else if (mustercheck(feld,spieler,x,y,1,1)==3) 
			                spieler3er++; 
			              else if (mustercheck(feld,gegner,x,y,1,1)==3)
			                gegner3er++;
			              // 2 gleiche Chips uebereinander?
			              else if (mustercheck(feld,spieler,x,y,1,1)==2) 
			                spieler2er++; 
			              else if (mustercheck(feld,gegner,x,y,1,1)==2)
			                gegner2er++;
			            } 
			            // Noch 4 Chips nach rechts moeglich?
			            if (breite-x>=4) {
			              if (mustercheck(feld,spieler,x,y,1,0)==4) 
			                return muster4Spieler;  // gewonnen
			              else if (mustercheck(feld,gegner,x,y,1,0)==4)
			                return muster4Gegner;  // verloren
			              // 3 gleiche Chips uebereinander?
			              else if (mustercheck(feld,spieler,x,y,1,0)==3) 
			                spieler3er++; 
			              else if (mustercheck(feld,gegner,x,y,1,0)==3)
			                gegner3er++;
			              // 2 gleiche Chips uebereinander?
			              else if (mustercheck(feld,spieler,x,y,1,0)==2) 
			                spieler2er++; 
			              else if (mustercheck(feld,gegner,x,y,1,0)==2)
			                gegner2er++;
			            }
			            // Noch 4 Chips nach rechts unten moeglich?
			            if ((breite-x>=4) && (y>=3)) {
			               if (mustercheck(feld,spieler,x,y,1,-1)==4) 
			                return muster4Spieler;  // gewonnen
			              else if (mustercheck(feld,gegner,x,y,1,-1)==4)
			                return muster4Gegner;  // verloren
			              // 3 gleiche Chips uebereinander?
			              else if (mustercheck(feld,spieler,x,y,1,-1)==3) 
			                spieler3er++; 
			              else if (mustercheck(feld,gegner,x,y,1,-1)==3)
			                gegner3er++;
			              // 2 gleiche Chips uebereinander?
			              else if (mustercheck(feld,spieler,x,y,1,-1)==2) 
			                spieler2er++; 
			              else if (mustercheck(feld,gegner,x,y,1,-1)==2)
			                gegner2er++;
			            } 
			        		
			        	}
			        		
			        }
			     }
		 return muster3spieler*spieler3er + muster2spieler*spieler2er - muster3gegner*gegner3er + muster2gegner*gegner2er;
	}
	
	// Hilfsfunktion zum Erkennen eines 2er-, 3er- oder 4er-Musters
	// dx und dy geben die Richtung an, in die gesucht wird
	// (ab der Position (x,y))
	private static int mustercheck(int [][] feld, int spieler, int x, int y, int dx, int dy) {
	  int num = 0;
	  // dx und dy, je nach richtung 0 oder 1 oder -1 
	  if (  ((feld[x][y]==spieler) || (feld[x][y]==leer)) // Überprüfung auf Koordinate
	     && ((feld[x+1*dx][y+1*dy]== spieler) || (feld[x+1*dx][y+1*dy]==leer)) // Überprüfung auf nächster Koordinate = 2-Muster
	     && ((feld[x+2*dx][y+2*dy]== spieler) || (feld[x+2*dx][y+2*dy]==leer)) // Überprüfung auf nächster Koordinate = 3 Muster
	     && ((feld[x+3*dx][y+3*dy]== spieler) || (feld[x+3*dx][y+3*dy]==leer)))
	  { // überprüfung auf nächster Koordinate = 4 Gewinnt

	    // zaehle Anzahl von spieler belegter Felder
		  for (int i=0; i<4; i++){ // geht 4 felder durch 
	    	if (feld[x+i*dx][y+i*dy]==spieler) num++;// wenn feld belegt ist +1 bis max 4
		  }
	  }
	return num;// wenn max 4 automatisch 4 gewinnt
	} // Ende Hilfsfunktion
	
	public void zugausführen() {
		// Zug ausführen
		
	}
	
	// Mitch - Nimmt erste Spalte- Ziel keine fehler werfen

	
	// Diese Methode berechnet die Entscheidung der KI 
	// geht Spalten und Zeilen durch und sucht dich erstes freies Feld 
	// schreibt die SpaltenNummer in Spalte und wirft diese zurück
	public int berechnen() {
		
		int spalte = 1; 
		 
		for(int y=0; y<=6; y++)
		{
			for (int x = 5; x >= 0; x--) {
				
				if(feld[x][y] == 0)
				{
					 spalte = y; 
					 System.out.println(spalte);
					
					 return spalte;  
					  
				}
			 ;}
			}
		 
		// Dann neue Zufallszahl 
		return 1;
		}
	
		
	}


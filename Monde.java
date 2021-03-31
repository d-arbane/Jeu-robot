import java.util.Vector;

public class Monde {
	private int nbL,nbC;
	private int xDecharge,yDecharge, papiers;
	private Vector<Vector<Boolean>> Mat;
	private GestionnaireRobots Gest;
	
	public int getNbL() {return nbL;}
	public int getNbC() {return nbC;}
	public int getxDecharge() {return xDecharge;}
	public int getyDecharge() {return yDecharge;}
	public int getPapiersDecharges() {return papiers;}
	public Vector<RobotPollueur> PollueursA(int x,int y){return Gest.PollueursA(x, y);}
	public GestionnaireRobots getGestionnaire() {return Gest;}
	
	public Monde(int L,int C) {
		nbL=L;
		nbC=C;
		xDecharge=0;
		yDecharge=0;
		papiers=0;
		Mat= new Vector<Vector<Boolean>>();
		Gest = new GestionnaireRobots(this, new Vector<RobotNettoyeur>(), new Vector<RobotPollueur>(), new Vector<RobotNettoyeur>(), new Vector<RobotPollueur>());
		for(int i=0;i<L;i++)
			Mat.add(new Vector<Boolean>());
		for(Vector<Boolean> lig:Mat)
			for(int i=0;i<C;i++)
				lig.add(false); //false => propre
	}
	public Monde(int L, int C, int x,int y) {
		this(L,C);
		xDecharge=x;
		yDecharge=y;
	}
	public void balayer(boolean polluer) {
		for(Vector<Boolean> lig:Mat)
			for(int i=0;i<nbC;i++)
				lig.set(i,polluer);
	}
	public void MettrePapierGras(int i,int j) throws ExceptionCaseExistePas {
		if((i>nbL)||(j>nbC)||(i<0)||(j<0))
			throw new ExceptionCaseExistePas();
		Mat.get(i).set(j, true);
	}
	public void PrendsPapierGras(int i,int j) throws ExceptionCaseExistePas {
		if((i>nbL)||(j>nbC)||(i<0)||(j<0))
			throw new ExceptionCaseExistePas();
		Mat.get(i).set(j, false);
	}
	public boolean TestPapierGras(int i,int j) throws ExceptionCaseExistePas {
		if((i>=nbL)||(j>=nbC)||(i<0)||(j<0))
			throw new ExceptionCaseExistePas();
		return Mat.get(i).get(j);
	}
	public int NombrePapierGras() {
		int resultat=0;
		for(Vector<Boolean> lig:Mat)
			for(Boolean b:lig)
				if(b)
					resultat++;
		return resultat;
	}

	public void decharger(int c) {
		papiers+=c;
	}
}

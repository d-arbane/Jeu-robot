
public abstract class Robot {
	public int posx,posy;
	public Monde m;
	private int vitesse;
	private String ref;
	
	
	public int getVitesse() {return vitesse;}
	public String getref() {return ref;}
	public void nommer(String suffixe) {
		ref+=suffixe;
	}
	public Robot(int posx, int posy,int v, Monde m) {
		this.posx = posx;
		this.posy = posy;
		this.m = m;
		vitesse = v;
		ref="";
	}
	public Robot(int v,Monde m) {
		this.m=m;
		posx=(int)(Math.random()*m.getNbL());
		posy=(int)(Math.random()*m.getNbC());
		vitesse = v;
		ref="";
	}
	public void SeDeplacer(int i,int j) throws ExceptionCaseExistePas {
		m.TestPapierGras(i, j);
		if(this instanceof RobotPollueur)
			m.getGestionnaire().DeplacerRobotPollueur(i, j, (RobotPollueur)this);
		posx=i;
		posy=j;
	}
	public abstract int getNbRob();
	public abstract void Parcourir() throws ExceptionCaseExistePas;
}

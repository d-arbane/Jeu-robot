public abstract class RobotPollueur extends Robot{
	private boolean vivant;
	private static Integer nbRob=0;
	public RobotPollueur(int posx, int posy, int v, Monde m) {
		super(posx, posy, v, m);
		vivant=true;
		nommer("P-"+nbRob);
		nbRob++;
	}
	public RobotPollueur(int v,Monde m) {
		super(v,m);
		vivant=true;
		nommer("P-"+nbRob);
		nbRob++;
	}
	public boolean estEnVie() {
		return vivant;
	}
	public void tuer() {
		vivant = false;
		m.getGestionnaire().DetruireRobot(this);
	}
	public int getNbRob() {
		return nbRob;
	}
	public abstract void Detruire() throws ExceptionCaseExistePas;
}

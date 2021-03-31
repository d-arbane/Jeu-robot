public class RobotNettoyeurToutDroit extends RobotNettoyeur{
	private boolean dir;
	private int lig;
	public RobotNettoyeurToutDroit(int posx, int posy, int v, Monde m, int Cap) {
		super(posx, posy, v, m, Cap);
		dir = true;
		nommer("TD");
		lig=posx;
	}

	public int getLig() {
		return lig;
	}
	@Override
	public void Parcourir() throws ExceptionCaseExistePas {
		int y;
		if(dir) {
			if(posy+1<m.getNbC())
				y=posy+1; 
			else {
				y = posy-1;
				dir = !dir;
			}
		}else {
			if(posy-1>=0)
				y=posy-1;
			else {
				y = posy+1;
				dir=!dir;
			}
		}
		SeDeplacer(posx, y);
		if(m.TestPapierGras(posx, y)) {
			m.PrendsPapierGras(posx, y);
			incCharge();
		}
	}
	
}

public class RobotPollueurToutDroit extends RobotPollueur{
	private boolean dir;
	
	public RobotPollueurToutDroit(int posx, int posy, Monde m, int v) {
		super(posx, posy,v, m);
		try {
			m.MettrePapierGras(posx, posy);
		} catch (ExceptionCaseExistePas e) {
			e.printStackTrace();
		}
		dir=true;
		nommer("TD");
	}

	@Override
	public void Parcourir() throws ExceptionCaseExistePas {
		if(posx==m.getNbL()-1)
			dir=false;
		else if(posx==0)
			dir=true;
		if(dir) {
			m.MettrePapierGras(posx+1, posy);
			SeDeplacer(posx+1, posy);
			}
			else {
				m.MettrePapierGras(posx-1, posy);
				SeDeplacer(posx-1, posy);
			}
	}

	@Override
	public void Detruire() throws ExceptionCaseExistePas {
		tuer();
		
	}
}

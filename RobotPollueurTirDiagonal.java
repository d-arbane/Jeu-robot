public class RobotPollueurTirDiagonal extends RobotPollueur{
	/*
	 * Robot qui lance du papier gras sur les quatres diagonales simultanément, pose plus de papier gras et est "détruit" a la fin de la course
	 * */
	private boolean diag1,diag2,diag3,diag4;
	private int pas;
	public RobotPollueurTirDiagonal(int posx, int posy, int v, Monde m) {
		super(posx, posy, v, m);
		m.getGestionnaire().DeplacerRobotPollueur(posx, posy, this);
		diag1=true;
		diag2=true;
		diag3=true;
		diag4=true;
		pas=0;
		nommer("DG");
	}
	@Override
	public void Parcourir() throws ExceptionCaseExistePas {
		if(estEnVie()) {
			pas++;
			if(diag1&&((posx-pas<0)||(posy-pas<0)))
				diag1=false;
			if(diag2&&((posx-pas<0)||(posy+pas>m.getNbC()-1)))
				diag2=false;
			if(diag3&&((posx+pas>m.getNbL()-1)||(posy+pas>m.getNbC()-1)))
				diag3=false;
			if(diag4&&((posx+pas>m.getNbL()-1)||(posy-pas<0)))
				diag4=false;
			if(diag1) {
				m.MettrePapierGras(posx-pas, posy-pas);
				m.MettrePapierGras(posx-pas+1, posy-pas);
				m.MettrePapierGras(posx-pas, posy-pas+1);
			}
			if(diag2) {
				m.MettrePapierGras(posx-pas, posy+pas);
				m.MettrePapierGras(posx-pas, posy+pas-1);
				m.MettrePapierGras(posx-pas+1, posy+pas);
			}
			if(diag3) {
				m.MettrePapierGras(posx+pas, posy+pas);
				System.out.println(posx+pas);
				m.MettrePapierGras(posx+pas, posy+pas-1);
				m.MettrePapierGras(posx+pas-1, posy+pas);
			}
			if(diag4) {
				m.MettrePapierGras(posx+pas, posy-pas);
				m.MettrePapierGras(posx+pas, posy-pas+1);
				m.MettrePapierGras(posx+pas-1, posy-pas);
			}
			if(!(diag1||diag2||diag3||diag4)) Detruire();
		}
	}
	public void Detruire() {
		tuer();
	}
	
	
}

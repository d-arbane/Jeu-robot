import java.util.Vector;

public class RobotNettoyeurChasseurSauteur extends RobotNettoyeur{
	private int Portee;
	private boolean peutSauter;
	public RobotNettoyeurChasseurSauteur(int posx, int posy, int v, Monde m, int Cap, int Portee) {
		super(posx, posy, v, m, Cap);
		peutSauter = PeutSauter();
		this.Portee = Portee;
		nommer("CS");
	}

	@Override
	public void Parcourir() throws ExceptionCaseExistePas {
		if(!peutSauter) {
			peutSauter=PeutSauter();
			if(!peutSauter)
				Portee--;
			return;
		}
		int x,y;
		for(int Case=1;Case<8*Portee+1;Case++) {
			if(Case<Portee*2) {
				x=posx-Portee;
				y=posy-Portee+Case;
			}else if(Case<Portee*4) {
				y=posy+Portee;
				x=posx-Portee+Case-2*Portee;
			}else if(Case<Portee*6) {
				x=posx+Portee;
				y=posy-Portee+Case-4*Portee+1;
			}else {
				y=posy-Portee;
				x=posx-Portee+Case-6*Portee;
			}
			if((x>0)&&(x<m.getNbL())&&(y>0)&&(y<m.getNbC())) {
				Vector<RobotPollueur> vec = m.PollueursA(x, y);
				if(vec.size()>0) {
					Vector<RobotPollueur> vecAtuer = new Vector<RobotPollueur>();
					for(RobotPollueur r : vec) 
						vecAtuer.add(r);
					SeDeplacer(x, y);
					if(m.TestPapierGras(posx, posy)) {
						m.PrendsPapierGras(x, y);
						incCharge();
					}
					vec.clear();
					for(RobotPollueur r : vecAtuer)
						r.Detruire();
					return;
				}
			}
			if(m.TestPapierGras(posx, posy)) {
				m.PrendsPapierGras(posx, posy);
				incCharge();
			}
			}
	}
	
	public boolean PeutSauter() {
		int x,y;
		for(int Case=1;Case<8*Portee+1;Case++) {
			if(Case<Portee*2) {
				x=posx-Portee;
				y=posy-Portee+Case;
			}else if(Case<Portee*4) {
				y=posy+Portee;
				x=posx-Portee+Case-2*Portee;
			}else if(Case<Portee*6) {
				x=posx+Portee;
				y=posy-Portee+Case-4*Portee+1;
			}else {
				y=posy-Portee;
				x=posx-Portee+Case-6*Portee;
			}
			if((x>0)&&(x<m.getNbL())&&(y>0)&&(y<m.getNbC()))
				return true;
			}
		return false;
	}

	public void aDecharge() {
		peutSauter=false;
	}
}

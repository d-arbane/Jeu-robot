import java.util.Vector;

public class RobotNettoyeurEssuieGlace extends RobotNettoyeur{
	private boolean dirx,diry,ParY;
	public RobotNettoyeurEssuieGlace(int posx, int posy, int v, Monde m, int Cap) {
		super(posx, posy, v, m, Cap);
		dirx = true;
		diry = true;
		ParY=true;
		nommer("EG");
	}
	
	@Override
	public void Parcourir() throws ExceptionCaseExistePas {
		if(!ParY) //se deplacer se fait implicitement
			ParcourirX();
		else 
			ParcourirY();
		if(m.TestPapierGras(posx, posy)) {
			m.PrendsPapierGras(posx, posy);
			incCharge();
		}
		Vector<RobotPollueur> vec = m.PollueursA(posx, posy),vecAtuer = new Vector<RobotPollueur>();
		for(RobotPollueur r : vec)
			vecAtuer.add(r);
		for(RobotPollueur r : vecAtuer)
			r.Detruire();
		vec.clear();
	}
	private void ParcourirX() {
		int x = (dirx)? posx+1 : posx-1;
		if((x<0)||(x>m.getNbL()-1)) {
				dirx = ! dirx;
				ParY=true;
		}
		else posx=x;
	}
	private void ParcourirY(){
		int y = (diry)? posy+1 : posy-1;
		if((y<0)||(y>m.getNbC()-1)) {
			diry = !diry;
			y = (diry)? posy+1 : posy-1;
		}
		posy = y;
		ParY=false;
	}
}

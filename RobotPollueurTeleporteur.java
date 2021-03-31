public class RobotPollueurTeleporteur extends RobotPollueur{

	public RobotPollueurTeleporteur(int v, Monde m) {
		super(v, m);
		nommer("TP");
	}

	@Override
	public void Parcourir() throws ExceptionCaseExistePas {
		int x=0,y=0;
		do {
		x = (int)(Math.random()*(m.getNbL())); 
		y = (int)(Math.random()*(m.getNbC()));
		}while((m.TestPapierGras(x, y))&&(m.NombrePapierGras()!=m.getNbC()*m.getNbL()));
		if(m.NombrePapierGras()==m.getNbC()*m.getNbL())
			return;
		m.MettrePapierGras(x, y);
		SeDeplacer(x, y);
	}

	@Override
	public void Detruire() throws ExceptionCaseExistePas { //Quand il est détruit il pollue instantanément une nombre aléatoire de cases < nombre de lignes
		int n = (int)(Math.random()*(m.getNbL()));
		for(int i=0;i<=n;i++)
			Parcourir();
		tuer();
		
	}
	
}

import java.util.Vector;

public class GestionnaireRobots {
	Vector<RobotNettoyeur> Nettoyeurs;
	Vector<RobotPollueur> Pollueurs;
	Vector<RobotNettoyeur> Surcharges;
	Vector<RobotPollueur> Detruits;
	private Vector<Vector<Vector<RobotPollueur>>> MatRobotsP;
	
	private long instant;
	public GestionnaireRobots(Monde m, Vector<RobotNettoyeur> nettoyeurs, Vector<RobotPollueur> pollueurs,
			Vector<RobotNettoyeur> surcharges, Vector<RobotPollueur> detruits) {
		Nettoyeurs = nettoyeurs;
		Pollueurs = pollueurs;
		Surcharges = surcharges;
		Detruits = detruits;
		MatRobotsP = new Vector<Vector<Vector<RobotPollueur>>>();
		for(int i=0;i<m.getNbL();i++) {
			MatRobotsP.add(new Vector<Vector<RobotPollueur>>());
			for(int j=0;j<m.getNbC();j++) {
				MatRobotsP.get(i).add(new Vector<RobotPollueur>());
			}
		}
		instant=0L;
	}
	public long getInstant() {
		return instant;
	}
	public Vector<RobotPollueur> PollueursA(int x,int y){return MatRobotsP.get(x).get(y);}
	public void Evolution() throws ExceptionCaseExistePas {
		instant++;
		Vector<RobotNettoyeur> toRemove = new Vector<RobotNettoyeur>();
		for(RobotNettoyeur r:Nettoyeurs)
			if(!r.EstPasSurcharge()) {
				toRemove.add(r);
				Surcharges.add(r);
			}else if(instant%r.getVitesse()==0) 
				r.Parcourir();
		for(RobotNettoyeur r:toRemove)
			Nettoyeurs.remove(r);
		toRemove.clear();
		
		Vector<RobotPollueur> toRemoveP = new Vector<RobotPollueur>();
		for(RobotPollueur r:Pollueurs)
			if(!r.estEnVie()) {
				toRemoveP.add(r);
				Detruits.add(r);
			}else if(instant%r.getVitesse()==0)
				r.Parcourir();
		for(RobotPollueur r:toRemoveP)
			Pollueurs.remove(r);
		toRemoveP.clear();
		
		for(RobotNettoyeur r:Surcharges) {
			r.Decharger();
			if(r.EstPasSurcharge()) {
				toRemove.add(r);
				Nettoyeurs.add(r);
				if(r instanceof RobotNettoyeurChasseurSauteur)
					((RobotNettoyeurChasseurSauteur)r).aDecharge();
			}
		}
		for(RobotNettoyeur r:toRemove)
			Surcharges.remove(r);
		toRemove.clear();
	}

	public void DeplacerRobotPollueur(int x,int y,RobotPollueur r) {
		MatRobotsP.get(r.posx).get(r.posy).remove(r);
		MatRobotsP.get(x).get(y).add(r);
	}
	public void DetruireRobot(RobotPollueur r) {
		MatRobotsP.get(r.posx).get(r.posy).remove(r);
	}

	public void AjouterRobot(Robot r) throws ExceptionCaseExistePas {
		if(r instanceof RobotPollueur)
			if(((RobotPollueur) r).estEnVie())
				Pollueurs.add((RobotPollueur)r);
			else Detruits.add((RobotPollueur)r);
		if(r instanceof RobotNettoyeur)
			if(((RobotNettoyeur)r).EstPasSurcharge())
				Nettoyeurs.add((RobotNettoyeur)r);
			else Surcharges.add((RobotNettoyeur)r);
		if(r instanceof RobotPollueurSauteur)
			if(!((RobotPollueurSauteur)r).peutSauter())
				((RobotPollueurSauteur)r).Detruire();
	}
}

import java.util.ArrayList;

public class RobotNettoyeurSpiraleMaximale extends RobotNettoyeur{
	private int xSource,ySource,larg,rep,taux,caseC, nbCase;
	private ArrayList<Integer> itineraireX,itineraireY;
	public RobotNettoyeurSpiraleMaximale(int v, Monde m, int cap, int largeur, int taux) {
		super(v, m, cap);
		TrouverItineraire(largeur);
		this.taux=taux;
		xSource = -1;
		ySource = -1;
		rep = 0;
		nbCase = largeur*largeur-1;
		larg = largeur;
		nommer("SM");
	}

	@Override
	public void Parcourir() throws ExceptionCaseExistePas {//Si il n'y a pas de source trouver une source, sinon parcourir de mani�re circulaire 2 cases par tour
		if(xSource==-1) {
			TrouverSource();
			caseC=0;
			SeDeplacer(xSource, ySource);
			if(m.TestPapierGras(xSource, ySource)) {
				incCharge();
				m.PrendsPapierGras(xSource, ySource);
			}
			return;
		}
		int x = xSource+itineraireX.get(caseC), y = ySource+itineraireY.get(caseC);
		caseC++;
		SeDeplacer(x, y);
		if(m.TestPapierGras(x, y)) {
			incCharge();
			m.PrendsPapierGras(x, y);
		}
		if(caseC==nbCase+1) {//On est arriv�s
			xSource = -1;
			rep = 0;
		}
		rep ++;
		if(rep%2==0) {//Avance deux fois par tour
			Parcourir();
		}
	}
	private void TrouverSource() throws ExceptionCaseExistePas {
		int x = 0, y = 0, max = 0;
		int nbPapiersGras = larg*larg;
		if(nbPapiersGras>0)
			for(int i = 0;i<m.getNbL()-larg+1;i++)
				for(int j = 0;j<m.getNbC()-larg+1;j++) { //Deux premieres boucles pour selectionner une source valable
					int somme = 0;
					for(int k=i;k<i+larg;k++)  //Deux secondes boucles pour calculer le nombre de papiers gras 
						for(int l=j;l<j+larg;l++) 
							if(m.TestPapierGras(k, l))
								somme++;
					if(somme>max) {
						x = i;
						y = j;
					}
					if((somme>0)&&((int)((double)somme*100/(double)nbPapiersGras)>=taux)) {
						xSource = i;
						ySource = j;
						return;
					}
			}
		xSource = x;
		ySource = y;
	}
	
	private void TrouverItineraire(int larg) {
		ArrayList<Integer> x=new ArrayList<Integer>(),y=new ArrayList<Integer>();
		/////////////////
		int N=larg*larg,Min = 0, Max = larg-1,var = larg -1;
		
		while(x.size()<N) {
			for(int i=0;i<var;i++)
				if(x.size()<N)
					x.add(Min);
			var--;
			for(int i=Min;i<=Max;i++)
				if(x.size()<N)
					x.add(i);
			for(int i=0;i<var;i++)
				if(x.size()<N) 
					x.add(Max);
			var--;
			Min++;
			for(int i=Max;i>=Min;i--)
				if(x.size()<N) 
					x.add(i);
			Max--;
		}
		/////////////////
		/////////////////
		Min = 0;
		Max = larg - 1;
		var = larg - 1;
		
		while(y.size()<N) {
			for(int i=Min;i<=Max;i++)
				if(y.size()<N)
					y.add(i);
			for(int i=0;i<var-1;i++)
				if(y.size()<N)
					y.add(Max);
			for(int i=Max;i>=Min;i--)
				if(y.size()<N)
					y.add(i);
			for(int i=0;i<var-1;i++)
				if(y.size()<N)
					y.add(Min);
			var-=2;
			Min++;
			Max--;
		}
		itineraireX=x;
		itineraireY=y;
	}
}

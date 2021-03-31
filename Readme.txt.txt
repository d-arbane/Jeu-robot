
Le monde évolue de manière synchrone à chaque ‘instant’, le gestionnaire 
de robots se charge de cette évolution en incrémentant la valeur d’instant et en 
itérant à travers touts les robots pour voir si il est temps de les déplacer.
Chaque robot a une ‘vitesse’, plus sa valeur est basse plus fréquemment il 
se déplacera.
________________________________
Les robots nettoyeurs ont une capacité et une charge, la charge 
s’incrémente à chaque fois que le robot ramasse un papier gras, quand elle est 
égale à la capacité le robot se déplace vers la décharge ou il se libérera de sa 
charge. En cours de route vers la décharge le robot ne prendra pas de papiers 
gras.
_________________________________
Touts les robots nettoyeurs peuvent détruire un robot pollueur s’ils se 
retrouvent sur la même case, sauf le robot en spirale.
Les robots pollueurs agissent différemment à leur destruction, le 
téléporteur pollue un nombre aléatoire de case, le sauteur pollue toutes les cases
qui l’entourent et les autres ne font rien.
_________________________________
Parcours : 
Pollueur Sauteur ; il est doté d’une portée, il saute sur une case aléatoire à 
sa portée (un carré avec le robot au milieu), s’il ne peut sauter sur aucune case il 
s’autodétruit.
Pollueur Tout Droit ; il longe une seule colonne et ne la quitte jamais.
Pollueur Téléporteur ; il se téléporte sur une case aléatoire et la pollue.
Pollueur Tir Diagonal ; il ne se déplace pas, à la place il tire une jet de 
papier gras sur ses quatre diagonales, il s’arrête après avoir atteint les limites du 
monde puis s’autodétruit.
Nettoyeur Tout Droit ; il longe une seule ligne et y retourne toujours après 
avoir déchargé.
Nettoyeur Essuie Glace ; il longe les colonnes une à une de gauche a droite
puis de droite a gauche.
Nettoyeur Chasseur Sauteur ; il ne se déplace que si un robot pollueur est 
à sa porté pour le détruire, sinon il nettoie uniquement la case sur laquelle il est.
Nettoyeur Spirale Maximale ; il est doté d’une largeur, qui est la largeur du 
carré qu’il va nettoyer, à son instanciation il établit l’itinéraire qu’il suivra lors de 
ses parcours. Pour parcourir il doit d’abord trouver une source, il choisit celle 
depuis lequel il prendra le plus de papiers gras (ou un taux acceptable définit à 
l’instanciation). Puis il lance son parcours, il avance deux cases à la fois.
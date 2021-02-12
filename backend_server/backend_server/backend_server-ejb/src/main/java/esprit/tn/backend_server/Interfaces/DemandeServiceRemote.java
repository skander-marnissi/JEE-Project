package esprit.tn.backend_server.Interfaces;


import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import esprit.tn.backend_server.Entities.AutreDemande;
import esprit.tn.backend_server.Entities.DemandeRemboursement;



@Remote
public interface DemandeServiceRemote {

	
	public void affecterAutreDemandeAUser(int userId, int autreDemandeId);
	public void affecterDemandeRemboursementAUser(int userId, int demandeRemboursementId);
	public List<DemandeRemboursement> getAllDemandeRemboursementByUser(int userId);
	public List<AutreDemande> getAllAutreDemandeByUser(int userId);
	public void deleteDemandeRemboursementById(int demandeRemboursementId);
	public void deleteAutreDemandeById(int auteDemandeId);
	public AutreDemande getAutreDemandeById(int auteDemandeId);
	public DemandeRemboursement getDemandeRemboursementById(int demandeRemboursementId);
	int ajouterDemandeRemboursement(DemandeRemboursement demandeRemboursement, int userId);
	int ajouterAutreDemande(AutreDemande autreDemande, int userId);
	
	
}

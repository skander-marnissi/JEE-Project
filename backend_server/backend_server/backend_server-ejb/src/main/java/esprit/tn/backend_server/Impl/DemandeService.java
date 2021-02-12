package esprit.tn.backend_server.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.CurrencyCode;

import esprit.tn.backend_server.Entities.AutreDemande;
import esprit.tn.backend_server.Entities.DemandeRemboursement;
import esprit.tn.backend_server.Entities.EtatDemande;

import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Interfaces.DemandeServiceRemote;




@Stateless
@LocalBean
public class DemandeService implements DemandeServiceRemote{
	
	@PersistenceContext(unitName = "backend_server-ejb")
	EntityManager em;
	

	
/*---------------------------------------------------------------Demande Remboursement-------------------------------------------------------------------*/	
	
	
	
	public List<DemandeRemboursement> getAllDemandeDeRemboursementEnAttentes() {
		
		TypedQuery<DemandeRemboursement> query = em.createQuery("Select dem from DemandeRemboursement dem where dem.etatDemande=:etatDemande", DemandeRemboursement.class);
		
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		
		return query.getResultList();
	}
	
	public List<DemandeRemboursement> getAllDemandeDeRemboursementTraiter() {

		TypedQuery<DemandeRemboursement> query = em.createQuery("Select dem from DemandeRemboursement dem where dem.etatDemande!=:etatDemande", DemandeRemboursement.class);
		
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		
		return query.getResultList();
	}
	
	public List<DemandeRemboursement> getAllDemandeDeRemboursementTraiterForRest(int idFinancier) {

		TypedQuery<DemandeRemboursement> query = em.createQuery("Select dem from DemandeRemboursement dem where dem.etatDemande!=:etatDemande and dem.idFinancier=:idFinancier", DemandeRemboursement.class);
		
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		query.setParameter("idFinancier", idFinancier);
		
		return query.getResultList();
	}
	
	
	
	
	public List<DemandeRemboursement> getAllDemandeDeRemboursementAccepter() {
		
		TypedQuery<DemandeRemboursement> query = em.createQuery("Select dem from DemandeRemboursement dem where dem.etatDemande=:etatDemande", DemandeRemboursement.class);
		
		
		query.setParameter("etatDemande", EtatDemande.ACCEPTER);
		
		return query.getResultList();
	}
	
	
	
	public List<DemandeRemboursement> getAllDemandeDeRemboursementRejeter() {
		
		TypedQuery<DemandeRemboursement> query = em.createQuery("Select dem from DemandeRemboursement dem where dem.etatDemande=:etatDemande", DemandeRemboursement.class);
		
		
		query.setParameter("etatDemande", EtatDemande.REJETER);
		
		return query.getResultList();
	}
	
	public List<DemandeRemboursement> getAllDemandeDeRemboursement(int idEmploye) {
		
	
		System.out.println("---i'm in allDemandesEnAttente");
		
		User userManagedEntity = em.find(User.class, idEmploye);
		
		
		for(DemandeRemboursement d : userManagedEntity.getDemandeRemboursement()) {
			System.out.println("---ID Demande Rem : "+ d.getId());
		}
		
		return userManagedEntity.getDemandeRemboursement();	
	
	}
	
	
	@Override
	public int ajouterDemandeRemboursement(DemandeRemboursement demandeRemboursement, int userId) {
		//entreprise.setName("autre entreprise"); avec ça une autre requete d'update 
		//va s'exécuter a la fin de la methode pour mettre
		//a jour la base de données avec le nouveau nom
		User userManagedEntity = em.find(User.class, userId);
		
		String country = userManagedEntity.getMission().getPaysMission();
		
		/*Curency and country code*/
		
		System.out.println("size :"+CurrencyCode.getByCountry(country).size());
		
		for(CurrencyCode cc :CurrencyCode.getByCountry(country,false) ) {
			System.out.println("------------coutnry name :"+cc.getName());
			
		}
		
		demandeRemboursement.setUser(userManagedEntity);
		demandeRemboursement.setIdMission(userManagedEntity.getMission().getId());
	
		em.persist(demandeRemboursement);
		return demandeRemboursement.getId();
	}
	
	@Override
	public DemandeRemboursement getDemandeRemboursementById(int demandeRemboursementId){
		return em.find(DemandeRemboursement.class, demandeRemboursementId);
	}

	@Override
	public void deleteDemandeRemboursementById(int demandeRemboursementId){
		em.remove(em.find(DemandeRemboursement.class, demandeRemboursementId));
	}
	
	
	

	
	public void rejectDemandeDeRemboursement(int demandeId, int idFinancier) {
		DemandeRemboursement demandeManagedEntity = em.find(DemandeRemboursement.class, demandeId);
		
		demandeManagedEntity.setEtatDemande(EtatDemande.REJETER);
		demandeManagedEntity.setIdFinancier(idFinancier);
		demandeManagedEntity.setDateValidation(Calendar.getInstance());
		
		
		em.merge(demandeManagedEntity);
	}



	public void acceptDemandeDeRemboursement(int demandeId, int idFinancier) {
		
		DemandeRemboursement demandeManagedEntity = em.find(DemandeRemboursement.class, demandeId);
		
		demandeManagedEntity.setEtatDemande(EtatDemande.ACCEPTER);
		demandeManagedEntity.setIdFinancier(idFinancier);
		demandeManagedEntity.setDateValidation(Calendar.getInstance());
		
		
		em.merge(demandeManagedEntity);
		
	}
	
	
	@Override
	public void affecterDemandeRemboursementAUser(int userId, int demandeRemboursementId) {
		//Le bout Master de cette relation N:1 est departement  
		//donc il faut rajouter l'entreprise a departement 
		// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
		//Rappel : la classe qui contient mappedBy represente le bout Slave
		//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		User entrepriseManagedEntity = em.find(User.class, userId);
		DemandeRemboursement depManagedEntity = em.find(DemandeRemboursement.class, demandeRemboursementId);

		depManagedEntity.setUser(entrepriseManagedEntity);
		//inutile de faire : em.merge(depManagedEntity);
		//Dans ce cas, le merge n'est pas utile parce que depManagedEntity est 
		//dans l'état managed, autrement dit, 
		//depManagedEntity existe dans le persistence context.
		//n'importe quel entité dans le persistence context sera automatiquement
		//synchronisé avec la base de donnees
	}
	
	@Override
	public List<DemandeRemboursement> getAllDemandeRemboursementByUser(int userId) {
		User entrepriseManagedEntity = em.find(User.class, userId);
		List<DemandeRemboursement> depNames = new ArrayList<>();
		for(DemandeRemboursement dep : entrepriseManagedEntity.getDemandeRemboursement()){
			depNames.add(dep);
		}
		
		return depNames;
	}
	
	
/*---------------------------------------------------------------Autre Demande-------------------------------------------------------------------*/	
	

	public List<AutreDemande> getAllAutreDemandeEnAttentes() {
		
		TypedQuery<AutreDemande> query = em.createQuery("Select dem from AutreDemande dem where dem.etatDemande=:etatDemande", AutreDemande.class);
		
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		
		return query.getResultList();
	}
	
	public List<AutreDemande> getAllAutreDemandeTraiter() {

		TypedQuery<AutreDemande> query = em.createQuery("Select dem from AutreDemande dem where dem.etatDemande!=:etatDemande", AutreDemande.class);
		
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		
		return query.getResultList();
	}
	
	public List<AutreDemande> getAllAutreDemandeTraiterForRest(int idManager) {

		TypedQuery<AutreDemande> query = em.createQuery("Select dem from AutreDemande dem where dem.etatDemande!=:etatDemande and dem.idManager=:idManager", AutreDemande.class);
		
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		query.setParameter("idManager", idManager);
		
		return query.getResultList();
	}
	
	
	public List<AutreDemande> getAllAutreDemande(int idEmploye) {
		
		System.out.println("---i'm in allDemandesEnAttente");
		
		User userManagedEntity = em.find(User.class, idEmploye);
		
		
		for(AutreDemande d : userManagedEntity.getAutreDemande()) {
			System.out.println("---ID Demande Rem : "+ d.getId());
		}
		
		return userManagedEntity.getAutreDemande();	
	}

	
	public List<AutreDemande> getAllAutreDemandeAccepter() {
		
		TypedQuery<AutreDemande> query = em.createQuery("Select dem from AutreDemande dem where dem.etatDemande=:etatDemande", AutreDemande.class);
		
		
		query.setParameter("etatDemande", EtatDemande.ACCEPTER);
		
		return query.getResultList();
	}
	
	
	
	public List<AutreDemande> getAllAutreDemandeRejeter() {
		
		TypedQuery<AutreDemande> query = em.createQuery("Select dem from AutreDemande dem where dem.etatDemande=:etatDemande", AutreDemande.class);
		
		
		query.setParameter("etatDemande", EtatDemande.REJETER);
		
		return query.getResultList();
	}

	public List<AutreDemande> getAllAutreDemandeTraiter(int idManager) {
		
		TypedQuery<AutreDemande> query = em.createQuery("Select dem from AutreDemande dem where dem.etatDemande!=:etatDemande and dem.idManager=:idManager", AutreDemande.class);
		
		
		System.out.println("---i'm in allDemandesEnTraiter");
		
		query.setParameter("etatDemande", EtatDemande.EN_ATTENTE);
		query.setParameter("idManager", idManager);
		
		return query.getResultList();
	}



	public void rejectAutreDemande(int demandeId, int idManager) {
		AutreDemande demandeManagedEntity = em.find(AutreDemande.class, demandeId);
		
		demandeManagedEntity.setEtatDemande(EtatDemande.REJETER);
		demandeManagedEntity.setIdManager(idManager);
		demandeManagedEntity.setDateValidation(Calendar.getInstance());
		
		em.merge(demandeManagedEntity);
		
	}



	public void acceptAutreDemande(int demandeId, int idManager) {
		AutreDemande demandeManagedEntity = em.find(AutreDemande.class, demandeId);
		
		demandeManagedEntity.setEtatDemande(EtatDemande.ACCEPTER);
		demandeManagedEntity.setIdManager(idManager);
		demandeManagedEntity.setDateValidation(Calendar.getInstance());
		
		
		em.merge(demandeManagedEntity);
		
	}
	
	
	
	@Override
	public int ajouterAutreDemande(AutreDemande autreDemande , int userId) {
		//entreprise.setName("autre entreprise"); avec ça une autre requete d'update 
		//va s'exécuter a la fin de la methode pour mettre
		//a jour la base de données avec le nouveau nom
		User userManagedEntity = em.find(User.class, userId);
		
		autreDemande.setIdMission(userManagedEntity.getMission().getId());
		
		autreDemande.setUser(userManagedEntity);
		
		em.persist(autreDemande);
		return autreDemande.getId();
	}
	

	
	
	
	
	@Override
	public void affecterAutreDemandeAUser(int userId, int autreDemandeId) {
		//Le bout Master de cette relation N:1 est departement  
		//donc il faut rajouter l'entreprise a departement 
		// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
		//Rappel : la classe qui contient mappedBy represente le bout Slave
		//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		User entrepriseManagedEntity = em.find(User.class, userId);
		AutreDemande depManagedEntity = em.find(AutreDemande.class, autreDemandeId);

		depManagedEntity.setUser(entrepriseManagedEntity);
		//inutile de faire : em.merge(depManagedEntity);
		//Dans ce cas, le merge n'est pas utile parce que depManagedEntity est 
		//dans l'état managed, autrement dit, 
		//depManagedEntity existe dans le persistence context.
		//n'importe quel entité dans le persistence context sera automatiquement
		//synchronisé avec la base de donnees
	}
	
	

	@Override
	public List<AutreDemande> getAllAutreDemandeByUser(int userId) {
		User entrepriseManagedEntity = em.find(User.class, userId);
		List<AutreDemande> depNames = new ArrayList<>();
		for(AutreDemande dep : entrepriseManagedEntity.getAutreDemande()){
			depNames.add(dep);
		}
		
		return depNames;
	}
	

	@Override
	public void deleteAutreDemandeById(int auteDemandeId){
		em.remove(em.find(AutreDemande.class, auteDemandeId));
	}
	
	


	@Override
	public AutreDemande getAutreDemandeById(int auteDemandeId){
		return em.find(AutreDemande.class, auteDemandeId);
	}



	



	







}

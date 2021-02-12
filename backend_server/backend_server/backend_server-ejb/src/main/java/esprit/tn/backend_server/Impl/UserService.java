package esprit.tn.backend_server.Impl;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import esprit.tn.backend_server.Entities.Etat;
import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Interfaces.UserServiceRemote;


@Stateless
@LocalBean


public class UserService implements UserServiceRemote {
	@PersistenceContext(unitName = "backend_server-ejb")
	EntityManager em;
	
	@Override
	public int ajouterUser(User User) {
		em.persist(User);
		return User.getId();
	}
	
	public void updateUser(User User) { 
		User emp = em.find(User.class, User.getId()); 
		
		emp.setPrenom(User.getPrenom()); 
		emp.setNom(User.getNom()); 
		emp.setEmail(User.getEmail());
		emp.setEtat(User.getEtat()); 
		emp.setRole(User.getRole());
		emp.setAdresse(User.getAdresse());
		emp.setTel(User.getTel());
		
		
		em.merge(emp);
		} 
	
	@Override
	public void updateUserIdJPQL(User user){
		Query query = em.createQuery("update User e set nom=:nom, prenom=:prenom, email=:email, etat=:etat, role=:role  where e.id=:userId");
		query.setParameter("nom", user.getNom());
		query.setParameter("prenom", user.getPrenom());
		query.setParameter("email", user.getEmail());
		query.setParameter("etat", user.getEtat());
		query.setParameter("role", user.getRole());
		query.setParameter("userId", user.getId());
		int modified = query.executeUpdate();
		
		if(modified == 1){
			System.out.println("successfully updated");
		}else{
			System.out.println("failed to update");
		}
	}



	@Override
	public void affecterUserAMission(int UserId, int missionId) {
		Mission missionManagedEntity = em.find(Mission.class, missionId);
		User UserManagedEntity = em.find(User.class, UserId);

		if(missionManagedEntity.getUsers() == null){
			List<User> Users = new ArrayList<>();
			Users.add(UserManagedEntity);
			missionManagedEntity.setUsers(Users);
		}else{
			missionManagedEntity.getUsers().add(UserManagedEntity);
		}

		em.merge(UserManagedEntity);
	}
	

	public void affecterUserAMission2(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
		//donc il faut rajouter l'entreprise a departement 
		// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
		//Rappel : la classe qui contient mappedBy represente le bout Slave
		//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		Mission missionManagedEntity = em.find(Mission.class, entrepriseId);
		User userManagedEntity = em.find(User.class, depId);
		
		userManagedEntity.setEtat(Etat.INDISPONIBLE);
		
		userManagedEntity.setMission(missionManagedEntity);
		//inutile de faire : em.merge(depManagedEntity);
		//Dans ce cas, le merge n'est pas utile parce que depManagedEntity est 
		//dans l'état managed, autrement dit, 
		//depManagedEntity existe dans le persistence context.
		//n'importe quel entité dans le persistence context sera automatiquement
		//synchronisé avec la base de donnees
	}
	
	
	
	
	/*
	
	@Override
	public void affecterContratAUser(int contratId, int UserId) {
		Contrat contratManagedEntity = em.find(Contrat.class, contratId);
		User UserManagedEntity = em.find(User.class, UserId);
		
		contratManagedEntity.setUser(UserManagedEntity);
		
		em.merge(contratManagedEntity);
	}
	
	*/
	@Override
	public void deleteUserById(int UserId) {
		User User = em.find(User.class, UserId);
		
		//Desaffecter l'User de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		/*for(Mission dep : dep.getMission()){
			dep.getUsers().remove(User);
		}
		*/
		em.remove(User);
	}
	
	
	
	@Override
	public String getUserPrenomById(int UserId) {
		User User = em.find(User.class, UserId);
		return User.getPrenom();
	}


//	@Override
//	public void deleteUserById(int UserId) {
//		em.remove(em.find(User.class, UserId));
//	}
	
	


	@Override
	public void desaffecterUserDeMission(int UserId, int missionId) {
		Mission dep = em.find(Mission.class, missionId);
		
		int UserNb = dep.getUsers().size();
		for(int index = 0; index < UserNb; index++){
			if(dep.getUsers().get(index).getId() == UserId){
				dep.getUsers().remove(index);
				break;//a revoir
			}
		}
		
		//em.merge(dep);
	}


	

	@Override
	public void mettreAjourEmailByUserId(String email, int UserId) {
		User User = em.find(User.class, UserId);
		User.setEmail(email);
	}


	@Override
	public long getNombreUserJPQL() {
		TypedQuery<Long> query = em.createQuery("select count(e) from User e", Long.class);
		return query.getSingleResult();
	}


	@Override
	public List<String> getAllUserNamesJPQL() {
		List<User> Users = em.createQuery("select e from User e", User.class).getResultList();
		List<String> UserNames = new ArrayList<>();
		for(User User : Users){
			UserNames.add(User.getNom());
		}
		
		return UserNames;
	}
	
	@Override
	public void mettreAjourEmailByUserIdJPQL(String email, int UserId){
		Query query = em.createQuery("update User e set email=:email where e.id=:UserId");
		query.setParameter("email", email);
		query.setParameter("UserId", UserId);
		int modified = query.executeUpdate();
		if(modified == 1){
			System.out.println("successfully updated");
		}else{
			System.out.println("failed to update");
		}
	}

    
	@Override
	public List<User> getAllUserByMission(Mission entreprise) {
		TypedQuery<User> query = em.createQuery("Select "
				+ "DISTINCT emp from User emp "
				+ "join dps.Mission entrep "
				+ "where entrep=:entreprise", User.class);
		
		query.setParameter("entreprise", entreprise);
		return query.getResultList();
	}

    
	


	/*public User getUserByEmailAndPassword(String login, String password) {
		TypedQuery<User> query=em.createQuery("Select e from User e where e.email=:email and e.password=:password", User.class);
	query.setParameter("email", email);
	query.setParameter("password", password);
	
	}*/
	public User getUserByEmailAndPassword(String email, String mdpLogin) {
		TypedQuery<User> query= em.createQuery("Select e from User e " + "where e.email=:email and "+"e.mdpLogin=:mdpLogin", User.class);
		query.setParameter("email", email);
		query.setParameter("mdpLogin", mdpLogin);
		User User = null;
		try{
			
			User = query.getSingleResult();
		} 
	catch(NoResultException e){
 Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
		}
		return User;
	}
	
	public User getUserByLoginAndPassword(String email, String mdpLogin) {
		TypedQuery<User> query= em.createQuery("Select e from User e " + "where e.idLogin=:idLogin and "+"e.mdpLogin=:mdpLogin", User.class);
		query.setParameter("idLogin", email);
		query.setParameter("mdpLogin", mdpLogin);
		User User = null;
		try{
			
			User = query.getSingleResult();
		} 
	catch(NoResultException e){
 Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
		}
		return User;
	}


	public List<User> getAllUsers() {
		List<User> emp = em.createQuery("Select e from User e", User.class).getResultList();
		return emp;
		}
	

	public List<User> getAllEmployees() {
		List<User> emp = null;
		TypedQuery<User> query = em.createQuery("Select e from User e where e.role=:role", User.class);
		//
		query.setParameter("role", Role.EMPLOYEE);
		try{
			
			emp = query.getResultList();
		} 
	catch(NoResultException e){
			Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
		}
		
		return emp;
		}
	

	public List<User> getAllManagers() {
		List<User> emp = null;
		TypedQuery<User> query = em.createQuery("Select e from User e where e.role=:role", User.class);
		//
		query.setParameter("role", Role.MANAGER);
		try{
			
			emp = query.getResultList();
		} 
		
		
			catch(NoResultException e){
		Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
	}
	
	return emp;
	}

	public List<User> getAllFinanciers() {
		List<User> emp = null;
		TypedQuery<User> query = em.createQuery("Select e from User e where e.role=:role", User.class);
		//
		query.setParameter("role", Role.FINANCIER);
		try{
			
			emp = query.getResultList();
		} 
		
		
			catch(NoResultException e){
		Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
	}
	
	return emp;
	}
	
	public List<User> getAllEmployeesDisponible() {
		List<User> emp = null;
		TypedQuery<User> query = em.createQuery("Select e from User e where e.role=:role and e.etat=:etat", User.class);
		//
		query.setParameter("role", Role.EMPLOYEE);
		query.setParameter("etat", Etat.DISPONIBLE);
		try{
			
			emp = query.getResultList();
		} 
	catch(NoResultException e){
			Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
		}
		
		return emp;
		}

	public User getUserById(int userId) {
		
		return em.find(User.class, userId);
		
		
	}
	
	public List<User> getAllEmployeesIndisponible() {
		List<User> emp = null;
		TypedQuery<User> query = em.createQuery("Select e from User e where e.role=:role and e.etat=:etat", User.class);
		//
		query.setParameter("role", Role.EMPLOYEE);
		query.setParameter("etat", Etat.INDISPONIBLE);
		try{
			
			emp = query.getResultList();
		} 
	catch(NoResultException e){
			Logger.getAnonymousLogger().info("Pbleeeeeeeeme"); //	logger.info("Aucun User trouve ayant un email pareil"  + email);
		}
		
		return emp;
		}


}

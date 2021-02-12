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
import esprit.tn.backend_server.Entities.Progress;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Interfaces.MissionServiceRemote;


@Stateless
@LocalBean
public class MissionService implements MissionServiceRemote{

	@PersistenceContext(unitName = "backend_server-ejb")
	EntityManager em;
	
	
	@Override
	public int ajouterMission(Mission mission) {
		//entreprise.setName("autre entreprise"); avec ça une autre requete d'update 
		//va s'exécuter a la fin de la methode pour mettre
		//a jour la base de données avec le nouveau nom
		
		em.persist(mission);
		return mission.getId();
	}
	
	@Override
	public int ajouterUser(User user) {
		em.persist(user);
		return user.getId();
	}
	
	@Override
	public void affecterUserAMission(int userId, int missionId) {
		//Le bout Master de cette relation N:1 est departement  
		//donc il faut rajouter l'entreprise a departement 
		// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
		//Rappel : la classe qui contient mappedBy represente le bout Slave
		//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		Mission missionManagedEntity = em.find(Mission.class, missionId);
		User userManagedEntity = em.find(User.class, userId);

		userManagedEntity.setEtat(Etat.INDISPONIBLE);
		
		userManagedEntity.setMission(missionManagedEntity);
		//inutile de faire : em.merge(depManagedEntity);
		//Dans ce cas, le merge n'est pas utile parce que depManagedEntity est 
		//dans l'état managed, autrement dit, 
		//depManagedEntity existe dans le persistence context.
		//n'importe quel entité dans le persistence context sera automatiquement
		//synchronisé avec la base de donnees
	}
	
	
	@Override
	public List<String> getAllUsersNamesByMission(int missionId) {
		Mission missionManagedEntity = em.find(Mission.class, missionId);
		List<String> userNames = new ArrayList<>();
		for(User users : missionManagedEntity.getUsers()){
			userNames.add(users.getNom());
		}
		
		return userNames;
	}
	
	@Override
	public void deleteMissionById(int missionId){
		
		Mission missionManagedEntity = em.find(Mission.class, missionId);
		User userManagedEntity ;
		for(User user : missionManagedEntity.getUsers()){
			userManagedEntity = em.find(User.class,user.getId());
			userManagedEntity.setEtat(Etat.DISPONIBLE);
			userManagedEntity.setMission(null);
			em.merge(userManagedEntity);
		}
		em.remove(em.find(Mission.class, missionId));
	}
	
	@Override
	public void deleteUserById(int userId){
		em.remove(em.find(User.class, userId));
	}



	@Override
	public Mission getMissionById(int missionId){
		return em.find(Mission.class, missionId);
	}


	public List<Mission> getMissionFinishedByManagerId(int idManager) {
		
		TypedQuery<Mission> query = em.createQuery("Select miss from Mission miss where miss.idManager=:idManager and miss.progression=:progression", Mission.class);
		
		
		
		query.setParameter("idManager", idManager);
		
		query.setParameter("progression", Progress.TERMINER);
		
		return query.getResultList();
		
	} 
	
	public List<Mission> getMissionNonFinishedByManagerId(int idManager) {
		
		 return checkUpdateMissionsDates(idManager);
		
	} 
	
	
	public List<Mission> getMissionByManagerId(int idManager) {
		

		return checkUpdateMissionsDates(idManager);
	}
	
	
	public List<Mission> getMissionByManagerIdRest(int idManager) {
		

			TypedQuery<Mission> query = em.createQuery("Select miss from Mission miss where miss.idManager=:idManager", Mission.class);
		
		
			query.setParameter("idManager", idManager);
		
	
		
			return query.getResultList();
	}
	

	public List<Mission> checkUpdateMissionsDates(int idManager) {
		
		TypedQuery<Mission> query = em.createQuery("Select miss from Mission miss where miss.idManager=:idManager and miss.progression!=:progression", Mission.class);
		
		
		query.setParameter("idManager", idManager);
		
		query.setParameter("progression", Progress.TERMINER);
		
		List<Mission> missions = query.getResultList();
		
		
		for(Mission m : missions) {
			if(m.getDateFin().before(new Date())) {
				
				System.out.println("Boolean dates : "+m.getDateFin().before(new Date()));
				m.setProgression(Progress.TERMINER);
				
				for(User u : m.getUsers()) {
						
						deleteUserFromMission(u.getId());
					}
				
				updateMission(m);
				
			}
		}
		
		/**/
	
		return query.getResultList();
		
		
	}

	
	public void deleteUserFromMission(int userId) {
		// TODO Auto-generated method stub

			User userManagedEntity= em.find(User.class, userId);
 
			
			System.out.println("------------------------------------------------------------------ user id : "+ userId);
			userManagedEntity.setEtat(Etat.DISPONIBLE);
			userManagedEntity.setMission(null);
			em.merge(userManagedEntity);
		
		
		
	}

	public void updateMission(Mission mission) { 
		Mission missionManagedEntity = em.find(Mission.class, mission.getId()); 
		
		missionManagedEntity.setName(mission.getName()); 
		missionManagedEntity.setDescription(mission.getDescription()); 
		missionManagedEntity.setLieuMission(mission.getLieuMission());
		//emp.setPassword(User.getPassword()); 
		missionManagedEntity.setDateDebut(mission.getDateDebut());
		missionManagedEntity.setDateFin(mission.getDateFin());
		missionManagedEntity.setProgression(mission.getProgression());
		em.merge(missionManagedEntity);
		}


	
	
}

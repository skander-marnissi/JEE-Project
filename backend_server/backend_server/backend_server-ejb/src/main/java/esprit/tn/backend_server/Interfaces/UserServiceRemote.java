package esprit.tn.backend_server.Interfaces;


import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;



@Remote
public interface UserServiceRemote {
	
	public int ajouterUser(User user);
	public void affecterUserAMission(int UserId, int missionId);
	public String getUserPrenomById(int UserId);
	public void desaffecterUserDeMission(int UserId, int missionId);	
	public void mettreAjourEmailByUserId(String email, int UserId);
	public long getNombreUserJPQL();
	public List<String> getAllUserNamesJPQL();
	public void mettreAjourEmailByUserIdJPQL(String email, int UserId);
	public List<User> getAllUserByMission(Mission mission);	
	public void deleteUserById(int UserId);
	public void updateUserIdJPQL(User user);
}

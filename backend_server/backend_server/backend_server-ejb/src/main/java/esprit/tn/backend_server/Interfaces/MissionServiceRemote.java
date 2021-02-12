package esprit.tn.backend_server.Interfaces;


import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;



@Remote
public interface MissionServiceRemote {
	
	public int ajouterMission(Mission mission);
	public int ajouterUser(User user);
	public void affecterUserAMission(int userId, int missionId);
	public List<String> getAllUsersNamesByMission(int missionId);
	public void deleteMissionById(int missionId);
	public void deleteUserById(int userId);
	public Mission getMissionById(int missionId);


}

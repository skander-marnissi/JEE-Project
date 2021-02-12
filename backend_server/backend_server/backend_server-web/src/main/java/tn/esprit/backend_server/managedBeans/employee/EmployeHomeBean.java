package tn.esprit.backend_server.managedBeans.employee;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;



@ManagedBean
@ViewScoped
public class EmployeHomeBean {

@EJB
MissionService missionService;

@EJB
UserService userService;
	
private Mission mission;

private int idUser;

private boolean inMission;
	
	@PostConstruct
	public void init(){
		
		
		
		HttpSession session = SessionUtils.getSession();
		idUser = (int) session.getAttribute("userid");
		
		 mission = userService.getUserById(idUser).getMission();
		
		
		 inMission = true;
	
		if(mission==null) {
			
			inMission = false ;
			
		}
		
		
	}

	public MissionService getMissionService() {
		return missionService;
	}

	public void setMissionService(MissionService missionService) {
		this.missionService = missionService;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public boolean isInMission() {
		return inMission;
	}

	public void setInMission(boolean inMission) {
		this.inMission = inMission;
	}
	
	
//	
	
	
	
}

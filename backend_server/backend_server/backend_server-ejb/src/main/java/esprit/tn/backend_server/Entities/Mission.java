package esprit.tn.backend_server.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Mission implements Serializable{

	private static final long serialVersionUID = -5369734855993305723L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nom")
	private String name;
	
	private String description;
	
	private String lieuMission;
	
	private String paysMission;
	
	private int idManager;
	
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Progress progression;
	
	@OneToMany(mappedBy="mission")
	private  List<User> users;
	

	public Mission() {
		super();
	}
	
	



	public Mission(int id, String name, String description, String lieuMission, Date dateDebut, Date dateFin,
			Progress progression, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.lieuMission = lieuMission;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.progression = progression;
		this.users = users;
	}


	



	public Mission(String name, String description, String lieuMission, Date dateDebut,
			Date dateFin,String paysMission) {
		super();
		this.name = name;
		this.description = description;
		this.lieuMission = lieuMission;
		
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.paysMission = paysMission;
		
		if(this.dateDebut.after(new Date())) {
				
				this.progression = Progress.EN_ATTENTE;
				
			}
			else {
				
				this.progression = Progress.EN_COURS;
				
			}
	}





	public Mission(String name, String description, String lieuMission, Date dateDebut, Date dateFin,
			Progress progression, List<User> users) {
		super();
		this.name = name;
		this.description = description;
		this.lieuMission = lieuMission;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.progression = progression;
		this.users = users;
	}





	public Mission(String name, String description, String lieuMission, Date dateDebut, Date dateFin) {
		super();
		this.name = name;
		this.description = description;
		this.lieuMission = lieuMission;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		if(this.dateDebut.after(new Date())) {
			
			this.progression = Progress.EN_ATTENTE;
			
		}
		else {
			
			this.progression = Progress.EN_COURS;
			
		}
		
	}





	public Mission(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	
	

	public int getIdManager() {
		return idManager;
	}





	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	  
  public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getLieuMission() {
		return lieuMission;
	}

	public void setLieuMission(String lieuMission) {
		this.lieuMission = lieuMission;
	}

	public Progress getProgression() {
		return progression;
	}

	public void setProgression(Progress progression) {
		this.progression = progression;
	}
	
		
    
	public Date getDateDebut() {
			return dateDebut;
		}
	
	
	
	

	
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}



	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	public Date getDateFin() {
		return dateFin;
	}




	




	public String getPaysMission() {
		return paysMission;
	}





	public void setPaysMission(String paysMission) {
		this.paysMission = paysMission;
	}





	@Override
	public String toString() {
		return "Mission [id=" + id + ", name=" + name + ", description=" + description + ", lieuMission=" + lieuMission
				+ ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", progression="
				+ progression + ", users=" + users + "]";
	}




}

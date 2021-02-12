package esprit.tn.backend_server.Entities;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class AutreDemande implements Serializable {

	private static final long serialVersionUID = 8516885669235948942L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String raison;
	
	private int idMission; 
	
	private int idManager;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private EtatDemande etatDemande;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private TypeDemande typeDemande;
	
	@Temporal(TemporalType.TIMESTAMP)
     private Calendar dateEnvoie;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Calendar dateValidation;

	
	@ManyToOne
	private User user;

	
	public AutreDemande() {
		super();
		
	}
	
	
	public AutreDemande(int id, String raison, EtatDemande etatDemande, TypeDemande typeDmande, Calendar dateEnvoie,
			User user) {
		super();
		this.id = id;
		this.raison = raison;
		this.etatDemande = etatDemande;
		this.typeDemande = typeDmande;
		this.dateEnvoie = dateEnvoie;
		this.user = user;
	}




	public AutreDemande(String raison, EtatDemande etatDemande, TypeDemande typeDmande, Calendar dateEnvoie,
			User user) {
		super();
		this.raison = raison;
		this.etatDemande = etatDemande;
		this.typeDemande = typeDmande;
		this.dateEnvoie = dateEnvoie;
		this.user = user;
	}
	

	public AutreDemande(String raison, EtatDemande etatDemande, TypeDemande typeDmande) {
		super();
		this.raison = raison;
		this.etatDemande = etatDemande;
		this.typeDemande = typeDmande;
		this.dateEnvoie = Calendar.getInstance();
		
	}
	
	


	public Calendar getDateValidation() {
		return dateValidation;
	}


	public void setDateValidation(Calendar dateValidation) {
		this.dateValidation = dateValidation;
	}

	
	

	public int getIdMission() {
		return idMission;
	}


	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public EtatDemande getEtatDemande() {
		return etatDemande;
	}

	public void setEtatDemande(EtatDemande etatDemande) {
		this.etatDemande = etatDemande;
	}

	public TypeDemande getTypeDemande() {
		return typeDemande;
	}

	public void setTypeDemande(TypeDemande typeDmande) {
		this.typeDemande = typeDmande;
	}


	public Calendar getDateEnvoie() {
		return dateEnvoie;
	}


	public void setDateEnvoie(Calendar dateEnvoie) {
		this.dateEnvoie = dateEnvoie;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getIdManager() {
		return idManager;
	}


	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}

	

	
	
	

}

package esprit.tn.backend_server.Entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class User implements Serializable{
	
	private static final long serialVersionUID = -1396669830860400871L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String prenom;
	
	private String nom;
	
	private boolean isActif;
	
	private String idLogin;
	
	private String mdpLogin;
	
	private String tel; 
	 
	private String adresse;
	
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Etat etat;
	
	
	@Column(unique=true)
	private String email;
	
	@ManyToOne
	private Mission mission;
	
	@OneToMany(mappedBy="user")
	private List<DemandeRemboursement> demandeRemboursement;
	
	@OneToMany(mappedBy="user")
	private List<AutreDemande> autreDemande;
	
	
	
	
	public User() {
		super();
	}
	
	
	

	public User(String prenom, String nom, String mdpLogin, String tel, String adresse, String email, Role role) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.mdpLogin = mdpLogin;
		this.tel = tel;
		this.adresse = adresse;
		this.email = email;
		this.isActif = true;
		this.etat = Etat.DISPONIBLE;
		this.idLogin=email;
		this.role = role; 
	}




	public User(int id, String prenom, String nom, boolean isActif, String idLogin, String mdpLogin, String tel,
			String adresse, Role role, Etat etat, String email, Mission mission) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.isActif = isActif;
		this.idLogin = idLogin;
		this.mdpLogin = mdpLogin;
		this.tel = tel;
		this.adresse = adresse;
		this.role = role;
		this.etat = etat;
		this.email = email;
		this.mission = mission;
	}


	public User(String prenom, String nom, String tel, String adresse) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.tel = tel;
		this.adresse = adresse;
	}
	
	
	public User(String prenom, String nom, boolean isActif, String idLogin, String mdpLogin, String tel, String adresse,
			Role role, Etat etat, String email) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.isActif = isActif;
		this.idLogin = idLogin;
		this.mdpLogin = mdpLogin;
		this.tel = tel;
		this.adresse = adresse;
		this.role = role;
		this.etat = etat;
		this.email = email;
	}




	public User(int id, String prenom, String nom, Role role, Etat etat, String email) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.role = role;
		this.etat = etat;
		this.email = email;
	}





	public int getId() {
		return id;
	}

	public Mission getMission() {
		return mission;
	}




	public void setMission(Mission mission) {
		this.mission = mission;
	}




	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActif() {
		return isActif;
	}

	public void setActif(boolean isActif) {
		this.isActif = isActif;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	
	public String getIdLogin() {
		return idLogin;
	}


	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}


	public String getMdpLogin() {
		return mdpLogin;
	}


	public void setMdpLogin(String mdpLogin) {
		this.mdpLogin = mdpLogin;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	

	public List<DemandeRemboursement> getDemandeRemboursement() {
		return demandeRemboursement;
	}

	public void setDemandeRemboursement(List<DemandeRemboursement> demandeRemboursement) {
		this.demandeRemboursement = demandeRemboursement;
	}

	public List<AutreDemande> getAutreDemande() {
		return autreDemande;
	}

	public void setAutreDemande(List<AutreDemande> autreDemande) {
		this.autreDemande = autreDemande;
	}




	@Override
	public String toString() {
		return "User [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", isActif=" + isActif + ", idLogin="
				+ idLogin + ", mdpLogin=" + mdpLogin + ", tel=" + tel + ", adresse=" + adresse + ", role=" + role
				+ ", etat=" + etat + ", email=" + email + ", mission=" + mission + ", demandeRemboursement="
				+ demandeRemboursement + ", autreDemande=" + autreDemande + "]";
	}

	

}

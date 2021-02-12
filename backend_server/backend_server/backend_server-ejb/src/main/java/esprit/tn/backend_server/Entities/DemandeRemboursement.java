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

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class DemandeRemboursement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1119888419665468439L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String raison;
	
	private int idFinancier; 
	
	private int idMission; 
	
	private int montant;
	
	private String currency;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private EtatDemande etatDemande;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private TypeFrais typeFrais;

	@Temporal(TemporalType.TIMESTAMP)
    private Calendar dateEnvoie;
	

	@Temporal(TemporalType.TIMESTAMP)
    private Calendar dateValidation;


	@ManyToOne
	private User user;
	
	

	
	public DemandeRemboursement() {
		super();
	}

	public DemandeRemboursement(int id, String raison, int idFinancier, EtatDemande etatDemande, TypeFrais typeFrais, User user) {
		super();
		this.id = id;
		this.raison = raison;
		this.idFinancier = idFinancier;
		this.etatDemande = etatDemande;
		this.typeFrais = typeFrais;
		this.dateEnvoie = Calendar.getInstance();
		this.user = user;
		
	}

	public DemandeRemboursement(String raison, EtatDemande etat, TypeFrais typeFrais) {
		// TODO Auto-generated constructor stub
		
		this.raison=raison;
		this.etatDemande = etat;
		this.typeFrais = typeFrais;
		this.dateEnvoie = Calendar.getInstance();
		
	}


	public DemandeRemboursement(String raison, int montant, EtatDemande etatDemande, TypeFrais typeFrais) {
		super();
		this.raison = raison;
		this.montant = montant;
		this.etatDemande = etatDemande;
		this.typeFrais = typeFrais;
		this.dateEnvoie = Calendar.getInstance();
	}

	
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getIdMission() {
		return idMission;
	}


	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}

	
	public Calendar getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Calendar dateValidation) {
		this.dateValidation = dateValidation;
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

	public int getIdFinancier() {
		return idFinancier;
	}

	public void setIdFinancier(int idFinancier) {
		this.idFinancier = idFinancier;
	}

	public EtatDemande getEtatDemande() {
		return etatDemande;
	}

	public void setEtatDemande(EtatDemande etatDemande) {
		this.etatDemande = etatDemande;
	}

	public TypeFrais getTypeFrais() {
		return typeFrais;
	}

	public void setTypeFrais(TypeFrais typeFrais) {
		this.typeFrais = typeFrais;
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

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}


	

	
	
}

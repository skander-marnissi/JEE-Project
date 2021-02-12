package tn.esprit.backend_server.managedBeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.neovisionaries.i18n.CountryCode;


import esprit.tn.backend_server.Entities.Etat;
import esprit.tn.backend_server.Entities.Progress;
import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.TypeDemande;
import esprit.tn.backend_server.Entities.TypeFrais;

@ManagedBean
@ApplicationScoped
public class Data {

	public Role[] getRoles() {
		return Role.values();
	}
	
	public Etat[] getEtats() {
		return Etat.values();
	}
	
	public Progress[] getProgress() {
		return Progress.values();
	}
	
	public TypeDemande[] getTypeDemandes() {
		return TypeDemande.values();
	}
	
	public TypeFrais[] getTypeFrais() {
		return TypeFrais.values();
	}
	
	public List<String> getCountryCodes() {
		
		
		
		List<String> countryNames = new ArrayList<String>(CountryCode.values().length);
		
		for(CountryCode c : CountryCode.values()) {
			countryNames.add(c.getName());
			
		}
		
		countryNames.remove(0);
		Collections.sort(countryNames);
		return countryNames;
	}
	
	
	
	
}
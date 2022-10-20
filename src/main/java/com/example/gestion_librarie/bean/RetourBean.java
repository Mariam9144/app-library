package com.example.gestion_librarie.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.gestion_librarie.model.Retour;
import com.example.gestion_librarie.model.Retour;
import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.repository.RetourRepository;
import com.example.gestion_librarie.repository.RetourRepository;
import com.example.gestion_librarie.service.RetourService;
import com.example.gestion_librarie.service.RetourService;
import com.example.gestion_librarie.service.LivreService;

import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data
public class RetourBean {
	private List<Retour> retours;

	private Retour selectedRetour;

	private List<Retour> selectedRetours;
	
	/*private List<Membre> membres;

	private MembreService membreService;*/

	private List<Livre> livres;
	@Autowired
	private LivreService livreService;

	@Autowired
	private RetourService retourService;

	@Autowired
	private RetourRepository retourRepository;

	@PostConstruct
	public void init() {
		this.retours = this.retourService.getRetourRepository().findAll();
	}

	public List<Retour> getRetours() {
		return retours;
	}

	public Retour getSelectedRetour() {
		return selectedRetour;
	}

	public void setSelectedRetour(Retour selectedRetour) {
		this.selectedRetour = selectedRetour;
	}

	public List<Retour> getSelectedRetours() {
		return selectedRetours;
	}

	public void setSelectedRetours(List<Retour> selectedRetours) {
		this.selectedRetours = selectedRetours;
	}

	public void openNew() {
		this.selectedRetour = new Retour();
	}

	public void saveRetour() {
		if (this.selectedRetour.getId() == null) {
			// this.selectedRetour.setCode(UUID.randomUUID().toString().replaceAll("-",
			// "").substring(0, 9));
			// this.retours.add(this.selectedRetour);

			//System.out.println("Membre "+selectedRetour);
			Livre livre=new Livre();
			livre.setId(selectedRetour.getLivre().getId());
			livre.setCouverture(selectedRetour.getLivre().getCouverture());
			livre.setNom(selectedRetour.getLivre().getNom());
			livre.setDate_publication(selectedRetour.getLivre().getDate_publication());
			livre.setDescription(selectedRetour.getLivre().getDescription());
			livre.setLangue(selectedRetour.getLivre().getLangue());
			int nbrRest=selectedRetour.getLivre().getExemplaire()+1;
			if(nbrRest>0)
                livre.setExemplaire(nbrRest);
			System.out.println("*********************Livre ON Selectb items******************** "+livre);

			livreService.saveLivre(livre);
			System.out.println("*********************Livre******************** "+livre);

			retourService.saveRetour(selectedRetour);
	
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Retour ajouté"));
		} else {
			retourService.saveRetour(selectedRetour);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le Retour est mis a jour"));
		}
		retours = retourService.getRetours();
		PrimeFaces.current().executeScript("PF('manageRetourDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-retours");
		
		
	}

	public void deleteRetour() {
		// this.retours.remove(this.selectedRetour);
		// retours = retourService.getRetourRepository().findAll();
		this.retourService.deleteRetour(selectedRetour.getId());
		// recharge
		retours = retourService.getRetourRepository().findAll();
		this.selectedRetour = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Retour supprimé"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-retours");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedRetours()) {
			int size = this.selectedRetours.size();
			return size > 1 ? size + " retours selected" : "1 retour selected";
		}

		return "Delete";
	}

	public boolean hasSelectedRetours() {
		return this.selectedRetours != null && !this.selectedRetours.isEmpty();
	}

	public void deleteSelectedRetours() {
		this.retours.removeAll(this.selectedRetours);
		this.retourRepository.deleteAll(this.selectedRetours);
		this.selectedRetours = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Retours Removed"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-retours");
		PrimeFaces.current().executeScript("PF('dtRetours').clearFilters()");
	}
}

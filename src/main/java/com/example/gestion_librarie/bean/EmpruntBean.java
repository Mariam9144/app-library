package com.example.gestion_librarie.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.gestion_librarie.model.Emprunt;
import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.repository.EmpruntRepository;
import com.example.gestion_librarie.service.EmpruntService;
import com.example.gestion_librarie.service.LivreService;


import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data
public class EmpruntBean {

	private List<Emprunt> emprunts;

	private Emprunt selectedEmprunt;

	private List<Emprunt> selectedEmprunts;


	/*private List<Membre> membres;

        private MembreService membreService;*/
	private List<Integer> selectedLivres;

	private List<Livre> livres;
	@Autowired
	private LivreService livreService;

	@Autowired
	private EmpruntService empruntService;

	/*@Autowired
	private EmpruntRepository empruntRepository;*/
	public void onItemUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage();
		msg.setSummary("Item unselected: " + event.getObject().toString());
		msg.setSeverity(FacesMessage.SEVERITY_INFO);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@PostConstruct
	public void init() {
		this.emprunts = this.empruntService.getEmpruntRepository().findAll();
	}

	public List<Emprunt> getEmprunts() {
		return emprunts;
	}

	public Emprunt getSelectedEmprunt() {
		return selectedEmprunt;
	}

	public void setSelectedEmprunt(Emprunt selectedEmprunt) {
		this.selectedEmprunt = selectedEmprunt;
	}

	public List<Emprunt> getSelectedEmprunts() {
		return selectedEmprunts;
	}

	public void setSelectedEmprunts(List<Emprunt> selectedEmprunts) {
		this.selectedEmprunts = selectedEmprunts;
	}

	public void openNew() {
		this.selectedEmprunt = new Emprunt();
	}

	public void saveEmprunt() {
		/*if (this.selectedEmprunt.getId() == null) {
			//System.out.println("LISTE ID LIVRE "+ Arrays.stream(selectedLivres).iterator());
			// this.selectedEmprunt.setCode(UUID.randomUUID().toString().replaceAll("-",
			// "").substring(0, 9));
			// this.emprunts.add(this.selectedEmprunt);
			selectedLivres.forEach(select->{
				System.out.println("selectedLivres "+select);
				Livre livre=new Livre();
				livre=livreService.getLivreRepository().findById(select).get();

				int nbrRest=livre.getExemplaire()-1;
				if(nbrRest>0)
					livre.setExemplaire(nbrRest);
				//System.out.println("*********************Livre ON Selectb items******************** "+i+" "+livre);

				livreService.saveLivre(livre);
				//System.out.println("*********************Livre******************** "+i+" "+livre);
				selectedEmprunt.setLivre(new Livre());
				selectedEmprunt.setLivre(livre);
				empruntService.saveEmprunt(selectedEmprunt);

			});
			for (int i = 0;i<selectedLivres.length+1;i++){
				System.out.println("selectedLivres "+selectedLivres[i]);
				Livre livre=new Livre();
				livre=livreService.getLivreRepository().findById(selectedLivres[i]).get();

				int nbrRest=livre.getExemplaire()-1;
				if(nbrRest>0)
					livre.setExemplaire(nbrRest);
				//System.out.println("*********************Livre ON Selectb items******************** "+i+" "+livre);

				livreService.saveLivre(livre);
				//System.out.println("*********************Livre******************** "+i+" "+livre);
				selectedEmprunt.setLivre(new Livre());
				selectedEmprunt.setLivre(livre);
				empruntService.saveEmprunt(selectedEmprunt);
			}*/


	
		if (this.selectedEmprunt.getId() == null) {
			// this.selectedEmprunt.setCode(UUID.randomUUID().toString().replaceAll("-",
			// "").substring(0, 9));
			// this.emprunts.add(this.selectedEmprunt);

			//System.out.println("Membre "+selectedEmprunt);
			Livre livre=new Livre();
			livre.setId(selectedEmprunt.getLivre().getId());
			livre.setCouverture(selectedEmprunt.getLivre().getCouverture());
			livre.setNom(selectedEmprunt.getLivre().getNom());
			livre.setDate_publication(selectedEmprunt.getLivre().getDate_publication());
			livre.setDescription(selectedEmprunt.getLivre().getDescription());
			livre.setLangue(selectedEmprunt.getLivre().getLangue());
			int nbrRest=selectedEmprunt.getLivre().getExemplaire()-1;
			if(nbrRest>0)
                livre.setExemplaire(nbrRest);
			System.out.println("*********************Livre ON Selectb items******************** "+livre);

			livreService.saveLivre(livre);
			System.out.println("*********************Livre******************** "+livre);

			empruntService.saveEmprunt(selectedEmprunt);
	
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprunt ajouté"));
		} else {
			empruntService.saveEmprunt(selectedEmprunt);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le Emprunt est mis a jour"));
		}
		emprunts = empruntService.getEmprunts();
		PrimeFaces.current().executeScript("PF('manageEmpruntDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-emprunts");
		
		
	}

	public void deleteEmprunt() {
		// this.emprunts.remove(this.selectedEmprunt);
		// emprunts = empruntService.getEmpruntRepository().findAll();
		this.empruntService.deleteEmprunt(selectedEmprunt.getId());
		// recharge
		emprunts = empruntService.getEmpruntRepository().findAll();
		this.selectedEmprunt = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprunt supprimé"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-emprunts");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedEmprunts()) {
			int size = this.selectedEmprunts.size();
			return size > 1 ? size + " emprunts selected" : "1 emprunt selected";
		}

		return "Delete";
	}

	public boolean hasSelectedEmprunts() {
		return this.selectedEmprunts != null && !this.selectedEmprunts.isEmpty();
	}

	public void deleteSelectedEmprunts() {
		this.emprunts.removeAll(this.selectedEmprunts);
		//this.empruntRepository.deleteAll(this.selectedEmprunts);
		this.selectedEmprunts = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprunts supprimés"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-emprunts");
		PrimeFaces.current().executeScript("PF('dtEmprunts').clearFilters()");
	}
}

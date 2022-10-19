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

import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.model.MaisonEdition;
import com.example.gestion_librarie.repository.MaisonEditionRepository;
import com.example.gestion_librarie.service.LivreService;
import com.example.gestion_librarie.service.MaisonEditionService;


import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data

public class MaisonEditionBean {

	private List<MaisonEdition> maisonEditions;

    private MaisonEdition selectedMaisonEdition;

    private List<MaisonEdition> selectedMaisonEditions;
    
	
	private List<Livre> livres;
	private LivreService livreService;

    @Autowired
    private MaisonEditionService maisonEditionService;
    
    @Autowired
    private MaisonEditionRepository maisonEditionRepository;

    @PostConstruct
    public void init() {
        this.maisonEditions = this.maisonEditionService.getMaisonEditionRepository().findAll();
    }

    public List<MaisonEdition> getMaisonEditions() {
        return maisonEditions;
    }

    public MaisonEdition getSelectedMaisonEdition() {
        return selectedMaisonEdition;
    }

    public void setSelectedMaisonEdition(MaisonEdition selectedMaisonEdition) {
        this.selectedMaisonEdition = selectedMaisonEdition;
    }

    public List<MaisonEdition> getSelectedMaisonEditions() {
        return selectedMaisonEditions;
    }

    public void setSelectedMaisonEditions(List<MaisonEdition> selectedMaisonEditions) {
        this.selectedMaisonEditions = selectedMaisonEditions;
    }

    public void openNew() {
        this.selectedMaisonEdition = new MaisonEdition();
    }

    public void saveMaisonEdition() {
        if (this.selectedMaisonEdition.getId() == null) {
           // this.selectedMaisonEdition.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            //this.maisonEditions.add(this.selectedMaisonEdition);
        	maisonEditionService.saveMaisonEdition(selectedMaisonEdition);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MaisonEdition ajouté"));
        }
        else {
        	maisonEditionService.saveMaisonEdition(selectedMaisonEdition);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le MaisonEdition est mis a jour"));
        }
        maisonEditions = maisonEditionService.getMaisonEditions();
        PrimeFaces.current().executeScript("PF('manageMaisonEditionDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-maisonEditions");
    }

    public void deleteMaisonEdition() {
        //this.maisonEditions.remove(this.selectedMaisonEdition);
    	//maisonEditions = maisonEditionService.getMaisonEditionRepository().findAll();
    	this.maisonEditionService.deleteMaisonEdition(selectedMaisonEdition.getId());
    	//recharge
    	maisonEditions = maisonEditionService.getMaisonEditionRepository().findAll();
        this.selectedMaisonEdition = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MaisonEdition supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-maisonEditions");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMaisonEditions()) {
            int size = this.selectedMaisonEditions.size();
            return size > 1 ? size + " maisonEditions selected" : "1 maisonEdition selected";
        }

        return "Delete";
    }

    public boolean hasSelectedMaisonEditions() {
        return this.selectedMaisonEditions != null && !this.selectedMaisonEditions.isEmpty();
    }

    public void deleteSelectedMaisonEditions() {
        this.maisonEditions.removeAll(this.selectedMaisonEditions);
        this.maisonEditionRepository.deleteAll(this.selectedMaisonEditions);
        this.selectedMaisonEditions = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MaisonEditions Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-maisonEditions");
        PrimeFaces.current().executeScript("PF('dtMaisonEditions').clearFilters()");
    }


}

package com.example.gestion_librarie.model;

import java.util.Date;

import javax.persistence.*;


import lombok.Data;

@Entity
@Data

public class Emprunt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date date_debut;
	private Date date_fin;
	/*private int quantite ;*/

	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "membre_id", referencedColumnName = "id")
	private Membre membre;
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "livre_id", referencedColumnName = "id")
	private Livre livre;

	
	//modification
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Emprunt))
			return false;
		Emprunt castOther = (Emprunt) other;

		return ((this.getId() == castOther.getId())
				|| (this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());

		return result;
	}

	

}

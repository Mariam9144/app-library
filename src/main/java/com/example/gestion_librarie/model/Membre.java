package com.example.gestion_librarie.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Membre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String prenom;
	private String nom;
	private String telephone;
	private String adresse;
	private String sexe;
	private String profession;
	private Date date_naissance;
	
	//modification
		public boolean equals(Object other) {
			if ((this == other))
				return true;
			if ((other == null))
				return false;
			if (!(other instanceof Membre))
				return false;
			Membre castOther = (Membre) other;

			return ((this.getId() == castOther.getId())
					|| (this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId())));
		}

		public int hashCode() {
			int result = 17;

			result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());

			return result;
		}


}

package com.dcm.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "updatecase")
public class UpdateCase implements Comparable<UpdateCase>{
	
	@Id
	@GeneratedValue
	private int updateid;
	private String caseno;
	private String connected;
	private String cavet;
	private String lasthearing;
	private String nexthearing;
	private String status;
	private String title;
	private String court;
	
	public UpdateCase() {
		
	}
	
	public UpdateCase(int updateid, String caseno, String connected, String cavet, String lasthearing,
			String nexthearing, String status, String court) {
		super();
		this.updateid = updateid;
		this.caseno = caseno;
		this.connected = connected;
		this.cavet = cavet;
		this.lasthearing = lasthearing;
		this.nexthearing = nexthearing;
		this.status = status;
		this.court = court;
	}
	public int getUpdateid() {
		return updateid;
	}
	public void setUpdateid(int updateid) {
		this.updateid = updateid;
	}
	
	public String getCaseno() {
		return caseno;
	}
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}
	public String getConnected() {
		return connected;
	}
	public void setConnected(String connected) {
		this.connected = connected;
	}
	public String getCavet() {
		return cavet;
	}
	public void setCavet(String cavet) {
		this.cavet = cavet;
	}
	public String getLasthearing() {
		return lasthearing;
	}
	public void setLasthearing(String lasthearing) {
		this.lasthearing = lasthearing;
	}
	public String getNexthearing() {
		return nexthearing;
	}
	public void setNexthearing(String nexthearing) {
		this.nexthearing = nexthearing;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

       @Override
       public int compareTo(UpdateCase o) {
               if (o == null) {
                       return 1;
               }
               return Integer.compare(this.updateid, o.updateid);
       }

}

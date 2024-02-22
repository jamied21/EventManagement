package com.example.EventManagement.Models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role implements GrantedAuthority {
	@Id
	@SequenceGenerator(name = "ROLE_ID_GEN", sequenceName = "Role_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_ID_GEN")
	private Integer roleId;

	private String authority;

	public Role() {

	}

	public Role(String authority) {

		this.authority = authority;
	}

	public Role(Integer roleId, String authority) {
		super();
		this.roleId = roleId;
		this.authority = authority;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String getAuthority() {

		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}

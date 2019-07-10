package com.pzg.code.login.vo;


import com.pzg.code.login.entity.Authority;
import com.pzg.code.login.entity.Organization;
import com.pzg.code.login.entity.Role;
import com.pzg.code.login.entity.User;

import java.util.List;


public class UserVo {
    private User user;
    private Organization organization;
    private Role role;
    private String tooken;
    private List<Authority> authorityList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }
}

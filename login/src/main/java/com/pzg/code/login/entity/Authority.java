package com.pzg.code.login.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Authority implements Serializable {
    @Id
    @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authId;

    @Column(name = "au_name")
    private String auName;

    private String path;

    private static final long serialVersionUID = 1L;

    /**
     * @return auth_id
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * @param authId
     */
    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    /**
     * @return au_name
     */
    public String getAuName() {
        return auName;
    }

    /**
     * @param auName
     */
    public void setAuName(String auName) {
        this.auName = auName == null ? null : auName.trim();
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", authId=").append(authId);
        sb.append(", auName=").append(auName);
        sb.append(", path=").append(path);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
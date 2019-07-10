package com.pzg.code.login.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Organization implements Serializable {
    @Id
    @Column(name = "og_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ogId;

    @Column(name = "og_name")
    private String ogName;

    @Column(name = "parent_id")
    private String parentId;

    private static final long serialVersionUID = 1L;

    /**
     * @return og_id
     */
    public String getOgId() {
        return ogId;
    }

    /**
     * @param ogId
     */
    public void setOgId(String ogId) {
        this.ogId = ogId == null ? null : ogId.trim();
    }

    /**
     * @return og_name
     */
    public String getOgName() {
        return ogName;
    }

    /**
     * @param ogName
     */
    public void setOgName(String ogName) {
        this.ogName = ogName == null ? null : ogName.trim();
    }

    /**
     * @return parent_id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ogId=").append(ogId);
        sb.append(", ogName=").append(ogName);
        sb.append(", parentId=").append(parentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
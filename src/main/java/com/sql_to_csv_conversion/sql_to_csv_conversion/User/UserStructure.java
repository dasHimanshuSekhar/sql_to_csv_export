package com.sql_to_csv_conversion.sql_to_csv_conversion.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserStructure {
    @Id
    private long id;
    private String name;
    private String email_id;

    public UserStructure(){}

    public UserStructure(long id, String name, String email_id) {
        this.id = id;
        this.name = name;
        this.email_id = email_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}

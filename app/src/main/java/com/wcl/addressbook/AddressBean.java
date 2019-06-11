package com.wcl.addressbook;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "addressBook")
public class AddressBean {

    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String company;
    private String phone_num;
    private String email;
    @Generated(hash = 986815419)
    public AddressBean(Long id, String name, String company, String phone_num,
            String email) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.phone_num = phone_num;
        this.email = email;
    }
    @Generated(hash = 30780671)
    public AddressBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getPhone_num() {
        return this.phone_num;
    }
    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



}

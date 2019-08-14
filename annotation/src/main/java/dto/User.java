package dto;

import anno.*;

@MyEntity
@MyTable(name="t_user")
public class User {


    private Long id;
    private String name;
    private String age;
    private String address;
    private String password;

    @MyId
    @MyGeneratedValue(strategy = "identity")
    @MyColumn("t_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @MyColumn(value = "t_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MyColumn(value = "t_age")
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @MyColumn(value = "t_address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @MyColumn(value = "t_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

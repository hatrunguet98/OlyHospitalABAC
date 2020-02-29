package UET.model.DTO;

public class PatientRecordDTO {
    private Integer id;
    private String name;
    private Integer age;
    private String numberPhone;
    private String address;
    private String Sick;
    private String sickStatus;
    private Integer idDoctor;
    private Integer idPatient;

    public PatientRecordDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSick() {
        return Sick;
    }

    public void setSick(String sick) {
        Sick = sick;
    }

    public String getSickStatus() {
        return sickStatus;
    }

    public void setSickStatus(String sickStatus) {
        this.sickStatus = sickStatus;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }
}

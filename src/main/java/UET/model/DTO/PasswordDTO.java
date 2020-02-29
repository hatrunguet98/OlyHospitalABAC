package UET.model.DTO;

public class PasswordDTO {
    private String email;
    private String token;
    private String oldPassword;
    private String newPassword;
    private String confirmonPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmonPassword() {
        return confirmonPassword;
    }

    public void setConfirmonPassword(String confirmonPassword) {
        this.confirmonPassword = confirmonPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

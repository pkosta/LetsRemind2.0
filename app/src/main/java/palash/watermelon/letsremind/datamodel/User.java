package palash.watermelon.letsremind.datamodel;

/*
 * Created by 849501 on 10/25/2017.
 */

public class User {

    private String username;

    private String email;

    private String photoUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String photoUrl) {
        this.username = username;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

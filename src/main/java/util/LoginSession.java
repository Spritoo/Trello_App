package util;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import model.User;

@Named
@Stateful
public class LoginSession implements Serializable{
	
	private static final long serialVersionUID = 1L;

    private User user;

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
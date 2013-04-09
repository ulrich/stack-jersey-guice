package fr.mappy.service.user;

import fr.mappy.model.User;

import java.util.Collection;

public interface UserService {

    /**
     * Cette méthode renvoie un objet User ou null si non trouvé.
     *
     * @param userName le nom...
     * @return ...
     */
    Collection<User> searchUsersByName(String userName);

    // retourne le flux JSON d'un utilisateur sur github.com
    String getUserAsJSON(String userName);
}

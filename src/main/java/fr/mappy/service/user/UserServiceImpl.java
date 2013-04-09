package fr.mappy.service.user;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import fr.mappy.model.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang.StringUtils.isBlank;

public class UserServiceImpl implements UserService {

    public static final String API_GITHUB_USER_SEARCH = "https://api.github.com/legacy/user/search/";

    @Override
    public Collection<User> searchUsersByName(final String userName) {
        String userAsJSON = getUserAsJSON(userName);

        if (isBlank(userAsJSON)) {
            return null;
        }
        Users users = convertJSONToUsers(userAsJSON);

        if (null == users) {
            return Lists.newArrayList();
        }
        return users.getUsers();
    }

    // convertir le flux JSON retourné par le provider en objet métier
    public Users convertJSONToUsers(String usersAsJSON) {
        Users users = null;

        Gson gson = new Gson();
        try {
            users = gson.fromJson(usersAsJSON, Users.class);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to build User object from JSON.", e);
        }
        return users;
    }

    // retourne le flux JSON d'un utilisateur sur github.com
    @Override
    public String getUserAsJSON(String userName) {
        String result = null;

        HttpGet get = new HttpGet(API_GITHUB_USER_SEARCH + userName);

        BufferedReader reader = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpResponse httpResponse = httpclient.execute(get);

            InputStreamReader inputStreamReader = new InputStreamReader(httpResponse.getEntity().getContent());

            reader = new BufferedReader(inputStreamReader);

            result = reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get JSon document from Github.", e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return result;
    }

    class Users {
        Collection<User> users = new ArrayList<User>();

        public Users() {

        }

        public Collection<User> getUsers() {
            return users;
        }

        public void setUsers(Collection<User> users) {
            this.users = users;
        }

    }
}

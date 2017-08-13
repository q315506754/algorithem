package com.jiangli.http;

import feign.*;
import feign.gson.GsonDecoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class FeignService {
  public static final String API_URL = "https://api.github.com";

//    @Headers("Accept: application/json")
    public interface GitHub {
//        @Headers("Content-Type: application/json")
//        @Body("%7B\"user_name\": \"{user_name}\", \"password\": \"{password}\"%7D")
//@Body("<login \"user_name\"=\"{user_name}\" \"password\"=\"{password}\"/>")
//@Headers("X-Ping: {token}")
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

        @RequestLine("POST /")
        void post(@HeaderMap Map<String, Object> headerMap,@Param(value = "date", expander = DateToMillis.class) Date date,@QueryMap Map<String, Object> queryMap);

        @RequestLine("GET /users/{username}/repos?sort={sort}")
        List<Repo> repos(@Param("username") String owner, @Param("sort") String sort);

        default List<Repo> repos(String owner) {
            return repos(owner, "full_name");
        }

        /**
         * Lists all contributors for all repos owned by a user.
         */
        default List<Contributor> contributors(String user) {
            List<Contributor> contributors = new LinkedList<>();
            for(Repo repo : this.repos(user)) {
                contributors.addAll(this.contributors(user, user));
            }
            return contributors;
        }

        static GitHub connect() {
            return Feign.builder()
                    .decoder(new GsonDecoder())
                    .target(GitHub.class, "https://api.github.com");
        }
    }
    public interface GitHubJAXRS {
        @GET
        @Path("/repos/{owner}/{repo}/contributors")
        List<Contributor> contributorsJAXRS(@PathParam("owner") String owner, @PathParam("repo") String repo);
    }

    public static class Contributor {
        String login;
        int contributions;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getContributions() {
            return contributions;
        }

        public void setContributions(int contributions) {
            this.contributions = contributions;
        }
    }

    public static void main(String... args) {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");

        // Fetch and print a list of the contributors to this library.
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
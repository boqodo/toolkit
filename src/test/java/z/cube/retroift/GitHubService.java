package java.z.cube.retroift;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
  @GET("/repos/{owner}/{repo}/contributors")
  List<Contributor> repoContributors(
          @Path("owner") String owner,
          @Path("repo") String repo);
  
  
  @GET("users/{user}/repos")
  Observable<List<Repo>> listReposRx(@Path("user") String user);
  @GET("users")
  Observable<List<User>> listUsersRx();
}
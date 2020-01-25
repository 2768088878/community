package lift.majiang.community.provider;
import com.alibaba.fastjson.JSON;
import lift.majiang.community.entity.AccessEntity;
import lift.majiang.community.entity.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccess(AccessEntity accessEntity){
        MediaType mediaType  = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessEntity));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token="+token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }


    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
        return  githubUser;
    }

}

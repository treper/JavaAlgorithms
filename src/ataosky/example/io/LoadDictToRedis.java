package ataosky.example.io;

/**
 * Created by tangning on 14-6-16.
 */
import java.io.*;
import java.nio.Buffer;

import com.google.common.base.Preconditions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class LoadDictToRedis {
    public static void main(String[] args) throws IOException {
        Preconditions.checkArgument(args.length==2,"usage:exe label_dict_path");

        String dictPath = args[0];
        String redisServer = args[1];

        System.out.println("load label dictionary to redis");

        Jedis jedis = new Jedis(redisServer);
        jedis.select(0);//select the 0 db
        FileReader fr = new FileReader(new File(dictPath));
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while((line = br.readLine())!=null)
        {
            //Integer tagId = Integer.parseInt(line.split("\t")[0]);
            String tagId = line.split("\t")[0];
            String tag = line.split("\t")[1];
            jedis.set(tagId,tag);
        }
        br.close();

        System.out.println("test 1205 "+ jedis.get("1205"));
        jedis.close();
        System.out.println("complete loading label dictionary");

    }
}

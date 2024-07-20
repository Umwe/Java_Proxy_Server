import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class GeoIPService {
    @Value("${geolite2.db.location}")
    private String dbLocation;
    private DatabaseReader dbReader;

    @PostConstruct
    public void init() throws IOException {
        dbReader = new DatabaseReader.Builder(new ClassPathResource(dbLocation).getFile()).build();
    }

    public CityResponse getCityResponse(String ip) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        return dbReader.city(ipAddress);
    }
}

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ProxyController {

    @Value("${proxy.target.url}")
    private String targetUrl;

    private final RestTemplate restTemplate;
    private final GeoIPService geoIPService;

    public ProxyController(RestTemplate restTemplate, GeoIPService geoIPService) {
        this.restTemplate = restTemplate;
        this.geoIPService = geoIPService;
    }

    @RequestMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = request.getRemoteAddr();
        if (!isValidGeographicalLocation(ip)) {
            return ResponseEntity.status(403).body("Access Forbidden: Invalid Geographical Location");
        }

        String url = targetUrl + request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        ResponseEntity<String> response = restTemplate.exchange(url, method, null, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    private boolean isValidGeographicalLocation(String ip) throws IOException, GeoIp2Exception {
        String allowedCountry = "US";
        return geoIPService.getCityResponse(ip).getCountry().getIsoCode().equals(allowedCountry);
    }
}

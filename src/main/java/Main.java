import DTO.UserDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class Main {
    private static final String url = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String sessionId = responseEntity.getHeaders().get("set-cookie").get(0);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, sessionId);

        UserDTO userDTO = new UserDTO(3L, "James", "Brown", 73);

        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);


        userDTO.setName("Thomas");
        userDTO.setLastName("Shelby");
        HttpEntity<UserDTO> update = new HttpEntity<>(userDTO, httpHeaders);
        ResponseEntity<String> responsePut = restTemplate.exchange(url, HttpMethod.PUT, update, String.class);

        ResponseEntity<String> delete = restTemplate.exchange(url + "/3", HttpMethod.DELETE, request, String.class);
        String text = response.getBody() + responsePut.getBody() + delete.getBody();
        System.out.println(text);

    }
}

package wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * Testes com Wiremock simulando um servico externo que esta API consome
 * @author j.alves.de.barros
 */
public class WiremockTest {

	private static final String WIREMOCK = "src/test/resources/wiremock/profiles";
	private static final Integer PORT = 8081;
	private static final String HOST = "http://localhost";
	private static final String PATH = "/externalService/profile/";
	private static final Integer ID_ADMIN = 1;
	private static final Integer ID_NOT_FOUND = 999;
	
	private WireMockServer wireMockServer;

	@Before
	public void setUp() {
		this.wireMockServer = new WireMockServer(options().port(PORT).withRootDirectory(WIREMOCK));
		this.wireMockServer.start();
		configureFor(HOST, this.wireMockServer.port());
	}
	
	@After
	public void finish() {
		this.wireMockServer.stop();
	}

	@Test
	public void getAdminProfile() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(HOST+":"+ PORT + PATH + ID_ADMIN);

		request.addHeader("accept", "application/json");
		
		HttpResponse response = client.execute(request);
		assertTrue(response.getStatusLine().getStatusCode() == 200);
		
		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONObject body = new JSONObject(json);
		assertTrue(body.get("nome").equals("Administrador"));
	}
	
	@Test
	public void getProfileNotFound() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(HOST+":"+ PORT + PATH + ID_NOT_FOUND);

		request.addHeader("accept", "application/json");
		
		HttpResponse response = client.execute(request);
		assertTrue(response.getStatusLine().getStatusCode() == 404);
	}
	
	@Test
	public void getAllProfiles() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(HOST+":"+ PORT + PATH);
		request.addHeader("accept", "application/json");
		
		HttpResponse response = client.execute(request);
		assertTrue(response.getStatusLine().getStatusCode() == 200);
		
		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONArray jsonarray = new JSONArray(json);
		assertTrue(jsonarray.length() > 0);
	}
	
	@Test
	public void getAllProfilesByPriority() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(HOST+":"+ PORT + PATH + "?priority=2");
		request.addHeader("accept", "application/json");
		
		HttpResponse response = client.execute(request);
		assertTrue(response.getStatusLine().getStatusCode() == 200);
		
		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONArray jsonarray = new JSONArray(json);
		
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
		    Integer idPriority = (Integer) jsonobject.get("prioridade");
		    assertTrue(idPriority==2);
		}
	}
	
	@Test
	public void getProfileBadRequest() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(HOST+":"+ PORT + PATH + "abc");
		request.addHeader("accept", "application/json");
		
		HttpResponse response = client.execute(request);
		assertTrue(response.getStatusLine().getStatusCode() == 400);
	}

}

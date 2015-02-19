package de.hdm.ast.sites;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.util.AuthenticationException;

public class Authorization {

	private static final String API_KEY = "key.p12";

	public static SitesService getSitesService()
			throws GeneralSecurityException, IOException,
			AuthenticationException {

		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = GoogleNetHttpTransport
				.newTrustedTransport();

		SitesService sitesService = new SitesService("inductive-mind-846");

		GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(
						"1094795664207-88e383oaijjuh334qdkr7nf0dvuut32r@developer.gserviceaccount.com")
				.setServiceAccountPrivateKeyFromP12File(new File(API_KEY))
				.setServiceAccountScopes(
						Collections
								.singleton("https://sites.google.com/feeds/"))
				.build();
		sitesService.setOAuth2Credentials(credential);

		return sitesService;

	}

}

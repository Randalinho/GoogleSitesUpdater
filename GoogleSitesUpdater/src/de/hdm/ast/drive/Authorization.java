package de.hdm.ast.drive;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

public class Authorization {

	// public static AppIdentityCredential createCredential() {
	// AppIdentityCredential credential = new AppIdentityCredential(
	// Arrays.asList(DriveScopes.DRIVE));
	// return credential;
	// }

	private static final String API_KEY = "key.p12";

	public static Drive getDriveService() throws GeneralSecurityException,
			IOException, URISyntaxException {
//		HttpTransport httpTransport = new NetHttpTransport();
//		JsonFactory jsonFactory = new JacksonFactory();
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//		AppIdentityCredential credential = new AppIdentityCredential.Builder(
//				Arrays.asList(DriveScopes.DRIVE)).build();
		GoogleCredential credential = new GoogleCredential.Builder()
	    .setTransport(httpTransport)
	    .setJsonFactory(JSON_FACTORY)
	    .setServiceAccountId("1094795664207-88e383oaijjuh334qdkr7nf0dvuut32r@developer.gserviceaccount.com")
	    .setServiceAccountPrivateKeyFromP12File(new File(API_KEY))
	    .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
	    .build();
		GoogleClientRequestInitializer keyInitializer = new CommonGoogleClientRequestInitializer(
				API_KEY);
		Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, null)
				.setHttpRequestInitializer(credential)
				.setGoogleClientRequestInitializer(keyInitializer)
				.setApplicationName("GoogleSitesUpdater")
				.build();
		return service;

	}

}

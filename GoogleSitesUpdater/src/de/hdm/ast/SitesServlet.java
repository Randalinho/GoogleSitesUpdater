package de.hdm.ast;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import de.hdm.ast.drive.Authorization;
import de.hdm.ast.drive.Service;

/**
 * Servlet, dass über einen Cronjob angestoßen wird.
 * 
 * @author Fabian
 *
 */
@SuppressWarnings("serial")
public class SitesServlet extends HttpServlet {

	AppIdentityCredential credentials;
	Drive drive;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		credentials = Authorization.createCredential();
//		drive = Service.buildService(credentials);
		
		try {
			drive = Authorization.getDriveService();
		} catch (GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Files.List request1 = drive.files().list();
		FileList files = request1.execute();

		if (files != null) {
			  for (File file : files.getItems()) {
			    // Meta data
			    System.out.println("Title: " + file.getTitle());
			    System.out.println("Description: " + file.getDescription());
			    System.out.println("MIME type: " + file.getMimeType());
			    System.out.println("LastModifyingUserName: " + file.getLastModifyingUserName());
			    System.out.println("ID: " + file.getId());
			  }
			}
		
		System.out.println("Liste Dateien Anzahl: " + drive.files().list().size());
		System.out.println("Parents" + drive.getRootUrl());
		System.out.print("Job wurde erfolgreich ausgeführt");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

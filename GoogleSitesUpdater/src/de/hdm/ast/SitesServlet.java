package de.hdm.ast;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.drive.Drive;

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
		
		credentials = Authorization.createCredential();
		drive = Service.buildService(credentials);
		

		System.out.println(drive.files().list().size());
		System.out.print("Job wurde erfolgreich ausgeführt");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

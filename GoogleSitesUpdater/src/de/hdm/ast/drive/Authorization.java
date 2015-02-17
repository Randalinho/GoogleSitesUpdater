package de.hdm.ast.drive;

import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.drive.DriveScopes;

public class Authorization {

	public AppIdentityCredential createCredential() {
		AppIdentityCredential credential = new AppIdentityCredential(
				DriveScopes.all());
		return credential;
	}

}

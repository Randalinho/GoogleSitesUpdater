package de.hdm.ast.drive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DriveUtil {

	AppIdentityCredential credentials;
	static Drive drive;

	public static void printAllFiles() throws IOException {
		try {
			drive = Authorization.getDriveService();
		} catch (GeneralSecurityException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Files.List listOfFiles = drive.files().list();
		FileList files = listOfFiles.execute();

		if (files != null) {
			for (File file : files.getItems()) {
				// Meta data
				System.out.println("Title: " + file.getTitle());
				System.out.println("Description: " + file.getDescription());
				System.out.println("MIME type: " + file.getMimeType());
				System.out.println("LastModifyingUserName: "
						+ file.getLastModifyingUserName());
				System.out.println("ID: " + file.getId());
				System.out.println("Download URL: " + file.getDownloadUrl());
				FileDownload.downloadFile(drive, file);
			}
		}

		System.out.println("Liste Dateien Anzahl: "
				+ drive.files().list().size());
		System.out.println("Parents" + drive.getRootUrl());

	}

}

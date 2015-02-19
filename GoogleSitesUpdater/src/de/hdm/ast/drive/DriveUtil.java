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

	public static String getMetadata() throws IOException {
		String result = "";

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
				result += "Title: " + file.getTitle() + "<br />" + "Description: "
						+ file.getDescription() + "<br />" + "MIME type: "
						+ file.getMimeType() + "<br />" + "LastModifyingUserName: "
						+ file.getLastModifyingUserName() + "<br />" + "ID: "
						+ file.getId() + "<br />" + "File Size: " + file.getFileSize() + "<br /><br />";
				System.out.println("Download URL: " + file.getDownloadUrl());
				FileDownload.downloadFile(drive, file);
			}
		}

		System.out.println("Liste Dateien Anzahl: "
				+ drive.files().list().size());
		
		return result;

	}

}

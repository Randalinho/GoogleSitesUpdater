package de.hdm.ast.sites;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.gdata.client.sites.SitesService;
import com.google.gdata.util.AuthenticationException;

public class Authorization {

	private static final String API_KEY = "key.p12";

	public static SitesService getSitesService()
			throws GeneralSecurityException, IOException,
			AuthenticationException {


		SitesService sitesService = new SitesService("inductive-mind-846");

		sitesService.setUserCredentials("fabianroeber@prestige-worldwide.in",
				"schmieder1234");

		return sitesService;

	}

}

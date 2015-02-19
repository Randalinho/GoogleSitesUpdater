package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import org.junit.Test;

import com.google.gdata.client.sites.SitesService;
import com.google.gdata.data.Category;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.sites.SiteEntry;
import com.google.gdata.data.sites.SiteFeed;
import com.google.gdata.data.sites.TagCategory;
import com.google.gdata.data.sites.Theme;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import de.hdm.ast.sites.Authorization;

public class SitesTest {

	SitesService service = new SitesService("TEST");

	@Test
	public void test() throws IOException, ServiceException, GeneralSecurityException {

		service.setUserCredentials("fabianroeber@prestige-worldwide.in",
				"schmieder1234");
		//SiteEntry newSiteEntry = createSite("title", "summary for site", "slate", "tag");
		getSiteFeed();

	}

	public String getSiteFeedUrl() {

		 String domain = "prestige-worldwide.in";  // OR if the Site is hosted on Google Apps, your domain (e.g. example.com)
		  return "https://sites.google.com/feeds/site/" + domain + "/";
	}

	public void getSiteFeed() throws IOException, ServiceException,
			GeneralSecurityException {

		SiteFeed siteFeed = service.getFeed(new URL(getSiteFeedUrl()),
				SiteFeed.class);
		

		for (SiteEntry entry : siteFeed.getEntries()) {
			System.out.println("Feed erfolgreich geladen");
			System.out.println("title: " + entry.getTitle().getPlainText());
			System.out.println("site name: " + entry.getSiteName().getValue());
			System.out.println("theme: " + entry.getTheme().getValue());
			System.out.println("");
		}
	}
	
	public SiteEntry createSite(String title, String summary, String theme,
			String tag) throws MalformedURLException, IOException,
			ServiceException, GeneralSecurityException {
		
//		SitesService client = Authorization.getSitesService();
		
		SiteEntry entry = new SiteEntry();
		entry.setTitle(new PlainTextConstruct(title));
		entry.setSummary(new PlainTextConstruct(summary));

		Theme tt = new Theme();
		tt.setValue(theme);
		entry.setTheme(tt);

		entry.getCategories().add(
				new Category(TagCategory.Scheme.TAG, tag, null));

		return service.insert(new URL(getSiteFeedUrl()), entry);
	}

}

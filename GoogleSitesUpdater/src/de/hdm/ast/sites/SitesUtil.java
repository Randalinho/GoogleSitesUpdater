package de.hdm.ast.sites;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.activation.MimetypesFileTypeMap;

import com.google.api.services.drive.model.File;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.data.Category;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.XhtmlTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.sites.AttachmentEntry;
import com.google.gdata.data.sites.BasePageEntry;
import com.google.gdata.data.sites.ContentFeed;
import com.google.gdata.data.sites.FileCabinetPageEntry;
import com.google.gdata.data.sites.ListPageEntry;
import com.google.gdata.data.sites.SiteEntry;
import com.google.gdata.data.sites.SiteFeed;
import com.google.gdata.data.sites.SitesLink;
import com.google.gdata.data.sites.TagCategory;
import com.google.gdata.data.sites.Theme;
import com.google.gdata.data.sites.WebPageEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.XmlBlob;

public class SitesUtil {

	public static String getSiteFeedUrl() {

		String domain = "prestige-worldwide.in"; // OR if the Site is hosted on
													// Google Apps, your domain
													// (e.g. example.com)
		return "https://sites.google.com/feeds/site/" + domain + "/";
	}

	public static void getSiteFeed() throws IOException, ServiceException,
			GeneralSecurityException {

		SitesService client = Authorization.getSitesService();

		SiteFeed siteFeed = client.getFeed(new URL(getSiteFeedUrl()),
				SiteFeed.class);
		System.out.println(siteFeed.getTitle());

		for (SiteEntry entry : siteFeed.getEntries()) {
			System.out.println("Feed erfolgreich geladen");
			System.out.println("title: " + entry.getTitle().getPlainText());
			System.out.println("site name: " + entry.getSiteName().getValue());
			System.out.println("theme: " + entry.getTheme().getValue());
			System.out.println("");
		}
	}

	public static ContentFeed getContentFeed() throws AuthenticationException,
			MalformedURLException, IOException, ServiceException,
			GeneralSecurityException {

		ContentFeed feed = Authorization.getSitesService().getFeed(
				new URL(buildContentFeedUrl()), ContentFeed.class);

		return feed;
	}

	public static SiteEntry createSite(String title, String summary,
			String theme, String tag) throws MalformedURLException,
			IOException, ServiceException, GeneralSecurityException {

		SitesService client = Authorization.getSitesService();

		SiteEntry entry = new SiteEntry();
		entry.setTitle(new PlainTextConstruct(title));
		entry.setSummary(new PlainTextConstruct(summary));

		Theme tt = new Theme();
		tt.setValue(theme);
		entry.setTheme(tt);

		entry.getCategories().add(
				new Category(TagCategory.Scheme.TAG, tag, null));

		return client.insert(new URL(getSiteFeedUrl()), entry);
	}

	public static MimetypesFileTypeMap getMediaTypes() {

		MimetypesFileTypeMap mediaTypes = new MimetypesFileTypeMap();
		mediaTypes.addMimeTypes("application/msword doc");
		mediaTypes.addMimeTypes("application/vnd.ms-excel xls");
		mediaTypes.addMimeTypes("application/pdf pdf");
		mediaTypes.addMimeTypes("text/richtext rtx");
		mediaTypes.addMimeTypes("text/csv csv");
		mediaTypes.addMimeTypes("text/tab-separated-values tsv tab");
		mediaTypes
				.addMimeTypes("application/x-vnd.oasis.opendocument.spreadsheet ods");
		mediaTypes.addMimeTypes("application/vnd.oasis.opendocument.text odt");
		mediaTypes.addMimeTypes("application/vnd.ms-powerpoint ppt pps pot");
		mediaTypes
				.addMimeTypes("application/vnd.openxmlformats-officedocument.wordprocessingml.document docx");
		mediaTypes
				.addMimeTypes("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet xlsx");
		mediaTypes.addMimeTypes("audio/mpeg mp3 mpeg3");
		mediaTypes.addMimeTypes("image/png png");
		mediaTypes.addMimeTypes("application/zip zip");
		mediaTypes.addMimeTypes("application/x-tar tar");
		mediaTypes.addMimeTypes("video/quicktime qt mov moov");
		mediaTypes.addMimeTypes("video/mpeg mpeg mpg mpe mpv vbs mpegv");
		mediaTypes.addMimeTypes("video/msvideo avi");

		return mediaTypes;

	}

	public static AttachmentEntry uploadAttachment(java.io.File file,
			BasePageEntry<?> parentPage, String title, String description)
			throws IOException, ServiceException, GeneralSecurityException {

		SitesService client = Authorization.getSitesService();

		MimetypesFileTypeMap mediaTypes = getMediaTypes();

		AttachmentEntry newAttachment = new AttachmentEntry();
		newAttachment.setMediaSource(new MediaFileSource(file, mediaTypes
				.getContentType(file)));
		newAttachment.setTitle(new PlainTextConstruct(title));
		newAttachment.setSummary(new PlainTextConstruct(description));
		newAttachment.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM, parentPage
				.getSelfLink().getHref());

		return client.insert(new URL(buildContentFeedUrl()), newAttachment);
	}

	public static AttachmentEntry uploadAttachment(File file)
			throws MalformedURLException, IOException, ServiceException,
			GeneralSecurityException {

		SitesService client = Authorization.getSitesService();

		ContentFeed contentFeed = client.getFeed(new URL(buildContentFeedUrl()
				+ "?kind=filecabinet"), ContentFeed.class);
		FileCabinetPageEntry parentPage = contentFeed.getEntries(
				FileCabinetPageEntry.class).get(0);

		AttachmentEntry attachment = uploadAttachment(file);

		System.out.println("Uploaded!");

		return attachment;
	}

	public static String buildContentFeedUrl() {
		String domain = "prestige-worldwide.in";
		String siteName = "title";
		return "https://sites.google.com/feeds/content/" + domain + "/"
				+ siteName + "/";
	}

	public static void updateSite(String s) throws AuthenticationException,
			MalformedURLException, IOException, ServiceException,
			GeneralSecurityException {
		ContentFeed contentFeed = Authorization.getSitesService().getFeed(
				new URL(buildContentFeedUrl() + "?kind=webpage"),
				ContentFeed.class);
		WebPageEntry entry = contentFeed.getEntries(WebPageEntry.class).get(1);

		// Update title
		entry.setTitle(new PlainTextConstruct("Updated Site"));

		// Update HTML content
		XmlBlob xml = new XmlBlob();
		xml.setBlob("<p>" + s + "<p>");
		entry.setContent(new XhtmlTextConstruct(xml));
		
		entry.update();

		System.out.println("ListPage updated!");
	}

}

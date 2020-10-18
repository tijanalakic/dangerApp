package net.etfbl.main.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.util.StringUtils;

import net.etfbl.main.dto.User;

public class Utils {

	private static final String LINK_PATTERN = "((http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?)";
	private static final String YOUTUBE_PATTERN = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
	private static final String YOUTUBE = "https://www.youtube.com/embed/";

	public static String parseMessage(String message) {

		Pattern pattern = Pattern.compile(LINK_PATTERN);
		if (!StringUtils.isNullOrEmpty(message)) {

			Matcher matcher = pattern.matcher(message);

			while (matcher.find()) {
				String link = matcher.group(1);
				String upgradedLink = "<a href=\"" + link + "\" target=\"_blank\" class=\"text-primary\">" + link
						+ "</a>";
				message = message.replace(link, upgradedLink);
			}

		}
		
		return message;
	}

	public static List<String> getLinks(String message) {

		List<String> linkList = new ArrayList<>();

		if (message != null) {
			Pattern pattern = Pattern.compile(LINK_PATTERN);
			Matcher matcher = pattern.matcher(message);

			while (matcher.find()) {

				String link = matcher.group(1);
				linkList.add(link);
			}
		}
		return linkList;
	}

	public static String getYoutubeEmbedVideo(String message) {

		String vID = null;
		Pattern pattern = Pattern.compile(YOUTUBE_PATTERN, Pattern.CASE_INSENSITIVE);

		if (message != null) {
			for (String link : getLinks(message)) {
				Matcher matcher = pattern.matcher(link);

				if (matcher.matches()) {
					vID = matcher.group(1);
					break;
				}
			}
		}
		if (vID == null) {
			return null;
		}
		return YOUTUBE + vID;
	}

	public static String saveFile(User user, String fileName, InputStream fileContent, String imagesPath, String fileLocation) throws IOException {

		File folder = new File(imagesPath);

		if (!folder.exists()) {
			folder.mkdir();
		}

		String generatedString = String.valueOf(new Random().nextInt());

		fileName = user.getUsername() + "_" + generatedString + "_" + fileName;
		String imageLocation = folder + File.separator + fileName;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(new File(imageLocation));

			byte[] bytes = new byte[2048];
			int length;

			while ((length = fileContent.read(bytes)) != -1) {
				fos.write(bytes, 0, length);
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (fileContent != null) {
				fileContent.close();
			}
		}
		return fileLocation + File.separator + fileName;
	}

}

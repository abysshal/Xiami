package info.dreamingfish123.robot.xiami;

import java.io.IOException;
import java.net.URL;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * 
 * @author hui
 * 
 */

public class XiamiLogin {

	public User user;
	public WebClient webClient;
	HtmlPage htmlPage;

	public XiamiLogin(User user) {
		this.user = user;
		webClient = new WebClient(BrowserVersion.FIREFOX_17);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(30000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getCookieManager().setCookiesEnabled(true);
	}

	public boolean getLoginPage() throws FailingHttpStatusCodeException,
			IOException {
		WebRequest request = new WebRequest(new URL(
				"https://login.xiami.com/member/login"), HttpMethod.GET);
		request.setCharset("utf-8");
		htmlPage = webClient.getPage(request);
		if (htmlPage != null) {
			System.out.println(htmlPage.getTitleText());
			return true;
		}
		return false;
	}

	public boolean login(User user) throws IOException {
		HtmlTextInput username = htmlPage.getHtmlElementById("email");
		HtmlPasswordInput password = htmlPage.getHtmlElementById("password");
		HtmlCheckBoxInput autologin = htmlPage.getHtmlElementById("autologin");
		HtmlSubmitInput submit = htmlPage.getElementByName("submit");
		if (username == null || password == null || autologin == null
				|| submit == null) {
			return false;
		}

		username.type(user.getUsername());
		password.type(user.getPassword());
		autologin.setChecked(true);
		htmlPage = submit.click();
		System.out.println(htmlPage.getTitleText());
		htmlPage = webClient.getPage("http://www.xiami.com/");
		if (htmlPage != null) {
			System.out.println(htmlPage.getTitleText());
			HtmlDivision userDiv = htmlPage.getHtmlElementById("user");
			HtmlDivision actions = userDiv.getOneHtmlElementByAttribute("div",
					"class", "action");
			HtmlElement toSign = actions.getOneHtmlElementByAttribute("b",
					"class", "icon tosign");
			htmlPage = toSign.click();
			System.out.println(htmlPage.asText());

		}
		return false;
	}

	public static void main(String[] args)
			throws FailingHttpStatusCodeException, IOException {

		User user = new User();
		user.setUsername("Your email address here");
		user.setPassword("Your password here");
		XiamiLogin xiamiLogin = new XiamiLogin(user);
		xiamiLogin.getLoginPage();
		xiamiLogin.login(user);

	}

}

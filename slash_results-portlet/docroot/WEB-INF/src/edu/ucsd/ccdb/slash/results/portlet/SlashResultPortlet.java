/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ucsd.ccdb.slash.results.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import java.io.IOException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.*;
import java.util.*;

import com.liferay.portal.service.*;
import com.liferay.portal.model.*;
/**
 *
 * @author wawong
 */
public class SlashResultPortlet  extends GenericPortlet
{

    SlashDBUtil dbutil = new SlashDBUtil();
    



public void init() throws PortletException {
		editJSP = getInitParameter("edit-jsp");
		helpJSP = getInitParameter("help-jsp");
		viewJSP = getInitParameter("view-jsp");

               SlashDBService.URL = this.getPortletConfig().getPortletContext().getInitParameter("JDBCUrl");
               SlashDBService.USERNAME = this.getPortletConfig().getPortletContext().getInitParameter("Username");
               SlashDBService.PASSWORD = this.getPortletConfig().getPortletContext().getInitParameter("Password");
	}

	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String jspPage = renderRequest.getParameter("jspPage");

		if (jspPage != null) {
			include(jspPage, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editJSP, renderRequest, renderResponse);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(helpJSP, renderRequest, renderResponse);
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException 
        {


            try
            {

                PortletURL purl =renderResponse.createActionURL();
                purl.setPortletMode(PortletMode.VIEW);
                renderRequest.setAttribute(JSPConstants.portletURL, purl);

                String userId = renderRequest.getRemoteUser();
                User user = UserLocalServiceUtil.getUserById(Long.parseLong(userId));
                String userName = user.getScreenName();
                System.out.println("---------------server userName:"+userName);

                Vector<SlashImage> vimage = dbutil.getSlashImages(userName);
                renderRequest.setAttribute(Constants.slashImageType, vimage);

                HashMap<String, Vector> map = dbutil.getDataModelInfo();
                renderRequest.setAttribute(Constants.imageMap, map);

                renderRequest.setAttribute("userName", userName);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

		include(viewJSP, renderRequest, renderResponse);
	}

	public void processAction(
			ActionRequest request, ActionResponse actionResponse)
		throws IOException, PortletException {

               String actionName = ParamUtil.getString(
				request, ActionRequest.ACTION_NAME);
              System.err.println("ActionName:-------------------"+actionName+"---------------");


              String removeBtn=  request.getParameter("removeBtn");
              System.out.println("Button--------------------"+removeBtn);

              if(removeBtn == null)
                  return;

              removeBtn = removeBtn.replace("Remove:", "");
              StringTokenizer st = new StringTokenizer(removeBtn,"-");

              if(st.countTokens() != 2)
                  return;

              String sdatasetID = st.nextToken();
              String smodelID = st.nextToken();

              System.out.println("--------------sdatasetID:"+sdatasetID+"------------------smodelID:"+smodelID);

              if(sdatasetID == null || smodelID == null)
                  return;

              try
              {
                  long datasetID = Long.parseLong(sdatasetID);
                  int modelID = Integer.parseInt(smodelID);
                  DeleteUtil dutil = new DeleteUtil();
                  dutil.deleteAnnotations(datasetID, modelID);
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }


	}

	protected void include(
			String path, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher =
			getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			_log.error(path + " is not a valid include");
		}
		else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	protected String editJSP;
	protected String helpJSP;
	protected String viewJSP;

	private static Log _log = LogFactoryUtil.getLog(SlashResultPortlet.class);
}

package org.sampleapp.services;

import java.io.StringWriter;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



import org.sampleapp.dto.User;
import org.sampleapp.exceptions.SampleAppException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * This class facilitates all the write functionalities to the REST Endpoint
 * such as creating, updating, deleting user objects, adding an user to a group/role,
 * deleting an user from a group/role.
 * @author t-mislam
 *
 */
public class RestAPIWriteServices {
		

	/**
	 * This method creates a new user.
	 * @param request The httpservletrequest object that contains the description
	 * of the new object.
	 * @return 
	 * @throws SampleAppException if the operation can not be successfully created.
	 */
	public static String createUser(HttpServletRequest request, String sessionKey) throws SampleAppException{		
		
		/**
		 * Send the http Post request to the appropriate url and
		 * using an appropriate message body.
		 */
		return HttpRequestHandler.handlRequestPost(
				"/Users", 
				null, 
				createXMLData(request), 
				"createUser",
				sessionKey);
	}


    /**
     * This method would create a string consisting of a xml document with all the necessary elements
     * set from the HttpServletRequest request.	
     * @param request The HttpServletRequest
     * @return the string containing the xml document.
     * @throws SampleAppException If there is any error processing the request.
     */
	private static String createXMLData(HttpServletRequest request) throws SampleAppException {
		try{
			/**
			 * Setup the necessary operations to build the xml document.
			 */
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();;
			Document xmlDoc = docBuilder.newDocument();
			
			/**
			 * Create a root element entry and set the appropriate namespace declarations.
			 */
			Element rootElement = xmlDoc.createElement("entry");
			rootElement.setAttribute("xmlns", AppParameter.xmlNameSpaceforEntry);
			rootElement.setAttribute("xmlns:d", AppParameter.xmlNameSpaceforD);
			rootElement.setAttribute("xmlns:m", AppParameter.xmlNameSpaceforM);
			xmlDoc.appendChild(rootElement);
			
			/**
			 * Create the content node and set the appropriate type attribute.
			 */
			Element content = xmlDoc.createElement("content");
			content.setAttribute("type", AppParameter.contentTypeXML);
			rootElement.appendChild(content);
			
			
			Element properties = xmlDoc.createElement("m:properties");
			content.appendChild(properties);
			
			/**
			 * Get all the field names of the Class User.
			 */
			Field[] allFields = User.class.getDeclaredFields();
			
			for(int i = 0; i < allFields.length; i++){
				/**
				 * If this is a simple field, that is of type String.
				 */
				if(allFields[i].getType().equals(String.class)){
										
					/**
					 * If a parameter by this field name is passed to the servlet and
					 * if the passed value is not empty.
					 */
					if ((request.getParameter(allFields[i].getName()) != null)&& !(request.getParameter(allFields[i].getName())).trim().isEmpty()) {
						/**
						 * Create a node by this fieldName and inserts the value
						 * passed into the servlet request.
						 */

						Element element = xmlDoc.createElement(String.format("d:%s", allFields[i].getName()));
						element.appendChild(xmlDoc.createTextNode(request.getParameter(allFields[i].getName())));
						properties.appendChild(element);
					}

				}
				
			}		
			
			/**
			 * Convert the xml document in a string and return.
			 */
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StringWriter writer = new StringWriter();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			writer.flush();
			return writer.toString();
			
			
		}catch(Exception e){
			throw new SampleAppException(AppParameter.ErrorCreatingXML,e.getMessage(), e);
			
		}
	}

	
	/**
	 * This method deletes an user identified by its ObjectId.
	 * @param objectId The ObjectId of the user to be deleted.
	 * @throws SampleAppException If the operation can not be done successfully.
	 */
	public static String deleteUser(String objectId, String sessionKey) throws SampleAppException{
		return HttpRequestHandler.handleRequestDelete( 
				String.format("/Users('User_%s')", objectId), 
				null,
				sessionKey);
		
	}

	/**
	 * This method updates an user.
	 * @param request The HttpServletRequest request
	 * @throws SampleAppException if there is an error while doing this operation.
	 */
	
	public static String updateUser(HttpServletRequest request, String sessionKey) throws SampleAppException {
		
		/**
		 * Send a patch request to the appropriate url with the request body
		 * as data.
		 */
		return HttpRequestHandler.handlRequestPost(
				String.format("/Users('%s')", request.getParameter("ObjectId")), 
				null, 
				createXMLData(request), 
				"updateUser",
				sessionKey);			
	}


	/**
	 * This method adds or removes a role or group to a particular user 
	 * depending on the parameters.
	 * @param userId The Id of the user who should be added to the group/role.
	 * @param objectName Is it Group or Role?
	 * @param opName Whether delete or add
	 * @param objectId The object Id of the Group or the Role.
	 * @throws SampleAppException 
	 */
	public static String updateLink(String userId, String objectName,
			String opName, String groupId, String sessionKey) throws SampleAppException {
		
		String newKey = null;
			/**
			 * If the operation is add.
			 */
		if(opName.equalsIgnoreCase("add")){		
			newKey =  addUserToGroup(userId, groupId, objectName, sessionKey);
		}
		
		/**
		 * If the operation is delete.
		 */
		if(opName.equalsIgnoreCase("delete")){
			String path = String.format("/%ss('%s_%s')/$links/Members('User_%s')", 
					objectName, 
					objectName,
					groupId,
					userId);
			
			newKey = HttpRequestHandler.handleRequestDelete(path, null, sessionKey);
		}
		
		return newKey;		
	}


	/**
	 * This method adds an user to a group/role.
	 * @param userId The ObjectId of the user to be added.
	 * @param groupId The ObjectId of the group/role object where to be added.
	 * @param objectName Whether user to be added in a group or a role.
	 * @throws SampleAppException If the operation can not be successfully carried out.
	 */	
	private static String addUserToGroup(
			String userId, 
			String groupId, 
			String objectName,
			String sessionKey) throws SampleAppException {
		
		String newKey = null;
		try{				
			
			/**
			 * Setup the necessary operations to build the xml document.
			 */
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();;
			Document xmlDoc = docBuilder.newDocument();
		
			/**
			 * Create a root element uri and set the appropriate namespace declarations.
			 */
			Element rootElement = xmlDoc.createElement("uri");
			rootElement.setAttribute("xmlns", AppParameter.xmlNameSpaceforM);
			xmlDoc.appendChild(rootElement);
			
			/**
			 * Create the content of uri tag.
			 */
			rootElement.appendChild(xmlDoc.createTextNode(String.format("%s://%s/%s/DirectoryObjects('User_%s')", 
					AppParameter.PROTOCOL_NAME, AppParameter.getRestServiceHost(), AppParameter.getTenantContextId(), userId)));
			
			/**
			 * Convert the xml document in a string and return.
			 */
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StringWriter writer = new StringWriter();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			writer.flush();
			String data = writer.toString();
			
			
			newKey = HttpRequestHandler.handlRequestPost(
					String.format("/%ss('%s_%s')/$links/Members", objectName, objectName, groupId), 
					null, 
					data,
					"addUserToGroup",
					sessionKey);

			
		
		}catch(ParserConfigurationException | TransformerException e){
			throw new SampleAppException( AppParameter.ErrorCreatingXML, e.getMessage(), e);
		} 
		
		return newKey;
		
	}
	

}


package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.NodeList;

/**
 * <p>
 * Class containing the static routines to create, update and delete Moodle
 * users.
 * </p>
 * 
 * @author Bill Antonia
 * @see MoodleUser
 */
public class MoodleRestUser {

	// private static final int BUFFER_MAX=4000;

	/**
	 * <p>
	 * Method to create a new user within Moodle from a MoodleUser object.
	 * </p>
	 * 
	 * @param user
	 *            MoodleUser with at least the username, password, lastname,
	 *            firstname and email fields set
	 * @return MoodleUser object updated with the user id within the Moodle
	 *         site.
	 * @throws MoodleRestUserException
	 * @throws MoodleRestException
	 */
	public static MoodleUser createUser(MoodleUser user) throws MoodleRestUserException, MoodleRestException {
		MoodleUser[] a = new MoodleUser[1];
		a[0] = user;
		MoodleUser[] usr = createUsers(a);
		return usr[0];
	}

	/**
	 * <p>
	 * Method to create new users within Moodle from an array of MoodleUser
	 * objects.
	 * </p>
	 * 
	 * @param user
	 *            MoodleUser[] array of MoodleUser with at least the username,
	 *            password, lastname, firstname and email fields set.
	 * @return MoodleUser[] array of MoodleUser objects updated with the user id
	 *         within the Moodle site.
	 * @throws MoodleRestUserException
	 * @throws MoodleRestException
	 */
	public static MoodleUser[] createUsers(MoodleUser[] user) throws MoodleRestUserException, MoodleRestException {
		Hashtable hash = new Hashtable();
		String functionCall = MoodleCallRestWebService.isLegacy() ? MoodleServices.MOODLE_USER_CREATE_USERS
				: MoodleServices.CORE_USER_CREATE_USERS;
		try {
			StringBuilder data = new StringBuilder();
			if (MoodleCallRestWebService.getAuth() == null)
				throw new MoodleRestUserException();
			else
				data.append(MoodleCallRestWebService.getAuth());// data.append(URLEncoder.encode("wstoken",
																// MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getToken(),
																// MoodleServices.ENCODING));
			data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=")
					.append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
			for (int i = 0; i < user.length; i++) {
				if (user[i] == null)
					throw new MoodleRestUserException(MoodleRestUserException.USER_NULL);
				if (user[i].getUsername() == null)
					throw new MoodleRestUserException(MoodleRestUserException.USERNAME_NULL);
				else
					data.append("&").append(URLEncoder.encode("users[" + i + "][username]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getUsername(), MoodleServices.ENCODING));
				if (user[i].getPassword() == null)
					throw new MoodleRestUserException(MoodleRestUserException.PASSWORD_NULL);
				else
					data.append("&").append(URLEncoder.encode("users[" + i + "][password]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getPassword(), MoodleServices.ENCODING));
				if (user[i].getFirstname() == null)
					throw new MoodleRestUserException(MoodleRestUserException.FIRSTNAME_NULL);
				else
					data.append("&").append(URLEncoder.encode("users[" + i + "][firstname]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getFirstname(), MoodleServices.ENCODING));
				if (user[i].getLastname() == null)
					throw new MoodleRestUserException(MoodleRestUserException.LASTNAME_NULL);
				else
					data.append("&").append(URLEncoder.encode("users[" + i + "][lastname]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getLastname(), MoodleServices.ENCODING));
				if (user[i].getEmail() == null)
					throw new MoodleRestUserException(MoodleRestUserException.EMAIL_NULL);
				else
					data.append("&").append(URLEncoder.encode("users[" + i + "][email]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getEmail(), MoodleServices.ENCODING));
				if (user[i].getAuth() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][auth]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getAuth(), MoodleServices.ENCODING));
				if (user[i].getIdNumber() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][idnumber]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getIdNumber(), MoodleServices.ENCODING));
				if (user[i].getLang() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][lang]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getLang(), MoodleServices.ENCODING));
				if (user[i].getTheme() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][theme]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getTheme(), MoodleServices.ENCODING));
				if (user[i].getTimezone() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][timezone]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getTimezone(), MoodleServices.ENCODING));
				data.append("&").append(URLEncoder.encode("users[" + i + "][mailformat]", MoodleServices.ENCODING))
						.append("=")
						.append(URLEncoder.encode("" + (user[i].getMailFormat() ? 1 : 0), MoodleServices.ENCODING));
				if (user[i].getDescription() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][description]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getDescription(), MoodleServices.ENCODING));
				if (user[i].getCity() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][city]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getCity(), MoodleServices.ENCODING));
				if (user[i].getCountry() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][country]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getCountry(), MoodleServices.ENCODING));
				if (user[i].getPreference() != null) {
					for (int j = 0; j < user[i].getPreference().size(); j++) {
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][preferences][" + j + "][type]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getPreference().get(j).getType(),
										MoodleServices.ENCODING));
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][preferences][" + j + "][value]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getPreference().get(j).getValue(),
										MoodleServices.ENCODING));
					}
				}
				if (user[i].getCustomFields() != null) {
					for (int j = 0; j < user[i].getCustomFields().size(); j++) {
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][customfields][" + j + "][type]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getCustomFields().get(j).getName(),
										MoodleServices.ENCODING));
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][customfields][" + j + "][value]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getCustomFields().get(j).getValue(),
										MoodleServices.ENCODING));
					}
				}
			}
			data.trimToSize();
			NodeList elements = MoodleCallRestWebService.call(data.toString());
			for (int j = 0; j < elements.getLength(); j += 2) {
				hash.put(elements.item(j + 1).getLocalName(), elements.item(j).getLocalName());
			}
		} catch (IOException ex) {
			Logger.getLogger(MoodleRestUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		for (int i = 0; i < user.length; i++) {
			user[i].setId(Long.parseLong((String) (hash.get(user[i].getUsername()))));
		}
		return user;
	}

	/**
	 * <p>
	 * Used to update the information for a single user within the Moodle site.
	 * </p>
	 * 
	 * @param user
	 *            MoodleUser object containing the updated user information.
	 * @throws MoodleRestUserException
	 * @throws MoodleRestException
	 */
	public static void updateUser(MoodleUser user) throws MoodleRestUserException, MoodleRestException {
		MoodleUser[] a = new MoodleUser[1];
		a[0] = user;
		updateUsers(a);
	}

	/**
	 * <p>
	 * Used to modify information about users within the Moodle site.
	 * </p>
	 * 
	 * @param user
	 *            MoodleUser[] array of MoodleUser objects containing the
	 *            updated user information.
	 * @throws MoodleRestUserException
	 * @throws MoodleRestException
	 */
	public static void updateUsers(MoodleUser[] user) throws MoodleRestUserException, MoodleRestException {
		String functionCall = MoodleCallRestWebService.isLegacy() ? MoodleServices.MOODLE_USER_UPDATE_USERS
				: MoodleServices.CORE_USER_UPDATE_USERS;
		try {
			StringBuilder data = new StringBuilder();
			if (MoodleCallRestWebService.getAuth() == null)
				throw new MoodleRestUserException();
			else
				data.append(MoodleCallRestWebService.getAuth());
			data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=")
					.append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
			for (int i = 0; i < user.length; i++) {
				if (user[i] == null)
					throw new MoodleRestUserException(MoodleRestUserException.USER_NULL);
				if (user[i].getId() == -1)
					throw new MoodleRestUserException(MoodleRestUserException.INVALID_USERID);
				else
					data.append("&").append(URLEncoder.encode("users[" + i + "][id]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode("" + user[i].getId(), MoodleServices.ENCODING));
				if (user[i].getUsername() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][username]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getUsername(), MoodleServices.ENCODING));
				if (user[i].getPassword() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][password]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getPassword(), MoodleServices.ENCODING));
				if (user[i].getFirstname() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][firstname]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getFirstname(), MoodleServices.ENCODING));
				if (user[i].getLastname() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][lastname]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getLastname(), MoodleServices.ENCODING));
				if (user[i].getEmail() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][email]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getEmail(), MoodleServices.ENCODING));
				if (user[i].getAuth() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][auth]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getAuth(), MoodleServices.ENCODING));
				if (user[i].getIdNumber() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][idnumber]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getIdNumber(), MoodleServices.ENCODING));
				if (user[i].getLang() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][lang]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getLang(), MoodleServices.ENCODING));
				if (user[i].getTheme() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][theme]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getTheme(), MoodleServices.ENCODING));
				if (user[i].getTimezone() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][timezone]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getTimezone(), MoodleServices.ENCODING));
				data.append("&").append(URLEncoder.encode("users[" + i + "][mailformat]", MoodleServices.ENCODING))
						.append("=")
						.append(URLEncoder.encode("" + (user[i].getMailFormat() ? 1 : 0), MoodleServices.ENCODING));
				if (user[i].getDescription() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][description]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getDescription(), MoodleServices.ENCODING));
				if (user[i].getCity() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][city]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getCity(), MoodleServices.ENCODING));
				if (user[i].getCountry() != null)
					data.append("&").append(URLEncoder.encode("users[" + i + "][country]", MoodleServices.ENCODING))
							.append("=").append(URLEncoder.encode(user[i].getCountry(), MoodleServices.ENCODING));
				if (user[i].getPreference() != null) {
					for (int j = 0; j < user[i].getPreference().size(); j++) {
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][preferences][" + j + "][type]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getPreference().get(j).getType(),
										MoodleServices.ENCODING));
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][preferences][" + j + "][value]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getPreference().get(j).getValue(),
										MoodleServices.ENCODING));
					}
				}
				if (user[i].getCustomFields() != null) {
					for (int j = 0; j < user[i].getCustomFields().size(); j++) {
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][customfields][" + j + "][type]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getCustomFields().get(j).getName(),
										MoodleServices.ENCODING));
						data.append("&")
								.append(URLEncoder.encode("users[" + i + "][customfields][" + j + "][value]",
										MoodleServices.ENCODING))
								.append("=").append(URLEncoder.encode(user[i].getCustomFields().get(j).getValue(),
										MoodleServices.ENCODING));
					}
				}
			}
			data.trimToSize();
			MoodleCallRestWebService.call(data.toString());
		} catch (IOException ex) {
			Logger.getLogger(MoodleRestUser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * <p>
	 * Method to delete the account of a single user within the Moodle site.
	 * </p>
	 * 
	 * @param user
	 *            long The Moodle id of the user to delete.
	 * @throws MoodleRestUserException
	 * @throws UnsupportedEncodingException
	 * @throws MoodleRestException
	 */
	public static void deleteUser(long user)
			throws MoodleRestUserException, UnsupportedEncodingException, MoodleRestException {
		long[] a = new long[1];
		a[0] = user;
		deleteUsers(a);
	}

	/**
	 * <p>
	 * Method used to delete a number of users within the Moodle site.
	 * </p>
	 * 
	 * @param userids
	 *            long[] array of Moodle user ids
	 * @throws MoodleRestUserException
	 * @throws UnsupportedEncodingException
	 * @throws MoodleRestException
	 */
	public static void deleteUsers(long[] userids)
			throws MoodleRestUserException, UnsupportedEncodingException, MoodleRestException {
		String functionCall = MoodleCallRestWebService.isLegacy() ? MoodleServices.MOODLE_USER_DELETE_USERS
				: MoodleServices.CORE_USER_DELETE_USERS;
		try {
			StringBuilder data = new StringBuilder();
			if (MoodleCallRestWebService.getAuth() == null)
				throw new MoodleRestUserException();
			else
				data.append(MoodleCallRestWebService.getAuth());// data.append(URLEncoder.encode("wstoken",
																// MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getToken(),
																// MoodleServices.ENCODING));
			data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=")
					.append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
			for (int i = 0; i < userids.length; i++) {
				if (userids[i] < 1)
					throw new MoodleRestUserException(MoodleRestUserException.INVALID_USERID);
				else
					data.append("&").append(URLEncoder.encode("userids[" + i + "]", MoodleServices.ENCODING))
							.append("=").append(userids[i]);
			}
			data.trimToSize();
			MoodleCallRestWebService.call(data.toString());
		} catch (IOException ex) {
			Logger.getLogger(MoodleRestUser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * <p>
	 * Method which gets the information of a single users from their Moodle id.
	 * </p>
	 * 
	 * @param userid
	 *            long Moodle id of the user
	 * @return MoodleUser object containing the data of the selected user.
	 * @throws MoodleRestUserException
	 * @throws UnsupportedEncodingException
	 * @throws MoodleRestException
	 */
	public static MoodleUser getUserById(long userid)
			throws MoodleRestUserException, UnsupportedEncodingException, MoodleRestException {
		long[] a = new long[1];
		a[0] = userid;
		MoodleUser[] usr = getUsersById(a);
		return usr[0];
	}

	/**
	 * Method which gets a number of MoodleUser objects containing the
	 * information of users from their Moodle ids.
	 * </p>
	 * 
	 * @param userids
	 *            long[] array containing the Moodle ids of a number of users
	 * @return MoodleUser[] array of MoodleUser objects containing the user
	 *         data.
	 * @throws MoodleRestUserException
	 * @throws UnsupportedEncodingException
	 * @throws MoodleRestException
	 */
	public static MoodleUser[] getUsersById(long[] userids)
			throws MoodleRestUserException, UnsupportedEncodingException, MoodleRestException {
		Vector v = new Vector();
		MoodleUser user = null;
		// boolean processed=false;
		String functionCall = MoodleCallRestWebService.isLegacy() ? MoodleServices.MOODLE_USER_GET_USERS_BY_ID
				: MoodleServices.CORE_USER_GET_USERS_BY_ID;
		try {
			StringBuilder data = new StringBuilder();
			if (MoodleCallRestWebService.getAuth() == null)
				throw new MoodleRestUserException();
			else
				data.append(MoodleCallRestWebService.getAuth());// data.append(URLEncoder.encode("wstoken",
																// MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getToken(),
																// MoodleServices.ENCODING));
			data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=")
					.append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
			UserCustomField customField = null;
			UserPreference preference = null;
			UserEnrolledCourse enrolledCourse = null;
			for (int i = 0; i < userids.length; i++) {
				if (userids[i] < 1)
					throw new MoodleRestUserException(MoodleRestUserException.INVALID_USERID);
				data.append("&").append(URLEncoder.encode("userids[" + i + "]", MoodleServices.ENCODING)).append("=")
						.append(userids[i]);
			}
			data.trimToSize();
			NodeList elements = MoodleCallRestWebService.call(data.toString());
			user = null;
			for (int j = 0; j < elements.getLength(); j++) {
				String parent = elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode()
						.getNodeName();
				if (parent.equals("KEY"))
					parent = elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode()
							.getAttributes().getNamedItem("name").getNodeValue();
				String content = elements.item(j).getLocalName();
				String nodeName = elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
				if (parent.equals("RESPONSE") && nodeName.equals("id")) {
					if (user == null)
						user = new MoodleUser(Long.parseLong(content));
					else {
						v.add(user);
						user = new MoodleUser(Long.parseLong(content));
					}
					customField = null;
					preference = null;
					enrolledCourse = null;
				} else {
					if (parent.equals("RESPONSE")) {
						try {
							user.setMoodleUserField(nodeName, content);
						} catch (NullPointerException ex) {
							System.out.println("Error " + nodeName + " = " + content);
						}
					} else {
						if (parent.equals("customfields") && nodeName.equals("type")) {
							if (customField != null)
								user.addCustomField(customField);
							customField = new UserCustomField();
							customField.setCustomFieldField(nodeName, content);
						} else {
							if (parent.equals("customfields")) {
								customField.setCustomFieldField(nodeName, content);
							} else {
								if (parent.equals("preferences") && nodeName.equals("name")) {
									if (preference != null)
										user.addPreference(preference);
									preference = new UserPreference();
									preference.setType(content);
								} else {
									if (parent.equals("preferences")) {
										preference.setValue(content);
									} else {
										if (parent.equals("enrolledcourses") && nodeName.equals("id")) {
											if (enrolledCourse != null)
												user.addEnrolledCourse(enrolledCourse);
											enrolledCourse = new UserEnrolledCourse(Long.parseLong(content));
										} else {
											if (parent.equals("enrolledcourses") && !nodeName.equals("id")) {
												enrolledCourse.setUserEnrolledCourseField(nodeName, content);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (user != null) {
				if (customField != null)
					user.addCustomField(customField);
				customField = null;
				if (preference != null)
					user.addPreference(preference);
				preference = null;
				if (enrolledCourse != null)
					user.addEnrolledCourse(enrolledCourse);
				enrolledCourse = null;
				v.add(user);
			}
		} catch (IOException ex) {
			Logger.getLogger(MoodleRestUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		MoodleUser[] users = new MoodleUser[v.size()];
		for (int i = 0; i < v.size(); i++) {
			users[i] = (MoodleUser) v.get(i);
		}
		v.removeAllElements();
		return users;
	}

	/**
	 * <p>
	 * Method to return the details of courses in which users are enrolled
	 * </p>
	 * 
	 * @param userList
	 * @return MoodleUser[]
	 * @throws MoodleRestUserException
	 * @throws UnsupportedEncodingException
	 * @throws MoodleRestException
	 */
	public MoodleUser[] getCourseUserProfiles(UserList[] userList)
			throws MoodleRestUserException, UnsupportedEncodingException, MoodleRestException {
		Vector v = new Vector();
		MoodleUser user = null;
		if (MoodleCallRestWebService.isLegacy())
			throw new MoodleRestUserException(MoodleRestException.NO_LEGACY);
		String functionCall = MoodleServices.CORE_USER_GET_COURSE_USER_PROFILE;
		try {
			StringBuilder data = new StringBuilder();
			if (MoodleCallRestWebService.getAuth() == null)
				throw new MoodleRestUserException();
			else
				data.append(MoodleCallRestWebService.getAuth());// data.append(URLEncoder.encode("wstoken",
																// MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getToken(),
																// MoodleServices.ENCODING));
			data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=")
					.append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
			UserCustomField customField = null;
			UserGroup group = null;
			UserRole role = null;
			UserPreference preference = null;
			UserEnrolledCourse enrolledCourse = null;
			for (int i = 0; i < userList.length; i++) {
				if (userList[i].getUserId() < 1)
					throw new MoodleRestUserException(MoodleRestUserException.INVALID_USERID);
				data.append("&").append(URLEncoder.encode("userlist[" + i + "][userid]", MoodleServices.ENCODING))
						.append("=").append(userList[i].getUserId());
				data.append("&").append(URLEncoder.encode("userlist[" + i + "][courseid]", MoodleServices.ENCODING))
						.append("=").append(userList[i].getCourseId());
			}
			data.trimToSize();
			NodeList elements = MoodleCallRestWebService.call(data.toString());
			user = null;
			for (int j = 0; j < elements.getLength(); j++) {
				String parent = elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode()
						.getNodeName();
				if (parent.equals("KEY"))
					parent = elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode()
							.getAttributes().getNamedItem("name").getNodeValue();
				String content = elements.item(j).getLocalName();
				String nodeName = elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
				if (parent.equals("RESPONSE") && nodeName.equals("id")) {
					if (user == null)
						user = new MoodleUser(Long.parseLong(content));
					else {
						v.add(user);
						user = new MoodleUser(Long.parseLong(content));
					}
					customField = null;
					group = null;
					role = null;
					preference = null;
					enrolledCourse = null;
				} else {
					if (parent.equals("RESPONSE"))
						user.setMoodleUserField(nodeName, content);
					else {
						if (parent.equals("customfields") && nodeName.equals("type")) {
							if (customField != null)
								user.addCustomField(customField);
							customField = new UserCustomField();
							customField.setCustomFieldField(nodeName, content);
						} else {
							if (parent.equals("customfields")) {
								customField.setCustomFieldField(nodeName, content);
							} else {
								if (parent.equals("groups") && nodeName.equals("id")) {
									if (group != null)
										user.addGroup(group);
									group = new UserGroup();
									group.setUserGroupField(nodeName, content);
								} else {
									if (parent.equals("groups")) {
										group.setUserGroupField(nodeName, content);
									} else {
										if (parent.equals("roles") && nodeName.equals("roleid")) {
											if (role != null)
												user.addRole(role);
											role = new UserRole();
											role.setUserRoleField(nodeName, content);
										} else {
											if (parent.equals("roles")) {
												role.setUserRoleField(nodeName, content);
											} else {
												if (parent.equals("preferences") && nodeName.equals("name")) {
													if (preference != null)
														user.addPreference(preference);
													preference = new UserPreference();
													preference.setType(content);
												} else {
													if (parent.equals("preferences")) {
														preference.setValue(content);
													} else {
														if (parent.equals("enrolledcourses") && nodeName.equals("id")) {
															if (enrolledCourse != null)
																user.addEnrolledCourse(enrolledCourse);
															enrolledCourse = new UserEnrolledCourse(
																	Long.parseLong(content));
														} else {
															if (parent.equals("enrolledcourses")
																	&& !nodeName.equals("id")) {
																enrolledCourse.setUserEnrolledCourseField(nodeName,
																		content);
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (user != null) {
				if (customField != null)
					user.addCustomField(customField);
				customField = null;
				if (preference != null)
					user.addPreference(preference);
				preference = null;
				if (enrolledCourse != null)
					user.addEnrolledCourse(enrolledCourse);
				enrolledCourse = null;
				v.add(user);
			}
		} catch (IOException ex) {
			Logger.getLogger(MoodleRestUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		MoodleUser[] users = new MoodleUser[v.size()];
		for (int i = 0; i < v.size(); i++) {
			users[i] = (MoodleUser) v.get(i);
		}
		v.removeAllElements();
		return users;
	}

	

	/**
	 * Method which gets a number of MoodleUser objects containing the
	 * information of users from their Moodle ids.
	 * </p>
	 * 
	 * @param userids
	 *            long[] array containing the Moodle ids of a number of users
	 * @return MoodleUser[] array of MoodleUser objects containing the user
	 *         data.
	 * @throws MoodleRestUserException
	 * @throws UnsupportedEncodingException
	 * @throws MoodleRestException
	 */
	public static MoodleUser getUserByUserName(String username)
			throws MoodleRestUserException, UnsupportedEncodingException, MoodleRestException {
		MoodleUser user = null;
		// boolean processed=false;
		String functionCall = MoodleServices.CORE_USER_GET_USERS;
		try {
			StringBuilder data = new StringBuilder();
			if (MoodleCallRestWebService.getAuth() == null)
				throw new MoodleRestUserException();
			else
				data.append(MoodleCallRestWebService.getAuth());

			data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=")
					.append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));

				if (username == null)
					throw new MoodleRestUserException(MoodleRestUserException.INVALID_USERID);
				
				data.append("&").append(URLEncoder.encode("criteria[0][key]", MoodleServices.ENCODING))
						.append("=").append("username");
				data.append("&").append(URLEncoder.encode("criteria[0][value]", MoodleServices.ENCODING))
						.append("=").append(username);
			
			data.trimToSize();
			
			NodeList elements = MoodleCallRestWebService.call(data.toString());
			user = null;
			for (int j = 0; j < elements.getLength(); j++) {
				String parent = elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode()
						.getNodeName();
				if (parent.equals("KEY"))
					parent = elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode()
							.getAttributes().getNamedItem("name").getNodeValue();
				String content = elements.item(j).getLocalName();
				String nodeName = elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
				if (parent.equals("users") && nodeName.equals("id")) {
					if (user == null) {
						user = new MoodleUser(Long.parseLong(content));
						break;
					}

				}
			}

		} catch (IOException ex) {
			Logger.getLogger(MoodleRestUser.class.getName()).log(Level.SEVERE, null, ex);
		}

		return user;
	}

}

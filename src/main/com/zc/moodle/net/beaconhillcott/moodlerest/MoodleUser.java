
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.util.ArrayList;

/**
 * Class to hold the state of a MoodleUser object. Used when creating, updating or fetching user information in Moodle.
 * @see MoodleRestUser
 * @author Bill Antonia
 */
public class MoodleUser {

    /**
     *
     */
    public static final boolean EMAIL_FORMAT_NONE = false;
    /**
     *
     */
    public static final boolean EMAIL_FORMAT_HTML = true;
    
    private long id=-1;
    private String username=null;
    private String password=null;
    private String firstname=null;
    private String lastname=null;
    private String email=null;
    private String auth=null;
    private String idnumber=null;
    private String lang=null;
    private String theme=null;
    private String timezone=null;
    private boolean mailformat=EMAIL_FORMAT_HTML;
    private String description=null;
    private String city=null;
    private String country=null;
    
    private String address=null;
    private String phone1=null;
    private String phone2=null;
    private String icq=null;
    private String skype=null;
    private String yahoo=null;
    private String msn=null;
    private String department=null;
    private String institution=null;
    private String interests=null;
    private long firstaccess=-1;
    private long lastaccess=-1;
    private double confirmed=0.0;
    private long descriptionformat=-1;
    private String url=null;
    private String profileimageurlsmall=null;
    private String profileimageurl=null;
    private ArrayList<UserCustomField> customfields=null;
    private ArrayList<UserGroup> groups=null;
    private ArrayList<UserRole> roles=null;
    private ArrayList<UserPreference> preferences=null;
    private ArrayList<UserEnrolledCourse> enrolledcourses=null;

    /**
     *
     */
    public MoodleUser() {}

    /**
     *
     * @param id
     */
    public MoodleUser(long id) {
        this.id=id;
    }

    /**
     *
     * @param username
     * @param password
     * @param firstname
     * @param lastname
     * @param email
     */
    public MoodleUser(String username, String password, String firstname, String lastname, String email) {
        this.username=username;
        this.password=password;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
    }

    /**
     *
     * @param nodeName
     * @param content
     */
    public void setMoodleUserField(String nodeName,String content) {
        if (nodeName.equals("id")) setId(Long.parseLong(content));
        if (nodeName.equals("username")) setUsername(content);
        if (nodeName.equals("firstname")) setFirstname(content);
        if (nodeName.equals("lastname")) setLastname(content);
        if (nodeName.equals("email")) setEmail(content);
        if (nodeName.equals("auth")) setAuth(content);
        if (nodeName.equals("idnumber") && !content.equals("")) setIdNumber(content);
        if (nodeName.equals("lang") && !content.equals("")) setLang(content);
        if (nodeName.equals("theme") && !content.equals("")) setTheme(content);
        if (nodeName.equals("timezone") && !content.equals("")) setTimezone(content);
        if (nodeName.equals("mailformat") && !content.equals("")) setMailFormat(Integer.parseInt(content)==0?EMAIL_FORMAT_NONE:EMAIL_FORMAT_HTML);
        if (nodeName.equals("description") && !content.equals("")) setDescription(content);
        if (nodeName.equals("descriptionformat") && !content.equals("")) setDescriptionFormat(Long.parseLong(content));
        if (nodeName.equals("city") && !content.equals("")) setCity(content);
        if (nodeName.equals("country") && !content.equals("")) setCountry(content);
        
        if (nodeName.equals("address") && !content.equals("")) setAddress(content);
        if (nodeName.equals("phone1") && !content.equals("")) setPhone1(content);
        if (nodeName.equals("phone2") && !content.equals("")) setPhone2(content);
        if (nodeName.equals("icq") && !content.equals("")) setICQ(content);
        if (nodeName.equals("skype") && !content.equals("")) setSkype(content);
        if (nodeName.equals("yahoo") && !content.equals("")) setYahoo(content);
        if (nodeName.equals("msn") && !content.equals("")) setMSN(content);
        if (nodeName.equals("department") && !content.equals("")) setDepartment(content);
        if (nodeName.equals("institution") && !content.equals("")) setInstitution(content);
        if (nodeName.equals("interests") && !content.equals("")) setInterests(content);
        if (nodeName.equals("firstaccess") && !content.equals("")) setFirstAccess(Long.parseLong(content));
        if (nodeName.equals("lastaccess") && !content.equals("")) setLastAccess(Long.parseLong(content));
        if (nodeName.equals("confirmed") && !content.equals("")) setConfirmed(Double.parseDouble(content));
        if (nodeName.equals("url") && !content.equals("")) setURL(content);
        if (nodeName.equals("profileimageurlsmall") && !content.equals("")) setProfileImageURLSmall(content);
        if (nodeName.equals("profileimageurl") && !content.equals("")) setProfileImageURL(content);
    }

    /**
     *
     * @param type
     * @param value
     * @param name
     * @param shortname
     */
    public void addCustomField(String type, String value, String name, String shortname) {
      if (customfields==null)
        customfields=new ArrayList();
      UserCustomField field=new UserCustomField(type, value, name, shortname);
      customfields.add(field);
    }
    
    /**
     *
     * @param field
     */
    public void addCustomField(UserCustomField field) {
      if (customfields==null)
        customfields=new ArrayList();
      customfields.add(field);
    }
    
    /**
     *
     * @param id
     * @param name
     * @param description
     */
    public void addGroup(long id, String name, String description) {
      if (groups==null)
        groups=new ArrayList();
      UserGroup group=new UserGroup(id, name, description);
      groups.add(group);
    }
    
    /**
     *
     * @param group
     */
    public void addGroup(UserGroup group) {
      if (groups==null)
        groups=new ArrayList();
      groups.add(group);
    }
    
    /**
     *
     * @param roleid
     * @param name
     * @param shortname
     * @param sortorder
     */
    public void addRole(long roleid, String name, String shortname, int sortorder) {
      if (roles==null)
        roles=new ArrayList();
      UserRole role=new UserRole(roleid, name, shortname, sortorder);
      roles.add(role);
    }
    
    /**
     *
     * @param role
     */
    public void addRole(UserRole role) {
      if (roles==null)
        roles=new ArrayList();
      roles.add(role);
    }
    
    /**
     *
     * @param name
     * @param value
     */
    public void addPreference(String name, String value) {
      if (preferences==null)
        preferences=new ArrayList();
      UserPreference preference=new UserPreference(name, value);
      preferences.add(preference);
    }
    
    /**
     *
     * @param preference
     */
    public void addPreference(UserPreference preference) {
      if (preferences==null)
        preferences=new ArrayList();
      preferences.add(preference);
    }
    
    /**
     *
     * @param id
     * @param fullname
     * @param shortname
     */
    public void addEnrolledCourse(long id, String fullname, String shortname) {
      if (enrolledcourses==null)
        enrolledcourses=new ArrayList();
      UserEnrolledCourse course=new UserEnrolledCourse(id, fullname, shortname);
      enrolledcourses.add(course);
    }
    
    /**
     *
     * @param course
     */
    public void addEnrolledCourse(UserEnrolledCourse course) {
      if (enrolledcourses==null)
        enrolledcourses=new ArrayList();
      enrolledcourses.add(course);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<UserCustomField> getCustomFields() {
      return customfields;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<UserGroup> getGroups() {
      return groups;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<UserRole> getRoles() {
      return roles;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<UserPreference> getPreference() {
      return preferences;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<UserEnrolledCourse> getEnrolledCourses() {
      return enrolledcourses;
    }
    
    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
    
    /**
     *
     * @return
     */
    public String getAuth() {
        return auth;
    }
    
    /**
     *
     * @return
     */
    public String getIdNumber() {
        return idnumber;
    }

    /**
     *
     * @return
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @return
     */
    public String getTheme() {
        return theme;
    }

    /**
     *
     * @return
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @return
     */
    public boolean getMailFormat() {
        return mailformat;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }
    
    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }
    
    /**
     *
     * @return
     */
    public String getPhone1() {
        return phone1;
    }
    
    /**
     *
     * @return
     */
    public String getPhone2() {
        return phone2;
    }
    
    /**
     *
     * @return
     */
    public String getICQ() {
        return icq;
    }
    
    /**
     * 
     * @return
     */
    public String getSkype() {
      return skype;  
    }
    
    /**
     *
     * @return
     */
    public String getYahoo() {
        return yahoo;
    }
    
    /**
     *
     * @return
     */
    public String getMSN() {
        return msn;
    }
    
    /**
     *
     * @return
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     *
     * @return
     */
    public String getInstitution() {
        return institution;
    }
    
    /**
     *
     * @return
     */
    public String getInterests() {
        return interests;
    }
    
    /**
     *
     * @return
     */
    public String getURL() {
        return url;
    }
    
    /**
     *
     * @return
     */
    public long getFirstAccess() {
        return firstaccess;
    }
    
    /**
     *
     * @return
     */
    public long getLastAccess() {
        return lastaccess;
    }
    
    /**
     *
     * @return
     */
    public String getProfileImageURLSmall() {
      return profileimageurlsmall;
    }

    /**
     *
     * @return
     */
    public String getProfileImageURL() {
      return profileimageurl;
    }
    
    /**
     * 
     * @return
     */
    public double getConfirmed() {
      return confirmed;
    }
    
    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id=id;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username=username;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password=password;
    }

    /**
     *
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname=firstname;
    }

    /**
     *
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname=lastname;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email=email;
    }

    /**
     *
     * @param auth
     */
    public void setAuth(String auth) {
        this.auth=auth;
    }

    /**
     *
     * @param idnumber
     */
    public void setIdNumber(String idnumber) {
        this.idnumber=idnumber;
    }

    /**
     *
     * @param lang
     */
    public void setLang(String lang) {
        this.lang=lang;
    }

    /**
     *
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme=theme;
    }

    /**
     *
     * @param timezone
     */
    public void setTimezone(String timezone) {
        this.timezone=timezone;
    }

    /**
     *
     * @param mailformat
     */
    public void setMailFormat(boolean mailformat) {
        this.mailformat=mailformat;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description=description;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city=city;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country=country;
    }

    /**
     *
     * @return
     */
    public boolean isEmailHTMLFormat() {
        return mailformat;
    }
    
    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address=address;
    }
    
    /**
     *
     * @param phone1
     */
    public void setPhone1(String phone1) {
        this.phone1=phone1;
    }
    
    /**
     *
     * @param phone2
     */
    public void setPhone2(String phone2) {
        this.phone2=phone2;
    }
    
    /**
     *
     * @param icq
     */
    public void setICQ(String icq) {
        this.icq=icq;
    }
    
    /**
     *
     * @param yahoo
     */
    public void setYahoo(String yahoo) {
        this.yahoo=yahoo;
    }
    
    /**
     *
     * @param msn
     */
    public void setMSN(String msn) {
        this.msn=msn;
    }
    
    /**
     *
     * @param skype
     */
    public void setSkype(String skype) {
        this.skype=skype;
    }
    
    /**
     *
     * @param department
     */
    public void setDepartment(String department) {
        this.department=department;
    }
    
    /**
     *
     * @param interests
     */
    public void setInterests(String interests) {
        this.interests=interests;
    }
    
    /**
     *
     * @param institution
     */
    public void setInstitution(String institution) {
        this.institution=institution;
    }
    
    /**
     *
     * @param url
     */
    public void setURL(String url) {
        this.url=url;
    }

    /**
     *
     * @param firstaccess
     */
    public void setFirstAccess(long firstaccess) {
        this.firstaccess=firstaccess;
    }
    
    /**
     *
     * @param lastaccess
     */
    public void setLastAccess(long lastaccess) {
        this.lastaccess=lastaccess;
    }
    
    /**
     * 
     * @param confirmed
     */
    public void setConfirmed(double confirmed) {
      this.confirmed=confirmed;
    }
    
    /**
     *
     * @param profileimageurlsmall
     */
    public void setProfileImageURLSmall(String profileimageurlsmall) {
      this.profileimageurlsmall=profileimageurlsmall;
    }

    /**
     *
     * @param profileimageurl
     */
    public void setProfileImageURL(String profileimageurl) {
      this.profileimageurl=profileimageurl;
    }

    /**
     * 
     * @param descriptionformat
     */
    public void setDescriptionFormat(long descriptionformat) {
    this.descriptionformat=descriptionformat;
  }
  
  /**
   * 
   * @return
   */
  public long getDescriptionFormat() {
    return descriptionformat;
  }
}

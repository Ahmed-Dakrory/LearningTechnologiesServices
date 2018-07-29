
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class containing the Moodle web services function names as constants.</p>
 * 
 * @author Bill Antonia
 */
public class MoodleServices {
  
  // Group functions
  /**
   *
   */
  public static final String CORE_GROUP_CREATE_GROUPS="core_group_create_groups";
  /**
   *
   */
  public static final String CORE_GROUP_GET_GROUPS="core_group_get_groups";
  /**
   *
   */
  public static final String CORE_GROUP_GET_COURSE_GROUPS="core_group_get_course_groups";
  /**
   *
   */
  public static final String CORE_GROUP_DELETE_GROUPS="core_group_delete_groups";
  /**
   *
   */
  public static final String CORE_GROUP_GET_GROUP_MEMBERS="core_group_get_group_members";
  /**
   *
   */
  public static final String CORE_GROUP_ADD_GROUP_MEMBERS="core_group_add_group_members";
  /**
   *
   */
  public static final String CORE_GROUP_DELETE_GROUP_MEMBERS="core_group_delete_group_members";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_GROUP_CREATE_GROUPS="moodle_group_create_groups";
  /**
   *
   */
  public static final String MOODLE_GROUP_GET_GROUPS="moodle_group_get_groups";
  /**
   *
   */
  public static final String MOODLE_GROUP_GET_COURSE_GROUPS="moodle_group_get_course_groups";
  /**
   *
   */
  public static final String MOODLE_GROUP_DELETE_GROUPS="moodle_group_delete_groups";
  /**
   *
   */
  public static final String MOODLE_GROUP_GET_GROUP_MEMBERS="moodle_group_get_group_members";
  /**
   *
   */
  public static final String MOODLE_GROUP_ADD_GROUP_MEMBERS="moodle_group_add_group_members";
  /**
   *
   */
  public static final String MOODLE_GROUP_DELETE_GROUP_MEMBERS="moodle_group_delete_group_members";
  
  // Files functions
  /**
   *
   */
  public static final String CORE_FILES_GET_FILES="core_files_get_files";
  /**
   *
   */
  public static final String CORE_FILES_UPLOAD="core_files_upload";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_FILES_GET_FILES="moodle_files_get_files";
  /**
   *
   */
  public static final String MOODLE_FILES_UPLOAD="moodle_files_upload";
  
  // User functions
  /**
   *
   */
  public static final String CORE_USER_CREATE_USERS="core_user_create_users";
  /**
   *
   */
  public static final String CORE_USER_GET_USERS_BY_ID="core_user_get_users_by_id";
  /**
   *
   */
  public static final String CORE_USER_GET_COURSE_USER_PROFILE="core_user_get_course_user_profile";
  /**
   *
   */
  public static final String CORE_USER_DELETE_USERS="core_user_delete_users";
  /**
   *
   */
  public static final String CORE_USER_UPDATE_USERS="core_user_update_users";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_USER_CREATE_USERS="moodle_user_create_users";
  /**
   *
   */
  public static final String MOODLE_USER_GET_USERS_BY_ID="moodle_user_get_users_by_id";
  /**
   *
   */
  public static final String MOODLE_USER_GET_USERS_BY_COURSEID="moodle_user_get_users_by_courseid";
  /**
   *
   */
  public static final String MOODLE_USER_GET_COURSE_PARTICIPANTS_BY_ID="moodle_user_get_course_participants_by_id";
  /**
   *
   */
  public static final String MOODLE_USER_DELETE_USERS="moodle_user_delete_users";
  /**
   *
   */
  public static final String MOODLE_USER_UPDATE_USERS="moodle_user_update_users";
  
  // Enrol functions
  /**
   *
   */
  public static final String CORE_ENROL_GET_ENROLLED_USERS="core_enrol_get_enrolled_users";
  /**
   *
   */
  public static final String CORE_ENROL_GET_USERS_COURSES="core_enrol_get_users_courses";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_ENROL_GET_ENROLLED_USERS="moodle_enrol_get_enrolled_users";
  /**
   *
   */
  public static final String MOODLE_ENROL_GET_USERS_COURSES="moodle_enrol_get_users_courses";
  
  // Role functions
  /**
   *
   */
  public static final String CORE_ROLE_ASSIGN_ROLES="core_role_assign_roles";
  /**
   *
   */
  public static final String CORE_ROLE_UNASSIGN_ROLES="core_role_unassign_roles";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_ROLE_ASSIGN="moodle_role_assign";
  /**
   *
   */
  public static final String MOODLE_ROLE_UNASSIGN="moodle_role_unassign";
  
  // Course functions
  /**
   *
   */
  public static final String CORE_COURSE_GET_COURSES="core_course_get_courses";
  /**
   *
   */
  public static final String CORE_COURSE_CREATE_COURSES="core_course_create_courses";
  /**
   *
   */
  public static final String CORE_COURSE_GET_CONTENTS="core_course_get_contents";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_COURSE_GET_COURSES="moodle_course_get_courses";
  /**
   *
   */
  public static final String MOODLE_COURSE_CREATE_COURSES="moodle_course_create_courses";
  
  // Message functions
  /**
   *
   */
  public static final String CORE_MESSAGE_SEND_INSTANT_MESSAGES="core_message_send_instant_messages";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_MESSAGE_SEND_INSTANTMESSAGES="moodle_message_send_instantmessages";
  
  // Notes functions
  /**
   *
   */
  public static final String CORE_NOTES_CREATE_NOTES="core_notes_create_notes";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_NOTES_CREATE_NOTES="moodle_notes_create_notes";
  
  // Webservice functions
  /**
   *
   */
  public static final String CORE_WEBSERVICE_GET_SITE_INFO="core_webservice_get_site_info";
  //Legacy calls
  /**
   *
   */
  public static final String MOODLE_WEBSERVICE_GET_SITE_INFO="moodle_webservice_get_siteinfo";
  
  /**
   *
   */
  public static final String ENCODING="UTF-8";
  
  public static final String CORE_USER_GET_USERS= "core_user_get_users";
}

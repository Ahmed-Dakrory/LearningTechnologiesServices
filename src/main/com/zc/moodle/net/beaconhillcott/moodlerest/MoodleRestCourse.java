
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Vector;
import org.w3c.dom.NodeList;

/**
 * <p>Class containing the static routines used to create or update Moodle courses.</p>
 * 
 * @author Bill Antonia
 * @see MoodleCourse
 */
public class MoodleRestCourse {
 
   // private static final int BUFFER_MAX=4000;

    /**
     * <p>Constructor to create a MoodleRestCourse object.<br />
     * Not required to be used as all member methods are static and are called via the class name.</p>
     */
    public MoodleRestCourse(){}

    /**
     * <p>Method to return an array of MoodleCourse objects of all courses within the installation.<br />
     * This call communicates with the Moodle WebServices.</p>
     * 
     * @return MoodleCourse[]
     * @throws MoodleRestException
     * @throws UnsupportedEncodingException
     */
    public static MoodleCourse[] getAllCourses() throws MoodleRestException, UnsupportedEncodingException {
        Vector v=new Vector();
        MoodleCourse course=null;
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_COURSE_GET_COURSES:MoodleServices.CORE_COURSE_GET_COURSES;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestCourseException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        data.append("&").append(URLEncoder.encode("options[ids]", MoodleServices.ENCODING));
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        course=null;
        for (int j=0;j<elements.getLength();j++) {
            String content=elements.item(j).getTextContent();
            String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
            if (nodeName.equals("id")) {
                if (course==null)
                    course=new MoodleCourse(Long.parseLong(content));
                else {
                    v.add(course);
                    course=new MoodleCourse(Long.parseLong(content));
                }
            }
            course.setMoodleCourseField(nodeName, content);
        }
        if (course!=null)
            v.add(course);
        MoodleCourse[] courses=new MoodleCourse[v.size()];
        for (int i=0;i<v.size();i++) {
            courses[i]=(MoodleCourse)v.get(i);
        }
        v.removeAllElements();
        return courses;
    }

    /**
     * <p>Method to return a MoodleCourse object given the id of the course within Moodle.<br />
     * This call communicates with the Moodle WebServices.</p>
     * 
     * @param id Moodle id of the course to fetch
     * @return MoodleCourse object
     * @throws MoodleRestCourseException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleCourse getCourseFromId(long id) throws MoodleRestCourseException, UnsupportedEncodingException, MoodleRestException {
        long[] a=new long[1];
        a[0]=id;
        MoodleCourse[] crs=getCoursesById(a);
        return crs[0];
    }

    /**
     * <p>Method to return an array of MoodleCourse objects given an array of id's of the courses within Moodle.<br />
     * This call communicates with the Moodle WebServices.</p>
     * 
     * @param courseids Array of Moodle course ids
     * @return MoodleCourse[] Array of MoodleCourse objects
     * @throws MoodleRestException
     * @throws UnsupportedEncodingException
     */
    public static MoodleCourse[] getCoursesById(long[] courseids) throws MoodleRestException, UnsupportedEncodingException{
        Vector v=new Vector();
        MoodleCourse course=null;
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_COURSE_GET_COURSES:MoodleServices.CORE_COURSE_GET_COURSES;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestCourseException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<courseids.length;i++) {
            if (courseids[i]<1) throw new MoodleRestCourseException(MoodleRestException.REQUIRED_PARAMETER+" courseid"); data.append("&").append(URLEncoder.encode("options[ids]["+i+"]", MoodleServices.ENCODING)).append("=").append(courseids[i]);
        }
        data.trimToSize();
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        course=null;
        for (int j=0;j<elements.getLength();j++) {
            String content=elements.item(j).getTextContent();
            String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
            if (nodeName.equals("id")) {
                if (course==null)
                    course=new MoodleCourse(Long.parseLong(content));
                else {
                    v.add(course);
                    course=new MoodleCourse(Long.parseLong(content));
                }
            }
            course.setMoodleCourseField(nodeName, content);
        }
            
        if (course!=null)
            v.add(course);
        MoodleCourse[] courses=new MoodleCourse[v.size()];
        for (int i=0;i<v.size();i++) {
            courses[i]=(MoodleCourse)v.get(i);
        }
        v.removeAllElements();
        return courses;
    }

    /**
     * <p>Method to create a MoodleCourse given the details of the course in a MoodleCourse object.<br />
     * This call communicates with the Moodle WebServices.</p>
     * 
     * @param course MoodleCourse object. Needs to have shortname,fullname and categoryid completed before this call.
     * @return MoodleCourse object with the id updated to the course id within Moodle.
     * @throws MoodleRestCourseException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleCourse createCourse(MoodleCourse course) throws MoodleRestCourseException, UnsupportedEncodingException, MoodleRestException {
        MoodleCourse[] a=new MoodleCourse[1];
        a[0]=course;
        MoodleCourse[] crs=createCourses(a);
        return crs[0];
    }

    /**
     * <p>Method to create a MoodleCourse given the details of the course in a MoodleCourse object.<br />
     * This call communicates with the Moodle WebServices.</p>
     * 
     * @param course MoodleCourse[]. Array of MoodleCourse each initialised with shortname,fullname and categoryid before the call.
     * @return MoodleCourse[]. Updated array, each MoodleCourse object within the array having their id values updated to that in Moodle.
     * @throws MoodleRestException
     * @throws UnsupportedEncodingException
     */
    public static MoodleCourse[] createCourses(MoodleCourse[] course) throws MoodleRestException, UnsupportedEncodingException {
        Hashtable hash=new Hashtable();
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_COURSE_CREATE_COURSES:MoodleServices.CORE_COURSE_CREATE_COURSES;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestCourseException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<course.length;i++) {
            if (course[i]==null) throw new MoodleRestCourseException();
            if (course[i].getShortname()==null) throw new MoodleRestCourseException(MoodleRestException.REQUIRED_PARAMETER+" shortname"); else data.append("&").append(URLEncoder.encode("courses["+i+"][shortname]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getShortname(), MoodleServices.ENCODING));
            if (course[i].getFullname()==null) throw new MoodleRestCourseException(MoodleRestException.REQUIRED_PARAMETER+" fullname"); else data.append("&").append(URLEncoder.encode("courses["+i+"][fullname]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getFullname(), MoodleServices.ENCODING));
            if (course[i].getCategoryId()==-1) throw new MoodleRestCourseException(MoodleRestException.REQUIRED_PARAMETER+" categoryid"); else data.append("&").append(URLEncoder.encode("courses["+i+"][categoryid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getCategoryId(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][summaryformat]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getSummaryFormat(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][format]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getFormat(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][showgrades]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getShowGrades(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][newsitems]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getNewsItems(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][numsections]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getNumSections(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][maxbytes]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getMaxBytes(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][showreports]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getShowReports(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][hiddensections]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getHiddenSections(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][groupmode]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getGroupMode(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][groupmodeforce]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getGroupModeForce(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][defaultgroupingid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getDefaultGroupingId(), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][enablecompletion]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+(course[i].getEnableCompletion()?1:0), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][completionstartonenrol]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+(course[i].getCompletionStartOnEnrol()?1:0), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][completionnotify]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+(course[i].getCompletionNotify()?1:0), MoodleServices.ENCODING));
            data.append("&").append(URLEncoder.encode("courses["+i+"][visible]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+(course[i].getVisible()?1:0), MoodleServices.ENCODING));
            if (course[i].getSummary()!=null) data.append("&").append(URLEncoder.encode("courses["+i+"][summary]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getSummary(), MoodleServices.ENCODING));
            if (course[i].getIdNumber()!=null) data.append("&").append(URLEncoder.encode("courses["+i+"][idnumber]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getIdNumber(), MoodleServices.ENCODING));
            if (course[i].getLang()!=null) data.append("&").append(URLEncoder.encode("courses["+i+"][lang]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getLang(), MoodleServices.ENCODING));
            if (course[i].getForceTheme()!=null) data.append("&").append(URLEncoder.encode("courses["+i+"][forcetheme]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(course[i].getForceTheme(), MoodleServices.ENCODING));
            if (course[i].getStartDate()!=-1) data.append("&").append(URLEncoder.encode("courses["+i+"][startdate]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+course[i].getStartDate(), MoodleServices.ENCODING));
        }
        data.trimToSize();
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        for (int j=0;j<elements.getLength();j+=2) {
            hash.put(elements.item(j+1).getTextContent(), elements.item(j).getTextContent());
        }
        for (int i=0;i<course.length;i++) {
            if (hash.containsKey(course[i].getShortname()))
                course[i].setId(Long.parseLong((String)(hash.get(course[i].getShortname()))));
            else
                course[i]=null;
        }
        return course;
    }
    
    //core_course_get_content
    /**
     * <p>Method to get the content of a course given the Moodle id of the course.</p>
     * 
     * @param courseId
     * @param options
     * @return MoodleCourseContent[]
     * @throws UnsupportedEncodingException
     * @throws MoodleRestCourseException
     * @throws MoodleRestException
     */
    public static MoodleCourseContent[] getCourseContent(long courseId, MoodleCourseContentOption[] options) throws UnsupportedEncodingException, MoodleRestCourseException, MoodleRestException {
      StringBuilder data=new StringBuilder();
      String functionCall=MoodleServices.CORE_COURSE_GET_CONTENTS;
      if (MoodleCallRestWebService.getAuth()==null)
          throw new MoodleRestCourseException();
      else
          data.append(MoodleCallRestWebService.getAuth());
      data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
      data.append("&").append(URLEncoder.encode("courseid", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+courseId, MoodleServices.ENCODING));
      if (options!=null)
        for (int i=0; i<options.length; i++) {
          data.append("&").append(URLEncoder.encode("options["+i+"][name]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+options[i].getName(), MoodleServices.ENCODING));
          data.append("&").append(URLEncoder.encode("options["+i+"][value]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+options[i].getValue(), MoodleServices.ENCODING));
        }
      NodeList elements=MoodleCallRestWebService.call(data.toString());
      MoodleCourseContent result=null;
      MoodleModule module=null;
      MoodleModuleContent moduleContent=null;
      Vector<MoodleCourseContent> v=new Vector();
      for (int i=0;i<elements.getLength();i++) {
        String parent=elements.item(i).getParentNode().getParentNode().getParentNode().getParentNode().getNodeName();
        if (parent.equals("KEY"))
          parent=elements.item(i).getParentNode().getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
        String content=elements.item(i).getTextContent();
        String nodeName=elements.item(i).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
        if (parent.equals("RESPONSE") && nodeName.equals("id")) {
          if (result!=null) {
            if (module!=null) {
              if (moduleContent!=null)
                module.addContent(moduleContent);
              result.addMoodleModule(module);
            }
            v.add(result);
            module=null;
            moduleContent=null;
          }
          result=new MoodleCourseContent(Long.parseLong(content));
        } else {
          if (parent.equals("RESPONSE")) {
            result.setMoodleCourseContentField(nodeName, content);
          } else {
            if (parent.equals("modules") &&  nodeName.equals("id")) {
              if (module!=null) {
                if (moduleContent!=null)
                  module.addContent(moduleContent);
                result.addMoodleModule(module);
              }
              module=new MoodleModule(Long.parseLong(content));
              moduleContent=null;
            } else {
              if (parent.equals("modules")) {
                module.setMoodleModuleField(nodeName, content);
              } else {
                if (parent.equals("contents") &&  nodeName.equals("type")) {
                  if (moduleContent!=null) {
                    module.addContent(moduleContent);
                  }
                  moduleContent=new MoodleModuleContent();
                  moduleContent.setType(content);
                } else {
                  // Contents of module left other than the type
                  moduleContent.setMoodleModuleContentField(nodeName, content);
                }
              }
            }
          }
        }
      }
      if (result!=null) {
        if (module!=null) {
          if (moduleContent!=null)
            module.addContent(moduleContent);
          result.addMoodleModule(module);
        }
        v.add(result);
      }
      if (v.isEmpty())
        return null;
      MoodleCourseContent[] results=new MoodleCourseContent[v.size()];
      for (int i=0; i<v.size(); i++) {
        results[i]=v.get(i);
      }
      return results;
    }
    
}

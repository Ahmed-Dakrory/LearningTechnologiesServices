
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Vector;
import org.w3c.dom.NodeList;

/**
 * <p>Class containing the static routines to manipulate Moodle groups and users within course groups.</p>
 *
 * @author Bill Antonia
 * @see MoodleGroup
 * @see MoodleGroupUser
 */
public class MoodleRestGroup {

   // private static final int BUFFER_MAX=4000;

    /**
     * <p>Method to create a new group in a Moodle course.</p>
     * 
     * @param group MoodleGroup
     * @return group MoodleGroup object
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroup createGroup(MoodleGroup group) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        MoodleGroup[] a=new MoodleGroup[1];
        a[0]=group;
        MoodleGroup[] gps=createGroups(a);
        return gps[0];
    }

    /**
     * <p>Method to create new groups in Moodle courses.<br />
     * Groups to be created do not necessarily need to be within the same course.</p>
     * 
     * @param group MoodleGroup[]
     * @return group MoodleGroup[] Updated array of MoodleGroup objects. Group ids created by Moodle stored in the id attribute of each object.
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroup[] createGroups(MoodleGroup[] group) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        Hashtable hash=new Hashtable();
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_CREATE_GROUPS:MoodleServices.CORE_GROUP_CREATE_GROUPS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<group.length;i++) {
            if (group[i]==null) throw new MoodleRestGroupException();
            if (group[i].getCourseId()<0) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("groups["+i+"][courseid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+group[i].getCourseId(), MoodleServices.ENCODING));
            if (group[i].getName()==null || group[i].getName().equals("")) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("groups["+i+"][name]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+group[i].getName(), MoodleServices.ENCODING));
            if (group[i].getDescription()==null) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("groups["+i+"][description]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+group[i].getDescription(), MoodleServices.ENCODING));
            if (group[i].getEnrolmentKey()==null) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("groups["+i+"][enrolmentkey]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+group[i].getEnrolmentKey(), MoodleServices.ENCODING));
        }
        data.trimToSize();
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        for (int j=0;j<elements.getLength();j+=5) {
            hash.put(elements.item(j+2).getTextContent(), elements.item(j).getTextContent());
        }
        for (int i=0;i<group.length;i++) {
            if (hash.containsKey(group[i].getName()))
                group[i].setId(Long.parseLong((String)(hash.get(group[i].getName()))));
            else
                group[i]=null;
        }
        return group;
    }

    /**
     * <p>Method to retrieve the information about a Moodle group from its id.</p>
     * 
     * @param groupid long
     * @return group MoodleGroup
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroup getGroupById(long groupid) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        long[] a=new long[1];
        a[0]=groupid;
        MoodleGroup[] gps=getGroupsById(a);
        return gps[0];
    }

    /**
     * <p>Method to fetch the details of a number of Moodle groups from their ids.</p>
     * 
     * @param groupids long[]
     * @return group MoodleGroup[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroup[] getGroupsById(long[] groupids) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        Vector v=new Vector();
        MoodleGroup group=null;
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_GET_GROUPS:MoodleServices.CORE_GROUP_GET_GROUPS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<groupids.length;i++) {
            if (groupids[i]<1) throw new MoodleRestGroupException(); data.append("&").append(URLEncoder.encode("groupids["+i+"]", MoodleServices.ENCODING)).append("=").append(groupids[i]);
        }
        data.trimToSize();
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        group=null;
        for (int j=0;j<elements.getLength();j++) {
            String content=elements.item(j).getTextContent();
            String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
            if (nodeName.equals("id")) {
                if (group==null)
                    group=new MoodleGroup(Long.parseLong(content));
                else {
                    v.add(group);
                    group=new MoodleGroup(Long.parseLong(content));
                }
            }
            group.setMoodleGroupField(nodeName, content);
        }
        if (group!=null)
            v.add(group);
        MoodleGroup[] groups=new MoodleGroup[v.size()];
        for (int i=0;i<v.size();i++) {
            groups[i]=(MoodleGroup)v.get(i);
        }
        v.removeAllElements();
        return groups;
    }

    /**
     * <p>Method to return the details of groups within a Moodle course from the course id.</p>
     * 
     * @param id long
     * @return groups MoodleGroup[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroup[] getGroupsFromCourseId(long id) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        Vector v=new Vector();
        MoodleGroup group=null;
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_GET_COURSE_GROUPS:MoodleServices.CORE_GROUP_GET_COURSE_GROUPS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        if (id<1) throw new MoodleRestGroupException(); else  data.append("&").append(URLEncoder.encode("courseid", MoodleServices.ENCODING)).append("=").append(id);
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        for (int j=0;j<elements.getLength();j++) {
            String content=elements.item(j).getTextContent();
            String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
            if (nodeName.equals("id")) {
                if (group==null)
                    group=new MoodleGroup(Long.parseLong(content));
                else  {
                    v.add(group);
                    group=new MoodleGroup(Long.parseLong(content));
                }
            }
            group.setMoodleGroupField(nodeName, content);
        }
        if (group!=null)
            v.add(group);
        MoodleGroup[] groups=new MoodleGroup[v.size()];
        for (int i=0;i<v.size();i++) {
            groups[i]=(MoodleGroup)v.get(i);
        }
        v.removeAllElements();
        return groups;
    }

    /**
     * <p>Method to delete a Moodle group given its id.</p>
     * 
     * @param groupid long
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static void deleteGroupById(long groupid) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        long[] a=new long[1];
        a[0]=groupid;
        deleteGroupsById(a);
    }

    /**
     * <p>Method to delete groups from Moodle given the ids of the groups.</p>
     * 
     * @param groupids long[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static void deleteGroupsById(long[] groupids) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_DELETE_GROUPS:MoodleServices.CORE_GROUP_DELETE_GROUPS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<groupids.length;i++) {
            if (groupids[i]<1) throw new MoodleRestGroupException(); data.append("&").append(URLEncoder.encode("groupids["+i+"]", MoodleServices.ENCODING)).append("=").append(groupids[i]);
        }
        data.trimToSize();
        MoodleCallRestWebService.call(data.toString());
    }

    /**
     * <p>Method to retrieve details of the memberships of a Moodle group.</p>
     * 
     * @param groupid long
     * @return groupUsers MoodleGroupUser[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroupUser[] getMembersFromGroupId(long groupid) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        long[] a=new long[1];
        a[0]=groupid;
        return getMembersFromGroupIds(a);
    }

    /**
     * <p>Method to retrieve details of the memberships of a number of Moodle groups.</p>
     * 
     * @param groupids long[]
     * @return groupUsers MoodleGroupUser[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleGroupUser[] getMembersFromGroupIds(long[] groupids) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        Vector v=new Vector();
        MoodleGroupUser user=null;
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_GET_GROUP_MEMBERS:MoodleServices.CORE_GROUP_GET_GROUP_MEMBERS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<groupids.length;i++) {
            if (groupids[i]<1) throw new MoodleRestGroupException(); data.append("&").append(URLEncoder.encode("groupids["+i+"]", MoodleServices.ENCODING)).append("=").append(groupids[i]);
        }
        data.trimToSize();
        NodeList elements=MoodleCallRestWebService.call(data.toString());
        user=null;
        for (int j=0;j<elements.getLength();j+=2) {
            String content1=elements.item(j).getTextContent();
            String content2=elements.item(j+1).getTextContent();
            user=new MoodleGroupUser(Long.parseLong(content1),Long.parseLong(content2));
            v.add(user);
        }
        if (user!=null)
            v.add(user);
        MoodleGroupUser[] users=new MoodleGroupUser[v.size()];
        for (int i=0;i<v.size();i++) {
            users[i]=(MoodleGroupUser)v.get(i);
        }
        v.removeAllElements();
        return users;
    }

    /**
     * <p>Method to add a users membership to a Moodle group.</p>
     * @param user MoodleGroupUser
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static void addMemberToGroup(MoodleGroupUser user) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        MoodleGroupUser[] users=new MoodleGroupUser[1];
        users[0]=user;
        addMembersToGroups(users);
    }

    /**
     * <p>Method to add a number of users memberships to a number of Moodle groups.</p>
     * 
     * @param users MoodleGroupUser[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static void addMembersToGroups(MoodleGroupUser[] users) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_ADD_GROUP_MEMBERS:MoodleServices.CORE_GROUP_ADD_GROUP_MEMBERS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<users.length;i++) {
            if (users[i]==null) throw new MoodleRestGroupException();
            if (users[i].getGroupId()<1) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("members["+i+"][groupid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+users[i].getGroupId(), MoodleServices.ENCODING));
            if (users[i].getUserId()<1) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("members["+i+"][userid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+users[i].getUserId(), MoodleServices.ENCODING));
        }
        data.trimToSize();
        MoodleCallRestWebService.call(data.toString());
    }

    /**
     * <p>Method to remove a users membership of a Moodle group.</p>
     * 
     * @param user MoodleGroupUser
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static void deleteMemberOfGroup(MoodleGroupUser user) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        MoodleGroupUser[] users=new MoodleGroupUser[1];
        users[0]=user;
        deleteMembersOfGroups(users);
    }

    /**
     * <p>Method to remove a number of users memberships from Moodle groups.</p>
     * 
     * @param users MoodleGroupUser[]
     * @throws MoodleRestGroupException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static void deleteMembersOfGroups(MoodleGroupUser[] users) throws MoodleRestGroupException, UnsupportedEncodingException, MoodleRestException {
        StringBuilder data=new StringBuilder();
        String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_GROUP_DELETE_GROUP_MEMBERS:MoodleServices.CORE_GROUP_DELETE_GROUP_MEMBERS;
        if (MoodleCallRestWebService.getAuth()==null)
            throw new MoodleRestGroupException();
        else
            data.append(MoodleCallRestWebService.getAuth());
        data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
        for (int i=0;i<users.length;i++) {
            if (users[i]==null) throw new MoodleRestGroupException();
            if (users[i].getGroupId()<1) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("members["+i+"][groupid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+users[i].getGroupId(), MoodleServices.ENCODING));
            if (users[i].getUserId()<1) throw new MoodleRestGroupException(); else data.append("&").append(URLEncoder.encode("members["+i+"][userid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+users[i].getUserId(), MoodleServices.ENCODING));
        }
        data.trimToSize();
        MoodleCallRestWebService.call(data.toString());
    }
}

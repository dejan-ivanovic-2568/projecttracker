package tapestry.projecttracker.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import tapestry.projecttracker.data.MemberDAO;
import tapestry.projecttracker.entities.Member;
import tapestry.projecttracker.pages.view.ViewDashboard;

/**
 * Start page of application ProjectTracker.
 */
@Import(stylesheet = {
    "context:css/bootstrap-custom.css",
    "context:css/main.css"})
public class Index {

//    TEMPORARY to ADD members
    @Property
    private Member member;

    @SessionState
    @Property
    private Member loggedInMember;

    @Property
    @Validate("required")
    private String memberUsername;

    @Property
    @Validate("required")
    private String memberPassword;

    @InjectComponent("loginForm")
    private Form form;

    @Inject
    private MemberDAO memberDao;

    @Inject
    private Session dbs;


    void onValidateFromLoginForm() {
        member = memberDao.validateMember(memberUsername, memberPassword);
        if (member == null) {
            System.out.println("SERVER SIDE VERIFICATION ERROR!");
            form.recordError("Login failed, wrong username or password!");
        } else {
            loggedInMember = member;
        }
    }

    Object onSuccessFromLoginForm() {
        System.out.println("LOGGED IN MEMBER AT INDEX..." + loggedInMember.getMemberUsername());
        return ViewDashboard.class;
    }

    public String getUser() {
        return loggedInMember.getMemberUsername();
    }

    public boolean getLoggedIn() {
        return (loggedInMember.getMemberUsername() == null) ? true : false;
    }
//    @CommitAfter
//    Object onSuccessFromAddMemberForm() {
//        dbs.persist(member);
//        return ViewMembers.class;
//    }

    void onActivate() {
        System.out.println("ON ACTIVATE - INDEX - LOGGED IN MEMBER..." + loggedInMember.getMemberUsername());
    }

}

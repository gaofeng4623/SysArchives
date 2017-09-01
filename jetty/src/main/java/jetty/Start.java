package jetty;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/30.
 */
@Service
public class Start {
    public static void main(String[] args){

        Server server = new Server(8080);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/jetty");
        context.setDescriptor("D:/apache-tomcat-7.0.67/webapps/SysArchives/WEB-INF/web.xml");
        context.setResourceBase("D:/apache-tomcat-7.0.67/webapps/SysArchives");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
